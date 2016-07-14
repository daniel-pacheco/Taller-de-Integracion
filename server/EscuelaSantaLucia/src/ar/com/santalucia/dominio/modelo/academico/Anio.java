package ar.com.santalucia.dominio.modelo.academico;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Eric 
 * @version 1.2
 *
*/

//Último modificador: Ariel Ramírez @ 12-07-2016 19:30

public class Anio {
	private Long idAnio;
	private String nombre;
	private String descripcion;
	private Especialidad especialidad;
	private Integer orden;
	private Set<Curso> listaCursos;
	private Set<Materia> listaMaterias;
	private String cicloLectivo;
	private Boolean activo;

	public Anio() {
		super();
		listaCursos = new HashSet<Curso>();
		listaMaterias = new HashSet<Materia>();
	}

	public Anio(Long idAnio, String nombre, String descripcion, Especialidad especialidad, Integer orden,
			Set<Curso> listaCursos, Set<Materia> listaMaterias, String cicloLectivo, Boolean activo) {
		super();
		this.idAnio = idAnio;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.especialidad = especialidad;
		this.orden = orden;
		this.setListaCursos(listaCursos);
		this.setListaMaterias(listaMaterias);
		this.cicloLectivo = cicloLectivo;
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

	public String getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(String cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

}
