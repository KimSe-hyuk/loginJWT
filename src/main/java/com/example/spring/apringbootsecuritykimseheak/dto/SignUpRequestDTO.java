package com.example.spring.apringbootsecuritykimseheak.dto;

import com.example.spring.apringbootsecuritykimseheak.enums.Role;
import com.example.spring.apringbootsecuritykimseheak.model.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
public class SignUpRequestDTO {
    private String userId;
    private String userName;
    private String password;
    private Role role;

    public Member toMember(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Member.builder()
                .userId(userId)
                .userName(userName)
                .password(bCryptPasswordEncoder.encode(password))
                .role(role)
                .build();
    }
}
