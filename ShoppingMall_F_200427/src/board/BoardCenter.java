package board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import client.ClientChat;
import client.EventHandlerC;

public class BoardCenter extends JFrame {
	String header[] = { "No", "아이디", "상품명", "평가" };
	ArrayList<String[]> blist = new ArrayList<>();
	JTabbedPane tabPane = new JTabbedPane();

	DefaultTableModel tableModel = new DefaultTableModel(null, header);

	JTable table = new JTable(tableModel);
	JScrollPane tableScroll = new JScrollPane(table);

	JPanel tab_1 = new JPanel();
	JPanel tab_1_inputP = new JPanel();
	JPanel tab_2 = new JPanel();

	JTextField[] indata = new JTextField[4];

	JPopupMenu popup;
	JMenuItem m_del, m_mod;

	int modIntRow = -1;

	BoardDAO sqlDAO = BoardDAO.getInstance();
	
	ClientChat c = null;
	String loginId = null;
	ArrayList<String[]> initListBoard=null;
	
	Color green = new Color(000,102,000);
	Color red = new Color(204,000,000);
	Color beige = new Color(204,153,102);
	Color brown = new Color(153,102,051);
	Color pink = new Color(204,153,153);

	public BoardCenter(ClientChat c, String inputId) {
		super("회원 메뉴_<게시판>");
		this.c = c;
		this.loginId = inputId;
		this.setBounds(100, 100, 800, 600);
		createInputP();
		createTabbedP();
		init(); // frame이 실행되고 모든 컴포넌트가 생성되면 초기 데이터 값을 가져오는 메서드
		this.addWindowListener(new EventHandlerC(c, inputId));
		this.add(tabPane);
		this.setVisible(true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
	}

	
	public void init() {
		initListBoard = c.getInitListBoard();
		for(int i=0 ; i < initListBoard.size(); i++) {
			tableModel.addRow(initListBoard.get(i));
		}
	}
	

	public void delTableRow(int row) {
		tableModel.removeRow(row);
	}

	public void createInputP() {
		tab_1_inputP.setLayout(new BoxLayout(tab_1_inputP, BoxLayout.X_AXIS));
		for (int i = 1; i < indata.length; i++) {
			tab_1_inputP.add(indata[i] = new JTextField());
			indata[i].setBackground(green);
			if(i==1) {
				indata[i].setText(loginId);
				indata[i].setEnabled(false);//텍스트필드 비활성화(아이디)
			}else if(i==2){
				indata[i].setText("상품명");
				
			}else if(i==3){
				indata[i].setText("구매 후기");
				
			}
			
		}

			
			
		JButton addB = new JButton("남기기");
		tab_1_inputP.add(addB);
		addB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[7];
				for (int i = 1; i < indata.length; i++) {
					in[i] = indata[i].getText();
					indata[i].setText("");
				}
				in[5] = "board";
				in[6] = "addB";
				c.send(in);
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				tableModel.addRow(in);
				
				
			}
		});
	}

	public void createTabbedP() {
		tab_1.setLayout(new BorderLayout());
		tab_1.add(tableScroll, "Center");
		tab_1.add(tab_1_inputP, "South");
		tabPane.add("구매 후기", tab_1);

	}


	

}
