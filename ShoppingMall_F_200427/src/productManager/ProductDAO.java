package productManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class ProductDAO {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private static ProductDAO ProductDAOobj;
	
	
	private ProductDAO(){
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
	
	
	
	public static ProductDAO getInstance() {
		if (ProductDAOobj == null) {
			ProductDAOobj = new ProductDAO();
		}
		return ProductDAOobj;
	}
	
	
	
	public ArrayList<String[]> getList(){
		ArrayList<String[]> list= new ArrayList();
		String sql="select * from product";
		if(connect()) {
			try {
				stmt=conn.createStatement();
				if(stmt != null) {
					rs = stmt.executeQuery(sql);  
					while(rs.next()) {
						ProductDTO product = new ProductDTO();
						product.setPname(rs.getString("pname"));
						product.setPrice(rs.getString("price"));
						product.setEa(rs.getString("ea"));
						
						list.add(product.getArray());
						
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



	public boolean insertProduct(ProductDTO newDTO) {
		boolean result = false;
		if (this.connect()) {
			try {
				String sql = "insert into product VALUES (?,?,?)";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, newDTO.getPname());
				psmt.setString(2, newDTO.getPrice());
				psmt.setString(3, newDTO.getEa());
				

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



	public void updateProduct(ProductDTO newDTO) {
		if(connect()) {
			try {
				String sql = "update product set price=?, ea=? where pname=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, newDTO.getPrice());
				psmt.setString(2, newDTO.getEa());
				psmt.setString(3, newDTO.getPname());
				
				psmt.executeUpdate();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("DB 연결 실패");
		}
		
		
	}


	public void removeProduct(ProductDTO newDTO) {
		if(connect()) {
			try {
				String sql = "delete from product where pname=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, newDTO.getPname());
				
				psmt.executeUpdate();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("DB 연결 실패");
		}
	}





	
	
	
}
