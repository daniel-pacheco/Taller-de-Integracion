package ar.com.santalucia.servicio;

import ar.com.santalucia.aplicacion.gestor.sistema.login.GestorLogin;

public class ServicioAutenticacion {
	
	private GestorLogin gLogin;
	
	public ServicioAutenticacion(){
		try {
			gLogin = new GestorLogin();
		} catch (Exception e) {
			// EXCEPCION NO TRATADA!
			e.printStackTrace();
		}
	}
	
	public Boolean autenticar(){
		return true;
	}
	
	
	
}
