package ar.com.santalucia.excepciones;

import ar.com.santalucia.dominio.modelo.usuarios.Directivo;

public class SugerenciaDirectivoException extends Exception {
	private static final long serialVersionUID = 1L;
	private Directivo directivoSugerido;
	private String mensaje;
	
	public SugerenciaDirectivoException() {
		directivoSugerido = new Directivo();
	}
	
	public Directivo getDirectivoSugerido() {
		return directivoSugerido;
	}
	
	public void setDirectivoSugerido(Directivo directivoSugerido) {
		this.directivoSugerido = directivoSugerido;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String getMessage() {
		return mensaje;
	}	
}
