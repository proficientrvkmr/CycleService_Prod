package com.app;

import java.io.IOException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.domain.LockDetail;
import com.app.service.LockService;
import com.app.util.JSONConverterUtil;

/**
 * 
 * @author Ravi Kumar
 *
 */
@Path("lock")
public class LockController {

	private LockService lockService = new LockService();

	@POST
	@Path("/register")
	@Produces("application/json;charset=UTF-8")
	public Response lockRegister(JSONObject object) throws JSONException {
		LockDetail lock = null;
		Response response = null;
		try {
			Object lockDetail = object.get("lockDetail");
			lock = (LockDetail) JSONConverterUtil.fromJson(lockDetail, LockDetail.class);
			response = lockService.lockRegister(lock);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
			throw new JSONException("wrong key-value pair");
		}
		return response;
	}

	@POST
	@Path("/verify")
	@Produces("application/json;charset=UTF-8")
	public Response verify(JSONObject object) throws JSONException {
		long lockId = object.getLong("lockId");
		long userId = object.getLong("userId");
		long bikeId = object.getLong("bikeId");
		String lockSecretCode = object.get("lockCode").toString();
		return lockService.lockRegister(lockId, lockSecretCode, userId, bikeId);
	}
}
