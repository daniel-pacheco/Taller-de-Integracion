package ar.com.santalucia.servicio;

import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorDomicilio;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorMail;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorTelefono;

public abstract class ServicioUsuario<T> implements IServicioUsuario<T> {
	protected GestorDomicilio gDomicilio;
	protected GestorMail gMail;
	protected GestorTelefono gTelefono;

	public ServicioUsuario() throws Exception {
		try {
			gDomicilio = new GestorDomicilio();
			gMail = new GestorMail();
			gTelefono = new GestorTelefono();
		} catch (Exception ex) {
			throw new Exception(
					"Ha ocurrido un problema al intentar inicializar el servicio de operaciones básicas. "
							+ ex.getMessage());
		}

	}

}
