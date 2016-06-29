package ar.com.santalucia.excepciones;

import ar.com.santalucia.dominio.modelo.usuarios.Personal;

public class SugerenciaPersonalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mensaje;
	private Personal personalSugerido;
	
	public SugerenciaPersonalException() {
		super();
		personalSugerido = new Personal();
	}
	
	public Personal getPersonalSugerido() {
		return personalSugerido;
	}
	
	public void setPersonalSugerido(Personal personalSugerido) {
		this.personalSugerido = personalSugerido;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String getMessage() {
		return mensaje;
	}	

}
