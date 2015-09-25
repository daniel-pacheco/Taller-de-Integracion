package ar.com.santalucia.dominio.modelo.desempenio;

import java.util.Set;
import java.util.HashSet;

import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

/**
 * Clase BoletinInasistencias: maneja un boletín de inasistencias
 * @author ericpennachini
 * @version 1.0
 *
 */

public class BoletinInasistencias {

	private Long idBoletinInasistencias;
	private Alumno propietario;
	private Float total;
	private Set<Inasistencia> listaInasistencias;
	private Integer cicloLectivo;

	public BoletinInasistencias() {
		super();
		listaInasistencias = new HashSet<Inasistencia>();
	}

	public BoletinInasistencias(Long idBoletinInasistencias, Alumno propietario, Float total,
			Set<Inasistencia> listaInasistencias, Integer cicloLectivo) {
		super();
		this.idBoletinInasistencias = idBoletinInasistencias;
		this.propietario = propietario;
		this.total = total;
		this.setListaInasistencias(listaInasistencias);
		this.cicloLectivo = cicloLectivo;
	}

	public Long getIdBoletinInasistencias() {
		return idBoletinInasistencias;
	}

	public void setIdBoletinInasistencias(Long idBoletinInasistencias) {
		this.idBoletinInasistencias = idBoletinInasistencias;
	}

	public Alumno getPropietario() {
		return propietario;
	}

	public void setPropietario(Alumno propietario) {
		this.propietario = propietario;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Set<Inasistencia> getListaInasistencias() {
		return listaInasistencias;
	}

	public void setListaInasistencias(Set<Inasistencia> listaInasistencias) {
		this.listaInasistencias = listaInasistencias;
	}

	public Integer getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(Integer cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

}
