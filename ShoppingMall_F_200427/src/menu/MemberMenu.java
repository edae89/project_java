package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import board.BoardCenter;
import client.ClientChat;
import login.Login;
import shop.ShopCenter;

public class MemberMenu extends JFrame {
	JPanel sP, cP, nP;
	JButton productMGEBtn, memberMGEBtn;
	ClientChat c = null;
	String inputId = null;
	
	Color green = new Color(000,102,000);
	Color red = new Color(204,000,000);
	Color pink = new Color(204,153,153);
	
	public MemberMenu(ClientChat c, String inputId, Login l){
		super("<회원 메뉴>");
		this.c = c;
		this.inputId = inputId;
		this.setLayout(new BorderLayout());
		this.setBounds(80, 80, 200, 200);
		createN();
		createC();
		createS();
		this.setBackground(pink);
		System.out.println("메뉴에서 아이디 :"+inputId);
		this.addWindowListener(new EventHandlerM(c, inputId, l));
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	


	private void createN() {
		nP = new JPanel();
		nP.setBorder(BorderFactory.createEmptyBorder(30, 5, 10, 5));
		nP.setBackground(pink);
		this.add(nP, "North");
		productMGEBtn = new JButton("쇼핑하기");
		productMGEBtn.setBackground(pink);
		productMGEBtn.setForeground(Color.white);
		nP.add(productMGEBtn);
		productMGEBtn.setPreferredSize(new Dimension(100,25));
		
		productMGEBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ShopCenter(c, inputId);
				
			}
		});
		
	}
	
	private void createC() {
		cP = new JPanel();
		cP.setBackground(pink);
		this.add(nP, "Center");
	}
	
	private void createS() {
		sP = new JPanel();
		sP.setBorder(BorderFactory.createEmptyBorder(10, 5, 30, 5));
		sP.setBackground(pink);
		this.add(sP, "South");
		memberMGEBtn = new JButton("게시판");
		memberMGEBtn.setBackground(pink);
		memberMGEBtn.setForeground(Color.white);
		sP.add(memberMGEBtn);
		memberMGEBtn.setPreferredSize(new Dimension(100,25));
		
		memberMGEBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new BoardCenter(c, inputId);
				
			}
		});
		
	}

	
}
