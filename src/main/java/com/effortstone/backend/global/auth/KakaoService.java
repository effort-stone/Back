package com.effortstone.backend.global.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoService {

    @Value("${kakao.client_id}")
    private String kakaoClientId;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    @Value(value = "${firebasekey.api}")
    private String firebaseApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String loginWithKakao(String code) throws FirebaseAuthException {
        log.info("Received Kakao auth code: {}", code);

        // 1️⃣ 카카오 사용자 정보 가져오기
        Map<String, Object> userInfo = processKakaoCallback(code);
        // 2️⃣ Firebase에서 사용자 확인 후 생성 or 업데이트
        String firebaseToken = createFirebaseCustomToken(userInfo);
        // 3️⃣ Firebase에서 사용자 확인 후(커스텀토큰) 유저 정보 전송
        //String idToken = exchangeCustomTokenForIdToken(firebaseToken);

        return firebaseToken;
    }

    public Map<String, Object> processKakaoCallback(String code) {
        log.info("Received Kakao auth code: {}", code);
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Authorization code is missing.");
        }

        // 1) 카카오 토큰 요청
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        MultiValueMap<String, String> tokenParams = new LinkedMultiValueMap<>();
        tokenParams.add("grant_type", "authorization_code");
        tokenParams.add("client_id", kakaoClientId);
        tokenParams.add("redirect_uri", kakaoRedirectUri);
        tokenParams.add("code", code);

        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(tokenParams, tokenHeaders);
        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenUrl, tokenRequest, Map.class);

        if (!tokenResponse.getStatusCode().is2xxSuccessful() ||
                tokenResponse.getBody() == null ||
                !tokenResponse.getBody().containsKey("access_token")) {
            log.error("Failed to obtain Kakao access token. Response: {}", tokenResponse);
            throw new IllegalStateException("Failed to obtain Kakao access token.");
        }

        String accessToken = (String) tokenResponse.getBody().get("access_token");

        // 2) 사용자 정보 요청
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity<Void> userInfoRequest = new HttpEntity<>(userInfoHeaders);
        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userInfoRequest, Map.class);

        if (!userInfoResponse.getStatusCode().is2xxSuccessful() || userInfoResponse.getBody() == null) {
            log.error("Failed to fetch Kakao user information. Response: {}", userInfoResponse);
            throw new IllegalStateException("Failed to fetch Kakao user information.");
        }

        // 3) 사용자 정보 파싱
        Map<String, Object> userInfo = userInfoResponse.getBody();
        Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
        Map<String, Object> profile = kakaoAccount != null ? (Map<String, Object>) kakaoAccount.get("profile") : null;

        String nickname = profile != null ? (String) profile.get("nickname") : "No nickname";

        String userId = userInfo.get("id").toString();
        String profileImage = (profile != null && profile.containsKey("profile_image_url"))
                ? (String) profile.get("profile_image_url")
                : "https://example.com/default-profile.png";

        // 4) 사용자 정보 저장용 데이터 구성
        Map<String, Object> userInfoDetail = new HashMap<>();
        userInfoDetail.put("id", userId);
        userInfoDetail.put("nickname", nickname);
        userInfoDetail.put("profileImage", profileImage);

        return userInfoDetail;
    }

    /**
     * 카카오 사용자 정보를 바탕으로 Firebase Custom Token을 생성합니다.
     *
     * @param userInfo 카카오 사용자 정보
     * @return Firebase Custom Token
     * @throws FirebaseAuthException Firebase 인증 오류 발생 시
     */
    public String createFirebaseCustomToken(Map<String, Object> userInfo) throws FirebaseAuthException {
        String uid = userInfo.get("id").toString();
        String displayName = userInfo.get("nickname") != null ? userInfo.get("nickname").toString() : "User";

        UserRecord userRecord;
        try {
            // 기존 사용자가 있으면 업데이트
            UserRecord.UpdateRequest updateRequest = new UserRecord.UpdateRequest(uid);

            if (displayName != null) {
                updateRequest.setDisplayName(displayName);
            }
            userRecord = FirebaseAuth.getInstance().updateUser(updateRequest);
            log.info("Updated existing Firebase user: {}", uid);
        } catch (FirebaseAuthException e) {
            // 업데이트 실패 시 신규 생성
            UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                    .setUid(uid)
                    .setDisplayName(displayName);

            userRecord = FirebaseAuth.getInstance().createUser(createRequest);
            log.info("Created new Firebase user: {}", uid);
        }



        return FirebaseAuth.getInstance().createCustomToken(userRecord.getUid());
    }

    // 커스텀 토큰 인증하고 idToken을 발급하는 로직
    // REST API 요청
    public String exchangeCustomTokenForIdToken(String customToken) {
        String firebaseAuthUrl = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithCustomToken?key=" + firebaseApiKey;

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

    // 사용자가 보낸 ID Token 검증
    public String getUserInfoFromIdToken(String idToken) throws FirebaseAuthException {
        // ID Token 검증
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        return decodedToken.getUid();
    }




};
