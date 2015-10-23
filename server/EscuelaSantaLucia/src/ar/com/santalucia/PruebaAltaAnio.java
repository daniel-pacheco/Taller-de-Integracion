package ar.com.santalucia;

import ar.com.santalucia.aplicacion.gestor.academico.GestorAnio;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorDocente;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.usuarios.Docente;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;

public class PruebaAltaAnio {

	public static void main(String[] args) throws Exception {
		// Con docente nuevo
		// agregarAnioMateria_DocNuevo();
		
		// Con docente existente
		agregarAnioMateria_DocExiste();
		
	}

	public static void agregarAnioMateria_DocNuevo() throws Exception {
		// Inicializo un gestor de años
		GestorAnio gAnio = new GestorAnio();
		// Inicializo un gestor de docentes
		// GestorDocente gDocente = new GestorDocente();

		// Inicializo un año, materia y docente para trabajar
		Anio anio = new Anio();
		Materia materia1 = new Materia();
		Docente docente1 = new Docente();

		// Seteo un año con un nombre
		anio.setNombre("2do año");

		// Seteo los campos básicos de los cursos y los agrego a la lista de
		// cursos del año
		/*
		 * curso1.setDivision('A'); curso1.setCicloLectivo(2015);
		 * curso2.setDivision('B'); curso2.setCicloLectivo(2015);
		 * anio.getListaCursos().add(curso1); anio.getListaCursos().add(curso2);
		 */

		// Seteo un docente y lo agrego a su gestor
		docente1.setNombre("Aldo");
		docente1.setApellido("Sigura");
		docente1.setNombreUsuario("aldi_kpo_09");
		docente1.setTipoDocumento("DNI");
		docente1.setNroDocumento(15333777L);
		docente1.setCuil(30153337771L);
		docente1.setActivo(true);
		docente1.setSexo('M');
		Domicilio domicilio1 = new Domicilio("Jorge Newbery", 1200, 0, "Paraná", "-", "Paraná", "Entre Ríos", 3100,
				"---");
		docente1.setDomicilio(domicilio1);

		// Seteo el nombre de las materias
		materia1.setNombre("Programación Orientada a Objetos");

		// Seteo el docente titular de la materia y la guardo en el año
		materia1.setDocenteTitular(docente1);
		materia1.setTipoDocente("Titular");
		anio.getListaMaterias().add(materia1);

		// Agrego el año al gestor
		gAnio.add(anio);
	}
	
	public static void agregarAnioMateria_DocExiste() throws Exception {
		// Inicializo un gestor de años
		GestorAnio gAnio = new GestorAnio();
		// Inicializo un gestor de docentes
		GestorDocente gDocente = new GestorDocente();
		
		Anio anio2 = new Anio();
		Materia materia2 = new Materia();
		Docente docente2 = new Docente();
		
		// Seteo un año con un nombre
		anio2.setNombre("5to año");

		// Seteo un docente a partir de uno que ya está, x el ID
		docente2 = gDocente.getById(8L);

		// Seteo el nombre de las materias
		materia2.setNombre("Administración de Recursos / Sistemas III");

		// Seteo el docente titular de la materia y la guardo en el año
		materia2.setDocenteTitular(docente2);
		materia2.setTipoDocente("Cualquiera");
		anio2.getListaMaterias().add(materia2);

		// Agrego el año al gestor
		gAnio.add(anio2);
		
	}

}
