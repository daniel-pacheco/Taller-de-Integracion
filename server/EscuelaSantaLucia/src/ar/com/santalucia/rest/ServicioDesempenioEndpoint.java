package ar.com.santalucia.rest;

import java.util.ArrayList;
import java.util.List;

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
import ar.com.santalucia.dominio.dto.BoletinNotasDTO;
import ar.com.santalucia.dominio.dto.GetListaPasajeAlumnosDTO;
import ar.com.santalucia.dominio.dto.GetPlanillaTrimestralDTO;
import ar.com.santalucia.dominio.dto.ListaPasajeAlumnosDTO;
import ar.com.santalucia.dominio.dto.PlanillaTrimestralDTO;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinInasistencias;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotas;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotasHist;
import ar.com.santalucia.dominio.modelo.desempenio.Inasistencia;
import ar.com.santalucia.dominio.modelo.desempenio.Nota;
import ar.com.santalucia.dominio.modelo.desempenio.Trimestre;
import ar.com.santalucia.excepciones.InasistenciaException;
import ar.com.santalucia.excepciones.ValidacionException;
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@DELETE
	@Path("/bol/{id:[0-9][0-9]*}")
	public Response deleteBoletinNotasById(@PathParam("id") Long idBoletinNotas) {
		BoletinNotas boletinNotas = new BoletinNotas();
		try {
			setInstance();
			boletinNotas = servicioDesempenio.getBoletin(idBoletinNotas);
			return Response.ok(servicioDesempenio.deleteBoletinNotas(boletinNotas)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@GET
	@Path("/bol/{id:[0-9][0-9]*}")
	public Response getBoletinNotasById(@PathParam("id") Long idBoletinNotas) {
		BoletinNotas boletinNotas = new BoletinNotas();
		try {
			setInstance();
			boletinNotas = servicioDesempenio.getBoletin(idBoletinNotas);
			if (boletinNotas == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(boletinNotas).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@GET
	@Path("/boletin/{id:[0-9][0-9]*}")
	public Response getBoletinNotasDTObyIdAlumno(@PathParam("id") final Long idAlumno) {
		BoletinNotasDTO boletinDTO = new BoletinNotasDTO();
		try {
			setInstance();
			boletinDTO = servicioDesempenio.getBoletinNotasDTObyIdAlumno(idAlumno);
			if (boletinDTO == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(boletinDTO).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@GET
	@Path("/bol/listAll")
	public Response boletinNotasListAll() {
		List<BoletinNotas> boletines = new ArrayList<BoletinNotas>();
		try {
			boletines = servicioDesempenio.getBoletines(new BoletinNotas());
			if (boletines.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			return Response.ok(boletines).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@PUT
	@Path("/bol/addHist")
	public Response addBoletinHistorico(BoletinNotasHist boletinHistorico) {
		try {
			setInstance();
			return Response.ok(servicioDesempenio.addBoletinHistorico(boletinHistorico)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@DELETE
	@Path("/not/{id:[0-9][0-9]*}")
	public Response deleteNotaById(@PathParam("id") Long idNota) {
		Nota nota = new Nota();
		try {
			nota = servicioDesempenio.getNota(idNota); 
			return Response.ok(servicioDesempenio.deleteNota(nota)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}

	@GET
	@Path("/not/{id:[0-9][0-9]*}")
	public Response getNotaById(@PathParam("id") Long idNota) {
		Nota nota = new Nota();
		try {
			nota = servicioDesempenio.getNota(idNota);
			if (nota == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(nota).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@GET
	@Path("/not/listAll")
	public Response notaListAll() {
		List<Nota> notas = new ArrayList<Nota>();
		try {
			notas = servicioDesempenio.getNotas(new Nota());
			if (notas.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			return Response.ok(notas).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();

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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();

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
		Trimestre trimestre = new Trimestre();
		try {
			setInstance();
			trimestre = servicioDesempenio.getTrimestre(idTrimestre);
			if (trimestre == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(trimestre).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}

	@GET
	@Path("/trim/listAll")
	public Response trimestreListAll() {
		List<Trimestre> trimestres = new ArrayList<Trimestre>();
		try {
			setInstance();
			trimestres = servicioDesempenio.getTrimestres(new Trimestre());
			if (trimestres.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			return Response.ok().build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@POST
	@Path("/planillaTrimestral")
	public Response getPlanillaTrimestral(GetPlanillaTrimestralDTO gptDTO) {
		try {
			setInstance();
			if ((gptDTO.getTrimestre() < 1)
					|| (gptDTO.getTrimestre() > 3)
					|| (gptDTO.getCicloLectivo() == 0) 
					|| (gptDTO.getCurso().equals("")) 
					|| (gptDTO.getAnio().equals(""))) {
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new FrontMessage("Hubo un error en los par�metros de consulta", FrontMessage.INFO))
						.build();
			} else {
				return Response.ok(servicioDesempenio.getPlanillaTrimestral(gptDTO)).build();
			}
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@GET
	@Path("/boletinInasist/{dni:[0-9][0-9]*}")
	public Response getBoletinInasistenciasDTObyDni(@PathParam("dni") Long dniAlumno) {
		BoletinInasistenciasDTO boletinInasistenciasDTO = new BoletinInasistenciasDTO();
		try {
			setInstance();
			boletinInasistenciasDTO = servicioDesempenio.getBoletinInasistenciasDTObyDni(dniAlumno);
			if (boletinInasistenciasDTO == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(boletinInasistenciasDTO).build();
		} catch (Exception ex) {
			// return Response.ok(ex).build();
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Hubo un error al obtener el bolet�n de inasistencias por el DNI: " 
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
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(iEx.getInasistenciasInvalidas()).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@PUT
	@Path("/planillaTrimestral")
	public Response procesarPlanillaTrimestral(PlanillaTrimestralDTO planillaTrimestralDTO){
		try {
			setInstance();
			servicioDesempenio.procesarPlanillaTrimestral(planillaTrimestralDTO);
			return Response.ok(true).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Devuelve una lista con todos los alumnos (lista de ListaPasajeAlumnosDTO) preparados para el pasaje de a�o (promoci�n, repitencia, graduaci�n)
	 * @return
	 */
	@POST
	@Path("/pasajeAnioMasivo")
	public Response listaPasajeAnioMasivo(){
		try{
			setInstance();
			return Response.ok(servicioDesempenio.ObtenerArregloPasajeDTO()).build();
		}catch(ValidacionException ex){
			return Response.ok().build();
		}catch(Exception ex){
			return Response.ok().build();
		}
	}
	
	/**
	 * Devuelve una lista de los alumnos (ListaPasajeAlumnosDTO) de un a�o para su promoci�n, repitencia, graduaci�n
	 * @param getListaDTO
	 * @return
	 */
	@POST
	@Path("/pasajeAnio/")
	public Response listaPasajeAlumnosDTO(final GetListaPasajeAlumnosDTO getListaDTO) {
		try {
			setInstance();
			ListaPasajeAlumnosDTO lista = servicioDesempenio.listaAlumnosPasajeCurso(getListaDTO.getAnio(),getListaDTO.getEspecialidad(), getListaDTO.getCurso());
			if (lista.getListaPasajeAlumnosDTO().size() == 0) {
				return Response.status(Status.NO_CONTENT)
					.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
					.build();
			}
			return Response.ok(lista).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
						FrontMessage.CRITICAL))
				.build();
		}
	}
	

}
