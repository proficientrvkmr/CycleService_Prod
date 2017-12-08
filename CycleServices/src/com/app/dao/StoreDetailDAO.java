package com.app.dao;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.domain.StoreMaster;
import com.app.util.HibernateSessionFactory;

public class StoreDetailDAO {

	private static final Logger logger = LoggerFactory.getLogger(StoreDetailDAO.class);
	
	public JSONArray getStoreInfo(String longitude,String latitude,String distance){
		JSONArray jsonArray = new JSONArray();
		try {
		Session session = HibernateSessionFactory.currentSession();
		
		Query query = session.createQuery("from StoreMaster where isActive=1");
		List<StoreMaster> list = query.list();
		
		for(int i=0;i<list.size();i++){
			JSONObject object = new JSONObject();
			StoreMaster master = list.get(i);			
				object.put("contactNumber", master.getContactNumber());
				object.put("ownerName",master.getOwnerName());
				object.put("latitude", master.getLatitude());
				object.put("longitude", master.getLongitude());
				object.put("address", master.getStoreAddress());
				object.put("name", master.getStoreName());
				jsonArray.put(object);	
		}
			} catch (JSONException e) {
				logger.error("Data Base error");
			}catch (Exception e) {
				logger.error(e.toString());
			}
			finally{
				HibernateSessionFactory.closeSession();
			}		
		return jsonArray;
	}
}
