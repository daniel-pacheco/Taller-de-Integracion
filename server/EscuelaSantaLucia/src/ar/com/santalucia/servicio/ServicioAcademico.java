package ar.com.santalucia.servicio;

import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.academico.GestorAnio;
import ar.com.santalucia.aplicacion.gestor.academico.GestorArea;
import ar.com.santalucia.aplicacion.gestor.academico.GestorCurso;
import ar.com.santalucia.aplicacion.gestor.academico.GestorLlamado;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMateria;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMesa;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorPersonal;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Area;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Llamado;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.academico.Mesa;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;
import ar.com.santalucia.excepciones.ValidacionException;

/**
 * Clase servicio para gestión académica. Engloba Anio, Curso, alumnos y
 * materias
 * 
 * @author ericpennachini
 *
 */

// Último modificador: Ariel Ramirez @ 23-12-2015 16:59

public class ServicioAcademico {

	private GestorAnio gAnio;
	private GestorCurso gCurso;
	private GestorMateria gMateria;
	private GestorArea gArea;
	private GestorAlumno gAlumno;
	private GestorPersonal gDocente;
	private GestorLlamado gLlamado;
	private GestorMesa gMesa;

	public ServicioAcademico() throws Exception {
		try {
			gAnio = new GestorAnio();
			gCurso = new GestorCurso();
			gMateria = new GestorMateria();
			gArea = new GestorArea();
			gAlumno = new GestorAlumno();
			gDocente = new GestorPersonal();
			gLlamado = new GestorLlamado();
			gMesa = new GestorMesa();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar el servicio de operaciones básicas: "
					+ ex.getMessage());
		}
	}
	

	public Boolean addAnio(Anio anio) throws Exception {	// EN ENDPOINT
		try {
			if (anio.getIdAnio() == null) {
				gAnio.add(anio);
			}
			else {
				gAnio.modify(anio);
			}
			
		} 
		catch(ValidacionException ex){
			throw ex;
		}
		catch (Exception ex) {
			throw new Exception("No se pudo dar de alta el AÑO: " + ex.getMessage());
		}
		
		return true;
	}

	public Boolean deleteAnio(Anio anio) throws Exception {  // EN ENDPOINT
		// TODO
		// Elimina un año.
		// ATENCIÓN: ¡La operacion puede ser en cascada!
		try {
			gAnio.delete(anio);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar el AÑO: " + ex.getMessage());
		}
		return true;
	}

	public Anio getAnio(Long idAnio) throws Exception { 	// EN ENDPOINT
		try {
			return gAnio.getById(idAnio);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener el AÑO: " + ex.getMessage());
		}
	}

	public List<Anio> list() throws Exception{				// EN ENDPOINT
		return getAnios(new Anio());
	} 
	
	public List<Anio> getAnios(Anio example) throws Exception { // EN ENDPOINT
		try {
			return gAnio.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista de AÑOS: " + ex.getMessage());
		}
	}
	

	public Boolean addCurso(Curso curso, Long idAnio) throws Exception { // CANDIDATO A SUPRIMIR
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
			throw new Exception("No se pudo agregar el curso al AÑO: " + ex.getMessage());
		}
	}
 
	/*
	public Boolean modifyCurso(Curso curso) throws Exception {			//CANDIDATO A SUPRIMIR
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

	public Boolean deleteCurso(Curso curso) throws Exception { 	// EN ENDPOINT
		// TODO
		// 1 - Rescatar listado de alumnos del curso a borrar
		// 2 - Obtener el curso genérico con el gestor
		// 3 - Obtener el listado de alumnos del curso genérico
		// 4 - Sumar el listado de alumnos del curso a borrar con los que
		// estaban en el curso genérico
		// 5 - Asignar el listado generado en (4) a la lista de alumnos del
		// curso genérico
		// 6 - Llamar al gestor de curso para guardar el curso genérico
		// 7 - Eliminar el curso que estaba destinado a eliminarse.´
		try {
			Set<Alumno> alumnosCursoBorrado = curso.getListaAlumnos();
			Curso cursoGenerico = gCurso.getByDivision('0');
			cursoGenerico.getListaAlumnos().addAll(alumnosCursoBorrado); // atencion, hay que ver si suma realmentelos datos
			gCurso.modify(cursoGenerico);
			gCurso.delete(curso);
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar el CURSO: " + ex.getMessage());
		}
	}

	public Curso getCurso(Long idCurso) throws Exception { 		// EN ENDPOINT
		try {
			return gCurso.getById(idCurso);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener el CURSO: " + ex.getMessage());
		}
	}
	
	public Boolean asignarAlumnoACurso(Alumno alumno, Long idCurso) throws Exception{ 			//EN ENDPOINT
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
			throw new Exception("No se pudo asignar el ALUMNO al CURSO: " + ex.getMessage());
		}
	}

	public Boolean desvincularAlumnoDeCurso(Alumno alumno, Long idCurso) throws Exception{ // EN ENDPOINT
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
			throw new Exception("No se pudo desvincular el ALUMNO del CURSO: " + ex.getMessage());
		}
		return false;
	}

	
	public Boolean addMateria(Materia materia) throws Exception { 		// EN ENDPOINT
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
			throw new Exception("No se pudo agregar la MATERIA: " + ex.getMessage());
		}
	}

	public Boolean deleteMateria(Materia materia) throws Exception { // EN ENDPOINT
		try {
			gMateria.delete(materia);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar la MATERIA: " + ex.getMessage());
		}
		return false;
	}

	public Materia getMateria(Long idMateria) throws Exception{ // EN ENDPOINT
		try{
			return gMateria.getById(idMateria);
		} catch(Exception ex){
			throw new Exception("No se pudo obtener la MATERIA: " + ex.getMessage());
		}
	}
	
	public List<Materia> getMaterias(Materia example) throws Exception{
		try {
			return gMateria.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista de MATERIAS: " + ex.getMessage());
		}
	}
	
	public Boolean asignarDocentesAMateria(Personal docenteTitular, Personal docenteSuplente, Long idMateria) throws Exception { // EN ENDPOINT
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
			throw new Exception("No se pudo asignar DOCENTES a la MATERIA: " + ex.getMessage());
		}
	}
	
	public Boolean desvincularDocentesDeMateria(Personal docenteTitular, Personal docenteSuplente, Long idMateria) throws Exception {
		try {
			Materia materia = gMateria.getById(idMateria);
			if (docenteTitular != null) {
				materia.setDocenteTitular(null);
			}
			if (docenteSuplente != null) {
				materia.setDocenteSuplente(null);
			}
			gMateria.modify(materia);
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo desvincular DOCENTES de la MATERIA: " + ex.getMessage());
		}
	}

	public Boolean asignarMateriaAAnio(Materia materia, Long idAnio) throws Exception { // EN ENDPOINT
		// TODO
		// 1 - Obtener el año
		// 2 - Rescatar la lista de materias del año
		// 3 - Agregar la materia a la lista rescatada
		// 4 - Asignar la lista al año llamando a set()
		// 5 - Llamar al modify de gestor de año
		try {
			Anio anio = gAnio.getById(idAnio);
			Set<Materia> materias = anio.getListaMaterias();
			materias.add(materia);
			anio.setListaMaterias(materias);
			gAnio.modify(anio);
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar la MATERIA al AÑO: " + ex.getMessage());
		}
	}

	public Boolean desvincularMateriaDeAnio(Materia materia, Long idAnio) throws Exception { // EN ENDPOINT 
		// TODO
		// 1 - Obtener el año
		// 2 - Rescatar la lista de materias
		// 3 - Remover la materia de esa lista
		// 4 - Asignar mediante set() la lista al año
		// 5 - Llamar al modify del gestor de año y guardarlo  
		try {
			Anio anio = gAnio.getById(idAnio);
			Set<Materia> materias = anio.getListaMaterias();
			materias.remove(materia);
			anio.setListaMaterias(materias);
			gAnio.modify(anio);
		} catch(Exception ex) {
			throw new Exception("No se pudo desvincular la MATERIA al AÑO: " + ex.getMessage());
		}
		return true;
	}
	
 	
	public Boolean addArea(Area area) throws Exception {  // EN ENDPOINT
		try{
			if(area.getIdArea() == null) {
				gArea.add(area);	
			} else {
				gArea.modify(area);
			}
		} catch(Exception ex) {
			throw new Exception("No se pudo dar de alta el ÁREA: " + ex.getMessage());
		}
		return true;
	}
	
	public Boolean deleteArea(Area area) throws Exception { // EN ENDPOINT
		try{
			gArea.delete(area);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar el ÁREA: " + ex.getMessage());
		}
		return true;
	}
	
	public Area getArea(Long idArea) throws Exception { // EN ENDPOINT
		try {
			return gArea.getById(idArea);
		} catch(Exception ex) {
			throw new Exception("No se pudo obtener el ÁREA: " + ex.getMessage());
		}
	}
	
	public List<Area> getAreas(Area example) throws Exception{ // EN ENDPOINT
		try{
			return gArea.getByExample(example);
		}catch(Exception ex){
			throw new Exception("No se pudo obtener el listado de ÁREAS: " + ex.getMessage());
		}
	}
	
	
	
	public Boolean addLlamado(Llamado llamado) throws Exception { // EN ENDPOINT
		try {
			if (llamado.getIdLlamado() == null) {
				gLlamado.add(llamado);
			}
			else {
				gLlamado.modify(llamado);
			}
		} catch (ValidacionException ex) {
			throw ex;			
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta el LLAMADO: " + ex.getMessage());
		}
		return true;
	}
	
	public Boolean deleteLlamado(Llamado llamado) throws Exception{ // EN ENDPOINT
		try {
			gLlamado.delete(llamado);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar el LLAMADO: " + ex.getMessage());
		}
		return true;
	}

	public Llamado getLlamado(Long idLlamado) throws Exception { // EN ENDPOINT
		try {
			return gLlamado.getById(idLlamado);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener el LLAMADO: " + ex.getMessage());
		}
	}
	
	public List<Llamado> getLlamados(Llamado example) throws Exception{ // EN ENDPOINT
		try {
			return gLlamado.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener el listado de LLAMADOS: " + ex.getMessage());
		}
	}
	
	
	public Boolean addMesa(Mesa mesa) throws Exception{ // EN ENDPOINT
		try {
			if (mesa.getIdMesa() == null) {
				gMesa.add(mesa);
			} else {
				gMesa.modify(mesa);
			}
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta la MESA: " + ex.getMessage());
		}
		return true;
	}
	
	public Mesa getMesa(Long idMesa) throws Exception { // EN ENDPOINT
		try {
			return gMesa.getById(idMesa);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtner la MESA: " + ex.getMessage());
		}
	}
	
	public List<Mesa> getMesas(Mesa example) throws Exception{ // EN ENDPOINT
		try {
			return gMesa.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtner las MESAS: " + ex.getMessage());
		}
	}
	
	public Boolean deleteMesa(Mesa mesa) throws Exception { // EN ENDPOINT
		try {
			gMesa.delete(mesa);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar la MESA: " + ex.getMessage());
		}
		return true;
	}

	
	public Boolean asignarMesaALlamado(Mesa mesa, Long idLlamado) throws Exception { // EN ENDPOINT
		//TODO
		try {
			Llamado llamado = gLlamado.getById(idLlamado);
			Set<Mesa> mesas = llamado.getListaMesas();
			mesas.add(mesa);
			llamado.setListaMesas(mesas);
			gLlamado.modify(llamado);
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar la MESA al LLAMADO: " + ex.getMessage());
		}
	}
	
	public Boolean desvincularMesaDeLlamado(Mesa mesa, Long idLlamado) throws Exception { // EN ENDPOINT
		//TODO
		try {
			Llamado llamado = gLlamado.getById(idLlamado);
			Set<Mesa> mesas = llamado.getListaMesas();
			mesas.remove(mesa);
			llamado.setListaMesas(mesas);
			gLlamado.modify(llamado);
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar la MESA al LLAMADO: " + ex.getMessage());
		}
	}
	
	public Boolean asignarDocenteAMesa(Personal personal, Long idMesa) throws Exception { // EN ENDPOINT
		try {
			Mesa mesa = gMesa.getById(idMesa);
			Set<Personal> docentes = mesa.getIntegrantesTribunal();
			docentes.add(personal);
			mesa.setIntegrantesTribunal(docentes);
			gMesa.modify(mesa);
			return true;
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar el DOCENTE al tribunal de la MESA: " + ex.getMessage());
		}
	}
	
	public Boolean desvincularDocenteDeMesa(Personal personal, Long idMesa) throws Exception { // EN ENDPOINT
		try {
			Mesa mesa = gMesa.getById(idMesa);
			Set<Personal> docentes = mesa.getIntegrantesTribunal();
			docentes.remove(personal);
			mesa.setIntegrantesTribunal(docentes);
			gMesa.modify(mesa);
			return true;
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("No se pudo desvincular el DOCENTE del tribunal de la MESA: " + ex.getMessage());
		}
	}
	
	public Boolean asignarMateriaAMesa(Materia materia, Long idMesa) throws Exception { // EN ENDPOINT
		try {
			Mesa mesa = gMesa.getById(idMesa);
			mesa.setMateria(materia);
			gMesa.modify(mesa);
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar la MATERIA a la MESA: " + ex.getMessage());
		}
	}
	
	public Boolean desvincularMateriaDeMesa(Materia materia, Long idMesa) throws Exception { // EN ENDPOINT
		try {
			Mesa mesa = gMesa.getById(idMesa);
			mesa.setMateria(null);
			gMesa.modify(mesa);
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo desvincular la MATERIA de la MESA: " + ex.getMessage());
		}
	}
	
	/*
	 * 
	 * */
	
	
	public void closeSession() throws Exception {
		gAnio.closeSession();
	}
}
