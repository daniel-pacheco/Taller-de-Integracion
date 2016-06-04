/**
 * 
 */
package ar.com.santalucia.rest;

import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import ar.com.santalucia.dominio.modelo.sistema.configuracion.ParametroConfiguracion;
import ar.com.santalucia.servicio.ServicioConfiguracion;

/**
 * @author Ariel
 *
 */
@Path("/sConfiguracion")
@Produces("application/json")
@Consumes("application/json")
public class ServicioConfiguracionEndpoint {

	private ServicioConfiguracion servicioConfiguracion = null;

	private void setInstance() throws Exception {
		if (servicioConfiguracion == null) {
			try {
				servicioConfiguracion = new ServicioConfiguracion();
			} catch (Exception ex) {
				throw ex;
			}
		}
	}
	
	@GET
	@Path("/parametro/{id:[0-9][0-9]*}")
	public Response getParametroById(@PathParam("id") final Long id){
		ParametroConfiguracion parametro = new ParametroConfiguracion();
		try{
			setInstance();
			parametro = servicioConfiguracion.getParametro(id);
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
		return Response.ok(parametro).build();
	}
	
	@PUT
	@Path("/parametro/")
	public Response updateParametro(final ParametroConfiguracion parametro){
		try{
			setInstance();
			servicioConfiguracion.addParametro(parametro);
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
		return Response.ok(parametro.getIdParametroConfiguracion()).build();
	}
	
	@DELETE
	@Path("/parametro/{id:[0-9][0-9]*}")
	public Response deleteParametroById(@PathParam("id") final Long id){
		try{
			setInstance();
			return Response.ok(servicioConfiguracion.deleteParametro(servicioConfiguracion.getParametro(id))).build();
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
	}
	
	@PUT
	@Path("/parametro/addAll")
	public Response updateAllParametros(final ArrayList<ParametroConfiguracion> parametros){
		try{
			setInstance();
			for(ParametroConfiguracion pc: parametros){
				servicioConfiguracion.addParametro(pc);
			}
			return Response.ok(true).build();
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
	}
}
