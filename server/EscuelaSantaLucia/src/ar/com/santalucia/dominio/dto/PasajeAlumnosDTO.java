package ar.com.santalucia.dominio.dto;

/**
 * PasajeAlumnosDTO: contiene la información básica de un alumno con la nota
 * final y nota definitiva del ciclo lectivo actual, y la cantidad de materias
 * previas de ese alumno
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class PasajeAlumnosDTO {

	private Long idUsuario;
	private Long dniAlumno;
	private String nombre;
	private String apellido;
	private Float notaFinal;
	private Float notaDefinitiva;
	private Integer cantPrevias;
	private Boolean habilitadoPromocion;

	public PasajeAlumnosDTO() {
		super();
	}

	public PasajeAlumnosDTO(Long idUsuario, Long dniAlumno, String nombre, String apellido, Float notaFinal,
			Float notaDefinitiva, Integer cantPrevias, Boolean habilitadoPromocion) {
		super();
		this.idUsuario = idUsuario;
		this.dniAlumno = dniAlumno;
		this.nombre = nombre;
		this.apellido = apellido;
		this.notaFinal = notaFinal;
		this.notaDefinitiva = notaDefinitiva;
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

	public Float getNotaFinal() {
		return notaFinal;
	}

	public void setNotaFinal(Float notaFinal) {
		this.notaFinal = notaFinal;
	}

	public Float getNotaDefinitiva() {
		return notaDefinitiva;
	}

	public void setNotaDefinitiva(Float notaDefinitiva) {
		this.notaDefinitiva = notaDefinitiva;
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

}