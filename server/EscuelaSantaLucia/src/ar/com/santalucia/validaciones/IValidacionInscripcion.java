package ar.com.santalucia.validaciones;

import ar.com.santalucia.dominio.modelo.academico.Inscripcion;

public interface IValidacionInscripcion extends IValidacion {

	/**
	 * Valida si la inscripcion se produjo en el plazo definido por la mesa
	 * @param inscripcion
	 * @return <b>true</b> si la fecha es anterior o igual a la fecha de la mesa menos los dias definidos; 
	 * 			<b>false</b> si la fecha es menor a la fecha de la mesa menos los días definidos 
	 */
	Boolean validarFecha(Inscripcion inscripcion);
	
	/**
	 * Valida si el numero de acta existe en la base de datos
	 * @param nroActa
	 * @return
	 * 		<b>true</b> si el acta existe en la bd
	 * 		<b>false</b> si el acta no existe en la bd
	 */
	Boolean existeNroActa(String nroActa);
	
}
