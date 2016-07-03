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

// Último modificador: Ariel Ramirez @ 02-07-2016 22:33

public class Inscripcion {
	
	private Long idInscripcion;
	private Long idLlamado;
	private Set<Mesa> listaMesas;
	private Date fecha;
	private Alumno alumno;
	private Integer codigo;
	private Boolean activo;
	
	public Inscripcion() {
		super();
	}

	public Inscripcion(Long idInscripcion, Long idLlamado, Set<Mesa> listaMesas, Date fecha, Alumno alumno, Integer codigo, Boolean activo) {
		super();
		this.idInscripcion = idInscripcion;
		this.idLlamado = idLlamado;
		this.listaMesas = listaMesas;
		this.fecha = fecha;
		this.alumno = alumno;
		this.codigo = codigo;
		this.activo = activo;
	}

	public Long getIdInscripcion() {
		return idInscripcion;
	}

	public void setIdInscripcion(Long idInscripcion) {
		this.idInscripcion = idInscripcion;
	}

	public Set<Mesa> getListaMesas() {
		return listaMesas;
	}

	public void setListaMesas(Set<Mesa> listaMesas) {
		this.listaMesas = listaMesas;
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

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Long getIdLlamado() {
		return idLlamado;
	}

	public void setIdLlamado(Long idLlamado) {
		this.idLlamado = idLlamado;
	}
	
}
