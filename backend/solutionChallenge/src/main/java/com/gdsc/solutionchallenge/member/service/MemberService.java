package com.gdsc.solutionchallenge.member.service;

import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import com.gdsc.solutionchallenge.member.domain.Member;
import com.gdsc.solutionchallenge.member.dto.request.FilterCondition;
import com.gdsc.solutionchallenge.member.dto.response.EmptyProfileResponseDto;
import com.gdsc.solutionchallenge.member.dto.response.ImageDto;
import com.gdsc.solutionchallenge.member.dto.response.MainProfileResponseDto;
import com.gdsc.solutionchallenge.member.dto.response.ProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final ImageRepository imageRepository;

    public ProfileResponseDto getProfile(Member member, FilterCondition filterCondition, Pageable pageable) {
        PageImpl<ImageDto> imagePage = imageRepository.findImageByMemberId(member.getId(), filterCondition, pageable);
        if (imagePage.getTotalElements() == 0) {
            return EmptyProfileResponseDto.builder()
                    .username(member.getUsername())
                    .build();
        }
        return MainProfileResponseDto.builder()
                .totalImage(imagePage.getTotalElements())
                .image(imagePage)
                .score(member.getScore())
                .username(member.getUsername())
                .build();
    }
}
