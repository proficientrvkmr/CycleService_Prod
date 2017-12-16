package com.app.config.mongodb;

import java.io.IOException;
import java.util.Set;

import org.bson.Document;

import com.app.config.resource.ResourceBundleFile;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class GeoMainTest {

	public static void main(String arg[]) throws IOException {
		String databaseName = ResourceBundleFile.getValueFromKey("mongodb.databaseName");
		String collectionName = ResourceBundleFile.getValueFromKey("mongodb.collectionName");
		String indexField = ResourceBundleFile.getValueFromKey("mongodb.indexField");
		
		MongoDatabase database = MongoDB.getMongoDatabase(databaseName);
		MongoCollection<Document> collection = database.getCollection(collectionName);
		FindIterable<Document> list = collection
				.find(Filters.geoWithinCenterSphere(indexField, 77.3559593, 28.574000, 3 / 6378.1));
		for (Document document : list) {
			Set<String> keys = document.keySet();
			for (String key : keys) {
				System.out.println(key + " : " + document.get(key).toString());
			}
			System.out.println("*******************");
		}
	}

}
