package ar.com.santalucia.servicio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.academico.GestorAnio;
import ar.com.santalucia.aplicacion.gestor.academico.GestorArea;
import ar.com.santalucia.aplicacion.gestor.academico.GestorCurso;
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
import ar.com.santalucia.dominio.dto.AnioDTO;
import ar.com.santalucia.dominio.dto.CursoDTO;
import ar.com.santalucia.dominio.dto.DetallePreviaDTO;
import ar.com.santalucia.dominio.dto.MateriaAltaDTO;
import ar.com.santalucia.dominio.dto.MateriaDTO;
import ar.com.santalucia.dominio.dto.MesaAltaDTO;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Area;
import ar.com.santalucia.dominio.modelo.academico.Curso;
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

/**
 * Clase servicio para gestión académica. Engloba Anio, Curso, alumnos y
 * materias
 * 
 * @author ericpennachini
 *
 */

// Último modificador: Ariel Ramirez @ 01-05-2016

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
				Personal docente1 = gDocente.getByExample(new Personal(mesaAltaDTO.getTribunalDoc1(),null,null,null,null,null,null,null,null,null,true,null,null,null,null)).get(0);
				Personal docente2 = gDocente.getByExample(new Personal(mesaAltaDTO.getTribunalDoc2(),null,null,null,null,null,null,null,null,null,true,null,null,null,null)).get(0);
				Personal docente3 = gDocente.getByExample(new Personal(mesaAltaDTO.getTribunalDoc3(),null,null,null,null,null,null,null,null,null,true,null,null,null,null)).get(0);
				Materia materia = gMateria.getById(mesaAltaDTO.getIdMateria());
				Set<Mesa> mesasLlamado = llamado.getListaMesas();
				for(Mesa m : mesasLlamado){
					if(m.getMateria().equals(materia)){
						throw new Exception("Ya existe la mesa en el llamado.");
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
			}
			
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
		listaAnio = gAnio.getByExample(new Anio(null,null,null,null,null,true));
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
		listaAnio = gAnio.getByExample(new Anio(null,null,null,null,null,true));
		for (Anio a: listaAnio) {
			if(a.getListaCursos().contains(curso)){
				return a;
			}
		}
		return null;
	}
	
	public void closeSession() throws Exception { 
		gAnio.closeSession();
	}
}
