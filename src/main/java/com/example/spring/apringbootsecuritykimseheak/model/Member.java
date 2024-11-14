package com.example.spring.apringbootsecuritykimseheak.model;

import com.example.spring.apringbootsecuritykimseheak.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Member {
    private long id;
    private String userName;
    private String userId;
    private String password;
    private Role role;
}
