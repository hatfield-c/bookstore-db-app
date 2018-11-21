package bks;

public class OrderEntry {

	private String orderNo;
	private String name;
	private String recDate;
	private String shipDate;
	
	OrderEntry(String orderNo, String name, String recDate, String shipDate){
		this.orderNo = orderNo;
		this.name = name;
		this.recDate = recDate.substring(0, recDate.length() - 10);
		
		if(shipDate != null)
			this.shipDate = shipDate.substring(0, recDate.length() - 10);
		else
			this.shipDate = "NULL";
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getRecDate() {
		return this.recDate;
	}
	
	public void setRecDate(String recDate) {
		this.recDate = recDate;
	}
	
	public String getShipDate() {
		return this.shipDate;
	}
	
	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}
	
}
