package com.hr.connection;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * This class will be responsible to perform only connection related operations.
 * @author HR
 *
 */

public class MongoConnection {
	private String host;
	private int port;
	private String dbName;
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * This constructor will be help full when you know which DB you want to access. 
	 * @param hostName
	 * @param portNo
	 * @param databaseName
	 */
	public MongoConnection(String hostName, int portNo, String databaseName) {
		this.host = hostName;
		this.port = portNo;
		this.dbName = databaseName;
	}

	public MongoDatabase createConnection() {
		MongoClient objMongoClient = new MongoClient(this.host, this.port);
		return objMongoClient.getDatabase(this.dbName);
	}

	public void addOneDoc(String collectionName) {
		MongoDatabase objDb = createConnection();
		MongoCollection<Document> objDbCollection = objDb.getCollection(collectionName);
		Document document = new Document("Company","OpsHub Inc.")
				.append("Details", new BasicDBObject("FName","Hiren")
						.append("SName", "Savalia")
						.append("Age", "25")
						.append("Designation", "SE-1"));
		objDbCollection.insertOne(document);
	}
}
