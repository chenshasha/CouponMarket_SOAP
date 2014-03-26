package module;


public class Cart {
	private Integer item_id;
	private String buyer_id;
	private Integer quantity;
	private String addDate;
	private Integer cart_id;
	
	public Integer getCart_id() {
		return cart_id;
	}
	public void setCart_id(Integer cart_id) {
		this.cart_id = cart_id;
	}
	public Integer getItem_id() {
		return item_id;
	}
	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

}
