package ar.com.santalucia;

import java.util.Iterator;
import java.util.Map;

public class PruebaArchivos {

	public static void main(String[] args) {
		
		Map<String, String> propiedades = System.getenv();
		Iterator<String> i = propiedades.keySet().iterator();
		while (i.hasNext()){
			String propiedad = i.next();
			System.out.println("Propiedad: "+ propiedad +" Valor: " + propiedades.get(propiedad));
		}
	}

	
}
