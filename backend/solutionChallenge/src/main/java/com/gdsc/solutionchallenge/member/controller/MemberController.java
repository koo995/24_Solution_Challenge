package com.gdsc.solutionchallenge.member.controller;

import com.gdsc.solutionchallenge.auth.annotation.Login;
import com.gdsc.solutionchallenge.member.domain.Member;
import com.gdsc.solutionchallenge.member.dto.request.FilterCondition;
import com.gdsc.solutionchallenge.member.dto.response.ProfileResponseDto;
import com.gdsc.solutionchallenge.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/v1/auth/profile")
    public ProfileResponseDto collection(@Login Member loginMember, @ModelAttribute FilterCondition filterCondition, Pageable pageable) {
        return memberService.getProfile(loginMember, filterCondition, pageable);
    }
}
