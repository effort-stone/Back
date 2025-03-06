package com.effortstone.backend.domain.user.service;


import com.effortstone.backend.domain.user.dto.request.UserRequestDto;
import com.effortstone.backend.domain.user.entity.Provider;
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
    public ApiResponse<User> updateUser(String userCode, UserRequestDto.UserUpdateRequest userDetails) {
        String currentUserCode = SecurityUtil.getCurrentUserCode(); // 현재 사용자 코드 가져오기
        User user = userRepository.findById(currentUserCode)       // 기존 사용자 조회
                .orElseThrow(() -> new RuntimeException("User not found")); // 없으면 예외 발생
        // Setter를 사용해 기존 엔티티 필드 수정 (Null 체크 포함)
        if (userDetails.getName() != null) user.setUserName(userDetails.getName());
        if (userDetails.getLatestLogin() != null) user.setUserLatestLogin(userDetails.getLatestLogin()); // UserLatestLogin 가정
        if (userDetails.getLevel() != null) user.setUserStoneLevel(userDetails.getLevel());
        if (userDetails.getExp() != null) user.setUserStoneExp(userDetails.getExp());
        if (userDetails.getSideObj() != null) user.setUserSideObj(userDetails.getSideObj());
        if (userDetails.getTopObj() != null) user.setUserTopObj(userDetails.getTopObj());
        if (userDetails.getAccountLinkType() != null) user.setUserLoginProvider(Provider.fromCode(userDetails.getAccountLinkType()));
        if (userDetails.getLinkDate() != null) user.setUserLinkDate(userDetails.getLinkDate());
        if (userDetails.getGender() != null) user.setUserGender(userDetails.getGender());
        if (userDetails.getBirthDay() != null) user.setUserBirth(userDetails.getBirthDay());
        if (userDetails.getNumber() != null) user.setUserPhone(userDetails.getNumber());
        if (userDetails.getAlram() != null) user.setUserIsAlert(userDetails.getAlram());
        if (userDetails.getSubscriptionEndDate() != null) user.setUserSubEnddate(userDetails.getSubscriptionEndDate());
        if (userDetails.getIsFreeTrialUsed() != null) user.setUserFreeSub(userDetails.getIsFreeTrialUsed());
        // status는 User 엔티티에 없으므로 제외하거나 추가 필드 필요

        User updatedUser = userRepository.save(user); // 수정된 엔티티 저장
        return ApiResponse.success(SuccessCode.USER_UPDATE_SUCCESS, updatedUser); // 성공 응답 반환User updatedUser = userRepository.save(user);

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
