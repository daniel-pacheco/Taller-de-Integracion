package ar.com.santalucia.dominio.modelo.desempenio;

import java.util.Set;
import java.util.HashSet;

import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

/**
 * Clase BoletinInasistencias: maneja un boletín de inasistencias
 * 
 * @author ericpennachini
 * @version 1.0
 *
 */

public class BoletinInasistencias {

	private Long idBoletinInasistencias;
	private Alumno propietario;
	private String anio;
	private String curso;
	private Float total;
	private Set<Inasistencia> listaInasistencias;
	private Integer cicloLectivo;
	private Boolean activo;

	public BoletinInasistencias() {
		super();
		listaInasistencias = new HashSet<Inasistencia>();
	}

	public BoletinInasistencias(Long idBoletinInasistencias, Alumno propietario, String anio, String curso, Float total,
			Set<Inasistencia> listaInasistencias, Integer cicloLectivo, Boolean activo) {
		super();
		this.idBoletinInasistencias = idBoletinInasistencias;
		this.propietario = propietario;
		this.anio = anio;
		this.curso = curso;
		this.total = total;
		this.setListaInasistencias(listaInasistencias);
		this.cicloLectivo = cicloLectivo;
		this.activo = activo;
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

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
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
