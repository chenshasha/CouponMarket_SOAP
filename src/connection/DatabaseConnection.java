package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.UUID;

import module.Item;
import module.order;
import module.user;


public class DatabaseConnection {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private Statement stmt = null;

	public DatabaseConnection(){
		connect = DbUtil.getConnection();
	}
	
	//view each order detail
	public order getOrderDetail(String order_id){
		order orderDetail = new order();
		try{
			preparedStatement = connect
				      .prepareStatement("SELECT quantity, buyer_id, placedDate, address, item_id, creditCard FROM couponmarket.order where order_id = ?");
				preparedStatement.setString(1, order_id);
				ResultSet rs = preparedStatement.executeQuery();
			if(rs != null){
				while(rs.next()){
					orderDetail.setAddress(rs.getString("address"));
					orderDetail.setBuyer_id(rs.getString("buyer_id"));
					orderDetail.setCreditCard(rs.getString("creditCard"));
					orderDetail.setItem_id(rs.getInt("item_id"));
					orderDetail.setOrder_id(order_id);
					orderDetail.setPlacedDate(rs.getString("placedDate"));
					orderDetail.setQuantity(rs.getInt("quantity"));
		
				}
			}
		}
		catch(SQLException s)
			{
				s.printStackTrace();
			}
		return orderDetail;
	}
	//view order_id info
	public String[] getAllOrder(String buyer_id){
		String[] rl = new String[100];
		int n = 0;
		try{
			preparedStatement = connect
				      .prepareStatement("SELECT order_id FROM couponmarket.order WHERE buyer_id = ?");
				preparedStatement.setString(1, buyer_id);
				ResultSet rs = preparedStatement.executeQuery();
				
			if(rs != null){
				while(rs.next()){
					rl[n] = rs.getString("order_id");		
					n++;
				}
			}
		}
		catch(SQLException s)
			{
				s.printStackTrace();
			}
		return Arrays.copyOfRange(rl, 0, n);
	}
	
	//view user info (no successful)
//	public user getUser(String email){
//		user u = new user();
//		try{
//			preparedStatement = connect
//				      .prepareStatement("SELECT password, lname, fname, curCheckin, lastChecin FROM user where email = ?");
//				preparedStatement.setString(1, email);
//				ResultSet rs = preparedStatement.executeQuery();
//			if(rs != null){
//				while(rs.next()){
//					u.setCurCheckin(rs.getString("curCheckin"));
//					u.setEmail(email);
//					u.setFname(rs.getString("fname"));
//					u.setLastCheckin(rs.getString("lastCheckin"));
//					u.setLname(rs.getString("lname"));
//					u.setPassword(rs.getString("password"));
//					
//				}
//			}
//		}
//		catch(SQLException s)
//			{
//				s.printStackTrace();
//			}
//		return u;
//	}
	

	//view cart
	public int[] getItemInCart(String buyer_id){
		int[] rl = new int[100];
		int n = 0;
		try{
			preparedStatement = connect
				      .prepareStatement("SELECT item_id FROM cart WHERE buyer_id = ?");
				preparedStatement.setString(1, buyer_id);
				ResultSet rs = preparedStatement.executeQuery();
				
			if(rs != null){
				while(rs.next()){
					rl[n] = rs.getInt("item_id");					
					n++;
				}
			}
		}
		catch(SQLException s)
			{
				s.printStackTrace();
			}
		return Arrays.copyOfRange(rl, 0, n);
	}
	
	
	//view item detail
	
	public Item getItemFromId(int id){
		Item adItem = new Item();
		try{
			preparedStatement = connect
				      .prepareStatement("SELECT merchandise, createDate, description, price, quantity, seller_id FROM item where item_id = ?");
				preparedStatement.setInt(1, id);
				ResultSet rs = preparedStatement.executeQuery();
			if(rs != null){
				while(rs.next()){
					adItem.setMerchandise(rs.getString("merchandise"));
					adItem.setCreateDate(rs.getString("createDate"));
					adItem.setDescription(rs.getString("description"));
					adItem.setPrice(rs.getDouble("price"));
					adItem.setQuantity(rs.getInt("quantity"));
					adItem.setSeller_id(rs.getString("seller_id"));
					adItem.setItem_id(id);
				}
			}
		}
		catch(SQLException s)
			{
				s.printStackTrace();
			}
		return adItem;
	}
	

	public Item[] getAllItem(){
		Item[] items = new Item[100];
		int n = 0;
		try
		{
			preparedStatement = connect
				      .prepareStatement("SELECT merchandise, createDate, description, price, quantity, seller_id FROM item where quantity > 0");
				
				ResultSet rs = preparedStatement.executeQuery();
			if (rs != null) {
				
				while(rs.next())
				{
					Item i = new Item();
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
		
		return Arrays.copyOfRange(items, 0, n);
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
					java.util.Date dt = new java.util.Date();
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String currentTime = sdf.format(dt);
					preparedStatement = connect
						      .prepareStatement("INSERT INTO couponmarket.order (buyer_id, address, item_id, quantity, creditCard, placedDate, order_id) VALUES (?, ?, ?, ?, ?, ?, ?)");
						preparedStatement.setString(1, buyer_id);
					    preparedStatement.setString(2, address);
					    preparedStatement.setInt(3, item_id);
					    preparedStatement.setInt(4, quantity);
					    preparedStatement.setString(5, creditCard);
					    preparedStatement.setString(6, currentTime);
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
				java.util.Date dt = new java.util.Date();
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = sdf.format(dt);
				preparedStatement = connect
					      .prepareStatement("INSERT INTO cart (item_id, buyer_id, quantity, addDate) VALUES (?, ?, ?, ?)");
					preparedStatement.setInt(1, item_id);
				    preparedStatement.setString(2, buyer_id);
				    preparedStatement.setInt(3, quantity);
				    preparedStatement.setString(4, currentTime);		    
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
			java.util.Date dt = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dt);
			preparedStatement = connect
				      .prepareStatement("INSERT INTO item (seller_id, quantity, merchandise, description, price, createDate) VALUES (?, ?, ?, ?, ?, ?)");
				preparedStatement.setString(1, seller_id);
			    preparedStatement.setInt(2, quantity);
			    preparedStatement.setString(3, merchandise);
			    preparedStatement.setString(4, description);
			    preparedStatement.setDouble(5, price);
			    preparedStatement.setString(6, currentTime);		    
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
		String tempDate = null;
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		
		try{
			String query = "SELECT * FROM user where email = \"" + email +"\"";
			
			stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				pass = rs.getString("password");
				tempDate = rs.getString("curCheckin");
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
				preparedStatement.setString(1, tempDate);
				preparedStatement.setString(2, currentTime);				
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
			java.util.Date dt = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dt);

			preparedStatement = connect
				      .prepareStatement("INSERT INTO USER (email, password, lname, fname, lastCheckin, curCheckin) VALUES (?, ?, ?, ?, ?, ?)");
				preparedStatement.setString(1, email);
			    preparedStatement.setString(2, password);
			    preparedStatement.setString(3, lname);
			    preparedStatement.setString(4, fname);
			    preparedStatement.setString(5, currentTime);
			    preparedStatement.setString(6, currentTime);
			 		    
			    preparedStatement.executeUpdate();

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	

}
