package warehouseInventoryManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

//Database handler for inventory using mySQL.
public class InventoryDatabase {
	
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
	
	
	 //connection to mysql
	public void establishConnection() {
		
		
        String url = "";
        String user = "";
        String sqlpassword = "";
        
        try {
			myConn = DriverManager.getConnection(url,user,sqlpassword);
    	} catch (Exception e) 
		 {System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());}
		
	}
	
	//Adds a new item into the mysql database using prepared statements and taking info from item object.
	public void addItem(String id, String name, String category, String quantity, String location, String supplier) {
		
		
		//Using item object for modularity. Currently, just passing through values would be enough, item object will be useful for expansion of application.
		Item item = new Item(id, name, category, quantity, location, supplier);
		 
		//sql query
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
			 {System.err.println("Got an exception! ");
		      System.err.println(e.getMessage());}
		
	}
	
	//removes an item from the database based on the id given
	public void removeItem(int id) {
		
		String sql = "delete from item where itemID = " + id;
		try {
		Statement stmt = myConn.createStatement();
		stmt.executeUpdate(sql);
		} catch (Exception e) 
		 {System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());}
		
	}
	
	//updates item information
	public void updateItem(int id, String choice, String newVal) {
		
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
	
	//Notification for low stock. Originally planned on using observer, however for this application it would just over complicate things. Clients aren't really featured in this specific application.
	public void notification() {
		
		try {
		Statement stmt = myConn.createStatement();
		String sql = "select * from item";
		ResultSet rs = stmt.executeQuery(sql);
		int i = 0;
		
		
		System.out.println("\nLow stock notifications: ");
		while (rs.next()) {
			if (rs.getInt(4) < 3) {
				System.out.println(rs.getString(6) + ", your product: " + rs.getString(2) + " has low stock. Current stock is: " + rs.getInt(4));
				i++;
			}
		}
		
		if (i == 0) {
			System.out.println("None.");
		}
		} catch (Exception e) 
		 {System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());}
		
	}
	
	//Prints a summary/report of a specific supplier's inventory
	public void printReport(String supplier) {
		
		try {
			Statement stmt = myConn.createStatement();
			String sql = "select * from item where supplier = " + supplier;
			ResultSet rs = stmt.executeQuery(sql);
			
			System.out.println("\nInventory Summary Report for " + supplier + ": ");
			while (rs.next()) {
				int itemid = rs.getInt(1);
				String itemname = rs.getString(2);
				String category = rs.getString(3);
				int quantity = rs.getInt(4);
				String location = rs.getString(5);
				
				System.out.println("");
				System.out.println("Item ID: " + itemid);
				System.out.println("Item Name: " + itemname);
				System.out.println("Category: " + category);
				System.out.println("Quantity: " + quantity);
				System.out.println("Location: " + location);
			}
			} catch (Exception e) 
			 {System.err.println("Got an exception! ");
		      System.err.println(e.getMessage());}
		
	}
	
	
}
