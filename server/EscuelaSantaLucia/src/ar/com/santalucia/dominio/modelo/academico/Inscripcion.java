package ar.com.santalucia.dominio.modelo.academico;

import java.util.Date;
import java.util.Set;

import ar.com.santalucia.dominio.modelo.desempenio.Nota;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

/**
 * 
 * @author Ariel Ramirez
 * @version 1.2
 */

// Último modificador: Ariel Ramirez @ 25-06-2016 20:05

public class Inscripcion {
	
	private Long idInscripcion;
	private Set<Mesa> mesa;
	private Date fecha;
	private Alumno alumno;
	private Integer codigo;
	
	public Inscripcion() {
		super();
	}

	public Inscripcion(Long idInscripcion, Set<Mesa> mesa, Date fecha, Alumno alumno, Integer codigo) {
		super();
		this.idInscripcion = idInscripcion;
		this.mesa = mesa;
		this.fecha = fecha;
		this.alumno = alumno;
		this.codigo = codigo;
	}

	public Long getIdInscripcion() {
		return idInscripcion;
	}

	public void setIdInscripcion(Long idInscripcion) {
		this.idInscripcion = idInscripcion;
	}

	public Set<Mesa> getMesa() {
		return mesa;
	}

	public void setMesa(Set<Mesa> mesa) {
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

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
}
