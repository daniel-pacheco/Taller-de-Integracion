package ar.com.santalucia.accesodatos.dao.academico;
// Generated 19/09/2015 17:43:36 by Hibernate Tools 4.3.1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.dominio.modelo.academico.Curso;

/**
 * Home object for domain model class Curso.
 * @see ar.com.santalucia.dominio.modelo.academico.Curso
 * @author Hibernate Tools
 */
public class CursoHome {

	private static final Log log = LogFactory.getLog(CursoHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Curso transientInstance) {
		log.debug("persisting Curso instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Curso instance) {
		log.debug("attaching dirty Curso instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Curso instance) {
		log.debug("attaching clean Curso instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Curso persistentInstance) {
		log.debug("deleting Curso instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Curso merge(Curso detachedInstance) {
		log.debug("merging Curso instance");
		try {
			Curso result = (Curso) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Curso findById(java.lang.Long id) {
		log.debug("getting Curso instance with id: " + id);
		try {
			Curso instance = (Curso) sessionFactory.getCurrentSession()
					.get("ar.com.santalucia.dominio.modelo.academico.Curso", id);
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

	public List findByExample(Curso instance) {
		log.debug("finding Curso instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("ar.com.santalucia.dominio.modelo.academico.Curso").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
