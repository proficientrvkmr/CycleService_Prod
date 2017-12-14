package com.app;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.service.StoreService;
import com.app.util.RestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

@Path("store")
public class StoreController {

	private StoreService storeService = new StoreService();

	@POST
	@Path("/findAllStoresNearestByMe")
	@Produces("application/json;charset=UTF-8")
	public Response findAllNearestByMe(JSONObject object) throws JSONException {
		double latitude = object.getDouble("my_latitude");
		double longitude = object.getDouble("my_longitude");
		long radius = object.getLong("radius");
		long userId = object.getLong("userId");
		return storeService.findAllNearestByMe(longitude, latitude, radius, userId);
	}

	@POST
	@Path("/findAllStores")
	@Produces("application/json;charset=UTF-8")
	public Response getAllStores(JSONObject object) throws JSONException, JsonProcessingException {
		// String userId = object.getString("userId");
		return storeService.getAllStores();
	}

	@GET
	@Path("/checkServer")
	@Produces("application/json;charset=UTF-8")
	public Response checkServer() {
		String message = "Running Successfully";
		return RestResponse.withSuccessAndMessage(message);
	}
	
	@GET
	@Path("/moveFromMysqlToMongoDB")
	@Produces("application/json;charset=UTF-8")
	public Response moveFromMysqlToMongoDB() {
		return storeService.moveFromMysqlToMongoDB();
	}

}
