package ar.com.santalucia.validaciones;

public interface IValidacionUsuarioDocDir extends IValidacionUsuario {
	
	/**
	 * Valida si el CUIL del docente o directivo existe en la base de datos.
	 * @param cuil
	 * @return
	 * @throws Exception
	 */
	Boolean existeCuil(Long cuil) throws Exception;

}
