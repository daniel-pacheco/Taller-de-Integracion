package ar.com.santalucia.dominio.modelo.academico;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Eric 
 * @version 1.0
 *
*/

//Último modificador: Eric Pennachini @ 19-09-15 16:01

public class Anio {
	private Long idAnio;
	private String nombre;
	private Set<Curso> listaCursos;
	private Set<Materia> listaMaterias;

	public Anio() {
		super();
		listaCursos = new HashSet<Curso>();
		listaMaterias = new HashSet<Materia>();
	}

	public Anio(Long idAnio, String nombre, Set<Curso> listaCursos, Set<Materia> listaMaterias) {
		super();
		this.idAnio = idAnio;
		this.nombre = nombre;
		this.setListaCursos(listaCursos);
		this.setListaMaterias(listaMaterias);
	}

	public Long getIdAnio() {
		return idAnio;
	}

	public void setIdAño(Long idAnio) {
		this.idAnio = idAnio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Curso> getListaCursos() {
		return listaCursos;
	}

	public void setListaCursos(Set<Curso> listaCursos) {
		this.listaCursos = listaCursos;
	}

	public Set<Materia> getListaMaterias() {
		return listaMaterias;
	}

	public void setListaMaterias(Set<Materia> listaMaterias) {
		this.listaMaterias = listaMaterias;
	}

}
