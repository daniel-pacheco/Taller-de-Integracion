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

// �ltimo modificador: Ariel Ramirez @ 13-06-2016 20:25

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
	 * @param id
	 *            identificador del usuario a buscar.
	 * @return Response ok (Status 200) e instancia de directivo, incluyendo
	 *         datos de tel�fono, mail, t�tulos y domicilio o null si no existe.
	 */
	@GET
	@Path("/doc/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		Personal docente = new Personal();
		docente = null;
		try {
			setInstance();
			docente = servicioDocente.getUsuario(id);
			if (docente.getNroDocumento().equals("")){
				return Response.serverError().entity(new FrontMessage("No ha sido posible localizar el elemento solicitado.",FrontMessage.INFO)).build();
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.",FrontMessage.CRITICAL)).build();
		}
		return Response.ok(docente).build();
	}
	
	/**
	 * Devuelve los datos de un directivo espec�fico proporcionando su DNI
	 * @param dni
	 * @return 
	 */
	@GET
	@Path("/doc/getByDni/{id:[0-9][0-9]*}")
	public Response getDocenteByDni(@PathParam("id") final Long dni){
		Personal docente = new Personal();
		try{
			setInstance();
			docente = servicioDocente.getUsuarioByDni(dni);
			if(docente.getIdUsuario().equals("")){
				return Response.serverError().entity(new FrontMessage("No se ha encontrado el Docente",FrontMessage.INFO)).build();
			}
		}catch(Exception ex){
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.",FrontMessage.CRITICAL)).build();
		}
		return Response.ok(docente).build();
	}
	
	/**
	 * 
	 * @param id
	 *            Identificador del usuario del cual se desea recuperar los
	 *            tel�fonos.
	 * @return Response ok (Status 200) y Set de tel�fonos o null si no existe
	 *         el alumno o no hay nada.
	 */
	@GET
	@Path("/tel/{id:[0-9][0-9]*}")
	public Response getTelefonos(@PathParam("id") final Long id) {
		Set<Telefono> telefonos = new HashSet<Telefono>();
		telefonos = null;
		try {
			setInstance();
			telefonos = servicioDocente.getTelefonos(id);
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.ok(ex).build();
		}
		return Response.ok(telefonos).build();
	}

	/**
	 * 
	 * @param id Identificador del usuario del cu�l se desea recuperar los mails.
	 * @return Response ok (Status 200) y Set de tel�fonos o null si no existe
	 *         el docente o no hay nada.
	 */
	
	@GET
	@Path("/mai/{id:[0-9][0-9]*}")
	public Response getMails(@PathParam("id") final Long id){
		Set<Mail> mails = new HashSet<Mail>();
		mails = null;
		try {
			setInstance();
			mails = servicioDocente.getMails(id);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			return Response.ok(ex).build();
		}
		return Response.ok(mails).build();
	}
	
	/**
	 * 
	 * @param id Identificador del usuario del cu�l se desea recuperar los t�tulos.
	 * @return Response ok (Status 200) y Set de t�tulos o null si no existe
	 *         el docente o no hay nada.
	 */
	@GET
	@Path("/tit/{id:[0-9][0-9]*}")
	public Response getTitulos(@PathParam("id") final Long id){
		Set<Titulo> titulos = new HashSet<Titulo>();
		titulos = null;
		try{
			setInstance();
			titulos = servicioDocente.getTitulos(id);
		}catch (Exception ex){
			return Response.ok(ex).build();
		}
		return Response.ok(titulos).build();
	}
	
	/**
	 * Utilice este m�todo para el listado de directivos completo.
	 * @return Response ok (Status 200) y listado de directivos o 
	 *  null si no hay nada. <br>
	 *  (! PRECAUCI�N) �El m�todo devuelve el listado de todos los directivos de la base de datos!
	 *
	 */
	@GET
	@Path("/listAll")
	public Response listAll(){
		List<Personal> docente = new ArrayList<Personal>();
		docente = null;
		try {
			setInstance();
			docente = servicioDocente.getUsuarios(new Personal());
		} catch (Exception ex) {
			if (docente.size() == 0) {
				return Response.status(Status.NO_CONTENT).entity(new FrontMessage("No hay elementos que mostrar.",FrontMessage.INFO)).build();
			}
		}
		return Response.ok(docente).build();
	}

	/**
	 * Utilice este m�todo para:<br>
	 * 1) Agregar un nuevo docente con o sin datos adicionales (tel�fono, mail, t�tulo y domicilio) de una sola vez. <br>
	 * 2) Actualizar datos de docente que no sean adicionales (tel�fono, mail, t�tulo y domicilio).<br>
	 * 3) Agregar uno o varios datos adicionales (tel�fono, t�tulo y mail).<br>
	 * (! - IMPORTANTE) Para actualizar o eliminar datos adicionales (tel�fono, mail, t�tulo y domicilio) use los m�todos UPDATE y DELETE respectivos. 
	 * @param docente
	 * @return Response ok (Status 200) con el id de usuario si el resultado es exitoso o la excepci�n generada. 
	 */
	@PUT
	@Path("/doc/")
	public Response update(final Personal docente) { 
		try {
			setInstance();
			servicioDocente.addUsuario(docente);
			return Response.ok(docente.getIdUsuario()).build();
		} catch(SugerenciaPersonalException ex){
			return Response.status(Status.CONFLICT).entity(ex.getPersonalSugerido()).build();
		}catch(ValidacionException ex){
			return Response.status(Status.CONFLICT).entity(ex.getMensajesError()).build();
		}catch (Exception ex) {
			// ex.printStackTrace();
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.",FrontMessage.CRITICAL)).build();
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
			exito = servicioDocente.modifyTelefono(telefono);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.ok(ex).build();
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
			exito = servicioDocente.modifyMail(mail);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.ok(ex).build();
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
			exito = servicioDocente.modifyDomicilio(domicilio);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
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
			exito = servicioDocente.modifyTitulo(titulo);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	
	/**
	 * Utilice este m�todo para eliminar un docente. Adem�s de eliminar el docente, tambi�n se eliminan los datos adicionales (tel�fono, mail, titulo y domicilio).
	 * @param id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepci�n generada.<br>
	 * (! PRECAUCI�N) Se eliminan todos los datos adicionales asociados (telefono, mail, titulos y domicilio) al eliminar el usuario.
	 */
	@DELETE
	@Path("/doc/{id:[0-9][0-9]*}")
	public Response deleteDocenteById(@PathParam("id") final Long id) {
		Boolean exito = false;
		try {
			setInstance();
			exito = servicioDocente.removeUsuario(servicioDocente.getUsuario(id));
			if(exito == true){
				return Response.ok(exito).build();
			}else{
				return Response.serverError().entity(new FrontMessage ("No se ha podido eliminar el docente", FrontMessage.INFO)).build();
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.",FrontMessage.CRITICAL)).build();
		}
	}
	
	/**
	 * Elimina un docente de manera l�gica proporcionando el DNI
	 * @param dni
	 * @return
	 */
	@DELETE
	@Path("/doc/deleteByDni/{dni:[0-9][0-9]*}")
	public Response deleteDocenteByDni(@PathParam("dni") final Long dni) {
		Boolean exito = false;
		try {
			setInstance();
			exito = servicioDocente.removeUsuario(servicioDocente.getUsuarioByDni(dni));
			if (exito==true){
				return Response.ok(exito).build();
			}
				return Response.serverError().entity(new FrontMessage("No se pudo eliminar el docente.",FrontMessage.INFO)).build();
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
			exito = servicioDocente.removeTelefono(id);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			//e.printStackTrace();
			return Response.ok(ex).build();
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
			exito = servicioDocente.removeDomicilio(id);
			return Response.ok(exito).build();
		}catch (Exception ex){
			//e.printStackTrace();
			return Response.ok(ex).build();
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
			exito = servicioDocente.removeTitulo(id);
			return Response.ok(exito).build();
		}catch (Exception ex){
			//e.printStackTrace();
			return Response.ok(ex).build();
		}
	}
	
	/*
	 * Endpoint light
	 */
	@GET
	@Path("/listAllMin")
	public Response listDocentesMateriasDTO() {
		ArrayList<DocenteMateriasDTO> docentesDTO = new ArrayList<DocenteMateriasDTO>();
		try {
			setInstance();
			docentesDTO = servicioDocente.listDocentesMateriasDTO();
			if (docentesDTO == null) {
				return Response.status(Status.NOT_FOUND).build();
			} else {
				return Response.ok(docentesDTO).build();
			}
		} catch (Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.",FrontMessage.CRITICAL)).build();
		}
	}
	
	/**
	 * Devuelve los datos personales del Docente logueado solamente enviando rol y token.
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@GET
	@Path("/DatosPersonales")
	public Response obtenerDatosPersonales(@HeaderParam("rol") String rolIn, @HeaderParam("auth0") String token){
		String nuevoToken = new String();
		Long usuarioDni;
		Personal docente = new Personal();
		try {
			nuevoToken = ServicioLogin.comprobar(token, rolIn);
			setInstance();
			if(nuevoToken == null){
				usuarioDni = Long.valueOf(ServicioLogin.obtenerIdentificacionUsuario(rolIn, token));
				docente = servicioDocente.getUsuarioByDni(usuarioDni);
			}else{
				usuarioDni = Long.valueOf(ServicioLogin.obtenerIdentificacionUsuario(rolIn, nuevoToken));
				docente = servicioDocente.getUsuarioByDni(usuarioDni);
			}
			}catch (LoginError ex){
				switch (ex.getDetalles()) {
				case LoginError.ROLERROR: 
					return Response.status(Status.UNAUTHORIZED).build();
				case LoginError.FIRMAERROR:
					return Response.status(Status.FORBIDDEN).build();
				case LoginError.EXPIRADO:
					return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new FrontMessage("Sus credenciales han expirado. Vuelva a iniciar sesi�n.",FrontMessage.INFO)).build(); 
				default:
					break;
				}
			} catch (Exception ex) {
				return Response.serverError().entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.",FrontMessage.CRITICAL)).build();
			}
		if(nuevoToken == null){
			return Response.ok(docente).build();
		}else{
			return Response.ok(docente).header("auth0", nuevoToken).build();
		}
	}
}


