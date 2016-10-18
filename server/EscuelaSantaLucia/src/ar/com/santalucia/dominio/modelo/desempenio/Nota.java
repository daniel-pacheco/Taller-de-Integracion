package ar.com.santalucia.dominio.modelo.desempenio;

import java.util.Date;

import ar.com.santalucia.dominio.modelo.academico.Materia;

/**
 * Clase Nota
 * 
 * @author ericpennachini
 * @version 2.0
 *
 */

public class Nota {
	private Long idNota;
	private String descripcion;
	private Date fecha;
	private Float calificacion;
	private Materia materia;
	private String tipo;
	
	public static final String EXAMEN = "Examen";
	public static final String TRABAJO_PRACTICO = "Trabajo pr�ctico";
	public static final String CONCEPTO = "Concepto";
	public static final String NOTA_FINAL_TRIMESTRAL = "Nota final trimestral";
	public static final String DICIEMBRE = "Diciembre";
	public static final String MARZO = "Marzo";
	
	public Nota() {
		super();
	}

	public Nota(Long idNota, String descripcion, Date fecha, Float calificacion, Materia materia, String tipo) {
		super();
		this.idNota = idNota;
		this.descripcion = descripcion;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
