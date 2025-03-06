package com.effortstone.backend.global.auth;

import com.effortstone.backend.domain.routine.dto.response.RoutineDTO;
import com.effortstone.backend.domain.routine.entity.Routine;
import com.effortstone.backend.domain.user.dto.response.UserResponseDto;
import com.effortstone.backend.domain.user.entity.Provider;
import com.effortstone.backend.domain.user.entity.RoleType;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static java.time.LocalDateTime.now;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseUserService {

    private final UserRepository userRepository;

    /**
     * 클라이언트로부터 전달받은 ID Token을 검증하고, 해당 사용자의 정보를 조회합니다.
     *
     * @param idToken 클라이언트가 전달한 Firebase ID Token
     * @return UserRecord 객체에 담긴 사용자 정보
     * @throws FirebaseAuthException 토큰 검증 또는 사용자 조회 중 오류 발생 시
     */
    public UserRecord getUserInfoFromIdToken(String idToken) throws FirebaseAuthException {
        // ID Token 검증: 토큰이 유효하지 않으면 예외 발생
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();
        // UID를 사용하여 사용자 정보를 조회"
        log.info("------------------", uid);
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        return userRecord;
    }

    @Transactional
    public ApiResponse<UserResponseDto> verifyIdTokenAndUpdateUser(String idToken, Provider provider) throws FirebaseAuthException {
        // 1) Firebase 토큰 검증
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();

        // 이메일, Provider 등 정보 파싱 (Firebase에서 Provider ID를 가져올 수 있음)
        String userName = decodedToken.getName();
        String displayName = (String) decodedToken.getClaims().get("name");
        // -> 주의: 소셜마다 "name"이 존재하지 않을 수도 있음

        // 2) DB에서 UID 존재 확인
        Optional<User> existingUser = userRepository.findById(uid);

        if (existingUser.isPresent()) {
            // 기존 사용자 업데이트
            User user = existingUser.get();
            // 변경사항 있으면 업데이트
            user.setUserLatestLogin(LocalDateTime.now()); // 최신 로그인 시간 갱신
            User nuser = userRepository.save(user);
            User newuser = userRepository.findById(nuser.getUserCode()).orElse(null);
            UserResponseDto userDto= fromEntity(newuser);

            return ApiResponse.success(SuccessCode.USER_LOGIN_SUCCESS, userDto);
        } else {
            // 신규 사용자 생성
            if (displayName == null) {
                displayName = "GUEST"; // 기본값
            }
            User newUser = User.builder()
                    .userCode(uid)
                    .userName((userName != null) ? userName : "GUEST") // 기본값 적용
                    .userBirth(null)
                    .userPhone(null)
                    .userGender(null)
                    .userLoginProvider(Provider.ANONYMOUS)
                    .userIsAlert(false)
                    .userLatestLogin(now())
                    .roleType(RoleType.USER) // 기본값 적용
                    .build();
            User user = userRepository.save(newUser);
            //createAt 보기
            User newuser = userRepository.findById(user.getUserCode()).orElse(null);
            UserResponseDto userDto= fromEntity(newuser);
            return ApiResponse.success(SuccessCode.USER_LOGIN_SUCCESS, userDto);
        }
    }

    // 🔹 Routine을 RoutineDTO로 변환하는 헬퍼 메서드
    private UserResponseDto fromEntity(User user) {
        return UserResponseDto.builder()
                .uid(user.getUserCode())
                .name(user.getUserName())
                .createdDate(user.getCreatedAt())
                .latestLogin(user.getUserLatestLogin())
                .level(user.getUserStoneLevel())
                .exp(user.getUserStoneExp())
                .sideObj(user.getUserSideObj())
                .topObj(user.getUserTopObj())
                .accountLinkType(user.getUserLoginProvider().getCode())
                .linkDate(user.getUserLinkDate())
                .gender(user.getUserGender())
                .birthDay(user.getUserBirth())
                .number(user.getUserPhone())
                .alram(user.getUserIsAlert())
                .subscriptionEndDate(user.getUserSubEnddate())
                .isFreeTrialUsed(user.getUserFreeSub())
                .status(user.getStatus())
                .build();
    }




}
