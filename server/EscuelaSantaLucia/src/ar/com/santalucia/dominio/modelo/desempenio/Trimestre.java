package ar.com.santalucia.dominio.modelo.desempenio;

import java.util.HashSet;
import java.util.Set;

import ar.com.santalucia.dominio.modelo.academico.Materia;

/**
 * Clase Trimestre: maneja los trimestres del boletín
 * @author ericpennachini
 * @version 1.0
 *
 */

public class Trimestre {

	private Long idTrimestre;
	private String descripcion;
	private Integer cicloLectivo;
	private Set<Nota> listaNotas;
	private Materia materia;

	public Trimestre() {
		super();
		listaNotas = new HashSet<Nota>();
	}

	public Trimestre(Long idTrimestre, String descripcion, Integer cicloLectivo, Set<Nota> listaNotas, Materia materia) {
		super();
		this.idTrimestre = idTrimestre;
		this.descripcion = descripcion;
		this.cicloLectivo = cicloLectivo;
		this.setListaNotas(listaNotas);
		this.materia = materia;
	}

	public Long getIdTrimestre() {
		return idTrimestre;
	}

	public void setIdTrimestre(Long idTrimestre) {
		this.idTrimestre = idTrimestre;
	}

	public Integer getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(Integer cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

	public Set<Nota> getListaNotas() {
		return listaNotas;
	}

	public void setListaNotas(Set<Nota> listaNotas) {
		this.listaNotas = listaNotas;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
