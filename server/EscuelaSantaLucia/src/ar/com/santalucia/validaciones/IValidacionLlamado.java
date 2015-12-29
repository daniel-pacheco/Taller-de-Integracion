package ar.com.santalucia.validaciones;

import ar.com.santalucia.dominio.modelo.academico.Llamado;
import ar.com.santalucia.dominio.modelo.academico.Mesa;

public interface IValidacionLlamado extends IValidacion{
	
	/**
	 * Valida si existe el llamado en la base de datos
	 * @param llamado
	 * @return
	 * @throws Exception
	 */
	Boolean existeLlamado(Llamado llamado) throws Exception;
	
	/**
	 * Valida si una mesa dada existe en un llamado
	 * @param mesa
	 * @param llamado
	 * @return
	 * @throws Exception
	 */
	Boolean existeMesaEnLlamado(Mesa mesa, Llamado llamado) throws Exception;

}
