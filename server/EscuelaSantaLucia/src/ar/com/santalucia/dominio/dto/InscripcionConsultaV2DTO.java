package ar.com.santalucia.dominio.dto;

import java.util.List;

public class InscripcionConsultaV2DTO {
	private Long idAlumno;
	private String alumno;
	private List<InscripcionConsultaV2Detalle> detalle;
	
	public InscripcionConsultaV2DTO() {
		super();
	}

	public InscripcionConsultaV2DTO(Long idAlumno, String alumno, List<InscripcionConsultaV2Detalle> detalle) {
		super();
		this.idAlumno = idAlumno;
		this.alumno = alumno;
		this.detalle = detalle;
	}

	public Long getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(Long idAlumno) {
		this.idAlumno = idAlumno;
	}

	public String getAlumno() {
		return alumno;
	}

	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}

	public List<InscripcionConsultaV2Detalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<InscripcionConsultaV2Detalle> detalle) {
		this.detalle = detalle;
	}
	
}
