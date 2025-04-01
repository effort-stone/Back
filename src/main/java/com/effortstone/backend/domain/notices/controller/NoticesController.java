package com.effortstone.backend.domain.notices.controller;

import com.effortstone.backend.domain.notices.dto.request.NoticesRequestDto;
import com.effortstone.backend.domain.notices.dto.response.NoticesResponseDto;
import com.effortstone.backend.domain.notices.service.NoticesService;
import com.effortstone.backend.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/v1/notices")
@RequiredArgsConstructor
public class NoticesController {

    private final NoticesService noticesService;

    /**
     * 공지사항 생성 API (POST /api/notices)
     */
    @PostMapping
    public ApiResponse<NoticesResponseDto> createNotice(@RequestBody NoticesRequestDto requestDto) {
        return noticesService.createNotice(requestDto);
    }

    /**
     * 전체 공지사항 조회 API (GET /api/notices)
     */
    @GetMapping
    public ApiResponse<List<NoticesResponseDto>> getAllNotices() {
        return noticesService.getAllNotices();
    }

    /**
     * 단일 공지사항 조회 API (GET /api/notices/{noticeCode})
     */
    @GetMapping("/{noticeCode}")
    public ApiResponse<NoticesResponseDto> getNotice(@PathVariable Long noticeCode) {
        return noticesService.getNotice(noticeCode);
    }

    /**
     * 공지사항 수정 API (PUT /api/notices/{noticeCode})
     */
    @PutMapping("/{noticeCode}")
    public ApiResponse<NoticesResponseDto> updateNotice(@PathVariable Long noticeCode,
                                                        @RequestBody NoticesRequestDto requestDto) {
        return noticesService.updateNotice(noticeCode, requestDto);
    }

    /**
     * 공지사항 삭제 API (DELETE /api/notices/{noticeCode})
     */
    @DeleteMapping("/{noticeCode}")
    public ApiResponse<Void> deleteNotice(@PathVariable Long noticeCode) {
        return noticesService.deleteNotice(noticeCode);
    }
}
