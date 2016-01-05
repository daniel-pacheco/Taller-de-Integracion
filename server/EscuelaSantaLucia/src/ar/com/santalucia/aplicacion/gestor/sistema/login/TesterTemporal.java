package ar.com.santalucia.aplicacion.gestor.sistema.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

import ar.com.santalucia.dominio.modelo.sistema.login.Login;

public class TesterTemporal {

	public static void main(String[] args) throws Exception {
//		String frase1 = new String("PasoDeLosToros");
//		String frase2 = new String("PasoDeLosToros");
//		if( (encriptar(frase1)).equals(encriptar(frase2)) ){
//			System.out.println("Frases coinciden");
//		}else{
//			System.out.println("Frases no coinciden");
//		}
		
		rutinaLogin();
		
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
			System.out.println("Credencial inválida");
		}
		
		System.in.read();
		
		Login loginOut = new Login();
		loginOut.setUsuario(32654321L);
		
		Boolean salida = glogin.finalizar(loginOut);
		
		if(salida == true){
			System.out.println("Desconexión exitosa");
		}else{
			System.out.println("Ocurrió un problema al intententar desconectar la cuenta");
		}
		
		
	}
}
