package com.effortstone.backend.domain.user.controller;


import com.effortstone.backend.domain.user.dto.response.CalenderResponseDto;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.domain.user.service.UserCalenderService;
import com.effortstone.backend.domain.user.service.UserService;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserCalenderService userCalenderService;


    // 🔹 사용자 전체 조회
    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    // 🔹 특정 사용자 조회
    @GetMapping("/{userCode}")
    public ApiResponse<User> getUser(@PathVariable String userCode) {
        return userService.getUserInfo(userCode);
    }
    // 🔹 사용자 본인 조회
    @GetMapping("/my")
    public ApiResponse<User> getUserSelf() {
        return userService.getUserInfoMY();
    }

//    // 🔹 사용자 생성 (회원가입)
//    @PostMapping("/")
//    public ApiResponse<User> createUser(@RequestBody User user) {
//        return userService.createUser(user);
//    }

    // 🔹 사용자 정보 수정
    @PostMapping("/")
    public ApiResponse<User> updateUser(
            @RequestBody User userDetails) {
        return userService.updateUser(userDetails);
    }

    // 🔹 사용자 삭제
    @DeleteMapping("")
    public ApiResponse<Void> deleteUser() {
        return userService.deleteUser();
    }

    // 🔹 사용자 월별 캘린더 조회
    /**
     * 통합 캘린더 데이터 조회 API  
     * URL 예시: GET /api/v1/routines/calendar?year=2025&month=04
     */
    @GetMapping("/calendar")
    public ResponseEntity<ApiResponse<CalenderResponseDto>> getUserCalendarData(
            @RequestParam int year,
            @RequestParam int month) {

        YearMonth yearMonth = YearMonth.of(year, month);
        CalenderResponseDto data = userCalenderService.getCalendarData(yearMonth);
        ApiResponse<CalenderResponseDto> response;
        response = ApiResponse.success(SuccessCode.USER_CALENDER_SEARCH_SUCCESS, data);
        return ResponseEntity.ok(response);
    }


}
