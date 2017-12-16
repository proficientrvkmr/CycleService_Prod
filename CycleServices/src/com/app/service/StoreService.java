package com.app.service;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.config.mongodb.MongoDB;
import com.app.config.resource.ResourceBundleFile;
import com.app.dao.StoreDetailDAO;
import com.app.domain.StoreMaster;
import com.app.domain.UserDetail;
import com.app.util.ApplicationConstant;
import com.app.util.JSONConverterUtil;
import com.app.util.RestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBList;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;

/**
 * 
 * @author Ravi Kumar
 *
 */
public class StoreService {
	private StoreDetailDAO storeDetailDAO = new StoreDetailDAO();
	private LoginService userService = new LoginService();

	public Response getAllStores() throws JsonProcessingException, JSONException {
		List<StoreMaster> storeList = storeDetailDAO.getAllStores();
		JSONArray array = JSONConverterUtil.toJsonArray(storeList);
		return RestResponse.withSuccessAndData(array);
	}

	public Response findAllNearestByMe(double longitude, double latitude, long radius, long userId) {
		UserDetail user = userService.getUserById(userId);
		// #distance to radians: divide the distance by the radius
		// #radians to distance: multiply the radian measure by
		double earthRadius = ApplicationConstant.earthRadiusInKM;
		String databaseName = ResourceBundleFile.getValueFromKey("mongodb.databaseName");
		String collectionName = ResourceBundleFile.getValueFromKey("mongodb.collectionName");
		String indexField = ResourceBundleFile.getValueFromKey("mongodb.indexField");

		if (user != null) {
			MongoDatabase database = MongoDB.getMongoDatabase(databaseName);
			MongoCollection<Document> collection = database.getCollection(collectionName);
			FindIterable<Document> list = collection
					.find(Filters.geoWithinCenterSphere(indexField, longitude, latitude, radius / earthRadius));
			JSONArray array = new JSONArray();
			for (Document document : list) {
				document.remove("_id");
				JSONObject object = null;
				try {
					object = JSONConverterUtil.toJson(document);
				} catch (JsonProcessingException | JSONException e) {
					e.printStackTrace();
				}
				if (object != null) {
					array.put(object);
				}
			}
			return RestResponse.withSuccessAndData(array);

		} else {

			return RestResponse
					.withErrorAndMessage("You are not authorized user. Please Login with valid credentials.");
		}
	}

	public StoreMaster getStoreById(long storeId) {
		return storeDetailDAO.getStoreById(storeId);
	}

	public Response moveFromMysqlToMongoDB() {
		List<StoreMaster> storeList = storeDetailDAO.getAllUnmappedStoresToMongoDB();
		String databaseName = ResourceBundleFile.getValueFromKey("mongodb.databaseName");
		String collectionName = ResourceBundleFile.getValueFromKey("mongodb.collectionName");
		String indexField = ResourceBundleFile.getValueFromKey("mongodb.indexField");

		MongoDatabase database = MongoDB.getMongoDatabase(databaseName);
		MongoCollection<Document> collection = database.getCollection(collectionName);
		for (StoreMaster store : storeList) {
			Document document = new Document();
			document.put("storeId", store.getId());
			document.put("storeName", store.getStoreName() != null ? store.getStoreName() : "");
			document.put("storeType", store.getStoreType() != null ? store.getStoreType() : "");
			document.put("storeAddress", store.getStoreAddress() != null ? store.getStoreAddress() : "");
			document.put("ownerName", store.getOwnerName() != null ? store.getOwnerName() : "");
			document.put("isActive", store.getIsActive() != null ? store.getIsActive() : "");
			document.put("contactNumber", store.getContactNumber() != null ? store.getContactNumber() : "");
			Document storeLocation = new Document();
			storeLocation.put("type", "Point");
			BasicDBList coordinates = new BasicDBList();
			coordinates.add(store.getLongitude());
			coordinates.add(store.getLatitude());
			storeLocation.put("coordinates", coordinates);
			document.put("storeLocation", storeLocation);
			collection.insertOne(document);
			ObjectId id = (ObjectId) document.get("_id");
			store.setMongoDocumentId(id.toHexString());
			storeDetailDAO.updateStore(store);
		}
		if (storeList.isEmpty()) {
			return RestResponse.withSuccessAndMessage("No new records found to migrate.");
		} else {
			collection.createIndex(Indexes.geo2dsphere(indexField));
			return RestResponse.withSuccessAndMessage("Successfully!! Data is migrated from Mysql to Mongo DB.");
		}

	}

	public Response storeRegister(StoreMaster store) throws JSONException {
		store.setCreatedDate(new Date());
		store.setModifiedDate(new Date());
		store.setIsActive("1");
		StoreMaster savedObject = storeDetailDAO.saveStore(store);
		JSONObject object = new JSONObject();
		object.put("storeId", savedObject.getId());
		object.put("message", "New store saved successfully.");
		return RestResponse.withSuccessAndData(object);
	}

}
