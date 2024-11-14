package com.example.spring.apringbootsecuritykimseheak.config.jwt;

import com.example.spring.apringbootsecuritykimseheak.dto.Email;
import com.example.spring.apringbootsecuritykimseheak.enums.Role;
import com.example.spring.apringbootsecuritykimseheak.model.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.time.Duration;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtProperties jwtProperties;

    public String generateToken(Member member, Duration expire) {
        Date now = new Date();
        return makeToken(member, new Date(now.getTime() + expire.toMillis()));
    }

    private String makeToken(Member member, Date expire) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expire)
                .setSubject(member.getUserId())
                .claim("id", member.getId())
                .claim("userName", member.getUserName())
                .claim("role", member.getRole())
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();

    }
    public String generateEmailToken(Email email, Duration expire) {
        Date now = new Date();
        return makeEmailToken(email, new Date(now.getTime() + expire.toMillis()));
    }

    private String makeEmailToken(Email email, Date expire) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expire)
                .setSubject(email.getUserId())
                .claim("id", email.getId())
                .claim("userName", email.getUserName())
                .claim("role", email.getRole())
                .claim("emailToken",email.getEmailToken())
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();

    }
    private SecretKey getSecretKey() {
        byte[] decode = Base64.getDecoder().decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(decode);
    }

    public int validateToken(String token) {
        // 1

        // 2

        // 3
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.info("Token validated");
            return 1;
        }catch (ExpiredJwtException e){
            // 토큰이 만료된 경우
            log.info("token is expired");
            return 2;
        }catch (Exception e){
            // 복호화 과정에서 에러 발생
            log.info("token is not valid");
            return 3;
        }
    }
    //true email 토큰 리턴
    public boolean checkEmailOrToken(String token) {
        Claims claims = getClaims(token);
        String str = claims.get("emailToken", String.class);
        return str != null && !str.isEmpty();
    }
    public Member getTokenDetails(String token) {
        Claims claims = getClaims(token);
        return Member.builder()
                .id(claims.get("id", Long.class))
                .userId(claims.getSubject())
                .userName(claims.get("userName", String.class))
                .role(Role.valueOf(claims.get("role",String.class)))
                .build();
    }
    public Email getTokenEmailDetails(String token) {
        Claims claims = getClaims(token);
        System.out.println("email"+claims);
        System.out.println("role"+Role.valueOf(claims.get("role",String.class)));
        return Email.builder()
                .id(claims.get("id", String.class))
                .userId(claims.getSubject())
                .userName(claims.get("userName", String.class))
                .role(Role.valueOf(claims.get("role",String.class)))
                .build();
    }
    public Email getTokenEmail(String token) {
        Claims claims = getClaims(token);
        System.out.println(claims);
        System.out.println(claims.get("role",String.class));
        return Email.builder()
                .id(claims.get("id", String.class))
                .userName(claims.get("userName", String.class))
                .userId(claims.get("userId", String.class))
                .emailToken(claims.get("emailToken",String.class))
                .role(Role.valueOf(claims.get("role",String.class)))
                .build();
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token) {

        Claims claims = getClaims(token);

        // Clamis에서 역할을 추출하고 , GrantedAuthority로 변환
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(String.valueOf(claims.get("role")))
        );

        User user = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(user,token,authorities);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey()) // Assuming getSecreteKey() provides the secret key
                .build()
                .parseClaimsJws(token) // Parse the JWT token
                .getBody();
    }


}

