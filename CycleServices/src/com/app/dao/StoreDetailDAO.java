package com.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.config.hibernate.HibernateSessionFactory;
import com.app.domain.StoreMaster;

public class StoreDetailDAO {

	private static final Logger logger = LoggerFactory.getLogger(StoreDetailDAO.class);

	@SuppressWarnings("unchecked")
	public List<StoreMaster> getAllStores() {
		List<StoreMaster> storeList = new ArrayList<>();
		try {
			Session session = HibernateSessionFactory.currentSession();
			Query query = session.createQuery("from StoreMaster where isActive=1");
			List<?> list = query.list();
			storeList = (List<StoreMaster>) list;
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return (List<StoreMaster>) storeList;
	}

	public StoreMaster getStoreById(long storeId) {
		StoreMaster store = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			Query query = session.createQuery("from StoreMaster where id = :storeId");
			query.setParameter("storeId", storeId);
			List<?> list = query.list();
			if(!list.isEmpty()){
				store = (StoreMaster) list.get(0);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return store;
	}
}
