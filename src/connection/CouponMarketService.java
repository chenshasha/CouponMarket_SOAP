package connection;

import javax.jws.WebService;

import module.item;

import java.sql.SQLException;


@WebService
public class CouponMarketService {
	
	public item[] getAllItem(){
		item[] items = new item[1000];
		DatabaseConnection db=new DatabaseConnection();
		items = db.getAllItem();
		return items;
	}
	
	public void reduceStock(Integer item_id, Integer sellQuantity){
		DatabaseConnection db=new DatabaseConnection();
		db.reduceStock(item_id, sellQuantity);
	}
	
	public boolean makeOrder(String buyer_id, String address, Integer item_id, String creditCard, Integer quantity){
		DatabaseConnection db=new DatabaseConnection();
		return db.makeOrder(buyer_id, address, item_id, creditCard, quantity);
	}
	
	public boolean addToCart(Integer item_id, String buyer_id, Integer quantity){
		DatabaseConnection db=new DatabaseConnection();
		return db.addToCart(item_id, buyer_id, quantity);	
	
	}
	
	public boolean postItem (String seller_id, Integer quantity, String merchandise, String description, Double price){
		DatabaseConnection db=new DatabaseConnection();
		return db.postItem(seller_id, quantity, merchandise, description, price);	
	}	

	public boolean signUp(String email, String password, String lname, String fname) throws SQLException
	{	
		DatabaseConnection db=new DatabaseConnection();
		return db.signUp(email, password, lname, fname);	
	}
	
	public boolean login(String email, String password)
	{	
		DatabaseConnection db=new DatabaseConnection();
		return db.login(email, password);	
	}
	
}
