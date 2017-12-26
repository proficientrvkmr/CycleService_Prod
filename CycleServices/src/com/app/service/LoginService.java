package com.app.service;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.config.hibernate.HibernateSessionFactory;
import com.app.dao.UserDetailDao;
import com.app.domain.EmailOTPTracking;
import com.app.domain.LoginType;
import com.app.domain.UserDetail;
import com.app.mail.SendMail;
import com.app.util.GenerateOTP;
import com.app.util.RestResponse;

public class LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	private SendMail sendMail = new SendMail();
	private UserDetailDao userDetailDao = new UserDetailDao();

	public Response testMail() {
		try {
			String subject = "Welcome to Test Cycle Service";
			logger.info("This is for testing purpose. Message will enhance once deployed on public ip. OTP \t");
			sendMail.transferToMailServer("**-alok2014mca@gmail.com", subject, "Testing");
		} catch (Exception e) {
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
			return RestResponse.withErrorAndMessage(e.getMessage());
		}
		return RestResponse.withSuccess();
	}

	/**
	 * Method used to for user sign up.
	 * 
	 * @param email
	 * @param contactNo
	 * @return final response
	 */
	public Response signupUser(String email, String contactNo) {
		JSONObject obj = new JSONObject();
		UserDetail contactUserDetail = new UserDetail();
		try {
			if (!contactNo.equals("")) {
				contactUserDetail = userDetailDao.loadUserDetail(contactNo, LoginType.BY_CONTACTNO);
				String otp = GenerateOTP.generateOTP();
				EmailOTPTracking emailOTPTracking = new EmailOTPTracking();
				emailOTPTracking.setEmailId(email);
				emailOTPTracking.setSentOTP(otp);
				emailOTPTracking.setIsValidated("0");
				userDetailDao.saveEmailOTP(emailOTPTracking);

				String subject = "Welcome to Test Cycle Service";
				String emailMessage = "This is for testing purpose. Message will enhance once deployed on public ip. OTP \t"
						+ otp;
				sendMail.transferToMailServer(email, subject, emailMessage);

				JSONObject obj1 = new JSONObject();
				if (contactUserDetail.getStatusCode().equals("1")) {
					// New User
					UserDetail userDetail = new UserDetail();
					userDetail.setContactNo(contactNo);
					userDetail.setEmailId(email);
					long userId = userDetailDao.saveUserDetail(userDetail);
					obj.put("status", "Success");
					obj.put("message", "Registration Successful");

					obj1.put("otp", otp);
					obj1.put("email", contactUserDetail.getEmailId());
					obj1.put("mobileNo", contactUserDetail.getContactNo());
					obj1.put("userId", userId);
					obj.put("object", obj1);
				} else {
					// old User
					obj.put("status", "Success");
					obj.put("message", "Existing User");
					obj1.put("otp", otp);
					obj1.put("email", contactUserDetail.getEmailId());
					obj1.put("mobileNo", contactUserDetail.getContactNo());
					obj1.put("userId", contactUserDetail.getId());
					obj.put("object", obj1);
				}
			} else {
				obj.put("message", "Contact No is mandatory");
				return RestResponse.withErrorAndData(obj);
			}

		} catch (Exception e) {
			try {
				obj.put("message", "Something happen Wrong.");
				obj.put("status", "Failed");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
			return RestResponse.withErrorAndData(obj);
		} finally {
			HibernateSessionFactory.closeSession();
		}

		return RestResponse.withSuccessAndData(obj);
	}

	public Response loginUser(String contactNo) {
		JSONObject obj = new JSONObject();
		UserDetail contactUserDetail = new UserDetail();
		try {
			if (!contactNo.equals("")) {
				contactUserDetail = userDetailDao.loadUserDetail(contactNo, LoginType.BY_CONTACTNO);

				if (contactUserDetail.getStatusCode().equals("0")) {
					// old User
					obj.put("status", "Success");
					obj.put("message", "Login Successfully");
					JSONObject obj1 = new JSONObject();
					String email = contactUserDetail.getEmailId();
					String otp = GenerateOTP.generateOTP();
					obj1.put("otp", otp);
					obj1.put("email", contactUserDetail.getEmailId());
					obj1.put("userId", contactUserDetail.getId());
					obj1.put("mobileNo", contactUserDetail.getContactNo());
					obj.put("object", obj1);

					EmailOTPTracking emailOTPTracking = new EmailOTPTracking();
					emailOTPTracking.setEmailId(email);
					emailOTPTracking.setSentOTP(otp);
					emailOTPTracking.setIsValidated("0");
					userDetailDao.saveEmailOTP(emailOTPTracking);

					String subject = "Welcome to Test Cycle Service";
					String emailMessage = "This is for testing purpose. Message will enhance once deployed on public ip. OTP \t"
							+ otp;
					sendMail.transferToMailServer(email, subject, emailMessage);

				} else {
					obj.put("message", "Not Registered User");
				}
			} else {
				obj.put("message", "Contact No is Mandatory");
			}

		} catch (Exception e) {
			try {
				obj.put("message", "Something went wrong. Contact Administrator");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
			return RestResponse.withErrorAndData(obj);
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return RestResponse.withSuccessAndData(obj);
	}

	public Response loginUserWithFB(String email, String facebookId, String userName, String contactNo) {
		JSONObject obj = new JSONObject();
		UserDetail fbUserDetail = new UserDetail();
		try {
			if (!contactNo.equals("")) {
				fbUserDetail = userDetailDao.loadUserDetail(facebookId, LoginType.BY_FACEBOOK);
				String otp = GenerateOTP.generateOTP();
				if (fbUserDetail.getStatusCode().equals("1")) {
					// new User
					UserDetail userDetail = new UserDetail();
					userDetail.setContactNo(contactNo);
					userDetail.setEmailId(email);
					userDetail.setFacebookId(facebookId);
					userDetail.setUserName(userName);
					long userId = userDetailDao.saveUserDetail(userDetail);
					obj.put("status", "success");
					obj.put("message", "New User created");
					obj.put("userId", userId);
					obj.put("otp", otp);
				} else {
					// old User
					obj.put("statusCode", "success");
					obj.put("message", "Existing User");
					obj.put("userId", fbUserDetail.getId());
					obj.put("otp", otp);
				}

				EmailOTPTracking emailOTPTracking = new EmailOTPTracking();
				emailOTPTracking.setEmailId(email);
				emailOTPTracking.setSentOTP(otp);
				emailOTPTracking.setIsValidated("0");
				userDetailDao.saveEmailOTP(emailOTPTracking);

				String subject = "Welcome to Test Cycle Service";
				String emailMessage = "This is for testing purpose. Message will enhance once deployed on public ip. Validate OTP \t"
						+ otp;
				sendMail.transferToMailServer(email, subject, emailMessage);
			} else {
				obj.put("message", "Contact No. can not be empty");
			}
		} catch (Exception e) {
			try {
				obj.put("status", "fail");
				obj.put("message", "Server Error");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
			return RestResponse.withErrorAndData(obj);
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return RestResponse.withSuccessAndData(obj);
	}

	public Response resendOTP(String contactNo) {
		JSONObject obj = new JSONObject();
		UserDetail contactUserDetail = new UserDetail();
		try {
			logger.info("********************************************");
			logger.info("################ OTP resend with contactNo " + contactNo + " ########################");
			logger.info("********************************************");
			contactUserDetail = userDetailDao.loadUserDetail(contactNo, LoginType.BY_CONTACTNO);
			if (!contactUserDetail.getContactNo().equals("")) {
				String otp = GenerateOTP.generateOTP();
				String email = contactUserDetail.getEmailId();

				EmailOTPTracking emailOTPTracking = new EmailOTPTracking();
				emailOTPTracking.setEmailId(email);
				emailOTPTracking.setSentOTP(otp);
				emailOTPTracking.setIsValidated("0");
				userDetailDao.saveEmailOTP(emailOTPTracking);

				String subject = "Welcome to Test Cycle Service";
				String emailMessage = "This is for testing purpose. Message will enhance once deployed on public ip. Validate OTP \t"
						+ otp;
				sendMail.transferToMailServer(email, subject, emailMessage);

				obj.put("message", "OTP has sent to user successfully!");

				// JSONObject obj1 = new JSONObject();
				// obj1.put("otp", otp);
				// obj1.put("email", contactUserDetail.getEmailId());
				// obj1.put("mobileNo", contactUserDetail.getContactNo());
				// obj.put("object", obj1);

			} else {
				obj.put("message", "");
			}

		} catch (Exception e) {
			try {
				obj.put("message", "Something went wrong. Contact Administrator");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
			return RestResponse.withErrorAndData(obj);

		} finally {
			HibernateSessionFactory.closeSession();
		}

		return RestResponse.withSuccessAndData(obj);
	}

	public Response validateOTP(String email, String otpString) {
		JSONObject obj = new JSONObject();
		try {
			logger.info("********************************************");
			logger.info("################ OTP validation with email " + email + " ########################");
			logger.info("********************************************");

			int result = UserDetailDao.validateOTP(email, otpString);
			UserDetail contactUserDetail = userDetailDao.loadUserDetail(email, LoginType.BY_EMAIL);
			if (result == 1) {
				obj.put("message", "OTP Matched");
				obj.put("email", contactUserDetail.getEmailId());
				obj.put("userId", contactUserDetail.getId());
				obj.put("mobileNo", contactUserDetail.getContactNo());
			} else {
				obj.put("message", "This is not one which we sent");
			}

		} catch (Exception e) {
			try {
				obj.put("message", "Something went wrong. Contact Administrator");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
			return RestResponse.withErrorAndData(obj);

		} finally {
			HibernateSessionFactory.closeSession();
		}

		return RestResponse.withSuccessAndData(obj);
	}

	public UserDetail getUserById(long userId) {
		return userDetailDao.getUserById(userId);
	}

}