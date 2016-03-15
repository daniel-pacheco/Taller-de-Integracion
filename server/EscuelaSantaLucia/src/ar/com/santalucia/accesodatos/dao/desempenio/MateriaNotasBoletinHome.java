package ar.com.santalucia.accesodatos.dao.desempenio;
// Generated 10/03/2016 18:13:58 by Hibernate Tools 4.3.1.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.dominio.modelo.desempenio.MateriaNotasBoletin;

/**
 * Home object for domain model class MateriaNotasBoletin.
 * @see ar.com.santalucia.dominio.modelo.desempenio.MateriaNotasBoletin
 * @author Hibernate Tools
 */
public class MateriaNotasBoletinHome {

	private static final Log log = LogFactory.getLog(MateriaNotasBoletinHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(MateriaNotasBoletin transientInstance) {
		log.debug("persisting MateriaNotasBoletin instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(MateriaNotasBoletin instance) {
		log.debug("attaching dirty MateriaNotasBoletin instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MateriaNotasBoletin instance) {
		log.debug("attaching clean MateriaNotasBoletin instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(MateriaNotasBoletin persistentInstance) {
		log.debug("deleting MateriaNotasBoletin instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MateriaNotasBoletin merge(MateriaNotasBoletin detachedInstance) {
		log.debug("merging MateriaNotasBoletin instance");
		try {
			MateriaNotasBoletin result = (MateriaNotasBoletin) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public MateriaNotasBoletin findById(java.lang.Long id) {
		log.debug("getting MateriaNotasBoletin instance with id: " + id);
		try {
			MateriaNotasBoletin instance = (MateriaNotasBoletin) sessionFactory.getCurrentSession()
					.get("ar.com.santalucia.dominio.modelo.desempenio.MateriaNotasBoletin", id);
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

	public List findByExample(MateriaNotasBoletin instance) {
		log.debug("finding MateriaNotasBoletin instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.santalucia.dominio.modelo.desempenio.MateriaNotasBoletin")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
