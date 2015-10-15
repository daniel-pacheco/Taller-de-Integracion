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
			for (Curso c: object.getListaCursos()) {
				GCurso.add(c);
			}
			for (Materia m: object.getListaMaterias()) {
				GMateria.add(m);
			}
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Anio object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Anio getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Anio> getByExample(Anio example) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Anio> List() throws Exception {
		// TODO Auto-generated method stub
		return null;
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
	public Boolean existeCurso(String nombreCurso) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean existeMateria(String nombreMateria) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void validar(Anio object) throws Exception {
		Boolean vNombre;
		ValidacionException exception = new ValidacionException();
		
	}
}
