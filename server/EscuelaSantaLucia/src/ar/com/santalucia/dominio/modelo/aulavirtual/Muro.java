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

// Último modificador: Ariel Ramirez @ 19:24

public class Muro {
	private Long idMuro;
	private String nombre;
	private String descripcion;
	private Materia materia;
	private Set<Publicacion> listaPublicaciones;

	public Muro() {
		super();
	}

	public Muro(Long idMuro, String nombre, String descripcion, Materia materia, Set<Publicacion> listaPublicaciones) {
		super();
		this.idMuro = idMuro;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.materia = materia;
		this.setListaPublicaciones(listaPublicaciones); 
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

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Set<Publicacion> getListaPublicaciones() {
		return listaPublicaciones;
	}

	public void setListaPublicaciones(Set<Publicacion> listaPublicaciones) {
		this.listaPublicaciones = listaPublicaciones;
	}

}
