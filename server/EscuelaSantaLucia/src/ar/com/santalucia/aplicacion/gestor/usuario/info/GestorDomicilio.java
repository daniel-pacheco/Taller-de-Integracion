package ar.com.santalucia.aplicacion.gestor.usuario.info;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.usuario.info.DomicilioHome;
import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;


/**
 * Clase gestor de Domicilio
 * 
 * @author ericd
 * @version 1.0
 */

//UltimoModificador: Ariel Ramirez @ 29-08-15 16:00

public class GestorDomicilio extends Gestor<Domicilio> implements IListable<Domicilio> {
	private DomicilioHome domicilioDAO;

	public GestorDomicilio() throws Exception {
		try {
			sesionDeHilo = HibernateUtil.getSessionFactory().getCurrentSession();
			domicilioDAO = new DomicilioHome();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	/**
	 * Nota: Domicilio. Este método no hace efectiva la persistencia (commit) ya
	 * que debe ser parte de una transacción mayor por ser parte de una
	 * composición. Use closeTransaction() para hacer efectiva la transacción o
	 * sesionDeHilo.getTransaction().commit().
	 */
	@Override
	public void add(Domicilio object) throws Exception {
		try {
			setSession();
			setTransaction();
			domicilioDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al agregar el DOMICILIO: " + ex.getMessage());
		}

	}

	@Override
	public void modify(Domicilio object) throws Exception {
		try {
			setSession();
			setTransaction();
			domicilioDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al modificar el DOMICILIO: " + ex.getMessage());
		}

	}

	@Override
	public void delete(Domicilio object) throws Exception {
		try {
			setSession();
			setTransaction();
			domicilioDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al eliminar el DOMICILIO: " + ex.getMessage());
		}
	}

	@Override
	public Domicilio getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Domicilio domicilioDevolver = new Domicilio();
			domicilioDevolver = domicilioDAO.findById(id);
			return domicilioDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al buscar el DOMICILIO por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Domicilio> getByExample(Domicilio example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Domicilio> listaDomicilioDevolver = (ArrayList<Domicilio>) domicilioDAO
					.findByExample((Domicilio) example);
			sesionDeHilo.getTransaction().commit();
			return listaDomicilioDevolver;
		} catch (Exception ex) {
			throw new Exception(
					"Ha ocurrido un error al buscar DOMICILIOS que coincidan con el ejemplo dado: " + ex.getMessage());

		}
	}

	@Override
	public ArrayList<Domicilio> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Domicilio criterioVacio = new Domicilio();
			ArrayList<Domicilio> listaDomicilioDevolver = new ArrayList<Domicilio>();
			listaDomicilioDevolver = (ArrayList<Domicilio>) domicilioDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaDomicilioDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los DOMICILIOS: " + ex.getMessage());

		}
	}

}
