package ar.com.santalucia.aplicacion.gestor.desempenio;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.desempenio.TrimestreHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.desempenio.Nota;
import ar.com.santalucia.dominio.modelo.desempenio.Trimestre;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.validaciones.IValidacionTrimestre;

public class GestorTrimestre extends Gestor<Trimestre> implements IListable<Trimestre>, IValidacionTrimestre {
	
	private TrimestreHome trimestreDAO;
	private GestorNota GNota;

	public GestorTrimestre() throws Exception {
		super();
		try {
			trimestreDAO = new TrimestreHome();
			GNota = new GestorNota();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(Trimestre object) throws Exception {
		try {
			this.validar(object);
			setSession();
			setTransaction();
			if (object.getListaNotas() != null) {
				for (Nota n : object.getListaNotas()) {
					GNota.add(n);
				}
			}
			trimestreDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el TRIMESTRE: " + ex.getMessage());
		}
	}

	@Override
	public void modify(Trimestre object) throws Exception {
		try {
			this.validar(object);
			setSession();
			setTransaction();
			trimestreDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el TRIMESTRE: " + ex.getMessage());
		}
	}

	@Override
	public void delete(Trimestre object) throws Exception {
		try {
			setSession();
			setTransaction();
			trimestreDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el TRIMESTRE: " + ex.getMessage());
		}
	}

	@Override
	public Trimestre getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Trimestre trimestreDevolver = new Trimestre();
			trimestreDevolver = trimestreDAO.findById(id);
			return trimestreDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el TRIMESTRE por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Trimestre> getByExample(Trimestre example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Trimestre> listaTrimestresDevolver = (ArrayList<Trimestre>) trimestreDAO.findByExample((Trimestre) example);
			sesionDeHilo.getTransaction().commit();
			return listaTrimestresDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar TRIMESTRES que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Trimestre> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Trimestre criterioVacio = new Trimestre();
			ArrayList<Trimestre> listaTrimestresDevolver = new ArrayList<Trimestre>();
			listaTrimestresDevolver = (ArrayList<Trimestre>) trimestreDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaTrimestresDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los TRIMESTRES: " + ex.getMessage());
		}
	}

	@Override
	public void validar(Object object) throws Exception {
		// TODO Auto-generated method stub

	}

}
