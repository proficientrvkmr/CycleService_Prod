package com.app;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.app.services.WalletService;

@Path("wallet")
public class WalletController {

	private WalletService walletService = new WalletService();

	@POST
	@Path("/checkBalance")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkBalance(@QueryParam("userId") long userId) {
		return walletService.checkBalance(userId);
	}
}
