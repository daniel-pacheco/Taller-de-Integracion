package ar.com.santalucia.dominio.modelo.desempenio;

import java.util.HashSet;
import java.util.Set;

import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

/**
 * Clase BoletinNotas: maneja un boletin de notas (libreta), conteniendo una
 * lista de Trimestres.
 *
 * @author ericpennachini
 * @version 2.0
 *
 */

// Último modificador: Eric Pennachini @ 07-04-2016 15:41

public class BoletinNotas {
	private Long idBoletinNotas;
	private Alumno propietario;
	private String anio;
	private String curso;
	private Set<Trimestre> listaTrimestres;
	private Set<Nota> listaNotasExamen; // notas de diciembre, marzo...
	private Integer cicloLectivo;

	public BoletinNotas() {
		super();
		listaTrimestres = new HashSet<Trimestre>();
		listaNotasExamen = new HashSet<Nota>();
	}

	public BoletinNotas(Long idBoletinNotas, Alumno propietario, String anio, String curso, Set<Trimestre> listaTrimestres, Set<Nota> listaNotasExamen, Integer cicloLectivo) {
		super();
		this.idBoletinNotas = idBoletinNotas;
		this.propietario = propietario;
		this.anio = anio;
		this.curso = curso;
		this.setListaTrimestres(listaTrimestres);
		this.setListaNotasExamen(listaNotasExamen);
		this.cicloLectivo = cicloLectivo;
	}

	public Long getIdBoletinNotas() {
		return idBoletinNotas;
	}

	public void setIdBoletinNotas(Long idBoletinNotas) {
		this.idBoletinNotas = idBoletinNotas;
	}

	public Alumno getPropietario() {
		return propietario;
	}

	public void setPropietario(Alumno propietario) {
		this.propietario = propietario;
	}

	public Set<Trimestre> getListaTrimestres() {
		return listaTrimestres;
	}

	public void setListaTrimestres(Set<Trimestre> listaTrimestres) {
		this.listaTrimestres = listaTrimestres;
	}

	public Integer getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(Integer cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

	public Set<Nota> getListaNotasExamen() {
		return listaNotasExamen;
	}

	public void setListaNotasExamen(Set<Nota> listaNotasExamen) {
		this.listaNotasExamen = listaNotasExamen;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

}
