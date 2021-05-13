package com.myweb.controller;

import java.io.IOException;

import javax.imageio.spi.RegisterableService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.board.service.DeleteServiceImpl;
import com.myweb.board.service.GetContentServiceImpl;
import com.myweb.board.service.GetListServiceImpl;
import com.myweb.board.service.IBoardService;
import com.myweb.board.service.RegistServiceImpl;
import com.myweb.board.service.UpHitServiceImpl;
import com.myweb.board.service.UpdateServiceImpl;
//1.
@WebServlet("*.board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BoardController() {
        super();
       
    }
    
    //2. get, post요청 통일
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		//3. 요청분기
		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI(); //URI
		String path = request.getContextPath();
		
		String command = uri.substring( path.length() ); //요청경로
		
		System.out.println(command);
		
		//MVC2방식은 forward이동을 기본적으로 사용합니다. Redirect는 다시 컨트롤러로 연결할 때 사용합니다.
		
		//service의 인터페이스 타입을 선언(if문 안에서 알맞은 서비스클래스로 연결)
		IBoardService service;
		
		if(command.equals("/board/list.board")) { //글 목록 요청
			
			service = new GetListServiceImpl();
			service.execute(request, response);
			
			//화면에 전달할 값이 있으니 forward이동
			request.getRequestDispatcher("board_list.jsp").forward(request, response);
			
		} else if(command.equals("/board/write.board")) { //글작성 화면요청

			//권한이 없으면 진입하지 못하도록 하는 작업. filter에서 처리
//			HttpSession session = request.getSession();
//			String user_id  = (String)session.getAttribute("user_id");
//			if(user_id == null) {
//				response.sendRedirect("/MyWeb");
//				return;
//			}
			
			request.getRequestDispatcher("board_write.jsp").forward(request, response);
					
			
		} else if(command.equals("/board/regist.board")) { //글 등록 요청

			service = new RegistServiceImpl();
			service.execute(request, response);
			
			response.sendRedirect("list.board"); //리다이렉트는 다시 컨트롤러를 태울때 사용합니다.
		
		} else if(command.equals("/board/getContent.board")  ) {
			
			//조회수 증가(service영역)
			service = new UpHitServiceImpl();
			service.execute(request, response);
			
			//게시글 정보 조회
			service = new GetContentServiceImpl();
			service.execute(request, response);
			
			request.getRequestDispatcher("board_content.jsp").forward(request, response);
		} else if(command.equals("/board/modify.board")) {
			
			//getContentServiceimpl를 재활용
			service = new GetContentServiceImpl();
			service.execute(request, response);
			
			request.getRequestDispatcher("board_modify.jsp").forward(request, response);
		
		} else if(command.equals("/board/update.board")) {
			/*
			 * 1.UpdateServiceImpl클래스를 생성하고 execute()메서드 실행. 
			 * 2. 서비스영역에서 bno, title, content를 받아서 DAO의 update() 메서드 실행
			 * 3. update()는 sql문을 이용해서 해당 글 번호의 내용을 수정.
			 * 4. 컨트롤러에서는 Content화면으로 이동.(content가 필요한값을 자바측에서 전송)
			 */
			service = new UpdateServiceImpl();
			service.execute(request, response);
			
			//다시컨트롤러를 태워서 이동
			response.sendRedirect("getContent.board?bno=" + request.getParameter("bno") ); 
		} else if(command.equals("/board/delete.board")) {
			
			service = new DeleteServiceImpl();
			service.execute(request, response);
			
			response.sendRedirect("list.board");
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
