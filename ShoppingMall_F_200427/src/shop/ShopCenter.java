package shop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import client.ClientChat;
import client.EventHandlerC;
import productManager.ProductDAO;

public class ShopCenter extends JFrame implements ActionListener {
	
	
	JPanel cP, sP;
	
	DefaultListModel<String> lm = new DefaultListModel<String>();
	
	String headerProduct[] = { "상품명", "가격", "물품 수량"};
	DefaultTableModel tableModelProduct = new DefaultTableModel(null, headerProduct);
	JTable tableProduct = new JTable(tableModelProduct);
	JScrollPane tableScrollProduct = new JScrollPane(tableProduct);
	
	String headerBasket[] = { "상품명", "가격", "물품 수량", "구매 수량" };
	DefaultTableModel tableModelBasket = new DefaultTableModel(null, headerBasket);
	JTable tableBasket = new JTable(tableModelBasket);
	JScrollPane tableScrollBasket = new JScrollPane(tableBasket);
	
	ArrayList<String[]> initListProduct=null;
	ArrayList<String[]> initListBasket=null;

	JTextField[] indataProduct = new JTextField[3];
	JTextField[] indataBasket = new JTextField[4];

	int modIntRowProduct = -1;			//Product JTable 줄 값
	int modIntRowBasket = -1;			//Basket JTable 줄 값

	ProductDAO sqlProductDAO = ProductDAO.getInstance();
	ShopDAO sqlShopDAO = ShopDAO.getInstance();
	
	ClientChat c = null;
	String loginId = null;
	
	Color green = new Color(000,102,000);
	Color red = new Color(204,000,000);
	Color beige = new Color(204,153,102);
	Color brown = new Color(153,102,051);
	
