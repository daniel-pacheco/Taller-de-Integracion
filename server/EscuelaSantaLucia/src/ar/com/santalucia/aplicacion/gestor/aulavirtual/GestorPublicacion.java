package ar.com.santalucia.aplicacion.gestor.aulavirtual;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.aulavirtual.PublicacionHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.dominio.modelo.aulavirtual.Publicacion;

public class GestorPublicacion extends Gestor<Publicacion> {
	
	private PublicacionHome publicacionDAO;
	
	public GestorPublicacion() throws Exception {
		super();
		publicacionDAO = new PublicacionHome();
	}

	@Override
	public void add(Publicacion object) throws Exception {
		try{
			setSession();
			setTransaction();
			publicacionDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar la PUBLICACION: " + ex.getMessage());
		}
	}

	@Override
	public void modify(Publicacion object) throws Exception {
		try{
			setSession();
			setTransaction();
			publicacionDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar la PUBLICACION: " + ex.getMessage());
		}
		
	}

	@Override
	public void delete(Publicacion object) throws Exception {
		try {
			setSession();
			setTransaction();
			publicacionDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar la PUBLICACION: " + ex.getMessage());
		}
		
	}

	@Override
	public Publicacion getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Publicacion publicacionDevolver = new Publicacion();
			publicacionDevolver = publicacionDAO.findById(id);
			return publicacionDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar la PUBLICACION por su ID: " + ex.getMessage());
		}
	}
	
	public ArrayList<Publicacion> getByExample(Publicacion example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Publicacion> listaPublicacionDevolver = (ArrayList<Publicacion>) publicacionDAO.findByExample((Publicacion) example);
			sesionDeHilo.getTransaction().commit();
			return listaPublicacionDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar PUBLICACIONES que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}
}
