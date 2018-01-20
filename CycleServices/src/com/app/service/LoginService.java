package com.app.service;

import java.util.Date;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.config.hibernate.HibernateSessionFactory;
import com.app.dao.UserDetailDao;
import com.app.domain.OTPTracking;
import com.app.domain.LoginType;
import com.app.domain.UserDetail;
import com.app.mail.SendMail;
import com.app.sms.ISmsExotelSender;
import com.app.sms.SmsExotelSenderImpl;
import com.app.util.ApplicationConstant;
import com.app.util.GenerateTokenUtil;
import com.app.util.RestResponse;

public class LoginService {

	private static final Logger logger = LoggerFactory
			.getLogger(LoginService.class);
	private SendMail sendMail = new SendMail();
	private UserDetailDao userDetailDao = new UserDetailDao();
	private ISmsExotelSender sendSms = new SmsExotelSenderImpl();

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
				OTPTracking emailOTPTracking = new OTPTracking();
				emailOTPTracking.setEmailId(contactUserDetail.getEmailId());
				emailOTPTracking.setMobileNo(contactUserDetail.getContactNo());
				emailOTPTracking.setSentOTP(otp);
				emailOTPTracking.setIsValidated(false);
				emailOTPTracking.setCreationDate(new Date());
				emailOTPTracking.setUser(contactUserDetail);
				
				try {
					userDetailDao.saveEmailOTP(emailOTPTracking);
					//sms
					String to = contactNo;
					String body = ApplicationConstant.smsMessageBody;
					String smsText = String.format(body, otp);
					sendSms.sendSms(to, smsText);
					//email
					String subject = ApplicationConstant.emailSubject;
					String emailMessage = ApplicationConstant.emailMessage
							+ otp;
					sendMail.transferToMailServer(email, subject, emailMessage);
				} catch (Exception e) {
					logger.error("===================**************=================");
					logger.error(e.toString());
					logger.error("===================**************=================");
				}
				if (contactUserDetail.getStatusCode().equals("1")) {
					// New User
					UserDetail userDetail = new UserDetail();
					userDetail.setContactNo(contactNo);
					userDetail.setEmailId(email);
					userDetail.setActive(false);
					userDetail.setCreatedDate(new Date());
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
					
					OTPTracking emailOTPTracking = new OTPTracking();
					emailOTPTracking.setEmailId(contactUserDetail.getEmailId());
					emailOTPTracking.setMobileNo(contactUserDetail.getContactNo());
					emailOTPTracking.setSentOTP(otp);
					emailOTPTracking.setIsValidated(false);
					emailOTPTracking.setCreationDate(new Date());
					emailOTPTracking.setUser(contactUserDetail);
					
					try {
						userDetailDao.saveEmailOTP(emailOTPTracking);
						//sms
						String to = contactNo;
						String body = ApplicationConstant.smsMessageBody;
						String smsText = String.format(body, otp);
						sendSms.sendSms(to, smsText);
						//email
						String subject = ApplicationConstant.emailSubject;
						String emailMessage = ApplicationConstant.emailMessage
								+ otp;
						sendMail.transferToMailServer(email, subject, emailMessage);
					} catch (Exception e) {
						logger.error("===================**************=================");
						logger.error(e.toString());
						logger.error("===================**************=================");
					}

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
		UserDetail userDetail = new UserDetail();
		try {
			if (!contactNo.equals("")) {
				userDetail = userDetailDao.loadUserDetail(facebookId,
						LoginType.BY_FACEBOOK);
				String otp = GenerateTokenUtil.generateOTP();
				if (userDetail.getStatusCode().equals("1")) {
					// new User
					UserDetail newUser = new UserDetail();
					newUser.setContactNo(contactNo);
					newUser.setEmailId(email);
					newUser.setFacebookId(facebookId);
					newUser.setUserName(userName);
					newUser.setActive(false);
					newUser.setCreatedDate(new Date());
					userDetail = userDetailDao.saveUserDetail(newUser);
					
					obj.put("type", "New User created");
					obj.put("userId", userDetail.getId());
					obj.put("referenceCode", userDetail.getReferenceCode());
					obj.put("otp", otp);
				} else {
					// old User
					obj.put("type", "Existing User");
					obj.put("userId", userDetail.getId());
					obj.put("referenceCode", userDetail.getReferenceCode());
					obj.put("otp", otp);
				}

				OTPTracking emailOTPTracking = new OTPTracking();
				emailOTPTracking.setEmailId(userDetail.getEmailId());
				emailOTPTracking.setMobileNo(userDetail.getContactNo());
				emailOTPTracking.setSentOTP(otp);
				emailOTPTracking.setIsValidated(false);
				emailOTPTracking.setCreationDate(new Date());
				emailOTPTracking.setUser(userDetail);
				
				try {
					userDetailDao.saveEmailOTP(emailOTPTracking);
					//sms
					String to = contactNo;
					String body = ApplicationConstant.smsMessageBody;
					String smsText = String.format(body, otp);
					sendSms.sendSms(to, smsText);
					//email
					String subject = ApplicationConstant.emailSubject;
					String emailMessage = ApplicationConstant.emailMessage
							+ otp;
					sendMail.transferToMailServer(email, subject, emailMessage);
				} catch (Exception e) {
					logger.error("===================**************=================");
					logger.error(e.toString());
					logger.error("===================**************=================");
				}
				
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
				
				OTPTracking emailOTPTracking = new OTPTracking();
				emailOTPTracking.setEmailId(contactUserDetail.getEmailId());
				emailOTPTracking.setMobileNo(contactUserDetail.getContactNo());
				emailOTPTracking.setSentOTP(otp);
				emailOTPTracking.setIsValidated(false);
				emailOTPTracking.setCreationDate(new Date());
				emailOTPTracking.setUser(contactUserDetail);
				
				try {
					userDetailDao.saveEmailOTP(emailOTPTracking);
					//sms
					String to = contactNo;
					String body = ApplicationConstant.smsMessageBody;
					String smsText = String.format(body, otp);
					sendSms.sendSms(to, smsText);
					//email
					String subject = ApplicationConstant.emailSubject;
					String emailMessage = ApplicationConstant.emailMessage
							+ otp;
					sendMail.transferToMailServer(email, subject, emailMessage);
				} catch (Exception e) {
					logger.error("===================**************=================");
					logger.error(e.toString());
					logger.error("===================**************=================");
				}
				
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
				obj.put("message", "This is not one which we had sent");
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