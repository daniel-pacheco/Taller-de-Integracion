package ar.com.santalucia.dominio.dto;

public class RecuperarClaveDTO {
	private Long dniUsuario;
	private String rol;
	private String email;
	
	public RecuperarClaveDTO() {
		super();
	}

	public RecuperarClaveDTO(Long dniUsuario, String rol, String email) {
		super();
		this.dniUsuario = dniUsuario;
		this.rol = rol;
		this.email = email;
	}

	public Long getDniUsuario() {
		return dniUsuario;
	}

	public void setDniUsuario(Long dniUsuario) {
		this.dniUsuario = dniUsuario;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
