package com.app;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.service.RideService;

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
	@Path("/validateQRCode")
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateQRCode(JSONObject object) throws JSONException {
		String qrCode = object.getString("qrCode");
		long userId = object.getLong("userId");
		long bikeId = object.getLong("bikeId");
		return rideService.validateQRCode(qrCode, userId, bikeId);
	}
	
	@POST
	@Path("/start")
	@Produces(MediaType.APPLICATION_JSON)
	public Response rideStarts(JSONObject object) throws JSONException {
		JSONObject startStation = (JSONObject) object.get("startStation");
		String startingStationId = startStation.getString("stationId");
//		String startingStationLongitude = startStation.getString("longitude");
//		String startingStationLatitude = startStation.getString("latitude");
		
		JSONObject endStation = (JSONObject) object.get("endStation");
		String endingStationId = endStation.getString("stationId");
//		String endingStationLongitude = endStation.getString("longitude");
//		String endingStationLatitude = endStation.getString("latitude");
		
		String bikeId = object.getString("bikeId");
		String userId = object.getString("userId");
		return rideService.rideStarts(startingStationId, endingStationId, userId, bikeId);
	}
	
	@POST
	@Path("/updateLastLocation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateLastLocation(JSONObject object) throws JSONException {
		String rideId = object.getString("rideId");
		Object rideStartTime = object.get("rideStartTime");
		Object rideEndTime = object.get("rideEndTime");
		return rideService.updateRide(rideId, rideStartTime, rideEndTime);
	}
	
	
	@POST
	@Path("/complete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response rideCompleted(JSONObject object) throws JSONException {
		String rideId = object.getString("rideId");
		Object rideStartTime = object.get("rideStartTime");
		Object rideEndTime = object.get("rideEndTime");
		double distanceTravel = object.getDouble("distanceTravel");
		long timeTravel = object.getLong("timeTaken");
		return rideService.completeRide(rideId, rideStartTime, rideEndTime, distanceTravel, timeTravel);
	}
}
