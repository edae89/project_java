package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.ClientChat;
import login.EventHandlerL;

public class Main extends JFrame{
	static JFrame a;
	static JPanel cP, eP;
	static JLabel idLabel, pwLabel, loginLabel;	
	
	ArrayList<String[]> initList=new ArrayList<>();
	String inLogin[] = new String[2];	//입력 id,pw를 넣을 배열
	static JTextField idField = new JTextField(10);
	static JTextField pwField = new JTextField(10);
	
	Color green = new Color(000,102,000);
	Color red = new Color(204,000,000);
		
	
	public static void main(String[] args)  {
		
		a.setLayout(new BorderLayout());
		a.setBounds(100, 100, 800, 550);
		cP = new JPanel();
		a.add(cP, "Center");
//		a.addWindowListener(new EventHandlerL(c, this));
		a.setDefaultCloseOperation(a.DISPOSE_ON_CLOSE);
		a.setVisible(true);
		
		
		//오른쪽 패널
		eP = new JPanel();
		eP.setLayout(new BorderLayout());
		eP.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		//오른쪽,위
		JPanel eP_n = new JPanel();
		eP_n.setLayout(new BorderLayout());
		eP_n.setBorder(BorderFactory.createEmptyBorder(20, 5, 35, 5));
		eP.add(eP_n, "North");
		
		JPanel eP_n_n = new JPanel();
		eP_n_n.setLayout(new BorderLayout());
		eP_n_n.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		eP_n.add("North", eP_n_n);
		
		//로그인 버튼
		loginLabel = new JLabel("회원 ");
		eP_n_n.add("Center", loginLabel);
		JButton loginBtn = new JButton("로그인");
		eP_n_n.add("East", loginBtn);
		
		
		
		
		//ID,PW 라벨, 텍스트 필드
		JPanel eP_n_c = new JPanel();
		eP_n_c.setLayout(new BorderLayout());
		eP_n_c.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		eP_n.add("Center", eP_n_c);
		
		idLabel = new JLabel("ID    ");
		eP_n_c.add("West", idLabel);
		idField = new JTextField(10);
		eP_n_c.add("Center", idField);
		idField.setPreferredSize(new Dimension(100,25));
		
		JPanel eP_n_s = new JPanel();
		eP_n_s.setLayout(new BorderLayout());
		eP_n_s.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		eP_n.add("South", eP_n_s);
		
		pwLabel = new JLabel("PW ");
		eP_n_s.add("West", pwLabel);
		pwField = new JTextField(10);
		eP_n_s.add("Center",pwField);
		pwField.setPreferredSize(new Dimension(100,25));
		
		
		
		//오른쪽,가운데
		JPanel eP_c = new JPanel();
		eP_c.setLayout(new BorderLayout());
		eP_c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		eP.add(eP_c, "Center");
		
		JPanel eP_c_n = new JPanel();
		eP_c_n.setLayout(new BorderLayout());
		eP_c_n.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		
		JPanel eP_c_c = new JPanel();
		eP_c_c.setLayout(new BorderLayout());
		eP_c_c.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		
		JPanel eP_c_s = new JPanel();
		eP_c_s.setLayout(new BorderLayout());
		eP_c_s.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		
		
		//오른쪽,아래
		JPanel eP_s = new JPanel();
		eP_s.setLayout(new BorderLayout());
		eP_s.setBorder(BorderFactory.createEmptyBorder(37, 5, 100, 5));
		eP.add(eP_s, "South");
		
		//회원가입 버튼
		JPanel eP_s_c = new JPanel();
		eP_s_c.setLayout(new BorderLayout());
		eP_s.add("Center", eP_s_c);
		JButton joinBtn = new JButton("회원 가입");
		eP_s_c.add("Center", joinBtn);
		
		a.add(eP, "East");
	}
	

}