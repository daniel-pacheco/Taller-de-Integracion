package ar.com.santalucia.dominio.dto;

import java.util.Date;

/**
 * DetallePreviaDTO proporciona un objeto simplificado para devolver al frontend
 * información resumida de materias previas.
 * 
 * @author Ariel
 * @version 1.0
 */
public class DetallePreviaDTO implements Comparable<DetallePreviaDTO> {
	private Long idDetallePrevia;
	private String nombreMateria;
	private Long dniAlumno;
	private Integer cicloLectivoMateria;
	private String anio;
	private Boolean asistencia;
	private Date fechaInscripcion; 
	private Float nota;
	
	public DetallePreviaDTO() {
		super();
	}

	public DetallePreviaDTO(Long idDetallePrevia, String nombreMateria, Long dniAlumno, Integer cicloLectivoMateria,
			String anio, Boolean asistencia, Date fechaInscripcion, Float nota) {
		super();
		this.idDetallePrevia = idDetallePrevia;
		this.nombreMateria = nombreMateria;
		this.dniAlumno = dniAlumno;
		this.cicloLectivoMateria = cicloLectivoMateria;
		this.anio = anio;
		this.asistencia = asistencia;
		this.fechaInscripcion = fechaInscripcion;
		this.nota = nota;
	}

	public Long getIdDetallePrevia() {
		return idDetallePrevia;
	}

	public void setIdDetallePrevia(Long idDetallePrevia) {
		this.idDetallePrevia = idDetallePrevia;
	}

	public String getNombreMateria() {
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	public Long getDniAlumno() {
		return dniAlumno;
	}

	public void setDniAlumno(Long dniAlumno) {
		this.dniAlumno = dniAlumno;
	}

	public Integer getCicloLectivoMateria() {
		return cicloLectivoMateria;
	}

	public void setCicloLectivoMateria(Integer cicloLectivoMateria) {
		this.cicloLectivoMateria = cicloLectivoMateria;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public Boolean getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(Boolean asistencia) {
		this.asistencia = asistencia;
	}

	public Date getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(Date fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public Float getNota() {
		return nota;
	}

	public void setNota(Float nota) {
		this.nota = nota;
	}

	@Override
	public boolean equals(Object objeto) {
		if(objeto instanceof DetallePreviaDTO){
			if ((this.nombreMateria).equals(((DetallePreviaDTO) objeto).getNombreMateria()) &&
			(this.anio).equals(((DetallePreviaDTO) objeto).getAnio()) ){
				return true;     
			}
		}
		return false;
	}

	@Override
	public int compareTo(DetallePreviaDTO objeto) {
		if(objeto instanceof DetallePreviaDTO && this.equals(objeto)){ // Que sean el mismo tipo y se cumpla lo de equals
			if( (this.fechaInscripcion).equals(((DetallePreviaDTO)objeto).getFechaInscripcion()) ){
				return 0; // Si es igual
			}else{
				if( (this.fechaInscripcion).before( ((DetallePreviaDTO)objeto).getFechaInscripcion() )  ){
					return 1; // Si es mayor
				}else{
					return -1; // Si es menor
				}
			}
		}
		return -2; // No fue posible comparar los objetos
	}

	@Override
	public int hashCode() {
		return (nombreMateria+anio).hashCode();
	}
	
	
}
