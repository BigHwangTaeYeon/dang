package com.example.demo.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class WalkDTO {
    private Long id;
    private LocalDateTime walkStartTime;
    private LocalDateTime walkEndTime;
    private int walkTime;
}
