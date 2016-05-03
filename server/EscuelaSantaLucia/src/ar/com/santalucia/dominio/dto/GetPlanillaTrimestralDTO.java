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

	private int nroTrimestre;
	private String nombreAnio;
	private String curso;
	private int cicloLectivo;

	public GetPlanillaTrimestralDTO(int nroTrimestre, String nombreAnio, String divCurso, int cicloLectivo) {
		super();
		this.nroTrimestre = nroTrimestre;
		this.nombreAnio = nombreAnio;
		this.curso = divCurso;
		this.cicloLectivo = cicloLectivo;
	}

	public GetPlanillaTrimestralDTO() {
		super();
	}

	/**
	 * @return the nroTrimestre
	 */
	public int getNroTrimestre() {
		return nroTrimestre;
	}

	/**
	 * @param nroTrimestre
	 *            the nroTrimestre to set
	 */
	public void setNroTrimestre(int nroTrimestre) {
		this.nroTrimestre = nroTrimestre;
	}

	/**
	 * @return the nombreAnio
	 */
	public String getNombreAnio() {
		return nombreAnio;
	}

	/**
	 * @param nombreAnio
	 *            the nombreAnio to set
	 */
	public void setNombreAnio(String nombreAnio) {
		this.nombreAnio = nombreAnio;
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
