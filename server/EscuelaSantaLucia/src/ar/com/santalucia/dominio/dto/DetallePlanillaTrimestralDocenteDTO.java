package ar.com.santalucia.dominio.dto;

public class DetallePlanillaTrimestralDocenteDTO {
	private String alumno;
	private Float nota;
	public String getAlumno() {
		return alumno;
	}
	
	public DetallePlanillaTrimestralDocenteDTO() {
		super();
	}

	public DetallePlanillaTrimestralDocenteDTO(String alumno, Float nota) {
		super();
		this.alumno = alumno;
		this.nota = nota;
	}

	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}
	public Float getNota() {
		return nota;
	}
	public void setNota(Float nota) {
		this.nota = nota;
	}
	
	
}
