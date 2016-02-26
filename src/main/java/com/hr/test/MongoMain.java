package com.hr.test;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Set;

import org.bson.Document;

import com.hr.connection.MongoConnection;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.FindOneAndDeleteOptions;

public class MongoMain {

	public static void main(String[] args) throws UnknownHostException {
		// get connection and db object
		MongoConnection objMongoConnection = new MongoConnection("localhost", 27017, "TestDB");
		MongoDatabase objDb = objMongoConnection.createConnection();
		
		// get all collections from db
		MongoIterable<String> allCollections = objDb.listCollectionNames();
		for (String collectionName : allCollections) {
			System.out.println(collectionName);
		}
		
		// let us print add data from EmpCollection 
		MongoCollection objDbCollection = objDb.getCollection("EmpCollection");
		FindIterable<Document> objDbCursor = objDbCollection.find();
		System.out.println("Before-----");
		for (Document document : objDbCursor) {
			System.out.println(document);
		}
		
		// add one sample record into database
		// objMongoConnection.addOneDoc("EmpCollection");
		
		// find one doc and update it
		Document docToDelete = new Document("Designation", "SE-1");
		objDbCollection.findOneAndUpdate(new Document("Company", "OpsHub Inc."), new Document("$unset", docToDelete));
		
		System.out.println("After-----");
		for (Document document : objDbCursor) {
			System.out.println(document);
		}
		
	}
}
