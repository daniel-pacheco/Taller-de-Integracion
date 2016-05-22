package ar.com.santalucia.dominio.dto;

import ar.com.santalucia.dominio.modelo.academico.Area;

/**
 * Este DTO permite dar de alta una nueva materia, con su correspondiente año y área
 * 
 * @author Ariel Ramirez
 * 
 * @version 1.0
 */

public class MateriaAltaDTO {
	private Long idMateria;
	private String nombreMateria;
	private String descripcion;
	private Long idDocenteTitular;
	private Long idDocenteSuplente;
	private Area area;
	private Long idAnio;
	private Boolean activo;
	
	public MateriaAltaDTO() {
		super();
	}

	
	public MateriaAltaDTO(Long idMateria, String nombreMateria, String descripcion, Long idDocenteTitular,
			Long idDocenteSuplente, Area area, Long idAnio) {
		super();
		this.idMateria = idMateria;
		this.nombreMateria = nombreMateria;
		this.descripcion = descripcion;
		this.setIdDocenteTitular(idDocenteTitular);
		this.setIdDocenteSuplente(idDocenteSuplente);
		this.area = area;
		this.setIdAnio(idAnio);
	}

	public String getNombreMateria() {
		return nombreMateria;
	}

	public Long getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(Long idMateria) {
		this.idMateria = idMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	

	public Long getIdDocenteTitular() {
		return idDocenteTitular;
	}



	public void setIdDocenteTitular(Long idDocenteTitular) {
		this.idDocenteTitular = idDocenteTitular;
	}



	public Long getIdDocenteSuplente() {
		return idDocenteSuplente;
	}



	public void setIdDocenteSuplente(Long idDocenteSuplente) {
		this.idDocenteSuplente = idDocenteSuplente;
	}



	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}


	public Long getIdAnio() {
		return idAnio;
	}


	public void setIdAnio(Long idAnio) {
		this.idAnio = idAnio;
	}


	public Boolean getActivo() {
		return activo;
	}


	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	
}

