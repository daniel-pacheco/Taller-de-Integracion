package ar.com.santalucia.accesodatos.dao.usuario;
// Generated 04/07/2015 12:20:19 by Hibernate Tools 4.3.1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.dominio.modelo.usuarios.Administrador;

/**
 * Home object for domain model class Administrador.
 * @see ar.com.santalucia.dominio.modelo.usuarios.Administrador
 * @author Hibernate Tools
 */
public class AdministradorHome {

	private static final Log log = LogFactory.getLog(AdministradorHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Administrador transientInstance) {
		log.debug("persisting Administrador instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Administrador instance) {
		log.debug("attaching dirty Administrador instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Administrador instance) {
		log.debug("attaching clean Administrador instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Administrador persistentInstance) {
		log.debug("deleting Administrador instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Administrador merge(Administrador detachedInstance) {
		log.debug("merging Administrador instance");
		try {
			Administrador result = (Administrador) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Administrador findById(java.lang.Long id) {
		log.debug("getting Administrador instance with id: " + id);
		try {
			Administrador instance = (Administrador) sessionFactory.getCurrentSession()
					.get("ar.com.santalucia.dominio.modelo.usuarios.Administrador", id);
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

	public List findByExample(Administrador instance) {
		log.debug("finding Administrador instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.santalucia.dominio.modelo.usuarios.Administrador")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
