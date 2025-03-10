package com.effortstone.backend.domain.diary.service;


import com.effortstone.backend.domain.diary.dto.request.DiaryRequestDto;
import com.effortstone.backend.domain.diary.dto.response.DiaryResponseDto;
import com.effortstone.backend.domain.diary.entity.Diary;
import com.effortstone.backend.domain.diary.repository.DiaryRepository;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import com.effortstone.backend.global.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;


    // 🔹 모든 Diary 조회
    public List<Diary> getAllDiaries() {
        return diaryRepository.findAll(); // diaryRepository를 통해 DB에서 모든 Diary를 조회하여 List로 반환
    }

    // 🔹 ID로 Diary 조회
    public Diary getDiaryById(Long diaryCode) {
        // diaryCode에 해당하는 Diary를 조회, 없으면 예외 발생
        return diaryRepository.findByDiaryCode(diaryCode)
                .orElseThrow(() -> new RuntimeException("Diary not found"));
    }

    // 🔹 Diary 생성 (Builder 적용)
    public ApiResponse<DiaryResponseDto> createDiary(DiaryRequestDto diary) {
        String userCode = SecurityUtil.getCurrentUserCode(); // 현재 로그인한 사용자의 userCode 가져오기
        User currentUser = userRepository.findById(userCode) // userCode로 사용자 조회
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Diary newDiary = Diary.builder() // 새로운 Diary 객체 생성
                .user(currentUser) // 현재 사용자 설정
                .diaryContent(diary.getContent()) // 요청 DTO에서 내용 설정
                .build();
        diaryRepository.save(newDiary); // DB에 저장 후 생성된 Diary 반환
        return ApiResponse.success(SuccessCode.DIARY_CREATE_OK, mapToDTO(newDiary));
    }

    // 🔹 Diary 수정 (Setter 적용)
    public ApiResponse<DiaryResponseDto> updateDiary(Long diaryCode, DiaryRequestDto diaryDetails) {
        Diary updatedDiary = getDiaryById(diaryCode); // 수정할 Diary 조회
        updatedDiary.setDiaryContent(diaryDetails.getContent()); // 새로운 내용으로 업데이트
        diaryRepository.save(updatedDiary); // 변경된 Diary를 DB에 저장
        return ApiResponse.success(SuccessCode.DIARY_UPDATE_OK, mapToDTO(updatedDiary));
    }

    // 🔹 Diary 삭제
    public void deleteDiary(Long diaryCode) {
        diaryRepository.deleteByDiaryCode(diaryCode); // diaryCode에 해당하는 Diary를 DB에서 삭제
    }


//    public DiaryResponseDTO getMonthlyDiarys(YearMonth yearMonth) {
//        String userCode = SecurityUtil.getCurrentUserCode();
//        User user = userRepository.findById(userCode)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        LocalDate monthStart = yearMonth.atDay(1);
//        LocalDate monthEnd = yearMonth.atEndOfMonth();
//
//
//        List<Diary> diaries = diaryRepository.findByUserAndDiaryDateBetween(user, monthStart, monthEnd);
//        Map<LocalDate, List<DiaryDto>> diaryMap = new HashMap<>();
//        for (LocalDate date = monthStart; !date.isAfter(monthEnd); date = date.plusDays(1)) {
//            diaryMap.put(date, new ArrayList<>());
//        }
//        // 다이어리 데이터를 해당 날짜에 추가
//        for (Diary diary : diaries) {
//            LocalDate date = diary.getDiaryDate(); // ✅ getCreatedAt() 대신 getDate() 사용
//            diaryMap.get(date).add(mapToDTO(diary));
//        }
//        return new DiaryResponseDTO(diaryMap);
//    }

//    private DiaryDto mapToDTO(Diary diary) {
//        return DiaryDto.builder()
//                .id(diary.getDiaryCode())
//                .content(diary.getDiaryContent())
//                .date(diary.getDiaryDate())
//                .build();

    private DiaryResponseDto mapToDTO(Diary diary) {
        return DiaryResponseDto.builder()
                .id(diary.getDiaryCode())
                .content(diary.getDiaryContent())
                .dateTime(LocalDate.from(diary.getCreatedAt()))
                .build();
    }

}
