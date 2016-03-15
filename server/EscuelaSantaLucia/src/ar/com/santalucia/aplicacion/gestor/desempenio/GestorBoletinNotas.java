package ar.com.santalucia.aplicacion.gestor.desempenio;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.desempenio.BoletinNotasHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotas;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotasHist;
import ar.com.santalucia.dominio.modelo.desempenio.Nota;
import ar.com.santalucia.dominio.modelo.desempenio.Trimestre;

public class GestorBoletinNotas extends Gestor<BoletinNotas> implements IListable<BoletinNotas> {

	private BoletinNotasHome boletinNotasDAO;
	private GestorTrimestre GTrimestre;
	private GestorNota GNota;
	
	public GestorBoletinNotas() throws Exception {
		super();
		try {
			boletinNotasDAO = new BoletinNotasHome();
			GTrimestre = new GestorTrimestre();
			GNota = new GestorNota();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(BoletinNotas object) throws Exception {
		try {
			setSession();
			setTransaction();
			if (object.getListaTrimestres() != null) {
				for (Trimestre t : object.getListaTrimestres()) {
					GTrimestre.add(t);
				}
			}
			if (object.getListaNotasExamen() != null) {
				for (Nota n : object.getListaNotasExamen()) {
					GNota.add(n);
				}
			}
			boletinNotasDAO.persist(object);
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el BOLETÍN DE NOTAS: " + ex.getMessage());
		}
	}

	@Override
	public void modify(BoletinNotas object) throws Exception {
		try {
			setSession();
			setTransaction();
			boletinNotasDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el BOLETÍN DE NOTAS: " + ex.getMessage());
		}
	}

	@Override
	public void delete(BoletinNotas object) throws Exception {
		try {
			setSession();
			setTransaction();
			boletinNotasDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el BOLETÍN DE NOTAS: " + ex.getMessage());
		}
	}

	@Override
	public BoletinNotas getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction(); 
			BoletinNotas boletinDevolver = new BoletinNotas();
			boletinDevolver = boletinNotasDAO.findById(id);
			return boletinDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el BOLETÍN por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<BoletinNotas> getByExample(BoletinNotas example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<BoletinNotas> listaBoletinDevolver = (ArrayList<BoletinNotas>) boletinNotasDAO.findByExample((BoletinNotas) example);
			sesionDeHilo.getTransaction().commit();
			return listaBoletinDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar BOLETINES que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<BoletinNotas> List() throws Exception {
		try {
			setSession();
			setTransaction();
			BoletinNotas criterioVacio = new BoletinNotas();
			ArrayList<BoletinNotas> listaBoletinDevolver = new ArrayList<BoletinNotas>();
			listaBoletinDevolver = (ArrayList<BoletinNotas>) boletinNotasDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaBoletinDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los BOLETINES: " + ex.getMessage());
		}
	}

}
