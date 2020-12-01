package login;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

import client.ClientChat;
import client.ClientMain;



public class EventHandlerL extends JFrame implements WindowListener{
	private Socket withClient;
	private ClientChat c = null;
	private Login l;
	
	public EventHandlerL(ClientChat c, Login l) {
		this.c = c;
		this.l = l;
	}
	

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
//		System.exit(0);
		
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
