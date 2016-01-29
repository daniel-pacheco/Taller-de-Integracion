package ar.com.santalucia;

import ar.com.santalucia.dominio.modelo.usuarios.Usuario;

public class PruebaGetClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a = "";
		Usuario b = new Usuario();
		
		System.out.println(b.getClass().getName());
		System.out.println(b.getClass().getSimpleName().toUpperCase());
		System.out.println(b.getClass().getCanonicalName());
	}

}
