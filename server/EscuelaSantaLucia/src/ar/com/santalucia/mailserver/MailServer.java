package ar.com.santalucia.mailserver;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailServer {

	private String username;
	private String password;
	private String from;
	private Properties props;
	private Message message;
	private Session session;

	public MailServer() {
		super();
		// this.username = "escuelasantalucia.info@gmail.com";
		// this.password = "santaluciaescuela2016";
		this.username = "soporte.sgsa@gmail.com";
		this.password = "linuxalpoder";
		this.from = "soporte.sgsa@gmail.com";
		// seteo de propiedades (host, port, ...)
		this.setProps();
	}

	/**
	 * Seteo de propiedades. Para testing se usa cuenta de Outlook
	 */
	public void setProps() {
		props = new Properties();
		// outlook
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.host", "smtp-mail.outlook.com");		
//		props.put("mail.smtp.port", "587"); // o 25
//		props.put("mail.smtp.auth", "true");
		
		// gmail
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.host", "smtp.gmail.com");		
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");

	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Boolean login() throws Exception {
		try {
			session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			return true;
		} catch (Exception e) {
			// throw new Exception("Ha ocurrido un error al iniciar sesión en la cuenta de correo especificada: " + e.getMessage());
			return false;
		}
	}
	
//	public Boolean logout() {
//		
//	}
	
	public Boolean sendMessage(String to, String subject, String messageContent) 
			throws AddressException, MessagingException {
		try {
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(messageContent);
			Transport.send(message);
		} catch (AddressException e) {
			throw new AddressException("Ha ocurrido un error al establecer 'from' y 'to': " + e.getMessage());
		} catch (MessagingException e) {
			throw new MessagingException("Ha ocurrido un error al enviar el correo con la contraseña: " + e.getMessage());
		}
		return true;
	}
}
