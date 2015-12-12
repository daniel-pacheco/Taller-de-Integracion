package ar.com.santalucia;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Area;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;
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
		
		Domicilio domicilio1=new Domicilio();
		domicilio1.setBarrio("San Agustín");
		domicilio1.setCalle("El Rodeo");
		domicilio1.setCodigoPostal(3100);
		domicilio1.setDepartamento("Paraná");
		domicilio1.setDpto("-");
		domicilio1.setLocalidad("Paraná");
		domicilio1.setNumero(419);
		domicilio1.setPiso(0);
		domicilio1.setProvincia("Entre Rios");
		
		Set<Mail> listaMail1 = new HashSet<Mail>();
		Mail mail1 = new Mail("alumno1@gmail.com","Personal");
		listaMail1.add(mail1);

		Set<Telefono> listaTelefono1 = new HashSet<Telefono>();
		Telefono telefono1 = new Telefono(343L,4232266L,"Casa");
		listaTelefono1.add(telefono1);
		
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
		alumno1.setListaMails(listaMail1);
		alumno1.setListaTelefonos(listaTelefono1);
		alumno1.setDomicilio(domicilio1);
		
		Set<Mail> listaMail2 = new HashSet<Mail>();
		Mail mail2 = new Mail("alumno2@gmail.com","Personal");
		listaMail2.add(mail2);

		Set<Telefono> listaTelefono2 = new HashSet<Telefono>();
		Telefono telefono2 = new Telefono(343L,4562144L,"Casa");
		listaTelefono2.add(telefono2);
		
		Domicilio domicilio2=new Domicilio();
		domicilio2.setBarrio("San Agustín");
		domicilio2.setCalle("El Rodeo");
		domicilio2.setCodigoPostal(3100);
		domicilio2.setDepartamento("Paraná");
		domicilio2.setDpto("-");
		domicilio2.setLocalidad("Paraná");
		domicilio2.setNumero(419);
		domicilio2.setPiso(0);
		domicilio2.setProvincia("Entre Rios");
		
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
		alumno2.setListaMails(listaMail2);
		alumno2.setListaTelefonos(listaTelefono2);
		alumno2.setDomicilio(domicilio2);
		
		Set<Mail> listaMail3 = new HashSet<Mail>();
		Mail mail3 = new Mail("alumno3@gmail.com","Personal");
		listaMail3.add(mail3);

		Set<Telefono> listaTelefono3 = new HashSet<Telefono>();
		Telefono telefono3 = new Telefono(343L,4231111L,"Casa");
		listaTelefono3.add(telefono3);
		
		Domicilio domicilio3=new Domicilio();
		domicilio3.setBarrio("San Agustín");
		domicilio3.setCalle("El Rodeo");
		domicilio3.setCodigoPostal(3100);
		domicilio3.setDepartamento("Paraná");
		domicilio3.setDpto("-");
		domicilio3.setLocalidad("Paraná");
		domicilio3.setNumero(419);
		domicilio3.setPiso(0);
		domicilio3.setProvincia("Entre Rios");
		
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
		alumno3.setDomicilio(domicilio3);
		alumno3.setListaMails(listaMail3);
		alumno3.setListaTelefonos(listaTelefono3);
		
		Set<Mail> listaMail4 = new HashSet<Mail>();
		Mail mail4 = new Mail("alumno4@gmail.com","Personal");
		listaMail4.add(mail4);

		Set<Telefono> listaTelefono4 = new HashSet<Telefono>();
		Telefono telefono4 = new Telefono(343L,4562144L,"Casa");
		listaTelefono4.add(telefono4);
		
		Domicilio domicilio4=new Domicilio();
		domicilio4.setBarrio("San Agustín");
		domicilio4.setCalle("El Rodeo");
		domicilio4.setCodigoPostal(3100);
		domicilio4.setDepartamento("Paraná");
		domicilio4.setDpto("-");
		domicilio4.setLocalidad("Paraná");
		domicilio4.setNumero(419);
		domicilio4.setPiso(0);
		domicilio4.setProvincia("Entre Rios");
		
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
		alumno4.setDomicilio(domicilio4);
		alumno4.setListaMails(listaMail4);
		alumno4.setListaTelefonos(listaTelefono4);
		
		Set<Mail> listaMail5 = new HashSet<Mail>();
		Mail mail5 = new Mail("docente1@gmail.com","Personal");
		listaMail5.add(mail5);

		Set<Telefono> listaTelefono5 = new HashSet<Telefono>();
		Telefono telefono5 = new Telefono(343L,4221177L,"Casa");
		listaTelefono5.add(telefono5);
		
		Set<Titulo> listaTitulos1 = new HashSet<Titulo>();
		Titulo titulos1 = new Titulo("Ingenieria en electrónica","Recibido en UTN año 1970");
		listaTitulos1.add(titulos1);
		
		Domicilio domicilio5=new Domicilio();
		domicilio5.setBarrio("San Agustín");
		domicilio5.setCalle("El Rodeo");
		domicilio5.setCodigoPostal(3100);
		domicilio5.setDepartamento("Paraná");
		domicilio5.setDpto("-");
		domicilio5.setLocalidad("Paraná");
		domicilio5.setNumero(419);
		domicilio5.setPiso(0);
		domicilio5.setProvincia("Entre Rios");
		
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
		docente1.setDomicilio(domicilio5);
		docente1.setListaMails(listaMail5);
		docente1.setListaTelefonos(listaTelefono5);
		docente1.setListaTitulos(listaTitulos1);
		docente1.setRol(Personal.DOCENTE);
		
		Set<Mail> listaMail6 = new HashSet<Mail>();
		Mail mail6 = new Mail("docente2@gmail.com","Personal");
		listaMail6.add(mail6);

		Set<Telefono> listaTelefono6 = new HashSet<Telefono>();
		Telefono telefono6 = new Telefono(343L,4221177L,"Casa");
		listaTelefono6.add(telefono6);
		
		Set<Titulo> listaTitulos6 = new HashSet<Titulo>();
		Titulo titulos6 = new Titulo("Ingeniería Civil","Recibido en UNL");
		listaTitulos6.add(titulos6);
		
		Domicilio domicilio6=new Domicilio();
		domicilio6.setBarrio("San Agustín");
		domicilio6.setCalle("El Rodeo");
		domicilio6.setCodigoPostal(3100);
		domicilio6.setDepartamento("Paraná");
		domicilio6.setDpto("-");
		domicilio6.setLocalidad("Paraná");
		domicilio6.setNumero(419);
		domicilio6.setPiso(0);
		domicilio6.setProvincia("Entre Rios");
		
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
		docente2.setDomicilio(domicilio6);
		docente2.setListaMails(listaMail6);
		docente2.setListaTelefonos(listaTelefono6);
		docente2.setListaTitulos(listaTitulos6);
		docente2.setRol(Personal.DOCENTE);
		
		Set<Mail> listaMail7 = new HashSet<Mail>();
		Mail mail7 = new Mail("docente3@gmail.com","Personal");
		listaMail7.add(mail7);

		Set<Telefono> listaTelefono7 = new HashSet<Telefono>();
		Telefono telefono7 = new Telefono(343L,4221177L,"Casa");
		listaTelefono7.add(telefono7);
		
		Set<Titulo> listaTitulos7 = new HashSet<Titulo>();
		Titulo titulos7 = new Titulo("Abogado","Recibido en UNL");
		listaTitulos7.add(titulos7);
		
		Domicilio domicilio7=new Domicilio();
		domicilio7.setBarrio("San Agustín");
		domicilio7.setCalle("El Rodeo");
		domicilio7.setCodigoPostal(3100);
		domicilio7.setDepartamento("Paraná");
		domicilio7.setDpto("-");
		domicilio7.setLocalidad("Paraná");
		domicilio7.setNumero(419);
		domicilio7.setPiso(0);
		domicilio7.setProvincia("Entre Rios");
		
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
		docente3.setDomicilio(domicilio7);
		docente3.setListaMails(listaMail7);
		docente3.setListaTelefonos(listaTelefono7);
		docente3.setListaTitulos(listaTitulos7);
		docente3.setRol(Personal.DOCENTE);
		
		Set<Mail> listaMail8 = new HashSet<Mail>();
		Mail mail8 = new Mail("docente4@gmail.com","Personal");
		listaMail8.add(mail8);

		Set<Telefono> listaTelefono8 = new HashSet<Telefono>();
		Telefono telefono8 = new Telefono(343L,4221177L,"Casa");
		listaTelefono8.add(telefono8);
		
		Set<Titulo> listaTitulos8 = new HashSet<Titulo>();
		Titulo titulos8 = new Titulo("Ingeniería en Sistemas","Recibida en UCC");
		listaTitulos8.add(titulos8);
		
		Domicilio domicilio8=new Domicilio();
		domicilio8.setBarrio("San Agustín");
		domicilio8.setCalle("El Rodeo");
		domicilio8.setCodigoPostal(3100);
		domicilio8.setDepartamento("Paraná");
		domicilio8.setDpto("-");
		domicilio8.setLocalidad("Paraná");
		domicilio8.setNumero(419);
		domicilio8.setPiso(0);
		domicilio8.setProvincia("Entre Rios");
		
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
		docente4.setDomicilio(domicilio8);
		docente4.setListaMails(listaMail8);
		docente4.setListaTelefonos(listaTelefono8);
		docente4.setListaTitulos(listaTitulos8);
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
		
		List<Anio> listaAnios = servAcademico.getAnios(anio1);
		Long idAnio = 0L;
		
		for(Anio a: listaAnios){
			idAnio=a.getIdAnio();
			System.out.println(idAnio);
		}
		
		
		Materia tempMateria = new Materia();
		List<Materia> listaMaterias = servAcademico.getMaterias(materia1);
		for (Materia m: listaMaterias){
			tempMateria = m;
		}		
		
		servAcademico.asignarMateriaAAnio(tempMateria, idAnio);
		
		listaMaterias = servAcademico.getMaterias(materia2);
		for (Materia m: listaMaterias){
			tempMateria = m;
		}
		
		servAcademico.asignarMateriaAAnio(tempMateria, idAnio);
		
		
	}

}
