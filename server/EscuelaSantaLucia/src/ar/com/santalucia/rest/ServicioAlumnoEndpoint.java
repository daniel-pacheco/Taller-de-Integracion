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
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.servicio.ServicioAlumno;

/**
 * 
 * @author Ariel Ramirez
 *
 *@version 1.0
 */

// Último modificador: Ariel Ramirez @ 17-09-2015 18:49

@Path("/sAlumno")
@Produces({ /*"application/xml", */ "application/json" })
@Consumes({ /*"application/xml", */ "application/json" })
public class ServicioAlumnoEndpoint {

	@POST
	public Response create(final ServicioAlumno servicioalumno) {
		// TODO: process the given servicioalumno
		// you may want to use the following return statement, assuming that texto
		// ServicioAlumno#getId() or a similar method
		// would provide the identifier to retrieve the created ServicioAlumno
		// resource:
		// return
		// Response.created(UriBuilder.fromResource(ServicioAlumnoEndpoint.class).path(String.valueOf(servicioalumno.getId())).build()).build();
		return Response.created(null).build();
	}

	/**
	 * 
	 * @param id Identificador del usuario a buscar.
	 * @return Status 200(Response: ok) e instancia de alumno o Status 404 (Response: not found) y null si no no existe. 
	 */
	@GET
	@Path("/galu/{id:[0-9][0-9]*}")
	public Response getById(@PathParam("id") final Long id) {
		ServicioAlumno servicioalumno = null;
		Alumno alumno = new Alumno();
		alumno = null;
		try {
			servicioalumno = new ServicioAlumno();
			alumno = servicioalumno.getUsuario(id); // estamos solicitando por parámetro!
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (alumno == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(alumno).build();
	}

	
	/**
	 * 
	 * @param id Identificador del usuario del cuál se desea recuperar los teléfonos.
	 * @return Status 200(Response: ok) y Set de teléfonos  o Status 404 (Response: not found) y null si no existe el alumno o no hay nada.
	 */
	@GET
	@Path("/gtel/{id:[0-9][0-9]*}")
	public Response getTelefonos(@PathParam("id") final Long id) {
		ServicioAlumno servicioAlumno = null;
		Set<Telefono> telefonos = new HashSet<Telefono>();
		telefonos = null;
		try {
			servicioAlumno = new ServicioAlumno();
			telefonos = servicioAlumno.getTelefonos(id);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		if (telefonos == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(telefonos).build();
	}

	/**
	 * 
	 * @param id Identificador del usuario del cuál se desea recuperar los mails.
	 * @return Status 200(Response: ok) y Set de mails  o Status 404 (Response: not found) y null si no existe el alumno o no hay nada.
	 */
	@GET
	@Path("/gmai/{id:[0-9][0-9]*}")
	public Response getMails(@PathParam("id") final Long id){
		ServicioAlumno servicioAlumno = null;
		Set<Mail> mails = new HashSet<Mail>();
		mails=null;
		try{
			servicioAlumno=new ServicioAlumno();
			mails=servicioAlumno.getMails(id);
		}catch(Exception ex){
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
	 * @return Status 200(Response: ok) y listado de alumnos  o Status 404 (Response: not found) y null si  no hay nada.
	 */
	@GET
	@Path("/list")
	public Response listAll(){
		ServicioAlumno servicioAlumno = null;
		List<Alumno> alumnos = new ArrayList<Alumno>();
		alumnos = null;
		try{
			servicioAlumno=new ServicioAlumno();
			alumnos=servicioAlumno.getUsuarios(new Alumno());
		}catch(Exception ex){
		if (alumnos == null) {
				return Response.status(Status.NOT_FOUND).build();
			}
		}
		return Response.ok(alumnos).build();
	}
	

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final Alumno servicioalumno) { //Esto es sólo una prueba
		// TODO: process the given servicioalumno
		Alumno alumno=new Alumno();
		//return Response.noContent().build();
		try {
			ServicioAlumno servicioAlumno = new ServicioAlumno();
			alumno = servicioAlumno.getUsuario(1L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.ok(alumno).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		// TODO: process the servicioalumno matching by the given id
		return Response.noContent().build();
	}

}
