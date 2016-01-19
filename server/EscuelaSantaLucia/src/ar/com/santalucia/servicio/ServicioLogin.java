package ar.com.santalucia.servicio;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ar.com.auth0.jwt.JWTSigner;
import ar.com.auth0.jwt.JWTVerifier;
import ar.com.santalucia.aplicacion.gestor.sistema.login.GestorLogin;
import ar.com.santalucia.dominio.modelo.sistema.login.Login;
import ar.com.santalucia.excepciones.LoginError;

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
	private GestorLogin gLogin;
	
	private static String FIRMA_AL = "xdfGtrhjsvSDyopñlasdcdsegily.EscuelaSantaLucia-uyfFdHhdKfLYGYHher.aqw3543543523fwSFA";
	private static String FIRMA_DO = "xdfGtrh545612342554gdsdagnsdcdsegily.EscuelaSantaLucia-uyfFdHhdKfLYGYHher.4567886ewr3543523fwSFA";
	private static String FIRMA_DIR = "qqwdopvjijhrntreugbeugb8890834.EscuelaSantaLucia-uyfFdHhdKfLYGYHher.aqw3dfsghDSFHYRjtyjTKcqw3erdqwecfa<QA";
	private static String FIRMA_AD = "SERHGERHHET$#/$ergdsfgds4356SFSRDF2#&#$.EscuelaSantaLucia-uyfFdHhdKfLYGYHher.aqw3543543523fwSFAaREWTER45dbsdgbqwt3$";
	//private static String SUBJECT="SantaLuciaSystem";
	
	public ServicioLogin() throws Exception{
		try {
			gLogin = new GestorLogin();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Agrega una entrada de login para un usuario nuevo. 
	 * @param usuario
	 * @param rol
	 */
	public void addLogin(Long usuario, String rol) throws Exception{
		try {
			gLogin.add(new Login(null, usuario , usuario.toString(), null, null, Login.ALUMNO, false));
		} catch(Exception ex) {
			throw new Exception("Servicio: No se pudo dar de alta la entrada de login: " + ex.getMessage());
		}
	}
	
	public void actualizarUsuario(Long usuarioViejo, Long usuarioNuevo) throws Exception{
		try{
			gLogin.updateUser(usuarioViejo, usuarioNuevo);
		}catch(Exception ex){
			throw new Exception("Servicio: No se pudo modificar el usuario en login: " + ex.getMessage());
		}
	}
	
	public String autenticar(Long usuario, String clave, String rol) throws Exception{
		// Comprobar las credenciales
		// Si es correcto, generar el token
		// Devolver el token
		String token = null;
		if (gLogin.autenticar(new Login(null, usuario, clave, null,null,rol,null))){
			token = generarToken(usuario.toString(), rol);
			return token;
		}else{
			throw new LoginError(LoginError.LOGINERROR);
		}
	}
	
	/**
	 * 
	 * @param token
	 * @throws Exception
	 */
	public static String comprobar(String token, String rol) throws Exception{
		Map<String, Object> claimDev = new HashMap<String, Object>();
		JWTVerifier verifier = new JWTVerifier(obtenerFirma(rol));
		claimDev = verifier.verify(token);
		return claimDev.get("sub").toString();
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
	
	public String generarToken(String usuario, String rol) throws Exception{
		Map<String, Object> claim = new HashMap<String, Object>(); // Definición del claim
		
		claim.put("iss", "systemStaLucia");
		claim.put("sub", rol);
		claim.put("aud", usuario);
		claim.put("exp", null);
		claim.put("nbf", Calendar.getInstance().getTime().getTime());
		claim.put("iat", null);
		claim.put("jti", null);
		
		String firma = obtenerFirma(rol);
		
		JWTSigner signer = new JWTSigner(firma); //Firma
		
		return signer.sign(claim); // Firmar y devolver
	}
	
	/**
	 * Obtiene la firma de acuerdo al rol que se haya solicitado
	 * @param rol
	 * @return
	 * @throws Exception
	 */
	private static String obtenerFirma(String rol) throws Exception{
		String firma = null;
		switch (rol) {
		case Login.ALUMNO :
			firma = ServicioLogin.FIRMA_AL;
			break;
		case Login.DIRECTIVO :
			firma = ServicioLogin.FIRMA_DIR;
			break;
		case Login.DOCENTE :
			firma = ServicioLogin.FIRMA_DO;
			break;
		case Login.ADMINISTRADOR :
			firma = ServicioLogin.FIRMA_AD;
			break;
		default:
			break;
			//Disparar excepcion PermisoDenegado
		}
		return firma;
	}
	
}
