package ar.com.santalucia.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import ar.com.santalucia.dominio.modelo.usuarios.Directivo;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;

import ar.com.santalucia.servicio.ServicioDirectivo;

/**
 * @author Ariel Ramirez
 * 
 * @version 1.0
 *
 */

// �ltimo modificador: Ariel Ramirez @ 15-10-2015 17:44

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
	 * @param id
	 *            identificador del usuario a buscar.
	 * @return Response ok (Status 200) e instancia de directivo, incluyendo
	 *         datos de tel�fono, mail, t�tulos y domicilio o null si no existe.
	 */
	@GET
	@Path("/dir/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		Directivo directivo = new Directivo();
		directivo = null;
		try {
			setInstance();
			directivo = servicioDirectivo.getUsuario(id);
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.ok(ex).build();
		}
		return Response.ok(directivo).build();
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
			telefonos = servicioDirectivo.getTelefonos(id);
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
	 *         el directivo o no hay nada.
	 */
	
	@GET
	@Path("/mai/{id:[0-9][0-9]*}")
	public Response getMails(@PathParam("id") final Long id){
		Set<Mail> mails = new HashSet<Mail>();
		mails = null;
		try {
			setInstance();
			mails = servicioDirectivo.getMails(id);
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
	 *         el directivo o no hay nada.
	 */
	@GET
	@Path("/tit/{id:[0-9][0-9]*}")
	public Response getTitulos(@PathParam("id") final Long id){
		Set<Titulo> titulos = new HashSet<Titulo>();
		titulos = null;
		try{
			setInstance();
			titulos = servicioDirectivo.getTitulos(id);
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
		List<Directivo> directivo = new ArrayList<Directivo>();
		directivo = null;
		try {
			setInstance();
			directivo = servicioDirectivo.getUsuarios(new Directivo());
		} catch (Exception ex) {
			if (directivo == null) {
				return Response.status(Status.NOT_FOUND).build();
			}
		}
		return Response.ok(directivo).build();
	}
	
	/**
	 * Utilice este m�todo para:<br>
	 * 1) Agregar un nuevo directivo con o sin datos adicionales (tel�fono, mail, t�tulo y domicilio) de una sola vez. <br>
	 * 2) Actualizar datos de directivo que no sean adicionales (tel�fono, mail, t�tulo y domicilio).<br>
	 * 3) Agregar uno o varios datos adicionales (tel�fono, t�tulo y mail).<br>
	 * (! - IMPORTANTE) Para actualizar o eliminar datos adicionales (tel�fono, mail, t�tulo y domicilio) use los m�todos UPDATE y DELETE respectivos. 
	 * @param directivo
	 * @return Response ok (Status 200) con el id de usuario si el resultado es exitoso o la excepci�n generada. 
	 */
	@PUT
	@Path("/dir/")
	public Response update(final Directivo directivo) { 
		try {
			setInstance();
			servicioDirectivo.addUsuario(directivo);
			return Response.ok(directivo.getIdUsuario()).build();
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.ok(ex).build();
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
			exito = servicioDirectivo.modifyMail(mail);
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
			exito = servicioDirectivo.modifyDomicilio(domicilio);
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
			exito = servicioDirectivo.modifyTitulo(titulo);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * Utilice este m�todo para eliminar un directivo. Adem�s de eliminar el directivo, tambi�n se eliminan los datos adicionales (tel�fono, mail, titulo y domicilio).
	 * @param id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepci�n generada.<br>
	 * (! PRECAUCI�N) Se eliminan todos los datos adicionales asociados (telefono, mail, titulos y domicilio) al eliminar el usuario.
	 */
	@DELETE
	@Path("/dir/{id:[0-9][0-9]*}")
	public Response deleteDirectivoById(@PathParam("id") final Long id) {
		Boolean exito = false;
		try {
			setInstance();
			exito = servicioDirectivo.removeUsuario(servicioDirectivo.getUsuario(id));
			return Response.ok(exito).build();
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.ok(ex).build();
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
			exito = servicioDirectivo.removeDomicilio(id);
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
			exito = servicioDirectivo.removeTitulo(id);
			return Response.ok(exito).build();
		}catch (Exception ex){
			//e.printStackTrace();
			return Response.ok(ex).build();
		}
	}
}
