package ar.com.santalucia.validaciones;

import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

public interface IValidacionUsuarioAlumno extends IValidacionUsuario {
	
	/**
	 * Valida si la matricula ya existe en la base de datos
	 * @param matricula
	 * @return
	 * @throws Exception
	 */
	Boolean existeMatricula(Alumno alumno) throws Exception;

}
