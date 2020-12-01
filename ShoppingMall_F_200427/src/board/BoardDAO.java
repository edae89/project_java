package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import board.BoardDTO;

public class BoardDAO {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	private static BoardDAO BoardDAOobj;
	
	public static BoardDAO getInstance() {
		if (BoardDAOobj == null) {
			BoardDAOobj = new BoardDAO();
		}
		return BoardDAOobj;
	}
	
	private BoardDAO(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 연결 실패:" + e.getMessage());
		}
	}
	
	
	private boolean connect() { // oracle DB에 접속하기 위한 자원
		boolean cFlag = false;
		try {
			conn = DriverManager.getConnection("" + "jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");// 오라클
																													// 주소
			cFlag = true;
		} catch (SQLException e) {

			e.printStackTrace();

		}
		return cFlag;

	}

	public void insertBoard(BoardDTO b) {
		boolean result = false;
		ArrayList<String[]> blist = new ArrayList<>();
		if (connect()) {
			String sql = "insert into board values(board_no.nextval,?,?,?)"; 
			try {
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, b.getId());
				psmt.setString(2, b.getPname());
				psmt.setString(3, b.getAppraisal());

				int r = psmt.executeUpdate();

				if (r > 0) {
					result = true;
				}
				psmt.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("DB연결 실패");
			System.exit(0);
		}

		return;
	}

	public ArrayList<String[]> getList() {
		ArrayList<String[]> blist = new ArrayList();
		String sql = "select * from board";
		if (connect()) {
			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql); //
					while (rs.next()) {
						BoardDTO b = new BoardDTO();

						b.setNo(rs.getString("no"));
						b.setId(rs.getString("id"));
						b.setPname(rs.getString("pname"));
						b.setAppraisal(rs.getString("appraisal"));

						blist.add(b.getArray());
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("DB연결 실패");
			System.exit(0);
		}
		return blist;
	}

	

	

}
