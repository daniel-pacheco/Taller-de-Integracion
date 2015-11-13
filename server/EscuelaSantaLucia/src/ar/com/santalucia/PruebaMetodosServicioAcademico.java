package ar.com.santalucia;

import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.servicio.ServicioAcademico;
import ar.com.santalucia.servicio.ServicioAlumno;

public class PruebaMetodosServicioAcademico {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		
		modificarAnioTest();
		
		//modificarAlumnoTest();
		
	}

	private static void modificarAlumnoTest() {
		try {
			ServicioAlumno servicioAlumno = new ServicioAlumno();
			Alumno alumno = new Alumno();
			alumno = servicioAlumno.getUsuario(25L);
			//servicioAlumno.closeSession();
			alumno.setActivo(false);
			ServicioAlumno servicioAlumno2 = new ServicioAlumno();
			servicioAlumno2.modifyUsuario(alumno);
			servicioAlumno2.closeSession();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void modificarAnioTest()  {
		// TODO
		// 1 - Obtener un año de id determinado
		// 2-  modificar los datos
		// 3 - hacer uso del servicio y modificar el año
		try {
			ServicioAcademico sAcademico;
			sAcademico = new ServicioAcademico();
			Anio anioExtraido = sAcademico.getAnio(4L);
			anioExtraido.setNombre("cualquier cosa que pongo aca");
			sAcademico.closeSession();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
