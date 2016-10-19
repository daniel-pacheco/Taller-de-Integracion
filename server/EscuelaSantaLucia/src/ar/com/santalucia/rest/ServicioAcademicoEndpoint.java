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
import javax.ws.rs.HeaderParam;
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
import ar.com.santalucia.dominio.dto.MesaDTO;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Area;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Especialidad;
import ar.com.santalucia.dominio.modelo.academico.Llamado;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.academico.Mesa;
import ar.com.santalucia.dominio.modelo.sistema.login.Login;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.servicio.ServicioAcademico;
import ar.com.santalucia.servicio.ServicioAlumnadoAcademico;
import ar.com.santalucia.servicio.ServicioAlumno;
import ar.com.santalucia.servicio.ServicioDocente;
import ar.com.santalucia.servicio.ServicioLlamadoAcademico;
import ar.com.santalucia.servicio.ServicioLogin;

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
	private ServicioAlumnadoAcademico servicioAlumnadoAcademico = null;
	private ServicioLlamadoAcademico servicioLlamadoAcademico = null;
	
	/**
	 * Instancia un objeto ServicioAcademico si no existe
	 * @throws Exception
	 */
	private void setInstance() throws Exception {
		if (servicioAcademico == null) {
			try {
				servicioAcademico = new ServicioAcademico();
				servicioAlumnadoAcademico = new ServicioAlumnadoAcademico();
				servicioLlamadoAcademico = new ServicioLlamadoAcademico();
			} catch (Exception ex) {
				throw ex;
			}
		}
	}

	/**
	 * Rol de acceso: DIRECTIVO
	 * Agrega o modifica un objeto de tipo Anio. Use este método cuando desee agregar un nuevo curso (SÓLO SI ANIO TAMBIÉN ES NUEVO).<br>
	 * El gestor de Anio cuenta con la lógica necesaria para detectar objetos Curso nuevos y agregarlos. No se realiza validaciones de nombre de Curso.<br>
	 * ATENCIÓN: Es posible modificar sólo los atributos propios del Anio con este método. Para modificar o eliminar Curso y Materia, utilice los métodos correspondientes.
	 * @param anio Objeto Anio que se desea agregar o modificar. Si se trata de una modificación, cargue los atributos de arreglos con null.
	 * @return Id de anio si la operación es exitosa.
	 */
	@PUT
	@Path("/anio/")
	public Response updateAnio(Anio anio,
			@HeaderParam("rol") final String rolIn, 
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
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
			servicioAcademico.addAnio(anio);
			if (nuevoToken == null) {
				return Response.ok(anio.getIdAnio()).build();
			} else {
				return Response.ok(anio.getIdAnio()).header("auth0", nuevoToken).build();
			}
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
	 * Rol de acceso: DIRECTIVO
	 * @param id Identificador de anio que se desea eliminar.
	 * @return True si la operación es exitosa.
	 */
	@DELETE
	@Path("/anio/{id:[0-9][0-9]*}")
	public Response deleteAnioById(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		Boolean exito = false;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			exito = servicioAcademico.deleteAnio(servicioAcademico.getAnio(id));
			if (nuevoToken == null) {
				return Response.ok(exito).build();
			} else {
				return Response.ok(exito).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}

	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * @param id Identificador de anio que se desea obtener.
	 * @return Objeto tipo Anio.
	 */
	@GET
	@Path("/anio/{id:[0-9][0-9]*}")
	public Response getAnioById(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		Anio anio = new Anio();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			anio = servicioAcademico.getAnio(id);
			if (anio == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(anio).build();
			} else {
				return Response.ok(anio).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * Lista todos los objetos de tipo Anio existente.
	 * @return Lista de Anios.
	 */
	@GET
	@Path("/anio/listAll")
	public Response aniolistAll(@HeaderParam("rol") final String rolIn, @HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		List<Anio> anios = new ArrayList<Anio>();
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			anios = servicioAcademico.getAnios(new Anio());
			if (anios.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(anios).build();
			} else {
				return Response.ok(anios).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@GET
	@Path("/anio/listAllMin")
	public Response getAniosDTO(@HeaderParam("rol") final String rolIn, @HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.DOCENTE)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		ArrayList<AnioDTO> aniosDTO = new ArrayList<AnioDTO>();
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			aniosDTO = servicioAcademico.getAniosDTO();
			if (aniosDTO.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(aniosDTO).build();
			} else {
				return Response.ok(aniosDTO).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			//return Response.ok(ex).build();
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Hubo un error al obtener el listado resumido de años: " + ex.getMessage(), FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
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
	 * Rol de acceso: DIRECTIVO
	 * @param idC Identificador del objeto Curso que se desea eliminar.
	 * @return True si la operación es exitosa.
	 */
	@DELETE
	@Path("/cur/{idC:[0-9][0-9]*}")
	public Response deleteCursoById(@PathParam("idC") final Long idC,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		Boolean exito = false;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			exito = servicioAcademico.deleteCurso(servicioAcademico.getCurso(idC));
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
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * @param id Identificador del curso que se desea obtener.
	 * @return Objeto de tipo Curso.
	 */
	@GET
	@Path("/cur/{id:[0-9][0-9]*}")
	public Response getCursoById(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		Curso curso = new Curso();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			curso = servicioAcademico.getCurso(id);
			if (curso == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(curso).build();
			} else {
				return Response.ok(curso).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}		
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * Agregar o modifica un objeto de tipo Materia.
	 * @param materia Objeto de tipo Materioas que se desea agregar o modificar.
	 * @return Identificador de Materia.
	 */
	@PUT
	@Path("/mat/")
	public Response updateMateria(MateriaAltaDTO materiaAltaDTO,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
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
			servicioAcademico.addMateria(materiaAltaDTO);
			if (nuevoToken == null) {
				return Response.ok(materiaAltaDTO.getIdMateria()).build();
			} else {
				return Response.ok(materiaAltaDTO.getIdMateria()).header("auth0", nuevoToken).build();
			}
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
	 * Rol de acceso: DIRECTIVO
	 * @param id Identificador del objeto de tipo Materia que se desea eliminar.
	 * @return True si la operación es exitosa.
	 */
	@DELETE
	@Path("/mat/{id:[0-9][0-9]*}")
	public Response deleteMateriaById(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		Boolean exito = false;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			exito = servicioAcademico.deleteMateria(servicioAcademico.getMateria(id));
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
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * @param id Identificador del objeto de tipo Materia que se desea obtener.
	 * @return Objeto de tipo Materia.
	 */
	@GET
	@Path("/mat/{id:[0-9][0-9]*}")
	public Response getMateriaById(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Materia materia = new Materia();
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			materia = servicioAcademico.getMateria(id);
			if (materia == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(materia).build();
			} else {
				return Response.ok(materia).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * @return
	 */
	@GET
	@Path("/mat/listAllMin")
	public Response getMateriasDTO(@HeaderParam("rol") final String rolIn, @HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.DOCENTE)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		ArrayList<MateriaDTO> materiasDTO = new ArrayList<MateriaDTO>();
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			materiasDTO = servicioAcademico.getMateriasDTO();
			if (materiasDTO.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(materiasDTO).build();
			} else {
				return Response.ok(materiasDTO).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
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
	 * Rol de acceso: DIRECTIVO
	 * @param area Objeto de tipo Area que se desea crear o modificar.
	 * @return Identificador de Area.
	 */
	@PUT
	@Path("/area")
	public Response updateArea(Area area,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token){
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			servicioAcademico.addArea(area);
			if (nuevoToken == null) {
				return Response.ok(area.getIdArea()).build();
			} else {
				return Response.ok(area.getIdArea()).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch(Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
		
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * Lista los objetos de tipo Area disponibles.
	 * @return Lista de objetos tipo Area.
	 */
	@GET
	@Path("/area/listAll")
	public Response areaListAll(@HeaderParam("rol") final String rolIn, @HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		ArrayList<Area> areas = new ArrayList<Area>();
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			areas = servicioAcademico.getAreas(new Area());
			if (areas.size() == 0) {
				return Response.status(Status.NO_CONTENT)
						.entity(new FrontMessage("Sin resultados", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(areas).build();
			} else {
				return Response.ok(areas).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch(Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * @param idArea Identificador del objeto tipo Area que se desea eliminar.
	 * @return True si la operación es exitosa.
	 */
	@DELETE
	@Path("/area/{id:[0-9][0-9]*}")
	public Response deleteArea(@PathParam("id") final Long idArea,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Area area = new Area();
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			area = servicioAcademico.getArea(idArea);
			if (area == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("Elemento a eliminar no encontrado", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(servicioAcademico.deleteArea(area)).build();
			} else {
				return Response.ok(servicioAcademico.deleteArea(area)).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
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
			boolean resultado = servicioAlumnadoAcademico.asignarAlumnoACurso(servicioAlumno.getUsuario(jsonPack.getValues().elementAt(0)), jsonPack.getValues().elementAt(1));
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
			boolean resultado = servicioAlumnadoAcademico.desvincularAlumnoDeCurso(servicioAlumno.getUsuario(jsonPack.getValues().elementAt(0)), jsonPack.getValues().elementAt(1));
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
	 * Rol de acceso: DIRECTIVO
	 * @param id Identificador del objeto tipo Area que se desea obtener.
	 * @return Objeto de tipo Area.
	 */
	@GET
	@Path("/area/{id:[0-9][0-9]*}")
	public Response getAreaById(@PathParam("id") final Long id,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("token") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		Area area = new Area();
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			area = servicioAcademico.getArea(id);
			if (area == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(area).build();
			} else {
				return Response.ok(area).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch(Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}

	/**
	 * Rol de acceso: DIRECTIVO
	 * @param llamado
	 * @return
	 */
	@PUT
	@Path("/llm/")
	public Response updateLlamado(Llamado llamado,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			servicioLlamadoAcademico.addLlamado(llamado);
			if (nuevoToken == null) {
				return Response.ok(llamado.getIdLlamado()).build();
			} else {
				return Response.ok(llamado.getIdLlamado()).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * @param idLlamado
	 * @return
	 */
	@DELETE
	@Path("/llm/{idL:[0-9][0-9]*}")
	public Response deleteLlamadoById(@PathParam("idL") Long idLlamado,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		
		try {
			setInstance();
			return Response.ok(servicioLlamadoAcademico.deleteLlamado(servicioLlamadoAcademico.getLlamado(idLlamado))).build();
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
			llamado = servicioLlamadoAcademico.getLlamado(descLlamado);
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
			llamado = servicioLlamadoAcademico.getLlamado(idL);
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
			llamados = servicioLlamadoAcademico.getLlamados(new Llamado());
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
	
	/**
	 * Rol de acceso: DIRECTIVO - ALUMNO
	 * @param doc
	 * @return
	 */
	@GET
	@Path("/previas/{doc:[0-9][0-9]*}")
	public Response getPrevia(@PathParam("doc") final Long doc,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ALUMNO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		List<DetallePreviaDTO> previas = new ArrayList<DetallePreviaDTO>();
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			previas = servicioLlamadoAcademico.getPreviasDesaprobadas(doc);
			if (previas.size() == 0) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No se encontraron previas", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(previas).build();
			} else {
				return Response.ok(previas).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch(Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * @param doc
	 * @return
	 */
	@GET
	@Path("/histPrevias/{doc:[0-9][0-9]*}")
	public Response getHistorialPrevia(@PathParam("doc") final Long doc,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		ArrayList<DetallePreviaDTO> previas = new ArrayList<DetallePreviaDTO>();
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			previas = servicioLlamadoAcademico.getPrevias(doc);
			if (previas.size() == 0) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(previas).build();
			} else {
				return Response.ok(previas).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch(Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}

	/** 
	 * Rol de acceso: DIRECTIVO
	 * @param mesaAltaDTO
	 * @return
	 */
	@PUT
	@Path("/mesa/")
	public Response updateMesa(MesaAltaDTO mesaAltaDTO,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			if (nuevoToken == null) {
				return Response.ok(servicioLlamadoAcademico.addMesa(mesaAltaDTO)).build();
			} else {
				return Response.ok(servicioLlamadoAcademico.addMesa(mesaAltaDTO)).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
//	/**
//	 * Rol de acceso: DIRECTIVO
//	 * @param mesa
//	 * @return
//	 */
//	@PUT
//	@Path("/mes/")
//	public Response updateMesa(Mesa mesa,
//			@HeaderParam("rol") final String rolIn,
//			@HeaderParam("auth0") final String token) {
//		if (!rolIn.equals(Login.DIRECTIVO)) {
//			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
//		}
//		String nuevoToken = new String();
//		try {
//			setInstance();
//			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
//			servicioAcademico.addMesa(mesa);
//			if (nuevoToken == null) {
//				return Response.ok(mesa.getIdMesa()).build();
//			} else {
//				return Response.ok(mesa.getIdMesa()).header("auth0", nuevoToken).build();
//			}
//		} catch (ValidacionException vEx) {
//			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
//		} catch (Exception ex) {
//			// TODO: volcar 'ex' en LOG y/o mostrar por consola
//			return Response.status(Status.INTERNAL_SERVER_ERROR)
//					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
//							FrontMessage.CRITICAL))
//					.build();
//		}
//	}

	/**
	 * Rol de acceso: DIRECTIVO
	 * @param idMe
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@DELETE
	@Path("/mes/{idMe:[0-9][0-9]*}")
	public Response deleteMesaById(@PathParam("idMe") Long idMe,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		String nuevoToken = new String();
		Boolean exito = false;
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			exito = servicioLlamadoAcademico.deleteMesa(servicioLlamadoAcademico.getMesa(idMe));
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
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}

	/**
	 * Rol de acceso: DIRECTIVO - ALUMNO
	 * @param idMe
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@GET
	@Path("/mesDTO/{idMe:[0-9][0-9]*}")
	public Response getMesaDTOById(@PathParam("idMe") Long idMe,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ALUMNO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		MesaDTO mesaDTO = new MesaDTO();
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			mesaDTO = servicioLlamadoAcademico.getMesaDTO(idMe);
			if (mesaDTO == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(mesaDTO).build();
			} else {
				return Response.ok(mesaDTO).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO - ALUMNO
	 * @param idMe
	 * @param rolIn
	 * @param token
	 * @return
	 */
	@GET
	@Path("/mes/{idMe:[0-9][0-9]*}")
	public Response getMesaById(@PathParam("idMe") Long idMe,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ALUMNO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		Mesa mesa = new Mesa();
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			mesa = servicioLlamadoAcademico.getMesa(idMe);
			if (mesa == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
						.build();
			}
			if (nuevoToken == null) {
				return Response.ok(mesa).build();
			} else {
				return Response.ok(mesa).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch (Exception ex) {
			// TODO: volcar 'ex' en LOG y/o mostrar por consola
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
							FrontMessage.CRITICAL))
					.build();
		}
	}

//	/**
//	 * Rol de acceso: DIRECTIVO 
//	 * @param rolIn
//	 * @param token
//	 * @return
//	 */
//	@GET
//	@Path("/mes/listAll")
//	public Response mesaListAll(@HeaderParam("rol") final String rolIn, @HeaderParam("auth0") final String token) {
//		if (!rolIn.equals(Login.DIRECTIVO)) {
//			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
//		}
//		List<Mesa> mesas = new ArrayList<Mesa>();
//		String nuevoToken = new String();
//		try {
//			setInstance();
//			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
//			mesas = servicioAcademico.getMesas(new Mesa());
//			if (mesas.size() == 0) {
//				return Response.status(Status.NOT_FOUND)
//						.entity(new FrontMessage("No encontrado", FrontMessage.INFO))
//						.build();
//			}
//			if (nuevoToken == null) {
//				return Response.ok(mesas).build();
//			} else {
//				return Response.ok(mesas).header("auth0", nuevoToken).build();
//			}
//		} catch (ValidacionException vEx) {
//			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
//		} catch (Exception ex) {
//			// TODO: volcar 'ex' en LOG y/o mostrar por consola
//			return Response.status(Status.INTERNAL_SERVER_ERROR)
//					.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operación más tarde.", 
//							FrontMessage.CRITICAL))
//					.build();
//		}
//	}

	/*@POST
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
	}*/
	
	@POST
	@Path("/mes/asignMat")
	public Response asignarMateriaAMesa(JsonPack jsonPack) {
		try {
			setInstance();
			return Response.ok(servicioLlamadoAcademico.asignarMateriaAMesa(
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
			return Response.ok(servicioLlamadoAcademico.desvincularMateriaDeMesa(
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
	
	/** TEST MODE
	 * Rol de acceso: DIRECTIVO - ALUMNO
	 * Agrega una inscripción a mesa de un alumno.<br>
	 * @param elementos [idMesa, idAlumno]
	 * @return
	 */
	@POST
	@Path("/inscripcion")
	public Response inscribir(final Long[] elementos,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ALUMNO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		String nuevoToken = new String();
		Boolean exito = false;
		try {
			setInstance();
			/*try {
				nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			} catch (ValidacionException vEx) {
				return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
			}*/
			exito = servicioLlamadoAcademico.addInscripcion(elementos[0],elementos[1]);
			if (nuevoToken == null) {
				return Response.ok(exito).build();
			} else {
				return Response.ok(exito).header("auth0", nuevoToken).build();
			}
		} catch(ValidacionException ex) {
			return Response.status(Status.CONFLICT).entity(new FrontMessage(ex.getMessage(),FrontMessage.INFO)).build();
		} catch(Exception ex) {
			return Response.serverError().entity(ex).build();
		}
	}	
	
	/**
	 * Rol de acceso: DIRECTIVO - ALUMNO
	 * Elimina una entrada inscripción a mesa de un alumno.<br>
	 * @param elementos [idMesa, idAlumno]
	 * @return
	 */
	@POST
	@Path("/desinscripcion")
	public Response desinscribir(final Long[] elementos,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ALUMNO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		String nuevoToken = new String(); nuevoToken = null;
		Boolean exito = false;
		try {
			setInstance();
			/*try {
				nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			} catch (ValidacionException vEx) {
				return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
			}*/
			exito = servicioLlamadoAcademico.deleteInscripcion(elementos[0],elementos[1]);
			if (nuevoToken == null) {
				return Response.ok(exito).build();
			} else {
				return Response.ok(exito).header("auth0", nuevoToken).build();
			}
		} catch(ValidacionException ex) {
			return Response.status(Status.CONFLICT).entity(new FrontMessage(ex.getMessage(),FrontMessage.INFO)).build();
		} catch(Exception ex) {
			return Response.serverError().entity(ex).build();
		}
	}
	
	/** TEST MODE
	 * Rol de acceso: DIRECTIVO - ALUMNO
	 * Busca el detalle de las mesas a la cual el alumno puede inscribirse.
	 * @param dniAlumno
	 * @return
	 */
	@GET
	@Path("/inscripcion/{idA:[0-9][0-9]*}")
	public Response listarInscribibles(@PathParam("idA") final Long dniAlumno,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO) && !rolIn.equals(Login.ALUMNO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		String nuevoToken = null;//new String();
		try {
			setInstance();
			/*try {
				nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			} catch (ValidacionException vEx) {
				return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
			}*/
			if (nuevoToken == null) {
				return Response.ok(servicioLlamadoAcademico.listarInscribiblesV2(dniAlumno)).build();
			} else {
				return Response.ok(servicioLlamadoAcademico.listarInscribiblesV2(dniAlumno)).header("auth0", nuevoToken).build();
			}
		} catch(ValidacionException ex) {
			return Response.status(Status.CONFLICT).entity(new FrontMessage(ex.getMensajesError().get(0),FrontMessage.INFO)).build();
		} catch(Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity( new FrontMessage("Ocurrió un problema al listar las mesas para inscripción",FrontMessage.CRITICAL)).build();
		}
	} 
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * Agrega o modifica una especialidad.
	 * @param especialidad
	 * @return
	 */
	@PUT
	@Path("/especialidad")
	public Response addEspecialidad(final Especialidad especialidad,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		String nuevoToken = new String();
		try {
			setInstance();
			try {
				nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			} catch (ValidacionException vEx) {
				return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
			}
			if (nuevoToken == null) {
				return Response.ok(servicioAcademico.addEspecialidad(especialidad)).build();
			} else {
				return Response.ok(servicioAcademico.addEspecialidad(especialidad)).header("auth0", nuevoToken).build();
			}
		} catch(ValidacionException vEx) {
			return Response.status(Status.CONFLICT).entity(new FrontMessage(vEx.getMensajesError().toString(),FrontMessage.INFO)).build();
		} catch(Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ocurrió un problema al agregar la especialidad.",FrontMessage.CRITICAL)).build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * Obtiene una especilidad proporcionando su id
	 * @param idEspecialidad
	 * @return
	 */
	@GET
	@Path("/especialidad/{id:[0-9][0-9]*}")
	public Response getEspecialidad(@PathParam("id") final Long idEspecialidad,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		String nuevoToken = new String();
		try {
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			if (nuevoToken == null) {
				return Response.ok(servicioAcademico.getEspecialidadById(idEspecialidad)).build();
			} else {
				return Response.ok(servicioAcademico.getEspecialidadById(idEspecialidad)).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch(Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ocurrió un problema al intentar obtener la especialidad.", FrontMessage.CRITICAL)).build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * Obtiene todas las especilidades existentes
	 * @return
	 */
	@GET
	@Path("/especialidadListAll")
	public Response getEspecialidades(@HeaderParam("rol") final String rolIn, @HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		String nuevoToken = new String();
		Boolean exito = false;
		try {
			setInstance();
			try {
				nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			} catch (ValidacionException vEx) {
				return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
			}
			if (nuevoToken == null) {
				return Response.ok(servicioAcademico.getEspecialidad(new Especialidad(null, null, null))).build();
			} else {
				return Response.ok(servicioAcademico.getEspecialidad(new Especialidad(null, null, null))).header("auth0", nuevoToken).build();
			}
		} catch(Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ocurrió un problema al intentar obtener las especialidades.",FrontMessage.CRITICAL)).build();
		}
	}
	
	/**
	 * Rol de acceso: DIRECTIVO
	 * Elimina una especialidad.
	 * @param idEspecialidad
	 * @return
	 */
	@DELETE
	@Path("/especialidad/{id:[0-9][0-9]*}")
	public Response deleteEspecialidad(@PathParam("id") final Long idEspecialidad,
			@HeaderParam("rol") final String rolIn,
			@HeaderParam("auth0") final String token) {
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		String nuevoToken = new String();
		Boolean exito = false;
		try {
			setInstance();
			try {
				nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			} catch (ValidacionException vEx) {
				return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
			}
			exito = servicioAcademico.deleteEspecialidad(servicioAcademico.getEspecialidadById(idEspecialidad));
			if (nuevoToken == null) {
				return Response.ok(exito).build();
			} else {
				return Response.ok(exito).header("auth0", nuevoToken).build();
			}
		} catch(ValidacionException vEx) {
			return Response.status(Status.CONFLICT).entity(new FrontMessage("Especialidad en uso por: "+vEx.getMensajesError().toString(),FrontMessage.INFO)).build();
		} catch(Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ocurrió un problema al intentar eliminar la especilidad.",FrontMessage.CRITICAL)).build();
		}
	}
	
	@GET
	@Path("/listLlamadosMin")
	public Response listarLlamadosMin(
		@HeaderParam("rol") final String rolIn,
		@HeaderParam("auth0") final String token){
		if (!rolIn.equals(Login.DIRECTIVO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		String nuevoToken = new String();
		try{
			setInstance();
			nuevoToken = ServicioLogin.comprobarCredenciales(rolIn, token);
			if (nuevoToken == null) {
				return Response.ok(servicioLlamadoAcademico.listarLlamados()).build();
			} else {
				return Response.ok(servicioLlamadoAcademico.listarLlamados()).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch(Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ocurrió un problema al intentar listar los llamados.",FrontMessage.CRITICAL)).build();
		}
	}
	
	@GET
	@Path("/getFiltrosCalificacion")
	public Response getFiltrosCalificacion(
		@HeaderParam("rol") final String rolIn,
		@HeaderParam("auth0") final String token){
		if (!rolIn.equals(Login.ALUMNO)) {
			return Response.status(Status.FORBIDDEN).entity(new FrontMessage("Acceso no autorizado", FrontMessage.INFO)).build();
		}
		String nuevoToken = new String();
		try{
			setInstance();
			nuevoToken = null; //ServicioLogin.comprobarCredenciales(rolIn, token);
			if (nuevoToken == null) {
				return Response.ok(servicioLlamadoAcademico.obtenerListadoMenuActaVolante()).build();
			} else {
				return Response.ok(servicioLlamadoAcademico.obtenerListadoMenuActaVolante()).header("auth0", nuevoToken).build();
			}
		} catch (ValidacionException vEx) {
			return Response.status(Status.UNAUTHORIZED).entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO)).build();
		} catch(Exception ex) {
			return Response.serverError().entity(new FrontMessage("Ocurrió un problema al intentar listar los llamados.",FrontMessage.CRITICAL)).build();
		}
	}
	
	@GET
	@Path("/getActaVolante/{idAV:[0-9][0-9]*}")
	public Response getActaVolanteDTO(@PathParam("idAV") final Long idActaVolante) {
		try {
			setInstance();
			return Response.ok(servicioLlamadoAcademico.getActaVolanteDTO(idActaVolante)).build();
		} catch (ValidacionException e) {
			return Response.serverError().build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
	
	@POST
	@Path("/generarCbteInscripcion/")
	public Response generarComprobanteInscripcionMesa(final Long[] datos) {
		try {
			setInstance();
			return Response.ok(servicioLlamadoAcademico.generarComprobanteInscripcionMesa(datos[0], datos[1], datos[2])).build();
		} catch (Exception ex) {
			return Response.serverError().build();
		}
	}
}
	

