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
public class Publicacion {
	private Long idPublicacion;
	// private String nombre;
	private String descripcion;
	private Date fechaCreacion;
	private Date fechaUltimaModificacion;
	private Set<Recurso> listaRecursos;

	public Publicacion() {
		super();
	}

	public Publicacion(Long idPublicacion, /* String nombre, */ String descripcion, Date fechaCreacion,
			Date fechaUltimaModificacion, Set<Recurso> listaRecursos) {
		super();
		this.idPublicacion = idPublicacion;
		// this.nombre = nombre;
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
	/*
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	*/
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

}
