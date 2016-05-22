package ar.com.santalucia.dominio.modelo.academico;

import ar.com.santalucia.dominio.modelo.usuarios.Personal;

/**
 * Clase Materia
 * @author Ariel Ramirez
 * @version 2.2
 * 
 */

// Ultimo modificador: Ariel Ramirez @ 22-05-2016 0:48

public class Materia {
	private Long idMateria;
	private String nombre;
	private String descripcion;
	private Personal docenteTitular;
	private Personal docenteSuplente;
	private Area area;
	private Boolean activo;
	
	public Materia() {
		super();
	}

	public Materia(Long idMateria, String nombre, String descripcion, Personal docenteTitular, Personal docenteSuplente,
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

	public Personal getDocenteTitular() {
		return docenteTitular;
	}

	public void setDocenteTitular(Personal docenteTitular) {
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

	public Personal getDocenteSuplente() {
		return docenteSuplente;
	}

	public void setDocenteSuplente(Personal docenteSuplente) {
		this.docenteSuplente = docenteSuplente;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	
	@Override
	public boolean equals(Object materia) {
		if(materia instanceof Materia){
			if(this.idMateria.equals(((Materia) materia).getIdMateria())){
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.idMateria.hashCode();
	}
	
	
	
}
