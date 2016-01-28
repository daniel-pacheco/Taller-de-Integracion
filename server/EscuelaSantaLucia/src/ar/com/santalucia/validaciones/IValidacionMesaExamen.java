package ar.com.santalucia.validaciones;

public interface IValidacionMesaExamen extends IValidacion {

	/**
	 * Valida si el numero de acta existe en la base de datos
	 * @param nroActa
	 * @return
	 * 		<b>true</b> si el acta existe en la bd
	 * 		<b>false</b> si el acta no existe en la bd
	 */
	Boolean existeNroActa(String nroActa);
	
}
