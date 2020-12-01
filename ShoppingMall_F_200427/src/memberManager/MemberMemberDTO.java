package memberManager;


public class MemberMemberDTO {
	String id;
	String pw;
	String lv;
	String point;
	
	public String[] getArray() {
		String[] returnData = new String[4];
		returnData[0]=this.id;
		returnData[1]=this.pw;
		returnData[2]=this.lv;
		returnData[3]=this.point;
		
		return returnData;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getLv() {
		return lv;
	}

	public void setLv(String lv) {
		this.lv = lv;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	
}
