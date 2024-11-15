# loginJWT, 구글 로그인

## 목차

1. [주요 기능](#주요-기능)
   - JWT 인증
   - 역할 기반 접근 제어
   - 엑세스 토큰과 리프레쉬 토큰 관리
2. [토큰방식의 장점](#토큰방식의-장점)
3. [OAuth2 로그인](#oauth2-로그인)
4. [플로우 차트](#플로우-차트)
5. [기술 스택](#기술-스택)
6. [인증 시스템 흐름](#인증-시스템-흐름)
   - 로그인 시
   - 인증 흐름
     - 엑세스 토큰이 있을 경우
     - 엑세스 토큰이 없을 경우
   - 엑세스 토큰 만료 처리
     - 리프레쉬 토큰 유효성 검사
   - 권한 처리
   - 전체 흐름
7. [역할에 맞는 권한 부여](#역할에-맞는-권한-부여)
8. [OAuth2 로그인 흐름](#oauth2-로그인-흐름)
   - 구글 로그인 흐름
   - OAuth2 설정
   - 코드 예시
9. [구글 로그인 시 핵심 사항](#구글-로그인-시에-핵심-사항)
   - 구글 콘솔 리다이렉트 설정
   - `application.yml` 설정 예시

---

## 주요 기능
- **JWT 인증**: 구글 로그인을 통해 인증 후, JWT를 사용하여 인증을 처리합니다.
- **역할 기반 접근 제어**: 관리자(ROLE_ADMIN)와 일반 사용자(ROLE_USER)의 접근 권한을 구분하여 제어합니다.
- **엑세스 토큰과 리프레쉬 토큰 관리**: 클라이언트에서 JWT 토큰을 로컬 스토리지와 쿠키에 저장하여 인증을 처리합니다.

## 토큰방식의 장점
1. **서버가 상태를 저장하지 않음**: 세션 데이터를 저장하거나 조회할 필요가 없습니다.
2. **토큰에 사용자 정보 포함**: 토큰을 검증하기만 하면 사용자 정보를 확인할 수 있습니다.
3. **보안**: 토큰 유효성 검증, 변조된 토큰을 확인 가능하며, 만료 시간이 설정됩니다.

## OAuth2 로그인
- 외부 인증 제공자(예: Google)를 사용하여 로그인하는 방식입니다.
- 구글 로그인 시 사용자의 이메일 정보 등을 통해 JWT 토큰을 발급하고, 역할에 따라 접근 권한을 설정합니다.

## 플로우 차트
![메인](https://github.com/user-attachments/assets/a87a024d-c388-4b56-9192-346e75d44d8f)
![User Login (1)](https://github.com/user-attachments/assets/8fee510a-bd6e-47aa-a45d-44bab7bafca6)

## 기술 스택
<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white" alt="Spring">
<img src="https://img.shields.io/badge/Spring_Security-00796B?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security">
<img src="https://img.shields.io/badge/JWT-FFD700?style=for-the-badge&logo=JWT&logoColor=white" alt="JWT">

---

## 인증 시스템 흐름

### 1. 로그인 시
- **엑세스 토큰**: 로그인 성공 시 엑세스 토큰을  **로컬 스토리지(localStorage)** 에 저장합니다.
- **리프레쉬 토큰**: 리프레쉬 토큰은  **쿠키(cookie)** 에 저장합니다.

### 2. 인증 흐름

#### 엑세스 토큰이 있을 경우
- 프론트엔드에서 **엑세스 토큰** 이 로컬 스토리지에 존재하면 이를 사용하여 서버에 요청을 보냅니다.
- 서버에서 토큰을 확인하고 유효하면 요청을 처리하며, 그렇지 않으면 401 에러를 반환합니다.

#### 엑세스 토큰이 없을 경우
- 로컬 스토리지에서 엑세스 토큰이 없으면, 프론트엔드는 로그인 페이지로 **리다이렉트**됩니다.
- 로그인 페이지로 이동하여 재로그인 과정을 진행합니다.

### 3. 엑세스 토큰 만료 처리
- 엑세스 토큰이 만료되면, 서버에서 `TokenAuthenticationFilter`를 통해 401 상태 코드를 반환합니다.
- 프론트엔드는 401 에러를 받으면 **리프레쉬 토큰**을 사용하여 새 엑세스 토큰을 요청합니다.

#### 리프레쉬 토큰 유효성 검사
- 쿠키에 저장된 **리프레쉬 토큰**의 유효 날짜를 확인합니다.
- 리프레쉬 토큰이 유효하면 **새로운 엑세스 토큰과 리프레쉬 토큰**을 반환합니다.
- 리프레쉬 토큰이 유효하지 않으면 다시 로그인 페이지로 리다이렉트됩니다.

### 4. 권한 처리
- 사용자가 **권한이 없는 페이지**에 접근하면, `/access-denied` 페이지로 리다이렉트됩니다.

### 5. 전체 흐름

1. **로그인**:
   - 로그인 성공 시 엑세스 토큰은 로컬 스토리지에, 리프레쉬 토큰은 쿠키에 저장됩니다.
   
2. **엑세스 토큰 유효성 검사**:
   - 프론트엔드는 엑세스 토큰을 통해 서버에 요청을 보냅니다.
   - 엑세스 토큰이 만료되면, 401 에러를 받아 리프레쉬 토큰으로 새로운 엑세스 토큰을 요청합니다.
   
3. **리프레쉬 토큰 유효성 검사**:
   - 리프레쉬 토큰이 유효하면, 새 엑세스 토큰과 리프레쉬 토큰을 반환합니다.
   
4. **권한 부족 시 처리**:
   - 권한이 없는 페이지에 접근하면, 사용자는 `/access-denied` 페이지로 이동합니다.

---

## 역할에 맞는 권한 부여
- `ROLE_USER`와 `ROLE_ADMIN` 역할에 따라 다른 콘텐츠를 보여줍니다.

### ROLE_USER일 시
![ROLE_USER](https://github.com/user-attachments/assets/41abd620-428b-479c-9e04-73037cdf0699)

### ROLE_ADMIN일 시
![ROLE_ADMIN](https://github.com/user-attachments/assets/18f73320-78dd-425e-89fa-05b3d0bc35cd)

---

## OAuth2 로그인 흐름
OAuth2 로그인을 통해 사용자가 Google과 같은 외부 인증 제공자(Google)를 이용해 로그인할 수 있도록 설정합니다.

### 구글 로그인 흐름
1. **사용자가 Google 로그인 페이지에서 로그인**  
   - 사용자가 애플리케이션에서 Google 로그인 버튼을 클릭하면, Google 로그인 페이지로 리디렉션됩니다.

2. **Google에서 인증 후 리디렉션**  
   - 사용자가 Google에서 인증을 완료하면, Google은 **인가 코드**를 애플리케이션으로 리디렉션합니다.
   - 이때 **인가 코드**는 사용자가 인증을 허가했다는 신호로, Google은 해당 코드를 애플리케이션에게 전달합니다.

3. **OAuth2에서 구글 인가 코드를 받아 엑세스 토큰을 자동으로 요청**  
   - 애플리케이션은 Spring Security의 `oauth2Login` 설정에 의해 자동으로 **엑세스 토큰**을 요청합니다.
   - 구글은 애플리케이션에게 **엑세스 토큰**을 반환합니다.

4. **`principalOauth2UserService`에서 엑세스 토큰을 사용하여 Google 사용자 데이터 가져오기**  
   - 받은 엑세스 토큰을 이용해 애플리케이션은 Google의 **사용자 정보 API** (`https://www.googleapis.com/oauth2/v3/userinfo`)를 호출하여 사용자의 정보를 가져옵니다. 
   - 이 사용자 정보를 기반으로 `OAuth2User` 객체를 생성합니다.

5. **`OAuth2User` 객체를 기반으로 엑세스 토큰과 리프레시 토큰 생성**  
   - `OAuth2User` 객체에 포함된 사용자 정보를 사용하여 서버에서 **엑세스 토큰**과 **리프레시 토큰**을 생성합니다. 
   - **엑세스 토큰**은 로컬 스토리지에, **리프레시 토큰**은 쿠키에 저장됩니다.

6. **엑세스 토큰을 클라이언트로 전달**  
   - 서버는 생성된 **엑세스 토큰**을 HTTP 응답 헤더에 추가하여 클라이언트에 전달합니다. 
   - 클라이언트는 이 엑세스 토큰을 **로컬 스토리지**에 저장하고, 이후 **인증된 요청**을 할 때 사용합니다.

### 코드 예시

```java
.oauth2Login(oauth2 -> oauth2
    // OAuth2 인증을 시작할 로그인 페이지 URL을 설정합니다.
    .loginPage("/member/login")
    
    // Google 로그인 후, 엑세스 토큰을 이용해 구글 유저 데이터를 받아옵니다.
    .userInfoEndpoint(u -> u
        .userService(principalOauth2UserService) // 사용자 데이터를 처리할 Service 설정
    )
    
    // 로그인 성공 시 실행될 핸들러를 설정합니다.
    // OAuth2 인증 성공 후, principalOauth2UserService에서 받은 OAuth2User를 사용하여
    // email, 엑세스 토큰 등을 처리합니다.
    .successHandler(authenticationSuccessHandler)
);
```

### 구글 로그인 시에 핵심 사항
- 구글 콘솔 리다이렉트에 http://localhost:8080/login/oauth2/code/google 추가  
-- **OAuth2 인증을 위한 설정**을 `application.yml` 파일에 추가하는 방법
- 각 설정 항목에 대한 간단한 설명과 사용 방법
```yaml
 security:
    oauth2:
      client:
        registration:
          google:
            client-id: 클라이언트 ID
            client-secret: 클라이언트 보안 비밀번호
            scope: profile, email
            authorization-grant-type: authorization_code
            client-name: Google
        provider:
          google:
            authorization-uri: https://accounts.google.com/o/oauth2/auth
            token-uri: https://oauth2.googleapis.com/token
            user-info-uri: https://www.googleapis.com/oauth2/v3/userinfo
            user-name-attribute: id
```

 





