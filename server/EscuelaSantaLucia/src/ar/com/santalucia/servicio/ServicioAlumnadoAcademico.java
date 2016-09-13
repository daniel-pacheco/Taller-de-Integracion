package ar.com.santalucia.servicio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.academico.GestorAnio;
import ar.com.santalucia.aplicacion.gestor.academico.GestorArea;
import ar.com.santalucia.aplicacion.gestor.academico.GestorCurso;
import ar.com.santalucia.aplicacion.gestor.academico.GestorEspecialidad;
import ar.com.santalucia.aplicacion.gestor.academico.GestorInscripcion;
import ar.com.santalucia.aplicacion.gestor.academico.GestorLlamado;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMateria;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMateriaHist;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMesa;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMesaExamenHist;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorBoletinInasistencias;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorBoletinNotas;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorBoletinNotasHist;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorInasistencia;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorNota;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorTrimestre;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorPersonal;
import ar.com.santalucia.dominio.dto.AlumnoDTO;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinInasistencias;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotas;
import ar.com.santalucia.dominio.modelo.desempenio.Nota;
import ar.com.santalucia.dominio.modelo.desempenio.Trimestre;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.excepciones.ValidacionException;

/**
 * Este servicio manipula la interación entre las operaciones que involucra a relaciones entre Alumnos, Cursos y Boletines
 * @author Ariel
 * @version 1.0 
 */
public class ServicioAlumnadoAcademico {
	
	private GestorCurso gCurso;
	private GestorNota gNota;
	private GestorTrimestre gTrimestre;
	private GestorAnio gAnio;
	private GestorBoletinNotas gBoletinNotas;
	private GestorBoletinInasistencias gBoletinInasistencias;
	
