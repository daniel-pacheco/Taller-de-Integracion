package ar.com.santalucia.dominio.dto;

/**
 * AlumnoDTO: maneja un alumno con su nro de documento, nombre y apellido y el curso al que pertenece
 * 
 * @author Eric
 * @version 1.0
 *
 */

// Último modificador: Ariel Ramirez @ 30-05-2016 22:01

public class AlumnoDTO {

	private Long nroDocumento;
	private String nombre;
	private String apellido;
	private String anio;
	private String curso;

	public AlumnoDTO() {
		super();
	}

	public AlumnoDTO(Long nroDocumento, String nombre, String apellido, String anio, String curso) {
		super();
		this.nroDocumento = nroDocumento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.setAnio(anio);
		this.curso = curso;
	}

	public Long getDniAlumno() {
		return nroDocumento;
	}

	public void setDniAlumno(Long nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getNombreAlumno() {
		return nombre;
	}

	public void setNombreAlumno(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoAlumno() {
		return apellido;
	}

	public void setApellidoAlumno(String apellido) {
		this.apellido = apellido;
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
