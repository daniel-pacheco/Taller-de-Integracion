package ar.com.santalucia.dominio.modelo.desempeño;

import java.util.Date;

import ar.com.santalucia.dominio.modelo.Entidad;
import ar.com.santalucia.dominio.modelo.academico.Materia;

public class Nota extends Entidad {

	private Date fecha;
	private float calificacion;
	private Materia materia;
	private String tipo;
	private Trimestre trimestre;

	public Nota() {
		super();
	}

	public Nota(Date fecha, float calificacion, Materia materia, String tipo,
			Trimestre trimestre) {
		super();
		this.fecha = fecha;
		this.calificacion = calificacion;
		this.materia = materia;
		this.tipo = tipo;
		this.trimestre = trimestre;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public float getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(float calificacion) {
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

	public Trimestre getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(Trimestre trimestre) {
		this.trimestre = trimestre;
	}

}
