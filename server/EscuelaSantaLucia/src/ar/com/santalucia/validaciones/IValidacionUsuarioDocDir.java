package ar.com.santalucia.validaciones;

import ar.com.santalucia.dominio.modelo.usuarios.Usuario;

public interface IValidacionUsuarioDocDir extends IValidacionUsuario {
	
	/**
	 * Valida si el CUIL del docente o directivo existe en la base de datos.
	 * @param cuil
	 * @return
	 * @throws Exception
	 */
	Boolean existeCuil(Usuario usuario) throws Exception;

}
