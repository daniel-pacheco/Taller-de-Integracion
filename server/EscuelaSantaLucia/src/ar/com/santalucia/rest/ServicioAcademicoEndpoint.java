/**
 * 
 */
package ar.com.santalucia.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Area;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;
import ar.com.santalucia.servicio.ServicioAcademico;
import ar.com.santalucia.servicio.ServicioAlumno;
import ar.com.santalucia.servicio.ServicioDocente;

/**
 * @author Casa
 *
 */
@Path("/sAcademico")
@PermitAll
@Produces("application/json")
@Consumes("application/json")
public class ServicioAcademicoEndpoint {

	private ServicioAcademico servicioAcademico = null;
	
	private void setInstance() throws Exception {
		if (servicioAcademico == null) {
			try {
				servicioAcademico = new ServicioAcademico();
			} catch (Exception ex) {
				throw ex;
			}
		}
	}
	
	/**
	* @param servicioacademico
	* @return
	*/
	@POST
	public Response create(final ServicioAcademico servicioacademico) {
		//TODO: process the given servicioacademico 
		//you may want to use the following return statement, assuming that ServicioAcademico#getId() or a similar method 
		//would provide the identifier to retrieve the created ServicioAcademico resource:
		//return Response.created(UriBuilder.fromResource(ServicioAcademicoEndpoint.class).path(String.valueOf(servicioacademico.getId())).build()).build();
		return Response.created(null).build();
	}

	@PUT
	@Path("/anio/")
	public Response updateAnio(Anio anio) {
		try {
			setInstance();
			servicioAcademico.addAnio(anio);
			return Response.ok(anio.getIdAnio()).build();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			return Response.ok(ex).build();
		}
	}
	
	@DELETE
	@Path("/anio/{id:[0-9][0-9]*}")
	public Response deleteAnioById(@PathParam("id") final Long id) {
		Boolean exito = false;
		try {
			setInstance();
			exito = servicioAcademico.deleteAnio(servicioAcademico.getAnio(id));
			return Response.ok(exito).build();
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.ok(ex).build();
		}

	}
	
	@RolesAllowed({"personal"}) //nueva Anotacion!
	@GET
	@Path("/anio/{id:[0-9][0-9]*}")
	public Response getAnioById(@PathParam("id") final Long id) {
		Anio anio = new Anio();
		try {
			setInstance();
			anio = servicioAcademico.getAnio(id);
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.ok(ex).build();
		}
		return Response.ok(anio).build();
	}
	
	@GET
	@Path("/anio/listAll")
	public Response aniolistAll() {
		List<Anio> anios = new ArrayList<Anio>();
		//anios = null;
		try {
			setInstance();
			anios = servicioAcademico.getAnios(new Anio());
		} catch (Exception ex) {
			if (anios == null) {
				return Response.status(Status.NOT_FOUND).build();
			}
		}
		return Response.ok(anios).build();
	}
	
	@PUT
	@Path("/cur/{id:[0-9][0-9]*}")
	public Response updateCurso(Curso curso, @PathParam("id") final Long idAnio) {
		try {
			setInstance();
			servicioAcademico.addCurso(curso, idAnio);
			return Response.ok(true).build();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			return Response.ok(ex).build();
		}
	}
	
	@DELETE
	@Path("/cur/{idC:[0-9][0-9]*}/{idA:[0-9][0-9]*}")
	public Response deleteCursoById(@PathParam("idC") final Long idC, @PathParam("idA") final Long idA) {
		Boolean exito = false;
		try {
			setInstance();
			exito = servicioAcademico.deleteCurso(servicioAcademico.getCurso(idC), idA);
			return Response.ok(exito).build();
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.ok(ex).build();
		}
	}
	
