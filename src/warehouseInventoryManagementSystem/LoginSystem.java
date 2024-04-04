package warehouseInventoryManagementSystem;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

public class LoginSystem {
	//Database handler for users using noSQL (MongoDB)
		
	//connection string for MongoDB
	String uri = "";
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
	

	//connection to mongodb
	public void establishConnection() {
		
		MongoClient mongoClient = MongoClients.create(uri);
		database = mongoClient.getDatabase("user_login");
		collection = database.getCollection("users");
		
	}
	
	//prints user information (currently using sample database)
	public String findUser(String id) {
		
		Document doc = collection.find(eq("id", id)).first();
	    if (doc != null) {
	        return doc.toJson();
	    } else {
	        return null;
	    }
		
	}
	
	//taking id as input, testing if that doc has the password then returns the user type
	public String login(String id, String pass) {
		
	    Document doc = collection.find(eq("id", id)).first();
	    
	    
	    //checks that the given password is equal to the value stored in the document
	    if (doc.getString("password").equals(pass)) {
	    	return (doc.getString("usertype"));
	    } else {
	        System.out.println("No matching documents found.");

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
	

	
}