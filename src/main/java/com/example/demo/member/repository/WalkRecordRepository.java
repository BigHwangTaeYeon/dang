package com.example.demo.member.repository;

import com.example.demo.member.entity.MemberEntity;
import com.example.demo.member.entity.WalkRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WalkRecordRepository extends JpaRepository<WalkRecord, Long> {
    List<WalkRecord> findByMemberIdAndWalkStartDayBetween(MemberEntity member, LocalDateTime walkStartTime, LocalDateTime walkEndTime);
}
