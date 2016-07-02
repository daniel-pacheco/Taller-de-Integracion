package ar.com.santalucia.dominio.dto;

/**
 * GetPlanillaTrimestralDTO: contiene información sobre los datos que se mandan desde el endpoint 
 * para obtener la planilla trimestral
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class GetPlanillaTrimestralDTO {

	private int trimestre;
	private String anio;
	private String curso;
	private int cicloLectivo;

	public GetPlanillaTrimestralDTO(int trimestre, String anio, String curso, int cicloLectivo) {
		super();
		this.trimestre = trimestre;
		this.anio = anio;
		this.curso = curso;
		this.cicloLectivo = cicloLectivo;
	}

	public GetPlanillaTrimestralDTO() {
		super();
	}

	/**
	 * @return the trimestre
	 */
	public int getTrimestre() {
		return trimestre;
	}

	/**
	 * @param trimestre
	 *            the trimestre to set
	 */
	public void setTrimestre(int trimestre) {
		this.trimestre = trimestre;
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
	public void setCurso(String divCurso) {
		this.curso = divCurso;
	}

	/**
	 * @return the cicloLectivo
	 */
	public int getCicloLectivo() {
		return cicloLectivo;
	}

	/**
	 * @param cicloLectivo
	 *            the cicloLectivo to set
	 */
	public void setCicloLectivo(int cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

}
