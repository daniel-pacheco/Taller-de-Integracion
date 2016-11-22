package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.ActaVolanteExamenesHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.academico.ActaVolanteExamenes;

/**
 * 
 * @author Ariel
 * @version 1.0
 */
public class GestorActaVolanteExamenes extends Gestor<ActaVolanteExamenes> implements IListable<ActaVolanteExamenes> {

	private ActaVolanteExamenesHome actaVolanteExamentesDAO;
	
	public GestorActaVolanteExamenes() throws Exception {
		super();
		actaVolanteExamentesDAO = new ActaVolanteExamenesHome();
	}

	@Override
	public void add(ActaVolanteExamenes object) throws Exception {
		try{
			setSession();
			setTransaction();
			actaVolanteExamentesDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el Acta Volante de Examen: " + ex.getMessage());
		}
		
	}

	@Override
	public void modify(ActaVolanteExamenes object) throws Exception {
		try{
			setSession();
			setTransaction();
			actaVolanteExamentesDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el Acta Volante de Examen: " + ex.getMessage());
		}
		
	}

	@Override
	public void delete(ActaVolanteExamenes object) throws Exception {
		try{
			setSession();
			setTransaction();
			actaVolanteExamentesDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el Acta Volante de Examen: " + ex.getMessage());
		}
		
	}
	
	@Override
	public ActaVolanteExamenes getById(Long id) throws Exception {
		try{
			setSession();
			setTransaction();
			ActaVolanteExamenes actaVolanteExamenes = new ActaVolanteExamenes();
			actaVolanteExamenes = actaVolanteExamentesDAO.findById(id);
			return actaVolanteExamenes;
		}catch(Exception ex){
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el Acta Volante de Examen por su ID: " + ex.getMessage());
		}
	}
	
	@Override
	public ArrayList<ActaVolanteExamenes> getByExample(ActaVolanteExamenes example) throws Exception {
		try{
			setSession();
			setTransaction();
			ArrayList<ActaVolanteExamenes> listaActaVolanteExamenes = (ArrayList<ActaVolanteExamenes>) actaVolanteExamentesDAO.findByExample(example);
			sesionDeHilo.getTransaction().commit();
			return listaActaVolanteExamenes;
		}catch(Exception ex){
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar Acta Volante de Examen que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<ActaVolanteExamenes> List() throws Exception {
		try{
			setSession();
			setTransaction();
			ActaVolanteExamenes criterioVacio = new ActaVolanteExamenes();
			ArrayList<ActaVolanteExamenes> listaActaVolanteExamenes = new ArrayList<ActaVolanteExamenes>();
			listaActaVolanteExamenes = (ArrayList<ActaVolanteExamenes>) actaVolanteExamentesDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaActaVolanteExamenes;
		}catch(Exception ex){
			closeSession();
			throw new Exception("Ha ocurrido un error al listar las Acta Volante de Examen: " + ex.getMessage());
		}
	}
}
