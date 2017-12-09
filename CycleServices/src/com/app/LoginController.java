package com.app;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.service.LoginService;

@Path("login")
public class LoginController {

	private LoginService loginService = new LoginService();

	@GET
	@Path("/test")
	@Produces("application/json;charset=UTF-8")
	public Response test() {
		return loginService.testMail();
	}

	@POST
	@Path("/signupUser")
	@Produces("application/json;charset=UTF-8")
	public Response signupUser(JSONObject object) throws JSONException{
		String email = object.get("email").toString();
		String contactNo = object.get("contactNo").toString();
		return loginService.signupUser(email, contactNo);
	}

	@POST
	@Path("/loginUser")
	@Produces("application/json;charset=UTF-8")
	public Response loginUser(JSONObject object) throws JSONException {
		String contactNo = object.get("contactNo").toString();
		return loginService.loginUser(contactNo);
	}

	@POST
	@Path("/signupUserWithFB")
	@Produces("application/json;charset=UTF-8")
	public Response loginUserWithFB(JSONObject object) throws JSONException {
		String email = object.getString("email").toString();
		String facebookId = object.getString("facebookId").toString();
		String userName = object.getString("userName").toString();
		String contactNo = object.getString("contactNo").toString();
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