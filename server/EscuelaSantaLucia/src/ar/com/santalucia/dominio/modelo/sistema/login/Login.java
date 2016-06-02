package ar.com.santalucia.dominio.modelo.sistema.login;

import java.util.Date;

public class Login {
	private Long idLogin;
	private Long usuario; // NRO DODUMENTO
	private String clave;
	private Date ultimoAcceso;
	private Date ultimoEgreso;
	private String rol;
	private Boolean habilitado; // HABILITACION PARA ACCESO AL SISTEMA. NO ES EL MISMO HABILITAR QUE EL DE USUARIO
	
	public static final String DOCENTE = "DOCENTE";
	//public static final String DOCENTE_DIRECTIVO = "DOCENTE/DIRECTIVO";
	public static final String DIRECTIVO = "DIRECTIVO";
	public static final String ALUMNO = "ALUMNO";
	public static final String ADMINISTRADOR = "ADMINISTRADOR";
	
	public Login(){
		idLogin = null;
		usuario = null;
		clave = null;
		ultimoAcceso = null;
		ultimoEgreso = null;
		rol = null;
		habilitado = null;
		
	}

	public Login(Long idLogin, Long usuario, String clave, Date ultimoAcceso, Date ultimoEgreso, String rol, Boolean habilitado) {
		super();
		this.idLogin = idLogin;
		this.usuario = usuario;
		this.clave = clave;
		this.ultimoAcceso = ultimoAcceso;
		this.ultimoEgreso = ultimoEgreso;
		this.rol = rol;
		this.habilitado= habilitado;
		
	}

	public Long getIdLogin() {
		return idLogin;
	}

	public void setIdLogin(Long idLogin) {
		this.idLogin = idLogin;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Date getUltimoAcceso() {
		return ultimoAcceso;
	}

	public void setUltimoAcceso(Date ultimoAcceso) {
		this.ultimoAcceso = ultimoAcceso;
	}
	
	public Date getUltimoEgreso() {
		return ultimoEgreso;
	}

	public void setUltimoEgreso(Date ultimoEgreso) {
		this.ultimoEgreso = ultimoEgreso;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}
	
}
