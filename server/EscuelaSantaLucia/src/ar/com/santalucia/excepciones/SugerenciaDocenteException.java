package ar.com.santalucia.excepciones;

import ar.com.santalucia.dominio.modelo.usuarios.Personal;

public class SugerenciaDocenteException extends Exception {
	private static final long serialVersionUID = 1L;
	private Personal docenteSugerido;
	private String mensaje;

	public SugerenciaDocenteException() {
		super();
		docenteSugerido = new Personal();
	}

	public Personal getDocenteSugerido() {
		return docenteSugerido;
	}

	public void setDocenteSugerido(Personal docenteSugerido) {
		this.docenteSugerido = docenteSugerido;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String getMessage() {
		return mensaje;
	}
	
	
}
