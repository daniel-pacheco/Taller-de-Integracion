package ar.com.santalucia;

import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.servicio.ServicioAcademico;

public class Borrar {

	public static void main(String[] args) {

		try{
			ServicioAcademico sAcademico = new ServicioAcademico();
			Materia materia = new Materia();
			materia = sAcademico.getMateria(1L);
			System.out.println(materia.getDocenteTitular().toString());
			System.out.println(materia.getDocenteSuplente().toString());
		}catch(Exception ex){
			
		}

	}

}
