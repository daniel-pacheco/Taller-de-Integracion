package ar.com.santalucia.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotas;
import ar.com.santalucia.servicio.ServicioBoletinNotas;
import ar.com.santalucia.servicio.ServicioInscripcionMesa;

@Path("/sBoletinNotas")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class ServicioBoletinNotasEndpoint {
	
	private ServicioBoletinNotas servicioBoletinNotas = null;

	/**
	 * Instancia un objeto ServicioInscripionMesa si no existe
	 * @throws Exception
	 */
	private void setInstance() throws Exception {
		if (servicioBoletinNotas == null) {
			try {
				servicioBoletinNotas = new ServicioBoletinNotas();
			} catch (Exception ex) {
				throw ex;
			}
		}
	}
	
	@POST
	public Response create(final ServicioBoletinNotas servicioboletinnotas) {
		//TODO: process the given servicioboletinnotas 
		//you may want to use the following return statement, assuming that ServicioBoletinNotas#getId() or a similar method 
		//would provide the identifier to retrieve the created ServicioBoletinNotas resource:
		//return Response.created(UriBuilder.fromResource(ServicioBoletinNotasEndpoint.class).path(String.valueOf(servicioboletinnotas.getId())).build()).build();
		return Response.created(null).build();
	}

	/*
	 * métodos de add, delete, get, etc 
	 * 
	 * 
	 * 
	 */
	
	@POST
	@Path("/toHist/")
	public Response pasarAHistorico(BoletinNotas boletinNotas) {
		try {
			setInstance();
			Boolean resultado = servicioBoletinNotas.pasarAHistorico(boletinNotas);
			return Response.ok(resultado).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
}
