package ar.com.santalucia.accesodatos.dao.desempenio;
// Generated 25/09/2015 19:37:31 by Hibernate Tools 4.3.1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.dominio.modelo.desempenio.Inasistencia;

/**
 * Home object for domain model class Inasistencia.
 * @see ar.com.santalucia.dominio.modelo.desempenio.Inasistencia
 * @author Hibernate Tools
 */
public class InasistenciaHome {

	private static final Log log = LogFactory.getLog(InasistenciaHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Inasistencia transientInstance) {
		log.debug("persisting Inasistencia instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Inasistencia instance) {
		log.debug("attaching dirty Inasistencia instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Inasistencia instance) {
		log.debug("attaching clean Inasistencia instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Inasistencia persistentInstance) {
		log.debug("deleting Inasistencia instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Inasistencia merge(Inasistencia detachedInstance) {
		log.debug("merging Inasistencia instance");
		try {
			Inasistencia result = (Inasistencia) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Inasistencia findById(java.lang.Long id) {
		log.debug("getting Inasistencia instance with id: " + id);
		try {
			Inasistencia instance = (Inasistencia) sessionFactory.getCurrentSession()
					.get("ar.com.santalucia.dominio.modelo.desempenio.Inasistencia", id);
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

	public List findByExample(Inasistencia instance) {
		log.debug("finding Inasistencia instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.santalucia.dominio.modelo.desempenio.Inasistencia")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
