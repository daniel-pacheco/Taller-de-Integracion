package ar.com.santalucia.dominio.modelo.aulavirtual;

import java.util.Date;
import java.util.Set;

/**
 * 
 * 
 * @author Eric
 * @version 1.0
 *
 */

public class ConfiguracionEntrega {
	private Long idConfiguracionEntrega;
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;
	private Character estado;
	private Set<Entrega> entregados;

	public ConfiguracionEntrega() {
		super();
	}

	public ConfiguracionEntrega(Long idConfiguracionEntrega, String descripcion, Date fechaInicio, Date fechaFin,
			Character estado, Set<Entrega> entregados) {
		super();
		this.idConfiguracionEntrega = idConfiguracionEntrega;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
		this.entregados = entregados;
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

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

	public Set<Entrega> getEntregados() {
		return entregados;
	}

	public void setEntregados(Set<Entrega> entregados) {
		this.entregados = entregados;
	}

}
