package com.effortstone.backend.global.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;


@Component
public class FirebaseAuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", ""); // "Bearer " 이후의 토큰 값만 추출

            // Firebase Token을 검증하고 UID를 추출
            FirebaseToken decodedToken = null;
            try {
                decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            } catch (FirebaseAuthException e) {
                throw new RuntimeException(e);
            }
            String uid = decodedToken.getUid();

            // UID를 이용하여 사용자 인증 객체 생성
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(uid, null, Collections.emptyList());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));

            // Spring Security의 SecurityContext에 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        // 다음 필터로 요청을 넘김
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
