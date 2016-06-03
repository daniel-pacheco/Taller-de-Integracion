package ar.com.santalucia.dominio.modelo.usuarios;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.JsonAdapter;

import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;

/**
 * Clase Alumno
 * @author EricDaniel
 * @version 1.2
 *
 */

//UltimoModificador: Eric Pennachini @ 16-08-15 16:20
@XmlRootElement

public class Alumno extends Usuario {

	private Long matricula;

	public Alumno() {
		super();
	}

	public Alumno(Long nroDocumento, String tipoDocumento, String nombre, String apellido,
			Set<Telefono> listaTelefonos, Set<Mail> listaMails, Domicilio domicilio, Character sexo, String nombreUsuario,
			Date fechaNacimiento, Boolean activo, Long matricula) {
		super(nroDocumento, tipoDocumento, nombre, apellido, listaTelefonos, listaMails, domicilio, sexo,
				nombreUsuario, fechaNacimiento, activo);
		this.matricula = matricula;
	}

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Alumno) {
			Alumno alumno = (Alumno) obj;
			if ((this.idUsuario.equals(alumno.idUsuario)) && (this.nroDocumento.equals(alumno.nroDocumento))
					&& (this.tipoDocumento.equals(alumno.tipoDocumento)) && (this.matricula.equals(alumno.matricula))) {
				return true;
			} 
		}
		return false;
	}

	@Override
	public int hashCode() {
		return idUsuario.hashCode();
	}	
	
}
