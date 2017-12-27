package com.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.app.service.LoginService;
import com.app.service.StoreService;
import com.app.util.RestResponse;

@Path("")
public class HomeController {

	private LoginService loginService = new LoginService();
	private StoreService storeService = new StoreService();

	@GET
	@Path("/testMail")
	@Produces("application/json;charset=UTF-8")
	public Response test() {
		return loginService.testMail();
	}

	@GET
	@Path("/")
	@Produces("application/json;charset=UTF-8")
	public Response checkServer() {
		String message = "Running Successfully";
		return RestResponse.withSuccessAndMessage(message);
	}

	@GET
	@Path("/moveFromMysqlToMongoDB")
	@Produces("application/json;charset=UTF-8")
	public Response moveFromMysqlToMongoDB() {
		return storeService.moveFromMysqlToMongoDB();
	}
}
