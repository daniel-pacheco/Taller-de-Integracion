package ar.com.santalucia.servicio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
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
import ar.com.santalucia.dominio.dto.AnioDTO;
import ar.com.santalucia.dominio.dto.CursoDTO;
import ar.com.santalucia.dominio.dto.DetallePreviaDTO;
import ar.com.santalucia.dominio.dto.InscripcionConsultaDTO;
import ar.com.santalucia.dominio.dto.InscripcionConsultaV2DTO;
import ar.com.santalucia.dominio.dto.InscripcionConsultaV2Detalle;
import ar.com.santalucia.dominio.dto.LlamadoDTO;
import ar.com.santalucia.dominio.dto.MateriaAltaDTO;
import ar.com.santalucia.dominio.dto.MateriaDTO;
import ar.com.santalucia.dominio.dto.MesaAltaDTO;
import ar.com.santalucia.dominio.dto.MesaDTO;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Area;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Especialidad;
import ar.com.santalucia.dominio.modelo.academico.Inscripcion;
import ar.com.santalucia.dominio.modelo.academico.Llamado;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.academico.MateriaHist;
import ar.com.santalucia.dominio.modelo.academico.Mesa;
import ar.com.santalucia.dominio.modelo.academico.MesaExamenHist;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinInasistencias;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotas;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotasHist;
import ar.com.santalucia.dominio.modelo.desempenio.MateriaNotasBoletin;
import ar.com.santalucia.dominio.modelo.desempenio.Nota;
import ar.com.santalucia.dominio.modelo.desempenio.Trimestre;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.rest.FrontMessage;

/**
 * Clase servicio para gestión académica. Engloba Anio, Curso, alumnos y
 * materias
 * 
 * @author ericpennachini
 *
 */

// Último modificador: Ariel Ramirez @ 02-07-2016

public class ServicioAcademico {

