package ar.com.santalucia.excepciones;

import java.util.ArrayList;

/**
 * Excepcion que maneja los mensajes de error de validaciones
 * @author ericpennachini
 * @version 1.0
 *
 */
public class ValidacionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> mensajesError = new ArrayList<String>();
	
	/**
	 * Agrega un mensaje de error a la lista
	 * @param mensaje
	 */
	public void addMensajeError(String mensaje) {
		if (mensaje != null) {
			mensajesError.add(mensaje);
		}
	}

	public ArrayList<String> getMensajesError() {
		return mensajesError;
	}

	public void setMensajesError(ArrayList<String> mensajesError) {
		this.mensajesError = mensajesError;
	}

	@Override
	public String getMessage() {
		String mensaje = new String("");
		for (String m : mensajesError) {
			mensaje += "\n" + m;
		}
		return super.getMessage() + "\n" + mensaje;
	}
}
