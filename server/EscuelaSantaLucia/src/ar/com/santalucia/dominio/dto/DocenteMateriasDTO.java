package ar.com.santalucia.dominio.dto;

import java.util.ArrayList;

/**
 * DocenteMateriasDTO: contiene informaci�n de un docente con los a�os en que
 * dicta materias las materias que dicta.
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class DocenteMateriasDTO {
	private Long dniDocente;
	private String nombreDocente;
	private String apellidoDocente;
	private ArrayList<String> anios;
	//private ArrayList<String> areas;
	private ArrayList<MateriaAreaCondDocenteDTO> materias;

	public DocenteMateriasDTO() {
		super();
	}

	public DocenteMateriasDTO(Long dniDocente, String nombreDocente, String apellidoDocente, ArrayList<String> anios,
			/*ArrayList<String> areas,*/ArrayList<MateriaAreaCondDocenteDTO> materias) {
		super();
		this.dniDocente = dniDocente;
		this.nombreDocente = nombreDocente;
		this.apellidoDocente = apellidoDocente;
		this.anios = anios;
		//this.areas = areas;
		this.materias = materias;
	}

	public Long getDniDocente() {
		return dniDocente;
	}

	public void setDniDocente(Long dniDocente) {
		this.dniDocente = dniDocente;
	}

	public String getNombreDocente() {
		return nombreDocente;
	}

	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}

	public String getApellidoDocente() {
		return apellidoDocente;
	}

	public void setApellidoDocente(String apellidoDocente) {
		this.apellidoDocente = apellidoDocente;
	}

	public ArrayList<String> getAnios() {
		return anios;
	}

	public void setAnios(ArrayList<String> anios) {
		this.anios = anios;
	}
	/*
	public ArrayList<String> getAreas() {
		return areas;
	}

	public void setAreas(ArrayList<String> areas) {
		this.areas = areas;
	}
	*/
	public ArrayList<MateriaAreaCondDocenteDTO> getMaterias() {
		return materias;
	}

	public void setMaterias(ArrayList<MateriaAreaCondDocenteDTO> materias) {
		this.materias = materias;
	}

}
