package com.gdsc.solutionchallenge.auth.controller;

import com.gdsc.solutionchallenge.auth.annotation.Login;
import com.gdsc.solutionchallenge.member.domain.Member;
import com.gdsc.solutionchallenge.member.dto.ProfileResponseDto;
import com.gdsc.solutionchallenge.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class AuthController {

    private final MemberService memberService;

    @GetMapping("/api/v1/auth/profile")
    public ProfileResponseDto collection(@Login Member LoginMember, Pageable pageable) {
        ProfileResponseDto profileResponse = memberService.getProfile(LoginMember.getId(), pageable);
        profileResponse.SetUsername(LoginMember.getUsername());
        return profileResponse;
    }

}
