package com.app.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.domain.UserDetail;
import com.app.domain.WalletDetail;
import com.app.util.HibernateSessionFactory;

public class WalletDetailDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(StoreDetailDAO.class);

	public double getAvailableBalance(long userId) {
		double availableBalance = 0.0;
		try {
			Session session = HibernateSessionFactory.currentSession();
			Query query = session.createQuery("from WalletDetail where userDetail = :user");
			UserDetail user = new UserDetail();
			user.setId(userId);
			query.setParameter("user", user);
			List<WalletDetail> list = query.list();
			System.out.println(list);
			availableBalance = list.get(0).getAmountAvailable();
		} catch (RuntimeException e) {
			logger.error("Data Base error");
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return availableBalance;
	}
}
