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
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotas;
import ar.com.santalucia.dominio.modelo.desempenio.Nota;
import ar.com.santalucia.dominio.modelo.desempenio.Trimestre;
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
	
	
}
