package ar.com.santalucia.rest;

public class FrontMessage {
	public static final String INFO = "INFO";
	public static final String CRITICAL = "CRITICAL";
	
	private String mensaje;
	private String severidad;
	
	public FrontMessage() {
		super();
	}

	public FrontMessage(String mensaje, String severidad) {
		super();
		this.mensaje = mensaje;
		this.severidad = severidad;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getSeveridad() {
		return severidad;
	}

	public void setSeveridad(String severidad) {
		this.severidad = severidad;
	}
	
	
}
