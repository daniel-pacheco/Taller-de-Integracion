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

import ar.com.santalucia.dominio.dto.DocenteMateriasDTO;
import ar.com.santalucia.dominio.dto.MateriaDTO;
import ar.com.santalucia.dominio.modelo.sistema.login.Login;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;
import ar.com.santalucia.excepciones.LoginError;
import ar.com.santalucia.excepciones.SugerenciaPersonalException;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.servicio.ServicioDocente;
import ar.com.santalucia.servicio.ServicioLogin;

/**
 * @author Ariel Ramirez
 *
 * @version 1.0
 */

// Último modificador: Ariel Ramirez @ 13-06-2016 20:25

@Path("/sDocente")
@Produces("application/json")
@Consumes("application/json")
public class ServicioDocenteEndpoint {

	private ServicioDocente servicioDocente = null;
	
	/**
	 * Instancia un objeto ServicioAlumno si no existe
	 * @throws Exception
	 */
	private void setInstance() throws Exception {
		if (servicioDocente == null) {
			try {
				servicioDocente = new ServicioDocente();
			} catch (Exception ex) {
				throw ex;
			}
		}
	}
	
	/**
	* @param serviciodocente
	* @return
	*/
	@POST
	public Response create(final ServicioDocente serviciodocente) {
		//TODO: process the given serviciodocente 
		//you may want to use the following return statement, assuming that ServicioDocente#getId() or a similar method 
		//would provide the identifier to retrieve the created ServicioDocente resource:
		//return Response.created(UriBuilder.fromResource(ServicioDocenteEndpoint.class).path(String.valueOf(serviciodocente.getId())).build()).build();
		return Response.created(null).build();
	}
	
	
	
