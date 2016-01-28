package ar.com.santalucia.aplicacion.gestor.desempenio;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.desempenio.NotaHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.desempenio.Nota;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.validaciones.IValidacionNota;

public class GestorNota extends Gestor<Nota> implements IListable<Nota>, IValidacionNota {

	private NotaHome notaDAO;

	public GestorNota() throws Exception {
		super();
		try {
			notaDAO = new NotaHome();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(Nota object) throws Exception {
		try {
			this.validar(object);
			setSession();
			setTransaction();
			notaDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar la NOTA: " + ex.getMessage());
		}
	}

	@Override
	public void modify(Nota object) throws Exception {
		try {
			this.validar(object);
			setSession();
			setTransaction();
			notaDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar la NOTA: " + ex.getMessage());
		}
	}

	@Override
	public void delete(Nota object) throws Exception {
		try {
			setSession();
			setTransaction();
			notaDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar la NOTA: " + ex.getMessage());
		}
	}

	@Override
	public Nota getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Nota notaDevolver = new Nota();
			notaDevolver = notaDAO.findById(id);
			return notaDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar la NOTA por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Nota> getByExample(Nota example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Nota> listaNotasDevolver = (ArrayList<Nota>) notaDAO.findByExample((Nota) example);
			sesionDeHilo.getTransaction().commit();
			return listaNotasDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar NOTAS que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Nota> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Nota criterioVacio = new Nota();
			ArrayList<Nota> listaNotasDevolver = new ArrayList<Nota>();
			listaNotasDevolver = (ArrayList<Nota>) notaDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaNotasDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar las NOTAS: " + ex.getMessage());
		}
	}

	@Override
	public void validar(Object object) throws Exception {
		// TODO Auto-generated method stub

	}

}
