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
	
	<h2>formatNumber, formatDate, parseNumber, parseDate</h2>
	<!-- format은 형식을 바꿈, parse는 형변환 -->
	
	<c:set var="d01" value="2020"/>
	
	<fmt:formatNumber var="v01" value="${d01 }" pattern="0000.000"  />
	<fmt:formatNumber var="v02" value="${d01 }" pattern="0000000"  />
	
	${v01 }<br/>
	<br/>
	<hr/>
	
	<c:set var="d02" value="<%= new Date() %>" />
	<fmt:formatDate var="v03" value="${d02 }" pattern="yyyyMMdd HH:mm:ss" /> <!-- value에는 반드시 날짜의 형태를 가지고 있는 값이 있어야 합니다 -->
	<fmt:formatDate var="v04" value="${d02 }" pattern="yyyy년MM월dd일  HH:mm:ss"/>
	<fmt:formatDate var="v05" value="${d02 }" pattern="yyyy-MM-dd HH:mm:ss"/>

	${d02 }<br/>
	${v03 }<br/>
	${v04 }<br/>
	${v05 }<br/>
	<hr/>
	
	<h2>parseNumber는 문자를 숫자로 변경할 떄 사용</h2>
	<fmt:parseNumber var="v06" value="1.100" pattern="0.000" />
	<fmt:parseNumber var="v07" value="1,123abc" type="number"/>
	${v06 }<br/>
	${v07 }<br/>
	<hr/>
	
	
	
	
	<h2>parseDate는 문자를 날짜로 변경할 때 사용</h2>
	<c:set var="d03" value="2020/11/04"/>
	
	<fmt:parseDate var="v08" value="${d03 }" pattern="yyyy/MM/dd"/>
	<fmt:formatDate var="a" value="${v08 }" pattern="yyyy년MM월dd일"/>
	${v08 }<br/>
	${a }<br/>
	
	<c:set var="d04" value="2020-11-04 23:12:11"/>
	<fmt:parseDate var="v09" value="${d04 }" pattern="yyyy-MM-dd HH:mm:ss" />
	<fmt:formatDate var="b" value="${v09 }" pattern="yyyy년MM월dd일"/>
	${v09 }<br/>
	${b }<br/>
	
	
</body>
</html>
	
	