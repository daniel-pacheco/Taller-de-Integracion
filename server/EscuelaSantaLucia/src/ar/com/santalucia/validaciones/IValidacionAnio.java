package ar.com.santalucia.validaciones;

/**
 * @author ericpennachini
 * @version 1.0
 *
 */

public interface IValidacionAnio {
	
	/**
	 * Valida si el nombre del a�o ya existe en la base de datos
	 * @param nombreAnio
	 * @return
	 */
	Boolean existeNombreAnio(String nombreAnio);
	
	Boolean existeCurso(String nombreCurso);
	
	Boolean existeMateria(String nombreMateria);
	
}
