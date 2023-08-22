<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>네이버 로그인</title>
    <script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
</head>
<body>
<!-- 네이버 아이디로 로그인 폼 -->
<form action="${contextPath}/member/naverlogin.do" method="post">
    <input type="hidden" name="response_type" value="code">
    <input type="hidden" name="client_id" value="9yPwV2Vfaq_nQV3nPjAH">
    <input type="hidden" name="redirect_uri" value="http://localhost:8070/">
    <input type="hidden" name="state" value="${sessionScope.naverState}">
    <button type="submit">
        네이버 아이디로 로그인
    </button>
</form>
<!-- //네이버 아이디로 로그인 폼 -->
</body>
</html>
