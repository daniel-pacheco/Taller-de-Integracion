package ar.com.santalucia.aplicacion.gestor;

/**
 * Interface para los gestores
 * 
 * @author Ariel Ramirez
 *
 * @version 2.0
 */

//UltimoModificador: Eric Pennachini @ 07-08-15 18:02

public interface IGestor<T> {
	void setSession() throws Exception;
	void setTransaction() throws Exception;
	void add(T object) throws Exception;
	void modify(T object) throws Exception;
	void delete(T object) throws Exception;
	T getById(Long id) throws Exception;
	void closeSession() throws Exception;
}
