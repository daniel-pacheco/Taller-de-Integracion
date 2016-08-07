package ar.com.santalucia.accesodatos.persistencia;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
	
    private static SessionFactory sessionFactory;
    private static Configuration hibConfig;
    
    public static Configuration getHibConfig() {
    	buildSessionFactory();
		return hibConfig;
	}

	public static synchronized void buildSessionFactory() {
        if (sessionFactory == null) {
	        hibConfig = new Configuration();
	        hibConfig.configure("/ar/com/santalucia/accesodatos/persistencia/hibernate.cfg.xml");
	        //configuration.configure("/ar/com/santalucia/accesodatos/persistencia/backConfig.cfg.xml");
	        hibConfig.setProperty("hibernate.current_session_context_class", "thread");
	        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(hibConfig.getProperties()).buildServiceRegistry();
	        sessionFactory = hibConfig.buildSessionFactory(serviceRegistry);
       }
   }

   public static void openSessionAndBindToThread() {
       Session session = sessionFactory.openSession();
       ThreadLocalSessionContext.bind(session);
   }


   public static SessionFactory getSessionFactory() {
       if (sessionFactory==null)  {
           buildSessionFactory();
       }
       return sessionFactory;
   }

   public static void closeSessionAndUnbindFromThread() {
       Session session = ThreadLocalSessionContext.unbind(sessionFactory);
       if (session!=null) {
           session.close();
       }
   }

   public static void closeSessionFactory() {
       if ((sessionFactory!=null) && (sessionFactory.isClosed()==false)) {
           sessionFactory.close();
       }
   }	


}