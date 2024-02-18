package com.gdsc.solutionchallenge.member.service;

import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import com.gdsc.solutionchallenge.member.dto.EmptyProfileResponseDto;
import com.gdsc.solutionchallenge.member.dto.ImageDto;
import com.gdsc.solutionchallenge.member.dto.MainProfileResponseDto;
import com.gdsc.solutionchallenge.member.dto.ProfileResponseDto;
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

    public ProfileResponseDto getProfile(Long memberId, Pageable pageable) {
        PageImpl<ImageDto> imagePage = imageRepository.findByMemberId(memberId, pageable);
        if (imagePage.getTotalElements() == 0) {
            return new EmptyProfileResponseDto();
        }
        return new MainProfileResponseDto(imagePage.getTotalElements(), imagePage);
    }
}
