package com.app.service;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.dao.BikeDetailDao;
import com.app.domain.BikeDetail;
import com.app.util.RestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * @author Ravi Kumar
 *
 */
public class BikeService {

	private BikeDetailDao bikeDetailDao = new BikeDetailDao();

	public Response bikeRegister(BikeDetail bikeDetail) throws JsonProcessingException, JSONException {
		BikeDetail savedObject = bikeDetailDao.saveBikeDetail(bikeDetail);
		JSONObject object = new JSONObject();
		//object.put("bikeDetail", JSONConverterUtil.toJson(savedObject));
		object.put("bikeId", savedObject.getId());
		object.put("bikeName", savedObject.getBikeName());
		object.put("vehicleNo", savedObject.getVehicleNo());
		return RestResponse.withSuccessAndData(object);
	}

	public BikeDetail getBikeById(long bikeId) {
		return bikeDetailDao.getBikeDetailById(bikeId);
	}

}
