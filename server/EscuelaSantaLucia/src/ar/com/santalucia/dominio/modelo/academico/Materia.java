package ar.com.santalucia.dominio.modelo.academico;

import ar.com.santalucia.dominio.modelo.usuarios.Docente;

/**
 * Clase Materia
 * @author Ariel Ramirez
 * @version 2.2
 * 
 */

// Ultimo modificador: Eric Pennachini @ 05-11-2015 18:46

public class Materia {
	private Long idMateria;
	private String nombre;
	private String descripcion;
	private Docente docenteTitular;
	private Docente docenteSuplente;
	private Area area;
	private Boolean activo;
	
	public Materia() {
		super();
	}

	public Materia(Long idMateria, String nombre, String descripcion, Docente docenteTitular, Docente docenteSuplente,
			Area area, Boolean activo) {
		super();
		this.idMateria = idMateria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.docenteTitular = docenteTitular;
		this.docenteSuplente = docenteSuplente;
		this.area = area;
		this.activo = activo;
	}

	public Long getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(Long idMateria) {
		this.idMateria = idMateria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Docente getDocenteTitular() {
		return docenteTitular;
	}

	public void setDocenteTitular(Docente docenteTitular) {
		this.docenteTitular = docenteTitular;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Docente getDocenteSuplente() {
		return docenteSuplente;
	}

	public void setDocenteSuplente(Docente docenteSuplente) {
		this.docenteSuplente = docenteSuplente;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
}
