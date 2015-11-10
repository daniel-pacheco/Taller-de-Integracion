package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;
import java.util.Set;

import ar.com.santalucia.accesodatos.dao.academico.AnioHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.usuarios.Usuario;
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
			this.validar(object);
			setSession();
			setTransaction();
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
			**/ 
			setSession();
			setTransaction();
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

	
	public ArrayList<Anio> getByExample(Anio example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Anio> listaAniosDevolver = (ArrayList<Anio>) anioDAO.findByExample((Anio) example);
			sesionDeHilo.getTransaction().commit();
			return listaAniosDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	public ArrayList<Anio> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Anio criterioVacio = new Anio();
			ArrayList<Anio> listaAniosDevolver = new ArrayList<Anio>();
			listaAniosDevolver = (ArrayList<Anio>) anioDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
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

	
	public Boolean existeNombreAnio(Anio anio) throws Exception{
		Boolean resultado = false;
		Anio anioEjemplo = new Anio();
		anioEjemplo.setNombre(anio.getNombre());
		try {
			ArrayList<Anio> listaAnios = this.getByExample(anioEjemplo);
			if (anio.getIdAnio() == null) {
				resultado = (listaAnios.isEmpty() ? false : true);
			} else {
				if (!listaAnios.isEmpty()) {
					Anio anioTemp = new Anio();
					for (Anio a : listaAnios) {
						anioTemp = a;
					}
					if (anioTemp.getIdAnio().equals(anio.getIdAnio())) {
						resultado = false;
					} else {
						resultado = true;
					}
				}
			}
		} catch (Exception ex) {
			throw ex;
		}
		return resultado;
	}

	public Boolean existeCurso(Character divisionCurso, Anio anio) throws Exception{
		// TODO
		// 1 - Obtener el año al que pertenece el curso
		// 2 - Rescatar el listado de curso
		// 3 - Comprobar si existe el curso en el listado
		// 4 - Devolver true si se encontro
		Boolean existeCurso = new Boolean(false);
		Curso cursoExample = new Curso();
		cursoExample.setDivision(divisionCurso);
		try{
			if(anio.getIdAnio() != null) // Esto es por si el año no existe
			{
				Anio anioBusqueda = this.getById(anio.getIdAnio());
				Set<Curso> cursos = anioBusqueda.getListaCursos();
				// Se uso contains porque es un arreglo de char y se sobrecargó equals
				existeCurso = cursos.contains(divisionCurso);
				return existeCurso;
			}
		} catch(Exception ex){
			// La excepcion no está tratada!
			throw new Exception("El proceso de validación de curso ha fallado. " + ex.getMessage());
		}
		return existeCurso;
		//Boolean existeCurso = new Boolean(false);
//		for (Curso c: anio.getListaCursos()) {
//			if (c.getDivision() == divisionCurso) {
//				existeCurso = true;
//				return existeCurso;
//			}
//		} 	
	}
	
	@Override
	public Boolean existeMateria(String nombreMateria) throws Exception{
		// Este caso es diferente al de curso porque busca entidades generales, no un arreglo de char como curso
		Materia materiaEjemplo = new Materia();
		materiaEjemplo.setNombre(nombreMateria);
		ArrayList<Materia> ejemplos = new ArrayList<Materia>();
		try {
			ejemplos = GMateria.getByExample(materiaEjemplo);
		} catch (Exception ex) {
			throw new Exception("El proceso de validación de materia ha fallado. " + ex.getMessage());
		}
		return (ejemplos.isEmpty() ? false : true);
	}
	
	public void validar(Anio object) throws Exception {
		Boolean vNombre;
		ValidacionException exception = new ValidacionException();
		
		vNombre = this.existeNombreAnio(object);
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
