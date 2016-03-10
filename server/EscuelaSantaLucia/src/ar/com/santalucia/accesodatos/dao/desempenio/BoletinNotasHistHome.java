package ar.com.santalucia.accesodatos.dao.desempenio;
// Generated 10/03/2016 17:10:44 by Hibernate Tools 4.3.1.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotasHist;

/**
 * Home object for domain model class BoletinNotasHist.
 * @see ar.com.santalucia.dominio.modelo.desempenio.BoletinNotasHist
 * @author Hibernate Tools
 */
public class BoletinNotasHistHome {

	private static final Log log = LogFactory.getLog(BoletinNotasHistHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(BoletinNotasHist transientInstance) {
		log.debug("persisting BoletinNotasHist instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(BoletinNotasHist instance) {
		log.debug("attaching dirty BoletinNotasHist instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BoletinNotasHist instance) {
		log.debug("attaching clean BoletinNotasHist instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(BoletinNotasHist persistentInstance) {
		log.debug("deleting BoletinNotasHist instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BoletinNotasHist merge(BoletinNotasHist detachedInstance) {
		log.debug("merging BoletinNotasHist instance");
		try {
			BoletinNotasHist result = (BoletinNotasHist) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public BoletinNotasHist findById(java.lang.Long id) {
		log.debug("getting BoletinNotasHist instance with id: " + id);
		try {
			BoletinNotasHist instance = (BoletinNotasHist) sessionFactory.getCurrentSession()
					.get("ar.com.santalucia.dominio.modelo.desempenio.BoletinNotasHist", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BoletinNotasHist instance) {
		log.debug("finding BoletinNotasHist instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.santalucia.dominio.modelo.desempenio.BoletinNotasHist")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
