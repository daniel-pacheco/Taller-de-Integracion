package ar.com.santalucia.aplicacion.gestor;

import java.util.ArrayList;

import org.hibernate.Session;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;

/**
 * Clase abstracta gestor genérico
 * 
 * @author ericd
 * @version 1.0
 *
 * @param <T>
 */

// Último modificador: Ariel Ramirez @ 29-08-15 16:00

public abstract class Gestor<T> implements IGestor<T> {

	protected Session sesionDeHilo;

	public Gestor() throws Exception {
		setSession();
		setTransaction();
	}

	@Override
	public void setSession() throws Exception {
		try {
			if ((sesionDeHilo == null) || (!sesionDeHilo.isOpen())) {
				sesionDeHilo = HibernateUtil.getSessionFactory().getCurrentSession();
			}
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar la persistencia: " + ex.getMessage());
		}
	}

	@Override
	public void setTransaction() throws Exception {
		try {
			if (!sesionDeHilo.getTransaction().isActive()) {
				sesionDeHilo.beginTransaction();
			}
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar la transacción: " + ex.getMessage());
		}
	}

	@Override
	public void closeTransaction() throws Exception {
		try {
			if (sesionDeHilo.getTransaction().isActive()) {
				sesionDeHilo.getTransaction().commit();
			}
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al finalizar la transacción: " + ex.getMessage());
		}
	}

	
	@Override
	public abstract void add(T object) throws Exception;

	/**
	 * Nota: Este método realiza la modificación del elemento e intenta cerrar
	 * la transacción dado a que no siempre es parte de otra transacción mayor.
	 */
	@Override
	public abstract void modify(T object) throws Exception;

	/**
	 * Nota: Este método elimina el elemento e intenta cerrar la transacción
	 * dado a que no siempre es parte de otra transacción mayor.
	 */
	@Override
	public abstract void delete(T object) throws Exception;

	/**
	 * Nota: Este método intenta obtener una sesión y una transacción,
	 * manteniéndolas abiertas.
	 */
	@Override
	public abstract T getById(Long id) throws Exception;

	/**
	 * Nota: Este método intenta obtener una sesión y una transacción,
	 * manteniéndolas abiertas.
	 */
	@Override
	public abstract ArrayList<T> getByExample(T example) throws Exception;

	/**
	 * Nota: Este método intenta obtener una sesión y una transacción,
	 * manteniéndolas abiertas.
	 */
	@Override
	public abstract ArrayList<T> List() throws Exception;
}
