package ar.com.santalucia.aplicacion.gestor;

import java.util.ArrayList;

public interface IListable<T> {
	
	public abstract ArrayList<T> getByExample(T example) throws Exception;

	/**
	 * Nota: Este m�todo intenta obtener una sesi�n y una transacci�n,
	 * manteni�ndolas abiertas.
	 */
	
	public abstract ArrayList<T> List() throws Exception;
}
