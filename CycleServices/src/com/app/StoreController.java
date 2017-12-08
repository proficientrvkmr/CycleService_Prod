package com.app;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.app.services.StoreService;

@Path("store")
public class StoreController {

	private StoreService storeService = new StoreService();

	@POST
	@Path("/getStoreInfo")
	@Produces("application/json;charset=UTF-8")
	public Response getStoreInfo(@QueryParam("longitude") String longitude, @QueryParam("latitude") String latitude,
			@QueryParam("distance") String distance) {
		return storeService.getStoreInfo(longitude, latitude, distance);
	}

	@GET
	@Path("/checkServer")
	@Produces("application/json;charset=UTF-8")
	public Response checkServer() {
		return storeService.checkServer();
	}

}
