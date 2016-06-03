package ar.com.santalucia.validaciones;

import java.util.Set;

import ar.com.santalucia.dominio.modelo.usuarios.Usuario;
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
	 * @throws Exception 
	 */
	Boolean existeDocumento(Usuario usuario) throws Exception;
	
	/**
	 * Valida si el mail ya existe en la base de datos
	 * @param mail
	 * @return
	 * @throws Exception 
	 */
	Boolean existeMail(Mail mail) throws Exception;
	
	/**
	 * Valida si el nombre de usuario ya existe en la base de datos
	 * @param nombreUsuario
	 * @return
	 * @throws Exception 
	 */
	Boolean existeNombreUsuario(Usuario usuario) throws Exception;
	
}
