<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h2>아래값을 yyyy-mm-dd일 형식으로 변경해서 출력</h2>

	<c:set var="TIME_A" value="2020/05/03"/>
	<c:set var="TIME_B" value="2020-05-03 21:30:32"/>
	<c:set var="TIME_C" value="<%=new Date() %>"/>



	<fmt:parseDate var="P_TIME_A" value="${TIME_A }" pattern="yyyy/MM/dd"/>
	<fmt:formatDate var="result_A" value="${P_TIME_A }" pattern="yyyy-MM-dd"/>
	${result_A }
	

	<fmt:parseDate var="P_TIME_B" value="${TIME_B }" pattern="yyyy-MM-dd HH:mm:ss"/>
	<fmt:formatDate var="result_B" value="${P_TIME_B }" pattern="yyyy-MM-dd"/>
	${result_B }
	
	
	<fmt:formatDate var="result_C" value="${TIME_C }" pattern="yyyy-MM-dd"/>
	${result_C }





</body>
</html>