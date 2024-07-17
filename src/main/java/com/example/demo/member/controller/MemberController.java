package com.example.demo.member.controller;

import com.example.demo.member.oauth.jwt.AuthTokens;
import com.example.demo.member.oauth.jwt.AuthTokensGenerator;
import com.example.demo.member.oauth.param.KakaoLoginParams;
import com.example.demo.member.oauth.param.NaverLoginParams;
import com.example.demo.member.service.MemberApiService;
import com.example.demo.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/auth")
public class MemberController {

    private final MemberService oAuthLoginService;
    private final MemberApiService memberApiService;
    private final AuthTokensGenerator authTokensGenerator;

    public MemberController(MemberService oAuthLoginService, MemberApiService memberApiService, AuthTokensGenerator authTokensGenerator) {
        this.oAuthLoginService = oAuthLoginService;
        this.memberApiService = memberApiService;
        this.authTokensGenerator = authTokensGenerator;
    }

    /**
     * state 있는거
     * https://kauth.kakao.com/oauth/authorize?client_id=1b02a8c5d5f529866e3bb44855645b62&redirect_uri=http://localhost:8000/member-service/v1/auth/kakao&response_type=code&state=dangdang
     * state 없는거
     * https://kauth.kakao.com/oauth/authorize?client_id=1b02a8c5d5f529866e3bb44855645b62&redirect_uri=http://localhost:8000/member-service/v1/auth/kakao&response_type=code
     */

    @GetMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestParam(value = "code", required = false) String code) {
        KakaoLoginParams params = new KakaoLoginParams(code);
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    @GetMapping("/naver")
    public ResponseEntity<AuthTokens> loginNaver(@RequestParam(value = "code", required = false) String code) {
        NaverLoginParams params = new NaverLoginParams(code);
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }
    // logout 은 그냥 클라이언트 토큰 날리는걸로

    /**
     * 탈퇴
     * @param request
     * @return
     */
    @DeleteMapping("/deleteAccount")
    public ResponseEntity<?> deleteAccount(HttpServletRequest request) {
        oAuthLoginService.deleteAccount(authTokensGenerator.extractMemberIdToHeader(request));
        return ResponseEntity.ok(ResponseEntity.ok());
    }
}
