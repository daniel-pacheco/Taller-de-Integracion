package ar.com.santalucia;

public class PruebaBackup {

	public static void main(String[] args) {
		Runtime app = Runtime.getRuntime();
		try {
			System.out.println(">> Ejecutando...");
			app.exec("cmd.exe /C start c:\\users\\eric\\desktop\\test_backup_mysql\\test_backup.bat");
			System.out.println(">> FIN");
			
			
		} catch (Exception ex) {
			System.err.println(">> ERROR: " + ex.getMessage());
		}
	}

}