	public ServicioAlumnadoAcademico() throws Exception {
		try {
			gCurso = new GestorCurso();
			gNota = new GestorNota();
			gTrimestre = new GestorTrimestre();
			gAnio = new GestorAnio();
			gBoletinNotas = new GestorBoletinNotas();
			gBoletinInasistencias = new GestorBoletinInasistencias();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar el servicio de operaciones básicas: "
					+ ex.getMessage());
		}
	}

	/**
	 * Asigna un alumno determinado a un Curso indicado. Siempre se realiza un pasaje previo por el curso genérico antes de ubicarlo en el Curso objetivo.
	 * @param alumno
	 * @param idCurso
	 * @return
	 * @throws Exception
	 */
	public Boolean asignarAlumnoACurso(Alumno alumno, Long idCurso) throws Exception{
		try {
			Curso curso = gCurso.getById(idCurso);
			Set<Alumno> alumnos = curso.getListaAlumnos();
			alumnos.add(alumno);
			curso.setListaAlumnos(alumnos);
			gCurso.modify(curso);
			Curso cursoGenerico = gCurso.getByDivision('0');
			Set<Alumno> alumnosCursoGenerico = cursoGenerico.getListaAlumnos();
			alumnosCursoGenerico.remove(alumno);
			cursoGenerico.setListaAlumnos(alumnosCursoGenerico);
			gCurso.modify(cursoGenerico);
			crearBoletinNotas(curso, alumno);          // agregar boletin de notas
			crearBoletinInasistencias(curso, alumno);  // agregar boletin de inasistencias
			return true;
		} catch(Exception ex) {
			throw new Exception("No se pudo asignar el ALUMNO al CURSO: " + ex.getMessage());
		}
	}

	/**
	 * Crear el boletin del alumno
	 * @param curso
	 * @param alumno
	 * @throws ValidacionException
	 * @throws Exception
	 */
	private void crearBoletinNotas(Curso curso, Alumno alumno) throws ValidacionException, Exception{
		try{
			Anio anio = this.cursoPerteneceAnio(curso);
			Set<Materia> materias = anio.getListaMaterias();
			BoletinNotas boletinNotas = (BoletinNotas) ServicioDesempenio
					.encontrarBoletinDeAlumno(alumno, ServicioDesempenio.BUSCAR_BOLETIN_NOTAS);
			//SI SE ENCUENTRA UN BOLETÍN, SE ELIMINA
			if (boletinNotas != null) {
				gBoletinNotas.delete(boletinNotas);
			}
			boletinNotas = new BoletinNotas();
			boletinNotas.setPropietario(alumno);
			boletinNotas.setCicloLectivo(Integer.valueOf(anio.getCicloLectivo()));
			boletinNotas.setAnio(anio.getNombre());
			boletinNotas.setCurso(curso.getDivision().toString());
			inicializarBoletinNotas(boletinNotas, materias);
			if (boletinNotas.getIdBoletinNotas() == null) {
				gBoletinNotas.add(boletinNotas);
			} else {
				gBoletinNotas.modify(boletinNotas);
			}
		}catch (ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * Crea un boletín de inasistencias
	 * @param curso
	 * @param alumno
	 * @throws ValidacionException
	 * @throws Exception
	 */
	private void crearBoletinInasistencias(Curso curso, Alumno alumno) throws ValidacionException, Exception{
	try{
		Anio anio = this.cursoPerteneceAnio(curso);
		BoletinInasistencias boletinInasistencias = (BoletinInasistencias) ServicioDesempenio
				.encontrarBoletinDeAlumno(alumno, ServicioDesempenio.BUSCAR_BOLETIN_INASISTENCIAS);
		if (boletinInasistencias != null) {
			gBoletinInasistencias.delete(boletinInasistencias);
		}
		boletinInasistencias = new BoletinInasistencias();
		boletinInasistencias.setPropietario(alumno);
		boletinInasistencias.setAnio(anio.getNombre());
		boletinInasistencias.setCurso(curso.getDivision().toString());
		boletinInasistencias.setActivo(true);
		if (boletinInasistencias.getIdBoletinInasistencias() == null) {
			gBoletinInasistencias.add(boletinInasistencias);
		} else {
			gBoletinInasistencias.modify(boletinInasistencias);
		}
		}catch (ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}

	/**
	 * Actualiza el boletin de notas en caso de se haya realizado una modificacion de materia (Agregar/Quitar)
	 * @throws ValidacionException
	 * @throws Exception
	 */
	void actualizarBoletinNotas(Materia materia) throws ValidacionException, Exception{
		//Localizar todos lo boletines de el año en el cual se insertó la materia
		//Por cada boletin, llamara a inicializarBoletinNotas con el set de materias con un elemento
		try{
			Anio anio = encontrarAnioDeMateria(materia);
			ServicioAlumno sAlumno = new ServicioAlumno();
			List<AlumnoDTO> alumnos =  new ArrayList<AlumnoDTO>();
			alumnos = sAlumno.listAlumnosActivosAnioDTO(anio.getIdAnio());
			Set<Materia> materiaSet = new HashSet<Materia>();
			materiaSet.add(materia);
			for(AlumnoDTO aDTO : alumnos){
				Alumno alumno = sAlumno.getUsuario(aDTO.getIdUsuario());
				BoletinNotas boletinNotas = (BoletinNotas) ServicioDesempenio.encontrarBoletinDeAlumno(alumno, ServicioDesempenio.BUSCAR_BOLETIN_NOTAS);
				inicializarBoletinNotas(boletinNotas,materiaSet);
				gBoletinNotas.modify(boletinNotas);
			}
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * Actualiza el boletin de notas en caso de que se haya quitado una materia.  
	 * @param materia
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	void actualizarBoletinNotasBaja(Materia materia) throws ValidacionException, Exception {
		try {
			Anio anio = encontrarAnioDeMateria(materia);
			ServicioAlumno sAlumno = new ServicioAlumno();
			List<AlumnoDTO> alumnos = sAlumno.listAlumnosActivosAnioDTO(anio.getIdAnio());
			for (AlumnoDTO aDTO : alumnos) {
				Alumno alumno = sAlumno.getUsuario(aDTO.getIdUsuario());
				BoletinNotas boletinNotas = (BoletinNotas) ServicioDesempenio.encontrarBoletinDeAlumno(alumno, ServicioDesempenio.BUSCAR_BOLETIN_NOTAS);
				Set<Trimestre> trimestresBoletin = new HashSet<Trimestre>();
				trimestresBoletin.addAll(boletinNotas.getListaTrimestres());
				Set<Nota> notasFinalesBoletin = new HashSet<Nota>();
				notasFinalesBoletin.addAll(boletinNotas.getListaNotasExamen());
				for (Trimestre t : trimestresBoletin) {
					if (t.getMateria().equals(materia)) {
						boletinNotas.getListaTrimestres().remove(t);
						gTrimestre.delete(t);
						gNota.delete(t.getNotaFinal());
					}
				}
				for (Nota nd : notasFinalesBoletin) {
					if (nd.getMateria().equals(materia)) {
						boletinNotas.getListaNotasExamen().remove(nd);
						gNota.delete(nd);
					}
				}
				gBoletinNotas.modify(boletinNotas);
			}
		} catch(ValidacionException vEx) {
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}

	private void inicializarBoletinNotas(BoletinNotas boletinNotas, Set<Materia> materias) throws Exception {
		for (int i = 0; i < 3; i++) {
			for (Materia m : materias) {
				Nota n = new Nota(null, "", Calendar.getInstance().getTime(), 0F, m, Nota.NOTA_FINAL_TRIMESTRAL);
				gNota.add(n);
				Trimestre t = new Trimestre(null, i+1, null, null, n, m);
				gTrimestre.add(t);
				boletinNotas.getListaTrimestres().add(t);
				Nota nfd = new Nota(null,null,Calendar.getInstance().getTime(),0F,m,Nota.DICIEMBRE); 
				gNota.add(nfd);
				Nota nfm = new Nota(null,null,Calendar.getInstance().getTime(),0F,m,Nota.MARZO); 
				gNota.add(nfm);
				boletinNotas.getListaNotasExamen().add(nfd);
				boletinNotas.getListaNotasExamen().add(nfm);
			} 
		}
	}

	public Boolean desvincularAlumnoDeCurso(Alumno alumno, Long idCurso) throws Exception { // EN ENDPOINT
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
			// modificar boletin de notas
			BoletinNotas boletinNotas = (BoletinNotas) ServicioDesempenio.encontrarBoletinDeAlumno(alumno, ServicioDesempenio.BUSCAR_BOLETIN_NOTAS);
			boletinNotas.setAnio(null);
			boletinNotas.setCurso("0");
			gBoletinNotas.modify(boletinNotas);
			// modificar boletin de inasistencias
			BoletinInasistencias boletinInasistencias = (BoletinInasistencias) ServicioDesempenio.encontrarBoletinDeAlumno(alumno, ServicioDesempenio.BUSCAR_BOLETIN_INASISTENCIAS);
			boletinInasistencias.setAnio(null);
			boletinInasistencias.setCurso("0");
			gBoletinInasistencias.modify(boletinInasistencias);
		} catch(Exception ex) {
			throw new Exception("No se pudo desvincular el ALUMNO del CURSO: " + ex.getMessage());
		}
		return true;
	}
	
	
	// ---------------------- MÉTODOS AUXILIARES PRIVADOS, PÚBLICOS y PACKAGE ---------------------------------------
	
	private Anio cursoPerteneceAnio(Curso curso) throws Exception {
		List<Anio> listaAnio = new ArrayList<Anio>();
		List<Curso> listaCurso = new ArrayList<Curso>(); 
		listaAnio = gAnio.getByExample(new Anio(null,null,null,null,null,null, null, null, true));
		for (Anio a: listaAnio) {
			if(a.getListaCursos().contains(curso)){
				return a;
			}
		}
		return null;
	}

	/**
	 * Devuelve el Año al que pertenece la materia recibida.
	 * @param materia
	 * @return
	 * @throws Exception
	 */
	Anio encontrarAnioDeMateria(Materia materia) throws Exception{
		ServicioAcademico sAcademico = new ServicioAcademico();
		List<Anio> listaAnio = new ArrayList<Anio>();
		Set<Materia>listaMateria = new HashSet<Materia>();
		Anio anioExample = new Anio();  //Establezco un ejemplo de anio
		anioExample.setActivo(true);
		listaAnio = sAcademico.getAnios(anioExample); 
		for(Anio a:listaAnio){
			listaMateria = a.getListaMaterias();
			for(Materia m:listaMateria){
				if((m.getNombre()).equals(materia.getNombre())){
					return a;
				}
			}		
		}
		return null; // No se encontró el año.
	}
}
