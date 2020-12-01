package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import board.BoardDAO;
import board.BoardDTO;
import login.LoginDAO;
import memberManager.MemberBlackDTO;
import memberManager.MemberBoardDTO;
import memberManager.MemberDAO;
import memberManager.MemberMemberDTO;
import productManager.ProductDAO;
import productManager.ProductDTO;
import shop.ShopDAO;
import shop.ShopDTO;

public class ServerChat extends Thread {
	private static ServerChat ServerChatobj;
	private InputStream receiveMsg = null;
	private OutputStream sendMsg = null;
	private ObjectOutputStream oosS = null;	//ServerCenter 보낼 용도
	private ObjectInputStream receiveOb = null;
	private Socket withClient = null;
	private String loginObject = null;
	private ServerCenter sc = null;
	
	
	
	
	
	
	
	LoginDAO sqlDAO = LoginDAO.getInstance();
	ArrayList <MemberMemberDTO> memberDTOList = sqlDAO.selAll();
	
	ProductDAO sqlProductDAO = ProductDAO.getInstance();
	MemberDAO sqlMemberDAO = MemberDAO.getInstance();
	BoardDAO sqlBoardDAO = BoardDAO.getInstance();
	ShopDAO sqlShopDAO = ShopDAO.getInstance();
	LoginDAO sqlLoginDAO = LoginDAO.getInstance();
	private String loginId = null;

	
	ServerChat(Socket withClient, ServerCenter sc) {
		this.withClient = withClient;
		this.sc = sc;
		
	}
	
	
	@Override
	public void run() {
		idChk();// 한번만 실행
		sendInitAll();// 한번만 실행
		System.out.println("돌았냐");
		receive();//멀티스레드
		
	}
	
	
	private void sendInitAll() {
		sendInitMember();
		sendInitProduct();
		sendInitBasket();
		sendInitBoard();
		sendInitBlack();
	}


	// 구분값 : ArrayList 0번 배열의 0번 값
	public void sendInitMember() {
		System.out.println("인잇멤버");
		ArrayList<String[]> initListMember = new ArrayList<>();
		initListMember = sqlMemberDAO.getListMember();
		String[] in = new String[6];
		for(int i = 0; i <initListMember.size(); i++) {
			for(int j = 0; j < 4; j++) {
				in[j]= initListMember.get(i)[j];
			}
			in[5]="member";
			sendTableList(in);
		}
	}
	
	public void sendInitProduct() {
		System.out.println("인잇프로덕트");
		ArrayList<String[]> initListProduct = new ArrayList<>();
		initListProduct = sqlProductDAO.getList();
		String[] in = new String[6];
		for(int i = 0; i <initListProduct.size(); i++) {
			for(int j = 0; j < 3; j++) {
				in[j]= initListProduct.get(i)[j];
			}
			in[5]="product";
			sendTableList(in);
		}
	}
	
	public void sendInitBasket() {
		System.out.println("인잇바스켓");
		ArrayList<String[]> initListBasket = new ArrayList<>();
		initListBasket = sqlShopDAO.selOne(loginId);
		System.out.println("loginId:"+loginId);
		String[] in = new String[6];
		for(int i = 0; i <initListBasket.size(); i++) {
			for(int j = 0; j < 4; j++) {
				in[j]= initListBasket.get(i)[j];
			}
			in[5]="basket";
			sendTableList(in);
		}
	}
	
	public void sendInitBoard() {
		System.out.println("인잇보드");
		ArrayList<String[]> initListBoard = new ArrayList<>();
		initListBoard = sqlBoardDAO.getList();
		String[] in = new String[6];
		for(int i = 0; i <initListBoard.size(); i++) {
			for(int j = 0; j < 4; j++) {
				in[j]= initListBoard.get(i)[j];
			}
			in[5]="board";
			sendTableList(in);
		}
	}
	
