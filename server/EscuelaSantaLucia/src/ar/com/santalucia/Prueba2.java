package ar.com.santalucia;

import java.util.ArrayList;
import java.util.List;

import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

public class Prueba2 {

	public static void main(String[] args) {
		
		try {
			GestorAlumno GAlumno = new GestorAlumno();
			Alumno alumnoEjemplo = new Alumno();
			alumnoEjemplo.setNombre("Eric Daniel");
			alumnoEjemplo.setListaTelefonos(null);
			alumnoEjemplo.setListaMails(null);
			List<Alumno> listaEjemplos = new ArrayList<Alumno>();
			listaEjemplos = GAlumno.getByExample(alumnoEjemplo);
			//listaEjemplos = GAlumno.List();
			for (Alumno a: listaEjemplos) {
				System.out.println("Ejemplo 1: " + a);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
