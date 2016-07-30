package ar.com.santalucia.rest;

import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.catalina.filters.RequestFilter;

import ar.com.santalucia.dominio.dto.AlumnoDTO;
import ar.com.santalucia.dominio.modelo.sistema.login.Login;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.excepciones.LoginError;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.servicio.ServicioAlumno;
import ar.com.santalucia.servicio.ServicioLogin;

/**
 * 
 * @author Ariel Ramirez
 *
 * @version 2.0
 */

// Último modificador: Ariel Ramirez @ 20-07-2016 20:01

@Path("/sAlumno")
@PermitAll
@Produces({"application/json" })
@Consumes({"application/json" })
public class ServicioAlumnoEndpoint{
	
	private ServicioAlumno servicioAlumno = null;
	
	/**
	 * Instancia un objeto ServicioAlumno si no existe
	 * @throws Exception 
	 */
	private void setInstance() throws Exception {
		if (servicioAlumno == null) {
			try {
				servicioAlumno = new ServicioAlumno();
			} catch (Exception ex) {
				throw ex;
			}
		}
	}
	
//	private String comprobarCredenciales(String rol, String token) throws ValidacionException, Exception {
//		try {
//			return ServicioLogin.comprobar(token, rol);
//		} catch (LoginError lEx) {
//			ValidacionException vEx = new ValidacionException();
//			switch (lEx.getDetalles()) {
//			case LoginError.ROLERROR: 
//				vEx.addMensajeError("Acceso no autorizado");
//				break;
//			case LoginError.FIRMAERROR:
//				vEx.addMensajeError("Acceso prohibido");
//				break;
//			case LoginError.EXPIRADO:
//				vEx.addMensajeError("Sesión expirada");
//				break; 
//			default:
//				break;
//			}
//			throw vEx;
//		}
//	}
	
	/**
	 * Rol de acceso: DIRECTIVO - DOCENTE
	 * Devuelve los datos de un alumno específico proporcionando su DNI
	 * @param dni
	 * @return 
	 */
	@GET
	@Path("/alu/getByDni/{dni:[0-9][0-9]*}")
	public Response getAlumnoByDni(@PathParam("dni") final Long dni,
			@HeaderParam("rol") final String rolIn, 
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.DOCENTE)) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		String nuevoToken = new String();
		Alumno alumno = new Alumno();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			alumno = servicioAlumno.getUsuarioByDni(dni);
			if(alumno.getIdUsuario().equals("")){
				return Response.serverError().entity(new FrontMessage("No se ha podido encontrar el alumno",FrontMessage.INFO)).build();
			}
			//} catch (LoginError ex){
