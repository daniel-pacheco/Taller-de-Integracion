package ar.com.santalucia;

import java.util.Date;

import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorUsuario;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

public class PruebasDeRefactor {

	public static void main(String[] args) throws Exception {
		crearAlumno();

	}

	private static void crearAlumno() throws Exception {
		Alumno alumno = new Alumno();
		GestorAlumno gAlu = new GestorAlumno();
		
		alumno.setIdUsuario(null);
		alumno.setApellido("Ramirez");
		alumno.setNombre("Mauricio Ariel");
		alumno.setFechaNacimiento(new Date(90, 12, 21));
		alumno.setMatricula(699841L);
		alumno.setNombreUsuario("La_mitocondria_gamer");
		alumno.setNroDocumento(35442873L);
		alumno.setSexo('m');
		alumno.setTipoDocumento("DU/DNI");
		alumno.setActivo(true);
		alumno.setDomicilio(null);
		alumno.setListaMails(null);
		alumno.setListaTelefonos(null);
		
	}

}
