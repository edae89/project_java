package menu;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import client.ClientChat;
import login.Login;

public class EventHandlerM extends JFrame implements WindowListener{
	private ClientChat c = null;
	private Login l = null;
	private String inputId = null;
	

	public EventHandlerM(ClientChat c, String inputId, Login l) {
		this.l = l;
		this.inputId = inputId;
		this.c = c;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		l.dispose();
		String[] in = new String[7];
		in[5] = "logout";
		c.send(in);
//		c.createLogin();//<<로그아웃처리 일단 보류, 클라에 새로 만든 멀티스레드 때문에 문제 생김
	}
	

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		l.setVisible(false);
	}

}
