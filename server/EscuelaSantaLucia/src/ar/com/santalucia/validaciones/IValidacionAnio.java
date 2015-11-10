package ar.com.santalucia.validaciones;

import ar.com.santalucia.dominio.modelo.academico.Anio;

/**
 * @author ericpennachini
 * @version 1.0
 *
 */

public interface IValidacionAnio {
	
	/**
	 * Valida si el nombre del año ya existe en la base de datos
	 * @param nombreAnio
	 * @return
	 * @throws Exception 
	 */
	Boolean existeNombreAnio(Anio anio) throws Exception;
	
	/**
	 * Valida si el curso existe en el anio
	 * @param divisionCurso
	 * @param anio
	 * @return
	 * @throws Exception 
	 */
	Boolean existeCurso(Character divisionCurso, Anio anio) throws Exception;
	
	
	/**
	 * Valida si la materia ya existe en la base de datos
	 * @param nombreMateria
	 * @return
	 * @throws Exception 
	 */
	Boolean existeMateria(String nombreMateria) throws Exception;
	
	
}
