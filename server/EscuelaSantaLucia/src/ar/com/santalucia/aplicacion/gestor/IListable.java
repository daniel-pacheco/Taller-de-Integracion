package ar.com.santalucia.aplicacion.gestor;

import java.util.ArrayList;

public interface IListable<T> {
	
	public abstract ArrayList<T> getByExample(T example) throws Exception;

	/**
	 * Nota: Este método intenta obtener una sesión y una transacción,
	 * manteniéndolas abiertas.
	 */
	
	public abstract ArrayList<T> List() throws Exception;
}
