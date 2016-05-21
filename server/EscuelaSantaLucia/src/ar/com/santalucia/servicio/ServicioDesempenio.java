package ar.com.santalucia.servicio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.academico.GestorAnio;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMateria;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorBoletinInasistencias;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorBoletinNotas;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorBoletinNotasHist;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorInasistencia;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorNota;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorTrimestre;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.dominio.dto.BoletinInasistenciasDTO;
import ar.com.santalucia.dominio.dto.DetallePreviaDTO;
import ar.com.santalucia.dominio.dto.GetPlanillaTrimestralDTO;
import ar.com.santalucia.dominio.dto.InasistenciaDTO;
import ar.com.santalucia.dominio.dto.ItemPlanillaTrimestralDTO;
import ar.com.santalucia.dominio.dto.MateriaNotaDTO;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Inscripcion;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinInasistencias;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotas;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotasHist;
import ar.com.santalucia.dominio.modelo.desempenio.Inasistencia;
import ar.com.santalucia.dominio.modelo.desempenio.MateriaNotasBoletin;
import ar.com.santalucia.dominio.modelo.desempenio.Nota;
import ar.com.santalucia.dominio.modelo.desempenio.Trimestre;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.excepciones.ValidacionException;

/**
 * Clase ServicioDesempenio: manejo del boletin de notas del alumno, además maneja el boletín histórico
 * 
 * @author Eric
 * @version 1.0
 *
 */

public class ServicioDesempenio {
	
	private GestorNota gNota;
	private GestorMateria gMateria; // ¿?
	private GestorTrimestre gTrimestre;
	private GestorBoletinNotas gBoletin;
	private GestorBoletinNotasHist gBoletinHist;
	private GestorAlumno gAlumno; // ¿?
	private GestorAnio gAnio;
	private GestorInasistencia gInasistencia;
	private GestorBoletinInasistencias gBoletinInasistencias;
	
