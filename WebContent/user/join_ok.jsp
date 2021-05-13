<%@page import="com.myweb.user.model.UserVO"%>
<%@page import="com.myweb.user.model.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8"); //한글처리
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	String name = request.getParameter("name");
	String email = request.getParameter("email");
	String address = request.getParameter("address");
	
	//1. 회원아이디 중복검사
	UserDAO dao = UserDAO.getInstance();
	int result = dao.checkId(id);
	
	if(result == 0) { //중복.
%>
	<script>
		alert("아이디가 중복 되었습니다");
		history.go(-1); //뒤로가기
	</script>
<%		
	} else {
	//2. 회원가입
		UserVO vo = new UserVO(id, pw, name, email, address, null);
		int result2 = dao.join(vo); //1이면 성공, 0이면 실패
		
		if(result2 == 1) {
%>			
		<script>
			alert("회원 가입을 축하합니다!");
			location.href = "login.jsp";
		</script>
<%			
		} else {
%>
		<script>
			alert("네트워크 오류입니다. 관리자에게 문의하세요")	;
			location.href = "join.jsp";
		</script>
		
<%			
		}
	}
%>
    
    