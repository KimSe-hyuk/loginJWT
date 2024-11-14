package com.example.spring.apringbootsecuritykimseheak.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignUpResponseDTO {
    private String url;
    private String message;
}
