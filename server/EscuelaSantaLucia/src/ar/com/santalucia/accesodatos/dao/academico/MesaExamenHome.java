package ar.com.santalucia.accesodatos.dao.academico;
// Generated 19/09/2015 17:43:36 by Hibernate Tools 4.3.1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.santalucia.dominio.modelo.academico.MesaExamen;

/**
 * Home object for domain model class MesaExamen.
 * @see ar.com.santalucia.dominio.modelo.academico.MesaExamen
 * @author Hibernate Tools
 */
public class MesaExamenHome {

	private static final Log log = LogFactory.getLog(MesaExamenHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(MesaExamen transientInstance) {
		log.debug("persisting MesaExamen instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(MesaExamen instance) {
		log.debug("attaching dirty MesaExamen instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MesaExamen instance) {
		log.debug("attaching clean MesaExamen instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(MesaExamen persistentInstance) {
		log.debug("deleting MesaExamen instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MesaExamen merge(MesaExamen detachedInstance) {
		log.debug("merging MesaExamen instance");
		try {
			MesaExamen result = (MesaExamen) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public MesaExamen findById(java.lang.Long id) {
		log.debug("getting MesaExamen instance with id: " + id);
		try {
			MesaExamen instance = (MesaExamen) sessionFactory.getCurrentSession()
					.get("ar.com.santalucia.dominio.modelo.academico.MesaExamen", id);
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

	public List findByExample(MesaExamen instance) {
		log.debug("finding MesaExamen instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.santalucia.dominio.modelo.academico.MesaExamen")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
