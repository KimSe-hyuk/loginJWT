package com.example.spring.apringbootsecuritykimseheak.service;

import com.example.spring.apringbootsecuritykimseheak.config.jwt.TokenProvider;
import com.example.spring.apringbootsecuritykimseheak.dto.Email;
import com.example.spring.apringbootsecuritykimseheak.dto.RefreshTokenResponseDTO;
import com.example.spring.apringbootsecuritykimseheak.model.Member;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;

    public RefreshTokenResponseDTO refreshToken(Cookie[] cookies) {
        String refreshToken = getRefreshTokenFromCookies(cookies);
        String newAccessToken="";
        String newRefreshToken="";
        if(refreshToken != null && tokenProvider.validateToken(refreshToken) == 1  ) {
            if(tokenProvider.checkEmailOrToken(refreshToken)){
                Email email =tokenProvider.getTokenEmail(refreshToken);
                 newAccessToken = tokenProvider.generateEmailToken(email, Duration.ofHours(2));
                 newRefreshToken = tokenProvider.generateEmailToken(email, Duration.ofDays(2));
            }else{
                Member member = tokenProvider.getTokenDetails(refreshToken);
                // Access Token
                newAccessToken = tokenProvider.generateToken(member, Duration.ofHours(2));

                // Refresh Token
                newRefreshToken = tokenProvider.generateToken(member, Duration.ofDays(2));

            }

            return RefreshTokenResponseDTO.builder()
                    .validated(true)
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        }
        return RefreshTokenResponseDTO.builder()
                .validated(false)
                .build();
    }
    private String getRefreshTokenFromCookies(Cookie[] cookies) {
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("refreshToken")){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
