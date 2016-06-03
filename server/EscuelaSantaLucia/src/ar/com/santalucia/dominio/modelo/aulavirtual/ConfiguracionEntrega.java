package ar.com.santalucia.dominio.modelo.aulavirtual;

import java.util.Date;

/**
 * 
 * 
 * @author Eric
 * @version 1.0
 *
 */

// Último modificador: Ariel Ramirez @ 03-02-2016 19:51

public class ConfiguracionEntrega {
	private Long idConfiguracionEntrega;
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;
	private Boolean estado;
	

	public ConfiguracionEntrega() {
		super();
	}

	public ConfiguracionEntrega(Long idConfiguracionEntrega, String descripcion, Date fechaInicio, Date fechaFin,
			Boolean estado) {
		super();
		this.idConfiguracionEntrega = idConfiguracionEntrega;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
	}

	public Long getIdConfiguracionEntrega() {
		return idConfiguracionEntrega;
	}

	public void setIdConfiguracionEntrega(Long idConfiguracionEntrega) {
		this.idConfiguracionEntrega = idConfiguracionEntrega;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

}
