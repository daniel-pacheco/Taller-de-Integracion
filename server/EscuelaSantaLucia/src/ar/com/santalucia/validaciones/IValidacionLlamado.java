package ar.com.santalucia.validaciones;

import ar.com.santalucia.dominio.modelo.academico.Llamado;
import ar.com.santalucia.dominio.modelo.academico.Mesa;

public interface IValidacionLlamado extends IValidacion{
	
	/**
	 * Valida si existe el llamado en la base de datos
	 * @param llamado
	 * @return <b>true</b> si el llamado ya existe; <b>false</b> si no existe.
	 * @throws Exception
	 */
	Boolean existeLlamado(Llamado llamado) throws Exception;
	
	/**
	 * Valida si una mesa dada existe en un llamado
	 * @param llamado
	 * @return <b>true</b> si la mesa ya existe en el llamado; <b>false</b> si no existe.
	 * @throws Exception
	 */
	Boolean existeMesaEnLlamado(Llamado llamado) throws Exception;

}
