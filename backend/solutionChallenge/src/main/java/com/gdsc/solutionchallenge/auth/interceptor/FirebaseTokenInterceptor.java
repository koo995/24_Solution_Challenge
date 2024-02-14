package com.gdsc.solutionchallenge.auth.interceptor;

import com.gdsc.solutionchallenge.auth.exception.UnAuthorizedException;
import com.gdsc.solutionchallenge.member.domain.Member;
import com.gdsc.solutionchallenge.member.repository.MemberRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class FirebaseTokenInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        FirebaseToken decodedToken;
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new UnAuthorizedException();
        }
        String token = header.split(" ")[1];
        // verify IdToken
        try{
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        } catch (FirebaseAuthException e) {
            throw new UnAuthorizedException();
        }
        String uid = decodedToken.getUid();
        Member member = memberRepository.findByUid(uid)
                .orElse(Member.builder()
                        .username(decodedToken.getName())
                        .email(decodedToken.getEmail())
                        .uid(uid)
                        .build());
        // 여기서 member을 영속화하는 것이 좋은 방법일까?
        memberRepository.save(member);
        request.setAttribute("loginMember", member);
        return true;
    }
}
