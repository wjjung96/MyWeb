package com.myweb.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.myweb.user.model.UserDAO;
import com.myweb.util.JdbcUtil;

public class BoardDAO {
	//1. 스스로 객체를 1개 생성합니다.
	private static BoardDAO instance = new BoardDAO();  
	//2. 외부에서 생성자를 호출할 수 없도록 생성자에 private제한을 붙임
	private BoardDAO() {
		try {
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//커넥션풀을 얻는 방법
			InitialContext ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		
		} catch (Exception e) {
			System.out.println("드라이버 호출 에러!");
		}
		
	}
	//3. 외부에서 객체생성을 요구하면 getter메서드를 이용해서 1번의 객체를 반환
	public static BoardDAO getInstance() {
		return instance;
	}
	
	///////////////////////////////////////////////////////////////////
	//멤버변수
	private DataSource ds; //데이터베이스 연결풀을 저장해놓는 객체
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	
	
	//글등록 메서드
	public void regist(String writer, String title, String content) {
		
		//insert...
		String sql = "insert into board(bno, writer, title, content) values(board_seq.nextval, ?, ?, ?)";
		
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, writer );
			pstmt.setString(2, title );
			pstmt.setString(3, content );
			
			pstmt.executeUpdate(); //등록후에 끝.
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
	}
	
	//글목록 조회
	/*
	public ArrayList<BoardVO> getList() {
		
		ArrayList<BoardVO> list = new ArrayList<>();
		
		String sql = "select * from board order by bno desc";
		
		try {
			conn = ds.getConnection(); //연결 
			
			pstmt = conn.prepareStatement(sql); //sql준비
			
			rs = pstmt.executeQuery(); //sql문실행
			
			while(rs.next()) { //1행에서 처리할 작업...
				
				int bno = rs.getInt("bno"); //숫자형은 getInt()
				String writer = rs.getString("writer");
				String title = rs.getString("title");
				String content =rs.getString("content");
				Timestamp regdate = rs.getTimestamp("regdate"); //날짜형은 Timestamp() or Date()
				int hit = rs.getInt("hit");
				
				//한 바퀴 회전당 VO를 하나씩 생성
				BoardVO vo = new BoardVO(bno, writer, title, content, regdate, hit);
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		
		return list;
	}
	*/
	
	public ArrayList<BoardVO> getList(int pageNum, int amount) {
		
		ArrayList<BoardVO> list = new ArrayList<>();
		
		String sql = "select *\r\n" + 
						"from (select rownum rn,\r\n" + 
						"             a.*\r\n" + 
						"        from (select *\r\n" + 
						"              from board \r\n" + 
						"              order by bno desc\r\n" + 
						"        ) a\r\n" + 
						") where rn > ? and rn <=?";
		
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (pageNum - 1) * amount );
			pstmt.setInt(2, pageNum * amount);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) { //1행에서 처리할 작업...
				
				int bno = rs.getInt("bno"); //숫자형은 getInt()
				String writer = rs.getString("writer");
				String title = rs.getString("title");
				String content =rs.getString("content");
				Timestamp regdate = rs.getTimestamp("regdate"); //날짜형은 Timestamp() or Date()
				int hit = rs.getInt("hit");
				
				//한 바퀴 회전당 VO를 하나씩 생성
				BoardVO vo = new BoardVO(bno, writer, title, content, regdate, hit);
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}

		return list;
	}
	
	//전체게시글 수
	public int getTotal() {
		int result = 0;
		
		String sql = "select count(*) as total from board";
		
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("total");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	
	
	
	
	//글 상세정보 메서드
	public BoardVO getContent(String bno) {
		
		BoardVO vo = null;
		
		String sql = "select * from board where bno = ?";
		
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bno); //자동형변환 
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int num = rs.getInt("bno");
				String writer = rs.getString("writer");
				String title =rs.getString("title");
				String content = rs.getString("content");
				Timestamp regdate = rs.getTimestamp("regdate");
				int hit = rs.getInt("hit");
				
				vo = new BoardVO(num, writer, title, content, regdate, hit);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return vo;
	}
	
	//글 수정메서드
	public void update(String bno, String title, String content) {
	
		String sql = "update board set title = ?, content = ?, regdate = sysdate where bno = ?";
		
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title );
			pstmt.setString(2, content );
			pstmt.setString(3, bno);

			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		
	}
	
	//글 삭제
	public void delete(String bno) {
		
		String sql = "delete from board where bno = ?";
		
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bno);
			
			pstmt.executeUpdate(); //sql문실행
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
	}
	
	//조회수 증가 메서드
	public void upHit(String bno) {
		
		String sql = "update board set hit = hit + 1 where bno = ?";
		
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bno);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
		
	}
	
	
	
	
	
	
	
	
	
}
