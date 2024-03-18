package com.gdsc.solutionchallenge.auth.service;

import com.gdsc.solutionchallenge.member.domain.Member;
import com.gdsc.solutionchallenge.member.repository.MemberRepository;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member JoinAndLogin(FirebaseToken decodedToken) {
        String uid = decodedToken.getUid();
        Member member = memberRepository.findByUid(uid)
                .orElseGet(Member.builder()
                        .username(decodedToken.getName())
                        .email(decodedToken.getEmail())
                        .uid(uid)::build);
        memberRepository.save(member);
        return member;
    }

}
