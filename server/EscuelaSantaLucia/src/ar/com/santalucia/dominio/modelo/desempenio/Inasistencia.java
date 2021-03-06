package ar.com.santalucia.dominio.modelo.desempenio;

import java.util.Date;

/**
 * Clase Inasistencia
 * @author ericpennachini
 * @version 1.0
 *
 */

public class Inasistencia implements Comparable<Inasistencia> {

	private Long idInasistencia;
	private Float cantidad;
	private Date fecha;
	private Boolean justificada;
	private String concepto; // concepto, o "falto a"

	public Inasistencia() {
		super();
	}

	public Inasistencia(Long idInasistencia, Float cantidad, Date fecha, Boolean justificada, String concepto) {
		super();
		this.idInasistencia = idInasistencia;
		this.cantidad = cantidad;
		this.fecha = fecha;
		this.justificada = justificada;
		this.concepto = concepto;
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

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Inasistencia inasistencia = (Inasistencia) obj;
		return (this.fecha.equals(inasistencia.fecha)) && (this.concepto.equals(inasistencia.concepto));
	}

	@Override
	public int compareTo(Inasistencia o) {
		if (this.fecha.equals(o.fecha)) {
			return 0;
		}
		if (this.fecha.before(o.fecha)) {
			return -1;
		}
		if (this.fecha.after(o.fecha)) {
			return 1;
		}
		return -2;
	}

	
	
}
