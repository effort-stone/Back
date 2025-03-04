package com.effortstone.backend.global.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FirebaseService {
    public String createFirebaseCustomToken(Map<String, Object> userInfo) throws Exception {

        UserRecord userRecord;
        String uid = userInfo.get("id").toString();
        String displayName = userInfo.get("nickname") != null ? userInfo.get("nickname").toString() : "사용자";

        // 1. 기존 Firebase 유저 정보 업데이트 또는 신규 유저 생성
        try {
            UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid);
            if (displayName != null) request.setDisplayName(displayName);

            userRecord = FirebaseAuth.getInstance().updateUser(request);
        } catch (FirebaseAuthException e) {
            // 기존 유저가 없으면 새로 생성
            UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest().setUid(uid);
            if (displayName != null) createRequest.setDisplayName(displayName);

            userRecord = FirebaseAuth.getInstance().createUser(createRequest);
        }

        // 2. Firebase Custom Token 생성 후 반환
        return FirebaseAuth.getInstance().createCustomToken(userRecord.getUid());
    }
}
