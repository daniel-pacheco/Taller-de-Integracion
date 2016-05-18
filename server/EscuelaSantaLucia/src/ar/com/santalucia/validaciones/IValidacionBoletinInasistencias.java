package ar.com.santalucia.validaciones;

import ar.com.santalucia.dominio.modelo.desempenio.Inasistencia;

public interface IValidacionBoletinInasistencias extends IValidacion {
	
	/**
	 * Valida si hay dos inasistencias con el mismo concepto en la misma fecha.
	 * @param inasistencia
	 * @return 
	 * 	<b>true</b> si ya existe una fecha del mismo concepto y fecha; 
	 * 	<b>false</b> si no existe, en este caso se permite el alta. 
	 * @throws Exception
	 */
	Boolean existeConceptoEnFecha(Inasistencia inasistencia) throws Exception;
	
}
