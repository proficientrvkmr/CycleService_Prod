package com.app;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.domain.StoreMaster;
import com.app.service.StoreService;
import com.app.util.JSONConverterUtil;
import com.app.util.RestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * @author Ravi Kumar
 *
 */
@Path("store")
public class StoreController {

	private StoreService storeService = new StoreService();

	@POST
	@Path("/register")
	@Produces("application/json;charset=UTF-8")
	public Response storeRegister(JSONObject object) throws JSONException {
		StoreMaster store = null;
		Response response = null;
		try {
			Object storeDetail = object.get("storeDetail");
			store = (StoreMaster) JSONConverterUtil.fromJson(storeDetail, StoreMaster.class);
			response = storeService.storeRegister(store);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
			throw new JSONException("wrong key-value pair");
		}
		return response;
	}

	@POST
	@Path("/findAllStoresNearestByMe")
	@Produces("application/json;charset=UTF-8")
	public Response findAllNearestByMe(JSONObject object) throws JSONException {
		double latitude = object.getDouble("latitude");
		double longitude = object.getDouble("longitude");
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
