package ar.com.santalucia.dominio.dto;

import java.util.Date;

public class InscripcionConsultaDTO {
	private Long idMesa;
	private String alumno;
	private String materia;
	private String anioMateria;
	private String descripcionMateria;
	private String fecha;
	private String horaInicio;
	private String horaFin;
	private String tribunal;
	private Boolean inscripto;
	
	public InscripcionConsultaDTO() {
		super();
	}

	public InscripcionConsultaDTO(Long idMesa, String alumno, String materia, String anioMateria, String descripcionMateria, String fecha, String horaInicio, String horaFin, String tribunal,
			Boolean inscripto) {
		super();
		this.idMesa = idMesa;
		this.alumno = alumno;
		this.materia = materia;
		this.setAnioMateria(anioMateria);
		this.setDescripcionMateria(descripcionMateria);
		this.fecha = fecha;
		this.setHoraInicio(horaInicio);
		this.setHoraFin(horaFin);
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

	public String getAnioMateria() {
		return anioMateria;
	}

	public void setAnioMateria(String anioMateria) {
		this.anioMateria = anioMateria;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getDescripcionMateria() {
		return descripcionMateria;
	}

	public void setDescripcionMateria(String descripcionMateria) {
		this.descripcionMateria = descripcionMateria;
	}
	
}
