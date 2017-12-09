package com.app.service;

import java.util.Date;

import javax.ws.rs.core.Response;

import org.apache.http.client.utils.DateUtils;
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
import com.app.util.RestResponse;

public class RideService {

	private static final Logger logger = LoggerFactory.getLogger(RideService.class);
	private static final double RATE = 6.75;
	private BikeService bikeService = new BikeService();
	private LoginService userService = new LoginService();
	private RideDetailDao rideDetailDao = new RideDetailDao();

	public Response getRidePriceBetweenStation(double distance) {
		double totalCost = distance * RATE;
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

	public Response rideStarts(String startingStationId, String endingStationId, String userId, String bikeId) {
//		BikeDetail bikeUsed = new BikeDetail();
//		bikeUsed.setId(Long.parseLong(bikeId));
//		StoreMaster startingStation = new StoreMaster();
//		startingStation.setId(Long.parseLong(startingStationId));
//		StoreMaster endingStation = new StoreMaster();
//		endingStation.setId(Long.parseLong(endingStationId));
//		UserDetail user = new UserDetail();
//		user.setId(Long.parseLong(userId));

		RideDetail newRide = new RideDetail();
		newRide.setBikeDetailId(Long.parseLong(bikeId));
//		newRide.setRideStartPointStationId(startingStation);
//		newRide.setRideEndPointStationId(endingStation);
		Date currentTime = new Date();
		newRide.setRideStartTime(currentTime);
		newRide.setRideEndTime(currentTime);
		newRide.setCurrentStatus(RideStatus.TO_BE_STARTED.name());
//		newRide.setUserDetail(user);

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
	}

	public Response updateRide(String rideId, Object rideStartTime, Object rideEndTime) {
		RideDetail ride = new RideDetail();
		ride.setId(Long.parseLong(rideId));
		ride.setRideStartTime(DateUtils.parseDate(rideStartTime.toString()));
		ride.setRideEndTime(DateUtils.parseDate(rideEndTime.toString()));
		ride.setCurrentStatus(RideStatus.IN_PROGRESS.name());

		try {
			RideDetail savedObject = rideDetailDao.updateRide(ride);
			return RestResponse.withSuccessAndData(savedObject);
		} catch (Exception e) {
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
			return RestResponse.withErrorAndMessage("Internal error occurs while update your ride.");
		}
	}

	public Response completeRide(String rideId, Object rideStartTime, Object rideEndTime, double distanceTravel,
			long timeTravel) {
		RideDetail ride = new RideDetail();
		ride.setId(Long.parseLong(rideId));
		ride.setRideStartTime(DateUtils.parseDate(rideStartTime.toString()));
		ride.setRideEndTime(DateUtils.parseDate(rideEndTime.toString()));
		ride.setDistanceTravel(distanceTravel);
		ride.setTimeTravel(timeTravel);
		ride.setCurrentStatus(RideStatus.COMPLETE.name());
		double totalFare = distanceTravel * RATE;
		ride.setTotalFare(totalFare);

		try {
			RideDetail savedObject = rideDetailDao.updateRide(ride);
			return RestResponse.withSuccessAndData(savedObject);
		} catch (Exception e) {
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
			return RestResponse.withErrorAndMessage("Internal error occurs while complete save your ride.");
		}
	}

}
