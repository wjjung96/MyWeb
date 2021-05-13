package com.myweb.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.board.model.BoardDAO;
import com.myweb.board.model.BoardVO;

public class GetContentServiceImpl implements IBoardService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String bno = request.getParameter("bno");
		/*
		DAO객체 생성
		DAO에 getContent()메서드 생성.
		bno를 기반으로 한 행을 VO에 담아서 가지고 나옵니다.
		request객체이 vo이름으로 데이터를 저장.
		content.jsp화면에서는 화면에 vo값을 출력.
		*/
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO vo = dao.getContent(bno);
		
		//request에 저장
		request.setAttribute("vo", vo);
		
		
	}

}
