package server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

	public static void main(String[] args) throws Exception {
		ServerSocket serverS = null;
		Socket withClient = null;
		serverS = new ServerSocket();
		serverS.bind(new InetSocketAddress("192.168.219.184", 9999));
		ServerCenter sc = ServerCenter.getInstance();
		
		while(true){
			withClient = serverS.accept();
			ServerChat s = new ServerChat(withClient, sc);
			sc.addServerChat(s);
			s.start();
		}
	}

}
