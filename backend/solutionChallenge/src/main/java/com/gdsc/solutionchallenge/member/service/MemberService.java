package com.gdsc.solutionchallenge.member.service;

import com.gdsc.solutionchallenge.member.dto.EmptyProfileResponseDto;
import com.gdsc.solutionchallenge.member.dto.ImageDto;
import com.gdsc.solutionchallenge.member.dto.MainProfileResponseDto;
import com.gdsc.solutionchallenge.member.dto.ProfileResponseDto;
import com.gdsc.solutionchallenge.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public ProfileResponseDto getProfile(Long memberId, Pageable pageable) {
        PageImpl<ImageDto> imagePage = memberRepository.findByMemberId(memberId, pageable);
        if (imagePage.getTotalElements() == 0) {
            return new EmptyProfileResponseDto();
        }
        return new MainProfileResponseDto(imagePage.getTotalElements(), imagePage);
    }
}
