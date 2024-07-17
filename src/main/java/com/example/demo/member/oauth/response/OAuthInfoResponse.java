package com.example.demo.member.oauth.response;

import com.example.demo.member.entity.SocialRoute;

public interface OAuthInfoResponse {
    String getEmail();
    SocialRoute getOAuthProvider();
}