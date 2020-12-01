package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import memberManager.MemberDAO;
import memberManager.MemberMemberDTO;

	public class LoginDAO {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private static LoginDAO LoginDAOobj;
	
	
	LoginDAO(){
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
	
	
	public static LoginDAO getInstance() {
		if (LoginDAOobj == null) {
			LoginDAOobj = new LoginDAO();
		}
		return LoginDAOobj;
	}
	
	public ArrayList<MemberMemberDTO> selAll() {
		ArrayList <MemberMemberDTO> memberDTOList = new ArrayList<>();
		MemberMemberDTO searchDTO = null;
		
		if(connect()) {
			try {
				stmt = conn.createStatement();
				String sql = "select * from member";
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					
					searchDTO = new MemberMemberDTO();
					searchDTO.setId(rs.getString("id"));
					searchDTO.setPw(rs.getString("pw"));
					searchDTO.setLv(rs.getString("lv"));
					searchDTO.setPoint(rs.getString("point"));
					
					memberDTOList.add(searchDTO);
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("DB 연결 실패");
		}
		
		return memberDTOList;
		
	}
	
	public ArrayList<String[]> getList(){
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
	
	
}
