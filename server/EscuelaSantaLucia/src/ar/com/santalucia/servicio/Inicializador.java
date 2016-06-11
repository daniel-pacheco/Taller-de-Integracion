package ar.com.santalucia.servicio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.*;

/**
 * Esta clase está destina a contener métodos de incialización del sistema. Tener en cuenta que debe llamarse unicamente cuando el sistema requiera<br>
 * reestablecer valores de configuración para archivos críticos, como lo que respecta a hibernate y log.
 * @author Ariel
 *
 * @version 1.0
 */
public class Inicializador {
	
	public void inicializarLog() throws Exception, FileNotFoundException, IOException{
		LogManager.getLogManager().readConfiguration(
		new FileInputStream("d:/log.properties")); ///EscuelaSantaLucia/Resources/log.properties
	}
}
