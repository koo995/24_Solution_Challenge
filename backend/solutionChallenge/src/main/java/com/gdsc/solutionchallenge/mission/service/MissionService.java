package com.gdsc.solutionchallenge.mission.service;

import com.gdsc.solutionchallenge.ai.service.GeminiMainService;
import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.app.domain.Species;
import com.gdsc.solutionchallenge.app.exception.ImageNotFoundException;
import com.gdsc.solutionchallenge.app.exception.NoLatLngException;
import com.gdsc.solutionchallenge.app.exception.NoSpeciesException;
import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import com.gdsc.solutionchallenge.app.repository.SpeciesRepository;
import com.gdsc.solutionchallenge.auth.exception.UnAuthorizedException;
import com.gdsc.solutionchallenge.file.FileStoreService;
import com.gdsc.solutionchallenge.member.domain.Member;
import com.gdsc.solutionchallenge.mission.domain.MemberMission;
import com.gdsc.solutionchallenge.mission.domain.Mission;
import com.gdsc.solutionchallenge.mission.dto.request.MissionCreateDto;
import com.gdsc.solutionchallenge.mission.dto.response.MissionDetail;
import com.gdsc.solutionchallenge.mission.dto.response.MissionListResponse;
import com.gdsc.solutionchallenge.mission.dto.response.MissionResult;
import com.gdsc.solutionchallenge.mission.exception.AlreadyCompletedMissionException;
import com.gdsc.solutionchallenge.mission.exception.AlreadyExistSpeciesMissionException;
import com.gdsc.solutionchallenge.mission.exception.MissionFailException;
import com.gdsc.solutionchallenge.mission.exception.MissionNotFoundException;
import com.gdsc.solutionchallenge.mission.repository.MissionRepository;
import com.gdsc.solutionchallenge.utils.ImgMetaDataExtractor;
import com.google.type.LatLng;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MissionService {

    private final FileStoreService fileStoreService;

    private final MissionRepository missionRepository;

    private final SpeciesRepository speciesRepository;

    private final ImageRepository imageRepository;

    private final GeminiMainService geminiMainService;

    @Transactional
    public Long createMission(MissionCreateDto missionCreateDto, Member loginMember) {
        // 먼저 로그인한 사용자가 이미지의 사용자와 일치하는지 체크하자
        Long imageId = missionCreateDto.getImageId();
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException());
        if (image.getMember().getId() != loginMember.getId()) {
            throw new UnAuthorizedException("권한이 없습니다.");
        }
        // 기존의 이미지로 부터 이미지파일을 가져오자
        String imageUrl = image.getFullPath();
        // speciesId로 부터 이름을 가져오자
        Long speciesId = missionCreateDto.getSpeciesId();
        Species species = speciesRepository.findById(speciesId)
                .orElseThrow(NoSpeciesException::new);

        // mission 생성
        Mission mission = Mission.builder()
                .title(missionCreateDto.getTitle())
                .description(missionCreateDto.getDescription())
                .species(species)
                .imageUrl(imageUrl)
                .build();
        try {
            missionRepository.save(mission);
            MemberMission.createMemberMission(mission, loginMember);
            return mission.getId();
        } catch (Exception e) {
            throw new AlreadyExistSpeciesMissionException();
        }
    }

    public List<MissionListResponse> getList(Member loginMember) {
        // 먼저 모든 리스트를 다 보여주긴 해야한다.
        // 그러나... 사용자별로 완료여부를 다르게 표시해야 한다.
        Long memberId = loginMember.getId();
        List<MissionListResponse> response = missionRepository.findAllWithMissionCompleteResult(memberId);
        return response;
    }

    public MissionDetail detail(Long missionId, Member loginMember) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionNotFoundException());
        List<MemberMission> memberMissions = mission.getMemberMission();
        Boolean result = false;
        for (MemberMission memberMission : memberMissions) {
            if (memberMission.getMember().getId() == loginMember.getId()) {
                result = true;
                break;
            }
        }

        return MissionDetail.builder()
                .title(mission.getTitle())
                .createdAt(mission.getCreatedDate())
                .scientificName(mission.getSpecies().getScientificName())
                .koreaName(mission.getSpecies().getKoreaName())
                .speciesId(mission.getSpecies().getId())
                .description(mission.getDescription())
                .missionId(missionId)
                .imageUrl(mission.getImageUrl())
                .result(result)
                .build();
    }

    // 이미지 아이디를 반환 받을까?
    @Transactional
    public MissionResult imageUpload(Long missionId, Member loginMember, MultipartFile file) {
        // 우선 미션아이디로부터 어떤 종에 해당하는지 찾아야 한다.
        // 먼저 미션에 성공했는지 부터 따져보자.
        // 성공한 것이 없다면
        // 그리고 그 종을 gemini prompt에 넣어준다.
        // 결과값을 받고 true or false 에러처리와 미션 성공처리를 한다.
        // 그리고 이미지포스트도 만들어서 저장한다.
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(MissionNotFoundException::new);
        // 로그인 멤버가 미션에 참여했다면 에러
        List<MemberMission> memberMissions = mission.getMemberMission();
        memberMissions.stream()
                .filter(memberMission -> memberMission.getMember().getId() == loginMember.getId())
                .forEach(memberMission -> {
            throw new AlreadyCompletedMissionException();
        });
        String scientificName = mission.getSpecies().getScientificName();
        Boolean result = geminiMainService.booleanPrediction(file, scientificName);
        if (!result) {
            throw new MissionFailException();
        }
        MemberMission.createMemberMission(mission, loginMember);
        int score = loginMember.addScore(20);
        // 메타데이터 추출해보고 올바르면 저장.
        LatLng latLng;
        try {
            latLng = ImgMetaDataExtractor.extractLatLng(file);
        } catch (Exception e) {
            throw new NoLatLngException();
        }
        String imageUrl = fileStoreService.storeFile(file);
        Image image = Image.builder()
                .fullPath(imageUrl)
                .latLng(latLng)
                .uploadFileName(file.getOriginalFilename())
                .type(file.getContentType())
                .build();
        image.setMember(loginMember);
        image.setSpecies(mission.getSpecies());
        imageRepository.save(image);
        return new MissionResult(image.getId(), score);
    }
}
