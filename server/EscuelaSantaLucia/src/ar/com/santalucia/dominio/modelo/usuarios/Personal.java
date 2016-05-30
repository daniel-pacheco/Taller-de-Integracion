package ar.com.santalucia.dominio.modelo.usuarios;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ar.com.santalucia.dominio.modelo.academico.Mesa;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;

/**
 * Clase Docente
 * 
 * @author EricDaniel
 * @version 1.2
 *
 */

//UltimoModificador: Ariel Ramirez @ 25-11-15 17:18

public class Personal extends Usuario {

	private Set<Titulo> listaTitulos;
	private Long cuil;
	private String rol;
	
	public static String DOCENTE = "DOCENTE";
	public static String DIRECTIVO = "DIRECTIVO";
	public static String DOCENTE_DIRECTIVO = "DOCENTE/DIRECTIVO";

	public Personal() {
		super();
		setListaTitulos(new HashSet<Titulo>());
	}

	public Personal(Long nroDocumento, String tipoDocumento, String nombre, String apellido,
			Set<Telefono> listaTelefonos, Set<Mail> listaMails, Domicilio domicilio, Character sexo, String nombreUsuario,
			Date fechaNacimiento, Boolean activo, Set<Titulo> listaTitulos, Long cuil, String rol) {
		super(nroDocumento, tipoDocumento, nombre, apellido, listaTelefonos, listaMails, domicilio, sexo,
				nombreUsuario, fechaNacimiento, activo);
		this.setListaTitulos(listaTitulos);
		this.cuil = cuil;
	}

	public Set<Titulo> getListaTitulos() {
		return listaTitulos;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public void setListaTitulos(Set<Titulo> listaTitulos) {
		this.listaTitulos = listaTitulos;
	}

	public Long getCuil() {
		return cuil;
	}

	public void setCuil(Long cuil) {
		this.cuil = cuil;
	}

	/* (non-Javadoc)
	 * @see ar.com.santalucia.dominio.modelo.usuarios.Usuario#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Personal) {
			Personal personal = (Personal) obj;
			if ((this.idUsuario.equals(personal.idUsuario)) && (this.nroDocumento.equals(personal.nroDocumento))
					&& (this.tipoDocumento.equals(personal.tipoDocumento)) && (this.cuil.equals(personal.cuil))) {
				return true;
			} 
		}
		return false;
	}
	

}
