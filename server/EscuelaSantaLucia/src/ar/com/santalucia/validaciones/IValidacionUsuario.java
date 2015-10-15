package ar.com.santalucia.validaciones;

import java.util.Set;

import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;

/**
 * Interfaz genérica para validaciones (para clases del modelo)
 * @author ericpennachini
 * @version 1.0
 * 
 * 
 */

//TODO: borrar throws

public interface IValidacionUsuario extends IValidacion {
	
	/**
	 * Valida si el documento ya existe en la base de datos
	 * @param tipo
	 * @param numero
	 * @return
	 */
	Boolean existeDocumento(String tipo, Long numero);
	
	/**
	 * Valida si el mail ya existe en la base de datos
	 * @param mail
	 * @return
	 */
	Boolean existeMail(Mail mail);
	
	/**
	 * Valida si el nombre de usuario ya existe en la base de datos
	 * @param nombreUsuario
	 * @return
	 */
	Boolean existeNombreUsuario(String nombreUsuario);
	
}
