package ar.com.santalucia.dominio.dto;

import java.util.Date;

/**
 * InasistenciaDTO: contiene información sobre una inasistencia y un campo acumulador para mostrar
 * en el listado completo, referenciando a los campos anteriores.
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class InasistenciaDTO {

	private String idInasistencia;
	private Float cantidad;
	private Date fecha;
	private String concepto;
	private Boolean justificada;
	private Float totalAcum;

	public InasistenciaDTO() {
		super();
	}

	public InasistenciaDTO(String idInasistencia, Float cantidad, Date fecha, String concepto, Boolean justificada,
			Float totalAcum) {
		super();
		this.idInasistencia = idInasistencia;
		this.cantidad = cantidad;
		this.fecha = fecha;
		this.concepto = concepto;
		this.justificada = justificada;
		this.totalAcum = totalAcum;
	}

	/**
	 * @return the idInasistencia
	 */
	public String getIdInasistencia() {
		return idInasistencia;
	}

	/**
	 * @param idInasistencia
	 *            the idInasistencia to set
	 */
	public void setIdInasistencia(String idInasistencia) {
		this.idInasistencia = idInasistencia;
	}

	/**
	 * @return the cantidad
	 */
	public Float getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad
	 *            the cantidad to set
	 */
	public void setCantidad(Float cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}

	/**
	 * @param concepto
	 *            the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	/**
	 * @return the justificada
	 */
	public Boolean getJustificada() {
		return justificada;
	}

	/**
	 * @param justificada
	 *            the justificada to set
	 */
	public void setJustificada(Boolean justificada) {
		this.justificada = justificada;
	}

	/**
	 * @return the totalAcum
	 */
	public Float getTotalAcum() {
		return totalAcum;
	}

	/**
	 * @param totalAcum
	 *            the totalAcum to set
	 */
	public void setTotalAcum(Float totalAcum) {
		this.totalAcum = totalAcum;
	}

}
