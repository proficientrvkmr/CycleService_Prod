package com.app;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.service.RideService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * @author Ravi Kumar
 *
 */
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
		long startingStationId = startStation.getLong("stationId");
		// double startingStationLongitude =
		// startStation.getDouble("longitude");
		// double startingStationLatitude = startStation.getDouble("latitude");

		JSONObject endStation = (JSONObject) object.get("endStation");
		long endingStationId = endStation.getLong("stationId");
		// double endingStationLongitude = endStation.getDouble("longitude");
		// double endingStationLatitude = endStation.getDouble("latitude");

		long bikeId = object.getLong("bikeId");
		long userId = object.getLong("userId");
		return rideService.rideStarts(startingStationId, endingStationId, userId, bikeId);
	}

	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateLastLocation(JSONObject object) throws JSONException {
		long rideId = object.getLong("rideId");
		String rideStartTime = object.getString("rideStartTime");
		String rideEndTime = object.getString("rideEndTime");

		JSONObject startPoint = object.getJSONObject("startPoint");
		double startingLatitude = startPoint.getDouble("latitude");
		double startingLongitude = startPoint.getDouble("longitude");

		JSONObject endPoint = object.getJSONObject("endPoint");
		double endingLatitude = endPoint.getDouble("latitude");
		double endingLongitude = endPoint.getDouble("longitude");

		return rideService.updateRide(rideId, rideStartTime, rideEndTime, startingLatitude, startingLongitude,
				endingLatitude, endingLongitude);
	}

	@POST
	@Path("/complete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response rideCompleted(JSONObject object) throws JSONException {
		long rideId = object.getLong("rideId");
		String rideStartTime = object.getString("rideStartTime");
		String rideEndTime = object.getString("rideEndTime");
		
		JSONObject startPoint = object.getJSONObject("startPoint");
		double startingLatitude = startPoint.getDouble("latitude");
		double startingLongitude = startPoint.getDouble("longitude");

		JSONObject endPoint = object.getJSONObject("endPoint");
		double endingLatitude = endPoint.getDouble("latitude");
		double endingLongitude = endPoint.getDouble("longitude");

		double distanceTravel = object.getDouble("distanceTravel");
		long timeTravel = object.getLong("timeTaken");
		return rideService.completeRide(rideId, rideStartTime, rideEndTime, startingLatitude, startingLongitude,
				endingLatitude, endingLongitude, distanceTravel, timeTravel);
	}

	@POST
	@Path("/getAllRideByUserId")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllRideByUserId(JSONObject object) throws JSONException, JsonProcessingException {
		long userId = object.getLong("userId");
		return rideService.getAllRideByUserId(userId);
	}
}
