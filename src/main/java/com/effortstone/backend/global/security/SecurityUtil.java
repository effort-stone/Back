package com.effortstone.backend.global.security;

import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    private SecurityUtil() { } // 인스턴스 생성 방지

    private static UserRepository userRepository;

    public SecurityUtil(UserRepository userRepository) {
        SecurityUtil.userRepository = userRepository;
    }

    public static String getCurrentUserCode() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }

        // 직접 Principal 가져오기
        Object principal = authentication.getPrincipal();

        // Principal을 String으로 변환
        String userCode = principal.toString();  // UID 값 가져오기
        System.out.println("🔹 Firebase userCode: " + userCode);

        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return user.getUserCode(); // userCode 반환
    }

}