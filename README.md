# loginJWT,구글 로그인
토큰방식의 장점
1.서버가 상태를 저장하지 않음 - 세션 데이터를 저장하거나 조회할 필요 없음
2. 토큰에 사용자 정보 포함 :토큰을 검증하기만 하면 됨
3. 보안 : 토큰 유효성 검증: 변조토큰확인가으능, 만료 시간

![메인](https://github.com/user-attachments/assets/a87a024d-c388-4b56-9192-346e75d44d8f)
 ![User Login (1)](https://github.com/user-attachments/assets/8fee510a-bd6e-47aa-a45d-44bab7bafca6)

접속시에

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
이것 처리

로그인시에 엑세스 토큰 로컬 스토리지에 저장 리프레쉬 토큰 쿠키에 저장
프론트에서 엑세스 토큰이 있으면 접속
엑세스 토큰 없으면 login으로 이동 
엑세스 토큰 날짜 TokenAuthenticationFilter에서 다되면 401 리턴  프론트에서 토큰 요청 
리프레쉬 토큰에 값이 쿠키에서 날짜가 남았으면 엑세스 토큰과 리프레쉬 토큰 리턴

다른페이지는 /access-denied 로 이동 시킨다. 


구글 로그인시에는 .oauth2Login(oauth2 -> oauth2
                        .loginPage("/member/login")
                        .userInfoEndpoint(u -> u
                                .userService(principalOauth2UserService))
                        .successHandler(authenticationSuccessHandler)
                );
 로 처리해서 principalOauth2UserService에서 구글 유저 데이터를 받아오고
successHandler에서 email 엑세스 토큰과 리프레쉬 토큰을 만든다. 
리프레쉬시에는 Eamil정보가 있으면 email엑세스 토큰과 리프레쉬 토큰을 만든다.

권한이 user일떄만 보이고 admin이면 보이지 않는다.
<img width="700" alt="image" src="https://github.com/user-attachments/assets/41abd620-428b-479c-9e04-73037cdf0699">
<img width="704" alt="image" src="https://github.com/user-attachments/assets/18f73320-78dd-425e-89fa-05b3d0bc35cd">


