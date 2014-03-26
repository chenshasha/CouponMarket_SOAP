package module;

public class Order {
	
	private Integer quantity;
	private String buyer_id;
	private String order_id;
	private String placedDate;
	private String address;
	private Integer item_id;
	private String creditCard;
	
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getPlacedDate() {
		return placedDate;
	}
	public void setPlacedDate(String placedDate) {
		this.placedDate = placedDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getItem_id() {
		return item_id;
	}
	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
}
