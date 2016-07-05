package ar.com.santalucia.dominio.dto;

import java.util.Date;

public class InscripcionConsultaDTO {
	private Long idMesa;
	private String alumno;
	private String materia;
	private String fecha;
	private String hora;
	private String tribunal;
	private Boolean inscripto;
	
	public InscripcionConsultaDTO() {
		super();
	}

	public InscripcionConsultaDTO(Long idMesa, String alumno, String materia, String fecha, String hora, String tribunal,
			Boolean inscripto) {
		super();
		this.idMesa = idMesa;
		this.alumno = alumno;
		this.materia = materia;
		this.fecha = fecha;
		this.hora = hora;
		this.tribunal = tribunal;
		this.inscripto = inscripto;
	}

	public Long getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(Long idMesa) {
		this.idMesa = idMesa;
	}

	public String getAlumno() {
		return alumno;
	}

	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getTribunal() {
		return tribunal;
	}

	public void setTribunal(String tribunal) {
		this.tribunal = tribunal;
	}

	public Boolean getInscripto() {
		return inscripto;
	}

	public void setInscripto(Boolean inscripto) {
		this.inscripto = inscripto;
	}
	
}
