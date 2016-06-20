package ar.com.santalucia;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class PruebaMail {

	public static void main(String[] args) {
		// para probar , ir a https://myaccount.google.com/security
		// cambiar el punto que dice "Permitir el acceso de aplicaciones menos seguras: SI"
		System.out.println(">> Setting username and password...");
//		final String userName = "eric.pennachini@gmail.com";
//		final String password = "metalcristiano";
		final String userName = "ericdp_0591@hotmail.com";
		final String password = "mglatu";
		System.out.println("Done!");

		System.out.println(">> Setting properties...");
		Properties props = new Properties();
//		props.put("mail.smtp.socketFactory.port", "465");
//		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		props.put("mail.smtp.host", "smtp.gmail.com");		
//		props.put("mail.smtp.port", "465");
//		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp-mail.outlook.com");		
		props.put("mail.smtp.port", "587"); // o 25
		props.put("mail.smtp.auth", "true");
		System.out.println("Done!");
		
		System.out.println(">> Setting authentication...");
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		System.out.println("Done!");
		
		try {
			System.out.println(">> Building the message...");
			Message message = new MimeMessage(session);
			String from = "ericdp_0591@hotmail.com";
			String to = "eric.pennachini@gmail.com";
			// String to = "mauricioarielramirez@gmail.com";
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Test subject");
			message.setText("This is a test message. I'm the best :===D \n\n "
					+ "Si estás viendo este mensaje, es porque sos un hincha de la TV");
			System.out.println("Done!");
			
			System.out.println(">> Sending message...");
			Transport.send(message);
			System.out.println("Success!!!");
		} catch (MessagingException e) {
			System.err.println("Error!!! \n" + e.getMessage());
		}
		
	}

}
