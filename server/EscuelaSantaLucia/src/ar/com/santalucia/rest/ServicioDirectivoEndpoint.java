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
import ar.com.santalucia.dominio.modelo.sistema.login.Login;
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

// �ltimo modificador: Ariel Ramirez @ 13-06-2016 20:25

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
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * Devuelve los datos de un directivo espec�fico proporcionando su DNI
	 * @param dni
	 * @return 
	 */
	@GET
	@Path("/dir/getByDni/{id:[0-9][0-9]*}")
	public Response getDirectivoByDni(@PathParam("id") final Long dni,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		Personal directivo = new Personal();
		try{
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token); 
			directivo = servicioDirectivo.getUsuarioByDni(dni);
			if(directivo.getNroDocumento().equals("")) {
				return Response.serverError().entity(new FrontMessage("No se ha encontrado el Directivo",FrontMessage.INFO)).build();
			}
		} catch(Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.",FrontMessage.CRITICAL)).build();
		}
		if (nuevoToken == null) {
			return Response.ok(directivo).build();
		} else {
			return Response.ok(directivo).header("auth0", nuevoToken).build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * @param id
	 *            identificador del usuario a buscar.
	 * @return Response ok (Status 200) e instancia de directivo, incluyendo
	 *         datos de tel�fono, mail, t�tulos y domicilio o null si no existe.
	 */
	@GET
	@Path("/dir/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Personal personal = new Personal();
		String nuevoToken = new String();
		personal = null;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			personal = servicioDirectivo.getUsuario(id);
			if(personal.getNroDocumento().equals("")){
				return Response.serverError().entity(new FrontMessage("No ha sido posible localizar el elemento solicitado.",FrontMessage.INFO)).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.",FrontMessage.CRITICAL)).build();
		}
		if (nuevoToken == null) {
			return Response.ok(personal).build();
		} else {
			return Response.ok(personal).header("auth0", nuevoToken).build();
		}
	}

	/**
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * @param id
	 *            Identificador del usuario del cual se desea recuperar los
	 *            tel�fonos.
	 * @return Response ok (Status 200) y Set de tel�fonos o null si no existe
	 *         el alumno o no hay nada.
	 */
	@GET
	@Path("/tel/{id:[0-9][0-9]*}")
	public Response getTelefonos(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Set<Telefono> telefonos = new HashSet<Telefono>();
		// telefonos = null;
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			telefonos = servicioDirectivo.getTelefonos(id);
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
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}

	/**
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * @param id Identificador del usuario del cu�l se desea recuperar los mails.
	 * @return Response ok (Status 200) y Set de tel�fonos o null si no existe
	 *         el directivo o no hay nada.
	 */
	@GET
	@Path("/mai/{id:[0-9][0-9]*}")
	public Response getMails(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Set<Mail> mails = new HashSet<Mail>();
		String nuevoToken = new String();
		// mails = null;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			mails = servicioDirectivo.getMails(id);
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
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * @param id Identificador del usuario del cu�l se desea recuperar los t�tulos.
	 * @return Response ok (Status 200) y Set de t�tulos o null si no existe
	 *         el directivo o no hay nada.
	 */
	@GET
	@Path("/tit/{id:[0-9][0-9]*}")
	public Response getTitulos(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Set<Titulo> titulos = new HashSet<Titulo>();
		String nuevoToken = new String();
		// titulos = null;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			titulos = servicioDirectivo.getTitulos(id);
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
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * Utilice este m�todo para el listado de directivos completo.
	 * @return Response ok (Status 200) y listado de directivos o 
	 *  null si no hay nada. <br>
	 *  (! PRECAUCI�N) �El m�todo devuelve el listado de todos los directivos de la base de datos!
	 *
	 */
	@GET
	@Path("/listAll")
	public Response listAll(@HeaderParam("rol") final String rolIn, @HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		List<Personal> personal = new ArrayList<Personal>();
		String nuevoToken = new String();
		// personal = null;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			personal = servicioDirectivo.getUsuarios(new Personal());
			if (personal.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(personal).build();
			} else {
				return Response.ok(personal).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * Utilice este m�todo para:<br>
	 * 1) Agregar un nuevo directivo con o sin datos adicionales (tel�fono, mail, t�tulo y domicilio) de una sola vez. <br>
	 * 2) Actualizar datos de directivo que no sean adicionales (tel�fono, mail, t�tulo y domicilio).<br>
	 * 3) Agregar uno o varios datos adicionales (tel�fono, t�tulo y mail).<br>
	 * (! - IMPORTANTE) Para actualizar o eliminar datos adicionales (tel�fono, mail, t�tulo y domicilio) use los m�todos UPDATE y DELETE respectivos. 
	 * @param personal
	 * @return Response ok (Status 200) con el id de usuario si el resultado es exitoso o la excepci�n generada. 
	 */
	@PUT
	@Path("/dir/")
	public Response update(final Personal personal,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR)) {
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
			servicioDirectivo.addUsuario(personal);
			return Response.ok(personal.getIdUsuario()).build();
		} catch (SugerenciaPersonalException spEx) {
			return Response.status(Status.CONFLICT).entity(spEx.getPersonalSugerido()).build();
		} catch (ValidacionException vEx) {
			return Response.status(Status.CONFLICT).entity(vEx.getMensajesError()).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}

	/**
	 * Utiice este m�todo para actualizar los datos de un tel�fono existente.
	 * @param telefono incluyendo su id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepci�n generada.
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
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Utilice este m�todo para actualizar los datos de un mail existente.
	 * @param mail Response ok (Status 200) con true si el resultado es exitoso o la excepci�n generada.
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
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Utilice este m�todo para actualizar los datos de un domicilio existente.
	 * @param domicilio
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepci�n generada.
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
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Utilice este m�todo para actualizar los datos de un t�tulo existente.
	 * @param titulo
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepci�n generada.
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
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * Utilice este m�todo para eliminar un directivo. Adem�s de eliminar el directivo, tambi�n se eliminan los datos adicionales (tel�fono, mail, titulo y domicilio).
	 * @param id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepci�n generada.<br>
	 * (! PRECAUCI�N) Se eliminan todos los datos adicionales asociados (telefono, mail, titulos y domicilio) al eliminar el usuario.
	 */
	@DELETE
	@Path("/dir/{id:[0-9][0-9]*}")
	public Response deleteDirectivoById(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Boolean exito = false;
		Personal personal = new Personal();
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			personal = servicioDirectivo.getUsuario(id);
			exito = servicioDirectivo.removeUsuario(servicioDirectivo.getUsuario(id));
			if (personal == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("Elemento a eliminar no encontrado", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(exito).build();
			} else {
				return Response.ok(exito).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * Elimina un directivo de manera l�gica proporcionando el DNI
	 * @param dni
	 * @return
	 */
	@DELETE
	@Path("/dir/deleteByDni/{dni:[0-9][0-9]*}")
	public Response deleteDirectivoByDni(@PathParam("dni") final Long dni,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
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
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.",FrontMessage.CRITICAL)).build();
		}
	}

	/**
	 * Utilice este m�todo para eliminar un tel�fono existente.
	 * @param id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepci�n generada.
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
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Utilice este m�todo para eliminar un domicilio existente.
	 * @param id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepci�n generada.
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
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Utilice este m�todo para eliminar un titulo existente.
	 * @param id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepci�n generada.
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
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * Devuelve los datos personales del Directivo logueado solamente enviando rol y token.
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@GET
	@Path("/DatosPersonales")
	public Response obtenerDatosPersonales(@HeaderParam("rol") final String rolIn, @HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		Long usuarioDni;
		Personal directivo = new Personal();
		try {
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			setInstance();
			if(nuevoToken == null) {
				usuarioDni = Long.valueOf(ServicioLogin.obtenerIdentificacionUsuario(rolIn, token));
				directivo = servicioDirectivo.getUsuarioByDni(usuarioDni);
			} else {
				usuarioDni = Long.valueOf(ServicioLogin.obtenerIdentificacionUsuario(rolIn, nuevoToken));
				directivo = servicioDirectivo.getUsuarioByDni(usuarioDni);
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.",FrontMessage.CRITICAL)).build();
		}
		if(nuevoToken == null){
			return Response.ok(directivo).build();
		}else{
			return Response.ok(directivo).header("auth0", nuevoToken).build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO - ADMINISTRADOR
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@GET
	@Path("/listAllMin")
	public Response listDirectivosDTO(@HeaderParam("rol") final String rolIn, @HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ADMINISTRADOR)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			ArrayList<DirectivoDTO> listaDirectivosDTO = new ArrayList<DirectivoDTO>();
			listaDirectivosDTO.addAll(servicioDirectivo.listDirectivosDTO());
			if (listaDirectivosDTO.size() == 0) {
				return Response.status(Status.NO_CONTENT)
					.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
					.build();
			}
			if (nuevoToken == null) {
				return Response.ok(listaDirectivosDTO).build();
			} else {
				return Response.ok(listaDirectivosDTO).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
						FrontMessage.CRITICAL))
				.build();
		}
	}
	
}
