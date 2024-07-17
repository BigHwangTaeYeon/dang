package com.example.demo.member.entity;

import com.example.demo.member.dto.DogInfoDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "oauth_login")
@Getter @Setter
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @OneToOne
    private DogInfo dogInfo;

    @OneToMany(mappedBy = "walk_records_id")
    private List<WalkRecord> walkRecords;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauthprovider")
    private SocialRoute oAuthProvider;

    public MemberEntity() {
    }

    @Builder
    private MemberEntity(Long id, String email, DogInfo dogInfo, List<WalkRecord> walkRecords, SocialRoute oauthProvider) {
        this.id = id;
        this.email = email;
        this.dogInfo = dogInfo;
        this.walkRecords = walkRecords;
        this.oAuthProvider = oauthProvider;
    }
}
