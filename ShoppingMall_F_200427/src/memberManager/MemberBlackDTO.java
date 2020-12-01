package memberManager;

public class MemberBlackDTO {
	String id;
	String memo;
	
	public String[] getArray() {
		String[] returnData = new String[2];
		returnData[0]=this.id;
		returnData[1]=this.memo;
		
		return returnData;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	
	
	
}
