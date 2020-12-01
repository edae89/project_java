package join;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import board.BoardDTO;
import login.Login;
import memberManager.MemberDAO;
import memberManager.MemberDTO;

public class MemberJoin extends JFrame {
	String header[] = { "ID", "PW","Lv", "Point" };
	ArrayList<String[]> mlist = new ArrayList<>();
	DefaultTableModel tableModel = new DefaultTableModel(null, header);
	JPanel nP, cP, sP, wP;
	JLabel idLabel, pwLabel, LvLaber, PointLaber;
	JTextField idField, pwdField, LvField, PointField;
	
	JTextField[] indata = new JTextField[4];
	MemberDAO sqlDAO = MemberDAO.getInstance();
	JTabbedPane tabPane = new JTabbedPane();
	JTable table = new JTable(tableModel);
	JScrollPane tableScroll = new JScrollPane(table);
	
	JPanel tab_1 = new JPanel();
	JPanel tab_1_inputP = new JPanel();
	JPanel tab_2 = new JPanel();
	
	public MemberJoin() {
			Dimension size = new Dimension(600, 400);
			createN();
			createC();
			createS();
			createW();
			createInputP();
			init();
			createInputP();
			createTabbedP();

			// init(); // frame이 실행되고 모든 컴포넌트가 생성되면 초기 데이터 값을 가져오는 메서드

			this.setLocation(300, 300);
			this.setSize(size);
			this.add(tabPane);
			// this.setPreferredSize(size);

			this.setVisible(true);
			this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		}
	

	private void createInputP() {
		idField.setLayout(new BoxLayout(idField,BoxLayout.X_AXIS));
		for (int i = 0; i < indata.length; i++) {
			idField.add(indata[i] = new JTextField());
		}
		JButton joinBtn = new JButton("회원가입");
		idField.add(joinBtn);
		joinBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[4];
				for (int i = 0; i < indata.length; i++) {
					in[i] = indata[i].getText();
					indata[i].setText("");
				}
				tableModel.addRow(in);
				saveToDB(in);
			}
		});
	}
		
	public void createTabbedP() {
		tab_1.setLayout(new BorderLayout());
		tab_1.add(tableScroll, "Center");
		tab_1.add(tab_1_inputP, "South");
		tabPane.add("Data", tab_1);

	}


	public void init() {
		mlist = sqlDAO.getList();
		for (int i = 0; i < mlist.size(); i++) {
			pwdField.add(nP, mlist.get(i));
		}
	}

	private void createW() {
		wP = new JPanel();
		LvLaber = new JLabel("Lv");
		LvField = new JTextField(15);
		wP.add(LvLaber);
		wP.add(LvField);
	}

	public void createC() {
		cP = new JPanel();
		pwLabel = new JLabel("암    호");
		pwdField = new JTextField(15);
		cP.add(pwLabel);
		cP.add(pwdField);
	}

	public void createN() {
		nP = new JPanel();
		idLabel = new JLabel("아이디");
		nP.add(idLabel);
		idField = new JTextField(15);
		nP.add(idField);
	}

	public void createS() {
		sP = new JPanel();
		LvLaber = new JLabel("Lv");
		sP.add(LvLaber);
		this.add(wP, "west");
		this.add(nP, "North");
		this.add(cP, "Center");
		this.add(sP, "South");

		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);
//		new Login();
	}

	public void saveToDB(String[] in) {
		MemberDTO newDTO = new MemberDTO();
		newDTO.setId(in[0]);
		newDTO.setPw(in[1]);
		newDTO.setLv(in[2]);
		newDTO.setPoint(in[3]);
		sqlDAO.insertmember(newDTO);
	}

	public static void main(String[] args) {
		new MemberJoin();
	}
}