package ar.com.santalucia.dominio.modelo.usuarios;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;

/**
 * Clase Usuario
 * 
 * @author EricDaniel
 * @version 1.5
 * 
 */

//UltimoModificador: Eric Pennachini @ 19-09-15 16:05

@XmlRootElement
public class Usuario {
	protected Long idUsuario;
	protected Long nroDocumento;
	protected String tipoDocumento;
	protected String nombre;
	protected String apellido;
	protected Set<Telefono> listaTelefonos;
	protected Set<Mail> listaMails;
	protected Domicilio domicilio;
	protected Character sexo; //f,m
	protected String nombreUsuario;
	protected Date fechaNacimiento;
	protected Boolean activo;

	public Usuario() {
		super();
		listaTelefonos = new HashSet<Telefono>();
		listaMails = new HashSet<Mail>();
	}

	public Usuario(Long nroDocumento, String tipoDocumento, String nombre, String apellido,
			Set<Telefono> listaTelefonos, Set<Mail> listaMails, Domicilio domicilio, Character sexo, String nombreUsuario,
			Date fechaNacimiento, Boolean activo) {
		super();
		this.nroDocumento = nroDocumento;
		this.tipoDocumento = tipoDocumento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.setListaTelefonos(listaTelefonos);
		this.setListaMails(listaMails);
		this.domicilio = domicilio;
		this.sexo = sexo;
		this.nombreUsuario = nombreUsuario;
		this.fechaNacimiento = fechaNacimiento;
		this.activo = activo;
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

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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

	public Set<Telefono> getListaTelefonos() {
		return listaTelefonos;
	}

	public void setListaTelefonos(Set<Telefono> listaTelefonos) {
		this.listaTelefonos = listaTelefonos;
	}

	public Set<Mail> getListaMails() {
		return listaMails;
	}

	public void setListaMails(Set<Mail> listaMails) {
		this.listaMails = listaMails;
	}

	public Character getSexo() {
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return this.nombre + " " + this.apellido;
	}	

	@Override
	public boolean equals(Object obj) {
		Usuario usuario = (Usuario) obj;
		if ((this.idUsuario.equals(usuario.idUsuario)) &&
				(this.nroDocumento.equals(usuario.idUsuario)) &&
				(this.tipoDocumento.equals(usuario.tipoDocumento))) {
			return true;
		}
		return false;
	}
}
