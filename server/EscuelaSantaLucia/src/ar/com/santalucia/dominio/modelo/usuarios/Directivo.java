package ar.com.santalucia.dominio.modelo.usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;

/**
 * Clase Directivo
 * 
 * @author EricDaniel
 * @version 1.3
 *
 */

//UltimoModificador: Eric Pennachini @ 19-09-15 16:07

public class Directivo extends Usuario {

	private Set<Titulo> listaTitulos;
	private Long cuil;

	public Directivo() {
		super();
		listaTitulos = new HashSet<Titulo>();
	}

	public Directivo(Long nroDocumento, String tipoDocumento, String nombre, String apellido,
			Set<Telefono> listaTelefonos, Set<Mail> listaMails, Domicilio domicilio, char sexo, String nombreUsuario,
			Date fechaNacimiento, Boolean activo, Set<Titulo> listaTitulos, Long cuil) {
		super(nroDocumento, tipoDocumento, nombre, apellido, listaTelefonos, listaMails, domicilio, sexo,
				nombreUsuario, fechaNacimiento, activo);
		this.setListaTitulos(listaTitulos);
		this.cuil = cuil;
	}

	public Set<Titulo> getListaTitulos() {
		return listaTitulos;
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

}
