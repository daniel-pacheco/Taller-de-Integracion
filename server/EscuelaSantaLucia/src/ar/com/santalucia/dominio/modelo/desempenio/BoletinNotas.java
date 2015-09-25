package ar.com.santalucia.dominio.modelo.desempenio;

import java.util.HashSet;
import java.util.Set;

import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

/**
 * Clase BoletinNotas: maneja un boletin de notas (libreta), conteniendo una
 * lista de Trimestres.
 * @author ericpennachini
 * @version 1.0
 *
 */

public class BoletinNotas {

	private Long idBoletinNotas;
	private Alumno propietario;
	private Set<Trimestre> listaTrimestres;
	private Integer cicloLectivo;

	public BoletinNotas() {
		super();
		listaTrimestres = new HashSet<Trimestre>();
	}

	public BoletinNotas(Long idBoletinNotas, Alumno propietario, Set<Trimestre> listaTrimestres, Integer cicloLectivo) {
		super();
		this.idBoletinNotas = idBoletinNotas;
		this.propietario = propietario;
		this.setListaTrimestres(listaTrimestres);
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

}
