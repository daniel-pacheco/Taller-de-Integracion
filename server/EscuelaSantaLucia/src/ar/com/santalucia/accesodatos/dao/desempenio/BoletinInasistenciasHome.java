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
import ar.com.santalucia.dominio.modelo.desempenio.BoletinInasistencias;

/**
 * Home object for domain model class BoletinInasistencias.
 * @see ar.com.santalucia.dominio.modelo.desempenio.BoletinInasistencias
 * @author Hibernate Tools
 */
public class BoletinInasistenciasHome {

	private static final Log log = LogFactory.getLog(BoletinInasistenciasHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(BoletinInasistencias transientInstance) {
		log.debug("persisting BoletinInasistencias instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(BoletinInasistencias instance) {
		log.debug("attaching dirty BoletinInasistencias instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BoletinInasistencias instance) {
		log.debug("attaching clean BoletinInasistencias instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(BoletinInasistencias persistentInstance) {
		log.debug("deleting BoletinInasistencias instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BoletinInasistencias merge(BoletinInasistencias detachedInstance) {
		log.debug("merging BoletinInasistencias instance");
		try {
			BoletinInasistencias result = (BoletinInasistencias) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public BoletinInasistencias findById(java.lang.Long id) {
		log.debug("getting BoletinInasistencias instance with id: " + id);
		try {
			BoletinInasistencias instance = (BoletinInasistencias) sessionFactory.getCurrentSession()
					.get("ar.com.santalucia.dominio.modelo.desempenio.BoletinInasistencias", id);
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

	public List findByExample(BoletinInasistencias instance) {
		log.debug("finding BoletinInasistencias instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.santalucia.dominio.modelo.desempenio.BoletinInasistencias")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
