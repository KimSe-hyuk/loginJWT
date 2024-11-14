$(document).ready(function() {
    // 회원가입 폼 제출 시
    $('#signup-form').submit(function(event) {
        event.preventDefault();  // 기본 폼 제출 방지

        // 폼 데이터 수집
        const formData = {
            userId: $('#user_id').val(),
            userName: $('#name').val(),
            password: $('#password').val(),
            role: $('#role').val()  // 선택한 role 값
        };

        // Ajax 요청
        $.ajax({
            type: 'POST',  // HTTP 방식 POST
            url: '/member/api/join',  // 서버에서 처리할 URL
            data: JSON.stringify(formData),  // 폼 데이터를 JSON 형식으로 변환
            contentType: 'application/json; charset=utf-8',  // 요청 데이터 타입
            dataType: 'json',  // 서버에서 받을 데이터 타입
            success: function(response) {
                // 회원가입 성공 시
                alert(response.message);  // 성공 메시지 표시
                window.location.href = response.url;  // 로그인 페이지로 리다이렉트
            },
            error: function(error) {
                // 오류 발생 시
                console.error('회원가입 실패:', error);  // 콘솔에 오류 출력
                alert("회원가입에 실패했습니다. 다시 시도해 주세요.");  // 오류 메시지 표시
            }
        });
    });
});
