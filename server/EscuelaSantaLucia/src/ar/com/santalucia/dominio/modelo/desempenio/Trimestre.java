package ar.com.santalucia.dominio.modelo.desempenio;

import java.util.Set;

import ar.com.santalucia.dominio.modelo.academico.Materia;

public class Trimestre {
	private Long idTrimestre;
	private Integer cicloLectivo;
	private Set<Nota> listaNotas;
	private Materia materia;

	public Trimestre() {
		super();
	}

	public Trimestre(Long idTrimestre, Integer cicloLectivo, Set<Nota> listaNotas, Materia materia) {
		super();
		this.idTrimestre = idTrimestre;
		this.cicloLectivo = cicloLectivo;
		this.listaNotas = listaNotas;
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

}
