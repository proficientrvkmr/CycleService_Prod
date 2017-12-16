package com.app;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.service.WalletService;

/**
 * 
 * @author Ravi Kumar
 *
 */
@Path("wallet")
public class WalletController {

	private WalletService walletService = new WalletService();

	@POST
	@Path("/checkBalance")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkBalance(JSONObject object) throws JSONException {
		long userId = object.getLong("userId");
		return walletService.checkBalance(userId);
	}
}
