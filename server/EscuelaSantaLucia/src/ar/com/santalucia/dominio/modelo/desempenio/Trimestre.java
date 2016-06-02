package ar.com.santalucia.dominio.modelo.desempenio;

import java.util.HashSet;
import java.util.Set;

import ar.com.santalucia.dominio.modelo.academico.Materia;

/**
 * Clase Trimestre: maneja los trimestres del boletín
 * 
 * @author ericpennachini
 * @version 2.1
 *
 */

//Último modificador: Eric Pennachini @ 12-03-16 14:51

public class Trimestre {

	private Long idTrimestre;
	private Integer orden;
	private Integer cicloLectivo;
	private Set<Nota> listaNotas; //notas de examenes o tps que hacen a la nota final que pone el docente
	private Nota notaFinal;
	private Materia materia;

	public Trimestre() {
		super();
		listaNotas = new HashSet<Nota>();
	}

	public Trimestre(Long idTrimestre, Integer orden, Integer cicloLectivo, Set<Nota> listaNotas, 
			Nota notaFinal, Materia materia) {
		super();
		this.idTrimestre = idTrimestre;
		this.orden = orden;
		this.cicloLectivo = cicloLectivo;
		this.setListaNotas(listaNotas);
		this.setNotaFinal(notaFinal);
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

	public Nota getNotaFinal() {
		return notaFinal;
	}

	public void setNotaFinal(Nota notaFinal) {
		this.notaFinal = notaFinal;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

}
