package com.effortstone.backend.global.auth;

import com.effortstone.backend.domain.user.entity.RoleType;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        log.info("------------------",uid);
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        return userRecord;
    }

    public User verifyIdTokenAndUpdateUser(String idToken, String Provider) throws FirebaseAuthException {
        // 1) Firebase 토큰 검증
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();

        // 이메일, Provider 등 정보 파싱 (Firebase에서 Provider ID를 가져올 수 있음)
        String userEmail = decodedToken.getEmail();
        String userName = decodedToken.getName();
        String provider = Provider; // 예: "google.com", "apple.com", "kakao.com"
        String displayName = (String) decodedToken.getClaims().get("name");
        // -> 주의: 소셜마다 "name"이 존재하지 않을 수도 있음

        // 2) DB에서 UID 존재 확인
        Optional<User> existingUser = userRepository.findById(uid);

        if (existingUser.isPresent()) {
            // 기존 사용자 업데이트
            User user = existingUser.get();
            // 변경사항 있으면 업데이트
            // 빌더를 이용하여 새로운 객체 생성, 받은 값만 적용하고 null이면 기존 값 유지


            User updatedUser = User.builder()
                    .userCode(user.getUserCode()) // ID는 변경하지 않음
                    .userName((userName != null) ? userName : user.getUserName())
                    .userEmail((userEmail != null) ? userEmail : user.getUserEmail())
                    .userLoginProvider((provider != null) ? provider : user.getUserLoginProvider())
                    .roleType(user.getRoleType()) // 기본값 유지
                    .build();
            updatedUser = userRepository.save(updatedUser);

            return updatedUser;
        } else {
            // 신규 사용자 생성
            if (displayName == null) {
                displayName = "User"; // 기본값
            }
            User newUser = User.builder()
                    .userCode(uid)
                    .userName((userName != null) ? userName : "User") // 기본값 적용
                    .userBirth(null)
                    .userPhone(null)
                    .userGender(null)
                    .userEmail(userEmail)
                    .userLoginProvider("COMMON")
                    .userIsSub(false)
                    .userIsAlert(false)
                    .roleType(RoleType.USER) // 기본값 적용
                    .build();
            newUser = userRepository.save(newUser);
            return newUser;
        }
    }




}
