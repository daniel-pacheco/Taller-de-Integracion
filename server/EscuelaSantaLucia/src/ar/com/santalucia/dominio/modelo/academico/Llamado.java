package ar.com.santalucia.dominio.modelo.academico;

import java.util.Date;
import java.util.Set;

/**
 * 
 * @author Ariel Ramirez
 *
 *@version 1.0
 */

public class Llamado {
	private Long idLlamado;
	private Set<Mesa>listaMesas;
	private Date fechaInicio;
	private Date fechaFin;
	
	public Llamado() {
		super();
	}
	
	public Llamado(Long idLlamado, Set<Mesa> listaMesas, Date fechaInicio, Date fechaFin) {
		super();
		this.idLlamado = idLlamado;
		setListaMesas(listaMesas);
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public Long getIdLlamado() {
		return idLlamado;
	}

	public void setIdLlamado(Long idLlamado) {
		this.idLlamado = idLlamado;
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
