package com.effortstone.backend.domain.notices.service;

import com.effortstone.backend.domain.notices.dto.request.NoticesRequestDto;
import com.effortstone.backend.domain.notices.dto.response.NoticesResponseDto;
import com.effortstone.backend.domain.notices.entity.Notices;
import com.effortstone.backend.domain.notices.repository.NoticesRepository;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticesService {
    private final NoticesRepository noticesRepository;

    /**
     * 공지사항 등록 (Create)
     *
     * @param requestDto 등록 요청 정보 (제목, 내용)
     * @return ApiResponse에 감싼 등록된 공지사항 정보 (NoticesResponseDto)
     */
    public ApiResponse<NoticesResponseDto> createNotice(NoticesRequestDto requestDto) {
        // 새로운 공지사항 엔티티 생성
        Notices notice = Notices.builder()
                .noticeTitle(requestDto.getTitle())
                .noticeContent(requestDto.getContent())
                .build();
        // 엔티티 저장 (DB에 noticeCode가 부여됨)
        notice = noticesRepository.save(notice);
        // 저장된 엔티티를 DTO로 변환한 후 ApiResponse.success로 감싸서 반환
        return ApiResponse.success(SuccessCode.NOTICE_CREATE_SUCCESS, NoticesResponseDto.fromEntity(notice));
    }

    /**
     * 전체 공지사항 조회 (Read)
     *
     * @return ApiResponse에 감싼 전체 공지사항 리스트 (NoticesResponseDto 리스트)
     */
    @Transactional(readOnly = true)
    public ApiResponse<List<NoticesResponseDto>> getAllNotices() {
        List<Notices> notices = noticesRepository.findAll();
        List<NoticesResponseDto> dtoList = notices.stream()
                .map(NoticesResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ApiResponse.success(SuccessCode.NOTICE_GET_ALL_SUCCESS, dtoList);
    }

    /**
     * 단일 공지사항 조회 (Read)
     *
     * @param noticeCode 조회할 공지사항의 고유 식별자
     * @return ApiResponse에 감싼 단일 공지사항 정보 (NoticesResponseDto)
     */
    @Transactional(readOnly = true)
    public ApiResponse<NoticesResponseDto> getNotice(Long noticeCode) {
        Notices notice = noticesRepository.findById(noticeCode)
                .orElseThrow(() -> new RuntimeException("Notice not found: " + noticeCode));
        return ApiResponse.success(SuccessCode.NOTICE_GET_SUCCESS, NoticesResponseDto.fromEntity(notice));
    }

    /**
     * 공지사항 수정 (Update)
     *
     * @param noticeCode 수정할 공지사항의 고유 식별자
     * @param requestDto 수정할 내용 (제목, 내용)
     * @return ApiResponse에 감싼 수정된 공지사항 정보 (NoticesResponseDto)
     */
    public ApiResponse<NoticesResponseDto> updateNotice(Long noticeCode, NoticesRequestDto requestDto) {
        Notices notice = noticesRepository.findById(noticeCode)
                .orElseThrow(() -> new RuntimeException("Notice not found: " + noticeCode));
        // 엔티티 업데이트
        notice.setNoticeTitle(requestDto.getTitle());
        notice.setNoticeContent(requestDto.getContent());
        // 수정된 엔티티 저장
        notice = noticesRepository.save(notice);
        return ApiResponse.success(SuccessCode.NOTICE_UPDATE_SUCCESS, NoticesResponseDto.fromEntity(notice));
    }

    /**
     * 공지사항 삭제 (Delete)
     *
     * @param noticeCode 삭제할 공지사항의 고유 식별자
     * @return ApiResponse에 감싼 삭제 성공 메시지 혹은 null 데이터
     */
    public ApiResponse<Void> deleteNotice(Long noticeCode) {
        Notices notice = noticesRepository.findById(noticeCode)
                .orElseThrow(() -> new RuntimeException("Notice not found: " + noticeCode));
        noticesRepository.delete(notice);
        return ApiResponse.success(SuccessCode.NOTICE_DELETE_SUCCESS, null);
    }

}
