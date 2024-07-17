package com.example.demo.member.entity;

import com.example.demo.member.dto.DogInfoDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "dog_info")
@Getter
@Setter
public class DogInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dog_info_id")
    private Long id;

    @Column(name = "dog_name")
    private String name;

    @Column(name = "breed")
    private String breed;

    @Column
    private String age;

    @Column
    private String symptom;

    @OneToOne(mappedBy = "member_id")
    private MemberEntity member;

    private DogInfo(DogInfoDTO dto) {
        this.name = dto.getName();
        this.breed = dto.getBreed();
        this.age = dto.getAge();
        if(StringUtils.hasText(dto.getSymptom())) this.symptom = dto.getSymptom();
    }

    public static DogInfo from(DogInfoDTO dto){
        return new DogInfo(dto);
    }

    public DogInfoDTO changeToDogInfoDTO() {
        return DogInfoDTO.builder()
                .name(name)
                .breed(breed)
                .age(age)
                .symptom(StringUtils.hasText(symptom) ? symptom : "")
                .build();
    }
}
