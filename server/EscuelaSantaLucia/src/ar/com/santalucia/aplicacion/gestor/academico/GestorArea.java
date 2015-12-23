package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.AreaHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.academico.Area;

public class GestorArea extends Gestor<Area> implements IListable<Area> {
	private AreaHome areaDAO;

	public GestorArea() throws Exception {
		super();
		try {
			areaDAO = new AreaHome();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(Area object) throws Exception {
		try {
			setSession();
			setTransaction();
			areaDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public void modify(Area object) throws Exception {
		try {
			setSession();
			setTransaction();
			areaDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el objeto: " + ex.getMessage());
		}		
	}

	@Override
	public void delete(Area object) throws Exception {
		try {
			setSession();
			setTransaction();
			areaDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public Area getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Area areaDevolver = new Area();
			areaDevolver = areaDAO.findById(id);
			return areaDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el objeto por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Area> getByExample(Area example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Area> listaAreasDevolver = (ArrayList<Area>) areaDAO.findByExample(example);
			sesionDeHilo.getTransaction().commit();
			return listaAreasDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Area> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Area criterioVacio = new Area();
			ArrayList<Area> listaAreasDevolver = new ArrayList<Area>();
			listaAreasDevolver = (ArrayList<Area>) areaDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaAreasDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar las áreas: " + ex.getMessage());
		}
	}

}
