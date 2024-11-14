package com.example.spring.apringbootsecuritykimseheak.dto;

import com.example.spring.apringbootsecuritykimseheak.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Email {
    private String id;
    private String userName;
    private String userId;
    private Role role;
    private String emailToken;
}
