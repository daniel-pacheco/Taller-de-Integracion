package ar.com.santalucia.accesodatos.dao.desempenio;
// Generated 22/09/2016 15:32:09 by Hibernate Tools 4.3.1.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.dominio.modelo.desempenio.RegistroPasajeAlumnos;

/**
 * Home object for domain model class RegistroPasajeAlumnos.
 * @see ar.com.santalucia.dominio.modelo.desempenio.RegistroPasajeAlumnos
 * @author Hibernate Tools
 */
public class RegistroPasajeAlumnosHome {

	private static final Log log = LogFactory.getLog(RegistroPasajeAlumnosHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(RegistroPasajeAlumnos transientInstance) {
		log.debug("persisting RegistroPasajeAlumnos instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(RegistroPasajeAlumnos instance) {
		log.debug("attaching dirty RegistroPasajeAlumnos instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RegistroPasajeAlumnos instance) {
		log.debug("attaching clean RegistroPasajeAlumnos instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(RegistroPasajeAlumnos persistentInstance) {
		log.debug("deleting RegistroPasajeAlumnos instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RegistroPasajeAlumnos merge(RegistroPasajeAlumnos detachedInstance) {
		log.debug("merging RegistroPasajeAlumnos instance");
		try {
			RegistroPasajeAlumnos result = (RegistroPasajeAlumnos) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public RegistroPasajeAlumnos findById(java.lang.Long id) {
		log.debug("getting RegistroPasajeAlumnos instance with id: " + id);
		try {
			RegistroPasajeAlumnos instance = (RegistroPasajeAlumnos) sessionFactory.getCurrentSession()
					.get("ar.com.santalucia.dominio.modelo.desempenio.RegistroPasajeAlumnos", id);
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

	public List findByExample(RegistroPasajeAlumnos instance) {
		log.debug("finding RegistroPasajeAlumnos instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.santalucia.dominio.modelo.desempenio.RegistroPasajeAlumnos")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
