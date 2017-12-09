package com.app.service;

import java.util.List;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import com.app.dao.StoreDetailDAO;
import com.app.domain.StoreMaster;
import com.app.util.JSONConverterUtil;
import com.app.util.RestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public class StoreService {

	public Response getAllStores(String longitude, String latitude,
			String distance) throws JsonProcessingException, JSONException {
		StoreDetailDAO storeDetailDAO = new StoreDetailDAO();
		List<StoreMaster> storeList = storeDetailDAO.getAllStores();
		JSONArray array = JSONConverterUtil.toJsonArray(storeList);
		return RestResponse.withSuccessAndData(array);
	}

	public Response checkServer() {
		String message = "Running Successfully";
		return RestResponse.withSuccessAndMessage(message);
	}

	public Response findAllNearestByMe(String longitude, String latitude, String userId) {
		return RestResponse.withSuccess();
	}

}
