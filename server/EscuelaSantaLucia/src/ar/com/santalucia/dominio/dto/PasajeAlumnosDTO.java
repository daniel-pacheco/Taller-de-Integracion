package ar.com.santalucia.dominio.dto;

/**
 * PasajeAlumnosDTO: contiene la información básica de un alumno con la nota
 * final y nota definitiva del ciclo lectivo actual, y la cantidad de materias
 * previas de ese alumno
 * 
 * @author Eric
 * @version 1.2
 *
 */

// Último modificador: Ariel Ramirez @ 14/07/2016 19:50

public class PasajeAlumnosDTO {

	private Long idUsuario;              // Salida y entrada inmutable
	private Long dniAlumno;              // Salida
	private String nombre;               // Salida
	private String apellido;             // Salida
	private Long idAnioActual;           // Entrada y salida inmutable
	private String anioActual;           // Salida
	private String cursoActual;          // Salida
	private Long idAnioSiguiente;        // Salida
	private Long idCursoSiguiente;       // Sugerencia y entrada
	private Integer cantPrevias;         // Salida
	private Boolean habilitadoPromocion; // Salida

	public PasajeAlumnosDTO() {
		super();
	}
	
	
	public PasajeAlumnosDTO(Long idUsuario, Long dniAlumno, String nombre, String apellido, Long idAnioActual,
			String anioActual, String cursoActual, Long idAnioSiguiente, Long idCursoSiguiente, Integer cantPrevias,
			Boolean habilitadoPromocion) {
		super();
		this.idUsuario = idUsuario;
		this.dniAlumno = dniAlumno;
		this.nombre = nombre;
		this.apellido = apellido;
		this.setIdAnioActual(idAnioActual);
		this.setAnioActual(anioActual);
		this.setCursoActual(cursoActual);
		this.setIdAnioSiguiente(idAnioSiguiente);
		this.setIdCursoSiguiente(idCursoSiguiente);
		this.cantPrevias = cantPrevias;
		this.habilitadoPromocion = habilitadoPromocion;
	}


	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getDniAlumno() {
		return dniAlumno;
	}

	public void setDniAlumno(Long dniAlumno) {
		this.dniAlumno = dniAlumno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Integer getCantPrevias() {
		return cantPrevias;
	}

	public void setCantPrevias(Integer cantPrevias) {
		this.cantPrevias = cantPrevias;
	}

	public Boolean getHabilitadoPromocion() {
		return habilitadoPromocion;
	}

	public void setHabilitadoPromocion(Boolean habilitadoPromocion) {
		this.habilitadoPromocion = habilitadoPromocion;
	}


	public Long getIdAnioActual() {
		return idAnioActual;
	}


	public void setIdAnioActual(Long idAnioActual) {
		this.idAnioActual = idAnioActual;
	}


	public String getAnioActual() {
		return anioActual;
	}


	public void setAnioActual(String anioActual) {
		this.anioActual = anioActual;
	}


	public String getCursoActual() {
		return cursoActual;
	}


	public void setCursoActual(String cursoActual) {
		this.cursoActual = cursoActual;
	}


	public Long getIdAnioSiguiente() {
		return idAnioSiguiente;
	}


	public void setIdAnioSiguiente(Long idAnioSiguiente) {
		this.idAnioSiguiente = idAnioSiguiente;
	}


	public Long getIdCursoSiguiente() {
		return idCursoSiguiente;
	}


	public void setIdCursoSiguiente(Long idCursoSiguiente) {
		this.idCursoSiguiente = idCursoSiguiente;
	}

}