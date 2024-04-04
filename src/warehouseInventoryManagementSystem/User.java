package warehouseInventoryManagementSystem;

import org.bson.Document;
import org.bson.types.ObjectId;

public class User {

	String userID;
	String password;
	String firstName;
	String lastName;
	String email;
	String userType;
	
	public User (String id, String pass, String fName, String lName, String email, String userType) {
		this.userID = id;
		this.password = pass;
		this.firstName = fName;
		this.lastName = lName;
		this.email = email;
		this.userType = userType;
	}

	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getType() {
		return userType;
	}
	
	public void setType(String type) {
		this.userType = type;
	}
	
	public Document toDoc () {
		return new Document()
				.append("_id", new ObjectId())
				.append("id", userID)
				.append("password", password)
				.append("firstname", firstName)
				.append("lastname", lastName)
				.append("email", email)
				.append("usertype", userType);
				
	}
}
