package ar.com.santalucia;

import java.util.Date;

import org.jvnet.hk2.internal.Creator;

import ar.com.santalucia.aplicacion.gestor.academico.GestorAnio;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorPersonal;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Area;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.servicio.*;

public class PruebaAltaAnio {

	public static void main(String[] args) throws Exception {
		
		/**
		 * TODO Crear:
		 * - 1 año
		 * - 2 cursos
		 * - 3 materias
		 * - 4 alumnos
		 * - 4 docentes 
		 */
		
		Anio anio1 = new Anio();
		Curso curso1 = new Curso();
		Curso curso2 = new Curso();
		Materia materia1 = new Materia();
		Materia materia2 = new Materia();
		Alumno alumno1 = new Alumno();
		Alumno alumno2 = new Alumno();
		Alumno alumno3 = new Alumno();
		Alumno alumno4 = new Alumno();
		Personal docente1 = new Personal();
		Personal docente2 = new Personal();
		Personal docente3 = new Personal();
		Personal docente4 = new Personal();
		
		anio1.setIdAño(null);
		anio1.setNombre("5to año");
		anio1.setDescripcion("5to año de Licenciatura en Sistemas de Información");
		anio1.setActivo(true);
		
		curso1.setIdCurso(null);
		curso1.setDivision('1');
		curso1.setTurno("Único");
		curso1.setCicloLectivo(2015);
		
		curso2.setIdCurso(null);
		curso2.setDivision('2');
		curso2.setTurno("Único");
		curso2.setCicloLectivo(2015);
		
		materia1.setIdMateria(null);
		materia1.setNombre("Administración de Recursos");
		materia1.setDescripcion("");
		materia1.setArea(new Area(null,"Sistemas"));
		materia1.setActivo(true);
		
		materia2.setIdMateria(null);
		materia2.setNombre("Seguridad y Auditoría");
		materia2.setDescripcion("");
		materia2.setArea(new Area(null,"Sistemas"));
		materia2.setActivo(true);
		
		alumno1.setIdUsuario(null);
		alumno1.setApellido("Ramirez");
		alumno1.setNombre("Mauricio Ariel");
		alumno1.setFechaNacimiento(new Date(90, 12, 21));
		alumno1.setMatricula(699841L);
		alumno1.setNombreUsuario("La_mitocondria_gamer");
		alumno1.setNroDocumento(35442873L);
		alumno1.setSexo('m');
		alumno1.setTipoDocumento("DU/DNI");
		alumno1.setActivo(true);
		alumno1.setDomicilio(null);
		alumno1.setListaMails(null);
		alumno1.setListaTelefonos(null);
		
		alumno2.setIdUsuario(null);
		alumno2.setApellido("Pennachini");
		alumno2.setNombre("Eric Daniel");
		alumno2.setFechaNacimiento(new Date(91, 11, 20));
		alumno2.setMatricula(123456L);
		alumno2.setNombreUsuario("ericpennachini");
		alumno2.setNroDocumento(36099930L);
		alumno2.setSexo('m');
		alumno2.setTipoDocumento("DU/DNI");
		alumno2.setActivo(true);
		alumno2.setDomicilio(null);
		alumno2.setListaMails(null);
		alumno2.setListaTelefonos(null);
		
		alumno3.setIdUsuario(null);
		alumno3.setApellido("Herrlein");
		alumno3.setNombre("Martin");
		alumno3.setFechaNacimiento(new Date(91, 10, 10));
		alumno3.setMatricula(321654L);
		alumno3.setNombreUsuario("el_tincho");
		alumno3.setNroDocumento(33000111L);
		alumno3.setSexo('m');
		alumno3.setTipoDocumento("DU/DNI");
		alumno3.setActivo(true);
		alumno3.setDomicilio(null);
		alumno3.setListaMails(null);
		alumno3.setListaTelefonos(null);
		
		alumno4.setIdUsuario(null);
		alumno4.setApellido("Pacheco");
		alumno4.setNombre("Daniel");
		alumno4.setFechaNacimiento(new Date(83, 12, 21));
		alumno4.setMatricula(555999L);
		alumno4.setNombreUsuario("dani-o-pachek");
		alumno4.setNroDocumento(22333444L);
		alumno4.setSexo('m');
		alumno4.setTipoDocumento("DU/DNI");
		alumno4.setActivo(true);
		alumno4.setDomicilio(null);
		alumno4.setListaMails(null);
		alumno4.setListaTelefonos(null);
		
		
		docente1.setIdUsuario(null);
		docente1.setApellido("Sigura");
		docente1.setNombre("Aldo");
		docente1.setFechaNacimiento(new Date(83, 12, 21));
		docente1.setNombreUsuario("aldo.sigura");
		docente1.setNroDocumento(11222333L);
		docente1.setCuil(20112223331L);
		docente1.setSexo('m');
		docente1.setTipoDocumento("DU/DNI");
		docente1.setActivo(true);
		docente1.setDomicilio(null);
		docente1.setListaMails(null);
		docente1.setListaTelefonos(null);
		docente1.setListaTitulos(null);
		docente1.setRol(Personal.DOCENTE);
		
		docente2.setIdUsuario(null);
		docente2.setApellido("Llorente");
		docente2.setNombre("Maria Emilia");
		docente2.setFechaNacimiento(new Date(83, 12, 21));
		docente2.setNombreUsuario("m.e.llorente");
		docente2.setNroDocumento(11000777L);
		docente2.setCuil(20110007771L);
		docente2.setSexo('f');
		docente2.setTipoDocumento("DU/DNI");
		docente2.setActivo(true);
		docente2.setDomicilio(null);
		docente2.setListaMails(null);
		docente2.setListaTelefonos(null);
		docente2.setListaTitulos(null);
		docente2.setRol(Personal.DOCENTE);
		
		docente3.setIdUsuario(null);
		docente3.setApellido("Carbó");
		docente3.setNombre("Alejandro");
		docente3.setFechaNacimiento(new Date(83, 12, 21));
		docente3.setNombreUsuario("ale.carbo.ucr");
		docente3.setNroDocumento(11444999L);
		docente3.setCuil(20114449991L);
		docente3.setSexo('m');
		docente3.setTipoDocumento("DU/DNI");
		docente3.setActivo(true);
		docente3.setDomicilio(null);
		docente3.setListaMails(null);
		docente3.setListaTelefonos(null);
		docente3.setListaTitulos(null);
		docente3.setRol(Personal.DOCENTE);
		
		docente4.setIdUsuario(null);
		docente4.setApellido("Cerini");
		docente4.setNombre("Dolores");
		docente4.setFechaNacimiento(new Date(83, 12, 21));
		docente4.setNombreUsuario("dolores.cerini");
		docente4.setNroDocumento(11888555L);
		docente4.setCuil(20118885551L);
		docente4.setSexo('m');
		docente4.setTipoDocumento("DU/DNI");
		docente4.setActivo(true);
		docente4.setDomicilio(null);
		docente4.setListaMails(null);
		docente4.setListaTelefonos(null);
		docente4.setListaTitulos(null);
		docente4.setRol(Personal.DOCENTE_DIRECTIVO);
		
		curso1.getListaAlumnos().add(alumno1);
		curso1.getListaAlumnos().add(alumno2);
		curso2.getListaAlumnos().add(alumno3);
		curso2.getListaAlumnos().add(alumno4);
		
		materia1.setDocenteTitular(docente1);
		materia1.setDocenteSuplente(docente2);
		materia1.setArea(null);
		materia2.setDocenteTitular(docente3);
		materia2.setDocenteSuplente(docente4);
		materia2.setArea(null);
		
		anio1.getListaCursos().add(curso1);
		anio1.getListaCursos().add(curso2);
	
		// prueba con servicio académico
		
		ServicioAcademico servAcademico = new ServicioAcademico();
		ServicioAlumno servAlumno = new ServicioAlumno();
		ServicioDocente servDocente = new ServicioDocente();
		
		
		servAlumno.addUsuario(alumno1);
		servAlumno.addUsuario(alumno2);
		servAlumno.addUsuario(alumno3);
		servAlumno.addUsuario(alumno4);
		servDocente.addUsuario(docente1);
		servDocente.addUsuario(docente2);
		servDocente.addUsuario(docente3);
		servDocente.addUsuario(docente4);
		
		servAcademico.addMateria(materia1);
		servAcademico.addMateria(materia2);
		
		servAcademico.addAnio(anio1);
		//servAcademico.asignarMateriaAAnio(materia, idAnio)
	}

}
