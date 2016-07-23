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

import ar.com.santalucia.dominio.dto.AnioDTO;
import ar.com.santalucia.dominio.dto.DetallePreviaDTO;
import ar.com.santalucia.dominio.dto.MateriaAltaDTO;
import ar.com.santalucia.dominio.dto.MateriaDTO;
import ar.com.santalucia.dominio.dto.MesaAltaDTO;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Area;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Especialidad;
import ar.com.santalucia.dominio.modelo.academico.Llamado;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.academico.Mesa;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.servicio.ServicioAcademico;
import ar.com.santalucia.servicio.ServicioAlumno;
import ar.com.santalucia.servicio.ServicioDocente;

/**
 * @author Casa
 *
 */

// Último modificador: Ariel Ramirez @ 02-05-2016

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
	 * Agrega o modifica un objeto de tipo Anio. Use este método cuando desee agregar un nuevo curso (SÓLO SI ANIO TAMBIÉN ES NUEVO).<br>
	 * El gestor de Anio cuenta con la lógica necesaria para detectar objetos Curso nuevos y agregarlos. No se realiza validaciones de nombre de Curso.<br>
	 * ATENCIÓN: Es posible modificar sólo los atributos propios del Anio con este método. Para modificar o eliminar Curso y Materia, utilice los métodos correspondientes.
	 * @param anio Objeto Anio que se desea agregar o modificar. Si se trata de una modificación, cargue los atributos de arreglos con null.
	 * @return Id de anio si la operación es exitosa.
	 */
	@PUT
	@Path("/anio/")
	public Response updateAnio(Anio anio) {
		try {
			setInstance();
			servicioAcademico.addAnio(anio);
			return Response.ok(anio.getIdAnio()).build();
		} catch (ValidacionException vEx) {
			return Response.status(Status.CONFLICT).entity(vEx.getMensajesError()).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
		Anio anio = new Anio();
		try {
			setInstance();
			anio = servicioAcademico.getAnio(id);
			if (anio == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(anio).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
		try {
			setInstance();
			anios = servicioAcademico.getAnios(new Anio());
			if (anios.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			return Response.ok(anios).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@GET
	@Path("/anio/listAllMin")
	public Response getAniosDTO() {
		ArrayList<AnioDTO> aniosDTO = new ArrayList<AnioDTO>();
		try {
			setInstance();
			aniosDTO = servicioAcademico.getAniosDTO();
			if (aniosDTO.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			return Response.ok(aniosDTO).build();
		} catch (Exception ex) {
			//return Response.ok(ex).build();
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Hubo un error al obtener el listado resumido de años: " + ex.getMessage(), FrontMessage.CRITICAL))
					.build();
		}
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			curso = servicioAcademico.getCurso(id);
			if (curso == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(curso).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
		} catch (ValidacionException vEx) {
			return Response.status(Status.CONFLICT).entity(vEx.getMensajesError()).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			if (materia == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(materia).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	
	@GET
	@Path("/mat/listAllMin")
	public Response getMateriasDTO() {
		ArrayList<MateriaDTO> materiasDTO = new ArrayList<MateriaDTO>();
		try {
			setInstance();
			materiasDTO = servicioAcademico.getMateriasDTO();
			if (materiasDTO.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			return Response.ok(materiasDTO).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Asigna Docente titular y suplente a una materia determinada. Todos los objetos involucrados deben estar en estado persistente<BR>
	 * Orden de elementos en JsonPack: <br> [0]id Docente Titular, <br>[1]Id Docente Suplente, <br>[2]Id Materia.
	 * @param jsonPack
	 * @return True si la operación es exitosa.
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Desvincula Docente titular y suplente a una materia determinada. Todos los objetos involucrados deben estar persistentes.<br>
	 * Orden de elementos en JsonPack <br>[0]id Docente Titular, <br> [1]id Docente Suplente, <br>[2]id Materia
	 * @param jsonPack
	 * @return true si la operación es exitosa
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
		
	}
	
	/**
	 * Lista los objetos de tipo Area disponibles.
	 * @return Lista de objetos tipo Area.
	 */
	@GET
	@Path("/area/listAll")
	public Response areaListAll(){
		ArrayList<Area> areas = new ArrayList<Area>();
		try{
			setInstance();
			areas = servicioAcademico.getAreas(new Area());
			if (areas.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			return Response.ok(areas).build();
		}catch(Exception ex){
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
		Area area = new Area();
		try {
			setInstance();
			area = servicioAcademico.getArea(idArea);
			if (area == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("Elemento a eliminar no encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(servicioAcademico.deleteArea(area)).build();
		} catch(Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Vincula un Alumno que se encuentre en el Curso genérico (sin curso) a un Curso determinado. Todos los objetos involucrados deben estar en estado persistente.<br>
	 * Orden de elementos en JsonPack: <br> [0]id Alumno, <br>[1]Id Curso.
	 * @param jsonPack
	 * @return True si la operación es exitosa.
	 */
	@POST
	@Path("/cur/vin")
	public Response asignarAlumnoACurso(JsonPack jsonPack){
		try {
			setInstance();
			ServicioAlumno servicioAlumno = new ServicioAlumno();
			//[0]idAlumno [1]idCurso
			boolean resultado = servicioAcademico.asignarAlumnoACurso(servicioAlumno.getUsuario(jsonPack.getValues().elementAt(0)), jsonPack.getValues().elementAt(1));
			return Response.ok(resultado).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Desvincula un Alumno que se encuentre en un Curso no genérico y lo asigna al Curso genérico(sin curso). Todos los objetos involucrados deben estar en estado persistente.<br>
	 * Orden de elementos en JsonPack: <br> [0]id Alumno, <br>[1]Id Curso.
	 * @param jsonPack
	 * @return True si la operación es exitosa.
	 */
	@POST
	@Path("/cur/desvin")
    public Response desvincularAlumnoDeCurso(JsonPack jsonPack){
		try {
			setInstance();
			ServicioAlumno servicioAlumno = new ServicioAlumno();
			boolean resultado = servicioAcademico.desvincularAlumnoDeCurso(servicioAlumno.getUsuario(jsonPack.getValues().elementAt(0)), jsonPack.getValues().elementAt(1));
			return Response.ok(resultado).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
		try {
			setInstance();
			//[0]idMateria [1]idAnio
			return Response.ok(servicioAcademico.asignarMateriaAAnio(
					    	   servicioAcademico.getMateria(jsonPack.getValues().elementAt(0)), 
					           jsonPack.getValues().elementAt(1))).build();
		} catch(Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
		Area area = new Area();
		try {
			setInstance();
			area = servicioAcademico.getArea(id);
			if (area == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(area).build();
		} catch(Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@DELETE
	@Path("/llm/{idL:[0-9][0-9]*}")
	public Response deleteLlamadoById(@PathParam("idL") Long idLlamado) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.deleteLlamado(servicioAcademico.getLlamado(idLlamado))).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@GET /*Borrar si no se usa, tampoco está probado*/
	@Path("/llm/getByDesc/{desc:[a-z]*}")
	public Response getByDescripcion(@PathParam("desc") String descLlamado){
		Llamado llamado = new Llamado();
		try {
			setInstance();
			llamado = servicioAcademico.getLlamado(descLlamado);
			if (llamado == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(llamado).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@GET
	@Path("/llm/{idL:[0-9][0-9]*}")
	public Response getLlamadoById(@PathParam("idL") Long idL) {
		Llamado llamado = new Llamado();
		try {
			setInstance();
			llamado = servicioAcademico.getLlamado(idL);
			if (llamado == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(llamado).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@GET
	@Path("/llm/listAll")
	public Response llamadoListAll() {
		ArrayList<Llamado> llamados = new ArrayList<Llamado>();
		try {
			setInstance();
			llamados = servicioAcademico.getLlamados(new Llamado());
			if (llamados.size() == 0) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(llamados).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@GET
	@Path("/previas/{doc:[0-9][0-9]*}")
	public Response getPrevia(@PathParam("doc") final Long doc) {
		List<DetallePreviaDTO> previas = new ArrayList<DetallePreviaDTO>();
		try {
			setInstance();
			previas = servicioAcademico.getPreviasDesaprobadas(doc);
			if (previas.size() == 0) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No se encontraron previas", FrontMessage.INFO))
						.build();
			}
			return Response.ok(previas).build();
		} catch(Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@GET
	@Path("/histPrevias/{doc:[0-9][0-9]*}")
	public Response getHistorialPrevia(@PathParam("doc") final Long doc) {
		ArrayList<DetallePreviaDTO> previas = new ArrayList<DetallePreviaDTO>();
		try {
			setInstance();
			previas = servicioAcademico.getPrevias(doc);
			if (previas.size() == 0) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(previas).build();
		} catch(Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@PUT
	@Path("/mesa/")
	public Response updateMesa(MesaAltaDTO mesaAltaDTO) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.addMesa(mesaAltaDTO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}

	@DELETE
	@Path("/mes/{idMe:[0-9][0-9]*}")
	public Response deleteMesaById(@PathParam("idMe") Long idMe) {
		try {
			setInstance();
			return Response.ok(servicioAcademico.deleteMesa(servicioAcademico.getMesa(idMe))).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	@GET
	@Path("/mes/{idMe:[0-9][0-9]*}")
	public Response getMesaById(@PathParam("idMe") Long idMe) {
		Mesa mesa = new Mesa();
		try {
			setInstance();
			mesa = servicioAcademico.getMesa(idMe);
			if (mesa == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(mesa).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}

	@GET
	@Path("/mes/listAll")
	public Response mesaListAll() {
		List<Mesa> mesas = new ArrayList<Mesa>();
		try {
			setInstance();
			mesas = servicioAcademico.getMesas(new Mesa());
			if (mesas.size() == 0) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			return Response.ok(mesas).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
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
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Agrega una inscripción a mesa de un alumno.<br>
	 * @param elementos [idMesa, idAlumno]
	 * @return
	 */
	@POST
	@Path("/inscripcion")
	public Response inscribir(final Long[] elementos){
		try{
			setInstance();
			servicioAcademico.addInscripcion(elementos[0],elementos[1]);
		}catch(ValidacionException ex){
			return Response.status(Status.CONFLICT).entity(new FrontMessage(ex.getMessage(),FrontMessage.INFO)).build();
		}
		catch(Exception ex){
			return Response.serverError().entity(ex).build();
		}
		return Response.ok(true).build();
	}	
	
	/**
	 * Elimina una entrada inscripción a mesa de un alumno.<br>
	 * @param elementos [idMesa, idAlumno]
	 * @return
	 */
	@POST
	@Path("/desinscripcion")
	public Response desinscribir(final Long[] elementos){
		try{
			setInstance();
			servicioAcademico.deleteInscripcion(elementos[0],elementos[1]);
		}catch(Exception ex){
			return Response.serverError().entity(ex).build();
		}
		return Response.ok(true).build();
	}
	
	/**
	 * Busca el detalle de las mesas a la cual el alumno puede inscribirse.
	 * @param idAlumno
	 * @return
	 */
	@GET
	@Path("/inscripcion/{idA:[0-9][0-9]*}")
	public Response listarInscribibles(@PathParam("idA") final Long idAlumno){
		try{
			setInstance();
			return Response.ok(servicioAcademico.listarInscribibles(idAlumno)).build();
		}catch(ValidacionException ex){
			return Response.status(Status.CONFLICT).entity(new FrontMessage(ex.getMensajesError().get(0),FrontMessage.INFO)).build();
		}catch(Exception ex){
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity( new FrontMessage("Ocurrió un problema al listar las mesas para inscripción",FrontMessage.CRITICAL)).build();
		}
		
	} 
	
	/**
	 * Agrega o modifica una especialidad.
	 * @param especialidad
	 * @return
	 */
	@PUT
	@Path("/especialidad")
	public Response addEspecialidad(final Especialidad especialidad){
		try{
			setInstance();
			return Response.ok(servicioAcademico.addEspecialidad(especialidad)).build();
		}catch(ValidacionException vEx){
			return Response.status(Status.CONFLICT).entity(new FrontMessage(vEx.getMensajesError().toString(),FrontMessage.INFO)).build();
		}catch(Exception ex){
			return Response.serverError().entity(new FrontMessage("Ocurrió un problema al agregar la especialidad.",FrontMessage.CRITICAL)).build();
		}
	}
	
	/**
	 * Obtiene una especilidad proporcionando su id
	 * @param idEspecialidad
	 * @return
	 */
	@GET
	@Path("/especialidad/{id:[0-9][0-9]*}")
	public Response getEspecialidad(@PathParam("id") final Long idEspecialidad){
		try{
			setInstance();
			return Response.ok(servicioAcademico.getEspecialidadById(idEspecialidad)).build();
		}catch(Exception ex){
			return Response.serverError().entity(new FrontMessage("Ocurrió un problema al intentar obtener la especialidad.",FrontMessage.CRITICAL)).build();
		}
	}
	
	/**
	 * Obtiene todas las especilidades existentes
	 * @return
	 */
	@GET
	@Path("/especialidadListAll")
	public Response getEspecialidades(){
		try{
			setInstance();
			return Response.ok(servicioAcademico.getEspecialidad(new Especialidad(null,null,null))).build();
		}catch(Exception ex){
			return Response.serverError().entity(new FrontMessage("Ocurrió un problema al intentar obtener las especialidades.",FrontMessage.CRITICAL)).build();
		}
	}
	
	/**
	 * Elimina una especialidad.
	 * @param idEspecialidad
	 * @return
	 */
	@DELETE
	@Path("/especialidad/{id:[0-9][0-9]*}")
	public Response deleteEspecialidad(@PathParam("id") final Long idEspecialidad){
		try{
			setInstance();
			return Response.ok(servicioAcademico.deleteEspecialidad(servicioAcademico.getEspecialidadById(idEspecialidad))).build();
		}catch(ValidacionException vEx){
			return Response.status(Status.CONFLICT).entity(new FrontMessage("Especialidad en uso por: "+vEx.getMensajesError().toString(),FrontMessage.INFO)).build();
		}catch(Exception ex){
			return Response.serverError().entity(new FrontMessage("Ocurrió un problema al intentar eliminar la especilidad.",FrontMessage.CRITICAL)).build();
		}
	}
	
	
}
	

