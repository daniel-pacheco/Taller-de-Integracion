package ar.com.santalucia.dominio.modelo.desempenio;

import java.util.Date;

import ar.com.santalucia.dominio.modelo.academico.Materia;

/**
 * Clase Nota
 * @author ericpennachini
 * @version 1.0
 *
 */

public class Nota {

	private Long idNota;
	private Date fecha;
	private Float calificacion;
	private Materia materia;
	private String tipo;
	//private Trimestre trimestre;

	public Nota() {
		super();
	}

	public Nota(Long idNota, Date fecha, Float calificacion, Materia materia, String tipo) {
		super();
		this.idNota = idNota;
		this.fecha = fecha;
		this.calificacion = calificacion;
		this.materia = materia;
		this.tipo = tipo;
	}

	public Long getIdNota() {
		return idNota;
	}

	public void setIdNota(Long idNota) {
		this.idNota = idNota;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Float getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Float calificacion) {
		this.calificacion = calificacion;
	}
	
	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/*
	public Trimestre getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(Trimestre trimestre) {
		this.trimestre = trimestre;
	}
	*/
}
