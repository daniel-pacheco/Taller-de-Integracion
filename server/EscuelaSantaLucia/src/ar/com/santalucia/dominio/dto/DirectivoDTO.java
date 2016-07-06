package ar.com.santalucia.dominio.dto;

/**
 * DirectivoDTO: contiene información básica de un directivo
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class DirectivoDTO {

	private Long idUsuario;
	private Long nroDocumento;
	private String nombre;
	private String apellido;

	public DirectivoDTO() {
		super();
	}

	public DirectivoDTO(Long idUsuario, Long nroDocumento, String nombre, String apellido) {
		super();
		this.idUsuario = idUsuario;
		this.nroDocumento = nroDocumento;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(Long nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

}
