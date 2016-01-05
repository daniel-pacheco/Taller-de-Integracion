package ar.com.santalucia.servicio;

import ar.com.santalucia.dominio.modelo.sistema.login.Login;

/**
 * 
 * @author Ariel Ramirez
 *
 */
public class ServicioLogin {
	/* Responsabilidades:
	Autenticar y desconectar usuarios
	Generar informcion para token
	Descifrar informacion de tokens
	Mantener control de tokens
	¿Podria ser una clase estática?
	*/
	private Login gLogin;
	
	public void Login(){
		gLogin = new Login();
	}
	
	public Boolean autenticar(Long usuario, String clave){
		return false;
	}
	
	public Boolean finalizar(Long usuario){
		/* TENER EN CUENTA QUE EL USUARIO
		 * ESTE LOGUEADO PREVIAMENTE ANTES
		 * DE HACER LA LLAMADA AL GESTOR
		 * */
		return false;
	}
	
	public Boolean recuperarClave(Long usuario){
		/*
		 * GENERAR UNA CLAVE ALEATORIA
		 * GUARDAR NUEVA CLAVE Y MANDARLA
		 * POR MAIL AL USUARIO
		 * */
		return false;
	}
	
	public void enviarMail(String direccion, String clave){
		// LANZAR EXCEPCION EN CASO DE ERROR
	}
}
