<%@page import="com.myweb.user.model.UserVO"%>
<%@page import="com.myweb.user.model.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	/*
	1. 사용자가 직전화면에서 입력한 pw값과 id를 기반으로 login()메서드를 재활용해서
	   사용자를 검증합니다.
	
	2. login()가 null을 반환하면 "현재 비밀번호를 확인하세요" 출력하고, 뒤로가기 
	   login()가 값을 가진다면 delete() 메서드를 작성하고 삭제를 진행하면 됩니다.
	
	3. 삭제 성공시에는 세션을 전부 지우고 index.jsp페이지로 리다이렉트.
	   삭제 실패시에는 경고창을 띄우고 mypage.jsp로 리다이렉트
	   
	*/
	request.setCharacterEncoding("utf-8");
	
	String id = (String)session.getAttribute("user_id"); //id는 세션에서
	String pw = request.getParameter("pw");
	
	//DAO생성
	UserDAO dao = UserDAO.getInstance();
	UserVO vo = dao.login(id, pw);
	
	if(vo == null) { //비밀번호가 일치하지 않는경우
%>
	<script>
		alert("비밀번호를 확인하세요");
		history.go(-1);
	</script>
<%		
	} else { //비밀번호가 맞는경우
		int result = dao.delete(id);
		
		if(result == 1) { //탈퇴성공
		session.invalidate(); //세션삭제
%>			
		<script>
			alert("안녕히 가세요!");
			location.href = "/MyWeb";
		</script>
<%		
		} else { //탈퇴실패
%>
		<script>
			alert("탈퇴에 실패했습니다. 관리자에게 문의하세요");		
			location.href = "mypage.jsp";
		</script>
<%			
		}
	}
%>






