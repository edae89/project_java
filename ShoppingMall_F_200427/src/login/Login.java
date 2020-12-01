package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.ClientChat;

public class Login extends JFrame {
	ImageIcon icon;
	
	JPanel cP, eP;
	JLabel idLabel, pwLabel, loginLabel;	
	
	ArrayList<String[]> initList=new ArrayList<>();
	String inLogin[] = new String[2];	//입력 id,pw를 넣을 배열
	JTextField idField = new JTextField(10);
	JTextField pwField = new JTextField(10);
	
	ClientChat c = null;
	Color green = new Color(000,102,000);
	Color red = new Color(204,000,000);
	Color beige = new Color(204,153,102);
	Color brown = new Color(153,102,051);
	
	public Login(ClientChat c){
		super("쇼핑몰 QUCCI ver1.0");
		this.c = c;
		this.setLayout(new BorderLayout());
		this.setBounds(100, 100, 800, 550);
		createC();
		createE();
		this.addWindowListener(new EventHandlerL(c, this));
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void disposeLogin() {
		this.dispose();
	}
	
	public void createC() {
		cP = new JPanel();
		cP.setLayout(new BorderLayout());
		cP.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		cP.setBackground(beige);
		this.add(cP, "Center");
		
		JPanel cP_n = new JPanel();
		cP_n.setPreferredSize(new Dimension(250,160));
		cP_n.setBackground(green);
		cP.add(cP_n, "North");
	
		JPanel cP_c = new JPanel();
		cP_c.setPreferredSize(new Dimension(250,160));
		cP_c.setBackground(red);
		cP.add(cP_c, "Center");
		
		JPanel cP_s = new JPanel();
		cP_s.setPreferredSize(new Dimension(250,160));
		cP_s.setBackground(green);
		cP.add(cP_s, "South");
		
		
	}
	
	
	
	//오른쪽 패널
	public void createE() {
		eP = new JPanel();
		eP.setLayout(new BorderLayout());
		eP.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		eP.setBackground(beige);
		//오른쪽,위
		JPanel eP_n = new JPanel();
		eP_n.setLayout(new BorderLayout());
		eP_n.setBorder(BorderFactory.createEmptyBorder(20, 5, 35, 5));
		eP_n.setBackground(beige);
		eP.add(eP_n, "North");
		
		JPanel eP_n_n = new JPanel();
		eP_n_n.setLayout(new BorderLayout());
		eP_n_n.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		eP_n_n.setBackground(beige);
		eP_n.add("North", eP_n_n);
		
		//로그인 버튼
		loginLabel = new JLabel("QUCCI ");
		loginLabel.setForeground(brown);
		eP_n_n.add("Center", loginLabel);
		JButton loginBtn = new JButton("로그인");
		loginBtn.setBackground(Color.BLACK);
		loginBtn.setForeground(Color.white);
		loginBtn.setFocusPainted(false);
		loginBtn.setContentAreaFilled(false);
		eP_n_n.add("East", loginBtn);
		
		loginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				inLogin[0] = idField.getText();
				inLogin[1] = pwField.getText();
				String inputId = inLogin[0];
				String inputPw = inLogin[1];
				System.out.println("Login창에서 확인:"+inputId);
				c.login(inputId, inputPw);
				
			}
		});
		
		
		//ID,PW 라벨, 텍스트 필드
		JPanel eP_n_c = new JPanel();
		eP_n_c.setLayout(new BorderLayout());
		eP_n_c.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
		eP_n_c.setBackground(beige);
		eP_n.add("Center", eP_n_c);
		
		idLabel = new JLabel("ID    ");
		eP_n_c.add("West", idLabel);
		idLabel.setForeground(Color.white);
		idField = new JTextField(10);
		eP_n_c.add("Center", idField);
		idField.setPreferredSize(new Dimension(100,25));
		
		JPanel eP_n_s = new JPanel();
		eP_n_s.setLayout(new BorderLayout());
		eP_n_s.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		eP_n_s.setBackground(beige);
		eP_n.add("South", eP_n_s);
		
		pwLabel = new JLabel("PW ");
		pwLabel.setForeground(Color.white);
		eP_n_s.add("West", pwLabel);
		pwField = new JTextField(10);
		eP_n_s.add("Center",pwField);
		pwField.setPreferredSize(new Dimension(100,25));
		
		
		
		//오른쪽,가운데
		icon = new ImageIcon("C:\\Users\\duman\\Desktop\\qucci.PNG");
		JPanel eP_c = new JPanel();
		eP_c.setLayout(new BorderLayout());
		eP_c.setPreferredSize(new Dimension(100,160));
		eP_c.setBackground(beige);
		eP_c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		eP.add(eP_c, "Center");

		JLabel qucciLB = new JLabel(icon);
		qucciLB.setHorizontalAlignment(0);
		eP_c.add(qucciLB);
		
		
		
//		JPanel eP_c_n = new JPanel();
//		eP_c_n.setLayout(new BorderLayout());
//		eP_c_n.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
//		
//		JPanel eP_c_c = new JPanel();
//		eP_c_c.setLayout(new BorderLayout());
//		eP_c_c.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
//		
//		JPanel eP_c_s = new JPanel();
//		eP_c_s.setLayout(new BorderLayout());
//		eP_c_s.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		
	
		//오른쪽,아래
		JPanel eP_s = new JPanel();
		eP_s.setLayout(new BorderLayout());
		eP_s.setBorder(BorderFactory.createEmptyBorder(37, 5, 100, 5));
		eP_s.setBackground(beige);
		eP.add(eP_s, "South");
		
		//회원가입 버튼
		JPanel eP_s_c = new JPanel();
		eP_s_c.setLayout(new BorderLayout());
		eP_s_c.setBackground(beige);
		eP_s.add("Center", eP_s_c);
		JButton joinBtn = new JButton("회원 가입");
		joinBtn.setBackground(beige);
		joinBtn.setForeground(Color.white);
		joinBtn.setFocusPainted(false);
		joinBtn.setContentAreaFilled(false);
		eP_s_c.add("Center", joinBtn);
		
		this.add(eP, "East");
		
	}
	
}
