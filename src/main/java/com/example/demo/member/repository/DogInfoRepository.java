package com.example.demo.member.repository;

import com.example.demo.member.entity.DogInfo;
import com.example.demo.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogInfoRepository extends JpaRepository<DogInfo, Long> {
}
