package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.MateriaHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorPersonal;
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

public class GestorMateria extends Gestor<Materia> implements IValidacionMateria, IListable<Materia> {

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
			//this.validar(object);
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
			throw new Exception("Ha ocurrido un problema al agregar la MATERIA: " + ex.getMessage());
		}
	}

	@Override
	public void modify(Materia object) throws Exception {
		try {
			//this.validar(object);
			setSession();
			setTransaction();
			materiaDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar la MATERIA: " + ex.getMessage());
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
			throw new Exception("Ha ocurrido un problema al eliminar la MATERIA: " + ex.getMessage());
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
			throw new Exception("Ha ocurrido un error al buscar la MATERIA por su ID: " + ex.getMessage());
		}
	}
	
	@Override
	public ArrayList<Materia> getByExample(Materia example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Materia> listaMateriasDevolver = (ArrayList<Materia>) materiaDAO.findByExample((Materia) example);
			sesionDeHilo.getTransaction().commit();
			return listaMateriasDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar MATERIAS que coincidan con el ejemplo dado: " + ex.getMessage());
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
			sesionDeHilo.getTransaction().commit();
			return listaMateriasDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar las MATERIAS: " + ex.getMessage());
		}
	}

	/*
	 * Implementación de IValidacionMateria
	 */

	@Override
	public Boolean existeMateria(Materia materia) throws Exception {
		/*
		Materia materiaEjemplo = new Materia();
		materiaEjemplo.setNombre(nombreMateria);
		ArrayList<Materia> ejemplos = new ArrayList<Materia>();
		try {
			ejemplos = this.getByExample(materiaEjemplo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (ejemplos.isEmpty() ? false : true);
		*/
		
		Boolean resultado = false;
		Materia materiaEjemplo = new Materia();
		materiaEjemplo.setNombre(materia.getNombre());
		try {
			ArrayList<Materia> listaMaterias = this.getByExample(materiaEjemplo);
			if (materia.getIdMateria() == null) {
				resultado = (listaMaterias.isEmpty() ? false : true);
			} else {
				if (!listaMaterias.isEmpty()) {
					Materia materiaTemp = new Materia();
					for (Materia m : listaMaterias) {
						materiaTemp = m;
					}
					if (materiaTemp.getIdMateria().equals(materia.getIdMateria())) {
						resultado = false;
					} else {
						resultado = true;
					}
				}
			}
		} catch (Exception ex) {
			// Excepción no tratada
			throw ex;
		}
		return resultado;
	}

	@Override
	public void validar(Object object) throws Exception {
		Materia materia = (Materia) object;
		Boolean vMateria;
		ValidacionException exception = new ValidacionException();
		
		vMateria = this.existeMateria(materia);
		
		exception.addMensajeError(vMateria 
									? "La materia ya existe" 
									: null);

		if (!exception.getMensajesError().isEmpty()) {
			throw exception;
		}
	}

}
