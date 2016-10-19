package ar.com.santalucia.dominio.dto;

import java.util.Date;

/**
 * ComprobanteInscripcionMesaDTO: devuelve la información para el comprobante de inscripcion del alumno.
 * 
 * @author Ariel
 * @version 1.0
 */

public class ComprobanteInscripcionMesaDTO {
	private Date fechaHoraActual;
	private Long nroTransaccion;
	private String nombreAlumno;
	private String apellidoAlumno;
	private Long dniAlumno;
	private String periodo;
	private String anio;
	private String materia;
	private Date fechaHoraInicioMesa;
	private Date fechaHoraFinMesa;

	public ComprobanteInscripcionMesaDTO() {
		super();
	}

	public ComprobanteInscripcionMesaDTO(Date fechaHoraActual, Long nroTransaccion, String nombreAlumno,
			String apellidoAlumno, Long dniAlumno, String periodo, String anio, String materia, 
			Date fechaHoraInicioMesa, Date fechaHoraFinMesa) {
		super();
		this.fechaHoraActual = fechaHoraActual;
		this.nroTransaccion = nroTransaccion;
		this.nombreAlumno = nombreAlumno;
		this.apellidoAlumno = apellidoAlumno;
		this.dniAlumno = dniAlumno;
		this.periodo = periodo;
		this.anio = anio;
		this.materia = materia;
		this.fechaHoraInicioMesa = fechaHoraInicioMesa;
		this.fechaHoraFinMesa = fechaHoraFinMesa;
	}

	public Date getFechaHoraActual() {
		return fechaHoraActual;
	}

	public void setFechaHoraActual(Date fechaHoraActual) {
		this.fechaHoraActual = fechaHoraActual;
	}

	public Long getNroTransaccion() {
		return nroTransaccion;
	}

	public void setNroTransaccion(Long nroTransaccion) {
		this.nroTransaccion = nroTransaccion;
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

	public Long getDniAlumno() {
		return dniAlumno;
	}

	public void setDniAlumno(Long dniAlumno) {
		this.dniAlumno = dniAlumno;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public Date getFechaHoraInicioMesa() {
		return fechaHoraInicioMesa;
	}

	public void setFechaHoraInicioMesa(Date fechaHoraInicioMesa) {
		this.fechaHoraInicioMesa = fechaHoraInicioMesa;
	}

	public Date getFechaHoraFinMesa() {
		return fechaHoraFinMesa;
	}

	public void setFechaHoraFinMesa(Date fechaHoraFinMesa) {
		this.fechaHoraFinMesa = fechaHoraFinMesa;
	}

}
