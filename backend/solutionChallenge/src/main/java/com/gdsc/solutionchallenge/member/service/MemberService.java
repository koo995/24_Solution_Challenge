package com.gdsc.solutionchallenge.member.service;

import com.gdsc.solutionchallenge.app.repository.ImageRepository;
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

    public ProfileResponseDto getProfile(Long memberId, FilterCondition filterCondition, Pageable pageable) {
        PageImpl<ImageDto> imagePage = imageRepository.findByMemberId(memberId, filterCondition, pageable);
        if (imagePage.getTotalElements() == 0) {
            return new EmptyProfileResponseDto();
        }
        return new MainProfileResponseDto(imagePage.getTotalElements(), imagePage);
    }
}
