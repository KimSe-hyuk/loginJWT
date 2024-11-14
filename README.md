# loginJWT
토큰방식의 장점 세션방시과 다르게 서버와 

로그인시에 
엑세스 토큰은 localstolage에 담고
리프레쉬 토큰은 쿠키에 담는다.

접속시에 시큐리티에서
tokenAuthenticationFilter로 
토큰을 가지고 있는지 1,2,3으로 확인 
토큰이 있고 기한이 남으면 1 - 
토큰이 있고 기한이 끝나면 2
토큰이 이상하면 3

프론트에서 처리 


구글 로그인시에는 
엑세스 토크을 만든다.
<img width="700" alt="image" src="https://github.com/user-attachments/assets/41abd620-428b-479c-9e04-73037cdf0699">
