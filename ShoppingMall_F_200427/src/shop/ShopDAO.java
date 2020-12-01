package shop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class ShopDAO {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	private static ShopDAO ShopDAOobj;
	
	private ShopDAO(){
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

	public static ShopDAO getInstance() {
		if (ShopDAOobj == null) {
			ShopDAOobj = new ShopDAO();
		}
		return ShopDAOobj;
	}
	
	
	public ArrayList<String[]> selOne(String id) {
		ArrayList<String[]> slist = new ArrayList();
		String sql = "select * from basket where id = '"+id+"'";
		if(connect()) {
			try {
				stmt = conn.createStatement();
				if(stmt != null) {
					rs = stmt.executeQuery(sql);
					while(rs.next()) {
						ShopDTO newDTO = new ShopDTO();
						newDTO.setPname(rs.getString("pname"));
						newDTO.setPrice(rs.getString("price"));
						newDTO.setEa(rs.getString("ea"));
						newDTO.setBuyea(rs.getString("buyea"));
						slist.add(newDTO.getArray());
					}
					
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("DB연결 실패");
		}
		
		return slist;
	}
	
	
	public ArrayList<String[]> getList() {
		ArrayList<String[]> slist = new ArrayList();	
		String sql = "select * from basket";
		if (connect()) {
			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql); 
					while (rs.next()) {
						ShopDTO s = new ShopDTO();
//						s.setNo(rs.getString("no"));
//						s.setId(rs.getString("id"));
						s.setPname(rs.getString("pname"));
						s.setPrice(rs.getString("price"));
						s.setEa(rs.getString("ea"));
						s.setBuyea(rs.getString("buyea"));

						slist.add(s.getArray());
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("DB연결 실패");
			System.exit(0);
		}
		return slist;
	}

	
	
	public void insertShop(ShopDTO newDTO) {
		boolean result = false;
		
		if (connect()) {
			try {
				String sql = "insert into basket values(basket_no.nextval,?,?,?,?,?)"; // 시퀸스 주소
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, newDTO.getId());
				psmt.setString(2, newDTO.getPname());
				psmt.setString(3, newDTO.getPrice());
				psmt.setString(4, newDTO.getEa());
				psmt.setString(5, newDTO.getBuyea());

				int r = psmt.executeUpdate();

				if (r > 0) {
					result = true;
				}
				psmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("DB연결 실패");
			System.exit(0);
		}

		return;
	}
	
//	public void updateShop(ShopDTO newDTO) {
//		if(connect()) {
//			try {
//				String sql = "update basket set id=?, pname=?, price=?, ea=?, buyea=? where no=?";
//				PreparedStatement psmt = conn.prepareStatement(sql);
//				psmt.setString(1, newDTO.getNo());
//				psmt.setString(2, newDTO.getId());
//				psmt.setString(3, newDTO.getPname());
//				psmt.setString(4, newDTO.getPrice());
//				psmt.setString(4, newDTO.getEa());
//				psmt.setString(5, newDTO.getBuyea());
//				
//				psmt.executeUpdate();	
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}else {
//			System.out.println("DB 연결 실패");
//		}				
//	}

	

	

	public void removeBasket(String id) { 
		if(connect()) {
			try {
				String sql = "delete from basket where id=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, id);
				
				psmt.executeUpdate();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("DB 연결 실패");
		}
	}

	

	

	
			
}
