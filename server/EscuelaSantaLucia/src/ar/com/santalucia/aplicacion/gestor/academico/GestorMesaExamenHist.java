package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.MesaExamenHistHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.academico.MesaExamenHist;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.validaciones.IValidacionMesaExamenHist;

/**
 * GestorMesaExamenHist: gestor para la entidad mesaExamen histórico
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class GestorMesaExamenHist extends Gestor<MesaExamenHist>
		implements IValidacionMesaExamenHist, IListable<MesaExamenHist> {

	private MesaExamenHistHome mesaExamenHistDAO;
	
	public GestorMesaExamenHist() throws Exception {
		super();
		try {
			mesaExamenHistDAO = new MesaExamenHistHome();
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
			mesaExamenHistDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar la MESAEXAMENHIST: " + ex.getMessage());
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
			MesaExamenHist mesaDevolver = new MesaExamenHist();
			mesaDevolver = mesaExamenHistDAO.findById(id);
			return mesaDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar la MESAEXAMENHIST por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<MesaExamenHist> getByExample(MesaExamenHist example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<MesaExamenHist> listaMesaExamenHistDevolver = (ArrayList<MesaExamenHist>) mesaExamenHistDAO.findByExample((MesaExamenHist) example);
			sesionDeHilo.getTransaction().commit();
			return listaMesaExamenHistDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar MESAEXAMENHIST que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<MesaExamenHist> List() throws Exception {
		try {
			setSession();
			setTransaction();
			MesaExamenHist criterioVacio = new MesaExamenHist();
			ArrayList<MesaExamenHist> listaMesaExamenHistDevolver = new ArrayList<MesaExamenHist>();
			listaMesaExamenHistDevolver = (ArrayList<MesaExamenHist>) mesaExamenHistDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaMesaExamenHistDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar las MESAS: " + ex.getMessage());
		}
	}

	@Override
	public void validar(Object object) throws Exception {
		// TODO Auto-generated method stub

	}

}
