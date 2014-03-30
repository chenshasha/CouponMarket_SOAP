package connection;

import javax.jws.WebService;

import module.Item;
import module.Order;
import module.User;
import module.Cart;

import java.sql.SQLException;


@WebService
public class CouponMarketService {
	
	public Order[] getOrderByBuyerId(String byer_email){
		DatabaseConnection db=new DatabaseConnection();		
		return db.getOrderByBuyerId(byer_email);
	}
	
	public void removeItemInCart(String cart_id){
		DatabaseConnection db=new DatabaseConnection();		
		db.removeItemInCart(cart_id);
	}
	
	public Cart getCartItemById(String cart_id){
		DatabaseConnection db=new DatabaseConnection();		
		return db.getCartItemById(cart_id);
	}
	
	public User getUserById(String email){
		DatabaseConnection db=new DatabaseConnection();		
		return db.getUserById(email);
	}
	
	public Order[] getOrderDetail(String order_id){
		DatabaseConnection db=new DatabaseConnection();		
		return db.getOrderDetail(order_id);
	}
	public String[] getAllOrder(String buyer_id){
		DatabaseConnection db=new DatabaseConnection();		
		return db.getAllOrder(buyer_id);
	}
	
	public Cart[] getItemInCart(String buyer_id){		
		DatabaseConnection db=new DatabaseConnection();		
		return db.getItemInCart(buyer_id);
	}
	
	public Item getItemFromId(int id){
		DatabaseConnection db=new DatabaseConnection();
		return db.getItemFromId(id);
	}
	
	public Item[] getAllItem(){
		
		DatabaseConnection db=new DatabaseConnection();
		return db.getAllItem();

	}
	
	public void reduceStock(Integer item_id, Integer sellQuantity){
		DatabaseConnection db=new DatabaseConnection();
		db.reduceStock(item_id, sellQuantity);
	}
	
	public boolean makeOrder(String buyer_id, String address, Integer item_id, String creditCard, Integer quantity){
		DatabaseConnection db=new DatabaseConnection();
		return db.makeOrder(buyer_id, address, item_id, creditCard, quantity);
	}
	
	public boolean addToCart(Integer item_id, String buyer_id, Integer quantity, String merchandise, String description, Double price){
		DatabaseConnection db=new DatabaseConnection();
		return db.addToCart(item_id, buyer_id, quantity, merchandise, description, price);
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
