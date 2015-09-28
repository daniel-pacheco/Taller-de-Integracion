package ar.com.santalucia.validaciones;

public interface IValidacionUsuarioAlumno extends IValidacionUsuario {
	
	/**
	 * Valida si la matricula ya existe en la base de datos
	 * @param matricula
	 * @return
	 * @throws Exception
	 */
	Boolean existeMatricula(Long matricula) throws Exception;

}
