package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.MesaHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.academico.Mesa;
import ar.com.santalucia.excepciones.ValidacionException;

public class GestorMesa extends Gestor<Mesa> implements IListable<Mesa> {

	private MesaHome mesaDAO;
	
	public GestorMesa() throws Exception {
		super();
		try {
			mesaDAO = new MesaHome();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(Mesa object) throws Exception {
		try {
			setSession();
			setTransaction();
			mesaDAO.persist(object);
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
	public void modify(Mesa object) throws Exception {
		try {
			setSession();
			setTransaction();
			mesaDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public void delete(Mesa object) throws Exception {
		try {
			setSession();
			setTransaction();
			mesaDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public Mesa getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Mesa mesaDevolver = new Mesa();
			mesaDevolver = mesaDAO.findById(id);
			return mesaDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el objeto por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Mesa> getByExample(Mesa example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Mesa> listaMesasDevolver = (ArrayList<Mesa>) mesaDAO.findByExample((Mesa) example);
			sesionDeHilo.getTransaction().commit();
			return listaMesasDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Mesa> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Mesa criterioVacio = new Mesa();
			ArrayList<Mesa> listaMesasDevolver = new ArrayList<Mesa>();
			listaMesasDevolver = (ArrayList<Mesa>) mesaDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaMesasDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los áreas: " + ex.getMessage());
		}
	}

}
