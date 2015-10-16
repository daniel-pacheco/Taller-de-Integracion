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

public class GestorMateria extends Gestor<Materia> implements IValidacionMateria{

	private MateriaHome materiaDAO;
	private GestorDocente GDocente;
	
	public GestorMateria() throws Exception {
		super();
		try {
			materiaDAO = new MateriaHome();
			GDocente = new GestorDocente();
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
			if (object.getDocenteTitular() != null) {
				if (object.getDocenteTitular().getIdUsuario() == null) {
					GDocente.add(object.getDocenteTitular());
				} 
			}
			setSession();
			setTransaction();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Materia object) throws Exception {
		// TODO Auto-generated method stub
		
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
			ArrayList<Materia> listaAniosDevolver = (ArrayList<Materia>) materiaDAO.findByExample((Materia) example);
			return listaAniosDevolver;
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
			ArrayList<Materia> listaAniosDevolver = new ArrayList<Materia>();
			listaAniosDevolver = (ArrayList<Materia>) materiaDAO.findByExample(criterioVacio);
			return listaAniosDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los alumnos: " + ex.getMessage());
		}
	}
	
	/*
	 * Implementación de IValidacionMateria
	 */
	
	@Override
	public Boolean validarTipoDocente(String tipoDoc) {
		Boolean resultado = false;
		if ((tipoDoc=="Titular")||(tipoDoc=="Suplente")) {
			resultado = true;
		}
		return resultado;
	}

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
		Boolean vMateria, vTipoDoc;
		ValidacionException exception = new ValidacionException();
		
		vMateria = this.existeMateria(object.getNombre());
		vTipoDoc = this.validarTipoDocente(object.getTipoDocente());
		
		exception.addMensajeError(vMateria 
									? "La materia ya existe" 
									: null);
		exception.addMensajeError(vTipoDoc 
									? null 
									: "Tipo de docente no válido! Solo puede ser \"Titular\" o \"Suplente\" ");
		
		if (!exception.getMensajesError().isEmpty()) {
			throw exception;
		}
	}


}
