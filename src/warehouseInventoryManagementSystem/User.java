package warehouseInventoryManagementSystem;

public class User {

	String userID;
	String password;
	String firstName;
	String lastName;
	String email;
	String userType;

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
	
	public String toString () {
		return "User ID: " + userID + ", Password: " + password + ", First Name: " + firstName + ", Last Name: " + lastName + ", Email Address: " + email + ",User Type: " + userType;
	}
}
