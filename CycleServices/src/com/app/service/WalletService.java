package com.app.service;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.dao.WalletDetailDAO;
import com.app.util.RestResponse;

public class WalletService {

	private WalletDetailDAO walletDetailDAO = new WalletDetailDAO();

	public Response checkBalance(long userId) {
		double availableBalance = walletDetailDAO.getAvailableBalance(userId);
		JSONObject result = new JSONObject();
		try {
			result.put("availableBalance", availableBalance);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return RestResponse.withSuccessAndData(result);

	}
}
