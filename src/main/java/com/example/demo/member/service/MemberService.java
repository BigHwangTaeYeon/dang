package com.example.demo.member.service;

import com.example.demo.com.exception.ValidationException;
import com.example.demo.member.entity.MemberEntity;
import com.example.demo.member.oauth.client.RequestOAuthInfoService;
import com.example.demo.member.oauth.jwt.AuthTokens;
import com.example.demo.member.oauth.jwt.AuthTokensGenerator;
import com.example.demo.member.oauth.param.OAuthLoginParams;
import com.example.demo.member.oauth.response.OAuthInfoResponse;
import com.example.demo.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public MemberService(MemberRepository memberRepository, AuthTokensGenerator authTokensGenerator, RequestOAuthInfoService requestOAuthInfoService) {
        this.memberRepository = memberRepository;
        this.authTokensGenerator = authTokensGenerator;
        this.requestOAuthInfoService = requestOAuthInfoService;
    }

    @Transactional
    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    @Transactional
    public void deleteAccount(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return Optional.ofNullable(memberRepository.findByEmail(oAuthInfoResponse.getEmail()))
                        .map(MemberEntity::getId)
                        .orElseGet(()-> {
                            try {
                                return newMember(oAuthInfoResponse);
                            } catch (ValidationException e) {
                                throw new RuntimeException(e);
                            }
                        });
    }

    @Transactional
    private Long newMember(OAuthInfoResponse oAuthInfoResponse) throws ValidationException {
        MemberEntity member = MemberEntity.builder()
                .email(oAuthInfoResponse.getEmail())
                .oauthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();
        memberRepository.save(member);
        return memberRepository.findByEmail(member.getEmail()).getId();
    }

}
