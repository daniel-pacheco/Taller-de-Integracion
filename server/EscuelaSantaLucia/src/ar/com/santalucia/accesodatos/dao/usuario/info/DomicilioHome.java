package ar.com.santalucia.accesodatos.dao.usuario.info;
// Generated 18/08/2015 18:54:40 by Hibernate Tools 4.3.1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;

/**
 * Home object for domain model class Domicilio.
 * @see ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio
 * @author Hibernate Tools
 */
public class DomicilioHome {

	private static final Log log = LogFactory.getLog(DomicilioHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Domicilio transientInstance) {
		log.debug("persisting Domicilio instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Domicilio instance) {
		log.debug("attaching dirty Domicilio instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Domicilio instance) {
		log.debug("attaching clean Domicilio instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Domicilio persistentInstance) {
		log.debug("deleting Domicilio instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Domicilio merge(Domicilio detachedInstance) {
		log.debug("merging Domicilio instance");
		try {
			Domicilio result = (Domicilio) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Domicilio findById(java.lang.Long id) {
		log.debug("getting Domicilio instance with id: " + id);
		try {
			Domicilio instance = (Domicilio) sessionFactory.getCurrentSession()
					.get("ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio", id);
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

	public List findByExample(Domicilio instance) {
		log.debug("finding Domicilio instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
