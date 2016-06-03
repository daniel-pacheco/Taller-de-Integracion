package ar.com.santalucia.dominio.modelo.usuarios.info;

/**
 * Clase Telefono, almacena las direcciones de mail de los usuarios
 * 
 * @author Eric
 * @version 1.3
 */

//UltimoModificador: Eric Pennachini @ 16-08-15 16:19

public class Telefono {

	private Long idTelefono;
	private Long caracteristica; 
	private Long nroTelefono;
	private String tipoTelefono;

	public Telefono() {
		super();
	}

	public Telefono(Long caracteristica, Long nroTelefono, String tipoTelefono) {
		super();
		this.caracteristica = caracteristica;
		this.nroTelefono = nroTelefono;
		this.tipoTelefono = tipoTelefono;
	}

	public Long getIdTelefono() {
		return idTelefono;
	}
	
	public void setIdTelefono(Long idTelefono) {
		this.idTelefono = idTelefono;
	}

	public Long getNroTelefono() {
		return nroTelefono;
	}

	public void setNroTelefono(Long nroTelefono) {
		this.nroTelefono = nroTelefono;
	}

	public String getTipoTelefono() {
		return tipoTelefono;
	}

	public void setTipoTelefono(String tipoTelefono) {
		this.tipoTelefono = tipoTelefono;
	}

	public Long getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(Long caracteristica) {
		this.caracteristica = caracteristica;
	}

	@Override
	public String toString() {
		String telefonoCompleto = "(" + caracteristica + ") " + nroTelefono;
		return super.toString();
	}

}
