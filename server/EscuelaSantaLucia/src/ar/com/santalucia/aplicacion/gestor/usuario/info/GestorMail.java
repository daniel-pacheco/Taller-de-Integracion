package ar.com.santalucia.aplicacion.gestor.usuario.info;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.usuario.info.MailHome;
import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;


/**
 * Clase gestor de Domicilio
 * 
 * @author ericd
 * @version 1.0
 */

// UltimoModificador: Ariel Ramirez @ 29-08-15 16:00
 
 public class GestorMail extends Gestor<Mail> {
	private MailHome mailDAO;

	public GestorMail() throws Exception {
		try {
			sesionDeHilo = HibernateUtil.getSessionFactory().getCurrentSession();
			mailDAO = new MailHome();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar la persistencia: " + ex.getMessage());
		}
	}

	/**
	 * Nota: Domicilio. Este método no hace efectiva la persistencia (commit) ya
	 * que debe ser parte de una transacción mayor por ser parte de una
	 * composición. Use closeTransaction() para hacer efectiva la transacción o
	 * sesionDeHilo.getTransaction().commit().
	 */
	@Override
	public void add(Mail object) throws Exception {
		try {
			setSession();
			setTransaction();
			mailDAO.persist(object);
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al agregar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public void modify(Mail object) throws Exception {
		try {
			setSession();
			setTransaction();
			mailDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al modificar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public void delete(Mail object) throws Exception {
		try {
			setSession();
			setTransaction();
			mailDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al modificar el objeto: " + ex.getMessage());
		}

	}

	@Override
	public Mail getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Mail mailDevolver = new Mail();
			mailDevolver = mailDAO.findById(id);
			return mailDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al buscar el objeto por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Mail> getByExample(Mail example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Mail> listaMailDevolver = (ArrayList<Mail>) mailDAO.findByExample((Mail) example);
			return listaMailDevolver;
		} catch (Exception ex) {
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());

		}
	}

	@Override
	public ArrayList<Mail> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Mail criterioVacio = new Mail();
			ArrayList<Mail> listaMailDevolver = new ArrayList<Mail>();
			listaMailDevolver = (ArrayList<Mail>) mailDAO.findByExample(criterioVacio);
			return listaMailDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los alumnos: " + ex.getMessage());

		}

	}

}
