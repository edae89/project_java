package memberManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class MemberDAO {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private static MemberDAO MemberDAOobj;

	
	MemberDAO(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 연결 실패:" + e.getMessage());
		}
	}
	
	
	private boolean connect() {
		boolean result = false;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
			result = true;
		} catch (Exception e) {
			System.out.println("연결 실패:" + e.getMessage());
		}
		return result;
	}
	
	
	public static MemberDAO getInstance() {
		if (MemberDAOobj == null) {
			MemberDAOobj = new MemberDAO();
		}
		return MemberDAOobj;
	}
	
	
	public ArrayList<String[]> getListBoard(){
		ArrayList<String[]> list= new ArrayList();
		String sql="select * from board";
		if(connect()) {
			try {
				stmt=conn.createStatement();
				if(stmt != null) {
					rs = stmt.executeQuery(sql);  
					while(rs.next()) {
						MemberBoardDTO board = new MemberBoardDTO();
						board.setNo(rs.getString("no"));
						board.setId(rs.getString("id"));
						board.setPname(rs.getString("pname"));
						board.setAppraisal(rs.getString("appraisal"));
						
						list.add(board.getArray());
						
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else {
			System.out.println("DB 연결 실패");
			System.exit(0);
		}
		return list;
	}
	
	
	public ArrayList<String[]> getListMember(){
		ArrayList<String[]> list= new ArrayList();
		String sql="select * from member";
		if(connect()) {
			try {
				stmt=conn.createStatement();
				if(stmt != null) {
					rs = stmt.executeQuery(sql);  
					while(rs.next()) {
						MemberMemberDTO member = new MemberMemberDTO();
						member.setId(rs.getString("id"));
						member.setPw(rs.getString("pw"));
						member.setLv(rs.getString("lv"));
						member.setPoint(rs.getString("point"));
						
						list.add(member.getArray());
						
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else {
			System.out.println("DB 연결 실패");
			System.exit(0);
		}
		return list;
	}
	
	
	public ArrayList<String[]> getListBlack(){
		ArrayList<String[]> list= new ArrayList();
		String sql="select * from blacklist";
		if(connect()) {
			try {
				stmt=conn.createStatement();
				if(stmt != null) {
					rs = stmt.executeQuery(sql);  
					while(rs.next()) {
						MemberBlackDTO black = new MemberBlackDTO();
						black.setId(rs.getString("id"));
						black.setMemo(rs.getString("memo"));
						
						list.add(black.getArray());
						
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else {
			System.out.println("DB 연결 실패");
			System.exit(0);
		}
		return list;
	}


	public boolean insertBlack(MemberBlackDTO newDTO) {
		boolean result = false;
		if (this.connect()) {
			try {
				String sql = "insert into blacklist VALUES (?,?)";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, newDTO.getId());
				psmt.setString(2, newDTO.getMemo());

				int r = psmt.executeUpdate();

				if (r > 0) {
					result = true;
				}
				psmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("DB 연결 실패");
			System.exit(0);
		}

		return result;
	
	}

	
	
	public void removeBlack(MemberBlackDTO newDTO) {
		if(connect()) {
			try {
				String sql = "delete from blacklist where id=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, newDTO.getId());
				
				psmt.executeUpdate();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("DB 연결 실패");
		}
	}
	
	
	
	public void removeBoard(MemberBoardDTO newDTO) {
		if(connect()) {
			try {
				String sql = "delete from Board where no=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, newDTO.getNo());
				
				psmt.executeUpdate();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("DB 연결 실패");
		}
	}
	
	
	
	public void updateMemberLvB(MemberBlackDTO newDTO) {
		if(connect()) {
			try {
				String sql = "update member set lv=? where id=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, "b");
				psmt.setString(2, newDTO.getId());
				
				psmt.executeUpdate();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("DB 연결 실패");
		}
	}

	public void updateMemberLv(MemberBlackDTO newDTO) {
		if(connect()) {
			try {
				String sql = "update member set lv=? where id=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, "n");
				psmt.setString(2, newDTO.getId());
				
				psmt.executeUpdate();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("DB 연결 실패");
		}
	}
	
	
}
