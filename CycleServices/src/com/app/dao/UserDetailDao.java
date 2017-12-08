package com.app.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.domain.EmailOTPTracking;
import com.app.domain.UserDetail;
import com.app.util.HibernateSessionFactory;

public class UserDetailDao {
	private static final Logger logger = LoggerFactory
			.getLogger(UserDetailDao.class);

	public long saveUserDetail(UserDetail userDetail) {
		long result = 0;
		Session session = HibernateSessionFactory.currentSession();
		result = (Long) session.save(userDetail);
		session.beginTransaction().commit();
		return result;
	}

	public UserDetail loadUserDetail(String inputValue, int paramCode) {
		UserDetail userDetail = new UserDetail();
		try {
			String queryString = "";
			Session session = HibernateSessionFactory.currentSession();
			logger.info("=========================================");
			if (paramCode == 1) {
				logger.info("Load UserDetail by Email \t" + inputValue);
				queryString = "from UserDetail where emailId=:inputValue";
			} else if (paramCode == 2) {
				logger.info("Load UserDetail by contactNo \t" + inputValue);
				queryString = "from UserDetail where contactNo=:inputValue";
			} else {
				logger.info("Load UserDetail by facebookId \t" + inputValue);
				queryString = "from UserDetail where facebookId=:inputValue";
			}
			logger.info("=========================================");

			Query query = session.createQuery(queryString);

			query.setParameter("inputValue", inputValue);
			List<UserDetail> userDetailList = query.list();
			if (userDetailList.size() > 0) {
				userDetail = userDetailList.get(0);				
				userDetail.setStatusCode("0");
				}else {
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
	
	public long saveEmailOTP(EmailOTPTracking emailOTPTracking){
		long result=0;
		try{
		Session session = HibernateSessionFactory.currentSession();
		result = (Long) session.save(emailOTPTracking);
		session.beginTransaction().commit();
		}catch(Exception e){
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return result;
	}
	
	public static int validateOTP(String contactNo,String otpString){		
		int result=0;	
		try{
			Session session = HibernateSessionFactory.currentSession();
			String queryString ="from EmailOTPTracking where isValidated=0 and id=(select max(id) from EmailOTPTracking where emailId=:email)";
			Query query = session.createQuery(queryString);
			query.setString("email", contactNo);
			List<EmailOTPTracking> otpList = query.list();
			if(otpList.size()>0){
				EmailOTPTracking emailOTPTracking = otpList.get(0);
				emailOTPTracking.setIsValidated("1");
				if(emailOTPTracking.getSentOTP().equals(otpString)){					
					result=1;
				session.update(emailOTPTracking);
				session.beginTransaction().commit();
				}
			}			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("===================**************=================");
			logger.error(e.toString());
			logger.error("===================**************=================");
		}finally{
			HibernateSessionFactory.closeSession();
		}		
		return result;
	}

}
