package ar.com.santalucia.dominio.modelo.desempenio;

public class RegistroPasajeAlumnos {
	private Long idRPA;
	private Long idAlumno;
	private Long idAnioActual;
	private Long idCursoActual;
	private Long idCursoSiguiente;
	private Boolean procesado;
	
	public RegistroPasajeAlumnos() {
		super();
	}

	public RegistroPasajeAlumnos(Long idRPA, Long idAlumno, Long idAnioActual, Long idCursoActual, Long idCursoSiguiente, Boolean procesado) {
		super();
		this.idRPA = idRPA;
		this.idAlumno = idAlumno;
		this.idAnioActual = idAnioActual;
		this.idCursoActual = idCursoActual;
		this.idCursoSiguiente = idCursoSiguiente;
		this.procesado = procesado;
	}

	public Long getIdRPA() {
		return idRPA;
	}

	public void setIdRPA(Long idRPA) {
		this.idRPA = idRPA;
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

	public Long getIdCursoActual() {
		return idCursoActual;
	}

	public void setIdCursoActual(Long idCursoActual) {
		this.idCursoActual = idCursoActual;
	}

	public Long getIdCursoSiguiente() {
		return idCursoSiguiente;
	}

	public void setIdCursoSiguiente(Long idCursoSiguiente) {
		this.idCursoSiguiente = idCursoSiguiente;
	}

	
	public Boolean getProcesado() {
		return procesado;
	}

	public void setProcesado(Boolean procesado) {
		this.procesado = procesado;
	}
	
	
}