//			switch (ex.getDetalles()) {
//			case LoginError.ROLERROR: 
//				return Response.status(Status.UNAUTHORIZED).build();
//			case LoginError.FIRMAERROR:
//				return Response.status(Status.FORBIDDEN).build();
//			case LoginError.EXPIRADO:
//				return Response.status(500).entity(new FrontMessage("Sus credenciales han expirado. Vuelva a iniciar sesión.",FrontMessage.INFO)).build(); //440 IIS LoginTimeOut
//			default:
//				break;
		} catch (ValidacionException vEx) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch(Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
		if (nuevoToken == null) {
			return Response.ok(alumno).build();
		} else {
			return Response.ok(alumno).header("auth0", nuevoToken).build();
		}
	}
	
	/**
	 * Rol de acceso: ALUMNO
	 * Devuelve los datos personales del Alumno logueado solamente enviando rol y token.
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@GET
	@Path("/DatosPersonales")
	public Response obtenerDatosPersonales(@HeaderParam("rol") String rolIn, @HeaderParam("auth0") String token){
		if (!rolIn.equals(Login.ALUMNO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		Long usuarioDni;
		Alumno alumno = new Alumno();
		try {
			//ServicioLogin.comprobar(token, rolIn); //Hace la comprobacion de la credencial
			nuevoToken = ServicioLogin.comprobar(token, rolIn);
			setInstance();
			if(nuevoToken == null){
				usuarioDni = Long.valueOf(ServicioLogin.obtenerIdentificacionUsuario(rolIn, token));
				alumno = servicioAlumno.getUsuarioByDni(usuarioDni);
			}else{
				usuarioDni = Long.valueOf(ServicioLogin.obtenerIdentificacionUsuario(rolIn, nuevoToken));
				alumno = servicioAlumno.getUsuarioByDni(usuarioDni);
			}
			}catch (LoginError ex){
				switch (ex.getDetalles()) {
				case LoginError.ROLERROR: 
					return Response.status(Status.UNAUTHORIZED).build();
				case LoginError.FIRMAERROR:
					return Response.status(Status.FORBIDDEN).build();
				case LoginError.EXPIRADO:
					return Response.status(500).entity(new FrontMessage("Sus credenciales han expirado. Vuelva a iniciar sesión.",FrontMessage.INFO)).build(); //440 IIS LoginTimeOut
				default:
					break;
				}
			} catch (Exception ex) {
				return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
			}
		if(nuevoToken == null){
			return Response.ok(alumno).build();
		}else{
			return Response.ok(alumno).header("auth0", nuevoToken).build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO - DOCENTE
	 * @param id
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@GET
	@Path("/alu/{id:[0-9][0-9]*}")
	public Response getAlumnoById(@PathParam("id") final Long id, 
			@HeaderParam("rol") String rolIn, 
			@HeaderParam("auth0") String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.DOCENTE)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Alumno alumno = new Alumno();
		String nuevoToken = new String();
		try {
			//ServicioLogin.comprobar(token, rolIn); //Hace la comprobacion de la credencial
			//nuevoToken = ServicioLogin.comprobar(token, rolIn);
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			setInstance();
			alumno = servicioAlumno.getUsuario(id);
//		} catch (LoginError ex) {
//			switch (ex.getDetalles()) {
//			case LoginError.ROLERROR: 
//				return Response.status(Status.UNAUTHORIZED).build();
//			case LoginError.FIRMAERROR:
//				return Response.status(Status.FORBIDDEN).build();
//			case LoginError.EXPIRADO:
//				return Response.status(500).entity(new FrontMessage("Sus credenciales han expirado. Vuelva a iniciar sesión.",FrontMessage.INFO)).build(); 
//			default:
//				break;
//			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
		if (nuevoToken == null) {
			return Response.ok(alumno).build();
		} else {
			return Response.ok(alumno).header("auth0", nuevoToken).build();
		}
	}

	/**
	 * Rol de acceso: DIRECTIVO
	 * @param id
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@GET
	@Path("/tel/{id:[0-9][0-9]*}") // tel
	public Response getTelefonos(@PathParam("id") final Long id, 
			@HeaderParam("rol") final String rolIn, 
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Set<Telefono> telefonos = new HashSet<Telefono>();
		String nuevoToken = new String();
		try {
			//nuevoToken = ServicioLogin.comprobar(token, rolIn);
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			setInstance();
			telefonos = servicioAlumno.getTelefonos(id);
			if (telefonos == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
//		} catch (LoginError ex){
//			switch (ex.getDetalles()) {
//			case LoginError.ROLERROR: 
//				return Response.status(Status.UNAUTHORIZED).build();
//			case LoginError.FIRMAERROR:
//				return Response.status(Status.FORBIDDEN).build();
//			case LoginError.EXPIRADO:
//				return Response.status(500).entity(new FrontMessage("Sus credenciales han expirado. Vuelva a iniciar sesión.",FrontMessage.INFO)).build(); //440 IIS LoginTimeOut
//			default:
//				break;
		} catch (ValidacionException vEx) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
		if (nuevoToken == null) {
			return Response.ok(telefonos).build();
		} else {
			return Response.ok(telefonos).header("auth0", nuevoToken).build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * @param id
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@GET
	@Path("/mai/{id:[0-9][0-9]*}")
	public Response getMails(@PathParam("id") final Long id, 
			@HeaderParam("rol") final String rolIn, 
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Set<Mail> mails = new HashSet<Mail>();
		String nuevoToken = new String();
		//mails = null;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			mails = servicioAlumno.getMails(id);
			if (mails == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
//			} catch (LoginError ex){
//			switch (ex.getDetalles()) {
//			case LoginError.ROLERROR: 
//				return Response.status(Status.UNAUTHORIZED).build();
//			case LoginError.FIRMAERROR:
//				return Response.status(Status.FORBIDDEN).build();
//			case LoginError.EXPIRADO:
//				return Response.status(500).entity(new FrontMessage("Sus credenciales han expirado. Vuelva a iniciar sesión.",FrontMessage.INFO)).build(); //440 IIS LoginTimeOut
//			default:
//				break;
		} catch (ValidacionException vEx) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
		if (nuevoToken == null) {
			return Response.ok(mails).build();
		} else {
			return Response.ok(mails).header("auth0", nuevoToken).build();
		}
	}

	/**
	 * Rol de acceso: DIRECTIVO
	 * Utilice este método para el listado de alumnos completo.
	 * @return Response ok (Status 200) y listado de alumnos o 
	 *  null si no hay nada. <br>
	 *  (! PRECAUCIÓN) ¡El método devuelve el listado de todos los alumnos de la base de datos!
	 */
	@GET
	@Path("/listAll")
	public Response listAll(@HeaderParam("rol") final String rolIn, @HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		List<Alumno> alumnos = new ArrayList<Alumno>();
		String nuevoToken = new String();
		alumnos = null;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			alumnos = servicioAlumno.getUsuarios(new Alumno());
			if (alumnos.size() == 0) {
				return Response.status(Status.NO_CONTENT).build();
			}
		//} catch (LoginError ex){
		//switch (ex.getDetalles()) {
		//	case LoginError.ROLERROR: 
		//				return Response.status(Status.UNAUTHORIZED).build();
		//			case LoginError.FIRMAERROR:
		//				return Response.status(Status.FORBIDDEN).build();
		//			case LoginError.EXPIRADO:
		//				return Response.status(500).entity(new FrontMessage("Sus credenciales han expirado. Vuelva a iniciar sesión.",FrontMessage.INFO)).build(); //440 IIS LoginTimeOut
		//			default:
		//				break;
		} catch (ValidacionException vEx) {
				return Response.status(Status.FORBIDDEN).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
		if (nuevoToken == null) {
			return Response.ok(alumnos).build();
		} else {
			return Response.ok(alumnos).header("auth0", nuevoToken).build();
		}
	}

	/**
	 * Rol de acceso: DIRECTIVO
	 * Utilice este método para:<br>
	 * 1) Agregar un nuevo alumno con o sin datos adicionales (teléfono, mail y domicilio) de una sola vez. <br>
	 * 2) Actualizar datos de alumno que no sean adicionales (teléfono, mail y domicilio).<br>
	 * 3) Agregar uno o varios datos adicionales (teléfono y mail).<br>
	 * (! - IMPORTANTE) Para actualizar o eliminar datos adicionales (teléfono, mail y domicilio) use los métodos UPDATE y DELETE respectivos. 
	 * @param alumno
	 * @return Response ok (Status 200) con el id de usuario si el resultado es exitoso o la excepción generada. 
	 */
	@PUT
	@Path("/alu/")
	public Response update(final Alumno alumno, @HeaderParam("rol") String rolIn, @HeaderParam("auth0") String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		try {
			// nuevoToken = ServicioLogin.comprobar(token, rolIn);
			try {
				nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			} catch (ValidacionException vEx) {
				return Response.status(Status.FORBIDDEN).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
			}
			setInstance();
			servicioAlumno.addUsuario(alumno);
		} catch (ValidacionException ex) {
			return Response.status(Status.CONFLICT).entity(ex.getMensajesError()).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
		if (nuevoToken==null) {
			return Response.ok(alumno.getIdUsuario()).build();
		} else {
			return Response.ok(alumno.getIdUsuario()).header("auth0", nuevoToken).build();
		}
	}

	/**
	 * Rol de acceso: DIRECTIVO
	 * Utilice este método para actualizar los datos de un teléfono existente.
	 * @param telefono incluyendo su id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepción generada.
	 */
	@PUT
	@Path("/tel/")
	public Response updateTelefono(final Telefono telefono) { 
		Boolean exito = false;
		try {
			setInstance();
			exito = servicioAlumno.modifyTelefono(telefono);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", FrontMessage.CRITICAL))
					.build();
		}
	}

	/**
	 * Rol de acceso: DIRECTIVO
	 * Utilice este método para actualizar los datos de un mail existente.
	 * @param mail Response ok (Status 200) con true si el resultado es exitoso o la excepción generada.
	 * @return
	 */
	@PUT
	@Path("/mai/")
	public Response updateMail(final Mail mail) {
		Boolean exito = false;
		try {
			setInstance();
			exito = servicioAlumno.modifyMail(mail);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * Utilice este método para actualizar los datos de un domicilio existente.
	 * @param domicilio
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepción generada.
	 */
	@PUT
	@Path("/dom/")
	public Response updateDomicilio(final Domicilio domicilio) {
		boolean exito = false;
		try {
			setInstance();
			exito = servicioAlumno.modifyDomicilio(domicilio);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", FrontMessage.CRITICAL))
					.build();
		}
	}

	/**
	 * Rol de acceso: DIRECTIVO
	 * Utilice este método para eliminar un alumno. Además de eliminar el alumno, también se eliminan los datos adicionales (teléfono, mail y domicilio).
	 * @param id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepción generada.<br>
	 * (! PRECAUCIÓN) Se eliminan todos los datos adicionales asociados (telefono, mail y domicilio) al eliminar el usuario.
	 */
	@DELETE
	@Path("/alu/deleteByDni/{dni:[0-9][0-9]*}")
	public Response deleteAlumnoByDni(@PathParam("dni") final Long dni,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Boolean exito = false;
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			exito = servicioAlumno.removeUsuario(servicioAlumno.getUsuarioByDni(dni));
			if (exito == true) {
				if (nuevoToken == null) {
					return Response.ok(exito).build();
				} else {
					return Response.ok(exito).header("auth0", nuevoToken).build();
				}
			} else {
				return Response.serverError()
						.entity(new FrontMessage ("No se ha podido eliminar el docente", FrontMessage.INFO))
						.build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * @param id
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@DELETE
	@Path("/alu/{id:[0-9][0-9]*}")
	public Response deleteAlumnoById(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		Boolean exito = false;
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			exito = servicioAlumno.removeUsuario(servicioAlumno.getUsuario(id));
			if (exito == true) {
				if (nuevoToken == null) {
					return Response.ok(exito).build();
				} else {
					return Response.ok(exito).header("auth0", nuevoToken).build();
				}
			} else {
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new FrontMessage("No se pudo eliminar el alumno", FrontMessage.INFO))
						.build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", FrontMessage.CRITICAL))
					.build();
		}

	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * Utilice este método para eliminar un teléfono existente.
	 * @param id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepción generada.
	 */
	@DELETE
	@Path("/tel/{id:[0-9][0-9]*}")
	public Response deleteTelefonoById(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token){
		Boolean exito = false;
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			exito = servicioAlumno.removeTelefono(id);
			if (exito == true) {
				if (nuevoToken == null) {
					return Response.ok(exito).build();
				} else {
					return Response.ok(exito).header("auth0", token).build();
				}
			} else {
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new FrontMessage("No se pudo eliminar el teléfono", FrontMessage.INFO))
						.build();
			}
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * Utilice este método para eliminar un domicilio existente.
	 * @param id
	 * @param rolIn 
	 * @param token 
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepción generada.
	 */
	@DELETE
	@Path("/dom/{id:[0-9][0-9]*}")
	public Response deleteDomicilioById(@PathParam("id") final Long id, 
			@HeaderParam("rol") final String rolIn, 
			@HeaderParam("auth0") final String token) {
		Boolean exito = false;
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			exito = servicioAlumno.removeDomicilio(id);
			if (exito == true) {
				if (nuevoToken == null) {
					return Response.ok(exito).build();
				} else {
					return Response.ok(exito).header("auth0", nuevoToken).build();
				}
			} else {
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new FrontMessage("No se pudo eliminar el domicilio", FrontMessage.INFO))
						.build();
			}
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * Obtiene todos los alumnos en formato DTO (AlumnoDTO)
	 * @return
	 */
	@GET
	@Path("/listAllMin")
	public Response listAlumnosDTO(@HeaderParam("rol") final String rolIn, @HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			if (nuevoToken == null) {
				return Response.ok(servicioAlumno.listAlumnosDTO()).build();
			} else {
				return Response.ok(servicioAlumno.listAlumnosDTO()).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * Devuelve todos los alumnos activos en formato DTO (AlumnoDTO)
	 * @return
	 */
	@GET
	@Path("/listAllMinAct")
	public Response listAlumnosActDTO() {
		try {
			setInstance();
			return Response.ok(servicioAlumno.listAlumnosActivosDTO()).build();
		} catch (Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO - DOCENTE
	 * @param dni
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@GET
	@Path("/getByDniMin/{dni:[0-9][0-9]*}")
	public Response getAlumnoByDniMin(@PathParam("dni") final Long dni,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.DOCENTE)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			AlumnoDTO alumnoDto = servicioAlumno.getAlumnoByDniMin(dni);
			if (alumnoDto.getDniAlumno()!=null) {
				if (nuevoToken == null) {
					return Response.ok(alumnoDto).build();
				} else {
					return Response.ok(alumnoDto).header("auth0", nuevoToken).build();
				}
			} else {
				return Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(new FrontMessage("No se pudo encontrar el Alumno con DNI: " + String.valueOf(dni), FrontMessage.INFO))
						.build();
			}
		} catch(Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * Devuelve todos los alumnos activos en formato DTO (AlumnoDTO)
	 * @return
	 */
	@GET
	@Path("/getByAnio/{idAnio:[0-9][0-9]*}")
	public Response listAlumnosActAnioDTO(@PathParam("idAnio") final Long idAnio,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		String nuevoToken = new String();
		try {
			setInstance();
			try {
				nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			} catch (ValidacionException vEx) {
				return Response.status(Status.FORBIDDEN).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
			}
			if (nuevoToken == null) {
				return Response.ok(servicioAlumno.listAlumnosActivosAnioDTO(idAnio)).build();
			} else {
				return Response.ok(servicioAlumno.listAlumnosActivosAnioDTO(idAnio)).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.serverError().entity(new FrontMessage(vEx.getMensajesError().toString(),FrontMessage.INFO)).build();
		} catch (Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
	}
	
	
}
