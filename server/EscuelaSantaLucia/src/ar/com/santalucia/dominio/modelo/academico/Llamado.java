package ar.com.santalucia.dominio.modelo.academico;

import java.util.Date;
import java.util.Set;

/**
 * 
 * @author Ariel Ramirez
 * @version 1.1
 */

//Último modificador: Eric Pennachini @ 28-12-2015 19:38

public class Llamado {
	private Long idLlamado;
	private String descripcion;
	private Set<Mesa>listaMesas;
	private Date fechaInicio;
	private Date fechaFin;
	
	public Llamado() {
		super();
	}	

	public Llamado(Long idLlamado, String descripcion, Set<Mesa> listaMesas, Date fechaInicio, Date fechaFin) {
		super();
		this.idLlamado = idLlamado;
		this.descripcion = descripcion;
		this.listaMesas = listaMesas;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public Long getIdLlamado() {
		return idLlamado;
	}

	public void setIdLlamado(Long idLlamado) {
		this.idLlamado = idLlamado;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Mesa> getListaMesas() {
		return listaMesas;
	}

	public void setListaMesas(Set<Mesa> listaMesas) {
		this.listaMesas = listaMesas;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
}
