package ar.com.santalucia.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.logging.Level;
import java.util.logging.Logger;

import ar.com.santalucia.excepciones.LoginError;
import ar.com.santalucia.servicio.ServicioLogin;

@Path("/sLogin")
@Produces({ "application/xml", "application/json" }) 
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
			//Logger.getLogger(getClass().getName()).log(Level.INFO,"Se requirió autenticación para usuario:" +credenciales[0]+" rol:"+ credenciales[2]); // Registro de log
			return Response.ok().header("auth0", token).header("Access-Control-Expose-Headers", "auth0").build();
		}catch (LoginError ex){
			//return Response.ok(ex).build();
			return Response.status(Status.UNAUTHORIZED).build();
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * Este método se puede utilziar para determinar la validez de un token y rol sin ser necesario hacer una petición de obtención o inserción de datos.
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@POST
	@Path("/validar")
	public Response validar(@HeaderParam("rol") String rolIn, @HeaderParam("auth0") String token){
		String nuevoToken = null;
		try{
			//setInstance();
			nuevoToken = ServicioLogin.comprobar(token, rolIn);
			//Logger.getLogger(getClass().getName()).log(Level.INFO,"Se requirió validación de credenciales para el usuario: " +ServicioLogin.obtenerIdentificacionUsuario(rolIn, (nuevoToken==null ?token:nuevoToken))+" rol:"+ rolIn); 
		}catch (LoginError ex){
			//Logger.getLogger(getClass().getName()).log(Level.WARNING,"Falló la validación de token");
			switch (ex.getDetalles()) {
			case LoginError.ROLERROR: 
				return Response.status(Status.UNAUTHORIZED).build();
			case LoginError.FIRMAERROR:
				return Response.status(Status.FORBIDDEN).build();
			case LoginError.EXPIRADO:
				return Response.ok(ex).build();
			default:
				break;
			}
		} catch (Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("No fue posible validar las credenciales").build();
		}
		if(nuevoToken == null){
			return Response.ok(true).build();
		}else{
			return Response.ok(true).header("auth0", nuevoToken).build();
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
	
	@POST
	@Path("/recuperarClave/{dni:[0-9][0-9]*}")
	public Response recuperarContraseña(@PathParam("dni") final Long dniUsuario, @HeaderParam("rol") final String rol) {
		try {
			setInstance();
			return Response.ok(servicioLogin.recuperarClave(dniUsuario, rol)).build();
		} catch (Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Hubo un error al enviar el correo "
					+ "con la contraseña: " + ex.getMessage()).build();
		}
	}

}
