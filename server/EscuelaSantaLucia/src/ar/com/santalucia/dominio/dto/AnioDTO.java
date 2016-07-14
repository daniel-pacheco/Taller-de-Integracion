package ar.com.santalucia.dominio.dto;

import java.util.ArrayList;

/**
 * AnioDTO: maneja información reducida de un año y la lista de cursos
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class AnioDTO {

	private Long idAnio;
	private String nombre;
	private String descripcion;
	private String especialidad;
	private Integer orden;
	private String cicloLectivo;
	private Boolean activo;
	private ArrayList<CursoDTO> listaCursos;

	public AnioDTO() {
		super();
		this.listaCursos = new ArrayList<CursoDTO>();
	}

	public AnioDTO(Long idAnio, String nombre, String descripcion, String especialidad, Integer orden, ArrayList<CursoDTO> listaCursos, String cicloLectivo, Boolean activo) {
		super();
		this.idAnio = idAnio;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.especialidad = especialidad;
		this.orden = orden;
		this.cicloLectivo = cicloLectivo;
		this.activo = activo;
		this.listaCursos = listaCursos;
	}

	/**
	 * @return the idAnio
	 */
	public Long getIdAnio() {
		return idAnio;
	}

	/**
	 * @param idAnio
	 *            the idAnio to set
	 */
	public void setIdAnio(Long idAnio) {
		this.idAnio = idAnio;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the listaCursos
	 */
	public ArrayList<CursoDTO> getListaCursos() {
		return listaCursos;
	}

	/**
	 * @param listaCursos
	 *            the listaCursos to set
	 */
	public void setListaCursos(ArrayList<CursoDTO> listaCursos) {
		this.listaCursos = listaCursos;
	}

	public String getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(String cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

}
