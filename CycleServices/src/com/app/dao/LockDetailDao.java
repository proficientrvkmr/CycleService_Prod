package com.app.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.app.config.hibernate.HibernateSessionFactory;
import com.app.domain.LockDetail;

/**
 * 
 * @author Ravi Kumar
 *
 */
public class LockDetailDao {

	public LockDetail saveLockDetail(LockDetail lockDetail) {
		Session session = HibernateSessionFactory.currentSession();
		long id = (long) session.save(lockDetail);
		session.beginTransaction().commit();
		lockDetail.setId(id);
		return lockDetail;
	}

	public LockDetail getLockDetailById(long lockId) {
		Session session = HibernateSessionFactory.currentSession();
		Query query = session.createQuery("from LockDetail where id = :lockId");
		query.setParameter("lockId", lockId);
		List<?> list = query.list();
		if (list.isEmpty()) {
			return null;
		}
		return (LockDetail) list.get(0);
	}

}
