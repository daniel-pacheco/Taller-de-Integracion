package ar.com.santalucia.aplicacion.gestor.aulavirtual;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.aulavirtual.RecursoHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.dominio.modelo.aulavirtual.Recurso;
/**
 * 
 * @author Ariel Ramirez
 *
 * @version 1.0
 *
 */
public class GestorRecurso extends Gestor<Recurso> {

	private RecursoHome recursoDAO;
	
	public GestorRecurso() throws Exception {
		super();
		recursoDAO = new RecursoHome();
	}

	@Override
	public void add(Recurso object) throws Exception {
		try{
			setSession();
			setTransaction();
			recursoDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el RECURSO: " + ex.getMessage());
		}
	}

	@Override
	public void modify(Recurso object) throws Exception {
		try{
			setSession();
			setTransaction();
			recursoDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el RECURSO: " + ex.getMessage());
		}
		
	}

	@Override
	public void delete(Recurso object) throws Exception {
		try {
			setSession();
			setTransaction();
			recursoDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el RECURSO: " + ex.getMessage());
		}
		
	}

	@Override
	public Recurso getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Recurso recursoDevolver = new Recurso();
			recursoDevolver = recursoDAO.findById(id);
			return recursoDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el RECURSO por su ID: " + ex.getMessage());
		}
	}
	
	public ArrayList<Recurso> getByExample(Recurso example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Recurso> listaRecursoDevolver = (ArrayList<Recurso>) recursoDAO.findByExample((Recurso) example);
			sesionDeHilo.getTransaction().commit();
			return listaRecursoDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar RECURSOS que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}
}
