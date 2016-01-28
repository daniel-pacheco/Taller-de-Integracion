package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.MesaExamenHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.academico.MesaExamen;
import ar.com.santalucia.validaciones.IValidacionMesaExamen;

/**
 * Clase gestor de MesaExamen (registra la "rendida")
 * 
 * @author Eric
 * @version 1.0
 *
 */

public class GestorMesaExamen extends Gestor<MesaExamen> implements IValidacionMesaExamen, IListable<MesaExamen> {

	private MesaExamenHome mesaExamenDAO;
	
	public GestorMesaExamen() throws Exception {
		super();
		try {
			mesaExamenDAO = new MesaExamenHome();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(MesaExamen object) throws Exception {
		try {
			this.validar(object);
			setSession();
			setTransaction();
			mesaExamenDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar la MESA-EXAMEN: " + ex.getMessage());
		}
	}

	@Override
	public void modify(MesaExamen object) throws Exception {
		try {
			this.validar(object);
			setSession();
			setTransaction();
			mesaExamenDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar la MESA-EXAMEN: " + ex.getMessage());
		}
	}

	@Override
	public void delete(MesaExamen object) throws Exception {
		try {
			setSession();
			setTransaction();
			mesaExamenDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar la MESA-EXAMEN: " + ex.getMessage());
		}
	}

	@Override
	public MesaExamen getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			MesaExamen mesaExamenDevolver = new MesaExamen();
			mesaExamenDevolver = mesaExamenDAO.findById(id);
			return mesaExamenDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar la MESA-EXAMEN por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<MesaExamen> getByExample(MesaExamen example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<MesaExamen> listaMesaExamenDevolver = (ArrayList<MesaExamen>) mesaExamenDAO.findByExample(example);
			sesionDeHilo.getTransaction().commit();
			return listaMesaExamenDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar objetos MESA-EXAMEN que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<MesaExamen> List() throws Exception {
		try {
			setSession();
			setTransaction();
			MesaExamen criterioVacio = new MesaExamen();
			ArrayList<MesaExamen> listaMesaExamenDevolver = new ArrayList<MesaExamen>();
			listaMesaExamenDevolver = (ArrayList<MesaExamen>) mesaExamenDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaMesaExamenDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los objetos MESA-EXAMEN: " + ex.getMessage());
		}
	}

	@Override
	public Boolean existeNroActa(String nroActa) {
		
		return null;
	}

	@Override
	public void validar(Object object) throws Exception {
		// TODO Auto-generated method stub

	}

}
