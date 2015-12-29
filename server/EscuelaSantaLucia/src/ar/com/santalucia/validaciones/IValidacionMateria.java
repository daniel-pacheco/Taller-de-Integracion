package ar.com.santalucia.validaciones;

import ar.com.santalucia.dominio.modelo.academico.Materia;

public interface IValidacionMateria extends IValidacion {
	
	/**
	 * Valida si la materia ya existe en la base de datos
	 * @param nombreMateria
	 * @return
	 */
	Boolean existeMateria (Materia materia) throws Exception;

}
