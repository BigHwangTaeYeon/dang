
package com.example.demo.member.entity;

import com.example.demo.member.dto.WalkDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "walk_record")
@Getter
@Setter
public class WalkRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "walk_record_id")
    private Long id;

    @Column(name = "walk_start_time")
    private LocalDateTime walkStartTime;

    @Column(name = "walk_end_time")
    private LocalDateTime walkEndTime;

    @Column(name = "walk_time")
    private int walkTimeMinute;

    @OneToMany(mappedBy = "member_id")
    private MemberEntity member;

    private void calculateTime(LocalDateTime start, LocalDateTime end) {
        int endTime = end.getHour() * 60 + end.getMinute();
        int startTime = start.getHour() * 60 + start.getMinute();
        this.walkTimeMinute = endTime - startTime;
    }

    private WalkRecord(MemberEntity member, WalkDTO walkDTO) {
        this.walkStartTime = walkDTO.getWalkStartTime();
        this.walkEndTime = walkDTO.getWalkEndTime();
        calculateTime(walkDTO.getWalkStartTime(), walkDTO.getWalkEndTime());
        this.member = member;
    }

    public static WalkRecord from(MemberEntity member, WalkDTO walkDTO) {
        return new WalkRecord(member, walkDTO);
    }

    public WalkDTO changeToDTO() {
        return WalkDTO.builder()
                .id(id)
                .walkStartTime(walkStartTime)
                .walkEndTime(walkEndTime)
                .walkTime(walkTimeMinute)
                .build();
    }
}
