package board;

public class BoardDTO {
	private String no;
	private String id;
	private String pname;
	private String appraisal;
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
	
	public String getAppraisal() {
		return appraisal;
	}

	public void setAppraisal(String appraisal) {
		this.appraisal = appraisal;
	}
	
	public String[] getArray() {
		String[] returnData = new String[4];
		returnData[0]=this.no;
		returnData[1]=this.id;
		returnData[2]=this.pname;
		returnData[3]=this.appraisal;
		
		
		return returnData;
	}
	
}
