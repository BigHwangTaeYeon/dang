package com.example.demo.member.oauth.response;

import com.example.demo.member.entity.SocialRoute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse implements OAuthInfoResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {
        private String email;
    }

    @Override
    public String getEmail() {
        return kakaoAccount.email;
    }

    @Override
    public SocialRoute getOAuthProvider() {
        return SocialRoute.KAKAO;
    }
}