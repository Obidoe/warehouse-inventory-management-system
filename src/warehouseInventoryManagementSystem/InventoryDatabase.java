package warehouseInventoryManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InventoryDatabase {
	//Database handler for inventory using mySQL.
	
	//Singleton Implementation
	private static InventoryDatabase instance;
	static Connection myConn;
	
	private InventoryDatabase() {}
	
	public static InventoryDatabase getInstance() {
		if (instance == null) {
			instance = new InventoryDatabase();
		}
		return instance;
	}
	
	
	
	public void establishConnection() {
		
		 //connection to mysql
        String url = "";
        String user = "";
        String sqlpassword = "";
        
        try {
			myConn = DriverManager.getConnection(url,user,sqlpassword);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void addItem(String id, String name, String category, String quantity, String location, String supplier) {
		
		
		//Using item object for modularity. Currently, just passing through values would be enough, item object will be useful for expansion of application.
		Item item = new Item(id, name, category, quantity, location, supplier);
		 
		  String sql = " insert into item (itemID, itemName, category, quantity, location, supplier)"
	        	    + " values (?, ?, ?, ?, ?, ?)";
		  try {
		  PreparedStatement preparedStmt = myConn.prepareStatement(sql);
		  preparedStmt.setString (1, item.getID());
		  preparedStmt.setString (2, item.getName());
		  preparedStmt.setString (3, item.getCategory());
		  preparedStmt.setString (4, item.getQuantity());
		  preparedStmt.setString (5, item.getLocation());
		  preparedStmt.setString (6, item.getSupplier());
	        
	      preparedStmt.executeUpdate();
		  } catch (Exception e)
		  {
			  System.err.println("Got an exception!");
			  // printStackTrace method 
			  // prints line numbers + call stack
			  e.printStackTrace();
			  // Prints what exception has been thrown 
			  System.out.println(e); 
			  }
		
	}
	
	public void removeItem(int id) {
		
		String sql = "delete from item where itemID = " + id;
		try {
		Statement stmt = myConn.createStatement();
		stmt.executeUpdate(sql);
		} catch (Exception e) 
		 {System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());}
		
	}
	
	public void updateItem(int id, String choice, String newVal) {
		
		 // create the java mysql update preparedstatement
	      String sql = "update item set " + choice + "= ? where itemID = ?";
	      
	      try {
	      PreparedStatement preparedStmt = myConn.prepareStatement(sql);
	      
	      if (choice.equals("quantity")) {
	    	  preparedStmt.setInt(1, Integer.parseInt(newVal));
	    	  preparedStmt.setInt(2, id);
	      }
	      
	      else {
	    	  preparedStmt.setString(1, newVal);
	    	  preparedStmt.setInt(2, id);
	      }

	      // execute the java preparedstatement
	      preparedStmt.executeUpdate();
	      } catch (Exception e) 
	      {System.err.println("Got an exception! ");
	       System.err.println(e.getMessage());}
		
	}
	
	public void searchItem(int id) {
		
		try {
		Statement stmt = myConn.createStatement();
		String sql = "select * from item where itemID = " + id;
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			
			int itemid = rs.getInt(1);
			String itemname = rs.getString(2);
			String category = rs.getString(3);
			int quantity = rs.getInt(4);
			String location = rs.getString(5);
			String supplier = rs.getString(6);
			
			System.out.println("Item ID: " + itemid);
			System.out.println("Item Name: " + itemname);
			System.out.println("Category: " + category);
			System.out.println("Quantity: " + quantity);
			System.out.println("Location: " + location);
			System.out.println("Supplier: " + supplier);
			
			
		}
		
		
		
		} catch (Exception e) 
		 {System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());}
		
	}
	
	public void closeConnection() throws SQLException {
		myConn.close();
	}
	
	
}
