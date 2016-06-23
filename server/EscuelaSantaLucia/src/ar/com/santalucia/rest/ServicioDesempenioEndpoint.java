package ar.com.santalucia.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ar.com.santalucia.dominio.dto.BoletinInasistenciasDTO;
import ar.com.santalucia.dominio.dto.GetPlanillaTrimestralDTO;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinInasistencias;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotas;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotasHist;
import ar.com.santalucia.dominio.modelo.desempenio.Inasistencia;
import ar.com.santalucia.dominio.modelo.desempenio.Nota;
import ar.com.santalucia.dominio.modelo.desempenio.Trimestre;
import ar.com.santalucia.excepciones.InasistenciaException;
import ar.com.santalucia.servicio.ServicioAcademico;
import ar.com.santalucia.servicio.ServicioAlumno;
import ar.com.santalucia.servicio.ServicioDesempenio;

@Path("/sDesempenio")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class ServicioDesempenioEndpoint {
	
	private ServicioDesempenio servicioDesempenio = null;

	/**
	 * Instancia un objeto ServicioInscripionMesa si no existe
	 * @throws Exception
	 */
	private void setInstance() throws Exception {
		if (servicioDesempenio == null) {
			try {
				servicioDesempenio = new ServicioDesempenio();
			} catch (Exception ex) {
				throw ex;
			}
		}
	}

	
	@PUT
	@Path("/bol/")
	public Response updateBoletinNotas(BoletinNotas boletinNotas) {
		try {
			setInstance();
			servicioDesempenio.addBoletinNotas(boletinNotas);
			return Response.ok(boletinNotas.getIdBoletinNotas()).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@DELETE
	@Path("/bol/{id:[0-9][0-9]*}")
	public Response deleteBoletinNotasById(@PathParam("id") Long idBoletinNotas) {
		try {
			setInstance();
			return Response.ok(servicioDesempenio.deleteBoletinNotas(
								servicioDesempenio.getBoletin(idBoletinNotas))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@GET
	@Path("/bol/{id:[0-9][0-9]*}")
	public Response getBoletinNotasById(@PathParam("id") Long idBoletinNotas) {
		try {
			setInstance();
			return Response.ok(servicioDesempenio.getBoletin(idBoletinNotas)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@GET
	@Path("/bol/listAll")
	public Response boletinNotasListAll() {
		try {
			BoletinNotas boletin = new BoletinNotas();
			return Response.ok(servicioDesempenio.getBoletines(boletin)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@POST
	@Path("/bol/toHist/")
	public Response pasarAHistorico(BoletinNotas boletinNotas) {
		try {
			setInstance();
			Boolean resultado = servicioDesempenio.pasarAHistorico(boletinNotas);
			return Response.ok(resultado).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@PUT
	@Path("/bol/addHist")
	public Response addBoletinHistorico(BoletinNotasHist boletinHistorico) {
		try {
			setInstance();
			return Response.ok(servicioDesempenio.addBoletinHistorico(boletinHistorico)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	
	@PUT
	@Path("/not/")
	public Response updateNota(Nota nota) {
		try {
			setInstance();
			servicioDesempenio.addNota(nota);
			return Response.ok(nota.getIdNota()).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@DELETE
	@Path("/not/{id:[0-9][0-9]*}")
	public Response deleteNotaById(@PathParam("id") Long idNota) {
		try {
			return Response.ok(servicioDesempenio.deleteNota(servicioDesempenio.getNota(idNota))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
			
		}
	}

	@GET
	@Path("/not/{id:[0-9][0-9]*}")
	public Response getNotaById(@PathParam("id") Long idNota) {
		try {
			return Response.ok(servicioDesempenio.getNota(idNota)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@GET
	@Path("/not/listAll")
	public Response notaListAll() {
		try {
			Nota nota = new Nota();
			return Response.ok(servicioDesempenio.getNotas(nota)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	
	@PUT
	@Path("/trim/")
	public Response updateTrimestre(Trimestre trimestre) {
		try {
			setInstance();
			servicioDesempenio.addTrimestre(trimestre);
			return Response.ok(trimestre.getIdTrimestre()).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}

	@DELETE
	@Path("/trim/{id:[0-9][0-9]*}")
	public Response deleteTrimestreById(@PathParam("id") Long idTrimestre) {
		try {
			setInstance();
			return Response.ok(servicioDesempenio.deleteTrimestre(servicioDesempenio.getTrimestre(idTrimestre))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}

	@GET
	@Path("/trim/{id:[0-9][0-9]*}")
	public Response getTrimestreById(@PathParam("id") Long idTrimestre) {
		try {
			setInstance();
			return Response.ok(servicioDesempenio.getTrimestre(idTrimestre)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}

	@GET
	@Path("/trim/listAll")
	public Response trimestreListAll() {
		try {
			setInstance();
			Trimestre trimestre = new Trimestre();
			return Response.ok(servicioDesempenio.getTrimestres(trimestre)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * 
	 * @param jsonPack [idNota, idTrimestre]
	 * @return
	 */
	@POST
	@Path("/trim/asignNota/")
	public Response asignarNotaATrimestre(JsonPack jsonPack) {
		try {
			setInstance();
			return Response.ok(servicioDesempenio.asignarNotaATrimestre(
								servicioDesempenio.getNota(jsonPack.getValues().elementAt(0)), 
								jsonPack.getValues().elementAt(1))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * 
	 * @param jsonPack [idMateria, idTrimestre]
	 * @return
	 */
	@POST
	@Path("/trim/asignMateria")
	public Response asignarMateriaATrimestre(JsonPack jsonPack) {
		try {
			setInstance();
			ServicioAcademico servicioAcademico = new ServicioAcademico();
			return Response.ok(servicioDesempenio.asignarMateriaATrimestre(
								servicioAcademico.getMateria(jsonPack.getValues().elementAt(0)), 
								jsonPack.getValues().elementAt(1))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * 
	 * @param jsonPack [idTrimestre, idBoletin]
	 * @return
	 */
	@POST
	@Path("/bol/asignTrim")
	public Response asignarTrimestreABoletin(JsonPack jsonPack) {
		try {
			setInstance();
			return Response.ok(servicioDesempenio.asignarTrimestreABoletin(
								servicioDesempenio.getTrimestre(jsonPack.getValues().elementAt(0)), 
								jsonPack.getValues().elementAt(1))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * 
	 * @param jsonPack [idAlumno, idBoletin]
	 * @return
	 */
	@POST
	@Path("/bol/asignAlu")
	public Response asignarAlumnoABoletin(JsonPack jsonPack) {
		try {
			setInstance();
			ServicioAlumno servicioAlumno = new ServicioAlumno();
			return Response.ok(servicioDesempenio.asignarAlumnoABoletin(
								servicioAlumno.getUsuario(jsonPack.getValues().elementAt(0)), 
								jsonPack.getValues().elementAt(1))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@POST
	@Path("/planillaTrimestral")
	public Response getPlanillaTrimestral(GetPlanillaTrimestralDTO gptDTO) {
		try {
			setInstance();
			if ((gptDTO.getNroTrimestre() == 0) 
					|| (gptDTO.getCicloLectivo() == 0) 
					|| (gptDTO.getCurso().equals("")) 
					|| (gptDTO.getNombreAnio().equals(""))) {
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new FrontMessage("Hubo un error en los parámetros de consulta", FrontMessage.INFO))
						.build();
			} else {
				return Response.ok(servicioDesempenio.getPlanillaTrimestral(gptDTO)).build();
			}
		} catch (Exception ex) {
			// return Response.ok(ex).build();
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Hubo un error al obtener la planilla trimestral: " + ex.getMessage(), FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Test method
	 * @param inasistencia
	 * @return
	 */
	@PUT
	@Path("/inasistencia/")
	public Response updateInasistencia(final Inasistencia inasistencia) { // test
		try {
			setInstance();
			servicioDesempenio.addInasistencia(inasistencia);
			return Response.ok(inasistencia.getIdInasistencia()).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@PUT
	@Path("/boletinInasist")
	public Response updateBoletinInasistencias(final BoletinInasistencias boletinInasistencias) {
		try {
			setInstance();
			servicioDesempenio.addBoletinInasistencias(boletinInasistencias);
			return Response.ok(boletinInasistencias.getIdBoletinInasistencias()).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@GET
	@Path("/boletinInasist/{dni:[0-9][0-9]*}")
	public Response getBoletinInasistenciasDTObyDni(@PathParam("dni") Long dniAlumno) {
		try {
			setInstance();
			return Response.ok(servicioDesempenio.getBoletinInasistenciasDTObyDni(dniAlumno)).build();
		} catch (Exception ex) {
			// return Response.ok(ex).build();
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Hubo un error al obtener el boletín de inasistencias por el DNI: " 
							+ ex.getMessage(), FrontMessage.CRITICAL))
					.build();
		}
	}
	
	
	@POST
	@Path("/inasistencia/procesar")
	public Response procesarBoletinInasistencias(BoletinInasistenciasDTO boletinInasistenciasDTO) throws Exception {
		try {
			setInstance();
			return Response.ok(servicioDesempenio.procesarBoletinInasistencias(boletinInasistenciasDTO)).build();
		} catch (InasistenciaException iEx) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(iEx).build();
		} catch (Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Hubo un error al procesar las inasistencias: " 
												+ ex.getMessage(), FrontMessage.CRITICAL))
					.build();
		}
	}
	
	

}
