package ar.com.santalucia.dominio.dto;

/**
 * AlumnoDTO: maneja un alumno con su nro de documento, nombre y apellido y el curso al que pertenece
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class AlumnoDTO {

	private Long dniAlumno;
	private String nombreAlumno;
	private String apellidoAlumno;
	private String anio;
	private String curso;

	public AlumnoDTO() {
		super();
	}

	public AlumnoDTO(Long dniAlumno, String nombreAlumno, String apellidoAlumno, String anio, String curso) {
		super();
		this.dniAlumno = dniAlumno;
		this.nombreAlumno = nombreAlumno;
		this.apellidoAlumno = apellidoAlumno;
		this.setAnio(anio);
		this.curso = curso;
	}

	public Long getDniAlumno() {
		return dniAlumno;
	}

	public void setDniAlumno(Long dniAlumno) {
		this.dniAlumno = dniAlumno;
	}

	public String getNombreAlumno() {
		return nombreAlumno;
	}

	public void setNombreAlumno(String nombreAlumno) {
		this.nombreAlumno = nombreAlumno;
	}

	public String getApellidoAlumno() {
		return apellidoAlumno;
	}

	public void setApellidoAlumno(String apellidoAlumno) {
		this.apellidoAlumno = apellidoAlumno;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

}
