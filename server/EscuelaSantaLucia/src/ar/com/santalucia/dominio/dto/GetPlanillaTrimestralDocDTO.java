package ar.com.santalucia.dominio.dto;
/**
 * DTO usado para solicitar planilla trimestral para Docente
 * @author Ariel Ramírez
 *
 */
public class GetPlanillaTrimestralDocDTO {
	private Long idMateria;
	private Integer trimestre;
	private String anio;
	private String curso;
	private Integer cicloLectivo;
	
	public GetPlanillaTrimestralDocDTO() {
		super();
	}

	public GetPlanillaTrimestralDocDTO(Long idMateria, Integer trimestre,
			String anio, String curso, Integer cicloLectivo) {
		super();
		this.idMateria = idMateria;
		this.trimestre = trimestre;
		this.anio = anio;
		this.curso = curso;
		this.cicloLectivo = cicloLectivo;
	}

	public Long getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(Long idMateria) {
		this.idMateria = idMateria;
	}

	public Integer getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(Integer trimestre) {
		this.trimestre = trimestre;
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

	public Integer getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(Integer cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}
	
}
