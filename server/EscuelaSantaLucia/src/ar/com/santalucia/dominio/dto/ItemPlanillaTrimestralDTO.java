package ar.com.santalucia.dominio.dto;

import java.util.ArrayList;

/**
 * ItemPlanillaTrimestralDTO: contiene información de la planilla trimestral (grilla de doble entrada)
 * 
 * @author Eric
 * @version 1.0
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
