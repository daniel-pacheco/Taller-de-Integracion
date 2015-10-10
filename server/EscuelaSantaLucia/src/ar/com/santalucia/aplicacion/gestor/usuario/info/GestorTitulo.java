package ar.com.santalucia.aplicacion.gestor.usuario.info;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.usuario.info.TituloHome;
import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;

/**
 * Clase gestor de Titulos
 * 
 * @author Ariel Ramírez ???
 * 
 * @version 1.0
 */

//UltimoModificador: Ariel Ramirez @ 29-08-15 16:00

public class GestorTitulo extends Gestor<Titulo> {
	private TituloHome tituloDAO;

	public GestorTitulo() throws Exception {
		try {
			sesionDeHilo = HibernateUtil.getSessionFactory().getCurrentSession();
			tituloDAO = new TituloHome();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar la persistencia: " + ex.getMessage());
		}
	}

	/**
	 * Nota: Domicilio. Este método no hace efectiva la persistencia (commit) ya
	 * que debe ser parte de una transacción mayor por ser parte de una
	 * composición. Use closeTransaction() para hacer efectiva la transacción o
	 * sesionDeHilo.getTransaction().commit().
	 */
	@Override
	public void add(Titulo object) throws Exception {
		try {
			setSession();
			setTransaction();
			tituloDAO.persist(object);
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al agregar el objeto: " + ex.getMessage());
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
			throw new Exception("Ha ocurrido un error al modificar el objeto: " + ex.getMessage());
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
			throw new Exception("Ha ocurrido un error al eliminar el objeto: " + ex.getMessage());
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
			throw new Exception("Ha ocurrido un error al buscar el objeto por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Titulo> getByExample(Titulo example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Titulo> listaTituloDevolver = (ArrayList<Titulo>) tituloDAO.findByExample((Titulo) example);
			return listaTituloDevolver;
		} catch (Exception ex) {
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());

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
			return listaTituloDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los alumnos: " + ex.getMessage());

		}
	}

	@Override
	public void validar(Titulo object) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
