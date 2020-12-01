package client;

import java.net.Socket;

public class ClientMain {

	public static void main(String[] args) throws Exception {
		Socket withServer = null;
		
		withServer = new Socket("192.168.219.184",9999);
		new ClientChat(withServer);
		
		
	}

}