package ar.com.santalucia.accesodatos.dao.academico;
// Generated 23-ago-2016 20:05:01 by Hibernate Tools 4.3.1.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.dominio.modelo.academico.ActaVolanteExamenes;

/**
 * Home object for domain model class ActaVolanteExamenes.
 * @see ar.com.santalucia.dominio.modelo.academico.ActaVolanteExamenes
 * @author Hibernate Tools
 */
public class ActaVolanteExamenesHome {

	private static final Log log = LogFactory.getLog(ActaVolanteExamenesHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(ActaVolanteExamenes transientInstance) {
		log.debug("persisting ActaVolanteExamenes instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ActaVolanteExamenes instance) {
		log.debug("attaching dirty ActaVolanteExamenes instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ActaVolanteExamenes instance) {
		log.debug("attaching clean ActaVolanteExamenes instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ActaVolanteExamenes persistentInstance) {
		log.debug("deleting ActaVolanteExamenes instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ActaVolanteExamenes merge(ActaVolanteExamenes detachedInstance) {
		log.debug("merging ActaVolanteExamenes instance");
		try {
			ActaVolanteExamenes result = (ActaVolanteExamenes) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ActaVolanteExamenes findById(java.lang.Long id) {
		log.debug("getting ActaVolanteExamenes instance with id: " + id);
		try {
			ActaVolanteExamenes instance = (ActaVolanteExamenes) sessionFactory.getCurrentSession()
					.get("ar.com.santalucia.dominio.modelo.academico.ActaVolanteExamenes", id);
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

	public List findByExample(ActaVolanteExamenes instance) {
		log.debug("finding ActaVolanteExamenes instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.santalucia.dominio.modelo.academico.ActaVolanteExamenes")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
