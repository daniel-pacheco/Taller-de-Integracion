package ar.com.santalucia.dominio.dto;

import java.util.ArrayList;

/**
 * ListaPasajeAlumnosDTO: contiene la lista de PasajeAlumnosDTO de un anio y curso especificado
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class ListaPasajeAlumnosDTO {

	private String anio;
	private String curso;
	private ArrayList<PasajeAlumnosDTO> listaPasajeAlumnosDTO;

	public ListaPasajeAlumnosDTO() {
		super();
		listaPasajeAlumnosDTO = new ArrayList<PasajeAlumnosDTO>();
	}

	public ListaPasajeAlumnosDTO(String anio, String curso, ArrayList<PasajeAlumnosDTO> listaPasajeAlumnosDTO) {
		super();
		this.anio = anio;
		this.curso = curso;
		this.listaPasajeAlumnosDTO = listaPasajeAlumnosDTO;
	}

	/**
	 * @return the anio
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * @param anio
	 *            the anio to set
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}

	/**
	 * @return the curso
	 */
	public String getCurso() {
		return curso;
	}

	/**
	 * @param curso
	 *            the curso to set
	 */
	public void setCurso(String curso) {
		this.curso = curso;
	}

	public ArrayList<PasajeAlumnosDTO> getListaPasajeAlumnosDTO() {
		return listaPasajeAlumnosDTO;
	}

	public void setListaPasajeAlumnosDTO(ArrayList<PasajeAlumnosDTO> listaPasajeAlumnosDTO) {
		this.listaPasajeAlumnosDTO = listaPasajeAlumnosDTO;
	}

}
