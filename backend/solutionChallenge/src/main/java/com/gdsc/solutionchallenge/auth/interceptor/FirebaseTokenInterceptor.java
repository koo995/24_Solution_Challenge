package com.gdsc.solutionchallenge.auth.interceptor;

import com.gdsc.solutionchallenge.auth.exception.UnAuthorizedException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
public class FirebaseTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("request={}", request);
        log.info("method={}", request.getMethod());
        log.info("request={}", request.getContentType());
        if (request instanceof ContentCachingRequestWrapper) {
            ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
            log.info("Request URL: {}, Request Body: {}", request.getRequestURI(), new String(cachingRequest.getContentAsByteArray()));
        } else {
            log.info("Request URL: {}", request.getRequestURI());
        }


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
        request.setAttribute("decodedToken", decodedToken);
        return true;
    }
}
