package ar.com.santalucia.servicio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
			throw new Exception("No se pudo dar de alta el par�metro: " + ex.getMessage());
		}
		return true;
	}
	
	/**
	 * Agrega varios par�metros a la vez
	 * @param parametrosConfig
	 * @return
	 */
	public Boolean addParametros(List<ParametroConfiguracion> parametrosConfig) throws Exception{
		validarFechasTrimestre(parametrosConfig);
		for(ParametroConfiguracion pc : parametrosConfig){
			addParametro(pc);
		}
		return true;
	}
	
	public Boolean deleteParametro(ParametroConfiguracion parametroConfiguracion) throws Exception{
		try{
			gConfiguracion.delete(parametroConfiguracion);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar el PAR�METRO: " + ex.getMessage());
		}
		return true;
	}
	
	public ParametroConfiguracion getParametro(Long idParametro) throws Exception{
		try {
			return gConfiguracion.getById(idParametro);
		} catch(Exception ex) {
			throw new Exception("No se pudo obtener el PAR�METRO: " + ex.getMessage());
		}
	}
	
	public List<ParametroConfiguracion> getParametros(ParametroConfiguracion parametroConfiguracion) throws Exception{
		try{
			return gConfiguracion.getByExample(parametroConfiguracion);
		}catch(Exception ex){
			throw new Exception("No se pudo obtener el listado de PAR�METROS: " + ex.getMessage());
		}
	}
	
	/**
	 * Valida la coherencia de las fechas de los trimestres comprendidas en el a�o academico 
	 */
	private void validarFechasTrimestre(List<ParametroConfiguracion> parametrosConfig) throws Exception{
		/* Capturo los par�metros de fecha que me interesan para validar*/
		Calendar COMIENZO_ACADEMICO = Calendar.getInstance();
		Calendar FIN_ACADEMICO      = Calendar.getInstance();
		Calendar COMIENZO_TRIM_1    = Calendar.getInstance();
		Calendar FIN_TRIM_1         = Calendar.getInstance();
		Calendar COMIENZO_TRIM_2    = Calendar.getInstance();
		Calendar FIN_TRIM_2         = Calendar.getInstance();
		Calendar COMIENZO_TRIM_3    = Calendar.getInstance();
		Calendar FIN_TRIM_3         = Calendar.getInstance();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		for(ParametroConfiguracion pc : parametrosConfig){
			switch (pc.getNombre()) {
			case "COMIENZO_ACADEMICO":
				COMIENZO_ACADEMICO.setTime(formatoFecha.parse(pc.getValor()));
			break;
			case "FIN_ACADEMICO":
				FIN_ACADEMICO.setTime(formatoFecha.parse(pc.getValor()));	
			break;
			case "COMIENZO_TRIM_1":
				COMIENZO_TRIM_1.setTime(formatoFecha.parse(pc.getValor()));	
			break;
			case "FIN_TRIM_1":
				FIN_TRIM_1.setTime(formatoFecha.parse(pc.getValor()));	
			break;
			case "COMIENZO_TRIM_2":
				COMIENZO_TRIM_2.setTime(formatoFecha.parse(pc.getValor()));	
			break;
			case "FIN_TRIM_2":
				FIN_TRIM_2.setTime(formatoFecha.parse(pc.getValor()));	
			break;
			case "COMIENZO_TRIM_3":
				COMIENZO_TRIM_3.setTime(formatoFecha.parse(pc.getValor()));	
			break;
			case "FIN_TRIM_3":
				FIN_TRIM_3.setTime(formatoFecha.parse(pc.getValor()));	
			break;
			default:
			break;
			}
		}
		/*Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar.setTime(COMIENZO_ACADEMICO);
		calendar2.setTime(FIN_ACADEMICO);*/
		if(COMIENZO_ACADEMICO.before(FIN_ACADEMICO)){ // Si el inicio del a�o est� antes que el fin
			//calendar.setTime(COMIENZO_TRIM_1);
			if( (COMIENZO_TRIM_1.before(FIN_TRIM_1)) && ( COMIENZO_TRIM_1.equals(COMIENZO_ACADEMICO) || COMIENZO_TRIM_1.after(COMIENZO_ACADEMICO) ) ){ // Si el inicio del primer trimestre est� antes que su fin y el inicio es igual o mayor al comienzo del a�i
				//calendar.setTime(COMIENZO_TRIM_2);
				if ( COMIENZO_TRIM_2.before(FIN_TRIM_2) && ( COMIENZO_TRIM_2.equals(FIN_TRIM_1) || COMIENZO_TRIM_2.after(FIN_TRIM_1) ) ){
					//calendar.setTime(COMIENZO_TRIM_3);
					if( COMIENZO_TRIM_3.before(FIN_TRIM_3) && ( COMIENZO_TRIM_3.equals(FIN_TRIM_2) || COMIENZO_TRIM_3.after(FIN_TRIM_2) ) ){
						//calendar.setTime(FIN_TRIM_3);
						if (FIN_TRIM_3.before(FIN_ACADEMICO) || FIN_TRIM_3.equals(FIN_ACADEMICO)){
							return;
						}else{
							throw new Exception ("Fin de trimestre 3 fuera de rango");
							//Falla si el fin del trimestre 3 es mayor a el fin del a�o academico
						}
					}else{
						//Falla en trimestre 3. Fecha fin es mayor a fecha inicio o se solapa con el trimestre 2
						throw new Exception ("Rango de fecha de trimestre 3 inv�lida");
					}
				}else{
					//Falla en trimestre 2. Fecha fin es mayor a fecha inicio o se solapa con el trimestre 1
					throw new Exception ("Rango de fecha de trimestre 2 inv�lida");
				}
			}else{
				// Falla en trimeste 1. Fecha fin es mayor a fecha inicio o se super comienza antes que el a�o acad�mico
				throw new Exception ("Rango de fecha de trimestre 1 inv�lida o fuera de rango");
			}
		}else{
			throw new Exception ("La fecha de finalizaci�n de ciclo debe ser mayor al inicio");
		}
	}
}
