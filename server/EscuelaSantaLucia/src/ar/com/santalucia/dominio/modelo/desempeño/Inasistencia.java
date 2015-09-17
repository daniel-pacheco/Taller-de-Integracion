package ar.com.santalucia.dominio.modelo.desempeño;

import java.util.Date;

import ar.com.santalucia.dominio.modelo.Entidad;

public class Inasistencia extends Entidad {

	private float cantidad;
	private Date fecha;
	private boolean justificada;
	private String descripcion;

	public Inasistencia() {
		super();
	}

	public Inasistencia(float cantidad, Date fecha, boolean justificada,
			String descripcion) {
		super();
		this.cantidad = cantidad;
		this.fecha = fecha;
		this.justificada = justificada;
		this.descripcion = descripcion;
	}

	public float getCantidad() {
		return cantidad;
	}

	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isJustificada() {
		return justificada;
	}

	public void setJustificada(boolean justificada) {
		this.justificada = justificada;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
