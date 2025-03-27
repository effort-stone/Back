package com.effortstone.backend.domain.diary.controller;


import com.effortstone.backend.domain.diary.dto.request.DiaryRequestDto;
import com.effortstone.backend.domain.diary.dto.response.DiaryResponseDto;
import com.effortstone.backend.domain.diary.entity.Diary;
import com.effortstone.backend.domain.diary.service.DiaryService;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/diaries")
public class DiaryController {

    private final DiaryService diaryService;


    // 🔹 모든 Diary 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<Diary>>> getAllDiaries() {
        // diaryService에서 모든 Diary 리스트를 조회하고, 성공 응답으로 반환
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.DIARY_SEARCH_ALL, diaryService.getAllDiaries()));
    }

    // 🔹 ID로 Diary 조회
    @GetMapping("/{diaryCode}")
    public ResponseEntity<ApiResponse<Diary>> getDiaryById(@PathVariable Long diaryCode) {
        Diary diary = diaryService.getDiaryById(diaryCode); // diaryCode로 Diary 조회
        // Optional을 사용해 Diary가 존재하면 성공 응답, 없으면 404 반환
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.DIARY_SEARCH_CODE, diary));
    }
    // 🔹 나의 모든 Diary 조회
    @GetMapping("/my")
    public ApiResponse<List<DiaryResponseDto>> getMyAllDiaries() {
        // 요청 본문의 DiaryRequestDto를 사용해 새로운 Diary 생성 후 성공 응답 반환
        return diaryService.getMyAllDiaries();
    }

    // 🔹 Diary 생성
    @PostMapping("/")
    public ApiResponse<DiaryResponseDto> createDiary(@RequestBody DiaryRequestDto diary) {
        // 요청 본문의 DiaryRequestDto를 사용해 새로운 Diary 생성 후 성공 응답 반환
        return diaryService.createDiary(diary);
    }

    // 🔹 Diary 수정
    @PutMapping("/{diaryCode}")
    public ApiResponse<DiaryResponseDto> updateDiary(@PathVariable Long diaryCode, @RequestBody DiaryRequestDto diaryDetails) {
        // diaryCode와 요청 본문의 DiaryRequestDto를 사용해 Diary 수정 후 성공 응답 반환
        return diaryService.updateDiary(diaryCode, diaryDetails);
    }

    // 🔹 Diary 삭제
    @DeleteMapping("/{diaryCode}")
    public ResponseEntity<ApiResponse<Void>> deleteDiary(@PathVariable Long diaryCode) {
        diaryService.deleteDiary(diaryCode); // diaryCode로 Diary 삭제
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.DIARY_DELETE_OK)); // 성공 응답 반환 (데이터 없음)
    }
}
