package ar.com.santalucia.aplicacion.gestor.aulavirtual;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.aulavirtual.EntregaHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.dominio.modelo.aulavirtual.Entrega;
/**
 * 
 * @author Ariel Ramirez
 *
 * @version 1.0
 *
 */
public class GestorEntrega extends Gestor<Entrega> {

	private EntregaHome entregaDAO;
	
	public GestorEntrega() throws Exception {
		super();
		entregaDAO = new EntregaHome();
	}

	@Override
	public void add(Entrega object) throws Exception {
		try{
			setSession();
			setTransaction();
			entregaDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar la ENTREGA: " + ex.getMessage());
		}
	}

	@Override
	public void modify(Entrega object) throws Exception {
		try{
			setSession();
			setTransaction();
			entregaDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar la ENTREGA: " + ex.getMessage());
		}
		
	}

	@Override
	public void delete(Entrega object) throws Exception {
		try {
			setSession();
			setTransaction();
			entregaDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar la ENTREGA: " + ex.getMessage());
		}
		
	}

	@Override
	public Entrega getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Entrega entregaDevolver = new Entrega();
			entregaDevolver = entregaDAO.findById(id);
			return entregaDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar la ENTREGA por su ID: " + ex.getMessage());
		}
	}
	
	public ArrayList<Entrega> getByExample(Entrega example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Entrega> listaEntregaDevolver = (ArrayList<Entrega>) entregaDAO.findByExample((Entrega) example);
			sesionDeHilo.getTransaction().commit();
			return listaEntregaDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar ENTREGAS que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

}
