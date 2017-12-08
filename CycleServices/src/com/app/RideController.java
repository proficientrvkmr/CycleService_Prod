package com.app;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.app.services.RideService;

@Path("ride")
public class RideController {

	private RideService rideService = new RideService();

	@POST
	@Path("/getExpectedRidePrice")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRidePriceBetweenStation(@QueryParam("distance") int distance) {
		return rideService.getRidePriceBetweenStation(distance);
	}

	@POST
	@Path("/sendMsg")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendMsg(String msg) {
		return rideService.sendMsg(msg);
	}
}
