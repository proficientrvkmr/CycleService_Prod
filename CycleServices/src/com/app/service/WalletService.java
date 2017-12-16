package com.app.service;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.dao.WalletDetailDAO;
import com.app.domain.UserDetail;
import com.app.util.RestResponse;

/**
 * 
 * @author Ravi Kumar
 *
 */
public class WalletService {

	private WalletDetailDAO walletDetailDAO = new WalletDetailDAO();
	private LoginService userService = new LoginService();

	public Response checkBalance(long userId) {
		UserDetail user = userService.getUserById(userId);
		if (user != null) {
			double availableBalance = walletDetailDAO.getAvailableBalance(userId);
			JSONObject result = new JSONObject();
			try {
				result.put("availableBalance", availableBalance);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return RestResponse.withSuccessAndData(result);
		} else {
			return RestResponse.withErrorAndMessage("Invalid user. please check again. ");
		}

	}
}
