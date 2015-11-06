package ar.com.santalucia.validaciones;


public interface IValidacionMateria extends IValidacion {
	
	/**
	 * Valida si la materia ya existe en la base de datos
	 * @param nombreMateria
	 * @return
	 */
	Boolean existeMateria(String nombreMateria);

}
