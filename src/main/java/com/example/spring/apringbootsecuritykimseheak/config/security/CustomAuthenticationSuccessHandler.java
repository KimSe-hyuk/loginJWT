package com.example.spring.apringbootsecuritykimseheak.config.security;

import com.example.spring.apringbootsecuritykimseheak.config.jwt.TokenProvider;
import com.example.spring.apringbootsecuritykimseheak.dto.Email;
import com.example.spring.apringbootsecuritykimseheak.enums.Role;
import com.example.spring.apringbootsecuritykimseheak.model.Member;
import com.example.spring.apringbootsecuritykimseheak.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 로그인 후 OAuth2User 정보 가져오기
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String username = oAuth2User.getName();
        String id = (String) oAuth2User.getAttributes().get("sub");// 구글에서 받은 "sub" 필드 (사용자 ID)
        String email = (String) oAuth2User.getAttributes().get("email");
        Role role = Role.ROLE_USER;
        String emailToken = oAuth2User.getAttributes().toString();;
        // Email 객체 생성
        Email emails = Email.builder().emailToken(emailToken).id(id).userId(email).userName(username).role(role).build();
         //JWT 토큰 생성
        String accessToken = tokenProvider.generateEmailToken(emails, Duration.ofHours(2));
        System.out.println("aceestoken"+accessToken);
        String refreshToken = tokenProvider.generateEmailToken(emails, Duration.ofDays(2));

         //쿠키에 Refresh Token 저장 (보안 설정 적용)
        CookieUtil.addCookie(response, "refreshToken", refreshToken, 7 * 24 * 60 * 60);

         //헤더에 accessToken 추가 (선택 사항)
        response.setHeader("Authorization", "Bearer " + accessToken);

        // 리다이렉트
        response.sendRedirect("/member/login?accessToken=" + accessToken);
    }
}
