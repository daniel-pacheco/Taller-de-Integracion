package ar.com.santalucia.dominio.modelo.academico;

import java.util.Date;

import ar.com.santalucia.dominio.modelo.desempenio.Nota;

/**
 * Clase MesaExamen, entidad histórica que registra las rendidas de los alumnos
 * 
 * @author Eric
 * @version 2.1
 *
 */

// Ultimo modificador: Eric Pennachini @ 10-03-2016 17:07

public class MesaExamenHist {
	private Long idMesaExamenHist;
	private String nroActa;
	private Float nota; // o Nota en vez de Long
	private Boolean asistencia;
	private Date fechaInscripcion;
	private String nombreMateria;
	private Long dniDocente1;
	private String nombreDocente1;
	private String apellidoDocente1;
	private Long dniDocente2;
	private String nombreDocente2;
	private String apellidoDocente2;
	private Long dniDocente3;
	private String nombreDocente3;
	private String apellidoDocente3;
	private Long dniAlumno;
	private String nombreAlumno;
	private String apellidoAlumno;
	private Date fechaHoraInicioMesa;
	private Date fechaHoraFinMesa;

	public MesaExamenHist() {
		super();
	}

	public MesaExamenHist(Long idMesaExamen, String nroActa, Float nota, Boolean asistencia, Date fechaInscripcion,
			String nombreMateria, Long dniDocente1, String nombreDocente1, String apellidoDocente1, Long dniDocente2,
			String nombreDocente2, String apellidoDocente2, Long dniDocente3, String nombreDocente3,
			String apellidoDocente3, Long dniAlumno, String nombreAlumno, String apellidoAlumno, Date horaInicioMesa,
			Date horaFinMesa) {
		super();
		this.idMesaExamenHist = idMesaExamen;
		this.nroActa = nroActa;
		this.nota = nota;
		this.asistencia = asistencia;
		this.fechaInscripcion = fechaInscripcion;
		this.nombreMateria = nombreMateria;
		this.dniDocente1 = dniDocente1;
		this.nombreDocente1 = nombreDocente1;
		this.apellidoDocente1 = apellidoDocente1;
		this.dniDocente2 = dniDocente2;
		this.nombreDocente2 = nombreDocente2;
		this.apellidoDocente2 = apellidoDocente2;
		this.dniDocente3 = dniDocente3;
		this.nombreDocente3 = nombreDocente3;
		this.apellidoDocente3 = apellidoDocente3;
		this.dniAlumno = dniAlumno;
		this.nombreAlumno = nombreAlumno;
		this.apellidoAlumno = apellidoAlumno;
		this.setFechaHoraInicioMesa(horaInicioMesa);
		this.setFechaHoraFinMesa(horaFinMesa);
	}

	public Long getIdMesaExamenHist() {
		return idMesaExamenHist;
	}

	public void setIdMesaExamenHist(Long idMesaExamenHist) {
		this.idMesaExamenHist = idMesaExamenHist;
	}

	public String getNroActa() {
		return nroActa;
	}

	public void setNroActa(String nroActa) {
		this.nroActa = nroActa;
	}

	public Float getNota() {
		return nota;
	}

	public void setNota(Float nota) {
		this.nota = nota;
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

	public void setFechaInscripcion(Date fecha) {
		this.fechaInscripcion = fecha;
	}

	public String getNombreMateria() {
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	public Long getDniDocente1() {
		return dniDocente1;
	}

	public void setDniDocente1(Long dniDocente1) {
		this.dniDocente1 = dniDocente1;
	}

	public String getNombreDocente1() {
		return nombreDocente1;
	}

	public void setNombreDocente1(String nombreDocente1) {
		this.nombreDocente1 = nombreDocente1;
	}

	public String getApellidoDocente1() {
		return apellidoDocente1;
	}

	public void setApellidoDocente1(String apellidoDocente1) {
		this.apellidoDocente1 = apellidoDocente1;
	}

	public Long getDniDocente2() {
		return dniDocente2;
	}

	public void setDniDocente2(Long dniDocente2) {
		this.dniDocente2 = dniDocente2;
	}

	public String getNombreDocente2() {
		return nombreDocente2;
	}

	public void setNombreDocente2(String nombreDocente2) {
		this.nombreDocente2 = nombreDocente2;
	}

	public String getApellidoDocente2() {
		return apellidoDocente2;
	}

	public void setApellidoDocente2(String apellidoDocente2) {
		this.apellidoDocente2 = apellidoDocente2;
	}

	public Long getDniDocente3() {
		return dniDocente3;
	}

	public void setDniDocente3(Long dniDocente3) {
		this.dniDocente3 = dniDocente3;
	}

	public String getNombreDocente3() {
		return nombreDocente3;
	}

	public void setNombreDocente3(String nombreDocente3) {
		this.nombreDocente3 = nombreDocente3;
	}

	public String getApellidoDocente3() {
		return apellidoDocente3;
	}

	public void setApellidoDocente3(String apellidoDocente3) {
		this.apellidoDocente3 = apellidoDocente3;
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
