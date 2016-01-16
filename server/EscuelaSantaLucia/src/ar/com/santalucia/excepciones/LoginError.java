package ar.com.santalucia.excepciones;

public class LoginError extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String detalles; 
	
	public static final String LOGINERROR="Las credenciales ingresadas son incorrectas";
	public static final String FIRMAERROR="No tiene permisos para esta solicitud";
	
	public LoginError(String mensaje){
		detalles = mensaje;
	}
	
	public String getDetalles() {
		return detalles;
	}
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	
}
