package com.app.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.app.config.hibernate.HibernateSessionFactory;
import com.app.domain.RideDetail;

public class RideDetailDao {

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
	
	public RideDetail getRideDetailById(int rideId) {
		Session session = HibernateSessionFactory.currentSession();
		Query query = session.createQuery("from RideDetail where id = :rideId");
		query.setParameter("rideId", rideId);
		List<?> list = query.list();
		RideDetail rideDetail = (RideDetail) list.get(0); 
		return rideDetail;
	}
}
