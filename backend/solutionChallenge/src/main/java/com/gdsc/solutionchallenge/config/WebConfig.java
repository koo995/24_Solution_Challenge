package com.gdsc.solutionchallenge.config;

import com.gdsc.solutionchallenge.auth.interceptor.FirebaseTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FirebaseTokenInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/error", "/sign-up", "/sign-in", "/logout");
    }
}
