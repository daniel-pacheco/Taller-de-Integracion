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
		//Inicializo un gestor de a�os
		GestorAnio gAnio = new GestorAnio();
		//Inicializo un gestor de docentes
		GestorDocente gDocente = new GestorDocente();
		
		//Inicializo un a�o, materias, cursos y docentes para trabajar
		Anio anio = new Anio();
		Curso curso1 = new Curso(); 
		Curso curso2 = new Curso();
		Materia materia1 = new Materia(); 
		Materia materia2 = new Materia();
		//Materia materia3 = new Materia();
		//Materia materia4 = new Materia();
		Docente docente1 = new Docente();
		Docente docente2 = new Docente();
		
		//Seteo un a�o con un nombre
		anio.setNombre("6to a�o");
		
		//Seteo los campos b�sicos de los cursos y los agrego a la lista de cursos del a�o
		/*
		curso1.setDivision('A');
		curso1.setCicloLectivo(2015);
		curso2.setDivision('B');
		curso2.setCicloLectivo(2015);
		anio.getListaCursos().add(curso1);
		anio.getListaCursos().add(curso2);
		*/
		
		//Seteo un docente y lo agrego a su gestor
		docente1.setNombre("Aldo");
		docente1.setApellido("Sigura");
		docente1.setNombreUsuario("aldi_kpo_09");
		docente1.setTipoDocumento("DNI");
		docente1.setNroDocumento(15333777L);
		docente1.setCuil(30153337771L);
		docente1.setActivo(true);
		docente1.setSexo('M');
		Domicilio domicilio1 = new Domicilio("Jorge Newbery", 
												1200, 
												0, 
												"Paran�", 
												"-", 
												"Paran�", 
												"Entre R�os", 
												3100,
												"---");
		docente1.setDomicilio(domicilio1);
		
		//Seteo el nombre de las materias
		materia1.setNombre("Programaci�n Orientada a Objetos");
		materia2.setNombre("Administraci�n de Recursos");
		
		//Seteo el docente titular de la materia y la guardo en el a�o
		materia1.setDocenteTitular(docente1);
		materia1.setTipoDocente("Titular");
		anio.getListaMaterias().add(materia1);
		
		//Agrego el a�o al gestor
		gAnio.add(anio);
		
		
	}

}
