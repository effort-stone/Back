package com.effortstone.backend.domain.diary.controller;


import com.effortstone.backend.domain.diary.dto.request.DiaryRequestDto;
import com.effortstone.backend.domain.diary.dto.response.DiaryDto;
import com.effortstone.backend.domain.diary.entity.Diary;
import com.effortstone.backend.domain.diary.service.DiaryService;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/diary")
public class DiaryController {

    private final DiaryService diaryService;


    @GetMapping
    public ResponseEntity<ApiResponse<List<Diary>>> getAllDiaries() {
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.DIARY_SEARCH_ALL, diaryService.getAllDiaries()));
    }

    @GetMapping("/{diaryCode}")
    public ResponseEntity<ApiResponse<Diary>> getDiaryById(@PathVariable Long diaryCode) {
        Optional<Diary> diary = diaryService.getDiaryById(diaryCode);
        return diary.map(d -> ResponseEntity.ok(ApiResponse.success(SuccessCode.DIARY_SEARCH_CODE, d)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Diary>> createDiary(@RequestBody DiaryRequestDto.DiaryCreateRequest diary) {
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.DIARY_CREATE_OK, diaryService.createDiary(diary)));
    }

    @PutMapping("/{diaryCode}")
    public ResponseEntity<ApiResponse<Diary>> updateDiary(@PathVariable Long diaryCode, @RequestBody DiaryRequestDto.DiaryUpdateRequest diaryDetails) {
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.DIARY_UPDATE_OK, diaryService.updateDiary(diaryCode, diaryDetails)));
    }

    @DeleteMapping("/{diaryCode}")
    public ResponseEntity<ApiResponse<Void>> deleteDiary(@PathVariable Long diaryCode) {
        diaryService.deleteDiary(diaryCode);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.DIARY_DELETE_OK));
    }
}
