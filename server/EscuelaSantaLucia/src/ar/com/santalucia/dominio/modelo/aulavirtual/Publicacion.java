package ar.com.santalucia.dominio.modelo.aulavirtual;

import java.util.Date;
import java.util.Set;

/**
 * Clase Publicacion (publicacion que realiza el docente en el muro, con
 * recursos incluídos o no)
 * 
 * @author Eric
 * @version 1.0
 *
 */

// Último modificador: Ariel Ramirez @ 03-02-2016 19:53

public class Publicacion {
	private Long idPublicacion;
	private String descripcion;
	private Date fechaCreacion;
	private Date fechaUltimaModificacion;
	private Set<Recurso> listaRecursos; //El recurso debe existir o se debe subir antes que la persistencia de la publicación
	private String texto;

	public Publicacion() {
		super();
	}

	public Publicacion(Long idPublicacion, String descripcion, Date fechaCreacion,
			Date fechaUltimaModificacion, Set<Recurso> listaRecursos) {
		super();
		this.idPublicacion = idPublicacion;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaModificacion = fechaUltimaModificacion;
		this.listaRecursos = listaRecursos;
	}

	public Long getIdPublicacion() {
		return idPublicacion;
	}

	public void setIdPublicacion(Long idPublicacion) {
		this.idPublicacion = idPublicacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public Set<Recurso> getListaRecursos() {
		return listaRecursos;
	}

	public void setListaRecursos(Set<Recurso> listaRecursos) {
		this.listaRecursos = listaRecursos;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
