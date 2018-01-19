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
import com.app.util.ApplicationConstant;
import com.app.util.GenerateTokenUtil;
import com.app.util.RestResponse;

public class LoginService {

	private static final Logger logger = LoggerFactory
			.getLogger(LoginService.class);
	private SendMail sendMail = new SendMail();
	private UserDetailDao userDetailDao = new UserDetailDao();

	public Response testMail() {
		try {
			String subject = "Welcome to Test Cycle Service";
			logger.info("This is for testing purpose. Message will enhance once deployed on public ip. OTP \t");
			sendMail.transferToMailServer("**-alok2014mca@gmail.com", subject,
					"Testing");
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
				contactUserDetail = userDetailDao.loadUserDetail(contactNo,
						LoginType.BY_CONTACTNO);
				String otp = GenerateTokenUtil.generateOTP();
				EmailOTPTracking emailOTPTracking = new EmailOTPTracking();
				emailOTPTracking.setEmailId(email);
				emailOTPTracking.setSentOTP(otp);
				emailOTPTracking.setIsValidated(false);
				userDetailDao.saveEmailOTP(emailOTPTracking);

				String subject = ApplicationConstant.emailSubject;
				String emailMessage = ApplicationConstant.emailMessage + otp;
				sendMail.transferToMailServer(email, subject, emailMessage);

				if (contactUserDetail.getStatusCode().equals("1")) {
					// New User
					UserDetail userDetail = new UserDetail();
					userDetail.setContactNo(contactNo);
					userDetail.setEmailId(email);
					UserDetail user = userDetailDao.saveUserDetail(userDetail);
					obj.put("message", "Registration Successful");
					obj.put("email", user.getEmailId());
					obj.put("mobileNo", user.getContactNo());
					obj.put("userId", user.getId());
					obj.put("referenceCode", user.getReferenceCode());
				} else {
					// old User
					obj.put("message", "Existing User");
					obj.put("otp", otp);
					obj.put("email", contactUserDetail.getEmailId());
					obj.put("mobileNo", contactUserDetail.getContactNo());
					obj.put("userId", contactUserDetail.getId());
					obj.put("referenceCode",
							contactUserDetail.getReferenceCode());
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
		JSONObject obj1 = new JSONObject();
		UserDetail contactUserDetail = new UserDetail();
		try {
			if (!contactNo.equals("")) {
				contactUserDetail = userDetailDao.loadUserDetail(contactNo,
						LoginType.BY_CONTACTNO);

				if (contactUserDetail.getStatusCode().equals("0")) {
					// old User
					String email = contactUserDetail.getEmailId();
					String otp = GenerateTokenUtil.generateOTP();
					obj1.put("message", "Login Successfully");
					obj1.put("otp", otp);
					obj1.put("email", contactUserDetail.getEmailId());
					obj1.put("userId", contactUserDetail.getId());
					obj1.put("mobileNo", contactUserDetail.getContactNo());
					obj1.put("referenceCode",
							contactUserDetail.getReferenceCode());
					// Todo
					
					EmailOTPTracking emailOTPTracking = new EmailOTPTracking();
					emailOTPTracking.setEmailId(email);
					emailOTPTracking.setSentOTP(otp);
					emailOTPTracking.setIsValidated(false);
					userDetailDao.saveEmailOTP(emailOTPTracking);

					String subject = ApplicationConstant.emailSubject;
					String emailMessage = ApplicationConstant.emailMessage + otp;
					sendMail.transferToMailServer(email, subject, emailMessage);

				} else {
					obj1.put("message", "Not Registered User");
				}
			} else {
				obj1.put("message", "Contact No is Mandatory");
			}

		} catch (Exception e) {
			try {
				obj1.put("message",
						"Something went wrong. Contact Administrator");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
			return RestResponse.withErrorAndData(obj1);
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return RestResponse.withSuccessAndData(obj1);
	}

	public Response loginUserWithFB(String email, String facebookId,
			String userName, String contactNo) {
		JSONObject obj = new JSONObject();
		UserDetail fbUserDetail = new UserDetail();
		try {
			if (!contactNo.equals("")) {
				fbUserDetail = userDetailDao.loadUserDetail(facebookId,
						LoginType.BY_FACEBOOK);
				String otp = GenerateTokenUtil.generateOTP();
				if (fbUserDetail.getStatusCode().equals("1")) {
					// new User
					UserDetail userDetail = new UserDetail();
					userDetail.setContactNo(contactNo);
					userDetail.setEmailId(email);
					userDetail.setFacebookId(facebookId);
					userDetail.setUserName(userName);
					UserDetail user = userDetailDao.saveUserDetail(userDetail);
					obj.put("type", "New User created");
					obj.put("userId", user.getId());
					obj.put("referenceCode", user.getReferenceCode());
					obj.put("otp", otp);
				} else {
					// old User
					obj.put("type", "Existing User");
					obj.put("userId", fbUserDetail.getId());
					obj.put("referenceCode", fbUserDetail.getReferenceCode());
					obj.put("otp", otp);
				}

				EmailOTPTracking emailOTPTracking = new EmailOTPTracking();
				emailOTPTracking.setEmailId(email);
				emailOTPTracking.setSentOTP(otp);
				emailOTPTracking.setIsValidated(false);
				userDetailDao.saveEmailOTP(emailOTPTracking);

				String subject = ApplicationConstant.emailSubject;
				String emailMessage = ApplicationConstant.emailMessage + otp;
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
			logger.info("################ OTP resend with contactNo "
					+ contactNo + " ########################");
			logger.info("********************************************");
			contactUserDetail = userDetailDao.loadUserDetail(contactNo,
					LoginType.BY_CONTACTNO);
			if (!contactUserDetail.getContactNo().equals("")) {
				String otp = GenerateTokenUtil.generateOTP();
				String email = contactUserDetail.getEmailId();

				EmailOTPTracking emailOTPTracking = new EmailOTPTracking();
				emailOTPTracking.setEmailId(email);
				emailOTPTracking.setSentOTP(otp);
				emailOTPTracking.setIsValidated(false);
				userDetailDao.saveEmailOTP(emailOTPTracking);
				
				String subject = ApplicationConstant.emailSubject;
				String emailMessage = ApplicationConstant.emailMessage + otp;
				sendMail.transferToMailServer(email, subject, emailMessage);
				obj.put("message", "OTP has sent to user successfully!");
				
			} else {
				obj.put("message", "");
			}

		} catch (Exception e) {
			try {
				obj.put("message",
						"Something went wrong. Contact Administrator");
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
			logger.info("################ OTP validation with email " + email
					+ " ########################");
			logger.info("********************************************");

			int result = UserDetailDao.validateOTP(email, otpString);
			UserDetail contactUserDetail = userDetailDao.loadUserDetail(email,
					LoginType.BY_EMAIL);
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
				obj.put("message",
						"Something went wrong. Contact Administrator");
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