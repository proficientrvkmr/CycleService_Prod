package com.app.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.dao.RideDetailDao;
import com.app.domain.BikeDetail;
import com.app.domain.RideDetail;
import com.app.domain.RideStatus;
import com.app.domain.StoreMaster;
import com.app.domain.UserDetail;
import com.app.util.ApplicationConstant;
import com.app.util.JSONConverterUtil;
import com.app.util.RestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * @author Ravi Kumar
 *
 */
public class RideService {

	private static final Logger logger = LoggerFactory.getLogger(RideService.class);

	private BikeService bikeService = new BikeService();
	private LoginService userService = new LoginService();
	private RideDetailDao rideDetailDao = new RideDetailDao();
	private StoreService storeService = new StoreService();

	public Response getRidePriceBetweenStation(double distance) {
		double totalCost = distance * ApplicationConstant.rideDistanceRATE;
		JSONObject object = new JSONObject();
		try {
			object.put("expectedCost", totalCost);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return RestResponse.withSuccessAndData(object);
	}

	public Response validateQRCode(String qrCode, long userId, long bikeId) throws JSONException {
		boolean isQRValid = false;
		UserDetail user = userService.getUserById(userId);
		BikeDetail bike = bikeService.getBikeById(bikeId);
		JSONObject object = new JSONObject();
		if (bike.getQrCode().equals(qrCode) && user != null) {
			isQRValid = true;
			object.put("message", "QR code matched.");
		} else {
			object.put("message", "Sorry, QR code is not matched. Please try again.");
		}
		object.put("isQRValid", isQRValid);
		return RestResponse.withSuccessAndData(object);
	}

	public Response rideStarts(long startingStationId, long endingStationId, long userId, long bikeId) {
		BikeDetail bikeUsed = bikeService.getBikeById(bikeId);
		StoreMaster startingStation = storeService.getStoreById(startingStationId);
		StoreMaster endingStation = storeService.getStoreById(endingStationId);
		UserDetail user = userService.getUserById(userId);

		if (bikeUsed != null && startingStation != null && endingStation != null && user != null) {
			RideDetail newRide = new RideDetail();
			newRide.setBikeDetail(bikeUsed);
			newRide.setRideStartPointStationId(startingStation);
			newRide.setRideEndPointStationId(endingStation);
			Date currentTime = new Date();
			newRide.setRideStartTime(currentTime);
			newRide.setRideEndTime(currentTime);
			newRide.setCurrentStatus(RideStatus.TO_BE_STARTED.name());
			newRide.setUserDetail(user);
			try {
				RideDetail savedObject = rideDetailDao.saveRide(newRide);
				JSONObject object = new JSONObject();
				object.put("message", "Your ride starts NOW !!");
				object.put("rideId", savedObject.getId());
				return RestResponse.withSuccessAndData(object);
			} catch (Exception e) {
				logger.error("===================**************=================");
				logger.error(e.toString());
				logger.error("===================**************=================");
				return RestResponse.withErrorAndMessage("Internal error occurs while save your ride.");
			}
		} else {
			return RestResponse.withErrorAndMessage("Input parameters are invalid.");
		}

	}

	public Response updateRide(long rideId, String rideStartTime, String rideEndTime, double startingLatitude,
			double startingLongitude, double endingLatitude, double endingLongitude) {
		RideDetail ride = rideDetailDao.getRideDetailById(rideId);
		if (ride != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				ride.setRideStartTime(dateFormat.parse(rideStartTime));
				ride.setRideEndTime(dateFormat.parse(rideEndTime));
			} catch (ParseException e1) {
				logger.error("===================**************=================");
				logger.error(e1.toString());
				logger.error("===================**************=================");
			}
			ride.setStartingLatitude(startingLatitude);
			ride.setStartingLongitude(startingLongitude);
			ride.setEndingLatitude(endingLatitude);
			ride.setEndingLongitude(endingLongitude);
			ride.setCurrentStatus(RideStatus.IN_PROGRESS.name());
			try {
				RideDetail savedObject = rideDetailDao.updateRide(ride);
				JSONObject object = new JSONObject();
				object.put("message", "Update successfully !!");
				object.put("rideId", savedObject.getId());
				return RestResponse.withSuccessAndData(object);
			} catch (Exception e) {
				logger.error("===================**************=================");
				logger.error(e.toString());
				logger.error("===================**************=================");
				return RestResponse.withErrorAndMessage("Internal error occurs while update your ride.");
			}
		} else {
			return RestResponse.withErrorAndMessage("Sorry, this Ride is not known for us.");
		}
	}

	public Response completeRide(long rideId, String rideStartTime, String rideEndTime, double startingLatitude,
			double startingLongitude, double endingLatitude, double endingLongitude, double distanceTravel,
			long timeTravel) {
		RideDetail ride = rideDetailDao.getRideDetailById(rideId);
		if (ride != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				ride.setRideStartTime(dateFormat.parse(rideStartTime));
				ride.setRideEndTime(dateFormat.parse(rideEndTime));
			} catch (ParseException e1) {
				logger.error("===================**************=================");
				logger.error(e1.toString());
				logger.error("===================**************=================");
			}
			ride.setDistanceTravel(distanceTravel);
			ride.setTimeTravel(timeTravel);
			ride.setStartingLatitude(startingLatitude);
			ride.setStartingLongitude(startingLongitude);
			ride.setEndingLatitude(endingLatitude);
			ride.setEndingLongitude(endingLongitude);
			ride.setCurrentStatus(RideStatus.COMPLETE.name());
			double totalFare = distanceTravel * ApplicationConstant.rideDistanceRATE;
			totalFare = totalFare + timeTravel * ApplicationConstant.rideTimeRATE;
			ride.setTotalFare(totalFare);

			try {
				RideDetail savedObject = rideDetailDao.updateRide(ride);
				JSONObject object = new JSONObject();
				object.put("message", "Congratulations!! You have complete your ride successfully.");
				object.put("rideId", savedObject.getId());
				object.put("distanceTravel", savedObject.getDistanceTravel());
				object.put("timeTaken", savedObject.getTimeTravel());
				object.put("totalFare", savedObject.getTotalFare());
				return RestResponse.withSuccessAndData(object);
			} catch (Exception e) {
				logger.error("===================**************=================");
				logger.error(e.toString());
				logger.error("===================**************=================");
				return RestResponse.withErrorAndMessage("Internal error occurs while complete save your ride.");
			}
		} else {
			return RestResponse.withErrorAndMessage("Sorry, this Ride is not known for us.");
		}
	}

	public Response getAllRideByUserId(long userId) throws JsonProcessingException, JSONException {
		UserDetail user = userService.getUserById(userId);
		if (user != null) {
			List<RideDetail> allRides = rideDetailDao.getAllRideByUserId(userId);
			JSONArray array = JSONConverterUtil.toJsonArray(allRides);
			return RestResponse.withSuccessAndData(array);
		} else {
			return RestResponse.withErrorAndMessage("Sorry, unable to identify the login user.");
		}
	}

}
