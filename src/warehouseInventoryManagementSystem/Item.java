package warehouseInventoryManagementSystem;

public class Item {
	
	//I created this class simply for modularity and potential expansion in the future.
	
	String id;
	String name;
	String category;
	String quantity;
	String location;
	String supplier;
	
	public Item(String id, String name, String category, String quantity, String location, String supplier) {
		
		this.id = id;
		this.name = name;
		this.category = category;
		this.quantity = quantity;
		this.location = location;
		this.supplier = supplier;
		
	}

	public void setID(String id) {
		this.id = id;
	}
	
	public String getID() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public String getQuantity() {
		return quantity;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	public String getSupplier() {
		return supplier;
	}
}
