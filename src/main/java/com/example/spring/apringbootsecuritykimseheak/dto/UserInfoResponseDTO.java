package com.example.spring.apringbootsecuritykimseheak.dto;

import com.example.spring.apringbootsecuritykimseheak.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDTO {
    private long id;
    private String userId;
    private String userName;
    private Role role;
}
