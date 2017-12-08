package com.app.services;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.dao.UserDetailDao;
import com.app.domain.EmailOTPTracking;
import com.app.domain.UserDetail;
import com.app.mail.SendMail;
import com.app.util.GenerateOTP;
import com.app.util.HibernateSessionFactory;

public class LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	public Response test() {		
    	JSONObject obj = new JSONObject();
    	try{
    		SendMail sendMail = new SendMail();
			String subject="Welcome to Test Cycle Service";
			logger.info("This is for testing purpose. Message will enhance once deployed on public ip. OTP \t");
			sendMail.transferToMailServer("alok2014mca@gmail.com", subject, "Testing");
				obj.put("statusCode", 100);
 
    	} catch (Exception e) {    		
    		logger.error("===================**************=================");
    		logger.error(e.toString());
    		logger.error("===================**************=================");
    	}
    	return Response.ok(obj.toString()).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", true).header("Access-Control-Allow-Methods", "GET")
				.header("Access-Control-Allow-Headers", "Content-Type,Accept,X-Requested-With").build();
	}

	public Response signupUser(String email, String contactNo) {
		JSONObject obj = new JSONObject();
		UserDetailDao userDetailDao = new UserDetailDao();
		UserDetail contactUserDetail = new UserDetail();
		try {
			if (!contactNo.equals("")) {
				contactUserDetail = userDetailDao.loadUserDetail(contactNo, 2);
				SendMail sendMail = new SendMail();
				String otp = GenerateOTP.generateOTP();

				EmailOTPTracking emailOTPTracking = new EmailOTPTracking();
				emailOTPTracking.setEmailId(email);
				emailOTPTracking.setSentOTP(otp);
				emailOTPTracking.setIsValidated("0");

				UserDetailDao dao = new UserDetailDao();
				dao.saveEmailOTP(emailOTPTracking);

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
					userDetailDao.saveUserDetail(userDetail);
					obj.put("status", "Success");
					obj.put("message", "Registration Successful");

					obj1.put("otp", otp);
					obj1.put("email", contactUserDetail.getEmailId());
					obj1.put("mobileNo", contactUserDetail.getContactNo());

					obj.put("object", obj1);
				} else {
					// old User
					obj.put("status", "Success");
					obj.put("message", "Existing User");
					obj1.put("otp", otp);
					obj1.put("email", contactUserDetail.getEmailId());
					obj1.put("mobileNo", contactUserDetail.getContactNo());

					obj.put("object", obj1);
				}
			} else {
				obj.put("message", "Contact No is mandatory");
				obj.put("status", "Failed");
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
		} finally {
			HibernateSessionFactory.closeSession();
		}

		return Response.ok(obj.toString()).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", true).header("Access-Control-Allow-Methods", "GET")
				.header("Access-Control-Allow-Headers", "Content-Type,Accept,X-Requested-With").build();
	}

	public Response loginUser(String contactNo) {
		JSONObject obj = new JSONObject();
		UserDetailDao userDetailDao = new UserDetailDao();
		UserDetail contactUserDetail = new UserDetail();
		try {
			if (!contactNo.equals("")) {
				contactUserDetail = userDetailDao.loadUserDetail(contactNo, 2);

				if (contactUserDetail.getStatusCode().equals("0")) {
					// old User
					obj.put("status", "Success");
					obj.put("message", "Login Successful");
					JSONObject obj1 = new JSONObject();

					String email = contactUserDetail.getEmailId();
					SendMail sendMail = new SendMail();
					String otp = GenerateOTP.generateOTP();

					obj1.put("otp", otp);
					obj1.put("email", contactUserDetail.getEmailId());
					obj1.put("mobileNo", contactUserDetail.getContactNo());

					obj.put("object", obj1);

					EmailOTPTracking emailOTPTracking = new EmailOTPTracking();
					emailOTPTracking.setEmailId(email);
					emailOTPTracking.setSentOTP(otp);
					emailOTPTracking.setIsValidated("0");

					UserDetailDao dao = new UserDetailDao();
					dao.saveEmailOTP(emailOTPTracking);

					String subject = "Welcome to Test Cycle Service";
					String emailMessage = "This is for testing purpose. Message will enhance once deployed on public ip. OTP \t"
							+ otp;
					sendMail.transferToMailServer(email, subject, emailMessage);

				} else {
					obj.put("status", "Fail");
					obj.put("message", "Not Registered User");
				}
			} else {
				obj.put("status", "Fail");
				obj.put("message", "Contact No is Mandatory");
			}

		} catch (Exception e) {
			try {
				obj.put("status", "Fail");
				obj.put("message", "Something went wrong. Contact Administrator");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
		} finally {
			HibernateSessionFactory.closeSession();
		}

		return Response.ok(obj.toString()).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", true).header("Access-Control-Allow-Methods", "GET")
				.header("Access-Control-Allow-Headers", "Content-Type,Accept,X-Requested-With").build();
	}

	public Response loginUserWithFB(String email, String facebookId,
			String userName, String contactNo) {
		JSONObject obj = new JSONObject();
		UserDetailDao userDetailDao = new UserDetailDao();
		UserDetail fbUserDetail = new UserDetail();
		try {
			if (!contactNo.equals("")) {
				fbUserDetail = userDetailDao.loadUserDetail(facebookId, 3);
				String otp = GenerateOTP.generateOTP();
				if (fbUserDetail.getStatusCode().equals("1")) {
					// new User
					UserDetail userDetail = new UserDetail();
					userDetail.setContactNo(contactNo);
					userDetail.setEmailId(email);
					userDetail.setFacebookId(facebookId);
					userDetail.setUserName(userName);
					userDetailDao.saveUserDetail(userDetail);
					obj.put("status", "success");
					obj.put("message", "New User created");
					obj.put("otp", otp);
				} else {
					// old User
					obj.put("statusCode", "success");
					obj.put("message", "Existing User");
					obj.put("otp", otp);
				}

				SendMail sendMail = new SendMail();
				EmailOTPTracking emailOTPTracking = new EmailOTPTracking();
				emailOTPTracking.setEmailId(email);
				emailOTPTracking.setSentOTP(otp);
				emailOTPTracking.setIsValidated("0");
				UserDetailDao dao = new UserDetailDao();
				dao.saveEmailOTP(emailOTPTracking);
				
				String subject = "Welcome to Test Cycle Service";
				String emailMessage = "This is for testing purpose. Message will enhance once deployed on public ip. Validate OTP \t"
						+ otp;
				sendMail.transferToMailServer(email, subject, emailMessage);
			} else {
				obj.put("status", "fail");
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
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return Response.ok(obj.toString()).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", true).header("Access-Control-Allow-Methods", "GET")
				.header("Access-Control-Allow-Headers", "Content-Type,Accept,X-Requested-With").build();
	}

	public Response resendOTP(String contactNo) {
		JSONObject obj = new JSONObject();
		UserDetailDao userDetailDao = new UserDetailDao();
		UserDetail contactUserDetail = new UserDetail();
		try {
			logger.info("********************************************");
			logger.info("################ OTP resend with contactNo " + contactNo + " ########################");
			logger.info("********************************************");
			contactUserDetail = userDetailDao.loadUserDetail(contactNo, 2);
			if (!contactUserDetail.getContactNo().equals("")) {
				SendMail sendMail = new SendMail();
				String otp = GenerateOTP.generateOTP();

				String email = contactUserDetail.getEmailId();
				EmailOTPTracking emailOTPTracking = new EmailOTPTracking();
				emailOTPTracking.setEmailId(email);
				emailOTPTracking.setSentOTP(otp);
				emailOTPTracking.setIsValidated("0");
				UserDetailDao dao = new UserDetailDao();
				dao.saveEmailOTP(emailOTPTracking);
				String subject = "Welcome to Test Cycle Service";
				String emailMessage = "This is for testing purpose. Message will enhance once deployed on public ip. Validate OTP \t"
						+ otp;
				sendMail.transferToMailServer(email, subject, emailMessage);

				obj.put("status", "success");
				obj.put("message", "OTP sent to user");

				JSONObject obj1 = new JSONObject();
				obj1.put("otp", otp);
				obj1.put("email", contactUserDetail.getEmailId());
				obj1.put("mobileNo", contactUserDetail.getContactNo());
				obj.put("object", obj1);

			} else {
				obj.put("status", "Fail");
				obj.put("message", "");
			}

		} catch (Exception e) {
			try {
				obj.put("status", "Fail");
				obj.put("message", "Something went wrong. Contact Administrator");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
		} finally {
			HibernateSessionFactory.closeSession();
		}

		return Response.ok(obj.toString()).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", true).header("Access-Control-Allow-Methods", "GET")
				.header("Access-Control-Allow-Headers", "Content-Type,Accept,X-Requested-With").build();
	}

	public Response validateOTP(String email, String otpString) {
		JSONObject obj = new JSONObject();
		try {
			logger.info("********************************************");
			logger.info("################ OTP validation with email " + email + " ########################");
			logger.info("********************************************");

			int result = UserDetailDao.validateOTP(email, otpString);
			if (result == 1) {
				obj.put("status", "Success");
				obj.put("message", "OTP Matched");
			} else {
				obj.put("status", "Fail");
				obj.put("message", "This is not one which we sent");
			}

		} catch (Exception e) {
			try {
				obj.put("status", "Fail");
				obj.put("message", "Something went wrong. Contact Administrator");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
		} finally {
			HibernateSessionFactory.closeSession();
		}

		return Response.ok(obj.toString()).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", true).header("Access-Control-Allow-Methods", "GET")
				.header("Access-Control-Allow-Headers", "Content-Type,Accept,X-Requested-With").build();
	}

}