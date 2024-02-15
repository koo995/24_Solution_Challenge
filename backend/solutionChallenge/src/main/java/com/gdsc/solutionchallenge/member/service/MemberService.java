package com.gdsc.solutionchallenge.member.service;

import com.gdsc.solutionchallenge.member.dto.ProfileResponseDto;
import com.gdsc.solutionchallenge.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public ProfileResponseDto getProfile(Long memberId, Pageable pageable) {
        ProfileResponseDto response = memberRepository.findByIdWithImage(memberId, pageable);
        return response;
    }
}
