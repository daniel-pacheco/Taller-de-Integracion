package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;
import java.util.List;

import ar.com.santalucia.accesodatos.dao.academico.EspecialidadHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.academico.Especialidad;
import ar.com.santalucia.excepciones.ValidacionException;

public class GestorEspecialidad extends Gestor<Especialidad> implements IListable<Especialidad> {

	private EspecialidadHome especialidadDAO;
	
	public GestorEspecialidad() throws Exception {
		super();
		try{
			especialidadDAO = new EspecialidadHome();
		}catch(Exception ex){
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(Especialidad object) throws Exception,ValidacionException {
		try {
			Validar(object);
			setSession();
			setTransaction();
			especialidadDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar la ESPECIALIDAD: " + ex.getMessage());
		}
		
	}

	@Override
	public void modify(Especialidad object) throws Exception,ValidacionException {
		try {
			Validar(object);
			setSession();
			setTransaction();
			especialidadDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar la ESPECIALIDAD: " + ex.getMessage());
		}		
		
	}

	@Override
	public void delete(Especialidad object) throws Exception {
		try {
			setSession();
			setTransaction();
			especialidadDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar la ESPECIALIDAD: " + ex.getMessage());
		}
		
	}

	@Override
	public Especialidad getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Especialidad especialidadDevolver = new Especialidad();
			especialidadDevolver = especialidadDAO.findById(id);
			return especialidadDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar la ESPECIALIDAD por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Especialidad> getByExample(Especialidad example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Especialidad> listaEspecialidadDevolver = (ArrayList<Especialidad>) especialidadDAO.findByExample(example);
			sesionDeHilo.getTransaction().commit();
			return listaEspecialidadDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar ÁREAS que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Especialidad> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Especialidad criterioVacio = new Especialidad();
			ArrayList<Especialidad> listaEspecialidadDevolver = new ArrayList<Especialidad>();
			listaEspecialidadDevolver = (ArrayList<Especialidad>) especialidadDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaEspecialidadDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar las ESPECIALIDADES: " + ex.getMessage());
		}
	}

	public void Validar(Especialidad especialidad) throws Exception, ValidacionException{
		try {
			List<Especialidad> especialidades = this.List();
			if(especialidad.getIdEspecialidad() != null){
				for (Especialidad e : especialidades){
					if(e.getIdEspecialidad() == especialidad.getIdEspecialidad()){	// Quito la primer ocurrencia del objeto
						especialidades.remove(especialidad);
						break;
					}
				}
			}
			if(especialidades!=null && especialidades.size()>0){
				if(especialidades.contains(especialidad)){
					ValidacionException vEx = new ValidacionException();
					vEx.addMensajeError("La especilidad ya existe. No pueden existir especialidades con nombre y/o nombre corto duplicadas.");
					throw vEx;
				}
			}
		} catch (ValidacionException vEx) {
			throw vEx;
		} catch (Exception Ex) {
			throw Ex;
		}			
	}
	
}