	@RolesAllowed({"personal"}) //nueva Anotacion!
	@GET
	@Path("/cur/{id:[0-9][0-9]*}")
	public Response getCursoById(@PathParam("id") final Long id) {
		Curso curso = new Curso();
		try {
			setInstance();
			curso = servicioAcademico.getCurso(id);
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
		return Response.ok(curso).build();
	}
	
	@PUT
	@Path("/mat/")
	public Response updateMateria(Materia materia) {
		try {
			setInstance();
			servicioAcademico.addMateria(materia);
			return Response.ok(materia.getIdMateria()).build();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			return Response.ok(ex).build();
		}
	}
	
	@DELETE
	@Path("/mat/{id:[0-9][0-9]*}")
	public Response deleteMateriaById(@PathParam("id") final Long id) {
		Boolean exito = false;
		try {
			setInstance();
			exito = servicioAcademico.deleteMateria(servicioAcademico.getMateria(id));
			return Response.ok(exito).build();
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.ok(ex).build();
		}
	}
	
	@GET
	@Path("/mat/{id:[0-9][0-9]*}")
	public Response getMateriaById(@PathParam("id") final Long id) {
		Materia materia = new Materia();
		try {
			setInstance();
			materia = servicioAcademico.getMateria(id);
		} catch (Exception ex) {
			// ex.printStackTrace();
			return Response.ok(ex).build();
		}
		return Response.ok(materia).build();
	}
	
	@POST
	@Path("/mat/asignDoc/{idDt:[0-9][0-9]*}/{idDs:[0-9][0-9]*}/{idM:[0-9][0-9]*}")
	public Response asignarDocentesAMateria(@PathParam("idDt") final Long idDt, @PathParam("idDs") final Long idDs, @PathParam("idM") final Long idM){
		try{
			setInstance();
			ServicioDocente servicioDocente = new ServicioDocente();
			return Response.ok(servicioAcademico.asignarDocentesAMateria(servicioDocente.getUsuario(idDt), 
																		servicioDocente.getUsuario(idDs), 
																		idM)).build(); 
		}catch(Exception ex){
			
		}
		return Response.ok(true).build();
	}
	
	@PUT
	@Path("/area")
	public Response updateArea(Area area){
		try{
			setInstance();
			servicioAcademico.addArea(area);
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
		return Response.ok(area.getIdArea()).build();
	}
	
	@GET
	@Path("/area/listAll")
	public Response areaListAll(){
		try{
			setInstance();
			Area area = new Area();
			return Response.ok(servicioAcademico.getAreas(area)).build();
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
	}
	
	@DELETE
	@Path("/area/{id:[0-9][0-9]*}")
	public Response deleteArea(@PathParam("id") final Long idArea){
		try{
			setInstance();
			servicioAcademico.deleteArea(servicioAcademico.getArea(idArea));
		} catch(Exception ex) {
			return Response.ok(ex).build();
		}
		return Response.ok(true).build();
	}
	
	@PUT
	@Path("/cur/vin/{idC:[0-9][0-9]*}")
	//@Path("/cur/vin/{idC:[0-9][0-9]*}/{idA:[0-9][0-9]*}")
	//public Response asignarAlumnoACurso(@PathParam("idA") final Long idAlumno, @PathParam("idC") final Long idCurso) {
	public Response asignarAlumnoACurso(Alumno alumno, @PathParam("idC") final Long idCurso) {
		try {
			setInstance();
			//ServicioAlumno servicioAlumno = new ServicioAlumno();
			//servicioAcademico.asignarAlumnoACurso(servicioAlumno.getUsuario(idAlumno), idCurso);
			servicioAcademico.asignarAlumnoACurso(alumno, idCurso);
			return Response.ok(true).build();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			return Response.ok(ex).build();
		}
	}
	
	@PUT
	@Path("/cur/desvin/{idC:[0-9][0-9]*}")
	//@Path("/cur/desvin/{idC:[0-9][0-9]*}/{idA:[0-9][0-9]*}")
	//public Response desvincularAlumnoDeCurso(@PathParam("idA") final Long idAlumno, @PathParam("idC") final Long idCurso) {
	public Response desvincularAlumnoDeCurso(Alumno alumno, @PathParam("idC") final Long idCurso) {
		try {
			setInstance();
			//ServicioAlumno servicioAlumno = new ServicioAlumno();
			//servicioAcademico.desvincularAlumnoDeCurso(servicioAlumno.getUsuario(idAlumno), idCurso);
			servicioAcademico.desvincularAlumnoDeCurso(alumno, idCurso);
			return Response.ok(true).build();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			return Response.ok(ex).build();
		}
	}
	
	@POST
	@Path("/mat/vin/{idM:[0-9][0-9]*}/{idA:[0-9][0-9]*}")
	public Response asignarMateriaAAnio(@PathParam("idM") final Long idMateria, @PathParam("idA") final Long idAnio){
		try{
			setInstance();
			return Response.ok(servicioAcademico.asignarMateriaAAnio(servicioAcademico.getMateria(idMateria), idAnio)).build();
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
	}
	
	@POST
	@Path("/mat/desvin/{idM:[0-9][0-9]*}/{idA:[0-9][0-9]*}")
	public Response desvincularMateriaDeAnio(@PathParam("idM") final Long idMateria, @PathParam("idA") final Long idAnio){
		try{
			setInstance();
			return Response.ok(servicioAcademico.desvincularMateriaDeAnio(servicioAcademico.getMateria(idMateria), idAnio)).build();
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
		
	}
	
	
	/**
	* @param id
	* @return
	*/
	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		//TODO: retrieve the servicioacademico 
		ServicioAcademico servicioacademico = null;
		if (servicioacademico == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(servicioacademico).build();
	}

	/**
	* @param startPosition
	* @param maxResult
	* @return
	*/
	@GET
	public List<ServicioAcademico> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		//TODO: retrieve the servicioacademicoes 
		final List<ServicioAcademico> servicioacademicoes = null;
		return servicioacademicoes;
	}

	/**
	* @param id
	* @param servicioacademico
	* @return
	*/
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final ServicioAcademico servicioacademico) {
		//TODO: process the given servicioacademico 
		return Response.noContent().build();
	}

	/**
	* @param id
	* @return
	*/
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		//TODO: process the servicioacademico matching by the given id 
		return Response.noContent().build();
	}

}
