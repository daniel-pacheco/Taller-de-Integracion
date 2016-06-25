package ar.com.santalucia.dominio.dto;

import java.util.ArrayList;

/**
 * ItemPlanillaTrimestralDTO: contiene información de las filas de la planilla trimestral
 * 
 * @author Eric
 * @version 1.1
 *
 */
public class ItemPlanillaTrimestralDTO {

	private String alumno;
	private ArrayList<MateriaNotaDTO> notas;

	public ItemPlanillaTrimestralDTO() {
		super();
		notas = new ArrayList<MateriaNotaDTO>();
	}

	public ItemPlanillaTrimestralDTO(String alumno, ArrayList<MateriaNotaDTO> notas) {
		super();
		this.alumno = alumno;
		this.notas = notas;
	}

	/**
	 * @return the alumno
	 */
	public String getAlumno() {
		return alumno;
	}

	/**
	 * @param alumno
	 *            the alumno to set
	 */
	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}

	/**
	 * @return the notas
	 */
	public ArrayList<MateriaNotaDTO> getNotas() {
		return notas;
	}

	/**
	 * @param notas
	 *            the notas to set
	 */
	public void setNotas(ArrayList<MateriaNotaDTO> notas) {
		this.notas = notas;
	}

}
