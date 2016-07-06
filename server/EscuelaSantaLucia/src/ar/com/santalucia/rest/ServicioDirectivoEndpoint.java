package ar.com.santalucia.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import ar.com.santalucia.dominio.dto.DirectivoDTO;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;
import ar.com.santalucia.excepciones.LoginError;
import ar.com.santalucia.excepciones.SugerenciaPersonalException;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.servicio.ServicioDirectivo;
import ar.com.santalucia.servicio.ServicioLogin;

/**
 * @author Ariel Ramirez
 * 
 * @version 1.1
 *
 */

// Último modificador: Ariel Ramirez @ 13-06-2016 20:25

@Path("/sDirectivo")
@Produces("application/json")
@Consumes("application/json")
public class ServicioDirectivoEndpoint {

	private ServicioDirectivo servicioDirectivo = null;

	/**
	 * Instancia un objeto ServicioAlumno si no existe
	 * @throws Exception
	 */
	private void setInstance() throws Exception {
		if (servicioDirectivo == null) {
			try {
				servicioDirectivo = new ServicioDirectivo();
			} catch (Exception ex) {
				throw ex;
			}
		}
	}

	@POST
	public Response create(final ServicioDirectivo serviciodirectivo) {
		// TODO: process the given serviciodirectivo
		// you may want to use the following return statement, assuming that
		// ServicioDirectivo#getId() or a similar method
		// would provide the identifier to retrieve the created
		// ServicioDirectivo resource:
		// return
		// Response.created(UriBuilder.fromResource(ServicioDirectivoEndpoint.class).path(String.valueOf(serviciodirectivo.getId())).build()).build();
		return Response.created(null).build();
	}

