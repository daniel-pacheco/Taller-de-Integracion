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
import ar.com.santalucia.dominio.modelo.academico.Llamado;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.academico.Mesa;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;
import ar.com.santalucia.servicio.ServicioAcademico;
import ar.com.santalucia.servicio.ServicioAlumno;
import ar.com.santalucia.servicio.ServicioDocente;

/**
 * @author Casa
 *
 */

// Último modificador: Ariel Ramirez @ 26/12/2015 12:46

/*
 *Ejemplo JsonPack:
 *
 * {
 *    "values":
 *    [1,
 *     2,
 *     3]
 * }
 */

@Path("/sAcademico")
@PermitAll
@Produces("application/json")
@Consumes("application/json")
public class ServicioAcademicoEndpoint {

	private ServicioAcademico servicioAcademico = null;
	
	/**
	 * Instancia un objeto ServicioAcademico si no existe
	 * @throws Exception
	 */
	private void setInstance() throws Exception {
		if (servicioAcademico == null) {
			try {
				servicioAcademico = new ServicioAcademico();
			} catch (Exception ex) {
				throw ex;
			}
		}
	}
	
	
	@POST
	public Response create(final ServicioAcademico servicioacademico) {
		//TODO: process the given servicioacademico 
		//you may want to use the following return statement, assuming that ServicioAcademico#getId() or a similar method 
		//would provide the identifier to retrieve the created ServicioAcademico resource:
		//return Response.created(UriBuilder.fromResource(ServicioAcademicoEndpoint.class).path(String.valueOf(servicioacademico.getId())).build()).build();
		return Response.created(null).build();
	}

	/**
	 * Agrega o modifica un objeto de tipo Anio. Use este método cuando desee agregar un nuevo curso.<br>
	 * El gestor de Anio cuenta con la lógica necesaria para detectar objetos Curso nuevos y agregarlos. No se realiza validaciones de nombre de Curso.
	 * @param anio Objeto Anio que se desea agregar o modificar.
	 * @return Id de anio si la operación es exitosa.
	 */
	@PUT
	@Path("/anio/")
	public Response updateAnio(Anio anio) {
		try {
			setInstance();
			servicioAcademico.addAnio(anio);
			return Response.ok(anio.getIdAnio()).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * 
	 * @param id Identificador de anio que se desea eliminar.
	 * @return True si la operación es exitosa.
	 */
	@DELETE
	@Path("/anio/{id:[0-9][0-9]*}")
	public Response deleteAnioById(@PathParam("id") final Long id) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.deleteAnio(servicioAcademico.getAnio(id))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}

	}
	
	/**
	 * 
	 * @param id Identificador de anio que se desea obtener.
	 * @return Objeto tipo Anio.
	 */
	@GET
	@Path("/anio/{id:[0-9][0-9]*}")
	public Response getAnioById(@PathParam("id") final Long id) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.getAnio(id)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * Lista todos los objetos de tipo Anio existente.
	 * @return Lista de Anios.
	 */
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
	
