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

import ar.com.santalucia.dominio.dto.RecuperarClaveDTO;
import ar.com.santalucia.dominio.modelo.sistema.login.Login;
import ar.com.santalucia.excepciones.LoginError;
import ar.com.santalucia.excepciones.ValidacionException;
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
			//Logger.getLogger(getClass().getName()).log(Level.INFO,"Se requiri� autenticaci�n para usuario:" +credenciales[0]+" rol:"+ credenciales[2]); // Registro de log
			return Response.ok().header("auth0", token).header("Access-Control-Expose-Headers", "auth0").build();
		}catch (LoginError ex){
			//return Response.ok(ex).build();
			return Response.status(Status.UNAUTHORIZED).build();
		}catch(Exception ex){
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * Este m�todo se puede utilziar para determinar la validez de un token y rol sin ser necesario hacer una petici�n de obtenci�n o inserci�n de datos.
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
			//Logger.getLogger(getClass().getName()).log(Level.INFO,"Se requiri� validaci�n de credenciales para el usuario: " +ServicioLogin.obtenerIdentificacionUsuario(rolIn, (nuevoToken==null ?token:nuevoToken))+" rol:"+ rolIn); 
		}catch (LoginError ex){
			//Logger.getLogger(getClass().getName()).log(Level.WARNING,"Fall� la validaci�n de token");
			switch (ex.getDetalles()) {
			case LoginError.ROLERROR: 
				return Response.status(Status.UNAUTHORIZED).build();
			case LoginError.FIRMAERROR:
				return Response.status(Status.FORBIDDEN).build();
			case LoginError.EXPIRADO:
				return Response.status(Status.SEE_OTHER).build();
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
	
	@POST
	@Path("/recuperarClave/")
	public Response recuperarContrase�a(final RecuperarClaveDTO recuClave) {
		try {
			setInstance();
			return Response.ok(servicioLogin.recuperarClave(recuClave.getDniUsuario(), recuClave.getRol(), recuClave.getEmail())).build();
		} catch (ValidacionException ex) {
			return Response.status(Status.CONFLICT).entity(new FrontMessage(ex.getMensajesError().toString(),FrontMessage.INFO)).build();
		} catch (Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Hubo un error al enviar el correo "
					+ "con la contrase�a: " + ex.getMessage()).build();
		}
	}

	/**
	 * Rol de acceso: TODOS
	 * Recibe un objeto del siguiente tipo ["dniUsuario", "claveActual", "claveNueva"]
	 * @param parametros
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@POST
	@Path("/cambiarClave")
	public Response cambiarContrase�a(final String[] parametros,
			@HeaderParam("rol") final String rolIn, 
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.DOCENTE) && !rolIn.equals(Login.ADMINISTRADOR) && !rolIn.equals(Login.ALUMNO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		Boolean resultado = false;
		String nuevoToken = new String();
		try {
			setInstance();
			try {
				nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			} catch (ValidacionException vEx) {
				return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
			}
			resultado = servicioLogin.cambiarContrase�a(parametros[0], rolIn, parametros[1].toString(), parametros[2].toString());
			if (nuevoToken == null) {
				return Response.ok(resultado).build();
			} else {
				return Response.ok(resultado).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.CONFLICT).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
}
