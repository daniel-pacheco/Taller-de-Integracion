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

import ar.com.santalucia.excepciones.LoginError;
import ar.com.santalucia.servicio.ServicioAlumno;
import ar.com.santalucia.servicio.ServicioLogin;

@Path("/sLogin")
@Produces({ "application/xml", "application/json" }) /*¿PODRÁ SER URL-ENCODE?*/
@Consumes({ "application/xml", "application/json" })
public class ServicioLoginEndpoint {

	private ServicioLogin servicioLogin;
	
	private void setInstance() throws Exception {
		if (servicioLogin == null) {
			try {
				servicioLogin = new ServicioLogin();
			} catch (Exception ex) {
				throw ex;
			}
		}
	}	
	
	
	
	@POST
	public Response create(final ServicioLogin serviciologin) {
		//TODO: process the given serviciologin 
		//you may want to use the following return statement, assuming that ServicioLogin#getId() or a similar method 
		//would provide the identifier to retrieve the created ServicioLogin resource:
		//return Response.created(UriBuilder.fromResource(ServicioLoginEndpoint.class).path(String.valueOf(serviciologin.getId())).build()).build();
		return Response.created(null).build();
	}

	@POST
	@Path("/login")
	public Response login(final String[] credenciales){
		String token = null;
		try{
			setInstance();
			token = servicioLogin.autenticar(Long.valueOf(credenciales[0]),credenciales[1],credenciales[2]);
			return Response.ok().header("auth0", token).build();
		}catch (LoginError ex){
			//return Response.ok(ex).build();
			return Response.status(Status.UNAUTHORIZED).build();
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
	}
	
	
	
	
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		//TODO: retrieve the serviciologin 
		ServicioLogin serviciologin = null;
		if (serviciologin == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(serviciologin).build();
	}

	@GET
	public List<ServicioLogin> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		//TODO: retrieve the serviciologins 
		final List<ServicioLogin> serviciologins = null;
		return serviciologins;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final ServicioLogin serviciologin) {
		//TODO: process the given serviciologin 
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		//TODO: process the serviciologin matching by the given id 
		return Response.noContent().build();
	}

}
