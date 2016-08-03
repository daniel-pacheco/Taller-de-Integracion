package ar.com.santalucia;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class PruebaBackup {

	public static void main(String[] args) {
		Runtime app = Runtime.getRuntime();
		
		try {			
			Map<String, String> parametros = new HashMap<String, String>();
			parametros.put("host", " --host=localhost");
			parametros.put("port", " --port=3306");
			parametros.put("user", " --user=root");
			parametros.put("password", " --password=mysqlwindows");
			parametros.put("fileOut", " --result-file=\"C:\\Users\\Eric\\Desktop\\test_backup_mysql\\escuelabd.backup\" ");
			parametros.put("exe", "c:\\users\\eric\\desktop\\test_backup_mysql\\mysqldump.exe");
			parametros.put("base", "escuelabd");
			
			File file = new File("c:\\users\\eric\\desktop\\test_backup_mysql\\test_backup2.bat");
			if (!file.exists()) {
				file.createNewFile();
				//file.setExecutable(true);
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
