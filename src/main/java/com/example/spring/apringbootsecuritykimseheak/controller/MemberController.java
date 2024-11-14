package com.example.spring.apringbootsecuritykimseheak.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public String signIn(){
        return "/login";
    }
    @GetMapping("/join")
    public String signUp(){
        return "/join";
    }

}
