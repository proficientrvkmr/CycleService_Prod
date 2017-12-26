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
		LockDetail lock = getLockByLockCode(lockDetail.getLockSecretCode());
		if (bike == null) {
			return RestResponse
					.withErrorAndMessage("This bike is not registered with us. Please verify and try again.");
		}
		// For NEW lock. this should be null.
		if (lock != null) {
			return RestResponse
					.withErrorAndMessage("This lock code is already registered. Please try again with new code.");
		}

		if (bike != null && lock == null) {
			lockDetail.setBikeDetail(bike);
			LockDetail savedObject = lockDetailDao.saveLockDetail(lockDetail);
			JSONObject object = new JSONObject();
			object.put("lockId", savedObject.getId());
			return RestResponse.withSuccessAndData(object);
		} else {
			return RestResponse.withErrorAndMessage("Inputs are not valid. Please verify and try again.");
		}
	}

	public LockDetail getLockById(long lockId) {
		return lockDetailDao.getLockDetailById(lockId);
	}

	public LockDetail getLockByLockCode(String lockCode) {
		return lockDetailDao.getLockByLockCode(lockCode);
	}

	public Response lockRegister(String lockSecretCode, long userId) throws JSONException {
		UserDetail user = userService.getUserById(userId);
		LockDetail lock = getLockByLockCode(lockSecretCode);
		if (user != null && lock != null) {
			JSONObject object = new JSONObject();
			object.put("message", "Lock verified successfully!.");
			object.put("lockId", lock.getId());
			object.put("bikeId", lock.getBikeDetail().getId());

			return RestResponse.withSuccessAndData(object);
		} else {
			return RestResponse.withErrorAndMessage(
					"Authentication failed. You have not provided the correct information, please verify and try again.");
		}
	}
}
