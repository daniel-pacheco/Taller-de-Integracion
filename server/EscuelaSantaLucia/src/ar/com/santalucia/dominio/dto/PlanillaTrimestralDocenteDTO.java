package ar.com.santalucia.dominio.dto;

import java.util.List;

/**
 * Resultado a una petición mediante GetPlanillaTrimestralDocDTO 
 * @author Ariel Ramírez
 *
 */
public class PlanillaTrimestralDocenteDTO {
	private Long idDocenteTitular;
	private Long idDocenteSuplente;
	private String anio;
	private String curso;
	private Long idMateria;
	private Integer trimestre;
	private List<DetallePlanillaTrimestralDocenteDTO> planilla;
	
	public PlanillaTrimestralDocenteDTO() {
		super();
	}

	public PlanillaTrimestralDocenteDTO(Long idDocenteTitular,
			Long idDocenteSuplente, String anio, String curso, Long idMateria,
			Integer trimestre, List<DetallePlanillaTrimestralDocenteDTO> planilla) {
		super();
		this.idDocenteTitular = idDocenteTitular;
		this.idDocenteSuplente = idDocenteSuplente;
		this.anio = anio;
		this.curso = curso;
		this.idMateria = idMateria;
		this.trimestre = trimestre;
		this.planilla = planilla;
	}

	public Long getIdDocenteTitular() {
		return idDocenteTitular;
	}

	public void setIdDocenteTitular(Long idDocenteTitular) {
		this.idDocenteTitular = idDocenteTitular;
	}

	public Long getIdDocenteSuplente() {
		return idDocenteSuplente;
	}

	public void setIdDocenteSuplente(Long idDocenteSuplente) {
		this.idDocenteSuplente = idDocenteSuplente;
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

	public List<DetallePlanillaTrimestralDocenteDTO> getPlanilla() {
		return planilla;
	}

	public void setPlanilla(List<DetallePlanillaTrimestralDocenteDTO> planilla) {
		this.planilla = planilla;
	}
	
}
