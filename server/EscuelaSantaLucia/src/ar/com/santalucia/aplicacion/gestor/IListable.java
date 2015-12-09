package ar.com.santalucia.aplicacion.gestor;

import java.util.ArrayList;

public interface IListable<T> {
	
	public abstract ArrayList<T> getByExample(T example) throws Exception;
	public abstract ArrayList<T> List() throws Exception;

}
