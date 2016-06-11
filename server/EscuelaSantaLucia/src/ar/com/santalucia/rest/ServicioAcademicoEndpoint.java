/**
 * 
 */
package ar.com.santalucia.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
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

import ar.com.santalucia.dominio.dto.MateriaAltaDTO;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Area;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Llamado;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.academico.Mesa;
import ar.com.santalucia.servicio.ServicioAcademico;
import ar.com.santalucia.servicio.ServicioAlumno;
import ar.com.santalucia.servicio.ServicioDocente;

/**
 * @author Casa
 *
 */

// �ltimo modificador: Ariel Ramirez @ 02-05-2016

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

	/**
	 * Agrega o modifica un objeto de tipo Anio. Use este m�todo cuando desee agregar un nuevo curso (S�LO SI ANIO TAMBI�N ES NUEVO).<br>
	 * El gestor de Anio cuenta con la l�gica necesaria para detectar objetos Curso nuevos y agregarlos. No se realiza validaciones de nombre de Curso.<br>
	 * ATENCI�N: Es posible modificar s�lo los atributos propios del Anio con este m�todo. Para modificar o eliminar Curso y Materia, utilice los m�todos correspondientes.
	 * @param anio Objeto Anio que se desea agregar o modificar. Si se trata de una modificaci�n, cargue los atributos de arreglos con null.
	 * @return Id de anio si la operaci�n es exitosa.
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
	 * @return True si la operaci�n es exitosa.
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
	 * @return True si la operaci�n es exitosa.
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
	public Response updateMateria(MateriaAltaDTO materiaAltaDTO) {
		try {
			setInstance();
			servicioAcademico.addMateria(materiaAltaDTO);
			return Response.ok(materiaAltaDTO.getIdMateria()).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * 
	 * @param id Identificador del objeto de tipo Materia que se desea eliminar.
	 * @return True si la operaci�n es exitosa.
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
	 * @return True si la operaci�n es exitosa.
	 */
	@POST
	@Path("/mat/asignDoc")
	public Response asignarDocentesAMateria(JsonPack jsonPack){
		try{
			//JsonPack jsonPack=new JsonPack();
			//[0]idDocenteTitular [1]idDocenteSuplente [2]idMateria
			setInstance();
			ServicioDocente servicioDocente = new ServicioDocente();
			return Response.ok(servicioAcademico.asignarDocentesAMateria(servicioDocente.getUsuario(jsonPack.getValues().elementAt(0)), 
																		servicioDocente.getUsuario(jsonPack.getValues().elementAt(1)), 
																		jsonPack.getValues().elementAt(2))).build(); 
		} catch(Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * Desvincula Docente titular y suplente a una materia determinada. Todos los objetos involucrados deben estar persistentes.<br>
	 * Orden de elementos en JsonPack <br>[0]id Docente Titular, <br> [1]id Docente Suplente, <br>[2]id Materia
	 * @param jsonPack
	 * @return true si la operaci�n es exitosa
	 */
	@POST
	@Path("/mat/desvinDoc")
	public Response desvincularDocentesDeMateria(JsonPack jsonPack) {
		try {
			setInstance();
			ServicioDocente servicioDocente = new ServicioDocente();
			return Response.ok(servicioAcademico.desvincularDocentesDeMateria(servicioDocente.getUsuario(jsonPack.getValues().elementAt(0)), 
																		servicioDocente.getUsuario(jsonPack.getValues().elementAt(1)), 
																		jsonPack.getValues().elementAt(2))).build(); 
		} catch(Exception ex) {
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
	 * @return True si la operaci�n es exitosa.
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
	 * Vincula un Alumno que se encuentre en el Curso gen�rico (sin curso) a un Curso determinado. Todos los objetos involucrados deben estar en estado persistente.<br>
	 * Orden de elementos en JsonPack: <br> [0]id Alumno, <br>[1]Id Curso.
	 * @param jsonPack
	 * @return True si la operaci�n es exitosa.
	 */
	@POST
	@Path("/cur/vin")
	public Response asignarAlumnoACurso(JsonPack jsonPack){
		try {
			setInstance();
			ServicioAlumno servicioAlumno = new ServicioAlumno();
			//[0]idAlumno [1]idCurso
			servicioAcademico.asignarAlumnoACurso(servicioAlumno.getUsuario(jsonPack.getValues().elementAt(0)), jsonPack.getValues().elementAt(1));
			return Response.ok(true).build();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * Desvincula un Alumno que se encuentre en un Curso no gen�rico y lo asigna al Curso gen�rico(sin curso). Todos los objetos involucrados deben estar en estado persistente.<br>
	 * Orden de elementos en JsonPack: <br> [0]id Alumno, <br>[1]Id Curso.
	 * @param jsonPack
	 * @return True si la operaci�n es exitosa.
	 */
	@POST
	@Path("/cur/desvin")
    public Response desvincularAlumnoDeCurso(JsonPack jsonPack){
		try {
			setInstance();
			ServicioAlumno servicioAlumno = new ServicioAlumno();
			servicioAcademico.desvincularAlumnoDeCurso(servicioAlumno.getUsuario(jsonPack.getValues().elementAt(0)), jsonPack.getValues().elementAt(1));
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
	@Path("/mat/vin")
	public Response asignarMateriaAAnio(JsonPack jsonPack){
		try{
			setInstance();
			//[0]idMateria [1]idAnio
			return Response.ok(servicioAcademico.asignarMateriaAAnio(
					    	   servicioAcademico.getMateria(jsonPack.getValues().elementAt(0)), 
					           jsonPack.getValues().elementAt(1))).build();
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
	@Path("/mat/desvin")
    public Response desvincularMateriaDeAnio(JsonPack jsonPack) {
		try {
			setInstance();
			//[0]idMateria [1]idAnio
			return Response.ok(servicioAcademico.desvincularMateriaDeAnio(
								servicioAcademico.getMateria(
										jsonPack.getValues().elementAt(0)), 
										jsonPack.getValues().elementAt(1))).build();
		} catch(Exception ex) {
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
	public Response getAreaById(@PathParam("id") final Long id) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.getArea(id)).build();
		} catch(Exception ex) {
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
	
	@GET
	@Path("/previas/{doc:[0-9][0-9]*}")
	public Response getPrevia(@PathParam("doc") final Long doc){
		try{
			setInstance();
			return Response.ok(servicioAcademico.getPreviasDesaprobadas(doc)).build();
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
	}
	
	@GET
	@Path("/histPrevias/{doc:[0-9][0-9]*}")
	public Response getHistorialPrevia(@PathParam("doc") final Long doc){
		try{
			setInstance();
			return Response.ok(servicioAcademico.getPrevias(doc)).build();
		}catch(Exception ex){
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

	@POST
	@Path("/mes/asign")
	public Response asignarMesaALlamado(JsonPack jsonPack) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.asignarMesaALlamado(
					servicioAcademico.getMesa(jsonPack.getValues().elementAt(0)), 
					jsonPack.getValues().elementAt(1))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@POST
	@Path("/mes/desvin")
	public Response desvincularMesaDeLlamado(JsonPack jsonPack) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.desvincularMesaDeLlamado(
					servicioAcademico.getMesa(jsonPack.getValues().elementAt(0)), 
					jsonPack.getValues().elementAt(1))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}

	@POST
	@Path("/mes/asignDoc")
	public Response asignarDocenteAMesa(JsonPack jsonPack) {
		try {
			setInstance();
			ServicioDocente servicioDocente = new ServicioDocente();
			return Response.ok(servicioAcademico.asignarDocenteAMesa(
					servicioDocente.getUsuario(jsonPack.getValues().elementAt(0)), 
					jsonPack.getValues().elementAt(1))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}

	@POST
	@Path("/mes/desvinDoc")
	public Response desvincularDocenteDeMesa(JsonPack jsonPack) {
		try {
			setInstance();
			ServicioDocente servicioDocente = new ServicioDocente();
			return Response.ok(servicioAcademico.desvincularDocenteDeMesa(
					servicioDocente.getUsuario(jsonPack.getValues().elementAt(0)), 
					jsonPack.getValues().elementAt(1))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@POST
	@Path("/mes/asignMat")
	public Response asignarMateriaAMesa(JsonPack jsonPack) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.asignarMateriaAMesa(
					servicioAcademico.getMateria(jsonPack.getValues().elementAt(0)), 
					jsonPack.getValues().elementAt(1))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@POST
	@Path("/mes/desvinMat")
	public Response desvincularMateriaDeMesa(JsonPack jsonPack) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.desvincularMateriaDeMesa(
					servicioAcademico.getMateria(jsonPack.getValues().elementAt(0)), 
					jsonPack.getValues().elementAt(1))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
}
	

