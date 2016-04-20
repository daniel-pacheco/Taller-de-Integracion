package ar.com.santalucia.dominio.dto;

import java.util.ArrayList;

/**
 * DocenteMateriasDTO: contiene información de un docente y las materias que dicta
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class DocenteMateriasDTO {
	private Long dniDocente;
	private String nombreDocente;
	private String apellidoDocente;
	private ArrayList<String> materias;

	public DocenteMateriasDTO() {
		super();
	}

	public DocenteMateriasDTO(Long dniDocente, String nombreDocente, String apellidoDocente,
			ArrayList<String> materias) {
		super();
		this.dniDocente = dniDocente;
		this.nombreDocente = nombreDocente;
		this.apellidoDocente = apellidoDocente;
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

	public ArrayList<String> getMaterias() {
		return materias;
	}

	public void setMaterias(ArrayList<String> materias) {
		this.materias = materias;
	}

}
