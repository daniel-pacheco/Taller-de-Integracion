package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.MesaExamenHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.academico.MesaExamenHist;
import ar.com.santalucia.validaciones.IValidacionMesaExamen;

/**
 * Clase gestor de MesaExamen (registra la "rendida")
 * 
 * @author Eric
 * @version 1.0
 *
 */

public class GestorMesaExamen extends Gestor<MesaExamenHist> implements IValidacionMesaExamen, IListable<MesaExamenHist> {

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
	public void add(MesaExamenHist object) throws Exception {
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
	public void modify(MesaExamenHist object) throws Exception {}

	@Override
	public void delete(MesaExamenHist object) throws Exception {}

	@Override
	public MesaExamenHist getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			MesaExamenHist mesaExamenDevolver = new MesaExamenHist();
			mesaExamenDevolver = mesaExamenDAO.findById(id);
			return mesaExamenDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar la MESA-EXAMEN por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<MesaExamenHist> getByExample(MesaExamenHist example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<MesaExamenHist> listaMesaExamenDevolver = (ArrayList<MesaExamenHist>) mesaExamenDAO.findByExample(example);
			sesionDeHilo.getTransaction().commit();
			return listaMesaExamenDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar objetos MESA-EXAMEN que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<MesaExamenHist> List() throws Exception {
		try {
			setSession();
			setTransaction();
			MesaExamenHist criterioVacio = new MesaExamenHist();
			ArrayList<MesaExamenHist> listaMesaExamenDevolver = new ArrayList<MesaExamenHist>();
			listaMesaExamenDevolver = (ArrayList<MesaExamenHist>) mesaExamenDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaMesaExamenDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los objetos MESA-EXAMEN: " + ex.getMessage());
		}
	}

	@Override
	public void validar(Object object) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
