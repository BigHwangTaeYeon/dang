package com.example.demo.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DogInfoDTO {
    private String name;
    private String breed;
    private String age;
    private String symptom;
}
