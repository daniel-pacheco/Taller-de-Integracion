package ar.com.santalucia.accesodatos.dao.aulavirtual;

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.dominio.modelo.aulavirtual.ConfiguracionEntrega;

/**
 * Home object for domain model class ConfiguracionEntrega.
 * @see ar.com.santalucia.dominio.modelo.aulavirtual.ConfiguracionEntrega
 * @author Hibernate Tools
 */
public class ConfiguracionEntregaHome {

	private static final Log log = LogFactory.getLog(ConfiguracionEntregaHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(ConfiguracionEntrega transientInstance) {
		log.debug("persisting ConfiguracionEntrega instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ConfiguracionEntrega instance) {
		log.debug("attaching dirty ConfiguracionEntrega instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ConfiguracionEntrega instance) {
		log.debug("attaching clean ConfiguracionEntrega instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ConfiguracionEntrega persistentInstance) {
		log.debug("deleting ConfiguracionEntrega instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ConfiguracionEntrega merge(ConfiguracionEntrega detachedInstance) {
		log.debug("merging ConfiguracionEntrega instance");
		try {
			ConfiguracionEntrega result = (ConfiguracionEntrega) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ConfiguracionEntrega findById(java.lang.Long id) {
		log.debug("getting ConfiguracionEntrega instance with id: " + id);
		try {
			ConfiguracionEntrega instance = (ConfiguracionEntrega) sessionFactory.getCurrentSession()
					.get("ar.com.santalucia.dominio.modelo.aulavirtual.ConfiguracionEntrega", id);
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

	public List findByExample(ConfiguracionEntrega instance) {
		log.debug("finding ConfiguracionEntrega instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.santalucia.dominio.modelo.aulavirtual.ConfiguracionEntrega").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
