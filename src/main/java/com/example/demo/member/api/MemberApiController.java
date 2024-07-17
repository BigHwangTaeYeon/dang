package com.example.demo.member.api;

import com.example.demo.com.annotation.LoginCheckEssential;
import com.example.demo.com.exception.PreconditionFailedException;
import com.example.demo.member.dto.DogInfoDTO;
import com.example.demo.member.dto.WalkDTO;
import com.example.demo.member.oauth.jwt.AuthTokensGenerator;
import com.example.demo.member.service.MemberApiService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1")
public class MemberApiController {
    private final MemberApiService memberApiService;
    private final AuthTokensGenerator authTokensGenerator;

    public MemberApiController(MemberApiService memberApiService, AuthTokensGenerator authTokensGenerator) {
        this.memberApiService = memberApiService;
        this.authTokensGenerator = authTokensGenerator;
    }

    /*
     * 강아지 정보 가져오기
     */
    @LoginCheckEssential
    @GetMapping("/dogInfo")
    public ResponseEntity<?> dogInfo(HttpServletRequest request) throws PreconditionFailedException {
        return ResponseEntity.ok(
                new Result<>(memberApiService.dogInfo(authTokensGenerator.extractMemberIdToHeader(request)))
        );
    }

    /*
     * 산책 기록 가져오기
     * startDate endDate
     */
    @LoginCheckEssential
    @GetMapping("/walkInfo")
    public ResponseEntity<?> walkInfo(HttpServletRequest request, WalkDTO walkDTO) {
        return ResponseEntity.ok(
                new Result<>(memberApiService.walkInfo(authTokensGenerator.extractMemberIdToHeader(request), walkDTO))
        );
    }

    /*
     * 산책 기록 일 기준 가져오기
     */
//    @LoginCheckEssential
//    @GetMapping("/walkDayInfo")
//    public ResponseEntity<?> walkDayInfo(HttpServletRequest request, WalkDTO walkDTO) throws PreconditionFailedException {
//        return ResponseEntity.ok(
//                new Result<>(memberApiService.walkDayInfo(authTokensGenerator.extractMemberIdToHeader(request)), walkDTO)
//        );
//    }

    /*
     * 산책 기록하기
     * startDate endDate
     */
    @LoginCheckEssential
    @PostMapping("/saveWalk")
    public ResponseEntity<?> saveWalk(HttpServletRequest request, WalkDTO walkDTO) {
        memberApiService.saveWalk(authTokensGenerator.extractMemberIdToHeader(request), walkDTO);
        return ResponseEntity.ok(ResponseEntity.ok());
    }

    /*
     * 산책 기록 삭제하기
     * startDate endDate
     */
    @LoginCheckEssential
    @DeleteMapping("/deleteWalk/{id}")
    public ResponseEntity<?> deleteWalk(@PathVariable Long id) {
        memberApiService.deleteWalk(id);
        return ResponseEntity.ok(ResponseEntity.ok());
    }

    /*
     * 강아지 저장, 수정
     */
    @LoginCheckEssential
    @PostMapping("/saveDog")
    public ResponseEntity<?> saveDog(HttpServletRequest request, DogInfoDTO dto) {
        memberApiService.saveDog(authTokensGenerator.extractMemberIdToHeader(request), dto);
        return ResponseEntity.ok(ResponseEntity.ok());
    }

    /*
     * 증상 저장, 수정
     */
    @LoginCheckEssential
    @GetMapping("/saveSymptom")
    public ResponseEntity<?> saveSymptom(HttpServletRequest request,@RequestParam String symptom) {
        memberApiService.saveSymptom(authTokensGenerator.extractMemberIdToHeader(request), symptom);
        return ResponseEntity.ok(ResponseEntity.ok());
    }

    @Getter
    public static class Result<T> {
        private final T data;
        public Result(T data) {
            this.data = data;
        }
    }

}