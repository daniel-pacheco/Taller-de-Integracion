package ar.com.santalucia.aplicacion.gestor.usuario.info;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.usuario.info.TituloHome;
import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;

/**
 * Clase gestor de Titulos
 * 
 * @author Ariel Ram�rez ???
 * 
 * @version 1.0
 */

//UltimoModificador: Ariel Ramirez @ 29-08-15 16:00

public class GestorTitulo extends Gestor<Titulo> implements IListable<Titulo>{
	private TituloHome tituloDAO;

	public GestorTitulo() throws Exception {
		try {
			tituloDAO = new TituloHome();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	/**
	 * Nota: Domicilio. Este m�todo no hace efectiva la persistencia (commit) ya
	 * que debe ser parte de una transacci�n mayor por ser parte de una
	 * composici�n. Use closeTransaction() para hacer efectiva la transacci�n o
	 * sesionDeHilo.getTransaction().commit().
	 */
	@Override
	public void add(Titulo object) throws Exception {
		try {
			setSession();
			setTransaction();
			tituloDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al agregar el T�TULO: " + ex.getMessage());
		}
	}

	@Override
	public void modify(Titulo object) throws Exception {
		try {
			setSession();
			setTransaction();
			tituloDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al modificar el T�TULO: " + ex.getMessage());
		}
	}

	@Override
	public void delete(Titulo object) throws Exception {
		try {
			setSession();
			setTransaction();
			tituloDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al eliminar el T�TULO: " + ex.getMessage());
		}

	}

	@Override
	public Titulo getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Titulo tituloDevolver = new Titulo();
			tituloDevolver = tituloDAO.findById(id);
			return tituloDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al buscar el T�TULO por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Titulo> getByExample(Titulo object) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Titulo> listaTituloDevolver = (ArrayList<Titulo>) tituloDAO.findByExample(object);
			sesionDeHilo.getTransaction().commit();
			return listaTituloDevolver;
		} catch (Exception ex) {
			throw new Exception(
					"Ha ocurrido un error al buscar T�TULOS que coincidan con el ejemplo dado: " + ex.getMessage());

		}
	}

	@Override
	public ArrayList<Titulo> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Titulo criterioVacio = new Titulo();
			ArrayList<Titulo> listaTituloDevolver = new ArrayList<Titulo>();
			listaTituloDevolver = (ArrayList<Titulo>) tituloDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaTituloDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los T�TULOS: " + ex.getMessage());

		}
	}

}
