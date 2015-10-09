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

import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.servicio.ServicioAlumno;

/**
 * 
 * @author Ariel Ramirez
 *
 * @version 2.0
 */

// Último modificador: Ariel Ramirez @ 03-10-2015 21:45

@Path("/sAlumno")
@Produces({ /* "application/xml", */ "application/json" })
@Consumes({ /* "application/xml", */ "application/json" })
public class ServicioAlumnoEndpoint {
	
	private ServicioAlumno servicioAlumno = null;

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
		// if (servicioAlumno==null){
		// try {
		// servicioAlumno=new ServicioAlumno();
		// } catch (Exception ex) {
		// //e.printStackTrace();
		// return Response.ok(ex).build();
		// }
		// }
		return Response.created(null).build();
	}

	/**
	 * 
	 * @param id
	 *            Identificador del usuario a buscar.
	 * @return Status 200(Response: ok) e instancia de alumno o Status 404
	 *         (Response: not found) y null si no no existe.
	 */
	@GET
	@Path("/alu/{id:[0-9][0-9]*}")
	public Response getAlumnoById(@PathParam("id") final Long id) {
		Alumno alumno = new Alumno();
		alumno = null;
		try {
			setInstance();
			alumno = servicioAlumno.getUsuario(id);
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.ok(ex).build();
		}
		return Response.ok(alumno).build();
	}

	/**
	 * 
	 * @param id
	 *            Identificador del usuario del cuál se desea recuperar los
	 *            teléfonos.
	 * @return Status 200(Response: ok) y Set de teléfonos o Status 404
	 *         (Response: not found) y null si no existe el alumno o no hay
	 *         nada.
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
	 *            Identificador del usuario del cuál se desea recuperar los
	 *            mails.
	 * @return Status 200(Response: ok) y Set de mails o Status 404 (Response:
	 *         not found) y null si no existe el alumno o no hay nada.
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
			ex.printStackTrace();
		}
		if (mails == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(mails).build();
	}

	/**
	 * 
	 * @return Status 200(Response: ok) y listado de alumnos o Status 404
	 *         (Response: not found) y null si no hay nada.
	 */
	@GET
	@Path("/list")
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

	@PUT
	@Path("/alu/")
	public Response update(final Alumno alumno) { // Agrega o modifica un alumno
		try {
			servicioAlumno.addUsuario(alumno);
			return Response.ok(alumno.getIdUsuario()).build();
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.ok(ex).build();
		}
	}

	/**
	 * 
	 * @param telefono
	 * @return
	 */
	@PUT
	@Path("/tel/")
	public Response updateTelefono(final Telefono telefono) { // Solo modifica el teléfono, para agregar use update(Alumnoalumno); y para quitar DELETE
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
