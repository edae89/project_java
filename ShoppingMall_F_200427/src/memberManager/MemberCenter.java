package memberManager;

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
import javax.swing.JPopupMenu;
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




public class MemberCenter extends JFrame implements ActionListener{

	JPanel cP, sP;
	JLabel boardLb, memberLb, blackLb;	
	DefaultListModel<String> lm = new DefaultListModel<String>();
	
	String headerBoard[] = { "No", "아이디", "상품명", "평가" };
	DefaultTableModel tableModelBoard = new DefaultTableModel(null, headerBoard);
	JTable tableBoard = new JTable(tableModelBoard);
	JScrollPane tableScrollBoard = new JScrollPane(tableBoard);
	
	String headerMember[] = { "아이디", "비밀번호", "레벨", "포인트", "블랙메모" };
	DefaultTableModel tableModelMember = new DefaultTableModel(null, headerMember);
	JTable tableMember = new JTable(tableModelMember);
	JScrollPane tableScrollMember = new JScrollPane(tableMember);
	
	String headerBlack[] = { "아이디", "블랙메모" };
	DefaultTableModel tableModelBlack = new DefaultTableModel(null, headerBlack);
	JTable tableBlack = new JTable(tableModelBlack);
	JScrollPane tableScrollBlack = new JScrollPane(tableBlack);
	
	ArrayList<String[]> initListBoard=null;
	ArrayList<String[]> initListMember=null;
	ArrayList<String[]> initListBlack=null;
	
	JTextField[] indataBoard = new JTextField[4];
	JTextField[] indataMember = new JTextField[5];
	JTextField[] indataBlack = new JTextField[2];
	
	int modIntRowMember = -1;			//member JTable 줄 값
	int modIntRowBlack = -1;		//black JTable 줄 값
	int modIntRowBoard = -1;
	
//	MemberDAO sqlDAO = MemberDAO.getInstance();
	ClientChat c = null;
	String loginId = null;
	
	Color green = new Color(000,102,000);
	Color red = new Color(204,000,000);
	
