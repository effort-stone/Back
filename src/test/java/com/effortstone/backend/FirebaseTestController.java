package com.effortstone.backend;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class FirebaseTestController {


    //토큰생성
    public String createCustomToken(String uid) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().createCustomToken(uid);
    }

    // REST API 요청
    public String exchangeCustomTokenForIdToken(String customToken) {
        String firebaseAuthUrl = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithCustomToken?key=" + "AIzaSyB5LnVZQst-VDiWz3vk0DYVv3KGCUvf8LI";

        RestTemplate restTemplate = new RestTemplate();

        // 요청 데이터 생성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("token", customToken);
        requestBody.put("returnSecureToken", true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(firebaseAuthUrl, HttpMethod.POST, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return (String) response.getBody().get("idToken"); // 🔹 여기서 ID Token을 가져옴
        }

        throw new RuntimeException("Failed to exchange Custom Token for ID Token");
    }

    public UserRecord getUserInfoFromIdToken(String idToken) throws FirebaseAuthException {
        // ID Token 검증
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();

        // Firebase 사용자 정보 조회
        return FirebaseAuth.getInstance().getUser(uid);
    }


    public void testFirebaseAuth() throws FirebaseAuthException {
        // 1️⃣ Custom Token 생성
        String uid = "test_user_123"; // 기존 Firebase 사용자의 UID 또는 신규 사용자 ID
        String customToken = createCustomToken(uid);
        System.out.println("Custom Token: " + customToken);

        // 2️⃣ Custom Token을 ID Token으로 변환
        String idToken = exchangeCustomTokenForIdToken(customToken);
        System.out.println("ID Token: " + idToken);

        // 3️⃣ ID Token 검증 및 사용자 정보 조회
        UserRecord userRecord = getUserInfoFromIdToken(idToken);
        System.out.println("User Info: " + userRecord.getEmail() + ", UID: " + userRecord.getUid());
    }

    /**
     * Firebase 인증 테스트를 실행하는 엔드포인트
     */
    @GetMapping("/test-auth")
    public String testFirebaseAuth2() {
        try {
            testFirebaseAuth();
            return "Firebase Auth 테스트 완료!";
        } catch (FirebaseAuthException e) {
            return "Firebase Auth 실패: " + e.getMessage();
        }
    }
}
