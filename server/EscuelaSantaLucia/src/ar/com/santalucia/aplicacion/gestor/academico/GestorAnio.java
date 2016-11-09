package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ar.com.santalucia.accesodatos.dao.academico.AnioHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.usuarios.Usuario;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.validaciones.IValidacionAnio;

/**
 * Gestor de a�os
 * 
 * @author ericpennachini
 * @version 1.0
 *
 */

//�ltimo modificador: Ariel Ramirez @ 09-12-2015 19:53

public class GestorAnio extends Gestor<Anio> implements IValidacionAnio, IListable<Anio> {

	private AnioHome anioDAO;
	private GestorCurso GCurso;
	
	public GestorAnio() throws Exception {
		super();
		try {
			anioDAO = new AnioHome();
			GCurso = new GestorCurso();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(Anio object) throws Exception {
		try {
			this.validar(object);
			if (object.getListaCursos() != null) {
				for (Curso c : object.getListaCursos()) {
					GCurso.add(c);
				} 
			}
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
			throw new Exception("Ha ocurrido un problema al agregar el A�O: " + ex.getMessage());
		}
		
	}

	@Override
	public void modify(Anio object) throws Exception {
		try {
			this.validar(object);
			closeSession();
			if (object.getListaCursos() != null) {
				for (Curso c : object.getListaCursos()) {
					if(c.getIdCurso() == null){
						GCurso.add(c);
					}else{
						GCurso.modify(c);
					}
				}
			} 
			setSession();
			setTransaction();
			anioDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el A�O: " + ex.getMessage());
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
			throw new Exception("Ha ocurrido un problema al eliminar el A�O: " + ex.getMessage());
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
			throw new Exception("Ha ocurrido un error al buscar el A�O por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Anio> getByExample(Anio example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Anio> listaAniosDevolver = (ArrayList<Anio>) anioDAO.findByExample(example);
			sesionDeHilo.getTransaction().commit();
			return listaAniosDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar A�OS que coincidan con el ejemplo dado: " + ex.getMessage());
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
			sesionDeHilo.getTransaction().commit();
			return listaAniosDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los A�OS: " + ex.getMessage());
		}
	}
	
	/*
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
					"Ha ocurrido un error al buscar A�OS que coincidan con el ejemplo dado: " + ex.getMessage());
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
			throw new Exception("Ha ocurrido un error al listar los A�OS: " + ex.getMessage());
		}
	}
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
			// Excepci�n no tratada
			throw ex;
		}
		return resultado;
	}

	@Override
	public Boolean existeCurso(Anio anio) throws Exception{
		Boolean existeCurso = new Boolean(false);
		try{
			Set<Curso> cursos = anio.getListaCursos();
			//existeCurso = cursos.contains(anio);
			/*
			for(Curso c: cursos){
				if(c.getIdCurso() != null){
					if(c.equals(curso)){
						existeCurso = true;
					}
				}
			}
			*/
			for(Curso c: cursos){
				for(Curso n: cursos){
					if((c.equals(n)) && ((c.getIdCurso() != n.getIdCurso()))) {
						existeCurso = true;
						break;
					}
				}
			}
			return existeCurso;
		} catch(Exception ex){
			throw new Exception("El proceso de validaci�n de curso ha fallado. " + ex.getMessage());
		}	
	}

	@Override
	public void validar(Object object) throws Exception {
		Anio anio = (Anio) object;
		
		Boolean vNombre;
		ValidacionException exception = new ValidacionException();		
		
		vNombre = this.existeNombreAnio(anio);
		exception.addMensajeError(vNombre ? "El nombre " + anio.getNombre() +" ya existe" : null);
		
		if (anio.getIdAnio() != null) {
			for (Curso c: anio.getListaCursos()) {
				exception.addMensajeError((this.existeCurso(anio) 
											? "El curso: " + c.getDivision() + " ya existe en el a�o." 
											: null));
			}
		}

		if (!exception.getMensajesError().isEmpty()) {
			throw exception;
		}
		
	}

}