	/**
	 * Devuelve los datos de un directivo específico proporcionando su DNI
	 * @param dni
	 * @return 
	 */
	@GET
	@Path("/dir/getByDni/{id:[0-9][0-9]*}")
	public Response getDirectivoByDni(@PathParam("id") final Long dni){
		Personal directivo = new Personal();
		try{
			setInstance();
			directivo = servicioDirectivo.getUsuarioByDni(dni);
			if(directivo.getNroDocumento().equals("")){
				return Response.serverError().entity(new FrontMessage("No se ha encontrado el Directivo",FrontMessage.INFO)).build();
			}
		}catch(Exception ex){
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
		return Response.ok(directivo).build();
	}
	
	
	/**
	 * @param id
	 *            identificador del usuario a buscar.
	 * @return Response ok (Status 200) e instancia de directivo, incluyendo
	 *         datos de teléfono, mail, títulos y domicilio o null si no existe.
	 */
	@GET
	@Path("/dir/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		Personal personal = new Personal();
		personal = null;
		try {
			setInstance();
			personal = servicioDirectivo.getUsuario(id);
			if(personal.getNroDocumento().equals("")){
				return Response.serverError().entity(new FrontMessage("No ha sido posible localizar el elemento solicitado.",FrontMessage.INFO)).build();
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
		return Response.ok(personal).build();
	}

	/**
	 * 
	 * @param id
	 *            Identificador del usuario del cual se desea recuperar los
	 *            teléfonos.
	 * @return Response ok (Status 200) y Set de teléfonos o null si no existe
	 *         el alumno o no hay nada.
	 */
	@GET
	@Path("/tel/{id:[0-9][0-9]*}")
	public Response getTelefonos(@PathParam("id") final Long id) {
		Set<Telefono> telefonos = new HashSet<Telefono>();
		// telefonos = null;
		try {
			setInstance();
			telefonos = servicioDirectivo.getTelefonos(id);
			if (telefonos.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			return Response.ok(telefonos).build();
		} catch (Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}

	/**
	 * 
	 * @param id Identificador del usuario del cuál se desea recuperar los mails.
	 * @return Response ok (Status 200) y Set de teléfonos o null si no existe
	 *         el directivo o no hay nada.
	 */
	
	@GET
	@Path("/mai/{id:[0-9][0-9]*}")
	public Response getMails(@PathParam("id") final Long id){
		Set<Mail> mails = new HashSet<Mail>();
		// mails = null;
		try {
			setInstance();
			mails = servicioDirectivo.getMails(id);
			if (mails.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			return Response.ok(mails).build();
		} catch (Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * 
	 * @param id Identificador del usuario del cuál se desea recuperar los títulos.
	 * @return Response ok (Status 200) y Set de títulos o null si no existe
	 *         el directivo o no hay nada.
	 */
	@GET
	@Path("/tit/{id:[0-9][0-9]*}")
	public Response getTitulos(@PathParam("id") final Long id){
		Set<Titulo> titulos = new HashSet<Titulo>();
		// titulos = null;
		try{
			setInstance();
			titulos = servicioDirectivo.getTitulos(id);
			if (titulos.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			return Response.ok(titulos).build();
		}catch (Exception ex){
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Utilice este método para el listado de directivos completo.
	 * @return Response ok (Status 200) y listado de directivos o 
	 *  null si no hay nada. <br>
	 *  (! PRECAUCIÓN) ¡El método devuelve el listado de todos los directivos de la base de datos!
	 *
	 */
	@GET
	@Path("/listAll")
	public Response listAll(){
		List<Personal> personal = new ArrayList<Personal>();
		// personal = null;
		try {
			setInstance();
			personal = servicioDirectivo.getUsuarios(new Personal());
			if (personal.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			return Response.ok(personal).build();
		} catch (Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Utilice este método para:<br>
	 * 1) Agregar un nuevo directivo con o sin datos adicionales (teléfono, mail, título y domicilio) de una sola vez. <br>
	 * 2) Actualizar datos de directivo que no sean adicionales (teléfono, mail, título y domicilio).<br>
	 * 3) Agregar uno o varios datos adicionales (teléfono, título y mail).<br>
	 * (! - IMPORTANTE) Para actualizar o eliminar datos adicionales (teléfono, mail, título y domicilio) use los métodos UPDATE y DELETE respectivos. 
	 * @param personal
	 * @return Response ok (Status 200) con el id de usuario si el resultado es exitoso o la excepción generada. 
	 */
	@PUT
	@Path("/dir/")
	public Response update(final Personal personal) { 
		try {
			setInstance();
			servicioDirectivo.addUsuario(personal);
			return Response.ok(personal.getIdUsuario()).build();
		} catch (SugerenciaPersonalException spEx) {
			return Response.status(Status.CONFLICT).entity(spEx.getPersonalSugerido()).build();
		} catch (ValidacionException vEx) {
			return Response.status(Status.CONFLICT).entity(vEx.getMensajesError()).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}

	/**
	 * Utiice este método para actualizar los datos de un teléfono existente.
	 * @param telefono incluyendo su id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepción generada.
	 */
	@PUT
	@Path("/tel/")
	public Response updateTelefono(final Telefono telefono) {
		Boolean exito = false;
		try {
			setInstance();
			exito = servicioDirectivo.modifyTelefono(telefono);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
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
			exito = servicioDirectivo.modifyMail(mail);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
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
			exito = servicioDirectivo.modifyDomicilio(domicilio);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Utilice este método para actualizar los datos de un título existente.
	 * @param titulo
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepción generada.
	 */
	@PUT
	@Path("/tit/")
	public Response updateTitulo(final Titulo titulo) {
		boolean exito = false;
		try {
			setInstance();
			exito = servicioDirectivo.modifyTitulo(titulo);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Utilice este método para eliminar un directivo. Además de eliminar el directivo, también se eliminan los datos adicionales (teléfono, mail, titulo y domicilio).
	 * @param id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepción generada.<br>
	 * (! PRECAUCIÓN) Se eliminan todos los datos adicionales asociados (telefono, mail, titulos y domicilio) al eliminar el usuario.
	 */
	@DELETE
	@Path("/dir/{id:[0-9][0-9]*}")
	public Response deleteDirectivoById(@PathParam("id") final Long id) {
		Boolean exito = false;
		Personal personal = new Personal();
		try {
			setInstance();
			personal = servicioDirectivo.getUsuario(id);
			exito = servicioDirectivo.removeUsuario(servicioDirectivo.getUsuario(id));
			if (personal == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("Elemento a eliminar no encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(exito).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Elimina un directivo de manera lógica proporcionando el DNI
	 * @param dni
	 * @return
	 */
	@DELETE
	@Path("/doc/deleteByDni/{dni:[0-9][0-9]*}")
	public Response deleteDirectivoByDni(@PathParam("dni") final Long dni) {
		Boolean exito = false;
		try {
			setInstance();
			exito = servicioDirectivo.removeUsuario(servicioDirectivo.getUsuarioByDni(dni));
			if(exito = true){
				return Response.ok(exito).build();
			}else{
				return Response.serverError().entity(new FrontMessage("No se pudo eliminar el directivo.",FrontMessage.INFO)).build();
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
	}

	/**
	 * Utilice este método para eliminar un teléfono existente.
	 * @param id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepción generada.
	 */
	@DELETE
	@Path("/tel/{id:[0-9][0-9]*}")
	public Response deleteTelefonoById(@PathParam("id") final Long id){
		Boolean exito = false;
		try {
			setInstance();
			exito = servicioDirectivo.removeTelefono(id);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Utilice este método para eliminar un domicilio existente.
	 * @param id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepción generada.
	 */
	@DELETE
	@Path("/dom/{id:[0-9][0-9]*}")
	public Response deleteDomicilioById(@PathParam("id") final Long id){
		Boolean exito = false;
		try{
			setInstance();
			exito = servicioDirectivo.removeDomicilio(id);
			return Response.ok(exito).build();
		}catch (Exception ex){
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Utilice este método para eliminar un titulo existente.
	 * @param id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepción generada.
	 */
	@DELETE
	@Path("/tit/{id:[0-9][0-9]*}")
	public Response deleteTituloById(@PathParam("id") final Long id){
		Boolean exito = false;
		try{
			setInstance();
			exito = servicioDirectivo.removeTitulo(id);
			return Response.ok(exito).build();
		}catch (Exception ex){
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Devuelve los datos personales del Directivo logueado solamente enviando rol y token.
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@GET
	@Path("/DatosPersonales")
	public Response obtenerDatosPersonales(@HeaderParam("rol") String rolIn, @HeaderParam("auth0") String token){
		String nuevoToken = new String();
		Long usuarioDni;
		Personal directivo = new Personal();
		try {
			nuevoToken = ServicioLogin.comprobar(token, rolIn);
			setInstance();
			if(nuevoToken == null){
				usuarioDni = Long.valueOf(ServicioLogin.obtenerIdentificacionUsuario(rolIn, token));
				directivo = servicioDirectivo.getUsuarioByDni(usuarioDni);
			}else{
				usuarioDni = Long.valueOf(ServicioLogin.obtenerIdentificacionUsuario(rolIn, nuevoToken));
				directivo = servicioDirectivo.getUsuarioByDni(usuarioDni);
			}
			}catch (LoginError ex){
				switch (ex.getDetalles()) {
				case LoginError.ROLERROR: 
					return Response.status(Status.UNAUTHORIZED).build();
				case LoginError.FIRMAERROR:
					return Response.status(Status.FORBIDDEN).build();
				case LoginError.EXPIRADO:
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new FrontMessage("Sus credenciales han expirado. Vuelva a iniciar sesión.",FrontMessage.INFO)).build(); 
				default:
					break;
				}
			} catch (Exception ex) {
				return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
			}
		if(nuevoToken == null){
			return Response.ok(directivo).build();
		}else{
			return Response.ok(directivo).header("auth0", nuevoToken).build();
		}
	}
	
	
	@GET
	@Path("/dir/listAllMin")
	public Response listDirectivosDTO() {
		try {
			setInstance();
			ArrayList<DirectivoDTO> listaDirectivosDTO = new ArrayList<DirectivoDTO>();
			listaDirectivosDTO.addAll(servicioDirectivo.listDirectivosDTO());
			if (listaDirectivosDTO.size() == 0) {
				return Response.status(Status.NO_CONTENT)
					.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
					.build();
			}
			return Response.ok(listaDirectivosDTO).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
						FrontMessage.CRITICAL))
				.build();
		}
	}
	
}
