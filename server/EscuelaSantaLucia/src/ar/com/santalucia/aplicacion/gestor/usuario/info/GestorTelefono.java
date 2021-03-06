package ar.com.santalucia.aplicacion.gestor.usuario.info;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.usuario.info.TelefonoHome;
import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;

/**
 * Clase gestor de Telefono
 * 
 * @author ericd
 * @version 1.0
 *
 */

//UltimoModificador: Ariel Ramirez @ 29-08-15 16:00

public class GestorTelefono extends Gestor<Telefono> implements IListable<Telefono> {
	private TelefonoHome telefonoDAO;

	public GestorTelefono() throws Exception {
		try {
			sesionDeHilo = HibernateUtil.getSessionFactory().getCurrentSession();
			telefonoDAO = new TelefonoHome();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	/**
	 * Nota: Este m�todo no hace efectiva la persistencia (commit) ya que debe
	 * ser parte de una transacci�n mayor por ser parte de una composici�n. Use
	 * closeTransaction() para hacer efectiva la transacci�n o
	 * sesionDeHilo.getTransaction().commit().
	 */
	@Override
	public void add(Telefono object) throws Exception {
		try {
			setSession();
			setTransaction();
			telefonoDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al agregar el TEL�FONO: " + ex.getMessage());
		}

	}

	@Override
	public void modify(Telefono object) throws Exception {
		try {
			setSession();
			setTransaction();
			telefonoDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al modificar el TEL�FONO: " + ex.getMessage());
		}

	}

	@Override
	public void delete(Telefono object) throws Exception {
		try {
			setSession();
			setTransaction();
			telefonoDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al eliminar el TEL�FONO: " + ex.getMessage());
		}
	}

	@Override
	public Telefono getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Telefono telefonoDevolver = new Telefono();
			telefonoDevolver = telefonoDAO.findById(id);
			return telefonoDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al buscar el TEL�FONO por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Telefono> getByExample(Telefono example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Telefono> listaTelefonoDevolver = (ArrayList<Telefono>) telefonoDAO
					.findByExample((Telefono) example);
			sesionDeHilo.getTransaction().commit();
			return listaTelefonoDevolver;
		} catch (Exception ex) {
			throw new Exception(
					"Ha ocurrido un error al buscar TEL�FONOS que coincidan con el ejemplo dado: " + ex.getMessage());

		}
	}

	@Override
	public ArrayList<Telefono> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Telefono criterioVacio = new Telefono();
			ArrayList<Telefono> listaTelefonoDevolver = new ArrayList<Telefono>();
			listaTelefonoDevolver = (ArrayList<Telefono>) telefonoDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaTelefonoDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los TEL�FONOS: " + ex.getMessage());

		}
	}
	
}
