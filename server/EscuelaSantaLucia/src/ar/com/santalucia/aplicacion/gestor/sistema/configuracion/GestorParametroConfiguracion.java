package ar.com.santalucia.aplicacion.gestor.sistema.configuracion;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.sistema.configuracion.ParametroConfiguracionHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.sistema.configuracion.ParametroConfiguracion;

public class GestorParametroConfiguracion extends Gestor<ParametroConfiguracion>
		implements IListable<ParametroConfiguracion> {

	private ParametroConfiguracionHome parametroConfiguracionDAO;
	
	public GestorParametroConfiguracion() throws Exception {
		super();
		try{
			parametroConfiguracionDAO = new ParametroConfiguracionHome();
		}catch(Exception ex){
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<ParametroConfiguracion> getByExample(ParametroConfiguracion example) throws Exception {
		try{
			setSession();
			setTransaction(); 
			ArrayList<ParametroConfiguracion> listaParametroConfiguracion = (ArrayList<ParametroConfiguracion>) parametroConfiguracionDAO.findByExample(example);
			sesionDeHilo.getTransaction().commit();
			return listaParametroConfiguracion;
		}catch(Exception ex){
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar " +example.getClass().getSimpleName().toUpperCase() +" que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<ParametroConfiguracion> List() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(ParametroConfiguracion object) throws Exception {
		try{
			setSession();
			setTransaction();
			parametroConfiguracionDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un error al agregar el parametro " +object.getNombre()+": " + ex.getMessage());
		}
		
	}

	@Override
	public void modify(ParametroConfiguracion object) throws Exception {
		try{
			setSession();
			setTransaction();
			parametroConfiguracionDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un error al modificar el parametro " +object.getNombre()+": " + ex.getMessage());
		}
		
	}

	@Override
	public void delete(ParametroConfiguracion object) throws Exception {
		try{
			setSession();
			setTransaction();
			parametroConfiguracionDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			throw new Exception("Ha ocurrido un error al eliminar el parametro " +object.getNombre()+": " + ex.getMessage());
		}
		
	}

	@Override
	public ParametroConfiguracion getById(Long id) throws Exception {
		ParametroConfiguracion parametroConfiguracion;
		try{ 
			parametroConfiguracion = parametroConfiguracionDAO.findById(id);
			return parametroConfiguracion;
		}catch(Exception ex){
			throw new Exception("Ha ocurrido un error al buscar el parametro con id" +id+": " + ex.getMessage());
		}
	}

}
