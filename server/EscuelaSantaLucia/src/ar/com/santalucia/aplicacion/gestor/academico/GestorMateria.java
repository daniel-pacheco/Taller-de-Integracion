package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.MateriaHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorDocente;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.validaciones.IValidacionMateria;

/**
 * Clase gestor de materias
 * 
 * @author ericpennachini
 * @version 1.0
 *
 */

// UltimoModificador: Eric Pennachini @ 23-10-2015 16:20

public class GestorMateria extends Gestor<Materia> implements IValidacionMateria{

	private MateriaHome materiaDAO;
	//private GestorDocente GDocente;
	
	public GestorMateria() throws Exception {
		super();
		try {
			materiaDAO = new MateriaHome();
			//GDocente = new GestorDocente();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(Materia object) throws Exception {
		try {
			setSession();
			setTransaction();
			this.validar(object);
			/*
			if (object.getDocenteTitular() != null) { // creo que est� de m�s...
				if (object.getDocenteTitular().getIdUsuario() == null) {
					GDocente.add(object.getDocenteTitular());
				} 
			}
			setSession();
			setTransaction();
			*/
			materiaDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public void modify(Materia object) throws Exception {
		try {
			setSession();
			setTransaction();
			this.validar(object);
			materiaDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el objeto: " + ex.getMessage());
		}
		
	}

	@Override
	public void delete(Materia object) throws Exception {
		try {
			setSession();
			setTransaction();
			materiaDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public Materia getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Materia materiaDevolver = new Materia();
			materiaDevolver = materiaDAO.findById(id);
			return materiaDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el objeto por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Materia> getByExample(Materia example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Materia> listaMateriasDevolver = (ArrayList<Materia>) materiaDAO.findByExample((Materia) example);
			return listaMateriasDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Materia> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Materia criterioVacio = new Materia();
			ArrayList<Materia> listaMateriasDevolver = new ArrayList<Materia>();
			listaMateriasDevolver = (ArrayList<Materia>) materiaDAO.findByExample(criterioVacio);
			return listaMateriasDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los alumnos: " + ex.getMessage());
		}
	}
	
	/*
	 * Implementaci�n de IValidacionMateria
	 */

	@Override
	public Boolean existeMateria(String nombreMateria) {
		Materia materiaEjemplo = new Materia();
		materiaEjemplo.setNombre(nombreMateria);
		ArrayList<Materia> ejemplos = new ArrayList<Materia>();
		try {
			ejemplos = this.getByExample(materiaEjemplo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (ejemplos.isEmpty() ? false : true);
	}

	@Override
	public void validar(Materia object) throws Exception {
		Boolean vMateria;
		ValidacionException exception = new ValidacionException();
		
		vMateria = this.existeMateria(object.getNombre());
		
		exception.addMensajeError(vMateria 
									? "La materia ya existe" 
									: null);

		if (!exception.getMensajesError().isEmpty()) {
			throw exception;
		}
	}


}
