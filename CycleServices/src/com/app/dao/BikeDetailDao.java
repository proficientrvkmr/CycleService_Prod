package com.app.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.app.config.hibernate.HibernateSessionFactory;
import com.app.domain.BikeDetail;

public class BikeDetailDao {

	public BikeDetail saveBikeDetail(BikeDetail bikeDetail) {
		Session session = HibernateSessionFactory.currentSession();
		long id = (long) session.save(bikeDetail);
		session.beginTransaction().commit();
		bikeDetail.setId(id);
		return bikeDetail;
	}

	public BikeDetail getBikeDetailById(long bikeId) {
		Session session = HibernateSessionFactory.currentSession();
		Query query = session.createQuery("from BikeDetail where id = :bikeId");
		query.setParameter("bikeId", bikeId);
		List<?> list = query.list();
		if(list.isEmpty()){
			return null;
		}
		return (BikeDetail) list.get(0);
	}

}
