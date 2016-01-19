package ar.com.santalucia.dominio.modelo.academico;

import java.util.Date;

import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

/**
 * 
 * @author Ariel Ramirez
 *
 * @version 1.0
 */
public class Inscripcion {
	
	private Long idInscripcion;
	private Mesa mesa;
	private Date fecha;
	private Alumno alumno;
	private Boolean asistencia;
	//private Boolean activo;
	//private Long codigoLlamado;
	//private Long codigoTransaccion;
	
	public Inscripcion() {
		super();
	}

	public Inscripcion(Long idInscripcion, Mesa mesa, Date fecha, Alumno alumno, Boolean asistencia) {
		super();
		this.idInscripcion = idInscripcion;
		this.mesa = mesa;
		this.fecha = fecha;
		this.alumno = alumno;
		this.asistencia = asistencia;
	}

	public Long getIdInscripcion() {
		return idInscripcion;
	}

	public void setIdInscripcion(Long idInscripcion) {
		this.idInscripcion = idInscripcion;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Boolean getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(Boolean asistencia) {
		this.asistencia = asistencia;
	}
	
}
