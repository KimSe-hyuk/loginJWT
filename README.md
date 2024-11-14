# loginJWT,구글 로그인
토큰방식의 장점
1.서버가 상태를 저장하지 않음 - 세션 데이터를 저장하거나 조회할 필요 없음
2. 토큰에 사용자 정보 포함 :토큰을 검증하기만 하면 됨
3. 보안 : 토큰 유효성 검증: 변조토큰확인가으능, 만료 시간

![메인](https://github.com/user-attachments/assets/a87a024d-c388-4b56-9192-346e75d44d8f)

로그인시에 
엑세스 토큰은 localstolage에 담고
리프레쉬 토큰은 쿠키에 담는다.

접속시에 시큐리티에서
tokenAuthenticationFilter로 
토큰이 있고 기한이 남으면 
로컬 스토리지에 엑세스 토큰 쿠키에 리프레시토큰을 담는다.

토큰이 있고 기한이 끝나면 
리프레쉬토큰이 끝나지 않았다면 엑세스 토큰과 리프레쉬 토큰을 재발급 받는다.

토큰이 인증이 안되면 로그인으로 다른페이지는 /access-denied 로 이동 시킨다. 

구글 로그인시에는 oauth2Login로 처리해서 구글 로그인 성공시 successHandler에서 email 엑세스 토큰과 리프레쉬 토큰을 만든다. 
리프레쉬시에는 Eamil정보가 있으면 email엑세스 토큰과 리프레쉬 토큰을 만든다.

@PreAuthorize("hasRole('ROLE_USER')")로 권한이 user일떄만 보이고 admin이면 보이지 않는다.
<img width="700" alt="image" src="https://github.com/user-attachments/assets/41abd620-428b-479c-9e04-73037cdf0699">
<img width="704" alt="image" src="https://github.com/user-attachments/assets/18f73320-78dd-425e-89fa-05b3d0bc35cd">


