package ar.com.santalucia.servicio;

import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import ar.com.auth0.jwt.JWTExpiredException;
import ar.com.auth0.jwt.JWTSigner;
import ar.com.auth0.jwt.JWTVerifier;
import ar.com.santalucia.aplicacion.gestor.sistema.login.GestorLogin;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorPersonal;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorUsuario;
import ar.com.santalucia.dominio.modelo.sistema.login.Login;
import ar.com.santalucia.dominio.modelo.usuarios.Administrador;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;
import ar.com.santalucia.dominio.modelo.usuarios.Usuario;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.excepciones.LoginError;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.mailserver.MailServer;
import ar.com.santalucia.rest.FrontMessage;

/**
 * 
 * @author Ariel Ramirez
 *
 */
public class ServicioLogin {
	private GestorLogin gLogin;
	private MailServer mailServer;
	
	private static String FIRMA_AL = "xdfGtrhjsvSDyoplasdcdsegilyEscuelaSantaLucia-uyfFdHhdKfLYGYHheraqw3543543523fwSFA";
	private static String FIRMA_DO = "xdfGtrh545612342554gdsdagnsdcdsegily.EscuelaSantaLucia-uyfFdHhdKfLYGYHher.4567886ewr3543523fwSFA";
	private static String FIRMA_DIR = "qqwdopvjijhrntreugbeugb8890834.EscuelaSantaLucia-uyfFdHhdKfLYGYHher.aqw3dfsghDSFHYRjtyjTKcqw3erdqwecfa<QA";
	private static String FIRMA_AD = "SERHGERHHET$#/$ergdsfgds4356SFSRDF2#&#$.EscuelaSantaLucia-uyfFdHhdKfLYGYHher.aqw3543543523fwSFAaREWTER45dbsdgbqwt3$";
	
	
	public ServicioLogin() throws Exception{
			gLogin = new GestorLogin();	
			mailServer = new MailServer();
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
	public void actualizarUsuario(Long usuarioViejo, Long usuarioNuevo, String rol) throws Exception{
		try{
			gLogin.updateUser(usuarioViejo, usuarioNuevo, rol);
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
	
	public static String comprobarCredenciales(String rol, String token) throws ValidacionException, Exception {
		try {
			return ServicioLogin.comprobar(token, rol);
		} catch (LoginError lEx) {
			ValidacionException vEx = new ValidacionException();
			switch (lEx.getDetalles()) {
			case LoginError.ROLERROR:
				vEx.addMensajeError("Acceso no autorizado");
				break;
			case LoginError.FIRMAERROR:
				vEx.addMensajeError("Acceso prohibido");
				break;
			case LoginError.EXPIRADO:
				vEx.addMensajeError("Sesión expirada");
				break; 
			default:
				break;
			}
			throw vEx;
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
	
	public Boolean recuperarClave(Long dniUsuario, String rol, String mail) throws ValidacionException, Exception {
		try {
			verificarMail(dniUsuario, rol, mail);
			Boolean resLogin = mailServer.login();
			
			Login loginUsuario = new Login();
			ArrayList<Login> listaLogin = gLogin.getByExample(new Login(null,dniUsuario,null,null,null,rol,null));
			loginUsuario = listaLogin.get(0);
			Usuario usuario = devolverUsuarioSegunRol(rol, dniUsuario);
			String nuevaClave = generarStringRandom(8);
			
			loginUsuario.setClave(gLogin.encriptar(nuevaClave));
			gLogin.modify(loginUsuario);
			
			String subject = "S.G.S.A. (soporte) - Recuperación de clave de acceso - usuario: " + dniUsuario + ", rol: " + rol;
			String message = "Sentimos que haya olvidado o perdido su contraseña de acceso al sistema. "
					+ "A continuación le proporcionamos una clave generada automáticamente para que pueda volver a ingresar: "
					+ "\n\n" + nuevaClave + "\n\n"
					+ "Recuerde que puede cambiar esta clave desde el apartado 'Mi Cuenta' de su perfil en la aplicación.\n"
					+ "Si usted no reconoce este email y usted nunca pidió la recuperación de la contraseña, póngase inmediatamente "
					+ "en contacto con el administrador, a la siguiente dirección: "
					+ "\n\nsoporte.sgsa@gmail.com"
					+ "\n\nGracias por utilizar S.G.S.A.!";
			
			for (Mail m : usuario.getListaMails()) {
				mailServer.sendMessage(m.getDireccionMail(), subject, message);
			}
			
			return true;
		} catch (ValidacionException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("Hubo un error al enviar el email con la contraseña: " + e.getMessage());
		}
	}
	
	/**
	 * Recibe datos del usuario para cambiar su clave, y también la clave actual y la nueva. Luego envía
	 * una notificación a los correos del usuario.
	 * @param dniUsuario
	 * @param rol
	 * @param contraseñaActual
	 * @param contraseñaNueva
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	public Boolean cambiarContraseña(String dniUsuario, String rol, String contraseñaActual, String contraseñaNueva) 
			throws ValidacionException, Exception {
		ValidacionException vEx = new ValidacionException();
		try {
			Boolean resLogin = mailServer.login();
			Login loginAModificar = gLogin.getByExample(new Login(null, Long.getLong(dniUsuario), null, null, null, rol, null)).get(0);
			if (loginAModificar.getClave().equals(gLogin.encriptar(contraseñaActual))) {
				loginAModificar.setClave(gLogin.encriptar(contraseñaNueva));
				gLogin.modify(loginAModificar);
				// generar mail para enviar a los mails del usuario
				Usuario usuarioModificaClave = devolverUsuarioSegunRol(rol, Long.getLong(dniUsuario));
				// notifica a los mails del usuario sobre el cambio de contraseña
				String subject = "S.G.S.A. (soporte) - Cambio de contraseña de usuario: " + dniUsuario + ", rol: " + rol;
				String message = "Se notifica que el usuario " + dniUsuario + " con rol " + rol 
						+ " ha generado una nueva contraseña de acceso al sistema S.G.S.A.\n"
						+ "Si usted no reconoce este email y usted nunca efectuó el cambio de la contraseña, póngase inmediatamente "
						+ "en contacto con el administrador, a la siguiente dirección: "
						+ "\n\nsoporte.sgsa@gmail.com"
						+ "\n\nGracias por utilizar S.G.S.A.!";
				for (Mail m : usuarioModificaClave.getListaMails()) {
					mailServer.sendMessage(m.getDireccionMail(), subject, message);
				}
			} else {
				vEx.addMensajeError("Clave actual incorrecta");
				throw vEx;
			}
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al modificar la contraseña: " + ex.getMessage());
		}
		return true;
	}
	
	/**
	 * Devuelve un usuario (alumno, personal, administrador o usuario por defecto) de acuerdo al rol
	 * y dni de usuario que se pasan.
	 * @param rol
	 * @param dniUsuario
	 * @return
	 * @throws Exception
	 */
	private Usuario devolverUsuarioSegunRol(String rol, Long dniUsuario) throws Exception {
		Usuario usuario;
		switch (rol) {
		case Login.ALUMNO:
			ServicioAlumno sAlumno = new ServicioAlumno();
			usuario = new Alumno();
			usuario = sAlumno.getUsuarioByDni(dniUsuario);
			break;
		case Login.DOCENTE:
			ServicioDocente sDocente = new ServicioDocente();
			usuario = new Personal();
			usuario = sDocente.getUsuarioByDni(dniUsuario);
			break;
		case Login.DIRECTIVO:
			ServicioDirectivo sDirectivo = new ServicioDirectivo();
			usuario = new Personal();
			usuario = sDirectivo.getUsuarioByDni(dniUsuario);
			break;
		case Login.ADMINISTRADOR:
			// ¿?
			usuario = new Administrador();
			break;	
		default:
			usuario = new Usuario();
		}
		
		return usuario;
	}
	
	
	/**
	 * Comprueba que el usuario que intenta recuperar la calve conozca un mail propio.
	 * @param dniUsuario
	 * @param rol
	 * @param mail
	 * @throws ValidacionException
	 * @throws Exception
	 */
	private void verificarMail(Long dniUsuario, String rol, String mail) throws ValidacionException, Exception{
		Boolean comprobacion = false;
		Set<Mail> mails = new HashSet<Mail>();
		GestorAlumno gAlumno = new GestorAlumno();
		GestorPersonal gPersonal = new GestorPersonal();
		ValidacionException vEx = new ValidacionException();
		try{
			switch (rol) {
			case Login.ALUMNO:
				
				List<Alumno> alumnos = gAlumno.getByExample(new Alumno(dniUsuario,null,null,null,null,null,null,null,null,null,null,true,null));
				if (alumnos.size() == 1){
					mails = alumnos.get(0).getListaMails();
				}
				break;
			case Login.DIRECTIVO:
				List<Personal> directivos = gPersonal.getByExample(new Personal(dniUsuario,null,null,null,null,null,null,null,null,null,null,true,null,null,true,null));
				if(directivos.size()==1){
					mails = directivos.get(0).getListaMails();
				}
				break;
			case Login.DOCENTE:
				List<Personal> docentes = gPersonal.getByExample(new Personal(dniUsuario,null,null,null,null,null,null,null,null,null,null,true,null,null,null,true));
				if(docentes.size()==1){
					mails = docentes.get(0).getListaMails();
				}
				break;
				}
		if(mails.size()!=0){
			for(Mail m : mails){
				if(m.getDireccionMail().equals(mail)){
					comprobacion = true;
				}
			}
		}
		if (comprobacion == false){
			vEx.addMensajeError("No se pudo verificar su identidad para recuperar la contraseña.");
			throw vEx;
		}
		}catch(ValidacionException ex){
			throw vEx;
		}catch (Exception ex){
			throw ex;
		}
	}

	public static String generarStringRandom(int cantCaract) {
		String caracteres = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		Random rnd = new Random();
		StringBuilder cadenaRandom = new StringBuilder();
		for (int i = 0; i < cantCaract; i++) {
			cadenaRandom.append(caracteres.charAt(rnd.nextInt(caracteres.length())));
		}
		return cadenaRandom.toString();
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
