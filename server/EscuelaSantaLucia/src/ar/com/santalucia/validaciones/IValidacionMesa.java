package ar.com.santalucia.validaciones;

import ar.com.santalucia.dominio.modelo.academico.Mesa;

public interface IValidacionMesa extends IValidacion {
	
	/**
	 * Valida si la fecha de la mesa está definida en el intervalo del llamado
	 * @param mesa
	 * @param idLlamado
	 * @return <b>true</b> si la fecha es válida; <b>false</b> si no es válida
	 * @throws Exception 
	 */
	//Boolean fechaValida(Mesa mesa, Long idLlamado) throws Exception;
	
	/**
	 * Valida si la mesa tiene el tribunal de docentes completo (3 docentes)
	 * @param mesa
	 * @return <b>true</b> si ya están los 3 docentes; <b>false</b> si todavía hay lugar
	 */
	Boolean tribunalCompleto(Mesa mesa);

}
