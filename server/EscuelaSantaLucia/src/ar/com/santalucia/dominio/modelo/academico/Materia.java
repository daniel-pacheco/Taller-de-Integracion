package ar.com.santalucia.dominio.modelo.academico;

import ar.com.santalucia.dominio.modelo.usuarios.Docente;

/**
 * Clase Materia
 * @author Ariel Ramirez
 * @version 2.0
 * 
 */

// Ultimo modificador: Eric Pennachini @ 23-10-2015 17:47

public class Materia {
	private Long idMateria;
	private String nombre;
	private Docente docenteTitular;
	private String tipoDocente;
	private Area area;
	
	public Materia() {
		super();
	}	

	public Materia(Long idMateria, String nombre, Docente docenteTitular, String tipoDocente, Area area) {
		super();
		this.idMateria = idMateria;
		this.nombre = nombre;
		this.docenteTitular = docenteTitular;
		this.tipoDocente = tipoDocente;
		this.area = area;
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

	public String getTipoDocente() {
		return tipoDocente;
	}

	public void setTipoDocente(String tipoDocente) {
		this.tipoDocente = tipoDocente;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
}
