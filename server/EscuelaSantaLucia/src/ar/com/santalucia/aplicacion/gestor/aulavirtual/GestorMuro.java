package ar.com.santalucia.aplicacion.gestor.aulavirtual;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.aulavirtual.MuroHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.aulavirtual.Muro;

/**
 * 
 * @author Ariel Ramirez
 *
 * @version 1.0
 *
 */

public class GestorMuro extends Gestor<Muro> {

	private MuroHome muroDAO;
	
	public GestorMuro() throws Exception {
		super();
		muroDAO = new MuroHome();
	}

	@Override
	public void add(Muro object) throws Exception {
		try{
			setSession();
			setTransaction();
			muroDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el MURO: " + ex.getMessage());
		}
	}

	@Override
	public void modify(Muro object) throws Exception {
		try{
			setSession();
			setTransaction();
			muroDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el MURO: " + ex.getMessage());
		}
		
	}

	@Override
	public void delete(Muro object) throws Exception {
		try {
			setSession();
			setTransaction();
			muroDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el MURO: " + ex.getMessage());
		}
		
	}

	@Override
	public Muro getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Muro muroDevolver = new Muro();
			muroDevolver = muroDAO.findById(id);
			return muroDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el MURO por su ID: " + ex.getMessage());
		}
	}
	
	public ArrayList<Muro> getByExample(Muro example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Muro> listaMuroDevolver = (ArrayList<Muro>) muroDAO.findByExample((Muro) example);
			sesionDeHilo.getTransaction().commit();
			return listaMuroDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar MUROS que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

}
