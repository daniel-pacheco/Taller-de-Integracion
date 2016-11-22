package ar.com.santalucia.aplicacion.gestor.desempenio;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.desempenio.RegistroPasajeAlumnosHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.desempenio.RegistroPasajeAlumnos;
import ar.com.santalucia.excepciones.ValidacionException;

public class GestorRegistroPasajeAlumnos extends Gestor<RegistroPasajeAlumnos> implements IListable<RegistroPasajeAlumnos> {

	private RegistroPasajeAlumnosHome registroPasajeAlumnosDAO;
	
	public GestorRegistroPasajeAlumnos() throws Exception {
		super();
		try {
			registroPasajeAlumnosDAO = new RegistroPasajeAlumnosHome();
		} catch (Exception ex) {			
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(RegistroPasajeAlumnos object) throws Exception {
		try {
			setSession();
			setTransaction();
			registroPasajeAlumnosDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el REGISTRO DE PASAJE DE ALUMNOS: " + ex.getMessage());
		}
	}

	@Override
	public void modify(RegistroPasajeAlumnos object) throws Exception {
		try {
			setSession();
			setTransaction();
			registroPasajeAlumnosDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el REGISTRO DE PASAJE DE ALUMNOS: " + ex.getMessage());
		}
	}

	@Override
	public void delete(RegistroPasajeAlumnos object) throws Exception {
		try {
			setSession();
			setTransaction();
			registroPasajeAlumnosDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el REGISTRO DE PASAJE DE ALUMNOS: " + ex.getMessage());
		}
	}

	@Override
	public RegistroPasajeAlumnos getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			RegistroPasajeAlumnos registroPasajeAlumnosDevolver = new RegistroPasajeAlumnos();
			registroPasajeAlumnosDevolver = registroPasajeAlumnosDAO.findById(id);
			return registroPasajeAlumnosDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el REGISTRO DE PASAJE DE ALUMNOS por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<RegistroPasajeAlumnos> getByExample(RegistroPasajeAlumnos example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<RegistroPasajeAlumnos> listaRegistroPasajeAlumnosDevolver = (ArrayList<RegistroPasajeAlumnos>) registroPasajeAlumnosDAO.findByExample((RegistroPasajeAlumnos) example);
			sesionDeHilo.getTransaction().commit();
			return listaRegistroPasajeAlumnosDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar REGISTROS DE PASAJE DE ALUMNOS que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<RegistroPasajeAlumnos> List() throws Exception {
		try {
			setSession();
			setTransaction();
			RegistroPasajeAlumnos criterioVacio = new RegistroPasajeAlumnos();
			ArrayList<RegistroPasajeAlumnos> listaRegistroPasajeAlumnosDevolver = new ArrayList<RegistroPasajeAlumnos>();
			listaRegistroPasajeAlumnosDevolver = (ArrayList<RegistroPasajeAlumnos>) registroPasajeAlumnosDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaRegistroPasajeAlumnosDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los REGISTROS DE PASAJE DE ALUMNOSS: " + ex.getMessage());
		}
	}

}
