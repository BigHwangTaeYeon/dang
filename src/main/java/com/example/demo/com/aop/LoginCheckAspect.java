package com.example.demo.com.aop;


import com.example.demo.com.config.ResponseMessage;
import com.example.demo.member.oauth.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoginCheckAspect {
    private final JwtTokenProvider jwtProvider;

    public LoginCheckAspect(JwtTokenProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Before("@annotation(mungMo.memberService.com.annotation.LoginCheckEssential)")
    public void LoginCheckEssential(JoinPoint jp) throws Throwable{
        log.info("LoginCheckEssential AOP called");
        Arrays.stream(jp.getArgs())
                .filter(argument -> argument instanceof HttpServletRequest)
                .findFirst()
                .ifPresentOrElse(request -> {
                    HttpServletRequest httpRequest = (HttpServletRequest) request;
                    String authorizationHeader = httpRequest.getHeader("Authorization");
                    log.info("Authorization Header: " + authorizationHeader);  // 로그 추가
                    if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                        try {
                            throw new LoginException(ResponseMessage.valueOfCode("Unauthorized").getMessage());
                        } catch (LoginException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    String token = jwtProvider.getAccessToken(httpRequest);
                    String subject = jwtProvider.extractSubject(token);
                    if (subject == null || subject.isEmpty()) {
                        try {
                            throw new LoginException(ResponseMessage.valueOfCode("Unauthorized").getMessage());
                        } catch (LoginException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, () -> {
                    try {
                        throw new LoginException(ResponseMessage.valueOfCode("Unauthorized").getMessage());
                    } catch (LoginException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