	/**
	 * 
	 * @param curso
	 * @param idAnio
	 * @return
	 */
	@PUT
	@Path("/cur/{id:[0-9][0-9]*}")
	public Response updateCurso(Curso curso, @PathParam("id") final Long idAnio) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.addCurso(curso, idAnio)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * 
	 * @param idC Identificador del objeto Curso que se desea eliminar.
	 * @return True si la operación es exitosa.
	 */
	@DELETE
	@Path("/cur/{idC:[0-9][0-9]*}")
	public Response deleteCursoById(@PathParam("idC") final Long idC) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.deleteCurso(servicioAcademico.getCurso(idC))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * 
	 * @param id Identificador del curso que se desea obtener.
	 * @return Objeto de tipo Curso.
	 */
	@GET
	@Path("/cur/{id:[0-9][0-9]*}")
	public Response getCursoById(@PathParam("id") final Long id) {
		Curso curso = new Curso();
		try {
			setInstance();
			return Response.ok(servicioAcademico.getCurso(id)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}		
	}
	
	/**
	 * Agregar o modifica un objeto de tipo Materia.
	 * @param materia Objeto de tipo Materioas que se desea agregar o modificar.
	 * @return Identificador de Materia.
	 */
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
	
	/**
	 * 
	 * @param id Identificador del objeto de tipo Materia que se desea eliminar.
	 * @return True si la operación es exitosa.
	 */
	@DELETE
	@Path("/mat/{id:[0-9][0-9]*}")
	public Response deleteMateriaById(@PathParam("id") final Long id) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.deleteMateria(servicioAcademico.getMateria(id))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * 
	 * @param id Identificador del objeto de tipo Materia que se desea obtener.
	 * @return Objeto de tipo Materia.
	 */
	@GET
	@Path("/mat/{id:[0-9][0-9]*}")
	public Response getMateriaById(@PathParam("id") final Long id) {
		Materia materia = new Materia();
		try {
			setInstance();
			materia = servicioAcademico.getMateria(id);
			return Response.ok(materia).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * Asigna Docente titular y suplente a una materia determinada. Todos los objetos involucrados deben estar en estado persistente<BR>
	 * Orden de elementos en JsonPack: <br> [0]id Docente Titular, <br>[1]Id Docente Suplente, <br>[2]Id Materia.
	 * @param jsonPack
	 * @return True si la operación es exitosa.
	 */
	@POST
	//@Path("/mat/asignDoc/{idDt:[0-9][0-9]*}/{idDs:[0-9][0-9]*}/{idM:[0-9][0-9]*}")
	@Path("/mat/asignDoc")
	//public Response asignarDocentesAMateria(@PathParam("idDt") final Long idDt, @PathParam("idDs") final Long idDs, @PathParam("idM") final Long idM){
	public Response asignarDocentesAMateria(JsonPack jsonPack){
		try{
			//JsonPack jsonPack=new JsonPack();
			//[0]idDocenteTitular [1]idDocenteSuplente [2]idMateria
			setInstance();
			ServicioDocente servicioDocente = new ServicioDocente();
			return Response.ok(servicioAcademico.asignarDocentesAMateria(servicioDocente.getUsuario(jsonPack.getValues().elementAt(0)), 
																		servicioDocente.getUsuario(jsonPack.getValues().elementAt(1)), 
																		jsonPack.getValues().elementAt(2))).build(); 
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * 
	 * @param area Objeto de tipo Area que se desea crear o modificar.
	 * @return Identificador de Area.
	 */
	@PUT
	@Path("/area")
	public Response updateArea(Area area){
		try{
			setInstance();
			servicioAcademico.addArea(area);
			return Response.ok(area.getIdArea()).build();
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
		
	}
	
	/**
	 * Lista los objetos de tipo Area disponibles.
	 * @return Lista de objetos tipo Area.
	 */
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
	
	/**
	 * 
	 * @param idArea Identificador del objeto tipo Area que se desea eliminar.
	 * @return True si la operación es exitosa.
	 */
	@DELETE
	@Path("/area/{id:[0-9][0-9]*}")
	public Response deleteArea(@PathParam("id") final Long idArea){
		try{
			setInstance();
			servicioAcademico.deleteArea(servicioAcademico.getArea(idArea));
			return Response.ok(true).build();
		} catch(Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * Vincula un Alumno que se encuentre en el Curso genérico (sin curso) a un Curso determinado. Todos los objetos involucrados deben estar en estado persistente.<br>
	 * Orden de elementos en JsonPack: <br> [0]id Alumno, <br>[1]Id Curso.
	 * @param jsonPack
	 * @return True si la operación es exitosa.
	 */
	@POST
	//@Path("/cur/vin/{idC:[0-9][0-9]*}")
	//@Path("/cur/vin/{idC:[0-9][0-9]*}/{idA:[0-9][0-9]*}")
	@Path("/cur/vin")
	//public Response asignarAlumnoACurso(@PathParam("idC") final Long idCurso, @PathParam("idA") final Long idAlumno) {
	public Response asignarAlumnoACurso(JsonPack jsonPack){
	//public Response asignarAlumnoACurso(Alumno alumno, @PathParam("idC") final Long idCurso) {
		try {
			setInstance();
			ServicioAlumno servicioAlumno = new ServicioAlumno();
			//servicioAcademico.asignarAlumnoACurso(servicioAlumno.getUsuario(idAlumno), idCurso);
			//[0]idAlumno [1]idCurso
			servicioAcademico.asignarAlumnoACurso(servicioAlumno.getUsuario(jsonPack.getValues().elementAt(0)), jsonPack.getValues().elementAt(1));
			//servicioAcademico.asignarAlumnoACurso(alumno, idCurso);
			return Response.ok(true).build();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * Desvincula un Alumno que se encuentre en un Curso no genérico y lo asigna al Curso genérico(sin curso). Todos los objetos involucrados deben estar en estado persistente.<br>
	 * Orden de elementos en JsonPack: <br> [0]id Alumno, <br>[1]Id Curso.
	 * @param jsonPack
	 * @return True si la operación es exitosa.
	 */
	@POST
	//@Path("/cur/desvin/{idC:[0-9][0-9]*}")
	//@Path("/cur/desvin/{idC:[0-9][0-9]*}/{idA:[0-9][0-9]*}")
	@Path("/cur/desvin")
	//public Response desvincularAlumnoDeCurso(@PathParam("idC") final Long idCurso, @PathParam("idA") final Long idAlumno) {
	//public Response desvincularAlumnoDeCurso(Alumno alumno, @PathParam("idC") final Long idCurso) {
    public Response desvincularAlumnoDeCurso(JsonPack jsonPack){
		try {
			setInstance();
			ServicioAlumno servicioAlumno = new ServicioAlumno();
			//servicioAcademico.desvincularAlumnoDeCurso(servicioAlumno.getUsuario(idAlumno), idCurso);
			//Orden de elementos en JsonPack: <br> [0]id Alumno, <br>[1]Id Curso.
			servicioAcademico.desvincularAlumnoDeCurso(servicioAlumno.getUsuario(jsonPack.getValues().elementAt(0)), jsonPack.getValues().elementAt(1));
			//servicioAcademico.desvincularAlumnoDeCurso(alumno, idCurso);
			return Response.ok(true).build();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * Vincula una Materia A un Anio. Todos los objetos involucrados deben estar en estado persistente.<br>
	 * Orden de elementos en JsonPack: <br> [0]id Materia, <br>[1]Id Anio.
	 * @param jsonPack
	 * @return
	 */
	@POST
	//@Path("/mat/vin/{idM:[0-9][0-9]*}/{idA:[0-9][0-9]*}")
	@Path("/mat/vin")
	//public Response asignarMateriaAAnio(@PathParam("idM") final Long idMateria, @PathParam("idA") final Long idAnio){
    public Response asignarMateriaAAnio(JsonPack jsonPack){
		try{
			setInstance();
			//[0]idMateria [1]idAnio
			return Response.ok(servicioAcademico.asignarMateriaAAnio(
					    	   servicioAcademico.getMateria(jsonPack.getValues().elementAt(0)), 
					           jsonPack.getValues().elementAt(1))).build();
			//return Response.ok(servicioAcademico.asignarMateriaAAnio(servicioAcademico.getMateria(idMateria), idAnio)).build();
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * Desvincula una Materia de un Anio.
	 * Orden de elementos en JsonPack: <br> [0]id Mateia, <br>[1]Id Anio.
	 * @param jsonPack
	 * @return
	 */
	@POST
	//@Path("/mat/desvin/{idM:[0-9][0-9]*}/{idA:[0-9][0-9]*}")
	@Path("/mat/desvin")
	//public Response desvincularMateriaDeAnio(@PathParam("idM") final Long idMateria, @PathParam("idA") final Long idAnio){
    public Response desvincularMateriaDeAnio(JsonPack jsonPack){
		try{
			setInstance();
			//[0]idMateria [1]idAnio
			return Response.ok(servicioAcademico.desvincularMateriaDeAnio(servicioAcademico.getMateria(jsonPack.getValues().elementAt(0)), jsonPack.getValues().elementAt(1))).build();
			//return Response.ok(servicioAcademico.desvincularMateriaDeAnio(servicioAcademico.getMateria(idMateria), idAnio)).build();
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
		
	}
	
	/**
	 * 
	 * @param id Identificador del objeto tipo Area que se desea obtener.
	 * @return Objeto de tipo Area.
	 */
	@GET
	@Path("/area/{id:[0-9][0-9]*}")
	public Response getAreaById(@PathParam("id") final Long id){
		try{
			setInstance();
			return Response.ok(servicioAcademico.getArea(id)).build();
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
	}

	
	@PUT
	@Path("/llm/")
	public Response updateLlamado(Llamado llamado) {
		try {
			setInstance();
			servicioAcademico.addLlamado(llamado);
			return Response.ok(llamado.getIdLlamado()).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@DELETE
	@Path("/llm/{idL:[0-9][0-9]*}")
	public Response deleteLlamadoById(@PathParam("idL") Long idLlamado) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.deleteLlamado(servicioAcademico.getLlamado(idLlamado))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@GET
	@Path("/llm/{idL:[0-9][0-9]*}")
	public Response getLlamadoById(@PathParam("idL") Long idL) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.getLlamado(idL)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@GET
	@Path("/llm/listAll")
	public Response llamadoListAll() {
		try {
			setInstance();
			Llamado llamado = new Llamado();
			return Response.ok(servicioAcademico.getLlamados(llamado)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	
	@PUT
	@Path("/mes/")
	public Response updateMesa(Mesa mesa) {
		try {
			setInstance();
			servicioAcademico.addMesa(mesa);
			return Response.ok(mesa.getIdMesa()).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}

	@DELETE
	@Path("/mes/{idMe:[0-9][0-9]*}")
	public Response deleteMesaById(@PathParam("idMe") Long idMe) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.deleteMesa(servicioAcademico.getMesa(idMe))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@GET
	@Path("/mes/{idMe:[0-9][0-9]*}")
	public Response getMesaById(@PathParam("idMe") Long idMe) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.getMesa(idMe)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}

	@GET
	@Path("/mes/listAll")
	public Response mesaListAll() {
		try {
			setInstance();
			Mesa mesa = new Mesa();
			return Response.ok(servicioAcademico.getMesas(mesa)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}

}
	

