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
	private StoreDetailDAO storeDetailDAO = new StoreDetailDAO();

	public Response getAllStores() throws JsonProcessingException, JSONException {
		List<StoreMaster> storeList = storeDetailDAO.getAllStores();
		JSONArray array = JSONConverterUtil.toJsonArray(storeList);
		return RestResponse.withSuccessAndData(array);
	}

	public Response findAllNearestByMe(String longitude, String latitude, String userId) {
		return RestResponse.withSuccessAndMessage("Still need to add logic to find nearest by me");
	}

	public StoreMaster getStoreById(long storeId) {
		return storeDetailDAO.getStoreById(storeId);
	}

}
