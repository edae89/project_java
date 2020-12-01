package client;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class EventHandlerC extends JFrame implements WindowListener{
	private ClientChat c = null;
	
	private String inputId = null;
	

	public EventHandlerC(ClientChat c, String inputId) {
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
		System.out.println("테이블리셋");
		String[] in = new String[7];
		in[5] = "tablereset";
		c.clearList();//기존 리스트 클리어
		c.send(in);
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
	}

}
