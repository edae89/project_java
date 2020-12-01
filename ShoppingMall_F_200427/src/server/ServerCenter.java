package server;

import java.util.ArrayList;

public class ServerCenter {

	private static ServerCenter ServerCenterobj;
	private ArrayList<ServerChat> sList = new ArrayList<>(); //ServerChat객체 리스트
	private ArrayList<String> loginMember = new ArrayList<>();	//로그인한 아이디 리스트

	private ServerCenter(){
		
	}
	
	public static ServerCenter getInstance() {
		if (ServerCenterobj == null) {
			ServerCenterobj = new ServerCenter();
		}
		return ServerCenterobj;
	}
	
	
	public ArrayList<String> getLoginMember() {
		
		for(int i = 0; i < loginMember.size(); i++) {
			System.out.println("겟로긴멤버"+loginMember.get(i));
		}
		System.out.println("사이즈get"+loginMember.size());
		return loginMember;
	}

	public void addLoginMember(String loginId) {
		loginMember.add(loginId);
		System.out.println("사이즈add"+loginMember.size());
		for(int i = 0; i < loginMember.size(); i++) {
			System.out.println("add:"+loginMember.get(i));
		}
	}
	
	public void removeLogoutId(String logoutId) {//실행안됨
		System.out.println("로그아웃아이디"+logoutId);//여기까진됨
		System.out.println("사이즈rem"+loginMember.size());//<<여기서 사이즈가 0나옴
		for(int i = 0; i < loginMember.size(); i++) {
			System.out.println("remove 전 배열:"+loginMember.get(i));
		}
		for(int i = 0; i < loginMember.size(); i++) {
			if(logoutId.equals(loginMember.get(i))) {
				loginMember.set(i, null);
				System.out.println("remove 후 배열:"+loginMember.get(i));
				break;
			}
			
		}
	}
	
	
	
//	public void setLoginMember(ArrayList<String> loginMember) {
//		this.loginMember = loginMember;
//	}

	
	
	public void addServerChat(ServerChat s) {
		sList.add(s);
	}
	
	

	
	
	
}
