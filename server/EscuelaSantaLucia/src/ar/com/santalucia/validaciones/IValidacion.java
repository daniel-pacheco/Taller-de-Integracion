package ar.com.santalucia.validaciones;

import ar.com.santalucia.dominio.modelo.usuarios.Usuario;

public interface IValidacion {
	public void validar(Object object) throws Exception;
}
