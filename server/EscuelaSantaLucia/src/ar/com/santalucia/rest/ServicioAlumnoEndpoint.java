package ar.com.santalucia.rest;

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

import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.excepciones.LoginError;
import ar.com.santalucia.servicio.ServicioAlumno;
import ar.com.santalucia.servicio.ServicioLogin;

/**
 * 
 * @author Ariel Ramirez
 *
 * @version 2.0
 */

// �ltimo modificador: Ariel Ramirez @ 15-10-2015 17:44

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
	
	@POST
	public Response create(final ServicioAlumno servicioalumno) {
		// TODO: process the given servicioalumno
		// you may want to use the following return statement, assuming that
		// ServicioAlumno#getId() or a similar method
		// would provide the identifier to retrieve the created ServicioAlumno
		// resource:
		// return
		// Response.created(UriBuilder.fromResource(ServicioAlumnoEndpoint.class).path(String.valueOf(servicioalumno.getId())).build()).build();
		return Response.created(null).build();
	}

	/**
	 *  
	 * @param id
	 *            Identificador del usuario a buscar.
	 * @return Response ok (Status 200) e instancia de alumno, incluyendo datos de tel�fono, 
	 * mail y domicilio o null si no existe.
	 */
	
	
	@GET
	@Path("/alu/{id:[0-9][0-9]*}")
	public Response getAlumnoById(@PathParam("id") final Long id, @HeaderParam("rol") String rolIn, @HeaderParam("auth0") String token) {
		Alumno alumno = new Alumno();
		String nuevoToken = new String();
		try {
			//ServicioLogin.comprobar(token, rolIn); //Hace la comprobacion de la credencial
			nuevoToken = ServicioLogin.comprobar(token, rolIn);
			setInstance();
			alumno = servicioAlumno.getUsuario(id);
		}catch (LoginError ex){
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
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		if(nuevoToken == null){
			return Response.ok(alumno).build();
		}else{
			return Response.ok(alumno).header("auth0", nuevoToken).build();
		}
	}

	/**
	 * @param id
	 *            Identificador del usuario del cu�l se desea recuperar los
	 *            tel�fonos.
	 * @return Response ok (Status 200) y Set de tel�fonos o 
	 *  null si no existe el alumno o no hay nada.
	 */
	@GET
	@Path("/tel/{id:[0-9][0-9]*}") // tel
	public Response getTelefonos(@PathParam("id") final Long id) {
		Set<Telefono> telefonos = new HashSet<Telefono>();
		telefonos = null;
		try {
			setInstance();
			telefonos = servicioAlumno.getTelefonos(id);
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.ok(ex).build();
		}
		return Response.ok(telefonos).build();
	}

	/**
	 * 
	 * @param id
	 *            Identificador del usuario del cu�l se desea recuperar los
	 *            mails.
	 * @return Response ok (Status 200) y Set de mails
	 *  o null si no existe el alumno o no hay nada.
	 */
	@GET
	@Path("/mai/{id:[0-9][0-9]*}")
	public Response getMails(@PathParam("id") final Long id) {
		Set<Mail> mails = new HashSet<Mail>();
		mails = null;
		try {
			setInstance();
			mails = servicioAlumno.getMails(id);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			return Response.ok(ex).build();
		}
		return Response.ok(mails).build();
	}

	/**
	 * Utilice este m�todo para el listado de alumnos completo.
	 * @return Response ok (Status 200) y listado de alumnos o 
	 *  null si no hay nada. <br>
	 *  (! PRECAUCI�N) �El m�todo devuelve el listado de todos los alumnos de la base de datos!
	 */
	@GET
	@Path("/listAll")
	public Response listAll() {
		List<Alumno> alumnos = new ArrayList<Alumno>();
		alumnos = null;
		try {
			setInstance();
			alumnos = servicioAlumno.getUsuarios(new Alumno());
		} catch (Exception ex) {
			if (alumnos == null) {
				return Response.status(Status.NOT_FOUND).build();
			}
		}
		return Response.ok(alumnos).build();
	}

	/**
	 * Utilice este m�todo para:<br>
	 * 1) Agregar un nuevo alumno con o sin datos adicionales (tel�fono, mail y domicilio) de una sola vez. <br>
	 * 2) Actualizar datos de alumno que no sean adicionales (tel�fono, mail y domicilio).<br>
	 * 3) Agregar uno o varios datos adicionales (tel�fono y mail).<br>
	 * (! - IMPORTANTE) Para actualizar o eliminar datos adicionales (tel�fono, mail y domicilio) use los m�todos UPDATE y DELETE respectivos. 
	 * @param alumno
	 * @return Response ok (Status 200) con el id de usuario si el resultado es exitoso o la excepci�n generada. 
	 */
	@PUT
	@Path("/alu/")
	public Response update(final Alumno alumno) {
		try {
			setInstance();
			servicioAlumno.addUsuario(alumno);
			return Response.ok(alumno.getIdUsuario()).build();
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
			exito = servicioAlumno.modifyTelefono(telefono);
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
			exito = servicioAlumno.modifyMail(mail);
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
			exito = servicioAlumno.modifyDomicilio(domicilio);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}

	/**
	 * Utilice este m�todo para eliminar un alumno. Adem�s de eliminar el alumno, tambi�n se eliminan los datos adicionales (tel�fono, mail y domicilio).
	 * @param id
	 * @return Response ok (Status 200) con true si el resultado es exitoso o la excepci�n generada.<br>
	 * (! PRECAUCI�N) Se eliminan todos los datos adicionales asociados (telefono, mail y domicilio) al eliminar el usuario.
	 */
	@DELETE
	@Path("/alu/{id:[0-9][0-9]*}")
	public Response deleteAlumnoById(@PathParam("id") final Long id) {
		Boolean exito = false;
		try {
			setInstance();
			exito = servicioAlumno.removeUsuario(servicioAlumno.getUsuario(id));
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
			exito = servicioAlumno.removeTelefono(id);
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
			exito = servicioAlumno.removeDomicilio(id);
			return Response.ok(exito).build();
		}catch (Exception ex){
			//e.printStackTrace();
			return Response.ok(ex).build();
		}
	}	
}
