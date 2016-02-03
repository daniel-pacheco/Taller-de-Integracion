package ar.com.santalucia.dominio.modelo.aulavirtual;

import java.util.Set;

import ar.com.santalucia.dominio.modelo.academico.Materia;

/**
 * Clase Muro (muro de una materia en el aula virtual
 * 
 * @author Eric
 * @version 1.0
 *
 */

public class Muro {
	private Long idMuro;
	private String nombre;
	private String descripcion;
	private Set<Recurso> listaRecursos;
	private Materia materia;

	public Muro() {
		super();
	}

	public Muro(Long idMuro, String nombre, String descripcion, Set<Recurso> listaRecursos, Materia materia) {
		super();
		this.idMuro = idMuro;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.listaRecursos = listaRecursos;
		this.materia = materia;
	}

	public Long getIdMuro() {
		return idMuro;
	}

	public void setIdMuro(Long idMuro) {
		this.idMuro = idMuro;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<Recurso> getListaRecursos() {
		return listaRecursos;
	}

	public void setListaRecursos(Set<Recurso> listaRecursos) {
		this.listaRecursos = listaRecursos;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

}
