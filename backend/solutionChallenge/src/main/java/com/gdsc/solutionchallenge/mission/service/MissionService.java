package com.gdsc.solutionchallenge.mission.service;

import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.app.domain.Species;
import com.gdsc.solutionchallenge.app.exception.ImageNotFoundException;
import com.gdsc.solutionchallenge.app.exception.NoSpeciesException;
import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import com.gdsc.solutionchallenge.app.repository.SpeciesRepository;
import com.gdsc.solutionchallenge.auth.exception.UnAuthorizedException;
import com.gdsc.solutionchallenge.member.domain.Member;
import com.gdsc.solutionchallenge.member.repository.MemberRepository;
import com.gdsc.solutionchallenge.mission.domain.Mission;
import com.gdsc.solutionchallenge.mission.dto.MissionRequestDto;
import com.gdsc.solutionchallenge.mission.exception.AlreadyExistSpeciesMissionException;
import com.gdsc.solutionchallenge.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MissionService {

    private final MissionRepository missionRepository;

    private final MemberRepository memberRepository;

    private final SpeciesRepository speciesRepository;

    private final ImageRepository imageRepository;

    @Transactional
    public void createMission(MissionRequestDto missionRequestDto, Member loginMember) {
        // 먼저 로그인한 사용자가 이미지의 사용자와 일치하는지 체크하자
        Long imageId = missionRequestDto.getImageId();
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException(imageId));
        if (image.getMember().getId() != loginMember.getId()) {
            throw new UnAuthorizedException("권한이 없습니다.");
        }
        // 기존의 이미지로 부터 이미지파일을 가져오자
        String imageUrl = image.getFullPath();
        // speciesId로 부터 이름을 가져오자
        Long speciesId = missionRequestDto.getSpeciesId();
        Species species = speciesRepository.findById(speciesId)
                .orElseThrow(() -> new NoSpeciesException());

        // mission 생성
        Mission mission = Mission.builder()
                .title(missionRequestDto.getTitle())
                .description(missionRequestDto.getDescription())
                .species(species)
                .imageUrl(imageUrl)
                .build();
        Long missionId;
        try {
            missionId = missionRepository.save(mission).getId();
        } catch (Exception e) {
            throw new AlreadyExistSpeciesMissionException();
        }
        // todo id값을 반환해주자.
    }
}
