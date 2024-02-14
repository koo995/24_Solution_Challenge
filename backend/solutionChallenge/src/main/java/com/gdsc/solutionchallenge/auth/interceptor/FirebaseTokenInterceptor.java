package com.gdsc.solutionchallenge.auth.interceptor;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class FirebaseTokenInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        FirebaseToken decodedToken;
        String header = request.getHeader("Authorization");
        log.info("header={}", header);
        if (header == null || !header.startsWith("Bearer ")) {
            log.info("first");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // todo 예외 처리 똑바로 하자.
            return false;
        }
        String token = header.split(" ")[1];
//        String token = header.substring(7);
        log.info("token={}", token);

        // verify IdToken
        try{
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        } catch (FirebaseAuthException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println("e.getMessage() = " + e.getMessage());
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