	/**
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * @param id
	 *            identificador del usuario a buscar.
	 * @return Response ok (Status 200) e instancia de directivo, incluyendo
	 *         datos de teléfono, mail, títulos y domicilio o null si no existe.
	 */
	@GET
	@Path("/doc/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Personal docente = new Personal();
		docente = null;
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			docente = servicioDocente.getUsuario(id);
			if (docente.getNroDocumento().equals("")){
				return Response.serverError().entity(new FrontMessage("No ha sido posible localizar el elemento solicitado.",FrontMessage.INFO)).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
		if (nuevoToken == null) {
			return Response.ok(docente).build();
		} else {
			return Response.ok(docente).header("auth0", nuevoToken).build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * Devuelve los datos de un directivo específico proporcionando su DNI
	 * @param dni
	 * @return 
	 */
	@GET
	@Path("/doc/getByDni/{id:[0-9][0-9]*}")
	public Response getDocenteByDni(@PathParam("id") final Long dni,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Personal docente = new Personal();
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			docente = servicioDocente.getUsuarioByDni(dni);
			if(docente.getIdUsuario().equals("")) {
				return Response.serverError().entity(new FrontMessage("No se ha encontrado el Docente",FrontMessage.INFO)).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch(Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
		if (nuevoToken == null) {
			return Response.ok(docente).build();
		} else {
			return Response.ok(docente).header("auth0", nuevoToken).build();
		}
	}
	
	/**
	 * Rol de acceso: DOCENTE - DIRECTIVO - ADMINISTRADOR
	 * @param id
	 *            Identificador del usuario del cual se desea recuperar los
	 *            teléfonos.
	 * @return Response ok (Status 200) y Set de teléfonos o null si no existe
	 *         el alumno o no hay nada.
	 */
	@GET
	@Path("/tel/{id:[0-9][0-9]*}")
	public Response getTelefonos(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.DOCENTE) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Set<Telefono> telefonos = new HashSet<Telefono>();
		String nuevoToken = new String();
		// telefonos = null;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			telefonos = servicioDocente.getTelefonos(id);
			if (telefonos.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(telefonos).build();
			} else {
				return Response.ok(telefonos).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}

	/**
	 * Rol de acceso: DOCENTE - DIRECTIVO - ADMINISTRADOR
	 * @param id Identificador del usuario del cuál se desea recuperar los mails.
	 * @return Response ok (Status 200) y Set de teléfonos o null si no existe
	 *         el docente o no hay nada.
	 */
	@GET
	@Path("/mai/{id:[0-9][0-9]*}")
	public Response getMails(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.DOCENTE) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Set<Mail> mails = new HashSet<Mail>();
		String nuevoToken = new String();
		// mails = null;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			mails = servicioDocente.getMails(id);
			if (mails.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(mails).build();
			} else {
				return Response.ok(mails).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DOCENTE - DIRECTIVO - ADMINISTRADOR
	 * @param id Identificador del usuario del cuál se desea recuperar los títulos.
	 * @return Response ok (Status 200) y Set de títulos o null si no existe
	 *         el docente o no hay nada.
	 */
	@GET
	@Path("/tit/{id:[0-9][0-9]*}")
	public Response getTitulos(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.DOCENTE) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Set<Titulo> titulos = new HashSet<Titulo>();
		String nuevoToken = new String();
		titulos = null;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			titulos = servicioDocente.getTitulos(id);
			if (titulos.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(titulos).build();
			} else {
				return Response.ok(titulos).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * Utilice este método para el listado de directivos completo.
	 * @return Response ok (Status 200) y listado de directivos o 
	 *  null si no hay nada. <br>
	 *  (! PRECAUCIÓN) ¡El método devuelve el listado de todos los directivos de la base de datos!
	 */
	@GET
	@Path("/listAll")
	public Response listAll(@HeaderParam("rol") final String rolIn, @HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		List<Personal> docente = new ArrayList<Personal>();
		String nuevoToken = new String();
		docente = null;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			docente = servicioDocente.getUsuarios(new Personal());
			if (docente.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(docente).build();
			} else {
				return Response.ok(docente).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}

	/**
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * Utilice este método para:<br>
	 * 1) Agregar un nuevo docente con o sin datos adicionales (teléfono, mail, título y domicilio) de una sola vez. <br>
	 * 2) Actualizar datos de docente que no sean adicionales (teléfono, mail, título y domicilio).<br>
	 * 3) Agregar uno o varios datos adicionales (teléfono, título y mail).<br>
	 * (! - IMPORTANTE) Para actualizar o eliminar datos adicionales (teléfono, mail, título y domicilio) use los métodos UPDATE y DELETE respectivos. 
	 * @param docente
	 * @return Response ok (Status 200) con el id de usuario si el resultado es exitoso o la excepción generada. 
	 */
	@PUT
	@Path("/doc/")
	public Response update(final Personal docente,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR) && !rolIn.equals(Login.DOCENTE)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		try {
			setInstance();
			try {
				nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			} catch (ValidacionException vEx) {
				return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
			}
			servicioDocente.addUsuario(docente);
			if (nuevoToken == null) {
				return Response.ok(docente.getIdUsuario()).build();
			} else {
				return Response.ok(docente.getIdUsuario()).header("auth0", nuevoToken).build();
			}
		} catch(SugerenciaPersonalException ex) {
			return Response.status(Status.CONFLICT).entity(ex.getPersonalSugerido()).build();
		} catch(ValidacionException ex) {
			return Response.status(Status.CONFLICT).entity(ex.getMensajesError()).build();
		} catch (Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
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
			exito = servicioDocente.modifyTelefono(telefono);
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
			exito = servicioDocente.modifyMail(mail);
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
			exito = servicioDocente.modifyDomicilio(domicilio);
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
			exito = servicioDocente.modifyTitulo(titulo);
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
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * Utilice este método para eliminar un docente. Además de eliminar el docente, también se eliminan los datos adicionales (teléfono, mail, titulo y domicilio).
	 * @param id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepción generada.<br>
	 * (! PRECAUCIÓN) Se eliminan todos los datos adicionales asociados (telefono, mail, titulos y domicilio) al eliminar el usuario.
	 */
	@DELETE
	@Path("/doc/{id:[0-9][0-9]*}")
	public Response deleteDocenteById(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		Boolean exito = false;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			exito = servicioDocente.removeUsuario(servicioDocente.getUsuario(id));
			if(exito == true){
				return Response.ok(exito).build();
			}else{
				return Response.serverError().entity(new FrontMessage ("No se ha podido eliminar el docente", FrontMessage.INFO)).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * Elimina un docente de manera lógica proporcionando el DNI
	 * @param dni
	 * @return
	 */
	@DELETE
	@Path("/doc/deleteByDni/{dni:[0-9][0-9]*}")
	public Response deleteDocenteByDni(@PathParam("dni") final Long dni,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		Boolean exito = false;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			exito = servicioDocente.removeUsuario(servicioDocente.getUsuarioByDni(dni));
			if (exito == true) {
				if (nuevoToken == null) {
					return Response.ok(exito).build();
				} else {
					return Response.ok(exito).header("auth0", nuevoToken).build();
				}
			}
			return Response.serverError().entity(new FrontMessage("No se pudo eliminar el docente.",FrontMessage.INFO)).build();
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
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
			exito = servicioDocente.removeTelefono(id);
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
			exito = servicioDocente.removeDomicilio(id);
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
			exito = servicioDocente.removeTitulo(id);
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
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * @return
	 */
	@GET
	@Path("/listAllMin")
	public Response listDocentesMateriasDTO(@HeaderParam("rol") final String rolIn, @HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR) && !rolIn.equals(Login.DOCENTE)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		ArrayList<DocenteMateriasDTO> docentesDTO = new ArrayList<DocenteMateriasDTO>();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			docentesDTO = servicioDocente.listDocentesMateriasDTO();
			if (docentesDTO.size() == 0) {
				return Response.status(Status.NO_CONTENT)
					.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
					.build();
			} else {
				if (nuevoToken == null) {
					return Response.ok(docentesDTO).build();
				} else {
					return Response.ok(docentesDTO).header("auth0", nuevoToken).build();
				}
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
	}
	
	/**
	 * Rol de acceso: DOCENTE
	 * Devuelve los datos personales del Docente logueado solamente enviando rol y token.
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@GET
	@Path("/DatosPersonales")
	public Response obtenerDatosPersonales(@HeaderParam("rol") String rolIn, @HeaderParam("auth0") String token) {
		if (!rolIn.equals(Login.DOCENTE)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		Long usuarioDni;
		Personal docente = new Personal();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			if (nuevoToken == null) {
				usuarioDni = Long.valueOf(ServicioLogin.obtenerIdentificacionUsuario(rolIn, token));
				docente = servicioDocente.getUsuarioByDni(usuarioDni);
			} else {
				usuarioDni = Long.valueOf(ServicioLogin.obtenerIdentificacionUsuario(rolIn, nuevoToken));
				docente = servicioDocente.getUsuarioByDni(usuarioDni);
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
		if(nuevoToken == null) {
			return Response.ok(docente).build();
		} else {
			return Response.ok(docente).header("auth0", nuevoToken).build();
		}
	}
	
	@GET
	@Path("/listAllMinByDoc/{idU:[0-9][0-9]*}")
	public Response obtenerMateriasDictadas(@PathParam("idU") final Long idUsuario,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token){
		if (!rolIn.equals(Login.DOCENTE) && !rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		List<MateriaDTO> listado = new ArrayList<MateriaDTO>();
		try{
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			listado = servicioDocente.ObtenerMateriasDictadas(idUsuario);
			if(nuevoToken == null) {
				return Response.ok(listado).build();
			} else {
				return Response.ok(listado).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.",FrontMessage.CRITICAL)).build();
		}
		
	}
	
}


