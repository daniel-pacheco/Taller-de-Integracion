package ar.com.santalucia.aplicacion.gestor.usuario.info;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.usuario.info.TelefonoHome;
import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;

/**
 * Clase gestor de Telefono
 * 
 * @author ericd
 * @version 1.0
 *
 */

//UltimoModificador: Ariel Ramirez @ 29-08-15 16:00

public class GestorTelefono extends Gestor<Telefono> {
	private TelefonoHome telefonoDAO;

	public GestorTelefono() throws Exception {
		try {
			sesionDeHilo = HibernateUtil.getSessionFactory().getCurrentSession();
			telefonoDAO = new TelefonoHome();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar la persistencia: " + ex.getMessage());
		}
	}

	/**
	 * Nota: Este método no hace efectiva la persistencia (commit) ya que debe
	 * ser parte de una transacción mayor por ser parte de una composición. Use
	 * closeTransaction() para hacer efectiva la transacción o
	 * sesionDeHilo.getTransaction().commit().
	 */
	@Override
	public void add(Telefono object) throws Exception {
		try {
			setSession();
			setTransaction();
			telefonoDAO.persist(object);
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al agregar el objeto: " + ex.getMessage());
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
			throw new Exception("Ha ocurrido un error al modificar el objeto: " + ex.getMessage());
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
			throw new Exception("Ha ocurrido un error al eliminar el objeto: " + ex.getMessage());
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
			throw new Exception("Ha ocurrido un error al buscar el objeto por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Telefono> getByExample(Telefono example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Telefono> listaTelefonoDevolver = (ArrayList<Telefono>) telefonoDAO
					.findByExample((Telefono) example);
			return listaTelefonoDevolver;
		} catch (Exception ex) {
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());

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
			return listaTelefonoDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los telefonos: " + ex.getMessage());

		}
	}

	@Override
	public void validar(Telefono object) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
