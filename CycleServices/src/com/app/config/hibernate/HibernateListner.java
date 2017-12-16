package com.app.config.hibernate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 
 * @author Ravi Kumar
 *
 */
@WebListener
public class HibernateListner implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		HibernateSessionFactory.currentSession();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		HibernateSessionFactory.closeSession();
	}

}
