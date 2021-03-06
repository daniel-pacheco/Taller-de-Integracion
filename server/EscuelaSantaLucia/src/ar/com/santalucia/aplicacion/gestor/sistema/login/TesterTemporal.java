package ar.com.santalucia.aplicacion.gestor.sistema.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;

import ar.com.auth0.jwt.JWTSigner;
import ar.com.auth0.jwt.JWTVerifier;
import ar.com.santalucia.dominio.modelo.sistema.login.Login;
import ar.com.santalucia.rest.ServicioAlumnoEndpoint;
import ar.com.santalucia.rest.ServicioLoginEndpoint;
import ar.com.santalucia.servicio.ServicioLogin;

public class TesterTemporal {

	public static void main(String[] args) throws Exception {
//		String frase1 = new String("PasoDeLosToros");
//		String frase2 = new String("PasoDeLosToros");
//		if( (encriptar(frase1)).equals(encriptar(frase2)) ){
//			System.out.println("Frases coinciden");
//		}else{
//			System.out.println("Frases no coinciden");
//		}
		//rutinaLogin();
		rutinaEjemplo();
		//rutinaConServicio();
		
	}
	
	public static String encriptar(String cadena) throws Exception{
		StringBuffer cadenaEncriptada = new StringBuffer();
		MessageDigest md = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
		md.update(cadena.getBytes());
		byte[] digest = md.digest();
		for (byte b : digest) {
			System.out.print(Integer.toHexString(0xFF & b));
			cadenaEncriptada.append(Integer.toHexString(0xFF & b));
	      }	
		System.out.println();
		return cadenaEncriptada.toString();
	}
	
	public static void rutinaLogin() throws Exception{
		GestorLogin glogin = new GestorLogin();
		Login login = new Login();
		login.setUsuario(32654321L);
		login.setClave("32654321");
		login.setRol(Login.ALUMNO);
		if (glogin.autenticar(login)){
			System.out.println("Credencial validada correctamente");
		}else{
			System.out.println("Credencial inv�lida");
		}
		
		System.in.read();
		
		Login loginOut = new Login();
		loginOut.setUsuario(32654321L);
		
		Boolean salida = glogin.finalizar(loginOut);
		
		if(salida == true){
			System.out.println("Desconexi�n exitosa");
		}else{
			System.out.println("Ocurri� un problema al intententar desconectar la cuenta");
		}
	}
	
	public static void rutinaConServicio() throws Exception{
		ServicioLogin sLogin = new ServicioLogin();
		
		StringBuffer tokenOut = new StringBuffer();
		
		tokenOut.append(sLogin.autenticar(32654321L, "32654321", "ALUMNO"));
		//tokenOut = sLogin.autenticar(32654321L, "32654321", "ALUMNO");
		StringBuffer tokenIn = new StringBuffer();
		tokenIn.append(tokenOut);
		
		ServicioLogin.comprobar(tokenIn.toString(), "ALUMNO");
		
	}
	
	public static void rutinaEjemplo() throws Exception{
		final StringBuffer FIRMA_AL = new StringBuffer();
		FIRMA_AL.append("xdfGtrhjsvSDyAdddddwwwwwqqqqqyyyyykkkkkbbbbbzzzzz44444<<<<<33333aaaaafdgsergdfvdsfgtrgdgsdfgdsgdfsg");		
		
		Map<String, Object> claim = new HashMap<String, Object>();
		Map<String, Object> claimDev = new HashMap<String, Object>();
		
		claim.put("iss", "system");
		claim.put("sub", "Subject");
		claim.put("aud", "342661242");
		claim.put("exp", null);
		claim.put("nbf", Calendar.getInstance().getTime().getTime());
		claim.put("iat", null);
		claim.put("jti", null);
		
		
		JWTSigner signer = new JWTSigner(FIRMA_AL.toString());
		JWTVerifier verifier = new JWTVerifier(FIRMA_AL.toString());
		
		String token = signer.sign(claim);
		System.out.println(token);
		
		claimDev = verifier.verify(token);
		System.out.println(claimDev);
		
		final String CLIENT_SECRET = ""; 
		
//		try {
//	            Base64 decoder = new Base64(true);
//	            byte[] secret = decoder.decodeBase64(CLIENT_SECRET);
//	            Map<String,Object> decodedPayload =
//	                new JWTVerifier(secret, "audience").verify("my-token");
//
//	            // Get custom fields from decoded Payload
//	            System.out.println(decodedPayload.get("name"));
//	        } catch (SignatureException signatureException) {
//	            System.err.println("Invalid signature!");
//	        } catch (IllegalStateException illegalStateException) {
//	            System.err.println("Invalid Token! " + illegalStateException);
//	        }
	}
}
