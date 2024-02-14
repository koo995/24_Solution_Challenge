package com.gdsc.solutionchallenge.member.service;

import com.gdsc.solutionchallenge.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public void getProfile() {

    }
}
