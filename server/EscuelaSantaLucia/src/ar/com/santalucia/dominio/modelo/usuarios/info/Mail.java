package ar.com.santalucia.dominio.modelo.usuarios.info;

/**
 * Clase Mail, almacena las direcciones de mail de los usuarios
 * 
 * @author Eric
 * @version 1.3
 */

// UltimoModificador: Eric Pennachini @ 16-08-15 16:19

public class Mail {

	private Long idMail;
	private String direccionMail;
	private String tipoMail;

	public Mail() {
		super();
	}

	public Mail(String direccionMail, String tipoMail) {
		super();
		this.direccionMail = direccionMail;
		this.tipoMail = tipoMail;
	}

	public Long getIdMail() {
		return idMail;
	}

	public void setIdMail(Long idMail) {
		this.idMail = idMail;
	}

	public String getDireccionMail() {
		return direccionMail;
	}

	public void setDireccionMail(String direccionMail) {
		this.direccionMail = direccionMail;
	}

	public String getTipoMail() {
		return tipoMail;
	}

	public void setTipoMail(String tipoMail) {
		this.tipoMail = tipoMail;
	}

	@Override
	public String toString() {
		String mailCompleto = direccionMail;
		if (tipoMail != null) {
			mailCompleto = mailCompleto + " (" + tipoMail + ")";
		}
		return mailCompleto;
	}

	@Override
	public boolean equals(Object obj) {

		if ((this.direccionMail.equals(((Mail) obj).direccionMail) 
			&& (this.tipoMail.equals(((Mail) obj).tipoMail)))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	
}
