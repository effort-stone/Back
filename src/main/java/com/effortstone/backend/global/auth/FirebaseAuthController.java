package com.effortstone.backend.global.auth;


import com.effortstone.backend.domain.user.entity.Provider;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class FirebaseAuthController {


    private final KakaoService kakaoService;
    private final FirebaseUserService firebaseUserService;


    @GetMapping("/kakao/login")
    public ApiResponse<Object> kakaoLogin(@RequestParam("code") String code) throws FirebaseAuthException {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Authorization code is missing.");
        }
        String firebaseCustomToken = kakaoService.loginWithKakao(code);
        // 2️⃣ Custom Token을 ID Token으로 변환
        String idToken = kakaoService.exchangeCustomTokenForIdToken(firebaseCustomToken);
        log.info("firebaseCustomToken: {}", firebaseCustomToken);
        System.out.println("ID Token: " + idToken);

        // 3️⃣ ID Token 검증 및 사용자 정보 조회
        return firebaseUserService.verifyIdTokenAndUpdateUser(idToken, Provider.NUMBER);;
    }

    /**
     * 일반 로그인 및 앱 최초 로딩 시 사용자 정보를 제공하는 엔드포인트
     * <p>
     * 클라이언트(예: Flutter 앱)에서는 앱 실행 시 Firebase에서 익명 로그인 등으로 발급받은 ID Token을 헤더에 담아 전송합니다.
     * 이 엔드포인트에서는 전달받은 ID Token을 검증하여 해당 사용자의 정보를 DB에서 조회(또는 신규 생성)하고 반환합니다.
     * <p>
     * 로그인 타입은 "ANONYMOUS" 등으로 구분할 수 있으며, 소셜 계정 연결 시 linkWithCredential을 통해 기존 정보와 통합할 수 있습니다.
     */
    @GetMapping("/login")
    public ResponseEntity<Object> generalLogin(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("Authorization token is missing.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // 헤더에서 Bearer 토큰 추출
        String idToken = authorizationHeader.replace("Bearer ", "").trim();
        try {
            // 로그인 타입을 "ANONYMOUS" 등으로 구분 (추후 소셜 로그인과의 구분이 필요하면 인자값을 조정)
            ApiResponse<Object> user = firebaseUserService.verifyIdTokenAndUpdateUser(idToken, Provider.ANONYMOUS);
            log.info("User logged in: {}", user);
            return ResponseEntity.ok(user);
        } catch (FirebaseAuthException e) {
            log.error("Firebase authentication failed", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            log.error("An error occurred while processing the login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
