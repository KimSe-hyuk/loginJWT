package com.example.spring.apringbootsecuritykimseheak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/terms")
public class TermsOfServiceController {
    @GetMapping("privacy-policy")
    public String privacyPolicy() {
        return "privacy-policy";
    }
    @GetMapping("terms-of-service")
    public String termsOfService() {
        return "terms-of-service";
    }
}
