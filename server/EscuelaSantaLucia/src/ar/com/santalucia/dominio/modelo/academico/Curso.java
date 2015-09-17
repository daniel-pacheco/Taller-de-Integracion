package ar.com.santalucia.dominio.modelo.academico;

import java.util.List;

import ar.com.santalucia.dominio.modelo.Entidad;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

/**
 * Clase Curso: contiene su lista de alumnos
 * @author EricDaniel
 * @version 1.0
 *
 */
public class Curso extends Entidad {

	private char division;
	private int cicloLectivo;
	private List<Alumno> listaAlumnos;

	public Curso() {
		super();
	}

	public Curso(char division, int cicloLectivo, List<Alumno> listaAlumnos) {
		super();
		this.division = division;
		this.cicloLectivo = cicloLectivo;
		this.listaAlumnos = listaAlumnos;
	}

	public char getDivision() {
		return division;
	}

	public void setDivision(char division) {
		this.division = division;
	}

	public int getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(int cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

	public List<Alumno> getListaAlumnos() {
		return listaAlumnos;
	}

	public void setListaAlumnos(List<Alumno> listaAlumnos) {
		this.listaAlumnos = listaAlumnos;
	}
	
	public void agregarAlumno(Alumno alumno) {
		this.listaAlumnos.add(alumno);
	}

	public void quitarAlumno(Alumno alumno) {
		this.listaAlumnos.add(alumno);
	}
}
