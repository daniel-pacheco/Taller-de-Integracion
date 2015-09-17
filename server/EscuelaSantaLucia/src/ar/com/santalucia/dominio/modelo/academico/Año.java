package ar.com.santalucia.dominio.modelo.academico;

import java.util.ArrayList;
import java.util.List;

import ar.com.santalucia.dominio.modelo.Entidad;

/**
 * Clase Año Contiene la lista de cursos y de materias
 * 
 * @author EricDaniel
 * @version 1.0
 *
 */
public class Año extends Entidad{

	private String nombre;
	private List<Curso> listaCursos;
	private List<Materia> listaMaterias;

	public Año() {
		super();
		listaCursos = new ArrayList<Curso>();
		listaMaterias = new ArrayList<Materia>();
	}

	public Año(String nombre, List<Curso> listaCursos,
			List<Materia> listaMaterias) {
		super();
		this.nombre = nombre;
		this.listaCursos = listaCursos;
		this.listaMaterias = listaMaterias;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Curso> getListaCursos() {
		return listaCursos;
	}

	public void setListaCursos(List<Curso> listaCursos) {
		this.listaCursos = listaCursos;
	}

	public List<Materia> getListaMaterias() {
		return listaMaterias;
	}

	public void setListaMaterias(List<Materia> listaMaterias) {
		this.listaMaterias = listaMaterias;
	}

	public void agregarCurso(Curso curso) {
		listaCursos.add(curso);
	}

	public void quitarCurso(Curso curso) {
		listaCursos.remove(curso);
	}

	public void agregarMateria(Materia materia) {
		listaMaterias.add(materia);
	}
	
	public void quitarMateria(Materia materia) {
		listaMaterias.remove(materia);
	}

}
