package com.example.demo.member.oauth.param;

import com.example.demo.member.entity.SocialRoute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@NoArgsConstructor
public class NaverLoginParams implements OAuthLoginParams {
    private String authorizationCode;
    private String state;

    public NaverLoginParams(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    @Override
    public SocialRoute oAuthProvider() {
        return SocialRoute.NAVER;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        body.add("state", state);
        return body;
    }
}