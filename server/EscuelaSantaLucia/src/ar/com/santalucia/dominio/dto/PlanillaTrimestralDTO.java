package ar.com.santalucia.dominio.dto;

import java.util.ArrayList;

/**
 * PlanillaTrimestralDTO: contiene información de la planilla trimestral (grilla
 * de doble entrada)
 * 
 * @author Eric
 * @version 1.1
 *
 */
public class PlanillaTrimestralDTO {

	private Integer trimestre;
	private String anio;
	private String curso;
	private ArrayList<ItemPlanillaTrimestralDTO> planilla;

	public PlanillaTrimestralDTO() {
		super();
		this.planilla = new ArrayList<ItemPlanillaTrimestralDTO>();
	}

	public PlanillaTrimestralDTO(Integer trimestre, ArrayList<ItemPlanillaTrimestralDTO> filas) {
		super();
		this.trimestre = trimestre;
		this.planilla = filas;
	}

	/**
	 * @return the trimestre
	 */
	public Integer getTrimestre() {
		return trimestre;
	}

	/**
	 * @param trimestre
	 *            the trimestre to set
	 */
	public void setTrimestre(Integer trimestre) {
		this.trimestre = trimestre;
	}

	/**
	 * @return the filas
	 */
	public ArrayList<ItemPlanillaTrimestralDTO> getPlanilla() {
		return planilla;
	}

	/**
	 * @param filas
	 *            the filas to set
	 */
	public void setPlanilla(ArrayList<ItemPlanillaTrimestralDTO> planilla) {
		this.planilla = planilla;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

}
