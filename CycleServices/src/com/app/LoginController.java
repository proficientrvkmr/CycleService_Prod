package com.app;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.app.services.LoginService;

@Path("login")
public class LoginController {

	private LoginService loginService = new LoginService();

	@GET
	@Path("/test")
	@Produces("application/json;charset=UTF-8")
	public Response test() {
		return loginService.test();
	}

	@POST
	@Path("/signupUser")
	@Produces("application/json;charset=UTF-8")
	public Response signupUser(@QueryParam("email") String email, @QueryParam("contactNo") String contactNo) {
		return loginService.signupUser(email, contactNo);
	}

	@POST
	@Path("/loginUser")
	@Produces("application/json;charset=UTF-8")
	public Response loginUser(@QueryParam("contactNo") String contactNo) {
		return loginService.loginUser(contactNo);
	}

	@POST
	@Path("/signupUserWithFB")
	@Produces("application/json;charset=UTF-8")
	public Response loginUserWithFB(@QueryParam("email") String email, @QueryParam("fbId") String facebookId,
			@QueryParam("userName") String userName, @QueryParam("contactNo") String contactNo) {
		return loginService.loginUserWithFB(email, facebookId, userName, contactNo);
	}

	@POST
	@Path("/resendOTP")
	@Produces("application/json;charset=UTF-8")
	public Response resendOTP(@QueryParam("contactNo") String contactNo) {
		return loginService.resendOTP(contactNo);
	}

	@POST
	@Path("/validateOTP")
	@Produces("application/json;charset=UTF-8")
	public Response validateOTP(@QueryParam("email") String email, @QueryParam("otpString") String otpString) {
		return loginService.validateOTP(email, otpString);
	}

}