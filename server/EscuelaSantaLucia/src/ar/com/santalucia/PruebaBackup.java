package ar.com.santalucia;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.util.xml.Origin;
import org.hibernate.internal.util.xml.XmlDocument;

import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;

public class PruebaBackup {

	public static void main(String[] args) {
		Runtime app = Runtime.getRuntime();
		try {
			Configuration configFile = HibernateUtil.getHibConfig();
			
			Calendar calendar = Calendar.getInstance();
			Integer d = new Integer(calendar.get(Calendar.DAY_OF_MONTH));
			Integer m = new Integer(calendar.get(Calendar.MONTH));
			Integer a = new Integer(calendar.get(Calendar.YEAR));
			Integer hh = new Integer(calendar.get(Calendar.HOUR_OF_DAY));
			Integer mm = new Integer(calendar.get(Calendar.MINUTE));
			String fechaHora = (d<=10?"0"+d:d) + "-" + (m<=10?"0"+m:m) + "-" + a + "_" + (hh<=10?"0"+hh:hh) + "-" + (mm<=10?"0"+mm:mm);
			Map<String, String> parametros = new HashMap<String, String>();
			parametros.put("host", " --host=localhost");
			parametros.put("port", " --port=3306");
			parametros.put("user", " --user=" + configFile.getProperty("hibernate.connection.username"));
			parametros.put("password", " --password=" + configFile.getProperty("hibernate.connection.password"));
			parametros.put("fileOut", " --result-file=\"C:\\Users\\Eric\\Desktop\\test_backup_mysql\\backup_escuelabd_" + fechaHora + ".backup\" ");
			parametros.put("exe", "c:\\users\\eric\\desktop\\test_backup_mysql\\mysqldump.exe");
			parametros.put("base", " " + configFile.getProperty("hibernate.connection.url").substring(28));
			
			File file = new File("c:/users/eric/desktop/test_backup_mysql/test_backup2.bat");
			if (!file.exists()) {
				file.createNewFile();
			} 
			FileOutputStream file2 = new FileOutputStream(file);
			file2.write(parametros.get("exe").getBytes());
			file2.write(parametros.get("host").getBytes());
			file2.write(parametros.get("port").getBytes());
			file2.write(parametros.get("user").getBytes());
			file2.write(parametros.get("password").getBytes());
			file2.write(parametros.get("fileOut").getBytes());
			file2.write(parametros.get("base").getBytes());
			
			app.exec("cmd.exe /C start " + file.getAbsolutePath());
			
			System.out.println("EXITO");
			
			file2.close();
			
		} catch (Exception ex) {
			System.err.println(">> ERROR: " + ex.getMessage());
		}
	}

}
