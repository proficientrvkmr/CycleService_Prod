package com.app.dao;

import java.io.Serializable;
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

	/**
	 * save store object
	 * 
	 * @param store
	 * @return
	 */
	public StoreMaster saveStore(StoreMaster store) {
		Session session = HibernateSessionFactory.currentSession();
		Serializable id = session.save(store);
		session.beginTransaction().commit();
		store.setId(Long.parseLong(id.toString()));
		return store;
	}

	/**
	 * update Store
	 * 
	 * @param store
	 * @return
	 */
	public StoreMaster updateStore(StoreMaster store) {
		Session session = HibernateSessionFactory.currentSession();
		session.saveOrUpdate(store);
		session.beginTransaction().commit();
		return store;
	}

	/**
	 * get all stores
	 * 
	 * @return
	 */
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

	/**
	 * get all stores who are unmapped to mongo database.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StoreMaster> getAllUnmappedStoresToMongoDB() {
		List<StoreMaster> storeList = new ArrayList<>();
		try {
			Session session = HibernateSessionFactory.currentSession();
			Query query = session.createQuery("from StoreMaster where mongoDocumentId is null");
			List<?> list = query.list();
			storeList = (List<StoreMaster>) list;
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return (List<StoreMaster>) storeList;
	}

	/**
	 * get store by id
	 * 
	 * @param storeId
	 * @return
	 */
	public StoreMaster getStoreById(long storeId) {
		StoreMaster store = null;
		try {
			Session session = HibernateSessionFactory.currentSession();
			Query query = session.createQuery("from StoreMaster where id = :storeId");
			query.setParameter("storeId", storeId);
			List<?> list = query.list();
			if (!list.isEmpty()) {
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
