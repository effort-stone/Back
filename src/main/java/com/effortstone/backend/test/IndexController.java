package com.effortstone.backend.test;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Value("${kakao.client_id}")
    private String kakaoClientId;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    @GetMapping("/")
    public String index() {
        return "index"; // index.html (Thymeleaf)
    }

    /**
     * 카카오 로그인 버튼을 누르면 이 메서드를 통해
     * 카카오 인증 페이지로 리다이렉트
     */
    @GetMapping("/login/kakao")
    public String kakaoLoginRedirect() {
        // 카카오 OAuth URL 생성
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize?"
                + "response_type=code"
                + "&client_id=" + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectUri;

        // 카카오로 리다이렉트
        return "redirect:" + kakaoAuthUrl;
    }



}
