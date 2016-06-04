package ar.com.santalucia.servicio;

import java.util.List;

import ar.com.santalucia.aplicacion.gestor.sistema.configuracion.GestorParametroConfiguracion;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.sistema.configuracion.ParametroConfiguracion;
import ar.com.santalucia.excepciones.ValidacionException;
/**
 * 
 * @author Ariel
 * @version 1.0
 */

public class ServicioConfiguracion {
	private GestorParametroConfiguracion gConfiguracion;

	public ServicioConfiguracion(GestorParametroConfiguracion gConfiguracion) {
		super();
		this.gConfiguracion = gConfiguracion;
	}
	
	public ServicioConfiguracion() throws Exception {
		// TODO Auto-generated constructor stub
		gConfiguracion = new GestorParametroConfiguracion();
	}

	public Boolean addParametro(ParametroConfiguracion parametroConfiguracion) throws Exception{
		try {
			if (parametroConfiguracion.getIdParametroConfiguracion() == null) {
				gConfiguracion.add(parametroConfiguracion);
			}
			else {
				gConfiguracion.modify(parametroConfiguracion);
			}
			
		} 
		catch (Exception ex) {
			throw new Exception("No se pudo dar de alta el parámetro: " + ex.getMessage());
		}
		return true;
	}
	
	public Boolean deleteParametro(ParametroConfiguracion parametroConfiguracion) throws Exception{
		try{
			gConfiguracion.delete(parametroConfiguracion);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar el PARÁMETRO: " + ex.getMessage());
		}
		return true;
	}
	
	public ParametroConfiguracion getParametro(Long idParametro) throws Exception{
		try {
			return gConfiguracion.getById(idParametro);
		} catch(Exception ex) {
			throw new Exception("No se pudo obtener el PARÁMETRO: " + ex.getMessage());
		}
	}
	
	public List<ParametroConfiguracion> getParametros(ParametroConfiguracion parametroConfiguracion) throws Exception{
		try{
			return gConfiguracion.getByExample(parametroConfiguracion);
		}catch(Exception ex){
			throw new Exception("No se pudo obtener el listado de PARÁMETROS: " + ex.getMessage());
		}
	}
}
