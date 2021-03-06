package ar.com.santalucia.validaciones;

import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Materia;

/**
 * @author ericpennachini
 * @version 1.1
 *
 */

//UltimoModificador: Ariel Ramirez @ 09-12-2015 19:38

public interface IValidacionAnio extends IValidacion{
	
	/**
	 * Valida si el nombre del a�o ya existe en la base de datos
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
	Boolean existeCurso(Anio anio) throws Exception;	
	
}
