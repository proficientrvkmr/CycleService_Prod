package com.app.services;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.util.RestResponse;

public class RideService {

	public Response getRidePriceBetweenStation(double distance) {
		double rate = 6.75;
		double totalCost = distance * rate;
		JSONObject object = new JSONObject();
		try {
			object.put("expectedCost", totalCost);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return RestResponse.withSuccessAndData(object);
	}

	public Response sendMsg(String msg) {
		JSONObject object = new JSONObject();
		try {
			object.put("message", "Very Nice Line, " + msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return RestResponse.withSuccessAndData(object);
	}
}
