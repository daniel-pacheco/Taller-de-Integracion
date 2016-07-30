package ar.com.santalucia.dominio.modelo.usuarios;

import java.util.Date;
import java.util.Set;

import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;

/**
 * Clase Administrador
 * - Contiene un campo 'descipcion' para que Hibernate configure correctamente el mapeo.
 * 
 * @author EricDaniel
 * @version 1.2
 *
 */

//UltimoModificador: Eric Pennachini @ 16-08-15 16:20

public class Administrador extends Usuario {

	private String descripcion;

	public Administrador() {
		super();
	}

	public Administrador(Long nroDocumento, String tipoDocumento, String nombre, String apellido,
			Set<Telefono> listaTelefonos, Set<Mail> listaMails, Domicilio domicilio, char sexo, String nombreUsuario,
			Date fechaNacimiento, String observaciones, Boolean activo, String descripcion) {
		super(nroDocumento, tipoDocumento, nombre, apellido, listaTelefonos, listaMails, domicilio, sexo,
				nombreUsuario, fechaNacimiento, observaciones, activo);
		this.descripcion = descripcion;
	}

	public Administrador(String descripcion) {
		super();
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
