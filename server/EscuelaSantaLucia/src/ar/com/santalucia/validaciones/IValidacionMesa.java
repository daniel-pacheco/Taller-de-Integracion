package ar.com.santalucia.validaciones;

import ar.com.santalucia.dominio.modelo.academico.Mesa;

public interface IValidacionMesa extends IValidacion {
	
	/**
	 * Valida si la fecha de la mesa est� definida en el intervalo del llamado
	 * @param mesa
	 * @param idLlamado
	 * @return <b>true</b> si la fecha es v�lida; <b>false</b> si no es v�lida
	 * @throws Exception 
	 */
	//Boolean fechaValida(Mesa mesa, Long idLlamado) throws Exception;
	
	/**
	 * Valida si la mesa tiene el tribunal de docentes completo (3 docentes)
	 * @param mesa
	 * @return <b>true</b> si ya est�n los 3 docentes; <b>false</b> si todav�a hay lugar
	 */
	Boolean tribunalCompleto(Mesa mesa);

}