	public MemberCenter(ClientChat c, String inputId){
		super("관리자 메뉴_<회원 및 게시판 관리>");
		this.c = c;
		this.loginId = inputId;
		this.setBounds(100, 100, 1100, 700);
		tableBoardSetting();
		tableMemberSetting();
		tableBlackSetting();
		initAll();
		createC();
		createS();
		this.addWindowListener(new EventHandlerC(c, inputId));
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	//member JTable 줄 삭제
	public void delTableMemberRow(int row) {
		tableModelMember.removeRow(row);
	}
	//black JTable 줄 삭제
	public void delTableBlackRow(int row) {
		tableModelBlack.removeRow(row);
	}
	
	//board JTable 줄 삭제
	public void delTableBoardRow(int row) {
		tableModelBoard.removeRow(row);
	}
	
	
//	
	
	
	public void initAll() {	//모든 DB 정보 불러오기
		initBoard();
		initMember();
		initBlack();
	}
	
	//board DB 정보 불러오기
	public void initBoard() {
		initListBoard = c.getInitListBoard();
		for(int i=0 ; i < initListBoard.size(); i++) {
			tableModelBoard.addRow(initListBoard.get(i));
		}
		
	}
	
	//member DB 정보 불러오기
	public void initMember() {
		initListMember = c.getInitListMember();
		for(int i=0 ; i < initListMember.size(); i++) {
			tableModelMember.addRow(initListMember.get(i));
		}
		
	}

	//black DB 정보 불러오기
	public void initBlack() {
		initListBlack = c.getInitListBlack();
		for(int i=0 ; i < initListBlack.size(); i++) {
			tableModelBlack.addRow(initListBlack.get(i));
		}
	
	}
	
	
	
	public void tableMemberSetting() {	
		tableMember.setRowMargin(0);
		tableMember.getColumnModel().setColumnMargin(0);
		tableMember.getTableHeader().setReorderingAllowed(false);
		tableMember.getTableHeader().setResizingAllowed(false);

		tableMember.setShowVerticalLines(false);
		tableMember.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableMember.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//클릭 시, member JTable 줄값을 텍스트필드에 세팅
				if (e.getButton() == 1) {
					int column = tableMember.columnAtPoint(e.getPoint());
					int row = tableMember.rowAtPoint(e.getPoint());
					tableMember.changeSelection(row, column, false, false);
					modIntRowMember = tableMember.getSelectedRow();
					for (int i = 0; i < indataMember.length; i++) {
						indataMember[i].setText((String) tableMember.getValueAt(modIntRowMember, i));
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
		TableColumnModel tc = tableMember.getColumnModel();
		for (int i = 0; i < tc.getColumnCount(); i++) {
			tc.getColumn(i).setCellRenderer(ts);
		}

	}
	
	
	
	public void tableBlackSetting() {	
		tableBlack.setRowMargin(0);
		tableBlack.getColumnModel().setColumnMargin(0);
		tableBlack.getTableHeader().setReorderingAllowed(false);
		tableBlack.getTableHeader().setResizingAllowed(false);

		tableBlack.setShowVerticalLines(false);
		tableBlack.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableBlack.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//클릭 시, black JTable 줄값을 텍스트필드에 세팅
				if (e.getButton() == 1) {
					int column = tableBlack.columnAtPoint(e.getPoint());
					int row = tableBlack.rowAtPoint(e.getPoint());
					tableBlack.changeSelection(row, column, false, false);
					modIntRowBlack = tableBlack.getSelectedRow();
					for (int i = 0; i < indataBlack.length; i++) {
						indataBlack[i].setText((String) tableBlack.getValueAt(modIntRowBlack, i));
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
		TableColumnModel tc = tableBlack.getColumnModel();
		for (int i = 0; i < tc.getColumnCount(); i++) {
			tc.getColumn(i).setCellRenderer(ts);
		}

	}
	
	
	
	public void tableBoardSetting() {	
		tableBoard.setRowMargin(0);
		tableBoard.getColumnModel().setColumnMargin(0);
		tableBoard.getTableHeader().setReorderingAllowed(false);
		tableBoard.getTableHeader().setResizingAllowed(false);

		tableBoard.setShowVerticalLines(false);
		tableBoard.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableBoard.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//클릭 시, Board JTable 줄값 읽기
				if (e.getButton() == 1) {
					int column = tableBoard.columnAtPoint(e.getPoint());
					int row = tableBoard.rowAtPoint(e.getPoint());
					tableBoard.changeSelection(row, column, false, false);
					modIntRowBoard = tableBoard.getSelectedRow();
					for (int i = 0; i < indataBoard.length; i++) {
						indataBoard[i].setText((String) tableBoard.getValueAt(modIntRowBoard, i));
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
		TableColumnModel tc = tableBoard.getColumnModel();
		for (int i = 0; i < tc.getColumnCount(); i++) {
			tc.getColumn(i).setCellRenderer(ts);
		}

	}
	
	
	// cP : JTable(board, member, black)
	private void createC() {
		cP = new JPanel();
		cP.setLayout(new BorderLayout());
		this.add(cP, "Center");
		
		//JTable board 세팅
		JPanel cP_w = new JPanel();	
		cP_w.setLayout(new BorderLayout());
		cP.add(cP_w, "West");
		
		
		JPanel cP_w_n = new JPanel();
		cP_w.add(cP_w_n, "North");
		boardLb = new JLabel("게시판");
		cP_w_n.add(boardLb);
		boardLb.setPreferredSize(new Dimension(100,25));
		
		JPanel cP_w_c = new JPanel();
		cP_w.add(cP_w_c, "Center");
		cP_w_c.add(tableScrollBoard);	//게시판DB JTable에 읽어오기
		tableScrollBoard.setPreferredSize(new Dimension(330,540));
		
		
		//JTable member 세팅
		JPanel cP_c = new JPanel();	
		cP_c.setLayout(new BorderLayout());
		cP.add(cP_c, "Center");
		
		JPanel cP_c_n = new JPanel();
		cP_c.add(cP_c_n, "North");
		memberLb = new JLabel("일반 회원 명단");
		cP_c_n.add(memberLb);
		memberLb.setPreferredSize(new Dimension(100,25));
		
		JPanel cP_c_c = new JPanel();
		cP_c.add(cP_c_c, "Center");
		cP_c_c.add(tableScrollMember);	//회원DB JTable에 읽어오기
		tableScrollMember.setPreferredSize(new Dimension(450,540));
		
		
		//JTable black 세팅
		JPanel cP_e = new JPanel();	
		cP_e.setLayout(new BorderLayout());
		cP.add(cP_e, "East");
		
		JPanel cP_e_n = new JPanel();
		cP_e.add(cP_e_n, "North");
		blackLb = new JLabel("블랙 회원 명단");
		cP_e_n.add(blackLb);
		blackLb.setPreferredSize(new Dimension(100,25));
		
		
		JPanel cP_e_c = new JPanel();
		cP_e.add(cP_e_c, "Center");
		cP_e_c.add(tableScrollBlack);	// 블랙리스트DB JTable에 읽어오기
		tableScrollBlack.setPreferredSize(new Dimension(280,540));
		
		
	}
	
	
	// sP : JButton(해당 후기 삭제), JTextField(member,black), JButton(블랙리스트 추가, 블랙리스트 삭제)
	private void createS() {
		sP = new JPanel();
		sP.setLayout(new BorderLayout());
		this.add(sP, "South");
		
		JPanel sP_w = new JPanel();
		sP_w.setLayout(new BorderLayout());
		sP.add(sP_w, "West");
		
		JPanel sP_w_n = new JPanel();
		sP_w.add(sP_w_n, "North");
		sP_w_n.setLayout(new BoxLayout(sP_w_n, BoxLayout.X_AXIS));
		sP_w_n.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 0 , 10));
		for (int i = 0; i < indataBoard.length; i++) {
			JTextField dataBoard = new JTextField();
			indataBoard[i] = dataBoard;
			sP_w_n.add(indataBoard[i]);
			indataBoard[i].setEnabled(false);//텍스트필드 비활성화
		}
		
		
		JPanel sP_w_s = new JPanel();
		sP_w.add(sP_w_s, "South");
		JButton boardDel = new JButton("해당 후기 삭제");
		sP_w_s.add(boardDel);
		boardDel.setPreferredSize(new Dimension(170,25));
		sP_w_s.setBorder(BorderFactory.createEmptyBorder(0 , 150 , 0 , 0));
		boardDel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//no를 읽어서 db에서 삭제
				String inBoard[] = new String[7];
				for (int i = 0; i < indataBoard.length; i++) {
					inBoard[i] = indataBoard[i].getText();
				}
				if(modIntRowBoard != -1) {
					inBoard[5] = "memberboard";
					inBoard[6] = "delB";
					delTableBoardRow(modIntRowBoard);
					c.send(inBoard);
					
				}
				modIntRowBoard= -1;
			}
		});
		
		
		JPanel sP_c = new JPanel();
		sP.add(sP_c, "Center");
		sP_c.setLayout(new BorderLayout());
		
		JPanel sP_c_n = new JPanel();
		sP_c.add(sP_c_n, "North");
		sP_c_n.setLayout(new BoxLayout(sP_c_n, BoxLayout.X_AXIS));
		sP_c_n.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 0 , 10));
		for (int i = 0; i < indataMember.length; i++) {
			JTextField dataMember = new JTextField();
			indataMember[i] = dataMember;
			sP_c_n.add(indataMember[i]);
			//텍스트필드 비활성화 (블랙메모TF만 제외)
			if(i < 4) {
				indataMember[i].setEnabled(false);
			}
		}
		
		
		JPanel sP_c_s = new JPanel();
		sP_c.add(sP_c_s, "South");
		JButton blackAddBtn = new JButton("블랙리스트 추가");
		sP_c_s.add(blackAddBtn);
		blackAddBtn.setPreferredSize(new Dimension(170,25));
		sP_c_s.setBorder(BorderFactory.createEmptyBorder(0 , 260 , 0 , 0));
		
		blackAddBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String in[] = new String[5];
				String inBlack[] = new String[7];
				//DB에 넘겨줄 값 in[]에 담기
				for (int i = 0; i < indataMember.length; i++) {
					in[i] = indataMember[i].getText();
				}
				in[2]="b";	// JTable에 실시간으로 변동되도록 lv변경
				
				//블랙리스트에 띄울 값 inBlack[]에 담기
				for (int i = 0; i < indataMember.length; i++) {
					if(i==0) {
						inBlack[0] = indataMember[i].getText();
					}else if(i==4) {
						inBlack[1] = indataMember[i].getText();
					}
				}
				
				//텍스트필드indata[] 초기화
				for (int i = 0; i < indataMember.length; i++) {
					indataMember[i].setText("");
				}
				
				if(modIntRowMember != -1) {
					
					delTableMemberRow(modIntRowMember);					//멤버JTable 줄 삭제
					tableModelMember.insertRow(modIntRowMember, in);	//멤버JTable 줄 끼워넣고 in정보 추가(수정)
					tableModelBlack.addRow(inBlack);					//블랙JTable 줄에 inBlack정보 추가
					
					inBlack[5] = "membermember";
					inBlack[6] = "addB";
					c.send(inBlack);									//ClientChat에 DTO를 생성하여 추가로 저장할 객체 보냄
					
					modIntRowMember = -1;
					
				}
			}
		});
		
		
		
		
		JPanel sP_e = new JPanel();
		sP.add(sP_e, "East");
		sP_e.setLayout(new BorderLayout());
		
		JPanel sP_e_n = new JPanel();
		sP_e.add(sP_e_n, "North");
		sP_e_n.setLayout(new BoxLayout(sP_e_n, BoxLayout.X_AXIS));
		sP_c_n.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 0 , 10));
		for (int i = 0; i < indataBlack.length; i++) {
			JTextField dataBlack = new JTextField();
			indataBlack[i] = dataBlack;
			sP_e_n.add(indataBlack[i]);
			indataBlack[i].setEnabled(false);	//텍스트필드 비활성화
		}
		

		
		
		JPanel sP_e_s = new JPanel();
		sP_e.add(sP_e_s, "South");
		JButton blackDelBtn = new JButton("블랙리스트 취소");
		sP_e_s.add(blackDelBtn);
		blackDelBtn.setPreferredSize(new Dimension(170,25));
		sP_e_s.setBorder(BorderFactory.createEmptyBorder(0 ,100 , 0 , 0));
		
		blackDelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String inBlack[] = new String[7];
				for (int i = 0; i < indataBlack.length; i++) {
					inBlack[i] = indataBlack[i].getText();
				}
				
				if(modIntRowBlack != -1) {
					
					delTableBlackRow(modIntRowBlack);	//블랙JTable줄 삭제
					
					inBlack[5] = "membermember";
					inBlack[6] = "delB";
					c.send(inBlack);		//ClientChat에 DTO를 생성하여 추가로 저장할 객체 보냄
					
					//DB값 변경 기다리도록 슬립
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					tableModelMember.setNumRows(0);	//멤버JTable 초기화
					
					//이 부분 수정해야 함 -> 테이블 내에서만 변경시키다가 종료하면 저장하는 방식으로
					String[] in = new String[7];
					in[5] = "tablereset";
					c.clearList();//기존 리스트 클리어
					c.send(in);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					initMember();					//멤버JTable만 다시 불러오기
					//텍스트필드 초기화
					for (int i = 0; i < indataBlack.length; i++) {
						indataBlack[i].setText("");
					}
					
					modIntRowBlack = -1;
				}
			}
					
			
		});
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	

	
}
