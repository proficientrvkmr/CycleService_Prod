package com.app;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.services.RideService;

@Path("ride")
public class RideController {

	private RideService rideService = new RideService();

	@POST
	@Path("/getExpectedRidePrice")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRidePriceBetweenStation(JSONObject object) throws JSONException {
		double distance = object.getDouble("distance");
		return rideService.getRidePriceBetweenStation(distance);
	}

	@POST
	@Path("/sendMsg")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendMsg(JSONObject msg) throws JSONException {
		return rideService.sendMsg(msg.getString("msg").toString());
	}
}
