package com.gdsc.solutionchallenge.member.controller;

import com.gdsc.solutionchallenge.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/profile")
    public void collection(Long id) {
        memberService.getProfile();
    }
}
