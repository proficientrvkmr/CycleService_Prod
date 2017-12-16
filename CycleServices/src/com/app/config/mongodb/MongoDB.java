package com.app.config.mongodb;

import com.app.config.resource.ResourceBundleFile;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;

/**
 * 
 * @author Ravi Kumar
 *
 */
public class MongoDB {

//	public static final String host = "127.0.0.1";
//	public static final int port = 27017;
//	public static String databaseName = "geoLocation";
//	public static String collectionName = "bikestores";
//	public static String indexField = "storeLocation";
	
	private static MongoClient mongoClient;

	private MongoDB() {

	}

	public static MongoClient getMongoClient() {
		if (mongoClient == null) {
			String host = ResourceBundleFile.getValueFromKey("mongodb.host");
			String port = ResourceBundleFile.getValueFromKey("mongodb.port");
			mongoClient = new MongoClient(host, Integer.valueOf(port));
		}
		return mongoClient;
	}

	public static MongoDatabase getMongoDatabase(String databaseName) {
		MongoDatabase database = null;
		try {
			database = getMongoClient().getDatabase(databaseName);
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return database;
	}
}
