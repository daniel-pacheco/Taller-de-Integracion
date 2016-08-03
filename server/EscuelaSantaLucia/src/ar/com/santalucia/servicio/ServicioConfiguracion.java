package ar.com.santalucia.servicio;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import ar.com.santalucia.aplicacion.gestor.sistema.configuracion.GestorParametroConfiguracion;
import ar.com.santalucia.dominio.modelo.sistema.configuracion.ParametroConfiguracion;
import ar.com.santalucia.excepciones.ValidacionException;
/**
 * 
 * @author Ariel
 * @version 1.0
 */

public class ServicioConfiguracion {
	private static GestorParametroConfiguracion gConfiguracion;

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
	
	/**
	 * Agrega varios parámetros a la vez
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
	
	public static ParametroConfiguracion getParametro(String nombreParametro) throws Exception{
		try {
			if (gConfiguracion==null){
				gConfiguracion = new GestorParametroConfiguracion();
			}
			return gConfiguracion.getByExample(new ParametroConfiguracion(null,nombreParametro,null,null)).get(0);
		}catch(Exception ex){
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
	
	public List<ParametroConfiguracion> listAllParametros() throws Exception{
		try{
			return gConfiguracion.getByExample(new ParametroConfiguracion(null,null,null,null));
		}catch(Exception ex){
			throw new Exception("No se pudo obtener el listado de PARÁMETROS: " + ex.getMessage());
		}
	}
	
	/**
	 * Valida la coherencia de las fechas de los trimestres comprendidas en el año academico 
	 */
	private void validarFechasTrimestre(List<ParametroConfiguracion> parametrosConfig) throws Exception{
		/* Capturo los parámetros de fecha que me interesan para validar*/
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
		if(COMIENZO_ACADEMICO.before(FIN_ACADEMICO)){ // Si el inicio del año está antes que el fin
			//calendar.setTime(COMIENZO_TRIM_1);
			if( (COMIENZO_TRIM_1.before(FIN_TRIM_1)) && ( COMIENZO_TRIM_1.equals(COMIENZO_ACADEMICO) || COMIENZO_TRIM_1.after(COMIENZO_ACADEMICO) ) ){ // Si el inicio del primer trimestre está antes que su fin y el inicio es igual o mayor al comienzo del añi
				//calendar.setTime(COMIENZO_TRIM_2);
				if ( COMIENZO_TRIM_2.before(FIN_TRIM_2) && ( COMIENZO_TRIM_2.equals(FIN_TRIM_1) || COMIENZO_TRIM_2.after(FIN_TRIM_1) ) ){
					//calendar.setTime(COMIENZO_TRIM_3);
					if( COMIENZO_TRIM_3.before(FIN_TRIM_3) && ( COMIENZO_TRIM_3.equals(FIN_TRIM_2) || COMIENZO_TRIM_3.after(FIN_TRIM_2) ) ){
						//calendar.setTime(FIN_TRIM_3);
						if (FIN_TRIM_3.before(FIN_ACADEMICO) || FIN_TRIM_3.equals(FIN_ACADEMICO)){
							return;
						}else{
							throw new Exception ("Fin de trimestre 3 fuera de rango");
							//Falla si el fin del trimestre 3 es mayor a el fin del año academico
						}
					}else{
						//Falla en trimestre 3. Fecha fin es mayor a fecha inicio o se solapa con el trimestre 2
						throw new Exception ("Rango de fecha de trimestre 3 inválida");
					}
				}else{
					//Falla en trimestre 2. Fecha fin es mayor a fecha inicio o se solapa con el trimestre 1
					throw new Exception ("Rango de fecha de trimestre 2 inválida");
				}
			}else{
				// Falla en trimeste 1. Fecha fin es mayor a fecha inicio o se super comienza antes que el año académico
				throw new Exception ("Rango de fecha de trimestre 1 inválida o fuera de rango");
			}
		}else{
			throw new Exception ("La fecha de finalización de ciclo debe ser mayor al inicio");
		}
	}
	
	/**
	 * Determina si el periodo calificado como menor está comprendido dentro de los límites del periodo mayor.<br>
	 * Es posible utilizar un parámetro válido de configuracion como COMIENZO_ACADEMICO.
	 * @param fecha1 Fecha de inicio del periodo menor.
	 * @param fecha2 Fecha de fin del periodo menor.
	 * @param parametro (Opcional) Nombre de parámetro válido para utilizar una periodo de fechas predefinidas de configuración. Establecer null si no se utiliza este parámetro.  
	 * @param periodo1 (Opcional) Fecha de inicio del periodo mayor. Establecer null si no se utiliza este parámetro. 
	 * @param periodo2 (Opcional) Fecha de fin del periodo mayor. Establecer null si no se utiliza este parámetro.
	 * @throws Exception
	 */
	public static void comprendidoEnPeriodo(Date fecha1, Date fecha2, String parametro, Date periodo1, Date periodo2) throws Exception{
		
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Calendar comienzoPeriodo = Calendar.getInstance();
		Calendar finPeriodo = Calendar.getInstance();
		Calendar fechaInicio = Calendar.getInstance();
		Calendar fechaFin = Calendar.getInstance();
		if( parametro!=null || periodo1==null || periodo2==null){
			parametro = parametro==null?"":parametro; 
			switch (parametro) {
			case "COMIENZO_ACADEMICO":
					comienzoPeriodo.setTime(formatoFecha.parse(getParametro("COMIENZO_ACADEMICO").getValor()));
					finPeriodo.setTime(formatoFecha.parse(getParametro("FIN_ACADEMICO").getValor()));
				break;
			default:
					comienzoPeriodo.setTime(formatoFecha.parse(getParametro("COMIENZO_ACADEMICO").getValor()));
					finPeriodo.setTime(formatoFecha.parse(getParametro("FIN_ACADEMICO").getValor()));
				break;
			}
		}else{
			comienzoPeriodo.setTime(periodo1);
			finPeriodo.setTime(periodo2);
		}
		fechaInicio.setTime(fecha1);
		fechaFin.setTime(fecha2);
		
		if(fechaFin.after(fechaInicio) || fechaFin.equals(fechaInicio)){
			if (fechaInicio.after(comienzoPeriodo) || fechaInicio.equals(comienzoPeriodo) ){
				if( fechaFin.before(finPeriodo) || fechaFin.equals(finPeriodo) ){
					return;
				}else{
					throw new Exception("Periodo excede fin del periodo comparado.");
				}
			}else{
				throw new Exception("Periodo se anticipa al comienzo del periodo comparado.");
			}
		}else{
			throw new Exception("Periodo con fecha de fin inferior al periodo comparado.");
		}
	}
	
	
	public Boolean generarBackup() throws Exception {
		Runtime app = Runtime.getRuntime();
		try {
			app.exec("cmd.exe /C start c:\\users\\eric\\desktop\\test_backup_mysql\\test_backup.bat");
			return true;
		} catch (Exception ex) {
			throw new Exception("Error al generar el backup: " + ex.getMessage());
		}
	}
	
}
