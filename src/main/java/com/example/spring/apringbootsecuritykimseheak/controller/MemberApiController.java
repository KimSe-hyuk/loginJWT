package com.example.spring.apringbootsecuritykimseheak.controller;

import com.example.spring.apringbootsecuritykimseheak.dto.*;
import com.example.spring.apringbootsecuritykimseheak.model.Member;
import com.example.spring.apringbootsecuritykimseheak.service.MemberService;
import com.example.spring.apringbootsecuritykimseheak.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/api")
public class MemberApiController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> signIn(
            @RequestBody SignInRequestDTO signInRequestDTO,
            HttpServletResponse response
    ) {
        SignInResponseDTO signInResponseDTO = memberService.signIn(signInRequestDTO.getUserId(), signInRequestDTO.getPassword());

        CookieUtil.addCookie(response, "refreshToken", signInResponseDTO.getRefreshToken(), 7 * 24 * 60 * 60);
        signInResponseDTO.setRefreshToken(null);

        return ResponseEntity.ok(signInResponseDTO);
    }
    @PostMapping("/join")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO){
        memberService.signUp(signUpRequestDTO.toMember(bCryptPasswordEncoder));
        return ResponseEntity.ok(
                SignUpResponseDTO.builder()
                    .url("/member/login")
                    .message("회원가입 성공")
                    .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDTO> logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response,"refreshToken");
        return ResponseEntity.ok(
                LogoutResponseDTO.builder()
                        .url("/member/login")
                        .message("로그아웃 성공")
                        .build()
        );
    }

    @GetMapping("/user/info")
    public ResponseEntity<UserInfoResponseDTO> getUserInfo(HttpServletRequest request) {

        Member member = (Member) request.getAttribute("member");
        return ResponseEntity.ok(
                UserInfoResponseDTO.builder()
                        .id(member.getId())
                        .userId(member.getUserId())
                        .userName(member.getUserName())
                        .role(member.getRole())
                        .build()
        );
    }
}
