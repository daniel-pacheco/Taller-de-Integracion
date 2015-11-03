package ar.com.santalucia.servicio;

import java.util.List;

import ar.com.santalucia.aplicacion.gestor.academico.GestorAnio;
import ar.com.santalucia.aplicacion.gestor.academico.GestorCurso;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMateria;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorDocente;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Docente;

/**
 * Clase servicio para gestión académica.
 * Engloba Anio, Curso, alumnos y materias
 * @author ericpennachini
 *
 */

public class ServicioAcademico {

	private GestorAnio gAnio;
	private GestorCurso gCurso;
	private GestorMateria gMateria;
	private GestorAlumno gAlumno;
	private GestorDocente gDocente;
	
	public ServicioAcademico() throws Exception {
		try {
			gAnio = new GestorAnio();
			gCurso = new GestorCurso();
			gMateria = new GestorMateria();
			gAlumno = new GestorAlumno();
			gDocente = new GestorDocente();
		} catch (Exception ex) {
			throw new Exception(
					"Ha ocurrido un problema al intentar inicializar el servicio de operaciones básicas. "
							+ ex.getMessage());
		}
	}
	
	public boolean addAnio(Anio anio) {
		return false;
	}
	
	public boolean modifyAnio(Anio anio) {
		return false;
	}
	
	public boolean deleteAnio(Anio anio) {
		return false;
	}
	
	public Anio getAnio(Long idAnio) {
		return null;
	}
	
	public List<Anio> getAnios() {
		return null;
	}
	
	public boolean addCurso(Curso curso, Long idAnio) {
		return false;
	}
	
	public boolean modifyCurso(Curso curso, Long idAnio) {
		return false;
	}
	
	public boolean deleteCurso(Curso curso, Long idAnio) {
		return false;
	}
	
	public Curso getCurso(Long idCurso) {
		return null;
	}
	
	public boolean addMateria(Materia materia) {
		return false;
	}
	
	public boolean modifyMateria(Materia materia) {
		return false;
	}
	
	public boolean deleteMateria(Materia materia) {
		return false;
	}
	
	public boolean asignarDocente(Docente docente, Long idMateria) {
		return false;
	}	
	
	public boolean asignarMateria(Materia materia, Long idAnio) {
		return false;
	}
	
	public boolean asignarAlumnoACurso(Alumno alumno, Long idCurso) {
		return false;
	}
	
	
	
}
