package ar.com.santalucia.accesodatos.dao.sistema.configuracion;
// Generated 04/07/2015 12:20:19 by Hibernate Tools 4.3.1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.dominio.modelo.sistema.configuracion.ParametroConfiguracion;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;

/**
 * Home object for domain model class ParametroConfiguracion.
 * @see ar.com.santalucia.dominio.modelo.sistema.configuracion.ParametroConfiguracion
 * @author Hibernate Tools
 */
public class ParametroConfiguracionHome {

	private static final Log log = LogFactory.getLog(ParametroConfiguracionHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(ParametroConfiguracion transientInstance) {
		log.debug("persisting ParametroConfiguracion instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ParametroConfiguracion instance) {
		log.debug("attaching dirty ParametroConfiguracion instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ParametroConfiguracion instance) {
		log.debug("attaching clean Personal instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ParametroConfiguracion persistentInstance) {
		log.debug("deleting ParametroConfiguracion instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ParametroConfiguracion merge(ParametroConfiguracion detachedInstance) {
		log.debug("merging Personal instance");
		try {
			ParametroConfiguracion result = (ParametroConfiguracion) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ParametroConfiguracion findById(java.lang.Long id) {
		log.debug("getting ParametroConfiguracion instance with id: " + id);
		try {
			ParametroConfiguracion instance = (ParametroConfiguracion) sessionFactory.getCurrentSession()
					.get("ar.com.santalucia.dominio.modelo.sistema.configuracion.ParametroConfiguracion", id);
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

	public List<ParametroConfiguracion> findByExample(ParametroConfiguracion instance) {
		log.debug("finding ParametroConfiguracion instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.santalucia.dominio.modelo.sistema.configuracion.ParametroConfiguracion").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
