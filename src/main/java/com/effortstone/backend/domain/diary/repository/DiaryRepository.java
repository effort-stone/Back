package com.effortstone.backend.domain.diary.repository;

import com.effortstone.backend.domain.diary.entity.Diary;
import com.effortstone.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findByUser_UserCode(String userCode);
    Optional<Diary> findByDiaryCode(Long diaryCode);
    void deleteByDiaryCode(Long diaryCode);
    //List<Diary> findByUserAndDiaryDateBetween(User user, LocalDate startDate, LocalDate endDate);

}
