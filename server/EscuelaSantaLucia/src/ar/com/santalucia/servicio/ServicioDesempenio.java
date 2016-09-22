package ar.com.santalucia.servicio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.academico.GestorAnio;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMateria;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorBoletinInasistencias;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorBoletinNotas;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorBoletinNotasHist;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorInasistencia;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorNota;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorTrimestre;
import ar.com.santalucia.aplicacion.gestor.sistema.configuracion.GestorParametroConfiguracion;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.dominio.dto.AnioDTO;
import ar.com.santalucia.dominio.dto.BoletinInasistenciasDTO;
import ar.com.santalucia.dominio.dto.BoletinNotasDTO;
import ar.com.santalucia.dominio.dto.CursoDTO;
import ar.com.santalucia.dominio.dto.DetallePlanillaTrimestralDocenteDTO;
import ar.com.santalucia.dominio.dto.GetPlanillaTrimestralDTO;
import ar.com.santalucia.dominio.dto.GetPlanillaTrimestralDocDTO;
import ar.com.santalucia.dominio.dto.InasistenciasBoletinDTO;
import ar.com.santalucia.dominio.dto.ItemPlanillaTrimestralDTO;
import ar.com.santalucia.dominio.dto.ListaPasajeAlumnosDTO;
import ar.com.santalucia.dominio.dto.MateriaDTO;
import ar.com.santalucia.dominio.dto.MateriaNotaDTO;
import ar.com.santalucia.dominio.dto.PasajeAlumnosDTO;
import ar.com.santalucia.dominio.dto.PlanillaNotasBoletinDTO;
import ar.com.santalucia.dominio.dto.PlanillaTrimestralDTO;
import ar.com.santalucia.dominio.dto.PlanillaTrimestralDocenteDTO;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinInasistencias;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotas;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotasHist;
import ar.com.santalucia.dominio.modelo.desempenio.Inasistencia;
import ar.com.santalucia.dominio.modelo.desempenio.MateriaNotasBoletin;
import ar.com.santalucia.dominio.modelo.desempenio.Nota;
import ar.com.santalucia.dominio.modelo.desempenio.RegistroPasajeAlumnos;
import ar.com.santalucia.dominio.modelo.desempenio.Trimestre;
import ar.com.santalucia.dominio.modelo.sistema.configuracion.ParametroConfiguracion;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Usuario;
import ar.com.santalucia.excepciones.*;

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
	private GestorParametroConfiguracion gParametroConfiguracion;
	
	public static String BUSCAR_BOLETIN_NOTAS = "bn";
	public static String BUSCAR_BOLETIN_INASISTENCIAS = "bi";
	
	public ServicioDesempenio() throws Exception {
		try {
			gNota = new GestorNota();
			gMateria = new GestorMateria();
			gTrimestre = new GestorTrimestre();
			gBoletin = new GestorBoletinNotas();
			gBoletinHist = new GestorBoletinNotasHist();
			gAlumno = new GestorAlumno();
			gAnio = new GestorAnio();
			gInasistencia = new GestorInasistencia();
			gBoletinInasistencias = new GestorBoletinInasistencias();
			gParametroConfiguracion = new GestorParametroConfiguracion();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar el servicio de operaciones básicas: "
					+ ex.getMessage());
		}
	}
	
	/**
	 * Devuelve el boletin de notas o de inasistencias. El resultado se debe castear a <i>BoletinNotas</i>
	 * o <i>BoletinInasistencias</i>, según el caso.
	 * @param alumno el alumno del cual se desea el boletín
	 * @param boletinBuscado el boletin que se quiere buscar: 
	 * 		<b>"bn"</b> para el de notas;
	 * 		<b>"bi"</b> para el de inasistencias.
	 * @return el boletin de inasistencias o de notas (como Object)
	 * @throws Exception
	 */
	public static Object encontrarBoletinDeAlumno(Alumno alumno, String boletinBuscado) throws Exception {
		try {
			String sql = "";
			switch (boletinBuscado) {
			case "bn":
				sql = "SELECT * FROM BOLETINNOTAS WHERE PROPIETARIO = "+String.valueOf(alumno.getIdUsuario());
				break;
			case "bi":
				sql = "SELECT * FROM BOLETININASISTENCIAS WHERE PROPIETARIO = "+String.valueOf(alumno.getIdUsuario());
				break;
			}
			Session sessAux = null;
			if ((sessAux == null) || (!sessAux.isOpen())) {
				sessAux = HibernateUtil.getSessionFactory().openSession();
				
			}
			if (!sessAux.getTransaction().isActive()) {
				sessAux.beginTransaction();
			}
			SQLQuery consulta = sessAux.createSQLQuery(sql).addEntity((boletinBuscado.equals("bn")
																		? BoletinNotas.class
																		: BoletinInasistencias.class));
			
			List result = consulta.list();
			sessAux.close();
			if (result.size() != 0) {
				return result.get(0);
			} else {
				return null;
			}
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener el boletin de notas del alumno especificado: " + ex.getMessage());
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
	
	public BoletinNotasDTO getBoletinNotasDTObyIdAlumno(Long idAlumno) throws Exception {
		try {
			Alumno alumno = (Alumno) gAlumno.getById(idAlumno);
			BoletinNotas boletinNotas = (BoletinNotas) encontrarBoletinDeAlumno(alumno, BUSCAR_BOLETIN_NOTAS);
			BoletinInasistencias boletinInasistencias = (BoletinInasistencias) encontrarBoletinDeAlumno(alumno, BUSCAR_BOLETIN_INASISTENCIAS);
			Anio anio = gAnio.getByExample(new Anio(null, 
													boletinNotas.getAnio(), 
													null, null, null, null, null, null, true)).get(0);	
			ArrayList<Materia> materias = new ArrayList<Materia>();
			materias.addAll(anio.getListaMaterias());
			
			BoletinNotasDTO boletinDTO = new BoletinNotasDTO();
			boletinDTO.setIdBoletinNotas(boletinNotas.getIdBoletinNotas());
			boletinDTO.setNroDocumento(alumno.getNroDocumento());
			boletinDTO.setNombre(alumno.getNombre());
			boletinDTO.setApellido(alumno.getApellido());
			boletinDTO.setAnio(boletinNotas.getAnio());
			boletinDTO.setCurso(boletinNotas.getCurso());
			boletinDTO.setCicloLectivo(boletinNotas.getCicloLectivo());
			for (Materia m : materias) {
				PlanillaNotasBoletinDTO planillaNotas = new PlanillaNotasBoletinDTO();
				planillaNotas.setMateria(m.getNombre());
				for (Trimestre t : boletinNotas.getListaTrimestres()) {
					if (t.getMateria().getNombre().equals(m.getNombre())) {
						switch (t.getOrden()) {
						case 1:
							planillaNotas.setNotaTrim1(t.getNotaFinal().getCalificacion().intValue());
							break;
						case 2:
							planillaNotas.setNotaTrim2(t.getNotaFinal().getCalificacion().intValue());
							break;
						case 3:
							planillaNotas.setNotaTrim3(t.getNotaFinal().getCalificacion().intValue());
							break;
						}
					}
				}
				Float promedio = (float) ((planillaNotas.getNotaTrim1() + planillaNotas.getNotaTrim2() + planillaNotas.getNotaTrim3()) / 3);
				planillaNotas.setNotaFinal(promedio);
				// ver la posibilidad de llenar la nota definitiva, más la nota de diciembre y marzo
				boletinDTO.getListaNotas().add(planillaNotas);
			}
			
			// ----- INASISTENCIAS -------
			ParametroConfiguracion inicioTrimestre1 = gParametroConfiguracion
					.getByExample(new ParametroConfiguracion(null, "COMIENZO_TRIM_1", null, null)).get(0);
			ParametroConfiguracion finTrimestre1 = gParametroConfiguracion
					.getByExample(new ParametroConfiguracion(null, "FIN_TRIM_1", null, null)).get(0);
			ParametroConfiguracion inicioTrimestre2 = gParametroConfiguracion
					.getByExample(new ParametroConfiguracion(null, "COMIENZO_TRIM_2", null, null)).get(0);
			ParametroConfiguracion finTrimestre2 = gParametroConfiguracion
					.getByExample(new ParametroConfiguracion(null, "FIN_TRIM_2", null, null)).get(0);
			ParametroConfiguracion inicioTrimestre3 = gParametroConfiguracion
					.getByExample(new ParametroConfiguracion(null, "COMIENZO_TRIM_3", null, null)).get(0);
			ParametroConfiguracion finTrimestre3 = gParametroConfiguracion
					.getByExample(new ParametroConfiguracion(null, "FIN_TRIM_3", null, null)).get(0);
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			Calendar calendar1 = Calendar.getInstance();
			Calendar calendar2 = Calendar.getInstance();
			Calendar calendar3 = Calendar.getInstance();
			Calendar calendar4 = Calendar.getInstance();
			Calendar calendar5 = Calendar.getInstance();
			Calendar calendar6 = Calendar.getInstance();
			calendar1.setTime(formatoFecha.parse(inicioTrimestre1.getValor()));
			calendar2.setTime(formatoFecha.parse(finTrimestre1.getValor()));
			calendar3.setTime(formatoFecha.parse(inicioTrimestre2.getValor()));
			calendar4.setTime(formatoFecha.parse(finTrimestre2.getValor()));
			calendar5.setTime(formatoFecha.parse(inicioTrimestre3.getValor()));
			calendar6.setTime(formatoFecha.parse(finTrimestre3.getValor()));
			InasistenciasBoletinDTO resumenInasistencias = new InasistenciasBoletinDTO();
			for (Inasistencia i : boletinInasistencias.getListaInasistencias()) {
				// si fecha está entre el inicio y fin del trimestre 1
				if ((i.getFecha().after(calendar1.getTime()) && i.getFecha().before(calendar2.getTime()))
						|| (i.getFecha().equals(calendar1.getTime()) || i.getFecha().equals(calendar2.getTime()))) { 
					if (i.getJustificada()) {
						resumenInasistencias.setJustificadasTrim1(resumenInasistencias.getJustificadasTrim1() + i.getCantidad());
					} else {
						resumenInasistencias.setInjustificadasTrim1(resumenInasistencias.getInjustificadasTrim1() + i.getCantidad());
					}
				}
				// si fecha está entre el inicio y fin del trimestre 2
				if ((i.getFecha().after(calendar3.getTime()) && i.getFecha().before(calendar4.getTime()))
						|| (i.getFecha().equals(calendar3.getTime()) || i.getFecha().equals(calendar4.getTime()))) { 
					if (i.getJustificada()) {
						resumenInasistencias.setJustificadasTrim2(resumenInasistencias.getJustificadasTrim2() + i.getCantidad());
					} else {
						resumenInasistencias.setInjustificadasTrim2(resumenInasistencias.getInjustificadasTrim2() + i.getCantidad());
					}
				}
				// si fecha está entre el inicio y fin del trimestre 3
				if ((i.getFecha().after(calendar5.getTime()) && i.getFecha().before(calendar6.getTime()))
						|| (i.getFecha().equals(calendar5.getTime()) || i.getFecha().equals(calendar6.getTime()))) { 
					if (i.getJustificada()) {
						resumenInasistencias.setJustificadasTrim3(resumenInasistencias.getJustificadasTrim3() + i.getCantidad());
					} else {
						resumenInasistencias.setInjustificadasTrim3(resumenInasistencias.getInjustificadasTrim3() + i.getCantidad());
					}
				}
			}
			
			boletinDTO.setDetalleInasistencias(resumenInasistencias);
			
			return boletinDTO;
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener el DTO del boletin del alumno por el ID de alumno: " + ex.getMessage());
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
			return true;
		} catch (Exception ex) {
			throw new Exception ("No se pudo pasar a histórico el BOLETÍN DE NOTAS: " + ex.getMessage());
		}
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
		
		ArrayList<ItemPlanillaTrimestralDTO> planillaTrimestral;
		try {
			planillaTrimestral = new ArrayList<ItemPlanillaTrimestralDTO>();
			Anio anio = new Anio();
			anio.setNombre(gptDTO.getAnio());
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
			boletinNotas.setAnio(gptDTO.getAnio());
			boletinNotas.setCurso(gptDTO.getCurso());
			ArrayList<BoletinNotas> listaBoletinNotas = gBoletin.getByExample(boletinNotas);
			
			for (BoletinNotas bn : listaBoletinNotas) {
				ItemPlanillaTrimestralDTO itemPlanillaTrimestralDTO = new ItemPlanillaTrimestralDTO();
				itemPlanillaTrimestralDTO.setAlumno(bn.getPropietario().toString());
				ArrayList<Trimestre> trimestres = new ArrayList<Trimestre>();
				for (Trimestre t : bn.getListaTrimestres()) {
					if (t.getOrden() == gptDTO.getTrimestre()) {
						MateriaNotaDTO mnDTO = new MateriaNotaDTO(t.getMateria().getNombre(),t.getNotaFinal().getCalificacion());
						itemPlanillaTrimestralDTO.getNotas().add(mnDTO);
					}
				}
				planillaTrimestral.add(itemPlanillaTrimestralDTO);
			}
			return planillaTrimestral;
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la PLANILLA TRIMESTRAL del trimestre " 
									+ gptDTO.getTrimestre() + ": " + ex.getMessage());
		}
	}
	
	
	public Boolean procesarPlanillaTrimestral(PlanillaTrimestralDTO planillaTrimestralDTO) throws Exception {
		try {
			List<ItemPlanillaTrimestralDTO> itemsPlanilla = planillaTrimestralDTO.getPlanilla();
			Anio anio = new Anio();
			Curso curso = new Curso();
			anio = gAnio.getByExample(new Anio(null, planillaTrimestralDTO.getAnio(), null, null, null, null, null, null, null)).get(0);
			for (Curso c : anio.getListaCursos()) {
				if (c.getDivision().equals(planillaTrimestralDTO.getCurso().charAt(0))) {
					curso = c;
				}
			}
			
			BoletinNotas boletin = new BoletinNotas();
			List<Alumno> alumnos = new ArrayList<Alumno>();
			alumnos.addAll(curso.getListaAlumnos());
			for (ItemPlanillaTrimestralDTO item : itemsPlanilla) {
				for (Alumno a : alumnos) {
					if (a.toString().equals(item.getAlumno())) {
						String sql = "SELECT * FROM BOLETINNOTAS WHERE PROPIETARIO = "+String.valueOf(a.getIdUsuario());
						Session sessAux = null;
						if ((sessAux == null) || (!sessAux.isOpen())) {
							sessAux = HibernateUtil.getSessionFactory().openSession();
							
						}
						if (!sessAux.getTransaction().isActive()) {
							sessAux.beginTransaction();
						}
						SQLQuery consulta = sessAux.createSQLQuery(sql).addEntity(BoletinNotas.class);
						
						//boletin = gBoletin.getByExample(boletin).get(0);
						boletin = (BoletinNotas) consulta.list().get(0);
						break;
					}
				}
				
				for (MateriaNotaDTO m : item.getNotas()) {
					Trimestre trimestre = new Trimestre();
					for (Trimestre t : boletin.getListaTrimestres()) {
						if (t.getMateria().getNombre().equals(m.getMateria())
								&& t.getOrden().equals(planillaTrimestralDTO.getTrimestre())) {
							trimestre = t;
							break;
						}
					}
					Nota nota = trimestre.getNotaFinal();
					nota.setCalificacion(m.getNota());
					nota.setTipo(Nota.NOTA_FINAL_TRIMESTRAL);
					nota.setFecha(Calendar.getInstance().getTime());
					nota.setMateria(trimestre.getMateria());
					gNota.modify(nota);
					/*trimestre.getNotaFinal().setCalificacion(m.getNota());
					trimestre.getNotaFinal().setTipo(Nota.NOTA_FINAL_TRIMESTRAL);
					trimestre.getNotaFinal().setMateria(trimestre.getMateria());
					trimestre.getNotaFinal().setFecha(Calendar.getInstance().getTime());
					gTrimestre.modify(trimestre);*/
				}
			}
			return true;
		} catch (Exception ex) {
			throw new Exception("No se pudo procesar la planilla trimestral: " + ex.getMessage());
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
	
	public BoletinInasistenciasDTO getBoletinInasistenciasDTObyDni(Long dniAlumno) throws Exception {
		try {
			BoletinInasistenciasDTO boletinDTO = new BoletinInasistenciasDTO();
			BoletinInasistencias boletin = null;
			
			ArrayList<BoletinInasistencias> listaBoletines = gBoletinInasistencias.getByExample(new BoletinInasistencias());
			for (BoletinInasistencias b : listaBoletines) {
				if (b.getPropietario().getNroDocumento().equals(dniAlumno)) {
					boletin = new BoletinInasistencias();
					boletin.setIdBoletinInasistencias(b.getIdBoletinInasistencias());
					boletin.setPropietario(b.getPropietario());
					boletin.setCurso(b.getCurso());
					boletin.setAnio(b.getAnio());
					boletin.setCicloLectivo(b.getCicloLectivo());
					boletin.setActivo(b.getActivo());
					boletin.setListaInasistencias(b.getListaInasistencias());
					boletin.setTotal(b.getTotal());
					break;
				}
			}
			if (boletin != null) {
				boletinDTO.setIdBoletinInasistencias(boletin.getIdBoletinInasistencias());
				boletinDTO.setNombre(boletin.getPropietario().getNombre());
				boletinDTO.setApellido(boletin.getPropietario().getApellido());
				boletinDTO.setNroDocumento(boletin.getPropietario().getNroDocumento());
				boletinDTO.setAnio(boletin.getAnio());
				boletinDTO.setCurso(boletin.getCurso());
				boletinDTO.setCicloLectivo(boletin.getCicloLectivo());
				ArrayList<Inasistencia> listaInasistencias = new ArrayList<Inasistencia>();
				Set<Inasistencia> listaSet = boletin.getListaInasistencias();
				listaInasistencias.addAll(listaSet);
				boletinDTO.setListaInasistencias(listaInasistencias);
				return boletinDTO;
			} else {
				return null;
			}
		} catch (Exception ex) {
			throw new Exception("No se pudo recuperar el BOLETIN DE INASISTENCIAS por su id: " + ex.getMessage());
		}
	}
	
	public Boolean procesarBoletinInasistencias(BoletinInasistenciasDTO bolInasistenciasDTO) throws Exception {		
		try {
			/*
			 * Pasos:
			 * 1) Obtener el boletin de inasistencias persistido a partir del id del DTO.
			 * 2) Sacar las listas nueva y persistente.
			 * 3) Comparar cantidad de inasistencias de las listas nueva y persistente.
			 * 	3.1) Si en la nueva hay menos inasistencias, crear listas auxiliares para eliminar
			 * 	las inasistencias que sobran.
			 * 4) Validar inasistencias (esto es: ver concepto y fecha con respecto a las otras),
			 * equals() de inasistencia.
			 * 	4.1) Si se repite alguna inasistencia, devolver excepcion con lo que se repita.
			 * 	4.2) Si está todo bien, seguir en paso 5).
			 * 5) Pisar la lista persistente con la nueva.
			 * 6) Mandar el boletin al modify de su gestor.
			 */
			
			BoletinInasistencias bolInasistencias = gBoletinInasistencias.getById(bolInasistenciasDTO.getIdBoletinInasistencias());
			// ver si llega ordenada la lista de inasistencias.
			// inicializar listados
			ArrayList<Inasistencia> listaNueva = bolInasistenciasDTO.getListaInasistencias();
			ArrayList<Inasistencia> listaPersis = new ArrayList<Inasistencia>();
			listaPersis.addAll(bolInasistencias.getListaInasistencias());
			
			// validar inasistencias
			validarListaInasistencias(listaNueva);
			// elimina inasistencias sobrantes
			eliminarInasistencias(listaNueva, listaPersis);
			
			gBoletinInasistencias.closeSession();
			
			Set<Inasistencia> setInasistencias = new HashSet<Inasistencia>();
			setInasistencias.addAll(listaNueva);
			bolInasistencias.setListaInasistencias(setInasistencias);
			
			gBoletinInasistencias.modify(bolInasistencias);
		} catch (ValidacionException vEx) {
			throw vEx;
		} catch (InasistenciaException iEx) {
			throw iEx;
		} catch (Exception ex) {
			throw new Exception("No se pudo procesar el BOLETIN DE INASISTENCIAS: " + ex.getMessage());
		}
		
		return true;
	}

	/**
	 * Elimina las inasistencias de la lista nueva, para que no queden inasistencias desvinculadas
	 * de cualquier boletín.
	 * @param listaNueva lista nueva que viene del cliente
	 * @param listaPersis lista que está guardada en el servidor
	 * @throws Exception
	 */
	private void eliminarInasistencias(ArrayList<Inasistencia> listaNueva, ArrayList<Inasistencia> listaPersis)
			throws Exception {
		if (listaNueva.size() < listaPersis.size()) {
			ArrayList<Inasistencia> listaAux1 = new ArrayList<Inasistencia>(listaNueva); // lista basada en la nueva que va a ser modificada
			ArrayList<Inasistencia> listaAux2 = new ArrayList<Inasistencia>(listaPersis); // lista que va a guardar los elementos que sobran
			for (Inasistencia ip : listaPersis) {
				for (Inasistencia in : listaNueva) {
					if (in.getIdInasistencia() != null) {
						if (ip.getIdInasistencia().equals(in.getIdInasistencia())) {
							listaAux1.remove(in);
							listaAux1.add(ip);
						} 
					}
				}
			}
			listaAux2.removeAll(listaAux1); 
			for (Inasistencia i : listaAux2) {
				gInasistencia.delete(i);
			}
		}
		if (listaNueva.size() >= listaPersis.size()) {
			for (Inasistencia i : listaPersis) {
				boolean contiene = false;
				for (Inasistencia in : listaNueva) {
					if (i.getIdInasistencia().equals(in.getIdInasistencia())) {
						contiene = true;
						// break;
					}
				}
				if (!contiene) {
					gInasistencia.delete(i);
				}
			}
		}
	}

	/**
	 * Valida las inasistencias. Compara el concepto y la fecha, y si hay inasistencias con el mismo
	 * concepto para la misma fecha lanza una excepción del tipo <b>InasistenciaException</b>.
	 * 
	 * @param inasistencias
	 * @throws InasistenciaException
	 */
	private void validarListaInasistencias(ArrayList<Inasistencia> inasistencias) throws InasistenciaException {
		InasistenciaException iException = new InasistenciaException();
		
		ArrayList<Inasistencia> listaAux1 = inasistencias;
		ArrayList<Inasistencia> listaAux2 = inasistencias;
		for (Inasistencia i : listaAux1) {
			//listaAux2.remove(i);
			for (Inasistencia in : listaAux2) {
				// hago el equals y comparo el id tmb para controlar que no se compare a si misma
				if (in.equals(i) && (i.getIdInasistencia() != null)/*&& !(in.getIdInasistencia().equals(i.getIdInasistencia()))*/) {
					if (!i.getIdInasistencia().equals(in.getIdInasistencia())) {
						iException.addInasistencia(in);
					}
				}
			}
			//listaAux2.add(i);
		}
		
		if (!iException.getInasistenciasInvalidas().isEmpty()) {
			throw iException;
		}
	}
	
	
	/*------ <<BEGIN>> PROCESO DEL PASAJE DE ALUMNOS -----------------*/
	
	/**
	 * Devuelve un listado de DTO para la pantalla del pasaje de alumnos, por anio.
	 * @param anio
	 * @param curso
	 * @return
	 * @throws Exception
	 */
	public ListaPasajeAlumnosDTO listaAlumnosPasajeCurso(String anio, String especialidad, String curso) throws Exception {
		try {
			//Encontrar orden máximo para el año y especialidad
			Integer maxOrden = 0;
			Integer ordenSiguiente = 0;
			Anio anioBuscar = new Anio();
			Anio anioSiguiente = null;
			List<Anio>anioBuscarAux = gAnio.List();  //Busco todos los años
			for(Anio ab : anioBuscarAux){
				if ( ab.getOrden() > maxOrden  &&  ab.getEspecialidad().getNombre().equals(especialidad) ){ //Busco el máximo orden para la especialidad correspondiente
					maxOrden = ab.getOrden();		
				}
				if (ab.getNombre().equals(anio)){ //Aprovecho la recorrida y capturo el año que me interesa.
					anioBuscar = ab;
				}
			}
			if ( (anioBuscar.getOrden() + 1) < maxOrden ){		//Si el ordenSiguiente es mayor que el máximo de la especialidad, entonces se deja en cero; sino se incrementa.
				ordenSiguiente = anioBuscar.getOrden() + 1;
				Anio anioAux = anioBuscar;
				anioAux.setOrden(ordenSiguiente);
				anioSiguiente = gAnio.getByExample(anioAux).get(0); // Capturo el año siguiente de paso.
			}else{
				ordenSiguiente = 0; 
			}
			//Datos del año con curso alumnos			
			Curso cursoBuscar = new Curso();
			for (Curso c : anioBuscar.getListaCursos()) {
				if (c.getDivision().toString().equals(curso)) {
					cursoBuscar = c;
					break;
				}
			}
			Set<Alumno> listaAlumnosCurso = cursoBuscar.getListaAlumnos();
			ListaPasajeAlumnosDTO listaPasajeAlumnosDTO = new ListaPasajeAlumnosDTO();
			ServicioLlamadoAcademico sAcad = new ServicioLlamadoAcademico();
			for (Alumno a : listaAlumnosCurso) {
				PasajeAlumnosDTO pasajeAlumnoDTO = new PasajeAlumnosDTO();
				pasajeAlumnoDTO.setIdUsuario(a.getIdUsuario());
				pasajeAlumnoDTO.setDniAlumno(a.getNroDocumento());
				pasajeAlumnoDTO.setNombre(a.getNombre());
				pasajeAlumnoDTO.setApellido(a.getApellido());
				pasajeAlumnoDTO.setAnioActual(anioBuscar.getNombre());
				pasajeAlumnoDTO.setCursoActual(String.valueOf(cursoBuscar.getDivision()));
				if(anioSiguiente!=null){													// Preguntar si hay año siguiente y sugerirlo
					pasajeAlumnoDTO.setIdAnioSiguiente(anioSiguiente.getIdAnio());
				}else{
					pasajeAlumnoDTO.setIdAnioSiguiente(null);
				}
				if(anioSiguiente!=null && anioSiguiente.getListaCursos().size() == 1){ //Si hay anio siguiente y tiene un solo curso, lo sugiero
					for(Curso c : anioSiguiente.getListaCursos()){
						pasajeAlumnoDTO.setIdCursoSiguiente(c.getIdCurso());
					}
				}else{
					pasajeAlumnoDTO.setIdCursoSiguiente(null);
				}
				
				
				pasajeAlumnoDTO.setCantPrevias(sAcad.getPreviasDesaprobadas(a.getNroDocumento()).size());
				pasajeAlumnoDTO.setHabilitadoPromocion(pasajeAlumnoDTO.getCantPrevias() < 3 ? true : false);
				
				listaPasajeAlumnosDTO.getListaPasajeAlumnosDTO().add(pasajeAlumnoDTO);
			}
			listaPasajeAlumnosDTO.setAnio(anioBuscar.getNombre());
			listaPasajeAlumnosDTO.setCurso(cursoBuscar.getDivision().toString());
			
			return listaPasajeAlumnosDTO;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los alumnos para pasar de año: " + ex.getMessage());
		}
	}
	
	/**
	 * Genera una lista de ListaPasajeAlumnoDTO usando listaAlumnosPasajeCurso(anio,especialidad,curso) <br>
	 * Obtiene todos los ListaPasajeAlumnoDTO de la totalidad de años existentes.
	 * @return
	 */
	public List<ListaPasajeAlumnosDTO> ObtenerArregloPasajeDTO() throws ValidacionException, Exception{
		try{
		// Obtener todos los años con especialidad y curso;
		// Por cada año-especialidad-curso llamar a listaAlumnosPasajeCurso y encadenar los resultados entre todos los años
			Long contadorEntes = 0L; // Cuenta cada combinación anio-especialidad-curso
			List<ListaPasajeAlumnosDTO> listaCompleta = new ArrayList<ListaPasajeAlumnosDTO>();
			ServicioAcademico sAcademico = new ServicioAcademico();
			List<AnioDTO> aniosDTO = sAcademico.getAniosDTO();
			for(AnioDTO aDTO: aniosDTO){
				for(CursoDTO cDTO: aDTO.getListaCursos()){
					listaCompleta.add(listaAlumnosPasajeCurso(aDTO.getNombre(),aDTO.getEspecialidad(),cDTO.getDivision()));
					contadorEntes = contadorEntes + cDTO.getCantAlu();
				}
			}
		if(listaCompleta.size() == 0 || listaCompleta.size() != contadorEntes){
			ValidacionException vEx = new ValidacionException();
			vEx.addMensajeError("Ocurrió un problema: la lista generada está incompleta.");
			throw vEx;
		}
		
		return listaCompleta;
		
		}catch(ValidacionException ex){
			throw ex;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * Llama a los métodos encargados de generar una promoción, repetincia o graduación según sea el caso del alumno procesado.
	 * @param listado
	 * @return
	 */
	public Boolean ProcesarPromocion(List<RegistroPasajeAlumnos> pasajeDTOin) throws Exception {
		try {
			ServicioAcademico sAcademico = new ServicioAcademico();
			ServicioAlumnadoAcademico sAlumnado = new ServicioAlumnadoAcademico();
			List<AnioDTO> todosLosAnios = sAcademico.getAniosDTO();
			//this.validarCoherencia(pasajeDTOin);
			
			for (RegistroPasajeAlumnos peDTO : pasajeDTOin) {
				Alumno alumnoObtenido = (Alumno) gAlumno.getById(peDTO.getIdAlumno());
				BoletinNotas boletinNotasAlumno = (BoletinNotas) this.encontrarBoletinDeAlumno(alumnoObtenido, this.BUSCAR_BOLETIN_NOTAS);
				// ver si se va a usar el boletin de inasistencias
				// >> pasar boletín a histórico
				this.pasarAHistorico(boletinNotasAlumno);
				// nulleo el boletín porque puede llegar a dar problemas cuando entre al IF
				boletinNotasAlumno = null;
//				if (peDTO.getPromociona() == true) {
//					// >> desvincular alumno de curso y vincularlo al nuevo
//					sAlumnado.desvincularAlumnoDeCurso(alumnoObtenido, peDTO.getIdCursoActual());
//					sAlumnado.asignarAlumnoACurso(alumnoObtenido, peDTO.getIdCursoSiguiente());
//				} else {
//					Curso cursoDeAlumno = sAcademico.getCurso(peDTO.getIdCursoSiguiente());
//				}
				sAlumnado.desvincularAlumnoDeCurso(alumnoObtenido, peDTO.getIdCursoActual());
				sAlumnado.asignarAlumnoACurso(alumnoObtenido, peDTO.getIdCursoSiguiente());
			}
			return true;
		} catch(ValidacionException ex) {
			throw ex;
		} catch(Exception ex) {
			throw ex;
		}
	}
	
	private void validarCoherencia(List<RegistroPasajeAlumnos> pasajeDTOin) throws Exception{
		ServicioAcademico sAcademico = new ServicioAcademico();
		List<AnioDTO> todosLosAnios = sAcademico.getAniosDTO();
		Integer ordenActual, ordenSiguiente = 0;
		try{
			for(RegistroPasajeAlumnos p : pasajeDTOin){
				ordenActual = sAcademico.ObtenerOrdenAnio(p.getIdAnioActual());
				ordenSiguiente = sAcademico.ObtenerOrdenAnioCurso(p.getIdCursoSiguiente());
				if(true){
					
				}
			}
		}catch(ValidacionException ex){
			throw ex;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/*------ <<END>> PROCESO DEL PASAJE DE ALUMNOS -----------------*/
	
	
	/**
	 * Devuelve una planilla trimestral para uso del docente.
	 * @param getPlanilla
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	public PlanillaTrimestralDocenteDTO getPlanillaTrimestralDocente(GetPlanillaTrimestralDocDTO getPlanilla) throws ValidacionException, Exception{
		try{
			//Recupero la planilla Trimestral como si fuera un directivo, para eso armo una solicitud al método getPlanillaTrimestral
			ValidacionException vEx = new ValidacionException();
			GetPlanillaTrimestralDTO gPlan = new GetPlanillaTrimestralDTO();
			gPlan.setAnio(getPlanilla.getAnio());
			gPlan.setCicloLectivo(getPlanilla.getCicloLectivo());
			gPlan.setCurso(getPlanilla.getCurso());
			gPlan.setTrimestre(getPlanilla.getTrimestre());
			List<ItemPlanillaTrimestralDTO> itemPlanilla = getPlanillaTrimestral(gPlan); 	//Tengo todas las materia en items
			Materia materia = gMateria.getById(getPlanilla.getIdMateria()); 
			PlanillaTrimestralDocenteDTO planillaDevolver = new PlanillaTrimestralDocenteDTO(); //Elemento a devolver vacío 
			if(materia==null){
				vEx.addMensajeError("No se pudo encontrar la materia solicitada.");
				throw vEx;
			}
			List<DetallePlanillaTrimestralDocenteDTO> detalle = new ArrayList<DetallePlanillaTrimestralDocenteDTO>(); //este es el detalle de la planilla de docente
			for(ItemPlanillaTrimestralDTO itemPadre: itemPlanilla){
				for(MateriaNotaDTO mnotaDTO: itemPadre.getNotas()){
					if (mnotaDTO.getMateria().equals(materia.getNombre())){
						DetallePlanillaTrimestralDocenteDTO elemento = new DetallePlanillaTrimestralDocenteDTO(); //creo el elemento y después lo agrego al detalle
						elemento.setAlumno(itemPadre.getAlumno());
						elemento.setNota(mnotaDTO.getNota());
						detalle.add(elemento);
					}
				}
			}
			planillaDevolver.setAnio(getPlanilla.getAnio());
			planillaDevolver.setCurso(getPlanilla.getCurso());
			planillaDevolver.setIdMateria(getPlanilla.getIdMateria());
			planillaDevolver.setTrimestre(getPlanilla.getTrimestre());
			planillaDevolver.setPlanilla(detalle);
			return planillaDevolver;
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	/**
	 * Procesa la planilla trimestral de Docente.
	 * @param planillaTrimestralDTO
	 * @return
	 * @throws ValidacionException
	 * @throws Exception
	 */
	public Boolean procesarPlanillaTrimestralDocente(PlanillaTrimestralDocenteDTO planillaTrimestralDTO) throws ValidacionException, Exception {
		try{
			ValidacionException vEx = new ValidacionException();
			ServicioAcademico sAcademico = new ServicioAcademico();
			Materia materia = sAcademico.getMateria(planillaTrimestralDTO.getIdMateria());
			if(materia == null){
				vEx.addMensajeError("No se pudo encontrar la materia.");
				throw vEx;
			}
			
			PlanillaTrimestralDTO planillaTrimestral = new PlanillaTrimestralDTO();
			planillaTrimestral.setAnio(planillaTrimestralDTO.getAnio());
			planillaTrimestral.setCurso(planillaTrimestralDTO.getCurso());
			planillaTrimestral.setTrimestre(planillaTrimestralDTO.getTrimestre());
			List<ItemPlanillaTrimestralDTO> itemsPlanilla = new ArrayList<ItemPlanillaTrimestralDTO>();
			List<DetallePlanillaTrimestralDocenteDTO> detalle = planillaTrimestralDTO.getPlanilla();
			
			for(DetallePlanillaTrimestralDocenteDTO dptDTO : detalle){
				MateriaNotaDTO mnDTO = new MateriaNotaDTO();
				mnDTO.setMateria(materia.getNombre());
				mnDTO.setNota(dptDTO.getNota());
				List<MateriaNotaDTO> arrayMnDTO = new ArrayList<MateriaNotaDTO>();
				arrayMnDTO.add(mnDTO);
				ItemPlanillaTrimestralDTO ipt = new ItemPlanillaTrimestralDTO();
				ipt.setAlumno(dptDTO.getAlumno());
				ipt.setNotas((ArrayList<MateriaNotaDTO>) arrayMnDTO);
				itemsPlanilla.add(ipt);
			}
			planillaTrimestral.setPlanilla((ArrayList<ItemPlanillaTrimestralDTO>) itemsPlanilla);
			//Llamo al método procesar planilla
			procesarPlanillaTrimestral(planillaTrimestral);
			return true;
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
}



