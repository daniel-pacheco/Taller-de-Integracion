package ar.com.santalucia.dominio.modelo.academico;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Eric 
 * @version 1.2
 *
*/

//Último modificador: Eric Pennachini @ 05-11-15 18:46

public class Anio {
	private Long idAnio;
	private String nombre;
	private String descripcion;
	private Set<Curso> listaCursos;
	private Set<Materia> listaMaterias;
	private Boolean activo;

	public Anio() {
		super();
		listaCursos = new HashSet<Curso>();
		listaMaterias = new HashSet<Materia>();
	}

	public Anio(Long idAnio, String nombre, String descripcion, Set<Curso> listaCursos, Set<Materia> listaMaterias,
			Boolean activo) {
		super();
		this.idAnio = idAnio;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.setListaCursos(listaCursos);
		this.setListaMaterias(listaMaterias);
		this.activo = activo;
	}

	public Long getIdAnio() {
		return idAnio;
	}

	public void setIdAño(Long idAnio) {
		this.idAnio = idAnio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Curso> getListaCursos() {
		return listaCursos;
	}

	public void setListaCursos(Set<Curso> listaCursos) {
		this.listaCursos = listaCursos;
	}

	public Set<Materia> getListaMaterias() {
		return listaMaterias;
	}

	public void setListaMaterias(Set<Materia> listaMaterias) {
		this.listaMaterias = listaMaterias;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

}
