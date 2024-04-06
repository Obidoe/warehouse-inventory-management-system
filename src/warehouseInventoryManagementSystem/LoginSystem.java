package warehouseInventoryManagementSystem;

import static com.mongodb.client.model.Filters.eq;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;


//Database handler for users using noSQL (MongoDB)
public class LoginSystem {
		
	//connection string for MongoDB
	String uri = "mongodb+srv://admin:fWj3n2qMP9zqvA3w@cluster0.id3dh70.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
	static MongoDatabase database;
	static MongoCollection<Document> collection;
	
	//Singleton Implementation
	private static LoginSystem instance;
	private LoginSystem() {}
	
	public static LoginSystem getInstance() {
		if (instance == null) {
			instance = new LoginSystem();
		}
		return instance;
	}
	

	//Establishes connection to mongoDB and declares the main collection used. Collection could be set in each method, but since this application primarily uses "users", I included it here.
	public void establishConnection() {
		
		MongoClient mongoClient = MongoClients.create(uri);
		database = mongoClient.getDatabase("user_login");
		collection = database.getCollection("users");
		
	}
	
	//Test Code | Not Part of actual application
	public String findUser(String id) {
		
		Document doc = collection.find(eq("id", id)).first();
	    if (doc != null) {
	        return doc.toJson();
	    } else {
	        return null;
	    }
		
	}
	
	//Checks user information in the database and sees if it can find a match to the id and password passed in as parameters.
	public String login(String id, String pass) {
		
	    Document doc = collection.find(eq("id", id)).first();
	    
	    //If user enters an id that does not exist, this prevents an error.
	    if (doc == null) {
	    	//I chose this message instead of "ID does not exist" as a security measure.
	    	System.out.println("Incorrect Username or Password.");
	    	return null;
	    }
	    
	    //checks that the given password is equal to the value stored in the document
	    if (doc.getString("password").equals(pass)) {
	    	return (doc.getString("usertype"));
	    } else {
	        System.out.println("Incorrect Username or Password.");
	        
			return null;
	    }
	}
	

	//Add new user document to collection
	public void addUser(String id, String password, String firstName, String lastName, String email, String userType) {
		User user = new User(id, password, firstName, lastName, email, userType);
		
		//inserts a new document into the collection. Appends relevant information
		collection.insertOne(user.toDoc());
		
	}
	
	//Removes a user document based on the userID 
	public void removeUser(String id) {
		
		//finds the first document that matches this id
		Bson query = eq("id", id);
		
		collection.deleteOne(query);
		
	}
	
	//Updates user document based on id given and what the user chooses to update. 
	//If the user is an admin/manager, they can update any user. Staff can only update their own information.
	public void updateUser(String id, String choice, String update) {
		
		Document query = new Document().append("id", id);
		Bson updatedInfo = Updates.set(choice, update);
		collection.updateOne(query, updatedInfo);
		
	}
	
	//Logs user actions. 
	public void logUserActions(String id, String action, String value) {
		
		String timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
		MongoCollection<Document> activities = database.getCollection("user_activities");
		Document log = new Document()
				.append("_id", new ObjectId())
				.append("timestamp", timestamp)
				.append("user_id", id)
				.append("action", action)
				.append("details", value);
		activities.insertOne(log);
		
		
	}
	

	
}