package com.test.mongo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

public class CrudTest {
	
	static MongoClient mongoClient;
	static MongoDatabase db;
	
	@BeforeClass
	public static void beforeClass() {
		mongoClient = new MongoClient("localhost", 27017);
		db = mongoClient.getDatabase("UnitTestDb");
		db.createCollection("Student");
	}
	
	@Test
	public void testInsertSingleDocument() {
		Document document = new Document("name", "Hiren")
				.append("job", "se");
		// insert into collection
		db.getCollection("Student").insertOne(document);
		// get same document from db using query
		Document queryDoc = new Document("name","Hiren");
		FindIterable<Document> docs = db.getCollection("Student").find(queryDoc);
		Document firstDoc = docs.first();
		assertEquals("Hiren", String.valueOf(firstDoc.get("name")));
	}
	
	@Test
	public void testUpdateSingleDocument() {
		Document document = new Document("name", "Hiren")
				.append("job", "se");
		// insert into collection
		db.getCollection("Student").insertOne(document);
		Document queryDoc = new Document("job","se");
		Document docToUpdate = new Document("job","Software Engineer");
		db.getCollection("Student").updateOne(queryDoc, new Document("$set", docToUpdate));
		FindIterable<Document> updatedDocFromDb = db.getCollection("Student").find(docToUpdate);
		assertThat(String.valueOf(updatedDocFromDb.first().get("job")), is("Software Engineer"));
	}
	
	@Test
	public void testDeleteSingleDocument() {
		Document document = new Document("name", "Hiren")
				.append("job", "se");
		// insert into collection
		db.getCollection("Student").insertOne(document);
		Document queryDoc = new Document("name","Hiren");
		DeleteResult deleteResutl = db.getCollection("Student").deleteOne(queryDoc);
		long deleteCount = deleteResutl.getDeletedCount();
		assertEquals("Checking delete operation.", 1L, deleteCount);
	}
	
	@Test
	public void testMultipleUpdate() {
		List<Document> docsToAdd = new ArrayList<Document>(5);
		Document document1 = new Document("name", "Hiren").append("job", "se0");
		Document document2 = new Document("name", "Ashish").append("job", "se1");
		Document document3 = new Document("name", "Jay").append("job", "se2");
		Document document4 = new Document("name", "Joy").append("job", "se3");
		Document document5 = new Document("name", "Loy").append("job", "se4");
		docsToAdd.add(document1);
		docsToAdd.add(document2);
		docsToAdd.add(document3);
		docsToAdd.add(document4);
		docsToAdd.add(document5);
		// insert into collection
		db.getCollection("Student").insertMany(docsToAdd);
		db.getCollection("Student").findOneAndUpdate(new Document("job","se2"), new Document("$set",new Document("job","lead1")));
		db.getCollection("Student").findOneAndUpdate(new Document("job","se3"), new Document("$set",new Document("job","lead2")));
		FindIterable<Document> results = db.getCollection("Student").find();
		for (Document document : results) {
			if(document.get("job").equals("lead1")) {
				assertEquals("lead1", String.valueOf(document.get("job")));
			}
			if(document.get("job").equals("lead2")) {
				assertEquals("lead2", String.valueOf(document.get("job")));
			}
		}
	}
	
	@AfterClass
	public static void afterClass() {
		db.drop();
	}

}
