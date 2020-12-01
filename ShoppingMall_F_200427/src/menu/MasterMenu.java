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

import client.ClientChat;
import login.Login;
import memberManager.MemberCenter;
import productManager.ProductCenter;

public class MasterMenu extends JFrame {
	JPanel nP, cP, sP;
	JButton productMGEBtn, memberMGEBtn;
	ClientChat c = null;
	String inputId = null;
	Login l = null;
	
	Color green = new Color(000,102,000);
	Color red = new Color(204,000,000);
	Color beige = new Color(204,153,102);
	Color brown = new Color(153,102,051);
	Color pink = new Color(204,153,153);
	
	public MasterMenu(ClientChat c, String inputId, Login l){
		super("<관리자 메뉴>");
		this.c = c;
		this.inputId = inputId;
		this.l = l;
		this.setLayout(new BorderLayout());
		this.setBounds(80, 80, 200, 200);
		this.setBackground(pink);
		createN();
		createC();
		createS();
		
		this.addWindowListener(new EventHandlerM(c, inputId, l));
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	private void createC() {
		cP = new JPanel();
		cP.setBackground(pink);
		this.add(cP, "Center");
	}


	private void createN() {
		nP = new JPanel();
		nP.setBackground(pink);
		nP.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 5));
		this.add(nP, "North");
		productMGEBtn = new JButton("물품 관리");
		productMGEBtn.setBackground(pink);
		productMGEBtn.setForeground(Color.white);
		nP.add(productMGEBtn);
		productMGEBtn.setPreferredSize(new Dimension(100,25));
		productMGEBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ProductCenter(c, inputId);
				
			}
		});
		
	}
	
	
	private void createS() {
		sP = new JPanel();
		sP.setBackground(pink);
		sP.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 5));
		this.add(sP, "South");
		memberMGEBtn = new JButton("회원 관리");
		memberMGEBtn.setBackground(pink);
		memberMGEBtn.setForeground(Color.white);
		sP.add(memberMGEBtn);
		memberMGEBtn.setPreferredSize(new Dimension(100,25));
		
		memberMGEBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MemberCenter(c, inputId);
				
			}
		});
		
	}

	
	
}
