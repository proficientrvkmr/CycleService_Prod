package com.app;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.services.StoreService;

@Path("store")
public class StoreController {

	private StoreService storeService = new StoreService();

	@POST
	@Path("/getStoreInfo")
	@Produces("application/json;charset=UTF-8")
	public Response getStoreInfo(JSONObject object) throws JSONException {
		String longitude = object.getString("longitude").toString();
		String latitude = object.getString("latitude").toString();
		String distance = object.getString("distance").toString();
		return storeService.getStoreInfo(longitude, latitude, distance);
	}

	@GET
	@Path("/checkServer")
	@Produces("application/json;charset=UTF-8")
	public Response checkServer() {
		return storeService.checkServer();
	}

}
