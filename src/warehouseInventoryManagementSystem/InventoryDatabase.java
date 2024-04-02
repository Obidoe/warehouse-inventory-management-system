package warehouseInventoryManagementSystem;

public class InventoryDatabase {
	//Database handler for inventory using mySQL.
	
	//Singleton Implementation
	private static InventoryDatabase instance;
	private InventoryDatabase() {}
	
	public static InventoryDatabase getInstance() {
		if (instance == null) {
			instance = new InventoryDatabase();
		}
		return instance;
	}
	
	public void establishConnection() {
		
	}
	
	public void addItem() {
		
	}
	
	public void removeItem() {
		
	}
	
	public void updateItem() {
		
	}
	
	
}
