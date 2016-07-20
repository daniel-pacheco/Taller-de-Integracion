package ar.com.santalucia.dominio.dto;

public class PasajeEntradaDTO {
	private Long idAlumno;
	private Long idAnioActual;
	private Long idCursoSiguiente;
	
	public PasajeEntradaDTO() {
		super();
	}

	public PasajeEntradaDTO(Long idAlumno, Long idAnioActual, Long idCursoSiguiente) {
		super();
		this.idAlumno = idAlumno;
		this.idAnioActual = idAnioActual;
		this.idCursoSiguiente = idCursoSiguiente;
	}

	public Long getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(Long idAlumno) {
		this.idAlumno = idAlumno;
	}

	public Long getIdAnioActual() {
		return idAnioActual;
	}

	public void setIdAnioActual(Long idAnioActual) {
		this.idAnioActual = idAnioActual;
	}

	public Long getIdCursoSiguiente() {
		return idCursoSiguiente;
	}

	public void setIdCursoSiguiente(Long idCursoSiguiente) {
		this.idCursoSiguiente = idCursoSiguiente;
	}
	
}
