package ar.com.santalucia.dominio.modelo.desempenio;

import java.util.Date;

/**
 * Clase Inasistencia
 * @author ericpennachini
 * @version 1.0
 *
 */

public class Inasistencia {

	private Long idInasistencia;
	private Float cantidad;
	private Date fecha;
	private Boolean justificada;
	private String descripcion;

	public Inasistencia() {
		super();
	}

	public Inasistencia(Long idInasistencia, Float cantidad, Date fecha, Boolean justificada, String descripcion) {
		super();
		this.idInasistencia = idInasistencia;
		this.cantidad = cantidad;
		this.fecha = fecha;
		this.justificada = justificada;
		this.descripcion = descripcion;
	}

	public Long getIdInasistencia() {
		return idInasistencia;
	}

	public void setIdInasistencia(Long idInasistencia) {
		this.idInasistencia = idInasistencia;
	}

	public Float getCantidad() {
		return cantidad;
	}

	public void setCantidad(Float cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Boolean getJustificada() {
		return justificada;
	}

	public void setJustificada(Boolean justificada) {
		this.justificada = justificada;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
