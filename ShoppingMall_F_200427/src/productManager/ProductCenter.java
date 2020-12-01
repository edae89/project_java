package productManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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


public class ProductCenter extends JFrame {
	
	JPanel cP, eP, sP;
	JLabel productLb, priceLb, eaLb;	
	JTextField productTF, priceTF, eaTF;
	JButton addBtn, modBtn, delBtn;
	DefaultListModel<String> lm = new DefaultListModel<String>();
	String header[] = {  "상품명", "가격", "수량" };
	DefaultTableModel tableModel = new DefaultTableModel(null, header);
	JTable table = new JTable(tableModel);
	JScrollPane tableScroll = new JScrollPane(table);
	ArrayList<String[]> initListProduct=null;
	
	JTextField[] indata = new JTextField[3];
	
	int modIntRow = -1;
	
	ClientChat c = null;
	String loginId = null;
	Color green = new Color(000,102,000);
	Color red = new Color(204,000,000);
	Color beige = new Color(204,153,102);
	Color brown = new Color(153,102,051);
	
	public ProductCenter(ClientChat c, String inputId){
		super("관리자 메뉴_<물품 관리>");
		this.c = c;
		this.loginId = inputId;
		this.setLayout(new BorderLayout());
		this.setBounds(100, 100, 650, 550);
		tableSetting();
		initProduct();
		createC();	
		createE(); 
		
		this.addWindowListener(new EventHandlerC(c, inputId));
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public void initProduct() {// 변경할 사항 : 받은 배열을 반복문으로 initList에 add하고 그 뒤에 테이블에 세팅
		initListProduct = c.getInitListProduct();
		for(int i=0 ; i < initListProduct.size(); i++) {
			tableModel.addRow(initListProduct.get(i));
		}
	}
	
//	public void addToList(String[] in) {// 변경할 사항 : 받은 배열을  initList에 add하고 그 뒤에 테이블에 세팅
//		initListProduct.add(in);
//	}
	
	
	public void delTableRow(int row) {
		tableModel.removeRow(row);
	}
	

	
	
	public void tableSetting() {	
		table.setRowMargin(0);
		table.getColumnModel().setColumnMargin(0);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setShowVerticalLines(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//마우스 클릭 줄값 텍스트필드에 세팅
		table.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1) {
					int column = table.columnAtPoint(e.getPoint());
					int row = table.rowAtPoint(e.getPoint());
					table.changeSelection(row, column, false, false);
					modIntRow = table.getSelectedRow();
					for (int i = 0; i < indata.length; i++) {
						indata[i].setText((String) table.getValueAt(modIntRow, i));
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
		TableColumnModel tc = table.getColumnModel();
		for (int i = 0; i < tc.getColumnCount(); i++) {
			tc.getColumn(i).setCellRenderer(ts);
		}

	}
	
	

	private void createC() {
		cP = new JPanel();
		cP.setLayout(new BorderLayout());
		cP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		cP.setBackground(brown);
		this.add(cP, "Center");
		
		//JTable product 세팅
		JPanel cP_n = new JPanel();
		cP_n.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		cP_n.setBackground(brown);
		cP.add(cP_n, "North");
		cP_n.add(tableScroll);
		tableScroll.setPreferredSize(new Dimension(370,400));
		
		
		//JTextField 세팅
		JPanel cP_s = new JPanel();
		cP_s.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
		cP_s.setBackground(brown);
		cP.add(cP_s, "South");
		cP_s.setLayout(new BoxLayout(cP_s, BoxLayout.X_AXIS));
		for (int i = 0; i < indata.length; i++) {
			JTextField dataProduct = new JTextField();
			indata[i] = dataProduct;
			cP_s.add(indata[i]);
			dataProduct.setPreferredSize(new Dimension(100,25));
			
			
		}
		
	}
	
	
	
	private void createE() {
		eP = new JPanel();
		eP.setLayout(new BorderLayout());
		eP.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		eP.setBackground(brown);
		this.add(eP, "East");
		
		
		JPanel eP_c = new JPanel();
		eP_c.setLayout(new BorderLayout());
		eP_c.setBackground(brown);
		eP.add(eP_c, "Center");
		
		JPanel eP_c_n = new JPanel();
		eP_c.add("North", eP_c_n);
		addBtn = new JButton("상품 추가");
		addBtn.setBackground(brown);
		addBtn.setForeground(Color.white);
		eP_c_n.setBorder(BorderFactory.createEmptyBorder(2, 5, 10, 5));
		eP_c_n.setBackground(brown);
		addBtn.setPreferredSize(new Dimension(100,30));
		eP_c_n.add(addBtn);
		
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String in[] = new String[7];
				for (int i = 0; i < indata.length; i++) {
					in[i] = indata[i].getText();
				}
				
				for (int i = 0; i < indata.length; i++) {
					indata[i].setText("");
				}
				
				tableModel.addRow(in);		//프로덕트JTable에 in[]정보 추가
				in[5] = "product";
				in[6] = "addB";
				c.send(in);		//ClientChat에 DTO를 생성하여 추가로 저장할 객체 보냄
				
				
				
			}
		});
		
		
		
		
		
		JPanel eP_c_c = new JPanel();
		eP_c.add("Center", eP_c_c);
		modBtn = new JButton("상품 수정");
		modBtn.setBackground(brown);
		modBtn.setForeground(Color.white);
		eP_c_c.setBorder(BorderFactory.createEmptyBorder(2, 5, 10, 5));
		eP_c_c.setBackground(brown);
		modBtn.setPreferredSize(new Dimension(100,30));
		eP_c_c.add(modBtn);
		
		modBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String in[] = new String[7];
				for (int i = 0; i < indata.length; i++) {
					in[i] = indata[i].getText();
				}
				
				delTableRow(modIntRow);					//프로덕트JTable 삭제
				tableModel.insertRow(modIntRow, in);	//프로덕트JTable 줄 끼워넣고 in[]정보 추가(결과적으로 수정)
				in[5] = "product";
				in[6] = "modB";
				c.send(in);		//ClientChat에 DTO를 생성하여 수정할 객체 보냄
				
				for (int i = 0; i < indata.length; i++) {
					indata[i].setText("");
				}
				
				modIntRow = -1;
			}

			
		});
		
		
		JPanel eP_c_s = new JPanel();
		eP_c.add("South", eP_c_s);
		delBtn = new JButton("상품 삭제");
		delBtn.setBackground(brown);
		delBtn.setForeground(Color.white);
		eP_c_s.setBorder(BorderFactory.createEmptyBorder(2, 5, 100, 5));
		eP_c_s.setBackground(brown);
		delBtn.setPreferredSize(new Dimension(100,30));
		eP_c_s.add(delBtn);
		
		delBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					
				String in[] = new String[7];
				
				for (int i = 0; i < indata.length; i++) {
					in[i] = indata[i].getText();
					indata[i].setText("");
				}
				if(modIntRow != -1) {
					in[5] = "product";
					in[6] = "delB";
					delTableRow(table.getSelectedRow());		//프로덕트JTable 줄 삭제
					c.send(in);		//ClientChat에 DTO를 생성하여 지울 객체 보냄
					
				}
				modIntRow= -1;
				
				
			}
		});
		
		
		
		JPanel eP_s = new JPanel();
		eP_s.setBorder(BorderFactory.createEmptyBorder(100, 20, 70, 20));
		eP_s.setBackground(brown);
		eP.add(eP_s, "South");
		
		JButton finBtn = new JButton("닫기");
		finBtn.setBackground(brown);
		finBtn.setForeground(Color.white);
		finBtn.setPreferredSize(new Dimension(100,50));
		eP_s.add(finBtn);
		
		finBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		
	}

	
}
