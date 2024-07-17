package com.example.demo.member.oauth.param;

import com.example.demo.member.entity.SocialRoute;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    SocialRoute oAuthProvider();
    MultiValueMap<String, String> makeBody();
}
