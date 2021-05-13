<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<!-- elseif문 c:choose문을 사용합니다 -->
	<c:choose>
		<c:when test="${param.name eq '홍길동' }">
			<p>홍길동입니다</p>
		</c:when>
		<c:when test="${param.name eq '이순신' }">
			<p>이순신입니다</p>
		</c:when>
		<c:when test="${param.name eq '홍길자' }">
			<p>홍길자입니다</p>
		</c:when>
		<c:otherwise>
			<p>셋다 아닌데요?</p>
		</c:otherwise>
	</c:choose>
	
	<!-- 20세이상, 20세 미만을 choose문으로 -->
	<c:choose>
		<c:when test="${param.age >= 20 }">
			성인입니다
		</c:when>
		<c:otherwise>
			미성년자입니다
		</c:otherwise>
	</c:choose>
	
	
	
	
</body>
</html>