	public void sendInitBlack() {
		System.out.println("인잇블랙");
		ArrayList<String[]> initListBlack = new ArrayList<>();
		initListBlack = sqlMemberDAO.getListBlack();
		String[] in = new String[6];
		for(int i = 0; i <initListBlack.size(); i++) {
			for(int j = 0; j < 2; j++) {
				in[j]= initListBlack.get(i)[j];
			}
			in[5]="black";
			sendTableList(in);
		}
		
	}
	

	
	public void sendTableList(String[] in) {
		try {
			System.out.println("sin:"+in[0]+""+in[1]+in[2]+""+in[3]+"");
			oosS = new ObjectOutputStream(withClient.getOutputStream());
			oosS.writeObject(in);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	// ID/PW를 ClientChat으로부터 InputStream하여 DB와 비교 체크한다.-> 결과를 ClinetChat에 OutputStream한다.
	private void idChk() {
		try {
			//ClientChat에서 아이디를 받아 String으로 바꿈
			System.out.println("idChk전");
			receiveMsg = withClient.getInputStream();
			byte[] receiveBuffer = new byte[100];
			receiveMsg.read(receiveBuffer);	// inputId/inputPw
			loginObject = new String(receiveBuffer);
			loginObject = loginObject.trim();
			System.out.println("idChk후");
			
			
			StringTokenizer tokenLO = new StringTokenizer(loginObject, "/");
			String inputId = tokenLO.nextToken();
			String inputPw = tokenLO.nextToken();
			
			// 접속 중인 아이디 확인
			ArrayList<String> loginMember = new ArrayList<>();
			loginMember = sc.getLoginMember();
			int flag = 0;
			System.out.println("사이즈체크"+loginMember.size());
			
			//접속 중인 아이디 없으면
			if(loginMember.size()==0) {
				flag = 1;
				
			//접속 중인 아이디 있으면
			}else if(loginMember.size()!=0){
				for(int i = 0; i < loginMember.size(); i++) {
					if(inputId.equals(loginMember.get(i))) {
						System.out.println("접속 중인 아이디");
						flag = -1;
						//GUI띄워주기해야함
						break;
					}
					System.out.println("[통과]접속 중인 아이디 아님");
					flag = 1;
					break;
				}
			}
			System.out.println("플래그 "+flag);
			
			
			//회원 명단에서 아이디, 비밀번호 일치 확인
//			ArrayList<String[]> initListMember=new ArrayList<>();
//			initListMember=sqlMemberDAO.getListMember();
//			for(int i = 0; i < initListMember.size(); i++) {
//				
//			}
			
			// 레벨 확인
			String msg = null;
			if(loginObject != null && flag == 1) {
				outter:for(int i = 0; i <memberDTOList.size(); i++) {
					for(int j = 0; j < 4; j++) {
						if(inputId.equals(memberDTOList.get(i).getId())
								&&inputPw.equals(memberDTOList.get(i).getPw())) {
							sc.addLoginMember(inputId);
							this.loginId  = inputId;
							String lv = memberDTOList.get(i).getLv();
							System.out.println("lv:"+lv);
							if(lv.equals("m")) {
								msg = "m";
								if(msg != null) {
									System.out.println("lv:"+lv);
									System.out.println("msg:"+msg);
									sendMsg = withClient.getOutputStream();
									byte[] sendBuffer = new byte[100];
									sendBuffer = msg.getBytes();
									sendMsg.write(sendBuffer);
									break outter;
								}
								
							}else if(lv.equals("n")) {
								msg = "n";
								if(msg != null) {
									System.out.println("lv:"+lv);
									System.out.println("msg:"+msg);
									sendMsg = withClient.getOutputStream();
									byte[] sendBuffer = new byte[100];
									sendBuffer = msg.getBytes();
									sendMsg.write(sendBuffer);
									break outter;
								}
								
							}else if(lv.equals("b")) {
								msg = "b";
								if(msg != null) {
									System.out.println("lv:"+lv);
									System.out.println("msg:"+msg);
									sendMsg = withClient.getOutputStream();
									byte[] sendBuffer = new byte[100];
									sendBuffer = msg.getBytes();
									sendMsg.write(sendBuffer);
									break outter;
								}
							}else {
								System.out.println("없는 LV");
							}
							
						}
					}
				}
			
			}else {
				System.out.println("접속 중인 아이디 or 아이디나 비번 틀림");
				msg = "x";
				if(msg != null) {
					sendMsg = withClient.getOutputStream();
					byte[] sendBuffer = new byte[100];
					sendBuffer = msg.getBytes();
					sendMsg.write(sendBuffer);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	
	
	
	//추가 상품 ClientChat에서 DTO를 받아 DAO로 보내기
	private void receive() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
					try {
						while(true) {
							System.out.println("서버멀티스레드");
							receiveOb = new ObjectInputStream(withClient.getInputStream());
							Object objectin = receiveOb.readObject();
							String[] in = (String[]) objectin;
							
							//ProductCenter
							if(in[5].equals("product")) {
								ProductDTO newDTO = new ProductDTO();
								newDTO.setPname(in[0]);
								newDTO.setPrice(in[1]);
								newDTO.setEa(in[2]);
								
								if(in[6].equals("addB")) {					//물품 추가 버튼
									sqlProductDAO.insertProduct(newDTO);
								}else if(in[6].equals("modB")) {			//물품 수정 버튼
									sqlProductDAO.updateProduct(newDTO);
								}else if(in[6].equals("delB")) {			//물품 삭제 버튼
									sqlProductDAO.removeProduct(newDTO);
								}
								
								//MemberCenter_member
							}else if(in[5].equals("membermember")) {
								MemberBlackDTO newDTO = new MemberBlackDTO();
								newDTO.setId(in[0]);
								newDTO.setMemo(in[1]);
								
								if(in[6].equals("addB")) {					//블랙 추가 버튼
									sqlMemberDAO.insertBlack(newDTO);
									sqlMemberDAO.updateMemberLvB(newDTO);
								}else if(in[6].equals("delB")) {			//블랙 취소 버튼
									sqlMemberDAO.removeBlack(newDTO);
									sqlMemberDAO.updateMemberLv(newDTO);
								}
								
								//MemberCenter_board
							}else if(in[5].equals("memberboard")) {
								MemberBoardDTO newDTO = new MemberBoardDTO();
								newDTO.setNo(in[0]);
								
								if(in[6].equals("delB")) {					//해당 후기 삭제 버튼
									sqlMemberDAO.removeBoard(newDTO);
								}
								
								//BoardCenter
							}else if(in[5].equals("board")) {
								BoardDTO newDTO = new BoardDTO();
								newDTO.setNo(in[0]);
								newDTO.setId(in[1]);
								newDTO.setPname(in[2]);
								newDTO.setAppraisal(in[3]);
								if(in[6].equals("addB")) {					//남기기 버튼
									sqlBoardDAO.insertBoard(newDTO);
								}
								
								//ShopCenter
							}else if(in[5].equals("shop")) {
								if(in[6].equals("orderB")) {					//장바구니 주문 버튼	
									ProductDTO newDTO = new ProductDTO();
									newDTO.setPname(in[0]);
									newDTO.setPrice(in[1]);
									newDTO.setEa(in[2]);
									sqlProductDAO.updateProduct(newDTO);			//상품 수량 수정
									
									ShopDTO newDTO2 = new ShopDTO();
									newDTO2.setPname(in[0]);
									newDTO2.setPrice(in[1]);
									newDTO2.setEa(in[2]);
									newDTO2.setBuyea(in[3]);
									newDTO2.setId(in[4]);
									sqlShopDAO.insertShop(newDTO2);					//장바구니 정보 추가
								}
							}else if(in[5].equals("logout")) {
								sc.removeLogoutId(loginId);
								idChk();
							}else if(in[5].equals("nologin")) {
								idChk();
							}else if(in[5].equals("tablereset")) {
								System.out.println("테이블리셋");
								sendInitAll();//리스트 다시 세팅
								
							}
							
						}
						
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
						
					}
					
				}
			
		}).start();
		Thread.interrupted();
	}	
	
	


	public void endChat() {
		try {
			withClient.close();
			receiveMsg.close();
			sendMsg.close();
			receiveOb.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
