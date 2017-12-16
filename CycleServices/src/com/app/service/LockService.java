package com.app.service;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.app.dao.LockDetailDao;
import com.app.domain.BikeDetail;
import com.app.domain.LockDetail;
import com.app.domain.UserDetail;
import com.app.util.RestResponse;

/**
 * 
 * @author Ravi Kumar
 *
 */
public class LockService {

	private LockDetailDao lockDetailDao = new LockDetailDao();
	private LoginService userService = new LoginService();
	private BikeService bikeService = new BikeService();

	public Response lockRegister(LockDetail lockDetail) throws JSONException {
		long bikeId = lockDetail.getBikeDetail().getId();
		BikeDetail bike = bikeService.getBikeById(bikeId);
		if (bike != null) {
			lockDetail.setBikeDetail(bike);
			LockDetail savedObject = lockDetailDao.saveLockDetail(lockDetail);
			JSONObject object = new JSONObject();
			object.put("lockId", savedObject.getId());
			return RestResponse.withSuccessAndData(object);
		} else {
			return RestResponse.withErrorAndMessage("BikeId is not valid. Please verify and try again.");
		}
	}

	public LockDetail getLockById(long lockId) {
		return lockDetailDao.getLockDetailById(lockId);
	}

	public Response lockRegister(long lockId, String lockSecretCode, long userId, long bikeId) throws JSONException {
		UserDetail user = userService.getUserById(userId);
		BikeDetail bike = bikeService.getBikeById(bikeId);
		LockDetail lock = getLockById(lockId);
		if (user != null && bike != null && lock != null) {
			JSONObject object = new JSONObject();
			if (lock.getLockSecretCode().equals(lockSecretCode)) {
				object.put("message", "lock verified.");
				object.put("lockId", lock.getId());
			} else {
				object.put("message", "Invalid Lock, details does not matched.");
			}
			return RestResponse.withSuccessAndData(object);
		} else {
			return RestResponse.withErrorAndMessage(
					"Authentication failed. You have not provided the correct information, please verify and try again.");
		}
	}
}
