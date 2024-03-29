package warehouseInventoryManagementSystem;

import java.util.Scanner;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.sql.*;

public class App {
	

	//this menu is displayed as the program is launched. A user is required to sign in before they can access anything else. They also have the option to exit.
	public static void launchMenu() { 
		System.out.println("Welcome to the Warehouse Inventory Management System.");
		System.out.println("To continue, press '1' to login. Otherwise, press '2' to exit.");
		System.out.println("\n1. Login");
		System.out.println("2. Exit");
	}
	
	public static void adminMenu() {
		System.out.println("Hello System Administrator, welcome to the admin panel! Please choose what you would like to do from the options below: \n");
		System.out.println("1. Add New Use");
		System.out.println("2. Update User Information");
		System.out.println("3. Remove User");
		System.out.println("4. Log Out");
		
	}
	
	public static void managerMenu() {
		
	}
	
	public static void staffMenu() {
		
	}
	
	public static void main(String[] args) throws SQLException {
		
		//connection to mongodb
		String uri = "";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");
            Document doc = collection.find(eq("title", "Back to the Future")).first();
            if (doc != null) {
                System.out.println(doc.toJson());
            } else {
                System.out.println("No matching documents found.");
            }
        }
        
        //connection to mysql
        String url = "";
        String user = "";
        String sqlpassword = "";
        
        Connection myConn = DriverManager.getConnection(url,user,sqlpassword);
        Statement myStatement = myConn.createStatement();
        String sql = "";
        ResultSet rs = myStatement.executeQuery(sql);
        
        while (rs.next())
        	
        {
        	System.out.println(rs.getString(""));
        }
        
		boolean signedIn = false;
		boolean loggedOut = true;
		
		
		Scanner input = new Scanner(System.in);
			
			//This while loop pertains to the launch menu and logging in to the system.
			while(loggedOut == true) {

				launchMenu();
				
				int choice = input.nextInt();
				
				//Login
				if (choice == 1) {
					
					System.out.println("Please enter your 7-digit User ID Number: ");
					
					int id = input.nextInt();
					
					//Check for id in database
					
					System.out.println("Please enter your password: ");
					
					String password = input.next();
					input.nextLine();
					
					//Check if password connected to id is valid. If not, try again.
					
					//if valid, break
					if (id == 1001001 && password.equals("1234")) {
						
					System.out.println("Logging in as... ");
					
					signedIn = true;
					
					}
					
					break;
					
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
			
			//This loop handles everything that happens while logged in. First we check to see what type of user has logged into the system, then we display the menu pertaining to the type.
			
			//currently giving me an error. Gotta fix over the weekend.
			while(signedIn == true) {
				
				adminMenu();
				
				int choice = input.nextInt();
				
				switch(choice) {
				
					case 1:
						
					{
						
						System.out.println("You chose 1");
						break;
						
					}
					
					case 2:
						
					{
						
						System.out.println("You chose 2");
						break;
					
					}	
					
					case 3:
					
					{
						
						
					}
					
					case 4:
						
					{
						
						System.out.println("Logging out...");
						
						signedIn = false;
						
					}
				
				
				}
				
				
			}
			
			
			
			
		}
		
	}

