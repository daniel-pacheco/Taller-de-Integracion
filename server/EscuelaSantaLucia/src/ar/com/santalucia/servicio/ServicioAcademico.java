package ar.com.santalucia.servicio;

import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.academico.GestorAnio;
import ar.com.santalucia.aplicacion.gestor.academico.GestorCurso;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMateria;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorPersonal;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;

/**
 * Clase servicio para gestión académica. Engloba Anio, Curso, alumnos y
 * materias
 * 
 * @author ericpennachini
 *
 */

// Último modificador: Ariel Ramirez @ 05-11-2015 17:57

public class ServicioAcademico {

	private GestorAnio gAnio;
	private GestorCurso gCurso;
	private GestorMateria gMateria;
	private GestorAlumno gAlumno;
	private GestorPersonal gDocente; 

	public ServicioAcademico() throws Exception {
		try {
			gAnio = new GestorAnio();
			gCurso = new GestorCurso();
			gMateria = new GestorMateria();
			gAlumno = new GestorAlumno();
			gDocente = new GestorPersonal();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al intentar inicializar el servicio de operaciones básicas. "
					+ ex.getMessage());
		}
	}

	public Boolean addAnio(Anio anio) throws Exception {
		try {
			if (anio.getIdAnio() == null) {
				gAnio.add(anio);
			}
			else {
				gAnio.modify(anio);
			}
		} catch (Exception ex) {
			throw new Exception("Servicio: No se pudo dar de alta el año " + ex.getMessage());
		}
		return true;
	}

	public Boolean deleteAnio(Anio anio) throws Exception {
		// TODO
		// Elimina un año.
		// ATENCIÓN: ¡La operacion puede ser en cascada!
		try {
			gAnio.delete(anio);
		} catch (Exception ex) {
			throw new Exception("Servicio: No se pudo eliminar el año. " + ex.getMessage());
		}
		return true;
	}

	public Anio getAnio(Long idAnio) throws Exception {
		try {
			return gAnio.getById(idAnio);
		} catch (Exception ex) {
			throw new Exception("Servicio: No se pudo obtener el año. " + ex.getMessage());
		}
	}

	public List<Anio> list() throws Exception{
		return getAnios(new Anio());
	} 
	
	public List<Anio> getAnios(Anio example) throws Exception {
		try {
			return gAnio.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("Servicio: no se pudo obtener la lista de años. " + ex.getMessage());
		}
	}

	public Boolean addCurso(Curso curso, Long idAnio) throws Exception {
		// TODO
		// 1 - Obtener el objeto año
		// 2 - Extraemos el listado de curso
		// 3 - Crear el objeto curso nuevo
		// 4 - Agregar el curso al listado
		// 5 - Modificar el año con el nuevo listado de curso
		// 6 - LLamar a modify del gestor del año con el año que se le agregó el
		// curso
		try {
			if (curso.getIdCurso() == null) {
				Anio anio = new Anio();
				anio = gAnio.getById(idAnio);
				Set<Curso> cursos = anio.getListaCursos();
				cursos.add(curso);
				anio.setListaCursos(cursos);
				gAnio.modify(anio);
				return true;
			} 
			else {
				gCurso.modify(curso);
				return true;
			}
		} catch (Exception ex) {
			throw new Exception("Servicio: no se pudo agregar el curso al año. " + ex.getMessage());
		}
	}
 
	/*
	public Boolean modifyCurso(Curso curso) throws Exception {
		// TODO
		// Usar el gestor de curso y pasar el curso modificado
		try {
			gCurso.modify(curso);
			return true;
		} catch (Exception ex) {
			throw new Exception("Servicio: no se pudo modificar el curso. " + ex.getMessage());
		}
	}
	*/

	public Boolean deleteCurso(Curso curso, Long idAnio) throws Exception {
		// TODO
		// 1 - Rescatar listado de alumnos del curso a borrar
		// 2 - Obtener el curso genérico con el gestor
		// 3 - Obtener el listado de alumnos del curso genérico
		// 4 - Sumar el listado de alumnos del curso a borrar con los que
		// estaban en el curso genérico
		// 5 - Asignar el listado generado en (4) a la lista de alumnos del
		// curso genérico
		// 6 - Llamar al gestor de curso para guardar el curso genérico
		// 7 - Eliminar el curso que estaba destinado a eliminarse.
		try {
			Set<Alumno> alumnosCursoBorrado = curso.getListaAlumnos();
			Curso cursoGenerico = gCurso.getByDivision('0');
			cursoGenerico.getListaAlumnos().addAll(alumnosCursoBorrado); // atencion,
																			// hay
																			// que
																			// ver
																			// si
																			// suma
																			// realmente
																			// los
																			// datos
			gCurso.modify(cursoGenerico);
			gCurso.delete(curso);
		} catch (Exception ex) {
			throw new Exception("Servicio: no se pudo eliminar el curso. " + ex.getMessage());
		}
		return false;
	}

	public Curso getCurso(Long idCurso) throws Exception {
		try {
			return gCurso.getById(idCurso);
		} catch (Exception ex) {
			throw new Exception("Servicio: no se pudo obtener el curso. " + ex.getMessage());
		}
	}

	public Boolean addMateria(Materia materia) throws Exception {
		// TODO
		// 1 - Llamar al método add para agregar la nueva materia
		try {
			if (materia.getIdMateria() ==  null) {
				gMateria.add(materia);
			}
			else {
				gMateria.modify(materia);
			}
			return true;
		} catch (Exception ex) {
			throw new Exception("Servicio: no se pudo agregar la materia. " + ex.getMessage());
		}
	}

	public Materia getMateria(Long idMateria) throws Exception{
		try{
			return gMateria.getById(idMateria);
		} catch(Exception ex){
			throw new Exception("Servicio: no se pudo obtener la materia. " + ex.getMessage());
		}
	}
	
	public Boolean deleteMateria(Materia materia) throws Exception {
		try {
			gMateria.delete(materia);
		} catch (Exception ex) {
			throw new Exception("Servicio: no se pudo eliminar la materia. " + ex.getMessage());
		}
		return false;
	}

	//TODO: seguir desde aca
	
	public Boolean asignarDocentesAMateria(Personal docenteTitular, Personal docenteSuplente, Long idMateria) throws Exception {
		// TODO
		// 1 - Obtener la materia con el gestor
		// 2 - Asignar docente titular y suplente haciedo
		// materia.setDocente(Docente t)
		// 3 - Llamar al metodo modify del gestor de materia con la misma
		// modificada
		// Sugerencia: si docente que llega es null, no actualizar llamando a
		// materia.setDocente(Docente t)
		try {
			Materia materia = gMateria.getById(idMateria);
			if (docenteTitular != null) {
				materia.setDocenteTitular(docenteTitular);
			}
			if (docenteSuplente != null) {
				materia.setDocenteSuplente(docenteSuplente);
			}
			gMateria.modify(materia);
			return true;
		} catch (Exception ex) {
			throw new Exception("Servicio: no se pudo asignar docentes a la materia. " + ex.getMessage());
		}
	}

	public Boolean asignarMateriaAAnio(Materia materia, Long idAnio) throws Exception{
		// TODO
		// 1 - Obtener el año
		// 2 - Rescatar la lista de materias del año
		// 3 - Agregar la materia a la lista rescatada
		// 4 - Asignar la lista al año llamando a set()
		// 5 - Llamar al modify de gestor de año
		try{
			Anio anio = gAnio.getById(idAnio);
			Set<Materia> materias = anio.getListaMaterias();
			materias.add(materia);
			anio.setListaMaterias(materias);
			gAnio.modify(anio);
			return true;
		}catch (Exception ex){
			throw new Exception("Servicio: no se pudo asignar la materia al año. " + ex.getMessage());
		}
	}

	public Boolean desvinvularMateriaDeAnio(Materia materia, Long idAnio) throws Exception{
		// TODO
		// 1 - Obtener el año
		// 2 - Rescatar la lista de materias
		// 3 - Remover la materia de esa lista
		// 4 - Asignar mediante set() la lista al año
		// 5 - Llamar al modify del gestor de año y guardarlo  
		try{
			Anio anio = gAnio.getById(idAnio);
			Set<Materia> materias = anio.getListaMaterias();
			materias.remove(materia);
			anio.setListaMaterias(materias);
			gAnio.modify(anio);
		} catch(Exception ex){
			throw new Exception("Servicio: no se pudo desvincular la materia al año. " + ex.getMessage());
		}
		return true;
	}
	
	public Boolean asignarAlumnoACurso(Alumno alumno, Long idCurso) throws Exception{
		// TODO
		// 1 - Obtener el curso 
		// 2 - Rescatar lista de alumnos del curso
		// 3 - Agregar alumno a la lista rescatada
		// 4 - Asignar el listado rescatado al curso
		// 5 - Llamar al modify del gestor de curso y guardarlo
		// 6 - Obtener el curso generico
		// 7 - Rescatar lista de alumnos de curso generico
		// 8 - Remover alumno desde la lista rescatada de curso generico
		// 9 - Asignar la lista modificada al curso genérico
		// 10 - Llamar al modify del gestor de curso y guardar el genérico
		try{
			Curso curso = gCurso.getById(idCurso);
			Set<Alumno> alumnos = curso.getListaAlumnos();
			alumnos.add(alumno);
			curso.setListaAlumnos(alumnos);
			gCurso.modify(curso);
			Curso cursoGenerico = gCurso.getByDivision('0');
			Set<Alumno> alumnosCursoGenerico = cursoGenerico.getListaAlumnos();
			alumnosCursoGenerico.remove(alumno);	 // Puede requerir sobrecarga de de equals()
			cursoGenerico.setListaAlumnos(alumnosCursoGenerico);
			gCurso.modify(cursoGenerico);
			return true;
		} catch(Exception ex){
			throw new Exception("Servicio: no se pudo asignar el alumno al curso. " + ex.getMessage());
		}
	}

	public Boolean desvincularAlumnoDeCurso(Alumno alumno, Long idCurso) throws Exception{
		// TODO
		// 1 - Traer el curso generico
		// 2 - Rescatar el listado de alumnos del curso generico
		// 3 - Asignar el alumno al listado de alumnos del curso generico
		// 4 - Asignar el listado modificado al curso generico
		// 5 - Llamar al modify del gestor y guardar el curso generico
		// 6 - Traer el curso
		// 7 - Rescatar el listado de alumnos del curso
		// 8 - Remover el alumno del listado
		// 9 - Asignar el listado modificado al curso
		// 10 - Llamar al modify del gestor y guardar el curso
		try{
			Curso cursoGenerico = gCurso.getByDivision('0');
			Set<Alumno> alumnosCursoGenerico = cursoGenerico.getListaAlumnos();
			alumnosCursoGenerico.add(alumno);
			cursoGenerico.setListaAlumnos(alumnosCursoGenerico);
			gCurso.modify(cursoGenerico);
			Curso curso = gCurso.getById(idCurso);
			Set<Alumno> alumnos = curso.getListaAlumnos();
			alumnos.remove(alumno);
			curso.setListaAlumnos(alumnos);
			gCurso.modify(curso);
		} catch(Exception ex){
			throw new Exception("Servicio: no se pudo desvincular el alumno del curso. " + ex.getMessage());
		}
		return false;
	}
	
	public void closeSession() throws Exception {
		gAnio.closeSession();
	}
}
