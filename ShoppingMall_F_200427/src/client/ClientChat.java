package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import board.BoardDTO;
import login.Login;
import login.LoginErrorGUI;
import login.LoginErrorGUI2;
import menu.MasterMenu;
import menu.MemberMenu;
import productManager.ProductDTO;
import server.ServerCenter;
import server.ServerChat;
import shop.ShopDTO;
import login.MasterLoginGUI;
import login.MemberLoginGUI;
import memberManager.MemberBlackDTO;
import memberManager.MemberBoardDTO;
import memberManager.MemberMemberDTO;
import login.BlackLoginGUI;

public class ClientChat {
	private InputStream receiveMsg = null;
	private OutputStream sendMsg = null;
	private ObjectOutputStream sendOb = null;
	private ObjectInputStream oisC = null;
	private Socket withServer = null;
	private Login l;
	private String inputId = null;
	private String inputPw = null;
	private ServerCenter sc = null;
	
	ArrayList<String[]> initListProduct=new ArrayList<>();
	ArrayList<String[]> initListBasket=new ArrayList<>();
	ArrayList<String[]> initListBoard=new ArrayList<>();
	ArrayList<String[]> initListMember=new ArrayList<>();
	ArrayList<String[]> initListBlack=new ArrayList<>();
	
	public ArrayList<String[]> getInitListProduct() {
		return initListProduct;
	}
	public ArrayList<String[]> getInitListBasket() {
		return initListBasket;
	}
	public ArrayList<String[]> getInitListBoard() {
		return initListBoard;
	}
	public ArrayList<String[]> getInitListMember() {
		return initListMember;
	}
	public ArrayList<String[]> getInitListBlack() {
		return initListBlack;
	}
	
	public ClientChat(Socket withServer) {
		this.withServer = withServer;
		createLogin();
		
		
	}
	
	public void clearList() {
		System.out.println("테이블리셋");
		initListMember.clear();
		initListProduct.clear();
		initListBasket.clear();
		initListBoard.clear();
		initListBlack.clear();
	}
	
	
	
	public void createLogin() {
		l = new Login(this);
	}
	
	
	public String getLoginId() {
		return inputId;
	}
	
	//Login클래스의 텍스트필드 id,pw를 받아 서버에 전송
	public void login(String inputId, String inputPw) {
		try {
			this.inputId = inputId;
			String clientObject = inputId+"/"+inputPw;
			System.out.println(clientObject);
			
			if(inputId.equals("")||inputPw.equals("")) { // ID or PW 입력X
				l.dispose();
				createLogin();
			}else {
				System.out.println("login전");
				sendMsg = withServer.getOutputStream(); // ID or PW 빈값 X
				byte[] sendBuffer = new byte[100];
				sendBuffer = clientObject.getBytes();
				sendMsg.write(sendBuffer);
				System.out.println("login후");
				receiveLogin();
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//서버로부터 로그인 정보 결과 받기
	public void receiveLogin() {
		try {
			receiveMsg = withServer.getInputStream();//로그아웃 후, 다시 로그인 시 여기서 막힘, 서버에서 보낸 String이 주소값?으로 옴
			byte[] receiveBuffer = new byte[100];
			receiveMsg.read(receiveBuffer);
			String msg = new String(receiveBuffer);
			msg = msg.trim();
			System.out.println("메시지"+msg);
			
			
			//여기에서 msg내용에 따라 로그인 분류하여 객체 생성
			if(msg.equals("m")) {
				new MasterMenu(this, inputId, l);
				new MasterLoginGUI();
				
				receiveTableList();
				
			}else if(msg.equals("n")) {
				new MemberMenu(this, inputId, l);
				new MemberLoginGUI();
				
				receiveTableList();
				
			}else if(msg.equals("b")) {
				l.dispose();
				String[] in = new String[7];
				in[5] = "logout";
				send(in);
				createLogin();
				new BlackLoginGUI();
				
			}else if(msg.equals("x")) {
				l.dispose();
				String[] in = new String[7];
				in[5] = "nologin";
				send(in);
				createLogin();
				new LoginErrorGUI();
				
			}else if(msg.equals("xx")) {
				l.dispose();
				String[] in = new String[7];
				in[5] = "nologin";
				send(in);
				createLogin();
				new LoginErrorGUI2();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	public void receiveTableList() {
		//멀티스레드로?
		new Thread (new Runnable() {
			
			@Override
			public void run() {
				try {
					
					while(true) {
//						try {
//							Thread.sleep(1000);
//							System.out.println("스레드자는중");
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
						System.out.println("클라멀티스레드");
						oisC = new ObjectInputStream(withServer.getInputStream());
						Object objectDTO = oisC.readObject();
						String[] in = (String[]) objectDTO;
						
						if(in[5].equals("member")) {
							initListMember.add(in);
						}else if(in[5].equals("product")) {
							initListProduct.add(in);
						}else if(in[5].equals("basket")) {
							initListBasket.add(in);
						}else if(in[5].equals("board")) {
							initListBoard.add(in);
						}else if(in[5].equals("black")) {
							initListBlack.add(in);
						}
						
					}
					
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
					
				}
			}
			
		}).start();
		
		Thread.interrupted();	
		
	}	
	
	
	//Center에서 호출하는 메소드
	//정보를 ServerChat에 보내기
	public void send(String[] in) {
		try {
			sendOb = new ObjectOutputStream(withServer.getOutputStream());
			sendOb.writeObject(in);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	

	public void endChat() {
		try {
			withServer.close();
			receiveMsg.close();
			sendMsg.close();
			sendOb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
