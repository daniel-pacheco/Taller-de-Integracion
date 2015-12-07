package ar.com.santalucia.aplicacion.gestor;

import org.hibernate.Session;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;

/**
 * Clase abstracta gestor gen�rico
 * 
 * @author ericd
 * @version 1.0
 *
 * @param <T>
 */

// �ltimo modificador: Ariel Ramirez @ 26-09-2015 12:56

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
			sesionDeHilo.close();
			throw new Exception("Ha ocurrido un problema al inicializar la transacci�n: " + ex.getMessage());
		}
	}

	//Casos posibles: Sesion abierta, transaccion abierta. Sesion abierta, transaccion cerrada
	@Override
	public void closeSession() throws Exception {
		try {	
			if(sesionDeHilo.isOpen()){
				if (sesionDeHilo.getTransaction().isActive()) {
						sesionDeHilo.getTransaction().commit();
					}else{
						sesionDeHilo.close();
					}
				}
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al finalizar la transacci�n: " + ex.getMessage());
		}
	}

	
	@Override
	public abstract void add(T object) throws Exception;

	/**
	 * Nota: Este m�todo realiza la modificaci�n del elemento e intenta cerrar
	 * la transacci�n dado a que no siempre es parte de otra transacci�n mayor.
	 */
	@Override
	public abstract void modify(T object) throws Exception;

	/**
	 * Nota: Este m�todo elimina el elemento e intenta cerrar la transacci�n
	 * dado a que no siempre es parte de otra transacci�n mayor.
	 */
	@Override
	public abstract void delete(T object) throws Exception;

	/**
	 * Nota: Este m�todo intenta obtener una sesi�n y una transacci�n,
	 * manteni�ndolas abiertas.
	 */
	@Override
	public abstract T getById(Long id) throws Exception;
	
}
