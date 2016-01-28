package ar.com.santalucia.excepciones;

public class LoginError extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String detalles; 
	
	public static final String LOGINERROR="Las credenciales ingresadas son incorrectas";
	public static final String FIRMAERROR = "La firma est� corrupta";
	public static final String ROLERROR = "No est� autorizado para realizar esta operaci�n";
	public static final String EXPIRADO = "La sesi�n ha expirado";
	
	public LoginError(String mensaje){
		detalles = mensaje;
	}
	
	public String getDetalles() {
		return detalles;
	}
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	
	@Override
	public String getMessage() {
		
		return getDetalles();
	}
	
}
