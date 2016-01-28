/**
 * 
 */
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

import ar.com.santalucia.dominio.modelo.academico.Inscripcion;
import ar.com.santalucia.servicio.ServicioAlumno;
import ar.com.santalucia.servicio.ServicioInscripcionMesa;

/**
 * @author Eric
 *
 */
@Path("/sInscripcionMesa")
@Produces({ "application/json" })
@Consumes({ "application/json" })
public class ServicioInscripcionMesaEndpoint {
	
	private ServicioInscripcionMesa servicioInscripcionMesa = null;
	
	/**
	 * Instancia un objeto ServicioInscripionMesa si no existe
	 * @throws Exception
	 */
	private void setInstance() throws Exception {
		if (servicioInscripcionMesa == null) {
			try {
				servicioInscripcionMesa = new ServicioInscripcionMesa();
			} catch (Exception ex) {
				throw ex;
			}
		}
	}

	@PUT
	@Path("/insc/")
	public Response updateInscripcion(Inscripcion inscripcion) {
		try {
			setInstance();
			servicioInscripcionMesa.addInscripcion(inscripcion);
			return Response.ok(inscripcion.getIdInscripcion()).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@DELETE
	@Path("/insc/{id:[0-9][0-9]*}")
	public Response deleteInscripcionById(@PathParam("id") Long idInscripcion) {
		try {
			setInstance();
			return Response.ok(servicioInscripcionMesa.deleteInscripcion(
								servicioInscripcionMesa.getInscripcion(idInscripcion))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@GET
	@Path("/insc/{id:[0-9][0-9]*}")
	public Response getInscripcionById(@PathParam("id") Long idInscripcion) {
		try {
			setInstance();
			return Response.ok(servicioInscripcionMesa.getInscripcion(idInscripcion)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/*
	@POST
	@Path("/insc/")
	public Response inscribirAlumno(JsonPack jsonPack) {
		
	}
	*/
}
