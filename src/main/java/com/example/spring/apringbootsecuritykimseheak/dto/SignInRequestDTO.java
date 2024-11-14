package com.example.spring.apringbootsecuritykimseheak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequestDTO {
    private String userId;
    private String password;
}
