$(document).ready(() => {
    $('#login-form').submit((event) => {
        event.preventDefault(); // 폼의 기본 제출 동작을 막음

        // 폼 데이터 수집
        const formData = {
            userId: $('#username').val(),
            password: $('#password').val()
        };

        // Ajax 요청
        $.ajax({
            type: "POST",  // 로그인은 일반적으로 POST로 전송
            url: "/member/api/login", // 로그인 URL (Spring Security에서 로그인 처리를 할 수 있는 URL)
            data: JSON.stringify(formData),  // 데이터를 JSON 형식으로 변환
            contentType: 'application/json; charset=utf-8', // 요청 데이터 타입
            dataType: 'json', // 서버에서 받을 데이터 타입
            success: (response) => {
                if (response.loggedIn) {
                    console.log(response.accessToken);
                    alert(response.message);  // 로그인 성공 시 메시지
                    localStorage.setItem('accessToken', response.accessToken);
                    window.location.href = response.url;  // 로그인 성공 후 홈 페이지로 이동
                } else {
                    alert(response.message);  // 로그인 실패 시 메시지
                }
            },
            error: (error) => {
                console.error('오류 발생:', error);  // 실패 시 오류 처리
                alert("로그인 실패. 다시 시도해 주세요.");
            }
        });
    });
});



// 구글 로그인

// /member/login 페이지에서 로그인 후 JWT 토큰을 받아서 로컬스토리지에 저장하고 홈으로 이동
window.onload = function() {
    // 서버에서 전달된 JWT 토큰을 URL 쿼리 파라미터에서 가져오기
    const accessToken = localStorage.getItem('accessToken');
    console.log("accessToken from localStorage: ", accessToken);

    if (!accessToken) {
        // 서버에서 제공한 경우 (리프레시 후 받았을 가능성)
        const tokenFromQuery = getAccessTokenFromQuery();  // URL에서 accessToken을 추출
        if (tokenFromQuery) {
            localStorage.setItem('accessToken', tokenFromQuery); // 로컬스토리지에 저장
            console.log("accessToken saved from query param: ", tokenFromQuery);
            window.location.href ="/";
        } else {
            console.error('Access token not found');
        }
    }
};

// URL 쿼리 파라미터에서 accessToken을 추출하는 함수
function getAccessTokenFromQuery() {
    const params = new URLSearchParams(window.location.search);
    const token = params.get('accessToken');
    return token;  // URL 쿼리 파라미터에서 토큰을 추출하여 반환
}