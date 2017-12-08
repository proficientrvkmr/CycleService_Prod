package com.app.services;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.app.dao.StoreDetailDAO;
import com.app.util.RestResponse;

public class StoreService {

	public Response getStoreInfo(String longitude, String latitude,
			String distance) {
		JSONArray array = new JSONArray();
		StoreDetailDAO storeDetailDAO = new StoreDetailDAO();
		array = storeDetailDAO.getStoreInfo(longitude, latitude, distance);
		return RestResponse.withSuccessAndData(array);
	}

	public Response checkServer() {
		String message = "Running Successfully";
		return RestResponse.withSuccessAndMessage(message);
	}

}
