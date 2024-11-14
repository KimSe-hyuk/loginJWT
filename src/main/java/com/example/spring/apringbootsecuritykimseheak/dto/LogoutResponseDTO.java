package com.example.spring.apringbootsecuritykimseheak.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LogoutResponseDTO {
    private String url;
    private String message;
}
