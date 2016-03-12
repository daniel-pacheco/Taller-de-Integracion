package ar.com.santalucia.aplicacion.gestor.desempenio;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.desempenio.BoletinNotasHistHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotasHist;
import ar.com.santalucia.dominio.modelo.desempenio.MateriaNotasBoletin;

public class GestorBoletinNotasHist extends Gestor<BoletinNotasHist> implements IListable<BoletinNotasHist> {

	private BoletinNotasHistHome boletinNotasHistDAO;
	private GestorMateriaNotasBoletin GMateriaNotaBoletin;
	
	public GestorBoletinNotasHist() throws Exception {
		super();
		try {
			boletinNotasHistDAO = new BoletinNotasHistHome();
			GMateriaNotaBoletin = new GestorMateriaNotasBoletin();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(BoletinNotasHist object) throws Exception {
		try {
			setSession();
			setTransaction();
			if (object.getListaMateriasNotasBoletin() != null) {
				for (MateriaNotasBoletin mnb : object.getListaMateriasNotasBoletin()) {
					GMateriaNotaBoletin.add(mnb);
				}
			}
			boletinNotasHistDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el BOLETÍN DE NOTAS histórico: " + ex.getMessage());
		}
	}

	@Override
	public void modify(BoletinNotasHist object) throws Exception {
		try {
			setSession();
			setTransaction();
			boletinNotasHistDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el BOLETÍN DE NOTAS: " + ex.getMessage());
		}
	}

	/**
	 * NO SE IMPLEMENTA	
	 */
	@Override
	public void delete(BoletinNotasHist object) throws Exception {}

	@Override
	public BoletinNotasHist getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction(); 
			BoletinNotasHist boletinDevolver = new BoletinNotasHist();
			boletinDevolver = boletinNotasHistDAO.findById(id);
			return boletinDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el BOLETÍN por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<BoletinNotasHist> getByExample(BoletinNotasHist example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<BoletinNotasHist> listaBoletinDevolver = (ArrayList<BoletinNotasHist>) boletinNotasHistDAO.findByExample((BoletinNotasHist) example);
			sesionDeHilo.getTransaction().commit();
			return listaBoletinDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar BOLETINES que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<BoletinNotasHist> List() throws Exception {
		try {
			setSession();
			setTransaction();
			BoletinNotasHist criterioVacio = new BoletinNotasHist();
			ArrayList<BoletinNotasHist> listaBoletinDevolver = new ArrayList<BoletinNotasHist>();
			listaBoletinDevolver = (ArrayList<BoletinNotasHist>) boletinNotasHistDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaBoletinDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los BOLETINES: " + ex.getMessage());
		}
	}

}
