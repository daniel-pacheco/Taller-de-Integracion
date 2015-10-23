package ar.com.santalucia.validaciones;

public interface IValidacionMateria extends IValidacion {
	
	/**
	 * Valida que el tipo de docente sea "Titular" o "Suplente"
	 * @param tipoDoc
	 * @return TRUE si el nombre es v�lido, FALSE si no es v�lido
	 */
	Boolean validarTipoDocente(String tipoDoc);
	
	/**
	 * Valida si la materia ya existe en la base de datos
	 * @param nombreMateria
	 * @return
	 */
	Boolean existeMateria(String nombreMateria);

}
