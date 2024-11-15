package com.example.spring.apringbootsecuritykimseheak.config.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2AccessToken accessToken = userRequest.getAccessToken();
//        String userInfoUri = "https://www.googleapis.com/oauth2/v3/userinfo"; // 구글 사용자 정보 API
//
//        // 구글 API로 사용자 정보 요청
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(accessToken.getTokenValue()); // Bearer Token 설정
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        ResponseEntity<Map> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, entity, Map.class);
//
//        // 구글 사용자 정보 추출
//        Map<String, Object> userAttributes = response.getBody();
//
//        // OAuth2User 객체를 반환
//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), // 권한 설정
//                userAttributes, // 사용자 정보 (구글에서 가져온 정보)
//                "name" // 인증된 사용자 ID 필드 (예: "name", "email")
//        );
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 사용자 정보를 가져와서 필요한 추가 작업 수행 가능
        // 예를 들어, 사용자의 정보에서 특정 값을 추출하거나, 커스터마이징
        System.out.println("OAuth2 User Info: " + oAuth2User.getAttributes());

        // OAuth2User를 반환하며 권한 추가
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), // 권한 설정
                oAuth2User.getAttributes(),  // 사용자 정보
                "name" // 인증된 사용자 ID 필드 (예: "name", "email")
        );
    }

}
