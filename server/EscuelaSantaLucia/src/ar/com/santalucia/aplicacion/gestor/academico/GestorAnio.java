package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.AnioHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.validaciones.IValidacionAnio;

/**
 * Gestor de años
 * 
 * @author ericpennachini
 * @version 1.0
 *
 */

public class GestorAnio extends Gestor<Anio>implements IValidacionAnio {

	private AnioHome anioDAO;
	private GestorCurso GCurso;
	private GestorMateria GMateria;
	
	public GestorAnio() throws Exception {
		super();
		try {
			anioDAO = new AnioHome();
			GCurso = new GestorCurso();
			GMateria = new GestorMateria();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(Anio object) throws Exception {
		try {
			setSession();
			setTransaction();
			this.validar(object);
			if (object.getListaCursos() != null) {
				for (Curso c : object.getListaCursos()) {
					GCurso.add(c);
				} 
			}
			/*
			for (Materia m: object.getListaMaterias()) {
				GMateria.add(m);
			}
			/**
			 * Se llama otra vez a estos dos metodos porque la materia
			 * cierra la transacción, y la tiene que cerrar porque 
			 * se puede dar de alta individualmente, por fuera del Anio.
			 
			setSession();
			setTransaction();
			*/
			anioDAO.persist(object);
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
	public void modify(Anio object) throws Exception {
		try {
			setSession();
			setTransaction();
			this.validar(object);
			anioDAO.attachDirty(object);
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
	public void delete(Anio object) throws Exception {
		try {
			setSession();
			setTransaction();
			anioDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public Anio getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Anio anioDevolver = new Anio();
			anioDevolver = anioDAO.findById(id);
			return anioDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el objeto por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Anio> getByExample(Anio example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Anio> listaAniosDevolver = (ArrayList<Anio>) anioDAO.findByExample((Anio) example);
			return listaAniosDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Anio> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Anio criterioVacio = new Anio();
			ArrayList<Anio> listaAniosDevolver = new ArrayList<Anio>();
			listaAniosDevolver = (ArrayList<Anio>) anioDAO.findByExample(criterioVacio);
			return listaAniosDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los alumnos: " + ex.getMessage());
		}
	}
	
	/*
	 * Implementación de IValidacionAnio
	 * (non-Javadoc)
	 * @see ar.com.santalucia.validaciones.IValidacionAnio#existeNombreAnio(java.lang.String)
	 */

	@Override
	public Boolean existeNombreAnio(String nombreAnio) {
		Anio anioEjemplo = new Anio();
		anioEjemplo.setNombre(nombreAnio);
		ArrayList<Anio> ejemplos = new ArrayList<Anio>();
		try {
			ejemplos = this.getByExample(anioEjemplo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (ejemplos.isEmpty() ? false : true);
	}

	@Override
	public Boolean existeCurso(Character divisionCurso, Anio anio) {
		Boolean existeCurso = new Boolean(false);
		for (Curso c: anio.getListaCursos()) {
			if (c.getDivision() == divisionCurso) {
				existeCurso = true;
				return existeCurso;
			}
		} 
		return existeCurso;
	}
	
	@Override
	public Boolean existeMateria(String nombreMateria) {
		Materia materiaEjemplo = new Materia();
		materiaEjemplo.setNombre(nombreMateria);
		ArrayList<Materia> ejemplos = new ArrayList<Materia>();
		try {
			ejemplos = GMateria.getByExample(materiaEjemplo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (ejemplos.isEmpty() ? false : true);
	}
	

	@Override
	public void validar(Anio object) throws Exception {
		Boolean vNombre;
		ValidacionException exception = new ValidacionException();
		
		vNombre = this.existeNombreAnio(object.getNombre());
		for (Curso c: object.getListaCursos()) {
			exception.addMensajeError((this.existeCurso(c.getDivision(), object) 
										? "El curso: " + c.getDivision() + " ya existe en el año." 
										: null));
		}
		

		for (Materia m: object.getListaMaterias()) {
			exception.addMensajeError((this.existeMateria(m.getNombre()) 
										? "La materia: " + m.getNombre() + " ya existe en la base de datos" 
										: null));
		}
		
		exception.addMensajeError(vNombre ? "El nombre " + object.getNombre() +" ya existe" : null);
		
		if (!exception.getMensajesError().isEmpty()) {
			throw exception;
		}
	}
}
