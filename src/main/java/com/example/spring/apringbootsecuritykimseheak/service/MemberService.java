package com.example.spring.apringbootsecuritykimseheak.service;

import com.example.spring.apringbootsecuritykimseheak.config.jwt.TokenProvider;
import com.example.spring.apringbootsecuritykimseheak.config.security.CustomUserDetails;
import com.example.spring.apringbootsecuritykimseheak.dto.SignInResponseDTO;
import com.example.spring.apringbootsecuritykimseheak.mapper.MemberMapper;
import com.example.spring.apringbootsecuritykimseheak.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final AuthenticationManager authenticationManager;
    private final MemberMapper memberMapper;
    private final TokenProvider tokenProvider;
    private final OAuth2AuthorizedClientService authorizedClientService;

    // 회원가입
    public void signUp(Member member) {
        memberMapper.signUp(member);
    }


    // 기존 로그인 처리 메서드
    public SignInResponseDTO signIn(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Member member = ((CustomUserDetails) authentication.getPrincipal()).getMember();

        // Access Token
        String accessToken = tokenProvider.generateToken(member, Duration.ofHours(2));

        // Refresh Token
        String refreshToken = tokenProvider.generateToken(member, Duration.ofDays(2));

        return SignInResponseDTO.builder()
                .isLoggedIn(true)
                .message("로그인 성공")
                .url("/")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(member.getUserId())
                .userName(member.getUserName())
                .build();
    }
}
