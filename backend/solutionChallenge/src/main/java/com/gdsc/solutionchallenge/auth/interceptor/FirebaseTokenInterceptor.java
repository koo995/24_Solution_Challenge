package com.gdsc.solutionchallenge.auth.interceptor;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
@Component
public class FirebaseTokenInterceptor implements HandlerInterceptor {

    private final FirebaseAuth firebaseAuth;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        FirebaseToken decodedToken;
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // todo 예외 처리 똑바로 하자.
            return false;
        }
        String token = header.substring(7);

        // verify IdToken
        try{
            decodedToken = firebaseAuth.verifyIdToken(token);
        } catch (FirebaseAuthException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        log.info("decoded token={}", decodedToken);
        log.info("decoded getUid={}", decodedToken.getUid());
        log.info("decoded getEmail={}", decodedToken.getEmail());
        log.info("decoded getIssuer={}", decodedToken.getIssuer());
        log.info("decoded getPicture={}", decodedToken.getPicture());
        return true;
    }
}
