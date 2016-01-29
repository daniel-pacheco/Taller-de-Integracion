/**
 * 
 */
package ar.com.santalucia.rest;

import java.util.List;

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

import ar.com.santalucia.dominio.modelo.academico.Inscripcion;
import ar.com.santalucia.dominio.modelo.academico.MesaExamen;
import ar.com.santalucia.servicio.ServicioAcademico;
import ar.com.santalucia.servicio.ServicioAlumno;
import ar.com.santalucia.servicio.ServicioInscripcionMesa;

/**
 * @author Eric
 *
 */
@Path("/sInscripcionMesa")
@Produces({ "application/json" })
@Consumes({ "application/json" })
public class ServicioInscripcionMesaEndpoint {
	
	private ServicioInscripcionMesa servicioInscripcionMesa = null;
	
	/**
	 * Instancia un objeto ServicioInscripionMesa si no existe
	 * @throws Exception
	 */
	private void setInstance() throws Exception {
		if (servicioInscripcionMesa == null) {
			try {
				servicioInscripcionMesa = new ServicioInscripcionMesa();
			} catch (Exception ex) {
				throw ex;
			}
		}
	}

	
	@PUT
	@Path("/insc/")
	public Response updateInscripcion(Inscripcion inscripcion) {
		try {
			setInstance();
			servicioInscripcionMesa.addInscripcion(inscripcion);
			return Response.ok(inscripcion.getIdInscripcion()).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@DELETE
	@Path("/insc/{id:[0-9][0-9]*}")
	public Response deleteInscripcionById(@PathParam("id") Long idInscripcion) {
		try {
			setInstance();
			return Response.ok(servicioInscripcionMesa.deleteInscripcion(
								servicioInscripcionMesa.getInscripcion(idInscripcion))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@GET
	@Path("/insc/{id:[0-9][0-9]*}")
	public Response getInscripcionById(@PathParam("id") Long idInscripcion) {
		try {
			setInstance();
			return Response.ok(servicioInscripcionMesa.getInscripcion(idInscripcion)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@GET
	@Path("/insc/listAll")
	public Response inscripcionListAll() {
		try {
			setInstance();
			Inscripcion inscripcion = new Inscripcion();
			return Response.ok(servicioInscripcionMesa.getInscripciones(inscripcion)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * Inscribe un alumno. Este método llama a inscribirAlumno(...) (paso 1) y asignarMesaAInscripcion(...) (paso 2)
	 * @param jsonPack [idAlumno, idInscripcion, idMesa]
	 * @return
	 */
	@POST
	@Path("/aluInsc/")
	public Response inscribirAlumno(JsonPack jsonPack) {
		try {
			setInstance();
			ServicioAlumno servicioAlumno = new ServicioAlumno();
			ServicioAcademico servicioAcademico = new ServicioAcademico();
			
			Boolean resPaso1 = false;
			Boolean resPaso2 = false;
			//Paso 1: inscribe el alumno (lo asigna a la inscripcion)
			resPaso1 = servicioInscripcionMesa.inscribirAlumno(
						servicioAlumno.getUsuario(jsonPack.getValues().elementAt(0)), 
						jsonPack.getValues().elementAt(1));
			//Paso 2: asigna la mesa a la inscripcion
			resPaso2 = servicioInscripcionMesa.asignarMesaAInscripcion(
						servicioAcademico.getMesa(jsonPack.getValues().elementAt(2)), 
						jsonPack.getValues().elementAt(1));
			
			return Response.ok(resPaso1 && resPaso2).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	
	@PUT
	@Path("/exam/")
	public Response updateMesaExamen(MesaExamen mesaExamen) {
		try {
			setInstance();
			servicioInscripcionMesa.addMesaExamen(mesaExamen);
			return Response.ok(mesaExamen.getIdMesaExamen()).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@DELETE
	@Path("/exam/{id:[0-9][0-9]*}")
	public Response deleteMesaExamen(@PathParam("id") Long idMesaExamen) {
		try {
			setInstance();
			return Response.ok(servicioInscripcionMesa.deleteMesaExamen(
								servicioInscripcionMesa.getMesaExamen(idMesaExamen))).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@GET
	@Path("/exam/{id:[0-9][0-9]*}")
	public Response getMesaExamenById(@PathParam("id") Long idMesaExamen) {
		try {
			setInstance();
			return Response.ok(servicioInscripcionMesa.getMesaExamen(idMesaExamen)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	@GET
	@Path("/exam/listAll/")
	public Response mesaExamenListAll() {
		try {
			setInstance();
			MesaExamen mesaExamen = new MesaExamen();
			return Response.ok(servicioInscripcionMesa.listMesaExamen(mesaExamen)).build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
	/**
	 * Registra cuando el alumno va o no a rendir.
	 * Si se inscribió a la mesa y se presenta a rendir, se pone la nota correspondiente. Sino, se pone la falta.
	 * 
	 * Paso 0: crear mesaExamen
	 * Paso 1: asignarInscripcionAMesaExamen(...)
	 * Paso 2: inscripcion con asistencia true -> sigue paso 3
	 * Paso 3: crear nota
	 * Paso 4: asignarNotaAMesaExamen(...) -> esto ya asigna la materia a la nota
	 * 
	 * @param jsonPack [idInscripcion, idMesaExamen, ]
	 * @return
	 */
	@POST
	@Path("/aluRinde/")
	public Response alumnoRinde(JsonPack jsonPack) {
		try {
			// obtengo la mesaExamen para poder después comprobar su asistencia, de paso la uso para asignarle la inscripción
			Inscripcion inscripcion = servicioInscripcionMesa.getInscripcion(jsonPack.getValues().elementAt(0));
			MesaExamen mesaExamen = servicioInscripcionMesa.getMesaExamen(jsonPack.getValues().elementAt(1));			
			
			Boolean resPaso1 = servicioInscripcionMesa.asignarInscripcionAMesaExamen(inscripcion, mesaExamen.getIdMesaExamen());
			
			if (inscripcion.getAsistencia() == true) {
				
			}
			
			return Response.ok().build();
		} catch (Exception ex) {
			return Response.ok(ex).build();
		}
	}
	
}
