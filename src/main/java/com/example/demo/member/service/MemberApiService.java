package com.example.demo.member.service;

import com.example.demo.com.config.ResponseMessage;
import com.example.demo.com.exception.PreconditionFailedException;
import com.example.demo.member.dto.DogInfoDTO;
import com.example.demo.member.dto.WalkDTO;
import com.example.demo.member.entity.DogInfo;
import com.example.demo.member.entity.MemberEntity;
import com.example.demo.member.entity.WalkRecord;
import com.example.demo.member.repository.DogInfoRepository;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.repository.WalkRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberApiService {

    private final MemberRepository memberRepository;
    private final DogInfoRepository dogInfoRepository;
    private final WalkRecordRepository walkRecordRepository;

    public MemberApiService(MemberRepository memberRepository, DogInfoRepository dogInfoRepository, WalkRecordRepository walkRecordRepository) {
        this.memberRepository = memberRepository;
        this.dogInfoRepository = dogInfoRepository;
        this.walkRecordRepository = walkRecordRepository;
    }

    @Transactional(readOnly = true)
    public DogInfoDTO dogInfo(Long id) throws PreconditionFailedException {
        return memberRepository.findById(id)
                .map(entity -> entity.getDogInfo().changeToDogInfoDTO())
                .orElseThrow(() -> new PreconditionFailedException(ResponseMessage.PRECONDITIONFAILED.getMessage()));
    }

    @Transactional(readOnly = true)
    public List<WalkDTO> walkInfo(Long id, WalkDTO walkDTO) {
        MemberEntity member = memberRepository.findById(id).orElseThrow();
        return walkRecordRepository.findByMemberIdAndWalkStartDayBetween(member, walkDTO.getWalkStartTime(), walkDTO.getWalkEndTime())
                .stream()
                .map(WalkRecord::changeToDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional
    public void saveWalk(Long id, WalkDTO walkDTO) {
        MemberEntity member = memberRepository.findById(id).orElseThrow();
        walkRecordRepository.save(WalkRecord.from(member, walkDTO));
    }

    @Transactional
    public void deleteWalk(Long id) {
        walkRecordRepository.deleteById(id);
    }

//    @Transactional(readOnly = true)
//    public List<WalkRecord> walkDayInfo(Long id) throws PreconditionFailedException {
//        return memberRepository.findById(id)
//                .map(MemberEntity::getWalkRecords)
//                .orElseThrow(() -> new PreconditionFailedException(ResponseMessage.PRECONDITIONFAILED.getMessage()));
//    }

    @Transactional
    public void saveDog(Long id, DogInfoDTO dto) {
        memberRepository.findById(id)
                .ifPresent(entity -> entity.setDogInfo(DogInfo.from(dto)));
    }

    @Transactional
    public void saveSymptom(Long id, String symptom) {
        memberRepository.findById(id)
                .ifPresent(entity -> entity.getDogInfo().setSymptom(symptom));
    }
}
