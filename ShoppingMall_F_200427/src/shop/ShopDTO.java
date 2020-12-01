package shop;

import java.io.Serializable;

public class ShopDTO implements Serializable{
	String no;
	String id;
	String pname;
	String price;
	String ea;
	String buyea;
	
	public String[] getArray() {
		String[] returnData = new String[4];
//		returnData[0]=this.no;
//		returnData[1]=this.id;
		returnData[0]=this.pname;
		returnData[1]=this.price;
		returnData[2]=this.ea;
		returnData[3]=this.buyea;
		return returnData;
	}

	public String getEa() {
		return ea;
	}

	public void setEa(String ea) {
		this.ea = ea;
	}

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

	public String getBuyea() {
		return buyea;
	}

	public void setBuyea(String buyea) {
		this.buyea = buyea;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
