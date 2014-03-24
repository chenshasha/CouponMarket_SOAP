package connection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import module.item;

public class DatabaseConnection {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private Statement stmt = null;

	public DatabaseConnection(){
		connect = DbUtil.getConnection();
	}
	
	//view order info
	//view account info
	//view cart	
	//view item detail
	//view all item
	public item[] getAllItem(){
		item[] items = new item[1000];
		
		try
		{
			String query = "SELECT seller_id, quantity, merchandise, description, price FROM item where quantity > 0";
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs != null) {
				int n = 0;
				while(rs.next())
				{
					item i = new item();
					//i.setCreateDate(rs.getDate("createDate"));
					i.setQuantity(rs.getInt("quantity"));
					i.setMerchandise(rs.getString("merchandise"));
					i.setDescription(rs.getString("description"));
					i.setPrice(rs.getDouble("price"));
					i.setSeller_id(rs.getString("seller_id"));
					items[n] = i;
					n++;
				}
			}
		}catch (SQLException sql) {
			sql.printStackTrace();
		}
		
		return items;
	}

	//check out
	//only able to check out one item now
	public boolean makeOrder(String buyer_id, String address, Integer item_id, String creditCard, Integer quantity){
		//if the stock is not enough
		int stock = 0;
		try{
			String query = "SELECT quantity FROM item where item_id = \"" + item_id +"\"";
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				stock = rs.getInt("quantity");
			}
			rs.close();
			}catch (SQLException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(stock < quantity){
				return false;
			}else{				
				try{
					preparedStatement = connect
						      .prepareStatement("INSERT INTO couponmarket.order (buyer_id, address, item_id, quantity, creditCard, placedDate, order_id) VALUES (?, ?, ?, ?, ?, ?, ?)");
						preparedStatement.setString(1, buyer_id);
					    preparedStatement.setString(2, address);
					    preparedStatement.setInt(3, item_id);
					    preparedStatement.setInt(4, quantity);
					    preparedStatement.setString(5, creditCard);
					    preparedStatement.setDate(6, new java.sql.Date(System.currentTimeMillis()));
					    preparedStatement.setString(7, UUID.randomUUID().toString());
					    preparedStatement.executeUpdate();   
					    reduceStock(item_id, quantity);
			
					}catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			return true;
	}
	
	//decrease stock
	public void reduceStock(Integer item_id, Integer sellQuantity){
		try{
			preparedStatement = connect
				      .prepareStatement("UPDATE item SET quantity = quantity - ? WHERE item_id = ?");
			preparedStatement.setInt(1, sellQuantity);
		    preparedStatement.setInt(2, item_id);
		    preparedStatement.executeUpdate();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//add an item to cart
	public boolean addToCart(Integer item_id, String buyer_id, Integer quantity){		
		//if the stock is not enough
		int stock = 0;
		try{
			String query = "SELECT quantity FROM item where item_id = \"" + item_id +"\"";
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				stock = rs.getInt("quantity");
			}
			rs.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(stock < quantity){
			return false;
		}else{		
			try{
				preparedStatement = connect
					      .prepareStatement("INSERT INTO cart (item_id, buyer_id, quantity, addDate) VALUES (?, ?, ?, ?)");
					preparedStatement.setInt(1, item_id);
				    preparedStatement.setString(2, buyer_id);
				    preparedStatement.setInt(3, quantity);
				    preparedStatement.setDate(4, new java.sql.Date(System.currentTimeMillis()));		    
				    preparedStatement.executeUpdate();
	
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
	}
	
	//post an item
	public boolean postItem (String seller_id, Integer quantity, String merchandise, String description, Double price){
		try{
			preparedStatement = connect
				      .prepareStatement("INSERT INTO item (seller_id, quantity, merchandise, description, price, createDate) VALUES (?, ?, ?, ?, ?, ?)");
				preparedStatement.setString(1, seller_id);
			    preparedStatement.setInt(2, quantity);
			    preparedStatement.setString(3, merchandise);
			    preparedStatement.setString(4, description);
			    preparedStatement.setDouble(5, price);
			    preparedStatement.setDate(6, new java.sql.Date(System.currentTimeMillis()));		    
			    preparedStatement.executeUpdate();

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	//login
	public boolean login(String email, String password){
		String pass = null;
		Date tempDate = null;
		try{
			String query = "SELECT * FROM user where email = \"" + email +"\"";
			
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				pass = rs.getString("password");
				tempDate = rs.getDate("curCheckin");
				//System.out.println(tempDate);
				//System.out.println(new java.sql.Date(System.currentTimeMillis()));
			}
			System.out.println("psw is " + pass);
			System.out.println("input psw is " + password);
			rs.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(pass.equals(password)){
			//update login time
			try{
				preparedStatement = connect
					      .prepareStatement("UPDATE user SET lastCheckin = ?, curCheckin = ? WHERE email = ?");
				preparedStatement.setDate(1, tempDate);
				preparedStatement.setDate(2, new java.sql.Date(System.currentTimeMillis()));				
			    preparedStatement.setString(3, email);
			    preparedStatement.executeUpdate();

			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return true;
		}else{
			return false;
		}

	}
	
	//signUp 
	public boolean signUp(String email, String password, String lname, String fname){
		//see if there is already the user
		try{
			String query = "SELECT * FROM user where email = \"" + email +"\"";
			//System.out.println(query);
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			//System.out.println(rs);
			if(rs.next()){
				return false;
			}
			rs.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			preparedStatement = connect
				      .prepareStatement("INSERT INTO USER (email, password, lname, fname, lastCheckin, curCheckin) VALUES (?, ?, ?, ?, ?, ?)");
				preparedStatement.setString(1, email);
			    preparedStatement.setString(2, password);
			    preparedStatement.setString(3, lname);
			    preparedStatement.setString(4, fname);
			    preparedStatement.setDate(5, new java.sql.Date(System.currentTimeMillis()));
			    preparedStatement.setDate(6, new java.sql.Date(System.currentTimeMillis()));
			 		    
			    preparedStatement.executeUpdate();

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	

}
