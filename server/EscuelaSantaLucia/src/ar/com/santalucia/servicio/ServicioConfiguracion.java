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
	
	public static ParametroConfiguracion getParametro(String nombreParametro) throws Exception{
		try {
			if (gConfiguracion==null){
				gConfiguracion = new GestorParametroConfiguracion();
			}
			return gConfiguracion.getByExample(new ParametroConfiguracion(null,nombreParametro,null,null)).get(0);
		}catch(Exception ex){
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
	
	public List<ParametroConfiguracion> listAllParametros() throws Exception{
		try{
			return gConfiguracion.getByExample(new ParametroConfiguracion(null,null,null,null));
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
	
	/**
	 * Determina si el periodo calificado como menor est� comprendido dentro de los l�mites del periodo mayor.<br>
	 * Es posible utilizar un par�metro v�lido de configuracion como COMIENZO_ACADEMICO.
	 * @param fecha1 Fecha de inicio del periodo menor.
	 * @param fecha2 Fecha de fin del periodo menor.
	 * @param parametro (Opcional) Nombre de par�metro v�lido para utilizar una periodo de fechas predefinidas de configuraci�n. Establecer null si no se utiliza este par�metro.  
	 * @param periodo1 (Opcional) Fecha de inicio del periodo mayor. Establecer null si no se utiliza este par�metro. 
	 * @param periodo2 (Opcional) Fecha de fin del periodo mayor. Establecer null si no se utiliza este par�metro.
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
