package com.app.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.LoginController;

public class HibernateSessionFactory {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private static String CONFIG_FILE_LOCATION = "com/app/util/hibernate.cfg.xml";

    private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    
    private static final Configuration cfg = new Configuration();

    private static org.hibernate.SessionFactory sessionFactory;

    public static Session currentSession() throws HibernateException {
    
        Session session = (Session) threadLocal.get();

        if (session != null && ! session.isConnected())
        	session = null; 
        if (session == null) {
            if (sessionFactory == null) {
                try {
                    cfg.configure(CONFIG_FILE_LOCATION);
                    sessionFactory = cfg.buildSessionFactory();
                }
                catch (Exception e) {
                	e.printStackTrace();
                	logger.error("===================******* HibernateSessionFactory *******=================");
            		logger.error(e.toString());
            		logger.error("===================**************=================");
                }
            }
            session = sessionFactory.openSession();
            threadLocal.set(session);
        }

        return session;
    }

    /**
     *  Close the single hibernate session instance.
     *
     *  @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);

        if (session != null) {
            session.close();
        }
    }

    /**
     * Default constructor.
     */
    private HibernateSessionFactory() {
    }
}
