package com.app;

import java.io.IOException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.domain.StoreMaster;
import com.app.service.StoreService;
import com.app.util.JSONConverterUtil;
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

//	@POST
//	@Path("/findAllStoresNearestByMe")
//	@Produces("application/json;charset=UTF-8")
//	public Response findAllNearestByMe(JSONObject object) throws JSONException {
//		double latitude = object.getDouble("latitude");
//		double longitude = object.getDouble("longitude");
//		long radius = object.getLong("radius");
//		long userId = object.getLong("userId");
//		return storeService.findAllNearestByMe(longitude, latitude, radius, userId);
//	}
	
	@POST
	@Path("/findAllStoresNearestByMe")
	@Produces("application/json;charset=UTF-8")
	public Response findAllNearestByMe(@QueryParam("latitude") double latitude,
			@QueryParam("longitude") double longitude,
			@QueryParam("radius") long radius, @QueryParam("userId") long userId)
			throws JSONException {
		return storeService.findAllNearestByMe(longitude, latitude, radius,
				userId);
	}

	@POST
	@Path("/findAllStores")
	@Produces("application/json;charset=UTF-8")
	public Response getAllStores() throws JSONException, JsonProcessingException {
		return storeService.getAllStores();
	}

}
