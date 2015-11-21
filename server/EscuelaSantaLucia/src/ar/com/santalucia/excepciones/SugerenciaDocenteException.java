package ar.com.santalucia.excepciones;

import ar.com.santalucia.dominio.modelo.usuarios.Docente;

public class SugerenciaDocenteException extends Exception {
	private static final long serialVersionUID = 1L;
	private Docente docenteSugerido;
	private String mensaje;

	public SugerenciaDocenteException() {
		super();
		docenteSugerido = new Docente();
	}

	public Docente getDocenteSugerido() {
		return docenteSugerido;
	}

	public void setDocenteSugerido(Docente docenteSugerido) {
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
