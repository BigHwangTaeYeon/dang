package com.example.demo.member.oauth.client;

import com.example.demo.member.entity.SocialRoute;
import com.example.demo.member.oauth.param.OAuthLoginParams;
import com.example.demo.member.oauth.response.OAuthInfoResponse;

public interface OAuthApiClient {
    SocialRoute oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}