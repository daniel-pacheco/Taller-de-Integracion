package ar.com.santalucia.accesodatos.dao.academico;
// Generated 22/12/2015 18:55:28 by Hibernate Tools 4.3.1.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.dominio.modelo.academico.Especialidad;

/**
 * Home object for domain model class Especialidad.
 * @see ar.com.santalucia.dominio.modelo.academico.Especialidad
 * @author Hibernate Tools
 */
public class EspecialidadHome {

	private static final Log log = LogFactory.getLog(EspecialidadHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Especialidad transientInstance) {
		log.debug("persisting Especialidad instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Especialidad instance) {
		log.debug("attaching dirty Especialidad instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Especialidad instance) {
		log.debug("attaching clean Especialidad instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Especialidad persistentInstance) {
		log.debug("deleting Especialidad instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Especialidad merge(Especialidad detachedInstance) {
		log.debug("merging Especialidad instance");
		try {
			Especialidad result = (Especialidad) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Especialidad findById(java.lang.Long id) {
		log.debug("getting Especialidad instance with id: " + id);
		try {
			Especialidad instance = (Especialidad) sessionFactory.getCurrentSession()
					.get("ar.com.santalucia.dominio.modelo.academico.Especialidad", id);
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

	public List findByExample(Especialidad instance) {
		log.debug("finding Especialidad instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.santalucia.dominio.modelo.academico.Especialidad").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
