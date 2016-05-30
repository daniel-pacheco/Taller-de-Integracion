package ar.com.santalucia.servicio;

import java.security.SignatureException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import ar.com.auth0.jwt.JWTExpiredException;
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
	private GestorLogin gLogin;
	
	private static String FIRMA_AL = "xdfGtrhjsvSDyoplasdcdsegilyEscuelaSantaLucia-uyfFdHhdKfLYGYHheraqw3543543523fwSFA";
	private static String FIRMA_DO = "xdfGtrh545612342554gdsdagnsdcdsegily.EscuelaSantaLucia-uyfFdHhdKfLYGYHher.4567886ewr3543523fwSFA";
	private static String FIRMA_DIR = "qqwdopvjijhrntreugbeugb8890834.EscuelaSantaLucia-uyfFdHhdKfLYGYHher.aqw3dfsghDSFHYRjtyjTKcqw3erdqwecfa<QA";
	private static String FIRMA_AD = "SERHGERHHET$#/$ergdsfgds4356SFSRDF2#&#$.EscuelaSantaLucia-uyfFdHhdKfLYGYHher.aqw3543543523fwSFAaREWTER45dbsdgbqwt3$";
	
	public ServicioLogin() throws Exception{
			gLogin = new GestorLogin();		
		}
	
	/**
	 * Agrega una entrada de login para un usuario nuevo. 
	 * @param usuario
	 * @param rol
	 */
	public void addLogin(Long usuario, String rol) throws Exception{
		try {
			gLogin.add(new Login(null, usuario , usuario.toString(), null, null, rol, false));
		} catch(Exception ex) {
			throw new Exception("Servicio: No se pudo dar de alta la entrada de login: " + ex.getMessage());
		}
	}
	
	/**
	 * Actualiza los datos de login de un usuario. Cuando un usuario cammbia de número de DNI se debe actualizar la tabla de login.
	 * @param usuarioViejo DNI antiguo del usuario.
	 * @param usuarioNuevo DNI nuevo del usuario.
	 * @throws Exception
	 */
	public void actualizarUsuario(Long usuarioViejo, Long usuarioNuevo) throws Exception{
		try{
			gLogin.updateUser(usuarioViejo, usuarioNuevo);
		}catch(Exception ex){
			throw new Exception("Servicio: No se pudo modificar el usuario en login: " + ex.getMessage());
		}
	}
	
	/**
	 * Verifica las credenciales presentadas por el usuario y datos de la llamada del front end. Si las credenciales son correctas se devuelve un token.
	 * @param usuario
	 * @param clave
	 * @param rol
	 * @return Token si las credenciales son correctas
	 * @throws Exception
	 */
	public String autenticar(Long usuario, String clave, String rol) throws Exception{
		String token = null;
		if (gLogin.autenticar(new Login(null, usuario, clave, null,null,rol,null))){
			token = generarToken(usuario.toString(), rol);
			return token;
		}else{
			throw new LoginError(LoginError.LOGINERROR);
		}
	}
	
	/**
	 * Comprueba el token y los datos asociados por cada petición realizada al backend. En caso de que las credenciales coincidan, se verifica
	 * el tiempo de expiración del token. De estar próximo a expirar el token se devuelve uno nuevo.
	 * @param token
	 * @param rol 
	 * @return
	 * @throws Exception 
	 * @throws SignatureException
	 * @throws LoginError
	 */
	public static String comprobar(String token, String rol) throws Exception, SignatureException, LoginError{
		try{
			Map<String, Object> claimDev = new HashMap<String, Object>();
			JWTVerifier verifier = new JWTVerifier(obtenerFirma(rol));
			claimDev = verifier.verify(token);
			System.out.println(">> Se requirió verificación para " + (claimDev.get("aud")).toString());					// CONSOLA
			return renovarToken(Long.valueOf((claimDev.get("exp")).toString()), (claimDev.get("aud")).toString(), rol);
		
			//claimDev se puede usar para necesidades futuras, como verificar la expiracion
		}catch(SignatureException ex){
			throw new LoginError(LoginError.FIRMAERROR);
		}catch (JWTExpiredException ex){
			throw new LoginError(LoginError.EXPIRADO);
		}catch(Exception ex){
			throw ex;
		}
		
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
	
	private static String generarToken(String usuario, String rol) throws Exception{
		Map<String, Object> claim = new HashMap<String, Object>(); // Definición del claim
		Calendar calendar = Calendar.getInstance();
		
		calendar.add(Calendar.MINUTE, 20); // Calendar para fijar el tiempo de expiración, n minutos a partir de fecha del sistema.
		//System.out.println("Supuesta expiracion: " + calendar.getTime()); // Descomentar esta linea para tener salida por consola
		
		claim.put("iss", "systemStaLucia");
		claim.put("sub", rol);
		claim.put("aud", usuario);
		claim.put("exp", calendar.getTimeInMillis());
		claim.put("nbf", Calendar.getInstance().getTimeInMillis());
		
		System.out.println("nbf(aprox): " + Calendar.getInstance().getTime());
		
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
			throw new LoginError(LoginError.FIRMAERROR);
		}
		return firma;
	}
	
	private static String renovarToken(Long tExpiracion, String usuario, String rol) throws Exception {
		Long diferencia = tExpiracion - System.currentTimeMillis();
		if (diferencia <= (1000*600)){ //Unidades en milisegundos (1000 * segundos antes de que expire) actual: 10 minutos
			System.out.println(">> Token renovado para " + usuario + " !"); 	// CONSOLA
			return generarToken(usuario, rol);
		}
		return null;
	}
	
	/**
	 * Devuelve el nombre del rol (aud del token)
	 * @param rol
	 * @param token
	 * @return
	 */
	public static String obtenerIdentificacionUsuario(String rol, String token) throws Exception{
		try{
			Map<String, Object> claimDev = new HashMap<String, Object>();
			JWTVerifier verifier = new JWTVerifier(obtenerFirma(rol));
			claimDev = verifier.verify(token);
				return (claimDev.get("aud")).toString();
			}catch(Exception ex){
				throw new Exception("Seguridad: No se pudo recuperar el usuario");
			}
	}
	
}