	public ShopCenter(ClientChat c, String inputId) {
		super("회원 메뉴_<쇼핑하기>");
		this.c = c;
		this.loginId  = inputId;
		this.setBounds(100, 100, 800, 700);

		tableProductSetting();
		tableBasketSetting();
		initAll();
		createC();
		createS();
		this.addWindowListener(new EventHandlerC(c, inputId));
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public void initAll() {	//모든 DB 정보 불러오기
		initProduct();
		initBasket();
	}
	
	//Product DB 정보 불러오기
	public void initProduct() {
		initListProduct = c.getInitListProduct();
		for(int i=0 ; i < initListProduct.size(); i++) {
			tableModelProduct.addRow(initListProduct.get(i));
		}
	}
	
	//Basket DB 접속자 정보만 불러오기
	public void initBasket() {
		initListBasket = c.getInitListBasket();
		for(int i=0 ; i < initListBasket.size(); i++) {
			tableModelBasket.addRow(initListBasket.get(i));
		}
		
	}

	
	
	public void tableProductSetting() {	
		tableProduct.setRowMargin(0);
		tableProduct.getColumnModel().setColumnMargin(0);
		tableProduct.getTableHeader().setReorderingAllowed(false);
		tableProduct.getTableHeader().setResizingAllowed(false);
		
		tableProduct.setShowVerticalLines(false);
		tableProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tableProduct.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//클릭 시, 상품 JTable 줄값을 텍스트필드에 세팅
				if (e.getButton() == 1) {
					int column = tableProduct.columnAtPoint(e.getPoint());
					int row = tableProduct.rowAtPoint(e.getPoint());
					tableProduct.changeSelection(row, column, false, false);
					modIntRowProduct = tableProduct.getSelectedRow();
					for (int i = 0; i < indataProduct.length; i++) {
						indataProduct[i].setText((String) tableProduct.getValueAt(modIntRowProduct, i));
					}
				}
				if (e.getClickCount() == 2) {
				}
				if (e.getButton() == 3) {
					
				}
			}
		});

		DefaultTableCellRenderer ts = new DefaultTableCellRenderer();
		ts.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tc = tableProduct.getColumnModel();
		for (int i = 0; i < tc.getColumnCount(); i++) {
			tc.getColumn(i).setCellRenderer(ts);
		}

	}
	
	
	public void tableBasketSetting() {	
		tableBasket.setRowMargin(0);
		tableBasket.getColumnModel().setColumnMargin(0);
		tableBasket.getTableHeader().setReorderingAllowed(false);
		tableBasket.getTableHeader().setResizingAllowed(false);

		tableBasket.setShowVerticalLines(false);
		tableBasket.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableBasket.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//클릭 시, 장바구니 JTable 줄값을 텍스트필드에 세팅
				if (e.getButton() == 1) {
					int column = tableBasket.columnAtPoint(e.getPoint());
					int row = tableBasket.rowAtPoint(e.getPoint());
					tableBasket.changeSelection(row, column, false, false);
					modIntRowBasket = tableBasket.getSelectedRow();
					for (int i = 0; i < indataBasket.length; i++) {
						indataBasket[i].setText((String) tableBasket.getValueAt(modIntRowBasket, i));
					}
				}
				if (e.getClickCount() == 2) {
				}
				if (e.getButton() == 3) {
					
				}
			}
		});

		DefaultTableCellRenderer ts = new DefaultTableCellRenderer();
		ts.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tc = tableBasket.getColumnModel();
		for (int i = 0; i < tc.getColumnCount(); i++) {
			tc.getColumn(i).setCellRenderer(ts);
		}

	}
	
	
	//Product JTable 줄 삭제
	public void delTableProductRow(int row) {
		tableModelProduct.removeRow(row);
	}
	//Basket JTable 줄 삭제
	public void delTableBasketRow(int row) {
		tableModelBasket.removeRow(row);
	}
	
	

	// cP : JTable(board, member, black)
	private void createC() {
		cP = new JPanel();
		cP.setLayout(new BorderLayout());
		cP.setBackground(beige);
		this.add(cP, "Center");
		
		
		//JTable product 세팅
		JPanel cP_c = new JPanel();
		cP_c.setBackground(beige);
		cP_c.setLayout(new BorderLayout());
		cP.add(cP_c, "Center");
		
		JPanel cP_c_n = new JPanel();
		cP_c.add(cP_c_n, "North");
		cP_c_n.setBackground(beige);
		JLabel productLb = new JLabel("상품 목록");
		cP_c_n.add(productLb);
		productLb.setPreferredSize(new Dimension(100,25));
		productLb.setBackground(beige);
		
		JPanel cP_c_c = new JPanel();
		cP_c_c.setBackground(beige);
		cP_c.add(cP_c_c, "Center");
		cP_c_c.add(tableScrollProduct);	//상품DB JTable에 읽어오기
		tableScrollProduct.setPreferredSize(new Dimension(500,500));
		
		
		//JTable basket 세팅
		JPanel cP_e = new JPanel();	
		cP_e.setLayout(new BorderLayout());
		cP_e.setBackground(beige);
		cP.add(cP_e, "East");
		
		JPanel cP_e_n = new JPanel();
		cP_e_n.setBackground(beige);
		cP_e.add(cP_e_n, "North");
		JLabel basketLb = new JLabel("장바구니 목록");
		basketLb.setBackground(beige);
		cP_e_n.add(basketLb);
		basketLb.setPreferredSize(new Dimension(100,25));
		
		
		JPanel cP_e_c = new JPanel();
		cP_e_c.setBackground(beige);
		cP_e.add(cP_e_c, "Center");
		cP_e_c.add(tableScrollBasket);	// 장바구니DB JTable에 읽어오기
		tableScrollBasket.setPreferredSize(new Dimension(280,500));
		
		
	}
	
	
	// sP : JButton(해당 후기 삭제), JTextField(member,black), JButton(블랙리스트 추가, 블랙리스트 삭제)
	private void createS() {
		sP = new JPanel();
		sP.setLayout(new BorderLayout());
		sP.setBackground(beige);
		this.add(sP, "South");
		
		
		JPanel sP_c = new JPanel();
		sP_c.setBackground(beige);
		sP.add(sP_c, "Center");
		sP_c.setLayout(new BorderLayout());
		
		JPanel sP_c_n = new JPanel();
		sP_c_n.setBackground(beige);
		sP_c.add(sP_c_n, "North");
		sP_c_n.setLayout(new BoxLayout(sP_c_n, BoxLayout.X_AXIS));
		sP_c_n.setBorder(BorderFactory.createEmptyBorder(0 , 50 , 0 , 10));
		for (int i = 0; i < indataProduct.length; i++) {
			JTextField dataProduct = new JTextField();
			indataProduct[i] = dataProduct;
			sP_c_n.add(indataProduct[i]);
			indataProduct[i].setEnabled(false);//텍스트필드 비활성화
		}
		
		
		JPanel sP_c_c = new JPanel();
		sP_c_c.setBackground(beige);
		sP_c.add(sP_c_c, "Center");
		JTextField buyeaTF = new JTextField(" 구매 수량 ");
		sP_c_c.add(buyeaTF);
		buyeaTF.setPreferredSize(new Dimension(100,25));
		buyeaTF.setBackground(green);
		
		
		JPanel sP_c_s = new JPanel();
		sP_c_s.setBackground(beige);
		sP_c.add(sP_c_s, "South");
		JButton basketAddBtn = new JButton("장바구니에 담기");
		basketAddBtn.setBackground(beige);
		sP_c_s.add(basketAddBtn);
		basketAddBtn.setPreferredSize(new Dimension(170,25));
		sP_c_s.setBorder(BorderFactory.createEmptyBorder(0 , 260 , 0 , 0));
		
		basketAddBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String in[] = new String[4];	//7
				//DB에 넘겨줄 값 in[]에 담기
				for (int i = 0; i < indataProduct.length; i++) {
					in[i] = indataProduct[i].getText();
				}
				if(modIntRowProduct != -1) {
					in[3] = buyeaTF.getText();	// 구매수량은 텍스트필드에서 입력받아 가져오도록
					int modEa = Integer.parseInt(in[2])-Integer.parseInt(buyeaTF.getText());	//수정된 남은 수량(형변환해서 계산 후
					in[2] = String.valueOf(modEa);											//다시 형변환)
					
					//텍스트필드indata[] 초기화
					for (int i = 0; i < indataProduct.length; i++) {
						indataProduct[i].setText("");
					}
					
//					delTableProductRow(modIntRowProduct);				//상품JTable 줄 삭제
//					tableModelProduct.insertRow(modIntRowProduct, in);	//상품JTable 줄 끼워넣고 in정보 추가(수정)
					tableModelBasket.addRow(in);						//장바구니JTable 줄에 in정보 추가
					
//					in[4] = loginId;
//					in[5] = "shop";
//					in[6] = "addB";
//					c.send(in);
					
					modIntRowProduct = -1;
					
				}
			}
		});
		
		
		
		
		JPanel sP_e = new JPanel();
		sP_e.setBackground(beige);
		sP.add(sP_e, "East");
		sP_e.setLayout(new BorderLayout());
		
		JPanel sP_e_n = new JPanel();
		sP_e_n.setBackground(beige);
		sP_e.add(sP_e_n, "North");
		sP_e_n.setLayout(new BoxLayout(sP_e_n, BoxLayout.X_AXIS));
		sP_c_n.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 0 , 10));
		for (int i = 0; i < indataBasket.length; i++) {
			JTextField dataBasket = new JTextField();
			indataBasket[i] = dataBasket;
			sP_e_n.add(indataBasket[i]);
			indataBasket[i].setEnabled(false);//텍스트필드 비활성화
		}
		
		
		
		
		
		JPanel sP_e_c = new JPanel();
		sP_e_c.setBackground(beige);
		sP_e.add(sP_e_c, "Center");
		JButton basketDelBtn = new JButton("담기 취소하기");
		basketDelBtn.setBackground(beige);
		sP_e_c.add(basketDelBtn);
		basketDelBtn.setPreferredSize(new Dimension(170,25));
		sP_e_c.setBorder(BorderFactory.createEmptyBorder(0 ,100 , 0 , 0));
		
		basketDelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String inBasket[] = new String[4];
				for (int i = 0; i < indataBasket.length; i++) {
					inBasket[i] = indataBasket[i].getText();
				}
				
				if(modIntRowBasket != -1) {
					
					delTableBasketRow(modIntRowBasket);	//장바구니JTable줄 삭제
					
					//텍스트필드 초기화
					for (int i = 0; i < indataBasket.length; i++) {
						indataBasket[i].setText("");
					}
					
					modIntRowBasket = -1;
				}
			}
		});
		
		
		JPanel sP_e_s = new JPanel();
		sP_e_s.setBackground(beige);
		sP_e.add(sP_e_s, "South");
		JButton basketOrderBtn = new JButton("장바구니 주문하기");
		basketOrderBtn.setBackground(beige);
		sP_e_s.add(basketOrderBtn);
		basketOrderBtn.setPreferredSize(new Dimension(170,25));
		sP_e_s.setBorder(BorderFactory.createEmptyBorder(0 ,100 , 0 , 0));
		
		basketOrderBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// JTable 전체 줄을 읽는다
				// 한줄씩 읽어 product 수량 수정, basket 추가 
				// product - [0]으로 검색하여 [2]값으로 수량만 수정
				// basket - [0][1][3]값만 저장
				
				sqlShopDAO.removeBasket(loginId);
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				
				//JTable값을 줄 단위로 읽어서 클라이언트로 보냄
				String inBasket[] = new String[7];
				inBasket[4] = loginId;
				inBasket[5] = "shop";
				inBasket[6] = "orderB";
				
				for(int i = 0; i < tableBasket.getRowCount(); i++) {
					for(int j = 0; j < tableBasket.getColumnCount(); j++) {
							inBasket[j] = (String) tableBasket.getValueAt(i, j);
						
					}
					c.send(inBasket);
				}
				
				//텍스트필드 초기화
				for (int i = 0; i < indataBasket.length; i++) {
					indataBasket[i].setText("");
				}
			}
		});
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	


	
}
