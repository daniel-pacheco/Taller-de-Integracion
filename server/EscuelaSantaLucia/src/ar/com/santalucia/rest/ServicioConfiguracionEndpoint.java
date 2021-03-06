/**
 * 
 */
package ar.com.santalucia.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import ar.com.santalucia.dominio.modelo.sistema.configuracion.ParametroConfiguracion;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.servicio.Inicializador;
import ar.com.santalucia.servicio.ServicioConfiguracion;

/**
 * @author Ariel
 *
 */
@Path("/sConfiguracion")
@Produces("application/json")
@Consumes("application/json")
public class ServicioConfiguracionEndpoint {

	private ServicioConfiguracion servicioConfiguracion = null;

	private void setInstance() throws Exception {
		if (servicioConfiguracion == null) {
			try {
				servicioConfiguracion = new ServicioConfiguracion();
			} catch (Exception ex) {
				throw ex;
			}
		}
	}
	
	@GET
	@Path("/parametro/{id:[0-9][0-9]*}")
	public Response getParametroById(@PathParam("id") final Long id){
		ParametroConfiguracion parametro = new ParametroConfiguracion();
		try{
			setInstance();
			parametro = servicioConfiguracion.getParametro(id);
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
		return Response.ok(parametro).build();
	}
	
	@PUT
	@Path("/parametro/")
	public Response updateParametro(final ParametroConfiguracion parametro){
		try{
			setInstance();
			servicioConfiguracion.addParametro(parametro);
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
		return Response.ok(parametro.getIdParametroConfiguracion()).build();
	}
	
	@GET
	@Path("/parametroListAll/")
	public Response getParametroById(){
		List<ParametroConfiguracion> parametros = new ArrayList<ParametroConfiguracion>();
		try{
			setInstance();
			parametros = servicioConfiguracion.listAllParametros();
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
		return Response.ok(parametros).build();
	}
	
	@DELETE
	@Path("/parametro/{id:[0-9][0-9]*}")
	public Response deleteParametroById(@PathParam("id") final Long id){
		try{
			setInstance();
			return Response.ok(servicioConfiguracion.deleteParametro(servicioConfiguracion.getParametro(id))).build();
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
	}
	
	@PUT
	@Path("/parametro/addAll")
	public Response updateAllParametros(final ArrayList<ParametroConfiguracion> parametros){
		try{
			setInstance();
			servicioConfiguracion.addParametros(parametros);
			return Response.ok(true).build();
		}catch(Exception ex){
			return Response.ok(ex).build();
		}
	}
	
	@GET
	@Path("/inicializarLog")
	public Response inicializarLog(){
		try{
			setInstance();
			Inicializador inicia = new Inicializador();
			inicia.inicializarLog();
			//Logger.getLogger(getClass().getName()).log(Level.INFO,"Se inici� el registro de log con �xito"); // Registro de log
			return Response.ok().build();
		}catch(Exception ex){
			return Response.serverError().entity(ex).build();
		}
	}
	
	@POST
	@Path("/backup/run")
	public Response generarBackup() {
		try {
			setInstance();
			return Response.ok(servicioConfiguracion.generarBackup()).build();
		} catch (ValidacionException vEx) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO))
					.build();
		} catch (Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
						FrontMessage.CRITICAL))
				.build();
		}
	}
	
	@POST
	@Path("/backup/restore")
	public Response restaurarBackup(String path) {
		try {
			setInstance();
			// ruta que se tiene que pasar como par�metro
			// String ruta = "C:/backup/backup_escuelabd_2016-07-08_00-08.backup";
			return Response.ok(servicioConfiguracion.restaurarBase_bat(path)).build();
		} catch (ValidacionException vEx) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new FrontMessage(vEx.getMessage(), FrontMessage.INFO))
					.build();
		} catch (Exception ex) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(new FrontMessage("Ha ocurrido un problema interno. Vuelva a intentar la operaci�n m�s tarde.", 
						FrontMessage.CRITICAL))
				.build();
		}
	}
	
}
