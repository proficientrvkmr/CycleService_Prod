package com.app.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.config.hibernate.HibernateSessionFactory;
import com.app.domain.LoginType;
import com.app.domain.OTPTracking;
import com.app.domain.UserDetail;
import com.app.util.GenerateTokenUtil;

public class UserDetailDao {
	private static final Logger logger = LoggerFactory.getLogger(UserDetailDao.class);

	public UserDetail saveUserDetail(UserDetail userDetail) {
		long id = 0;
		if(userDetail.getReferenceCode() == null){
			String referCode = GenerateTokenUtil.generateReferCode(7);
			userDetail.setReferenceCode(referCode);
		}
		Session session = HibernateSessionFactory.currentSession();
		id = (Long) session.save(userDetail);
		session.beginTransaction().commit();
		userDetail.setId(id);
		return userDetail;
	}

	public UserDetail loadUserDetail(String inputValue, LoginType loginType) {
		UserDetail userDetail = new UserDetail();
		try {
			String queryString = "";
			Session session = HibernateSessionFactory.currentSession();
			logger.info("=========================================");
			if (loginType == LoginType.BY_EMAIL) {
				logger.info("Load UserDetail by Email \t" + inputValue);
				queryString = "from UserDetail where emailId=:inputValue";

			} else if (loginType == LoginType.BY_CONTACTNO) {
				logger.info("Load UserDetail by contactNo \t" + inputValue);
				queryString = "from UserDetail where contactNo=:inputValue";

			} else if (loginType == LoginType.BY_ID) {
				logger.info("Load UserDetail by id \t" + inputValue);
				queryString = "from UserDetail where id=:inputValue";

			} else if (loginType == LoginType.BY_FACEBOOK) {
				logger.info("Load UserDetail by facebookId \t" + inputValue);
				queryString = "from UserDetail where facebookId=:inputValue";
			}
			logger.info("=========================================");

			Query query = session.createQuery(queryString);
			if (loginType == LoginType.BY_ID) {
				query.setParameter("inputValue", Long.parseLong(inputValue));
			} else {
				query.setParameter("inputValue", inputValue);
			}
			List<?> userDetailList = query.list();

			if (userDetailList.size() > 0) {
				userDetail = (UserDetail) userDetailList.get(0);
				userDetail.setStatusCode("0");
			} else {
				userDetail.setStatusCode("1");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");

		} finally {
			HibernateSessionFactory.closeSession();
		}
		return userDetail;
	}

	public long saveEmailOTP(OTPTracking emailOTPTracking) {
		long result = 0;
		try {
			Session session = HibernateSessionFactory.currentSession();
			result = (Long) session.save(emailOTPTracking);
			session.beginTransaction().commit();
		} catch (Exception e) {
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return result;
	}

	public static int validateOTP(String contactNo, String otpString) {
		int result = 0;
		try {
			Session session = HibernateSessionFactory.currentSession();
			String queryString = "from OTPTracking where isValidated = false and id=(select max(id) from OTPTracking where emailId=:email)";
			Query query = session.createQuery(queryString);
			query.setString("email", contactNo);
			List<?> otpList = query.list();
			if (otpList.size() > 0) {
				OTPTracking emailOTPTracking = (OTPTracking) otpList.get(0);
				emailOTPTracking.setIsValidated(true);
				emailOTPTracking.setValidateDate(new Date());
				if (emailOTPTracking.getSentOTP().equals(otpString)) {
					session.update(emailOTPTracking);
					session.beginTransaction().commit();
					result = 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return result;
	}

	public UserDetail getUserById(long userId) {
		Session session = HibernateSessionFactory.currentSession();
		Query query = session.createQuery("from UserDetail where id = :userId");
		query.setParameter("userId", userId);
		List<?> list = query.list();
		if (list.isEmpty()) {
			return null;
		} else {
			UserDetail userDetail = (UserDetail) list.get(0);
			return userDetail;
		}
	}
}