	public ServicioDesempenio() throws Exception {
		try {
			gNota = new GestorNota();
			//gMateria = new GestorMateria();
			gTrimestre = new GestorTrimestre();
			gBoletin = new GestorBoletinNotas();
			gBoletinHist = new GestorBoletinNotasHist();
			//gAlumno = new GestorAlumno();
			gAnio = new GestorAnio();
			gInasistencia = new GestorInasistencia();
			gBoletinInasistencias = new GestorBoletinInasistencias();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar el servicio de operaciones básicas: "
					+ ex.getMessage());
		}
	}
	
	public Boolean addBoletinNotas(BoletinNotas boletinNotas) throws Exception { // EN ENDPOINT
		try {
			if (boletinNotas.getIdBoletinNotas() != null) {
				gBoletin.add(boletinNotas);
			} else {
				gBoletin.modify(boletinNotas);
			}
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta el BOLETÍN DE NOTAS: " + ex.getMessage());
		}
		return true;
	}
	
	public Boolean deleteBoletinNotas(BoletinNotas boletinNotas) throws Exception { // EN ENDPOINT
		try {
			gBoletin.delete(boletinNotas);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar el BOLETÍN DE NOTAS: " + ex.getMessage());
		}
		return true;
	}
	
	public BoletinNotas getBoletin(Long idBoletin) throws Exception { // EN ENDPOINT
		try {
			return gBoletin.getById(idBoletin);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener el BOLETÍN DE NOTAS: " + ex.getMessage());
		}
	}
	
	public List<BoletinNotas> getBoletines(BoletinNotas example) throws Exception { // EN ENDPOINT
		try {
			return gBoletin.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista de BOLETINES DE NOTAS: " + ex.getMessage());
		}
	}
	
	public Boolean pasarAHistorico(BoletinNotas boletinNotas) throws Exception { // EN ENDPOINT
		try {
			BoletinNotasHist boletinHistorico = new BoletinNotasHist();
			// pasar a histórico
			boletinHistorico.setIdBoletin(boletinNotas.getIdBoletinNotas());
			boletinHistorico.setDniAlumno(boletinNotas.getPropietario().getNroDocumento());
			boletinHistorico.setNombreAlumno(boletinNotas.getPropietario().getNombre());
			boletinHistorico.setApellidoAlumno(boletinNotas.getPropietario().getApellido());
			boletinHistorico.setAnio(boletinNotas.getAnio());
			boletinHistorico.setCurso(boletinNotas.getCurso());
			boletinHistorico.setCicloLectivo(boletinNotas.getCicloLectivo());
			
			Set<Trimestre> trimestres = boletinNotas.getListaTrimestres();
			Set<Nota> notasExtras = boletinNotas.getListaNotasExamen();
			Set<MateriaNotasBoletin> listaMateriasNotasBoletin = new HashSet<MateriaNotasBoletin>();
			
			Anio anioBuscar = new Anio();
			anioBuscar.setNombre(boletinNotas.getAnio());
			ArrayList<Anio> listaAnios = gAnio.getByExample(anioBuscar);
			Anio anioEnc = new Anio();
			anioEnc = listaAnios.get(0);
			
			for (Materia m : anioEnc.getListaMaterias()) {
				MateriaNotasBoletin materiaNotasBoletin = new MateriaNotasBoletin();
				materiaNotasBoletin.setMateria(m.getNombre());
				for (Trimestre t : trimestres) {
					if (t.getOrden().equals(1) && t.getMateria().getNombre().equals(m.getNombre())) {
						materiaNotasBoletin.setNotaTrimestre1(t.getNotaFinal().getCalificacion().intValue());
					}
					if (t.getOrden().equals(2) && t.getMateria().getNombre().equals(m.getNombre())) {
						materiaNotasBoletin.setNotaTrimestre2(t.getNotaFinal().getCalificacion().intValue());
					}
					if (t.getOrden().equals(3) && t.getMateria().getNombre().equals(m.getNombre())) {
						materiaNotasBoletin.setNotaTrimestre3(t.getNotaFinal().getCalificacion().intValue());
					}
				}
				for (Nota ne : notasExtras) {
					if (ne.getTipo().equals(Nota.DICIEMBRE) && ne.getMateria().getNombre().equals(m.getNombre())) {
						materiaNotasBoletin.setNotaDiciembre(ne.getCalificacion().intValue());
					}
					if (ne.getTipo().equals(Nota.MARZO) && ne.getMateria().getNombre().equals(m.getNombre())) {
						materiaNotasBoletin.setNotaMarzo(ne.getCalificacion().intValue());
					}
				}
				listaMateriasNotasBoletin.add(materiaNotasBoletin);
			}
			
			boletinHistorico.setListaMateriasNotasBoletin(listaMateriasNotasBoletin);
			gBoletinHist.add(boletinHistorico);
			
		} catch (Exception ex) {
			throw new Exception ("No se pudo pasar a histórico el BOLETÍN DE NOTAS: " + ex.getMessage());
		}
		return true;
	}

	public Boolean addBoletinHistorico(BoletinNotasHist boletinHistorico) throws Exception {
		try {
			gBoletinHist.add(boletinHistorico); // validar id
		} catch (Exception ex) {
			throw new Exception("No se pudo agregar el BOLETÍN HISTÓRICO: " + ex.getMessage());
		}
		return true;
	}
	
	public Boolean addNota(Nota nota) throws Exception { // EN ENDPOINT
		try {
			if (nota.getIdNota() == null) {
				gNota.add(nota);
			} else {
				gNota.modify(nota);
			}
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta la NOTA: " + ex.getMessage());
		}
		return true;
	}
		
	public Boolean deleteNota(Nota nota) throws Exception { // EN ENDPOINT
		try {
			gNota.delete(nota);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar la NOTA: " + ex.getMessage());
		}
		return true;
	}
	
	public Nota getNota(Long idNota) throws Exception { // EN ENDPOINT
		try {
			return gNota.getById(idNota);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la NOTA: " + ex.getMessage());
		}
	}
	
	public List<Nota> getNotas(Nota example) throws Exception { // EN ENDPOINT
		try {
			return gNota.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista de NOTAS: " + ex.getMessage());
		}
	}
	
	
	public Boolean addTrimestre(Trimestre trimestre) throws Exception { // EN ENDPOINT
		try {
			if (trimestre.getIdTrimestre() == null) {
				gTrimestre.add(trimestre);
			} else {
				gTrimestre.modify(trimestre);
			}
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta el TRIMESTRE: " + ex.getMessage());
		}
		return true;
	}
		
	public Boolean deleteTrimestre(Trimestre trimestre) throws Exception { // EN ENDPOINT
		try {
			gTrimestre.delete(trimestre);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar el TRIMESTRE: " + ex.getMessage());
		}
		return true;
	}
	
	public Trimestre getTrimestre(Long idTrimestre) throws Exception { // EN ENDPOINT
		try {
			return gTrimestre.getById(idTrimestre);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener el TRIMESTRE: " + ex.getMessage());
		}
	}
	
	public List<Trimestre> getTrimestres(Trimestre example) throws Exception { // EN ENDPOINT
		try {
			return gTrimestre.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista de TRIMESTRES: " + ex.getMessage());
		}
	}

	
	public Boolean asignarMateriaATrimestre(Materia materia, Long idTrimestre) throws Exception { // EN ENDPOINT
		try {
			Trimestre trimestre = gTrimestre.getById(idTrimestre);
			trimestre.setMateria(materia);
			gTrimestre.modify(trimestre);
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar la MATERIA al TRIMESTRE: " + ex.getMessage());
		}
		return true;
	}
	
	public Boolean asignarNotaATrimestre(Nota nota, Long idTrimestre) throws Exception { // EN ENDPOINT
		try {
			Trimestre trimestre = gTrimestre.getById(idTrimestre);
			nota.setMateria(trimestre.getMateria()); // asigno la materia a la nota tomando del trimestre.
			gNota.modify(nota);
			if (nota.getTipo().equals(Nota.NOTA_FINAL_TRIMESTRAL)) {
				trimestre.setNotaFinal(nota);
			} else {
				Set<Nota> listaNotas = trimestre.getListaNotas();
				listaNotas.add(nota);
				trimestre.setListaNotas(listaNotas);
			}
			gTrimestre.modify(trimestre);
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar la NOTA al TRIMESTRE: " + ex.getMessage());
		}
		return true;
	}	 
	
	public Boolean asignarTrimestreABoletin(Trimestre trimestre, Long idBoletin) throws Exception {
		try {
			BoletinNotas boletinNotas = gBoletin.getById(idBoletin);
			Set<Trimestre> listaTrimestres = boletinNotas.getListaTrimestres();
			listaTrimestres.add(trimestre);
			boletinNotas.setListaTrimestres(listaTrimestres);
			gBoletin.modify(boletinNotas);
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar el TRIMESTRE al BOLETÍN DE NOTAS: " + ex.getMessage());
		}
		return true;
	}
	
	public Boolean asignarAlumnoABoletin(Alumno alumno, Long idBoletin) throws Exception {
		try {
			BoletinNotas boletinNotas = gBoletin.getById(idBoletin);
			boletinNotas.setPropietario(alumno);
			gBoletin.modify(boletinNotas);
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar el ALUMNO al BOLETÍN DE NOTAS: " + ex.getMessage());
		}
		return true;
	}

	/**
	 * Retorna una lista de alumnos con sus notas. Nótese que se retorna un arreglo de items
	 * @param gptDTO
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ItemPlanillaTrimestralDTO> getPlanillaTrimestral(GetPlanillaTrimestralDTO gptDTO) throws Exception {
		//TODO: ¿Qué se necesita para obtener la planilla trimestral?
		/*
		 * turno del curso // OJO !! en vez de división puse el turno ("Único", "Generico", ...)
		 * nro de trimestre
		 * nombre del año
		 * ciclo lectivo
		 */
		
		ArrayList<ItemPlanillaTrimestralDTO> planillaTrimestral;
		try {
			planillaTrimestral = new ArrayList<ItemPlanillaTrimestralDTO>();
			Anio anio = new Anio();
			anio.setNombre(gptDTO.getNombreAnio());
			ArrayList<Anio> anios = gAnio.getByExample(anio);
			anio = anios.get(0);
			Set<Curso> listaCursos = anio.getListaCursos();
			Curso curso = new Curso();
			
			for (Curso c : listaCursos) {
				if (c.getTurno().equals(gptDTO.getCurso())) {
					curso = c;
				}
			}
			
			BoletinNotas boletinNotas = new BoletinNotas();
			boletinNotas.setAnio(gptDTO.getNombreAnio());
			boletinNotas.setCurso(gptDTO.getCurso());
			ArrayList<BoletinNotas> listaBoletinNotas = gBoletin.getByExample(boletinNotas);
			
			for (BoletinNotas bn : listaBoletinNotas) {
				ItemPlanillaTrimestralDTO itemPlanillaTrimestralDTO = new ItemPlanillaTrimestralDTO();
				itemPlanillaTrimestralDTO.setAlumno(bn.getPropietario().toString());
				ArrayList<Trimestre> trimestres = new ArrayList<Trimestre>();
				for (Trimestre t : bn.getListaTrimestres()) {
					if (t.getOrden() == gptDTO.getNroTrimestre()) {
						MateriaNotaDTO mnDTO = new MateriaNotaDTO(t.getMateria().getNombre(),t.getNotaFinal().getCalificacion());
						itemPlanillaTrimestralDTO.getNotas().add(mnDTO);
					}
				}
				planillaTrimestral.add(itemPlanillaTrimestralDTO);
			}
			return planillaTrimestral;
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la PLANILLA TRIMESTRAL del trimestre " 
									+ gptDTO.getNroTrimestre() + ": " + ex.getMessage());
		}
	}

	/**
	 * Test method
	 * @param inasistencia
	 * @return
	 * @throws Exception
	 */
	public Boolean addInasistencia(Inasistencia inasistencia) throws Exception {
		try {
			if (inasistencia.getIdInasistencia() == null) {
				gInasistencia.add(inasistencia);
			} else {
				gInasistencia.modify(inasistencia);
			}
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta la INASISTENCIA: " + ex.getMessage());
		}
		return true;
	}
	
	/**
	 * Test method
	 * @param boletinInasistencias
	 * @return
	 * @throws Exception
	 */
	public Boolean addBoletinInasistencias(BoletinInasistencias boletinInasistencias) throws Exception {
		try {
			if (boletinInasistencias.getIdBoletinInasistencias() == null) {
				gBoletinInasistencias.add(boletinInasistencias);
			} else {
				gBoletinInasistencias.modify(boletinInasistencias);
			}
		} catch (ValidacionException vEx) {
			throw vEx;
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta el BOLETÍN DE INASISTENCIAS: " + ex.getMessage());
		}
		return true;
	}
	
	// OJO!!! >> recibir un DTO del boletin en vez de el boletin completo
	public Boolean procesarBoletinInasistencias(BoletinInasistenciasDTO bolInasistenciasDTO) throws Exception {
		/*
		 * Pasos para procesar un boletin de inasistencias:
		 * 1) Obtener el boletín de inasistencias a partir del DTO (que viene por parámetro) ... OK
		 * 2) Obtener el alumno propietario en base al nombre y apellido del DTO
		 * 3) Recorrer las inasistencias
		 * 	3.1) Si el id != null, obtener la inasistencia persistida
		 * 	3.2) Asignar la inasistencia obtenida al boletin
		 * 4) Mandar el boletín al modify del gestor
		 */
		
		// 1)
		BoletinInasistencias bolInasistencias = new BoletinInasistencias();
		bolInasistencias = gBoletinInasistencias.getById(bolInasistenciasDTO.getIdBoletinInasistencias());
		// 2)
//		Alumno propietario = new Alumno();
//		propietario.setApellido(bolInasistenciasDTO.getApellido());
//		propietario.setNombre(bolInasistenciasDTO.getNombre());
//		ArrayList<Alumno> alumnos = gAlumno.getByExample(propietario);
//		bolInasistencias.setPropietario(alumnos.get(0));
		// 3)
		Set<Inasistencia> listaInasistencia = bolInasistencias.getListaInasistencias();
		ArrayList<InasistenciaDTO> listaInasistenciasDTO = bolInasistenciasDTO.getListaInasistenciasDTO();
		for (InasistenciaDTO iDTO : listaInasistenciasDTO) {
			
		}
		
		return true;
	}
	

}
