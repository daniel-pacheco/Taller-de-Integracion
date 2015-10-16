package ar.com.santalucia.validaciones;

public interface IValidacionMateria extends IValidacion {
	
	/**
	 * Valida que el tipo de docente sea "Titular" o "Suplente"
	 * @param tipoDoc
	 * @return TRUE si el nombre es válido, FALSE si no es válido
	 */
	Boolean validarTipoDocente(String tipoDoc);
	
	/**
	 * Valida si la materia ya existe en la base de datos
	 * @param nombreMateria
	 * @return
	 */
	Boolean existeMateria(String nombreMateria);

}
