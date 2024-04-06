package warehouseInventoryManagementSystem;

import java.util.Scanner;

import java.sql.*;

public class App {
	
	private static String permission = "";
	
	//this menu is displayed as the program is launched. A user is required to sign in before they can access anything else. They also have the option to exit.
	public static void launchMenu() { 
		System.out.println("Welcome to the Warehouse Inventory Management System.");
		System.out.println("To continue, press '1' to login. Otherwise, press '2' to exit.");
		System.out.println("\n1. Login");
		System.out.println("2. Exit");
	}
	
	//this menu is displayed once the user is logged in. Certain actions require certain permissions.
	public static void useMenu() {
		System.out.println("[Currently signed in as: " + permission + "]");
		System.out.println("Hello, welcome to the command panel! Please choose what you would like to do from the options below: \n");
		System.out.println("1. Add New User");
		System.out.println("2. Update User Information");
		System.out.println("3. Remove User");
		System.out.println("4. Add New Item");
		System.out.println("5. Update Item Information");
		System.out.println("6. Remove Item");
		System.out.println("7. Search for item");
		System.out.println("8. Print Report");
		System.out.println("9. Log Out");
	}
	

	public static void main(String[] args) throws SQLException {
		
		LoginSystem loginsys = LoginSystem.getInstance();
		loginsys.establishConnection();
		
		InventoryDatabase invdatabase = InventoryDatabase.getInstance();
		invdatabase.establishConnection();
		
		//booleans for while loops
		boolean signedIn = false;
		boolean loggedOut = true;
		
		//Global variables to remember the info of the user currently logged in.
		String liID = "";
		String liPASS = "";
	
		//System.in shouldn't be closed, unless its end of the file which would close the scanner regardless.
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
			
			//This while loop pertains to the launch menu and logging in to the system.
			while(loggedOut == true) {

				launchMenu();
				
				int choice = input.nextInt();
				
				//Login
				if (choice == 1) {
					
					System.out.println("Please enter your User ID Number: ");
					
					liID = input.next();
					input.nextLine();
					
					//Check for id in database
					
					System.out.println("Please enter your password: ");
					
					liPASS = input.next();
					input.nextLine();
					
					
					//Check if password connected to id is valid. If not, try again.
					
						permission = loginsys.login(liID, liPASS);
						if (permission != null) {
							signedIn = true;
	
							break;
						}
						
					
					
				}
				
				//Exit
				if (choice == 2) {
					System.out.println("Exiting system...");
					System.exit(0);
				}
				
				//Invalid option. reroute to launch menu
				else {
					System.out.println("That is not a valid option. Please try again. \n ");
					
				}
				
				
			
			}
			
			//This loop handles everything that happens while logged in. 

			while(signedIn == true) {
				
				useMenu();
			
				//Low Stock Level notification. Displays when the quantity is below a certain threshold.
				invdatabase.notification();
				
				int choice = input.nextInt();
				
				switch(choice) {
				
					//Adds a new user into the MongoDB database
					case 1:
						
					{

						if (!permission.equals("admin")) {
							System.out.println("You do not have permission to add users. Please contact a System Administrator.");
							break;
						}
						
						System.out.println("Please enter the new user information: ");
						
						System.out.println("User ID:");
						String userid = input.next();
						input.nextLine();
						
						System.out.println("Password: ");
						String password = input.next();
						input.nextLine();
						
						System.out.println("First Name: ");
						String firstname = input.next();
						input.nextLine();
						
						System.out.println("Last Name: ");
						String lastname = input.next();
						input.nextLine();
						
						System.out.println("Email Address: ");
						String email = input.next();
						input.nextLine();
						
						System.out.println("User Type (staff/manager/admin): ");
						String usertype = input.next();
						input.nextLine();
						
						loginsys.addUser(userid, password, firstname, lastname, email, usertype);
						
						loginsys.logUserActions(liID, "Add User", "User ID: " + userid + "\nUser Full Name: " + firstname + " " + lastname);
						break;
						
					}
					
					//Updates user information
					case 2:
						
					{
						
						String updateChoice = "";
						String userid = liID;
						String update = "";
						
						//Staff can only update their own password and email. Anything else must be done by a manager or admin
						if (!permission.equals("admin") && !permission.equals("manager")) {
							
							
							System.out.println("Update your information: ");
							System.out.println("What would you like to update? (password/email): ");
							System.out.println("Please contact a Manager or System Administrator to update any other information.");
							
							updateChoice = input.next();
							input.nextLine();
							
							System.out.println("Enter updated information: ");
							
							update = input.next();
							input.nextLine();
							
							loginsys.updateUser(liID, updateChoice, update);
							
							
							
							
						}
						
						//This is the block of code for managers/admins
						else {
							
							System.out.println("Enter the User ID of the user whose information you wish to update: ");
							
							userid = input.next();
							input.nextLine();
							
							System.out.println("What information would you like to update? (id, password, firstname, lastname, email, usertype)");
							
							updateChoice = input.next();
							input.nextLine();
							
							System.out.println("Enter updated information: ");
							
							update = input.next();
							input.nextLine();
							
							loginsys.updateUser(userid, updateChoice, update);
							
							
							
							
						}
						
						loginsys.logUserActions(liID, "Update User: " + updateChoice, "User ID: " + userid + "\nNew Value: " + update);
						break;
					
					
					}	
					
					//Removes a user from the MongoDB database
					case 3:
					
					{
						
						if (!permission.equals("admin")) {
							System.out.println("You do not have permission to remove users. Please contact a System Administrator.");
							break;
						}
						
						System.out.println("Please enter the User ID of the user you wish to remove: ");
						String id = input.next();
						input.nextLine();
						
						System.out.println("You are attempting to remove the following document: " + loginsys.findUser(id));
						System.out.println("Confirm deletion of document: (Y/N)");
					
						
						String confirmation = input.next();
						input.nextLine();
						
						if (confirmation.equals("Y")) {
							loginsys.removeUser(id);
							System.out.println("User deleted.");
							loginsys.logUserActions(liID, "Remove User", "User ID: " + id);
						}
						
						if(confirmation.equals("N")) {
							System.out.println("Deletion process has been halted. Returning to main menu");
						}
						
						
						break;
						
					}
					
					//Adds a new item to the mySQL database
					case 4:
						
						if (!permission.equals("admin")) {
							System.out.println("You do not have permission to add items. Please contact a System Administrator.");
							break;
						}
						
					{
						
						System.out.println("Enter ID of item: ");
						String id = input.next();
						input.nextLine();
						
						System.out.println("Enter name of item: ");
						String name = input.next();
						input.nextLine();
						
						System.out.println("Enter category of item: ");
						String category = input.next();
						input.nextLine();
						
						System.out.println("Enter quantity of item: ");
						String quantity = input.next();
						input.nextLine();
						
						System.out.println("Enter location of item: ");
						String location = input.next();
						input.nextLine();
						
						System.out.println("Enter supplier of item: ");
						String supplier = input.next();
						input.nextLine();
						
				
						invdatabase.addItem(id, name, category, quantity, location, supplier);
						
						loginsys.logUserActions(liID, "Add Item", "Item ID: " + id + "\nItem Name: " + name);
						break;
						
				
					}
					
					//update item information
					case 5:
						
						if (!permission.equals("admin")) {
							System.out.println("You do not have permission to update item information. Please contact a System Administrator.");
							break;
						}
					
					{
						
						System.out.println("Enter ID of item you want to update: ");
						int id = input.nextInt();
						input.nextLine();
						
						System.out.println("What would you like to update? (name/category/quantity/location/supplier): ");
						String updateChoice = input.next();
						input.nextLine();
						
						System.out.println("Enter new value: ");
						String newVal = input.next();
						input.nextLine();
						
						invdatabase.updateItem(id, updateChoice, newVal);
						
						loginsys.logUserActions(liID, "Update Item: " + updateChoice, "Item ID: " + id + "\nNew Value: " + newVal);
						break;
						
						
					}

					
					//Removes an item specified from the item id, from the mySQL database.
					case 6:
						
						if (!permission.equals("admin")) {
							System.out.println("You do not have permission to remove items. Please contact a System Administrator.");
							break;
						}
					
					{
					
						System.out.println("Enter the ID of the item you want to remove: ");
						int id = input.nextInt();
						input.nextLine();
						
						invdatabase.removeItem(id);
						loginsys.logUserActions(liID, "Remove Item", "Item ID: " + id);
						break;
						
						
					}
					
					//Searches for an item and displays the info of that item
					case 7:
						
					{
						
						System.out.println("Enter the ID of the item you want to search: ");
						int id = input.nextInt();
						input.nextLine();
						
						invdatabase.searchItem(id);
						System.out.println("\nPress Enter to continue...");
						input.nextLine();
						
						loginsys.logUserActions(liID, "Search for item", "Item ID: " + id);
						break;
						
					}

					//Prints an inventory report of a specific supplier
					case 8:
					
					{
						System.out.println("Please enter the name of the supplier: ");
						String supplier = input.next();
						input.nextLine();

						invdatabase.printReport("'"+supplier+"'");

						loginsys.logUserActions(liID, "Generate Report", "For supplier: " + supplier);
						
						break;
					}
					
					//logs the user out of the program, exiting.
					case 9: 
						
					{

						System.out.println("Logging out...");
						
						signedIn = false;
						
					}
				
				
				}
				
				
			}
			
			
			
			
		}
		
	}

