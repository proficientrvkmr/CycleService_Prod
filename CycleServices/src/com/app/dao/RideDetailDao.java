package com.app.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.config.hibernate.HibernateSessionFactory;
import com.app.domain.RideDetail;
import com.app.domain.RideStatus;
import com.app.domain.UserDetail;

/**
 * 
 * @author Ravi Kumar
 *
 */
public class RideDetailDao {

	private static final Logger logger = LoggerFactory.getLogger(RideDetailDao.class);

	public RideDetail saveRide(RideDetail newRide) {
		Session session = HibernateSessionFactory.currentSession();
		Serializable id = session.save(newRide);
		session.beginTransaction().commit();
		newRide.setId(Long.parseLong(id.toString()));
		return newRide;
	}

	public RideDetail updateRide(RideDetail ride) {
		Session session = HibernateSessionFactory.currentSession();
		session.update(ride);
		session.beginTransaction().commit();
		return ride;
	}

	public RideDetail getRideDetailById(long rideId) {
		Session session = HibernateSessionFactory.currentSession();
		Query query = session.createQuery("from RideDetail where id = :rideId");
		query.setParameter("rideId", rideId);
		List<?> list = query.list();
		if (!list.isEmpty()) {
			RideDetail rideDetail = (RideDetail) list.get(0);
			return rideDetail;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<RideDetail> getAllRideByUserId(long userId) {
		List<RideDetail> storeList = new ArrayList<>();
		try {
			Session session = HibernateSessionFactory.currentSession();
			Query query = session.createQuery("from RideDetail where userDetail = :user and currentStatus = :status");
			UserDetail user = new UserDetail();
			user.setId(userId);
			query.setParameter("user", user);
			query.setParameter("status", RideStatus.COMPLETE.name());
			List<?> list = query.list();
			storeList = (List<RideDetail>) list;
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return (List<RideDetail>) storeList;
	}
}
