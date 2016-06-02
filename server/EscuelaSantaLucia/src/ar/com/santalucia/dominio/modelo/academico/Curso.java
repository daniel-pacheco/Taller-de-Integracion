package ar.com.santalucia.dominio.modelo.academico;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

/**
 * 
 * @author Ariel Ramirez
 *
 * @version 1.1
 */

// Último modificador: Eric Pennachini @ 05-11-15 16:36

public class Curso {
	private Long idCurso;
	private Character division;
	private String turno;
	private Integer cicloLectivo;
	private Set<Alumno> listaAlumnos;

	public Curso() {
		super();
		listaAlumnos = new HashSet<Alumno>();
	}
 
	public Curso(Long idCurso, Character division, String turno, Integer cicloLectivo, Set<Alumno> listaAlumnos) {
		super();
		this.idCurso = idCurso;
		this.division = division;
		this.turno = turno;
		this.cicloLectivo = cicloLectivo;
		this.setListaAlumnos(listaAlumnos);
	}

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public Character getDivision() {
		return division;
	}

	public void setDivision(Character division) {
		this.division = division;
	}

	public Integer getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(Integer cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

	public Set<Alumno> getListaAlumnos() {
		return listaAlumnos;
	}

	public void setListaAlumnos(Set<Alumno> listaAlumnos) {
		this.listaAlumnos = listaAlumnos;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == Curso.class) {
			if (this.division.equals(((Curso) obj).division)) {
				return true; 
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		 return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
		            // if deriving: appendSuper(super.hashCode()).
		            append(division).
		            append(turno).
		            append(cicloLectivo).
		            toHashCode();
	}
	
	

}
