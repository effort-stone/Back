package com.effortstone.backend.domain.diary.service;


import com.effortstone.backend.domain.diary.dto.request.DiaryRequestDto;
import com.effortstone.backend.domain.diary.dto.response.DiaryDto;
import com.effortstone.backend.domain.diary.dto.response.DiaryResponseDTO;
import com.effortstone.backend.domain.diary.entity.Diary;
import com.effortstone.backend.domain.diary.repository.DiaryRepository;
import com.effortstone.backend.domain.routine.entity.Routine;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.global.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;


    public List<Diary> getAllDiaries() {
        return diaryRepository.findAll();
    }

    public Optional<Diary> getDiaryById(Long diaryCode) {
        return diaryRepository.findByDiaryCode(diaryCode);
    }

    public Diary createDiary(DiaryRequestDto.DiaryCreateRequest diary) {
        String userCode = SecurityUtil.getCurrentUserCode();
        User currentUser = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Diary newDiary = Diary.builder()
                .user(currentUser)
                .diaryTitle(diary.getDiaryTitle())
                .diaryContent(diary.getDiaryContent())
                .diaryDate(LocalDate.now())
                .build();
        return diaryRepository.save(newDiary);
    }

    public Diary updateDiary(Long diaryCode, DiaryRequestDto.DiaryUpdateRequest diaryDetails) {
        return diaryRepository.findByDiaryCode(diaryCode).map(diary -> {
            diary.setDiaryTitle(diaryDetails.getDiaryTitle());
            diary.setDiaryContent(diaryDetails.getDiaryContent());
            return diaryRepository.save(diary);
        }).orElseThrow(() -> new IllegalArgumentException("Diary not found"));
    }

    public void deleteDiary(Long diaryCode) {
        diaryRepository.deleteByDiaryCode(diaryCode);
    }


    public DiaryResponseDTO getMonthlyDiarys(YearMonth yearMonth) {
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate monthStart = yearMonth.atDay(1);
        LocalDate monthEnd = yearMonth.atEndOfMonth();


        List<Diary> diaries = diaryRepository.findByUserAndDiaryDateBetween(user, monthStart, monthEnd);
        Map<LocalDate, List<DiaryDto>> diaryMap = new HashMap<>();
        for (LocalDate date = monthStart; !date.isAfter(monthEnd); date = date.plusDays(1)) {
            diaryMap.put(date, new ArrayList<>());
        }
        // 다이어리 데이터를 해당 날짜에 추가
        for (Diary diary : diaries) {
            LocalDate date = diary.getDiaryDate(); // ✅ getCreatedAt() 대신 getDate() 사용
            diaryMap.get(date).add(mapToDTO(diary));
        }
        return new DiaryResponseDTO(diaryMap);
    }

    private DiaryDto mapToDTO(Diary diary) {
        return DiaryDto.builder()
                .id(diary.getDiaryCode())
                .content(diary.getDiaryContent())
                .date(diary.getDiaryDate())
                .build();
    }

}