	private GestorAnio gAnio;
	private GestorCurso gCurso;
	private GestorMateria gMateria;
	private GestorArea gArea;
	private GestorAlumno gAlumno;
	private GestorPersonal gDocente;
	private GestorLlamado gLlamado;
	private GestorMesa gMesa;
	private GestorMateriaHist gMateriaHistorica;
	private GestorMesaExamenHist gMEHist;
	private GestorBoletinNotasHist gBoletinHist;
	private GestorNota gNota;
	private GestorTrimestre gTrimestre;
	private GestorBoletinNotas gBoletinNotas;
	private GestorInasistencia gInasistencia;
	private GestorBoletinInasistencias gBoletinInasistencias;
	private GestorInscripcion gInscripcion;
	private GestorEspecialidad gEspecialidad;

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
			gMateriaHistorica = new GestorMateriaHist();
			gMEHist = new GestorMesaExamenHist();
			gBoletinHist = new GestorBoletinNotasHist();
			gNota = new GestorNota();
			gTrimestre = new GestorTrimestre();
			gBoletinNotas = new GestorBoletinNotas();
			gInasistencia = new GestorInasistencia();
			gBoletinInasistencias = new GestorBoletinInasistencias();
			gInscripcion = new GestorInscripcion();
			gEspecialidad = new GestorEspecialidad();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar el servicio de operaciones básicas: "
					+ ex.getMessage());
		}
	}
	

	public Boolean addAnio(Anio anio) throws ValidacionException, Exception {	// EN ENDPOINT
		try {
			ValidacionException vEx = new ValidacionException();	// Inicio Bloque verificación de especialidad
			if(anio.getEspecialidad() == null){
				vEx.addMensajeError("No se puede cargar un año sin especialidad. La especialidad es obligatoria.");
				throw vEx;
			}else{
				Especialidad aux = gEspecialidad.getById(anio.getEspecialidad().getIdEspecialidad()); //
				if(aux == null){
					vEx.addMensajeError("La especialidad no existe.");
					throw vEx;
				}
			}														// Fin Bloque verificación de especialidad
			
			if (anio.getIdAnio() == null) {
				gAnio.add(anio);
			}
			else {
				Anio anioAux = new Anio();
				anioAux = getAnio(anio.getIdAnio());
				if ( ! ((anioAux.getNombre()).equals(anio.getNombre())) || ! ((anioAux.getCicloLectivo()).equals(anio.getCicloLectivo())) ){
					crearMateriaHist(anioAux);
				}
				gAnio.modify(completarAnioPersistente(anio));
			}
			
		} 
		catch(ValidacionException ex){
			throw ex;
		}catch(NullPointerException ex){
			throw new Exception("No se pudo dar de alta el AÑO");
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
	
	
	public ArrayList<AnioDTO> getAniosDTO() throws Exception {
		try {
			ArrayList<Anio> listaAniosPersis = gAnio.getByExample(new Anio());
			ArrayList<AnioDTO> listaAniosDTO = new ArrayList<AnioDTO>();
			
			for (Anio a : listaAniosPersis) {
				AnioDTO aDTO = new AnioDTO();
				aDTO.setIdAnio(a.getIdAnio());
				aDTO.setNombre(a.getNombre());
				aDTO.setDescripcion(a.getDescripcion());
				aDTO.setEspecialidad(a.getEspecialidad().getNombre());
				aDTO.setOrden(a.getOrden());
				aDTO.setCicloLectivo(a.getCicloLectivo());
				aDTO.setActivo(a.getActivo());
				Set<Curso> listaCursosAnioPersis = a.getListaCursos();
				for (Curso c : listaCursosAnioPersis) {
					CursoDTO cDTO = new CursoDTO();
					cDTO.setIdCurso(c.getIdCurso());
					cDTO.setDivision(c.getDivision().toString());
					cDTO.setTurno(c.getTurno());
					cDTO.setCantAlu(c.getListaAlumnos().size());
					aDTO.getListaCursos().add(cDTO);
				}
				listaAniosDTO.add(aDTO);
			}
			
			return listaAniosDTO;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al obtener la lista de años resumida: " + ex.getMessage());
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
				gCurso.add(curso);
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
	public Boolean modifyCurso(Curso curso) throws Exception {			//CANDIDATO A SUPRIMIR PORQUE addCurso TIENE LÓGICA NECESARIA PARA MODIFICAR
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
		try {
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
			// agregar boletin de notas
			Anio anio = this.cursoPerteneceAnio(curso);
			Set<Materia> materias = anio.getListaMaterias();
			BoletinNotas boletinNotas = (BoletinNotas) ServicioDesempenio
					.encontrarBoletinDeAlumno(alumno, ServicioDesempenio.BUSCAR_BOLETIN_NOTAS);
			if (boletinNotas == null) {
				boletinNotas = new BoletinNotas();
				boletinNotas.setPropietario(alumno);
				boletinNotas.setCicloLectivo(Integer.valueOf(anio.getCicloLectivo()));
				inicializarBoletinNotas(boletinNotas, materias);
			}
			boletinNotas.setAnio(anio.getNombre());
			boletinNotas.setCurso(curso.getDivision().toString());
			if (boletinNotas.getIdBoletinNotas() == null) {
				gBoletinNotas.add(boletinNotas);
			} else {
				gBoletinNotas.modify(boletinNotas);
			}
			// agregar boletin de inasistencias
			BoletinInasistencias boletinInasistencias = (BoletinInasistencias) ServicioDesempenio
					.encontrarBoletinDeAlumno(alumno, ServicioDesempenio.BUSCAR_BOLETIN_INASISTENCIAS);
			if (boletinInasistencias == null) {
				boletinInasistencias = new BoletinInasistencias();
				boletinInasistencias.setPropietario(alumno);
			}
			boletinInasistencias.setAnio(anio.getNombre());
			boletinInasistencias.setCurso(curso.getDivision().toString());
			boletinInasistencias.setActivo(true);
			if (boletinInasistencias.getIdBoletinInasistencias() == null) {
				gBoletinInasistencias.add(boletinInasistencias);
			} else {
				gBoletinInasistencias.modify(boletinInasistencias);
			}
			return true;
		} catch(Exception ex) {
			throw new Exception("No se pudo asignar el ALUMNO al CURSO: " + ex.getMessage());
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

	
	
	public Boolean addMateria(MateriaAltaDTO materiaAltaDTO) throws Exception {
		// DECLARO AUXILIARES
		Area areaAux = new Area();
		Anio anioAux = new Anio();
		Materia materiaAux = new Materia();
		Personal docenteTitular = new Personal();
		Personal docenteSuplente = new Personal();
		
		// VERIFICO EXISTENCIA DEL ANIO. SI NO SE ENCUENTRA LANZO UNA EXCEPCIÓN 
		anioAux = gAnio.getById(materiaAltaDTO.getIdAnio());
		if (anioAux == null){
			throw new Exception("SERVICIO: No se pudo dar de alta la materia. No se encontró el año.");
		}
		// TOMO EL AREA, SI ES NULL, CARGA NULL Y NO HAY PROBLEMA
		areaAux = materiaAltaDTO.getArea();

		// BUSQUEDA DE DOCENTES (POR ID)
		if(materiaAltaDTO.getIdDocenteTitular() != null){
			docenteTitular = (Personal) gDocente.getById(materiaAltaDTO.getIdDocenteTitular());
		}
		if (materiaAltaDTO.getIdDocenteSuplente() != null){
			docenteSuplente = (Personal) gDocente.getById(materiaAltaDTO.getIdDocenteSuplente());
		}
		// ARMO LA MATERIA PARA PERSISTIR
		materiaAux.setIdMateria(materiaAltaDTO.getIdMateria());			//PUEDE SER NULL O VENIR CON VALOR (MODIFY)
		materiaAux.setNombre(materiaAltaDTO.getNombreMateria());
		materiaAux.setDescripcion(materiaAltaDTO.getDescripcion());
		materiaAux.setDocenteTitular(docenteTitular);
		materiaAux.setDocenteSuplente(docenteSuplente);
		materiaAux.setArea(areaAux);
		materiaAux.setActivo(materiaAltaDTO.getActivo());
		if(materiaAux.getIdMateria() != null){						// SI YA EXISTE LA MATERIA
			if(materiaAltaDTO.getIdAnio() != null){					// Y ADEMÁS VIENE CON AÑO
				desvincularMateriaDeAnio(materiaAux, materiaPerteneceAnio(materiaAux));	// SI EXISTE LA MATERIA, VIENE CON AÑO, ENTONCES DESVINCULO ANTES
				asignarMateriaAAnio(materiaAux, materiaAltaDTO.getIdAnio());			// VUELVO A VINCULAR AL NUEVO AÑO
			}
			gMateria.modify(materiaAux);
			return true;
		}else{														// SI NO EXISTE LA MATERIA CREO Y VINCULO
			gMateria.add(materiaAux);
			asignarMateriaAAnio(materiaAux, materiaAltaDTO.getIdAnio());
			return true;
		} 
	}
	
	public Boolean deleteMateria(Materia materia) throws Exception { // EN ENDPOINT
		try {
			gMateria.delete(materia);
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar la MATERIA: " + ex.getMessage());
		}
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
	
	public ArrayList<MateriaDTO> getMateriasDTO() throws Exception {
		try {
			ArrayList<Materia> listaMateriasPersis = new ArrayList<Materia>(gMateria.List());
			ArrayList<MateriaDTO> listaMateriasDto = new ArrayList<MateriaDTO>();
			
			for (Materia mp : listaMateriasPersis) {
				MateriaDTO materiaDTO = new MateriaDTO();
				//BEGIN logica para llenar campos
				materiaDTO.setIdMateria(mp.getIdMateria());
				materiaDTO.setNombre(mp.getNombre());
				materiaDTO.setDescripcion(mp.getDescripcion());
				materiaDTO.setDocenteTitular((mp.getDocenteTitular() != null)
												? mp.getDocenteTitular().toString()
												:"");
				materiaDTO.setDocenteSuplente((mp.getDocenteSuplente() != null)
												? mp.getDocenteSuplente().toString()
												:"");
				materiaDTO.setArea(mp.getArea().getNombre());
				for (Anio a : this.getAnios(new Anio())) { // recupero la lista completa de años
					if (a.getListaMaterias().contains(mp)) {
						materiaDTO.setAnio(a.getNombre());
					}
				}
				//END logica para llenar campos
				listaMateriasDto.add(materiaDTO);
			}
			
			return listaMateriasDto;
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista DTO de MATERIAS: " + ex.getMessage());
		}
	}
	
	public MateriaDTO getMateriaDTO(Long idMateria) throws Exception{
		try{
			List<MateriaDTO> listadoCompleto = getMateriasDTO();
			for(MateriaDTO mDTO : listadoCompleto){
				if(mDTO.getIdMateria().equals(idMateria)){
					return mDTO;
				}
			}
			return null;
		}catch (Exception ex){
			throw ex;
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
	
	public ArrayList<Area> getAreas(Area example) throws Exception{ // EN ENDPOINT
		try{
			return gArea.getByExample(example);
		}catch(Exception ex){
			throw new Exception("No se pudo obtener el listado de ÁREAS: " + ex.getMessage());
		}
	}
	
	
	public Boolean addEspecialidad(Especialidad especialidad) throws Exception, ValidacionException{
		try{
			if(especialidad.getIdEspecialidad() == null){
				gEspecialidad.add(especialidad);
				return true;
			}else{
				gEspecialidad.modify(especialidad); 
				return true;
			}
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	
	public Boolean deleteEspecialidad(Especialidad especialidad) throws Exception, ValidacionException{
		try{
			especialidadOcupada(especialidad);
			gEspecialidad.delete(especialidad);
			return true;
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw new Exception("No se pudo eliminar el ÁREA: " + ex.getMessage());
		}
	}
	
	public Especialidad getEspecialidadById(Long idEspecialidad) throws Exception{
		try{
			return gEspecialidad.getById(idEspecialidad);
		}catch(Exception ex){
			throw new Exception("No se pudo obtener la ESPECIALIDAD: " + ex.getMessage());
		}
	}
	
	public ArrayList<Especialidad> getEspecialidad(Especialidad example) throws Exception{
		try{
			return gEspecialidad.getByExample(example);
		}catch(Exception ex){
			throw new Exception("No se pudo obtener el listado de ESPECIALIDADES: " + ex.getMessage());
		}
	}

	
	/**
	 * Verifica si una especialidad está siendo ocupada por algún año.
	 * @param especialidad
	 * @throws Exception
	 * @throws ValidacionException
	 */
	private void especialidadOcupada(Especialidad especialidad) throws Exception, ValidacionException{
		try{
			ValidacionException vEx = new ValidacionException();
			List<Anio> listadoAnio = gAnio.getByExample(new Anio(null,null,null,null,null,null,null,null,true));
			if(listadoAnio!= null && listadoAnio.size() > 0){
				for (Anio a : listadoAnio){
					if(a.getEspecialidad().equals(especialidad)){
						vEx.addMensajeError(a.getNombre());
					}
				}
			}
			if(vEx.getMensajesError().size() > 0){
				throw vEx;
			}
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	public Boolean addLlamado(Llamado llamado) throws Exception { // EN ENDPOINT
		try {
			ServicioConfiguracion.comprendidoEnPeriodo(llamado.getFechaInicio(), llamado.getFechaFin(),null,null,null);
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
	
	public Llamado getLlamado(String descLlamado) throws Exception{
		return getLlamados(new Llamado(null,descLlamado,null,null,null)).get(0);
	}
	
	public ArrayList<Llamado> getLlamados(Llamado example) throws Exception{ // EN ENDPOINT
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
	
	public Boolean addMesa(MesaAltaDTO mesaAltaDTO) throws Exception{
		try{
			Llamado llamado = gLlamado.getById(mesaAltaDTO.getIdLlamado());
			if(llamado!=null && !(llamado.getIdLlamado() == null)){
				ServicioConfiguracion.comprendidoEnPeriodo(mesaAltaDTO.getFechaHoraInicio(), mesaAltaDTO.getFechaHoraFin(), null, llamado.getFechaInicio(), llamado.getFechaFin());
				Personal docente1 = gDocente.getByExample(new Personal(mesaAltaDTO.getTribunalDoc1(),null,null,null,null,null,null,null,null,null,null,true,null,null,null,null)).get(0);
				Personal docente2 = gDocente.getByExample(new Personal(mesaAltaDTO.getTribunalDoc2(),null,null,null,null,null,null,null,null,null,null,true,null,null,null,null)).get(0);
				Personal docente3 = gDocente.getByExample(new Personal(mesaAltaDTO.getTribunalDoc3(),null,null,null,null,null,null,null,null,null,null,true,null,null,null,null)).get(0);
				Materia materia = gMateria.getById(mesaAltaDTO.getIdMateria());
				Set<Mesa> mesasLlamado = llamado.getListaMesas();
				for(Mesa m : mesasLlamado){
					if(m.getMateria().equals(materia)){
						ValidacionException ex = new ValidacionException();
						ex.addMensajeError("Ya existe la mesa en el llamado.");
						throw ex;
					}
				}
				Mesa mesa = new Mesa();
				mesa.setFechaHoraInicio(mesaAltaDTO.getFechaHoraInicio());
				mesa.setFechaHoraFin(mesaAltaDTO.getFechaHoraFin());
				Set<Personal> tribunal = new HashSet<Personal>();
				tribunal.add(docente1);
				tribunal.add(docente2);
				tribunal.add(docente3);
				mesa.setIntegrantesTribunal(tribunal);
				mesa.setMateria(materia);
				gMesa.add(mesa);
				llamado.getListaMesas().add(mesa);
				gLlamado.modify(llamado);
			}else{
				ValidacionException ex = new ValidacionException();
				ex.addMensajeError("No se encontró el llamado.");
				throw ex;
			}
		}catch(ValidacionException ex){
			ex.addMensajeError("Ocurrió un problema al intentar crear una mesa y asociarla al llamado.");
			throw ex;
		}catch(Exception ex){
			throw ex;
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

	
	/**
	 * Agrega una inscripción a mesa (No agrega una entidad inscripción al menos que sea necesario)<br>
	 * Quita y agrega elementos del listado de Mesa de la entidad Inscripción si este existe.<br>
	 * No permite operar si no existe periodo de inscripción a Mesa vigente.
	 * @param idMesa
	 * @param idAlumno
	 * @return
	 * @throws Exception
	 */
	public Boolean addInscripcion(Long idMesa, Long idAlumno) throws Exception{
		try {
			Alumno alumno = (Alumno) gAlumno.getById(idAlumno);
			Mesa mesa = gMesa.getById(idMesa);
			if(mesa == null){
				ValidacionException ex = new ValidacionException();
				ex.addMensajeError("La mesa solicitada no existe.");
				throw ex;
			}
			Calendar fechaActual = Calendar.getInstance();
			Calendar fechaLlamadoInicio = Calendar.getInstance(); 
			Calendar fechaLlamadoFin = Calendar.getInstance();
			Llamado llamado = encontrarLlamadoVigente();  //Encuentra el llamado solo si está en periodo vigente de inscripcion
			
			
			if (llamado != null){
				Inscripcion inscripcion = buscarInscripcion(idAlumno, llamado.getIdLlamado());
				if (inscripcion == null){
					fechaLlamadoInicio.setTime(llamado.getFechaInicio());
					fechaLlamadoFin.setTime(llamado.getFechaFin());
					if ( fechaActual.equals(fechaLlamadoInicio) || fechaActual.before(fechaLlamadoInicio) ){ //Para verificar que no esté intentando inscibirse durante la mesa   			
						inscripcion = new Inscripcion();
						inscripcion.setCodigo(this.codigoSiguienteInscripcion(llamado.getIdLlamado()));
						inscripcion.setIdLlamado(llamado.getIdLlamado());
						inscripcion.setActivo(true);
						inscripcion.setAlumno(alumno);
						inscripcion.setFecha(fechaActual.getTime());
						gInscripcion.add(inscripcion); //Creo la inscripción sin lista de Mesas
						Set<Mesa> listaMesa = new HashSet<Mesa>();
						listaMesa.add(mesa);			
						inscripcion.setListaMesas(listaMesa);
						gInscripcion.modify(inscripcion);   //Aca se vincula la inscripcion con la mesa
					}
				}else{
					inscripcion.getListaMesas().add(mesa);
					gInscripcion.modify(inscripcion);
				}
			}else{
				ValidacionException ex = new ValidacionException();
				ex.addMensajeError("No existe periodo de inscripción a llamado vigente.");
				throw ex;
			}
		} 
		catch(ValidacionException ex){
			throw ex;
		}
		catch (Exception ex) {
			throw new Exception("No se pudo agregar la Inscripcion: " + ex.getMessage());
			//e.printStackTrace();
		}
		return true;
	}
	/**
	 * Elimina las inscripciones a mesa de un llamado. Si la lista de mesas inscriptas es cero se elimina la Inscripción.
	 * @param idMesa
	 * @param idAlumno
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteInscripcion(Long idMesa, Long idAlumno) throws Exception{
		try{
			Mesa mesa = gMesa.getById(idMesa);
			if (mesa == null){
				ValidacionException ex = new ValidacionException();
				ex.addMensajeError("La mesa solicitada no existe.");
				throw ex;
			}
			Llamado llamado = encontrarLlamadoVigente();  //Encuentra el llamado solo si está en periodo vigente de inscripcion
			
			if (llamado != null){
				Inscripcion inscripcion = buscarInscripcion(idAlumno, llamado.getIdLlamado());
				if (inscripcion != null){
					Set<Mesa> listaMesa = inscripcion.getListaMesas();
					if (listaMesa.contains(mesa)){
						listaMesa.remove(mesa);
						if (listaMesa.size() == 0 ){
							gInscripcion.delete(inscripcion);
						}else{
							inscripcion.setListaMesas(listaMesa);
							gInscripcion.modify(inscripcion);
						}
					}
				}else{
					ValidacionException ex = new ValidacionException();
					ex.addMensajeError("La inscripción no existe.");
					throw ex;
				}
			}else{
				ValidacionException ex = new ValidacionException();
				ex.addMensajeError("No existe periodo de inscripción a llamado vigente.");
				throw ex;
			}
		}catch (Exception ex){
			throw new Exception("No se pudo eliminar la inscripicón a mesa solicitada. " + ex.getMessage());
		}
		return true;
	}
	
	/**
	 * Lista las mesas a la que el alumno puede inscribirse, teniendo en cuenta las amterias previas del mismo.
	 * @param idAlumno
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	public List<InscripcionConsultaDTO> listarInscribibles(Long idAlumno) throws ValidacionException, Exception{
		try{
			Alumno alumno = (Alumno)gAlumno.getById(idAlumno); // Hubo un problema al obtener el alumno
			if (alumno == null){
				ValidacionException ex = new ValidacionException();
				ex.addMensajeError("No se ha encontrado el alumno.");
				throw ex;
			}			
			Llamado llamadoVigente = encontrarLlamadoVigente(); // No hay llamado vigente a la fecha
			if(llamadoVigente == null){
				ValidacionException ex = new ValidacionException();
				ex.addMensajeError("No existen llamados vigentes.");
				throw ex;
			}			
			List<DetallePreviaDTO> previas = getPreviasDesaprobadas(alumno.getNroDocumento()); // El alumno no tiene previas
			if(previas.size()==0){
				ValidacionException ex = new ValidacionException();
				ex.addMensajeError("No hay materias previas para inscribirse.");
				throw ex;
			}
			Set<Mesa> mesas = llamadoVigente.getListaMesas();           //Mesas del llamado vigente
			List<InscripcionConsultaDTO> inscribibles = new ArrayList<InscripcionConsultaDTO>();
			for(Mesa m: mesas){											//Por cada mesa, recorro las previas en busca de coincidencia de nombre de materia
				for(DetallePreviaDTO p: previas){
					String tribunal = new String();
					InscripcionConsultaDTO aux = new InscripcionConsultaDTO();
					if( (m.getMateria().getNombre()).equals(p.getNombreMateria()) ){ 
						Calendar calendar = Calendar.getInstance();
						aux.setAlumno(alumno.toString());
						aux.setIdMesa(m.getIdMesa());
						aux.setInscripto(false); // No se si está inscripto todavía, lo dejo en false
						DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
					    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
						calendar.setTime(m.getFechaHoraInicio());
						aux.setFecha(formatoFecha.format(calendar.getTime()));
						calendar.setTime(m.getFechaHoraInicio());
						aux.setHoraInicio( formatoHora.format(calendar.getTime()) );
						calendar.setTime(m.getFechaHoraFin());
						aux.setHoraFin(formatoHora.format(calendar.getTime()) );
						aux.setMateria(m.getMateria().getNombre());
						aux.setDescripcionMateria(m.getMateria().getDescripcion());
						Set<Personal> iTribunal = m.getIntegrantesTribunal();
						for(Personal tri : iTribunal){
							tribunal = tribunal + tri.getApellido() +", "+tri.getNombre() + "; ";
						}
						aux.setTribunal(tribunal);
						
						List<MateriaDTO> materiasAux = getMateriasDTO();   //Capturo el nombre de la materia
						for(MateriaDTO mDTO : materiasAux){
							if(m.getMateria().getNombre().equals(mDTO.getNombre())){
								aux.setAnioMateria(mDTO.getAnio());
								break;
							}
						}
						
						inscribibles.add(aux);
					}
				}
			}
			
			if(inscribibles.size() == 0){									// Tiene previas, pero las mesas no están disponibles o la materia no existe más
				ValidacionException ex = new ValidacionException();
				String cadenaPrevias = new String();
				/*Iterator<DetallePreviaDTO> it = previas.iterator();
				while (it.hasNext()){
					cadenaPrevias = cadenaPrevias + " " +it.next().getNombreMateria()+" - ("+it.next().getAnio()+") | ";
				}*/
				for(DetallePreviaDTO i : previas){
					cadenaPrevias = cadenaPrevias + i.getNombreMateria()+" - ("+i.getAnio()+") | ";
				}
				ex.addMensajeError("No existen mesas disponibles para las previas encontradas: " + cadenaPrevias);
				throw ex;
			}
			
			Inscripcion inscripcion = buscarInscripcion(idAlumno, llamadoVigente.getIdLlamado());	// Busco las incripciones para ver el estado
			Set<Mesa> mesasInscriptas = new HashSet<Mesa>();
			
			if(inscripcion !=null){ // Si existe la inscripción tiene por lo menos una mesa adentro 
				mesasInscriptas = inscripcion.getListaMesas();
				for(Mesa minsc : mesasInscriptas){
					for(InscripcionConsultaDTO i : inscribibles){			// Seteo en true las que están inscriptas
						if(minsc.getIdMesa().equals(i.getIdMesa())){
							i.setInscripto(true);
						}
					}
				}
			}
			
			return inscribibles;
			
		}catch(ValidacionException ex){
			throw ex;
		}
		catch(Exception ex){
			throw ex;
		}
	} 
	
	/**
	 *  Lista las mesas a la que el alumno puede inscribirse, teniendo en cuenta las amterias previas del mismo, en formato InscripcionConsultaV2DTO.
	 * @param dniAlumno
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	public InscripcionConsultaV2DTO listarInscribiblesV2(Long dniAlumno) throws ValidacionException, Exception{
	try{
		// Obtener el id del alumno
		// Obtener lista de inscribibles InscripcionConsultaDTO
		// formatear con el nuevo dto. Devolver.
		InscripcionConsultaV2DTO inscribiblesDTO = new InscripcionConsultaV2DTO();
		List<InscripcionConsultaV2Detalle> detalle = new ArrayList<InscripcionConsultaV2Detalle>();
	
		ServicioAlumno sAlumno = new ServicioAlumno();
		Alumno alumno = new Alumno();
		alumno = sAlumno.getUsuarioByDni(dniAlumno);
		if(alumno == null){
			ValidacionException vEx = new ValidacionException();
			vEx.addMensajeError("El dni de alumno solicitado no existe.");
			throw vEx;
		}
		List<InscripcionConsultaDTO> listado = new ArrayList<InscripcionConsultaDTO>();
		listado = listarInscribibles(alumno.getIdUsuario());
		inscribiblesDTO.setAlumno(alumno.toString());
		inscribiblesDTO.setIdAlumno(alumno.getIdUsuario());
		for(InscripcionConsultaDTO insDTO : listado){
			InscripcionConsultaV2Detalle itemDetalle = new InscripcionConsultaV2Detalle();
			itemDetalle.setAnioMateria(insDTO.getAnioMateria());
			itemDetalle.setDescripcionMateria(insDTO.getDescripcionMateria());
			itemDetalle.setFecha(insDTO.getFecha());
			itemDetalle.setHoraInicio(insDTO.getHoraInicio());
			itemDetalle.setHoraFin(insDTO.getHoraFin());
			itemDetalle.setIdMesa(insDTO.getIdMesa());
			itemDetalle.setInscripto(insDTO.getInscripto());
			itemDetalle.setMateria(insDTO.getMateria());
			itemDetalle.setTribunal(insDTO.getTribunal());
			detalle.add(itemDetalle);
		}
		inscribiblesDTO.setDetalle(detalle);
		return inscribiblesDTO;
		}catch(ValidacionException ex){
			throw ex;
		}
		catch(Exception ex){
			throw ex;
		}
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
	/**
	 * Obtiene sólo las previas que no han sido aprobadas aún.
	 * @param numeroDni
	 * @param nombreMateria
	 * @return
	 */
	public List<DetallePreviaDTO> getPreviasDesaprobadas(Long numeroDni) throws Exception{
		// Recorrer el listado de DTOs en busca de nota mayor o igual a 6
		// Si nota es mayor a 6, agregar al listado de aprobadas
		// Luego, por cada aprobada eliminar del listado original de Previas (previa sobrecarga del equals, atributo anio y nombremateria)
		
		// Preparo la lista para las aprobadas
		List<DetallePreviaDTO> listaAprobadas = new ArrayList<DetallePreviaDTO>();
		// Pido a getPrevias que traiga el historial de desaprobadas e histórico de previas
		List<DetallePreviaDTO> listaPreviaDto = getPrevias(numeroDni);
		for (DetallePreviaDTO dpdto : listaPreviaDto){
			if (dpdto.getNota() != null) {
				if (dpdto.getNota() >= 6) {
					listaAprobadas.add(dpdto);
				}
			}
		}
		// Quito las aprobadas del listado de previa
		if(!listaAprobadas.isEmpty()){
			listaPreviaDto.removeAll(listaAprobadas);
		}
		
		// Elimino los duplicados, si existiera
		Set<DetallePreviaDTO> setListadoDevolver = new HashSet<DetallePreviaDTO>();
		if (!listaPreviaDto.isEmpty()) {
			for (DetallePreviaDTO dpdto : listaPreviaDto) {
				dpdto.setNota(null);
				dpdto.setFechaInscripcion(null);
				dpdto.setAsistencia(null);
				setListadoDevolver.add(dpdto);
			}
		}
		List<DetallePreviaDTO>listaPreviaDtoDevolver = new ArrayList<DetallePreviaDTO>();
		listaPreviaDtoDevolver.addAll(setListadoDevolver);
		return listaPreviaDtoDevolver;
	}
	
	/**
	 * Obtiene todas las previas y la historia de su rendida, desde el momento que se debió la materia hasta que la aprobó.
	 * @param numeroDni
	 * @return
	 * @throws Exception
	 */
	public ArrayList<DetallePreviaDTO> getPrevias(Long numeroDni) throws Exception{
		Float promedio = 0F;
		ArrayList<DetallePreviaDTO> listadoPrevias = new ArrayList<DetallePreviaDTO>();
		//DetallePreviaDTO detallePreviaDTO = new DetallePreviaDTO();
		BoletinNotasHist boletin = new BoletinNotasHist();
		// CAPTURO LOS BOLETINES HISTÓRICOS DEL ALUMNO
		boletin.setDniAlumno(numeroDni);
		ArrayList<BoletinNotasHist> listaBoletines = gBoletinHist.getByExample(boletin);
		// INICIALIZO PARA EL ARREGLO DE NOTAS TRIMESTRALES (FILA MATERIA, NOTAS TRIMESTRES)
		Set<MateriaNotasBoletin> listaMnb = new HashSet<MateriaNotasBoletin>();
		for (BoletinNotasHist bnh : listaBoletines){
			listaMnb = bnh.getListaMateriasNotasBoletin();
			// RECORRO CADA bhn BoletinNotaHistorico
			for(MateriaNotasBoletin mnb : listaMnb){
				promedio = (float) (mnb.getNotaTrimestre1() + mnb.getNotaTrimestre2() + mnb.getNotaTrimestre3())/3F;
				if( (mnb.getNotaTrimestre3() < 6) || (promedio < 6) ){
					if ( (mnb.getNotaDiciembre()==null) || (mnb.getNotaDiciembre() < 6) ) {
						if( (mnb.getNotaMarzo()==null) || mnb.getNotaMarzo() < 6) {
							// HAY QUE BUSCAR EL REGISTRO HISTÓRICO DE "RENDIDAS"
							ArrayList<MesaExamenHist> listadoMEH = obtenerHistoricoRendidas(numeroDni, mnb.getMateria(), bnh.getCicloLectivo());
							// Para comprobar si lo rindió alguna vez, se busca en el historico de mesas
							// Si no lo rindió nunca (historico vacio) entonces se toman los datos de la libreta
							if (!listadoMEH.isEmpty()) {
								for (MesaExamenHist lmeh : listadoMEH) {
									DetallePreviaDTO detallePreviaDTO = new DetallePreviaDTO(); // Lo puse aca porque sino se reescribía el objeto
									detallePreviaDTO.setNombreMateria(lmeh.getNombreMateria());
									detallePreviaDTO.setCicloLectivoMateria(lmeh.getCicloLectivoMateria());
									detallePreviaDTO.setDniAlumno(lmeh.getDniAlumno());
									detallePreviaDTO.setAnio(lmeh.getAnio());
									detallePreviaDTO.setAsistencia(lmeh.getAsistencia());
									detallePreviaDTO.setFechaInscripcion(lmeh.getFechaInscripcion());
									detallePreviaDTO.setNota(lmeh.getNota());
									listadoPrevias.add(detallePreviaDTO);
								}
							}else{
								DetallePreviaDTO detallePreviaDTO = new DetallePreviaDTO();
								detallePreviaDTO.setNombreMateria(mnb.getMateria());
								detallePreviaDTO.setCicloLectivoMateria(bnh.getCicloLectivo());
								detallePreviaDTO.setDniAlumno(bnh.getDniAlumno());
								detallePreviaDTO.setAnio(bnh.getAnio());
								listadoPrevias.add(detallePreviaDTO);
							}
						}
					}
				}
			}
		}
		return listadoPrevias;
	};
	
	
	// ##### MÉTODOS AUXILIARES #####
	
	/**
	 * Crear un registro histórico en MateriaHist.
	 * @param elemento
	 * @throws Exception
	 */
	private void crearMateriaHistorica(Object elemento) throws Exception{
		if(elemento instanceof Materia){  
			crearMateriaHist((Materia)elemento); 						// Si se recibe una Materia
		}else{
			if(elemento instanceof Anio){
				crearMateriaHist((Anio)elemento); 					// Si se recibe un Año
			}else{
				throw new Exception("No se pudo crear el histórico");
			}
		}
	}
	
	/**
	 * Crear un registro histórico en MateriaHist si se recibe una Materia.
	 * @param materia
	 * @return
	 * @throws Exception
	 */
	private Boolean crearMateriaHist(Materia materia) throws Exception{
		/* Cargamos datos de la materia al histórico */
		MateriaHist materiaHistorica = new MateriaHist();
		materiaHistorica.setNombreMateria(materia.getNombre());
		materiaHistorica.setDescripcionMateria(materia.getDescripcion());
		/* Localizar el año */
		Anio anio = encontrarAnioDeMateria(materia); // Método nuevo
		if (anio != null){
			materiaHistorica.setNombreAnio(anio.getNombre());
			materiaHistorica.setCicloLectivo(anio.getCicloLectivo());
			gMateriaHistorica.add(materiaHistorica); // Persistir
			return true;
		}
		return false;	
	}
	
	/**
	 * Devuelve el Año al que pertenece la materia recibida.
	 * @param materia
	 * @return
	 * @throws Exception
	 */
	private Anio encontrarAnioDeMateria(Materia materia) throws Exception{
		List<Anio> listaAnio = new ArrayList<Anio>();
		Set<Materia>listaMateria = new HashSet<Materia>();
		Anio anioExample = new Anio();  //Establezco un ejemplo de anio
		anioExample.setActivo(true);
		listaAnio = getAnios(anioExample); // :O tendría que ser listAnio() y que traiga solo los activos;
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
	
	/**
	 * Crear un registro histórico en MateriaHist si se recibe un Anio.
	 * @param anio
	 * @return
	 * @throws Exception
	 */
	private Boolean crearMateriaHist(Anio anio) throws Exception{
		Set<Materia> listaMaterias = anio.getListaMaterias();
		MateriaHist materiaHistorica = new MateriaHist();
		materiaHistorica.setNombreAnio(anio.getNombre());
		materiaHistorica.setCicloLectivo(anio.getCicloLectivo());
		for (Materia m:listaMaterias){
			materiaHistorica.setNombreMateria(m.getNombre());
			materiaHistorica.setDescripcionMateria(m.getDescripcion());
			gMateriaHistorica.add(materiaHistorica);
		}
		return true;
	}
	
	/**
	 * Realiza la modificación de una entidad Anio que viene con datos propios, no agregados.
	 * @param anioModif
	 * @return Entidad Anio completa desde la entidad persistente, con los campos indicados modificados.
	 * @throws Exception
	 */
	private Anio completarAnioPersistente(Anio anioModif) throws Exception{
		// RECUPERAR POR ID EL AÑO COMPLETO
		// MODIFICAR LOS DATOS
		// DEVOLVER LA ENTIDAD MODIFICADA
		Anio anioAux = this.getAnio(anioModif.getIdAnio()); 	// Obtengo el persistente (no copio el id porque ya viene con el objeto)
		anioAux.setNombre(anioModif.getNombre());				// Copio los datos de la modificación al persistente copiado
		anioAux.setDescripcion(anioModif.getDescripcion());
		anioAux.setCicloLectivo(anioModif.getCicloLectivo());
		anioAux.setEspecialidad(anioModif.getEspecialidad());
		anioAux.setActivo(anioModif.getActivo());
		return anioAux;
	}
	
	/**
	 * Obtiene registros históricos de rendidas del alumno.
	 * @param numeroDni
	 * @param nombreMateria
	 * @param cicloLectivoMateria
	 * @return
	 * @throws Exception
	 */
	private ArrayList<MesaExamenHist> obtenerHistoricoRendidas(Long numeroDni, String nombreMateria, Integer cicloLectivoMateria) throws Exception{
		MesaExamenHist ejemplo = new MesaExamenHist();
		ejemplo.setDniAlumno(numeroDni);
		ejemplo.setCicloLectivoMateria(cicloLectivoMateria);
		if(!nombreMateria.equals("")){
			ejemplo.setNombreMateria(nombreMateria);
		}
		return (ArrayList<MesaExamenHist>) gMEHist.getByExample(ejemplo);
	}
	
	private Long materiaPerteneceAnio(Materia materia) throws Exception{
		// Devolver el año al que pertenece la materia
		// Obtener los años activo
		// Eecorrer las materias y devolver el id de año (si se encuentra)
		List<Anio>listaAnio = new ArrayList<Anio>();
		List<Materia> listaMateria = new ArrayList<Materia>(); 
		listaAnio = gAnio.getByExample(new Anio(null,null,null,null,null,null, null, null, true));
		for(Anio a: listaAnio){
			if(a.getListaMaterias().contains(materia)){
				return a.getIdAnio();
			}
		}
		return 0L; // DEVUELVE 0 SI LA MATERIA NO PERTENECE A UN ANIO
	}
	
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
	 * Encuentra el llamado vigente a la fecha según la fecha actual del servidor.
	 * @return
	 * @throws Exception
	 */
	private Llamado encontrarLlamadoVigente() throws Exception{
		try{
		
		Calendar fechaActual = Calendar.getInstance(); //Quemar fecha comprendida en periodo
		Calendar periodoInicio = Calendar.getInstance();
		Calendar periodoFin = Calendar.getInstance();
		
		Long diasVigencia = Long.valueOf(ServicioConfiguracion.getParametro("VIS_DIAS_LLAMADO").getValor());
		
		ArrayList<Llamado> listaLlamado = gLlamado.List();
		for(Llamado ll: listaLlamado){
			periodoInicio.setTime(ll.getFechaInicio());
			periodoInicio.add(Calendar.DAY_OF_MONTH, - diasVigencia.intValue()); // Comienzo del periodo segun fecha de llamado
			periodoFin.setTime(ll.getFechaInicio());
			periodoFin.add(Calendar.DAY_OF_MONTH, -2); // Fin de periodo de inscripcion
			if( ( fechaActual.equals(periodoInicio) || fechaActual.after(periodoInicio) ) 
				&& ( fechaActual.equals(periodoFin) || fechaActual.before(periodoFin) ) ){
				return ll;
			}
		}
		}catch(Exception ex){
			throw ex;
		}
		return null;
	}
	
	/**
	 * Encuentra todas las inscripciones realizada por el alumno.
	 * @param idAlumno
	 * @param idLlamado
	 * @return
	 * @throws Exception
	 */
	private Inscripcion buscarInscripcion(Long idAlumno, Long idLlamado) throws Exception{
		try{
			String sql = new String();
			sql = "select * from INSCRIPCION WHERE ALUMNO = " + idAlumno;
			
			Session sessAux = null;
			if ((sessAux == null) || (!sessAux.isOpen())) {
				sessAux = HibernateUtil.getSessionFactory().openSession();
				
			}
			if (!sessAux.getTransaction().isActive()) {
				sessAux.beginTransaction();
			}
			SQLQuery consulta = sessAux.createSQLQuery(sql).addEntity(Inscripcion.class);

			List result = consulta.list();
			sessAux.close();
			if (result.size() != 0) {
				List<Inscripcion> listaInscripcion = result;
				for(Inscripcion i : listaInscripcion){
					if (i.getIdLlamado() == idLlamado){
						return i;
					}
					return null; // Existen inscripciones, pero no para este llamado
				}
			} else {
				return null; // No existen inscripciones para este llamado
			}
			
		}catch (Exception ex){
			throw ex;
		}
		return null;
	} 
	/**
	 * Devuelve el código de la siguiente inscripcion.
	 * @param idLlamado
	 * @return
	 */
	private Integer codigoSiguienteInscripcion(Long idLlamado) throws Exception{
		try {
			String sql = new String();
			sql = "SELECT MAX(INSCRIPCION.CODIGO) AS VALOR FROM INSCRIPCION WHERE IDLLAMADO = " + idLlamado;
			Session sessAux = null;
			if ((sessAux == null) || (!sessAux.isOpen())) {
				sessAux = HibernateUtil.getSessionFactory().openSession();
				
			}
			if (!sessAux.getTransaction().isActive()) {
				sessAux.beginTransaction();
			}
			SQLQuery consulta = sessAux.createSQLQuery(sql);
			Integer result = (Integer) consulta.uniqueResult();
			
			if ( result == null ){
				return 1;
			}else{
				return result + 1;
			}
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Obtiene el orden de un anio propocionando el id de Anio
	 * @param idAnio
	 * @return
	 * @throws Exception
	 */
	public Integer ObtenerOrdenAnio(Long idAnio) throws Exception{
		try{
			if(idAnio == 0){
				return 0;	//Si es id Cero, devuelvo Cero
			}
			List<Anio> todosLosAnios = getAnios(new Anio(null,null,null,null,null,null,null,null,null));
			for(Anio a : todosLosAnios){
				if(a.getIdAnio().equals(idAnio)){
					return a.getOrden();
				}
			}
		}catch(Exception ex){
			throw ex;
		}
		return 0; //No se encontró id y se devuelve Cero
	}
	
	/**
	 * Obtiene el orden del anio proporcionando el id de Curso
	 * @param idCurso
	 * @return
	 * @throws Exception
	 */
	public Integer ObtenerOrdenAnioCurso(Long idCurso) throws Exception{
		try{
			if(idCurso == 0){
				return 0;	//Si es id Cero, devuelvo Cero
			}
			List<Anio> todosLosAnios = getAnios(new Anio(null,null,null,null,null,null,null,null,null));
			for(Anio a : todosLosAnios){
				for(Curso c : a.getListaCursos()){
					if(c.getIdCurso().equals(idCurso)){
						return a.getOrden();
					}
				}
			}
		}catch(Exception ex){
			throw ex;
		}
		return 0;
	}
	
	/**
	 * Devuelve un listado de llamado en formato DTO
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	public List<LlamadoDTO> listarLlamados() throws ValidacionException, Exception{
		try{
			List<LlamadoDTO> llamadosDTO = new ArrayList<LlamadoDTO>();
			List<Llamado> llamados = new ArrayList<Llamado>();
			llamados = gLlamado.List();  //
			for(Llamado ll : llamados){
				LlamadoDTO llamadoDTO = new LlamadoDTO();
				List<MesaDTO> mesasDTO = new ArrayList<MesaDTO>();
				for(Mesa mesa: ll.getListaMesas()){
					MesaDTO mDTO = new MesaDTO();
					mDTO.setFechaHoraFin(mesa.getFechaHoraFin());
					mDTO.setFechaHoraInicio(mesa.getFechaHoraInicio());
					mDTO.setIdMateria(mesa.getMateria().getIdMateria());
					mDTO.setIdMesa(mesa.getIdMesa());
					mDTO.setMateria(mesa.getMateria().getNombre());
					Set<Personal> personal = mesa.getIntegrantesTribunal();
					Integer count = 0;
					for (Personal p : personal){  // Cargo los integrantes de tribunal
						count = count+1;
						switch (count) {
						case 1:
							mDTO.setIdTribunal1(p.getIdUsuario());
							mDTO.setNombreTribunal1(p.toString());
							break;
						case 2:
							mDTO.setIdTribunal2(p.getIdUsuario());
							mDTO.setNombreTribunal2(p.toString());
							break;
						case 3:
							mDTO.setIdTribunal3(p.getIdUsuario());
							mDTO.setNombreTribunal3(p.toString());
							break;
						}
					}
					mesasDTO.add(mDTO);
				}
				llamadoDTO.setDescripcion(ll.getDescripcion());
				llamadoDTO.setFechaFin(ll.getFechaFin());
				llamadoDTO.setFechaInicio(ll.getFechaInicio());
				llamadoDTO.setListaMesas(mesasDTO);
				llamadoDTO.setVigente(false);
				llamadoDTO.setIdLlamado(ll.getIdLlamado());
				llamadosDTO.add(llamadoDTO);
			}
		Llamado llamadoVigente = encontrarLlamadoVigente();
		if (llamadoVigente != null){
			for(LlamadoDTO ll : llamadosDTO){
				if(ll.getIdLlamado().equals(llamadoVigente.getIdLlamado())){  //Seteo en true si el llamado encontrado está vigente
					ll.setVigente(true);
				}
			}
		}
		return llamadosDTO;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	public void closeSession() throws Exception { 
		gAnio.closeSession();
	}
	
}
