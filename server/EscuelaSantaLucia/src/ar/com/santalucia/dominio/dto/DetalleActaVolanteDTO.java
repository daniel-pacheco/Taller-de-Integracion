package ar.com.santalucia.dominio.dto;

/**
 * Este DTO representa el detalle de la entidad ActaVolanteExamenes
 * @author Ariel
 *
 */
public class DetalleActaVolanteDTO {
	private Long idDetalleVolante;
	private String alumno;
	private Boolean asistencia;
	private Float nota;
	
	public DetalleActaVolanteDTO() {
		super();
	}

	public DetalleActaVolanteDTO(Long idDetalleVolante, String alumno, Boolean asistencia, Float nota) {
		super();
		this.idDetalleVolante = idDetalleVolante;
		this.alumno = alumno;
		this.asistencia = asistencia;
		this.nota = nota;
	}

	public Long getIdDetalleVolante() {
		return idDetalleVolante;
	}

	public void setIdDetalleVolante(Long idDetalleVolante) {
		this.idDetalleVolante = idDetalleVolante;
	}

	public String getAlumno() {
		return alumno;
	}

	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}

	public Boolean getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(Boolean asistencia) {
		this.asistencia = asistencia;
	}

	public Float getNota() {
		return nota;
	}

	public void setNota(Float nota) {
		this.nota = nota;
	}
	
}
