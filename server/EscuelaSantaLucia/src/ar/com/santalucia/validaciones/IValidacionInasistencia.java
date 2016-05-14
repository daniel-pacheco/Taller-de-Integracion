package ar.com.santalucia.validaciones;

import ar.com.santalucia.dominio.modelo.desempenio.Inasistencia;

public interface IValidacionInasistencia extends IValidacion {
	
	Boolean existeConceptoEnFecha(Inasistencia inasistencia) throws Exception;
}
