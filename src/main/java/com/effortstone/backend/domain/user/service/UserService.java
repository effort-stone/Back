package com.effortstone.backend.domain.user.service;


import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import com.effortstone.backend.global.error.ErrorCode;
import com.effortstone.backend.global.error.exception.CustomException;
import com.effortstone.backend.global.security.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    //특정 사용자 호출
    public ApiResponse<User> getUserInfo(String userCode){
        User user = getUserById(userCode);
        return ApiResponse.success(SuccessCode.USER_GET_INFO_SUCCESS,user);
    }
    //사용자 본인 호출
    public ApiResponse<User> getUserInfoMY(){
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = getUserById(userCode);
        return ApiResponse.success(SuccessCode.USER_GET_INFO_SUCCESS,user);
    }
    // 🔹 모든 사용자 조회
    public ApiResponse<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ApiResponse.success(SuccessCode.USER_GET_ALL_SUCCESS, users);
    }

    // 🔹 사용자 생성 (회원가입)
    public ApiResponse<User> createUser(User user) {
        User savedUser = userRepository.save(user);
        return ApiResponse.success(SuccessCode.USER_CREATE_SUCCESS, savedUser);
    }

    // 🔹 사용자 정보 수정
    public ApiResponse<User> updateUser(User userDetails) {
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = User.builder()
                .userCode(userCode)
                .userBirth(userDetails.getUserBirth())
                .userEmail(userDetails.getUserEmail())
                .userGender(userDetails.getUserGender())
                .userIsSub(userDetails.getUserIsSub())
                .userIsAlert(userDetails.getUserIsAlert())
                .userName(userDetails.getUserName())
                .userPhone(userDetails.getUserPhone())
                .userLoginProvider(userDetails.getUserLoginProvider())
                .roleType(userDetails.getRoleType())
                .build();
        User updatedUser = userRepository.save(user);
        return ApiResponse.success(SuccessCode.USER_UPDATE_SUCCESS, updatedUser);
    }

    // 🔹 사용자 삭제
    public ApiResponse<Void> deleteUser() {
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = getUserById(userCode);
        userRepository.delete(user);
        return ApiResponse.success(SuccessCode.USER_DELETE_SUCCESS, null);
    }

    // 사용자 조회 함수
    private User getUserById(String userCode){
        return userRepository.findById(userCode).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

}
