package ar.com.santalucia.dominio.modelo.aulavirtual;

import java.util.Set;
import ar.com.santalucia.dominio.modelo.academico.Materia;

/**
 * 
 * 
 * @author Eric
 * @version 1.0
 *
 */

// Último modificador: Ariel Ramírez @ 03-02-2016 21:31

public class AulaVirtual {
	private Long idAulaVirtual;
	private String descripcion;
	private Materia materia;
	private Set<ConfiguracionEntrega> listaEntregas;
	private Muro muro;

	public AulaVirtual() {
		super();
	}

	public AulaVirtual(Long idAulaVirtual, String descripcion, Materia materia,
			Set<ConfiguracionEntrega> listaEntregas) {
		super();
		this.idAulaVirtual = idAulaVirtual;
		this.descripcion = descripcion;
		this.materia = materia;
		this.listaEntregas = listaEntregas;
	}

	public Long getIdAulaVirtual() {
		return idAulaVirtual;
	}

	public void setIdAulaVirtual(Long idAulaVirtual) {
		this.idAulaVirtual = idAulaVirtual;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Set<ConfiguracionEntrega> getListaEntregas() {
		return listaEntregas;
	}

	public void setListaEntregas(Set<ConfiguracionEntrega> listaEntregas) {
		this.listaEntregas = listaEntregas;
	}

	public Muro getMuro() {
		return muro;
	}

	public void setMuro(Muro muro) {
		this.muro = muro;
	}

}
