package productManager;


public class ProductDTO {
	String pname;
	String price;
	String ea;
	
	public String[] getArray() {
		String[] returnData = new String[3];
		returnData[0]=this.pname;
		returnData[1]=this.price;
		returnData[2]=this.ea;
		
		return returnData;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getEa() {
		return ea;
	}

	public void setEa(String ea) {
		this.ea = ea;
	}
	
	
}
