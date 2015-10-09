package ar.com.santalucia;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;

import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.servicio.ServicioAlumno;

public class Prueba3 {

	public static void main(String[] args) throws Exception {

		// Creación de un alumno.
		 crearUnAlumno();

		// Mostrar datos con toString sobrecargado
		// verToString();

		// Modificar un mail
		// modificarUnMail();

		// Pruebas Json sin persistir
		// jSonTestSinPersistir();

		// Prueba Json con persistencia;
		// jSonTestPersistir();

		// Prueba Json de modificación
		//modificarMailJson();
		 
		 //COMENTARIO subida de un branch backend y merge to master
	}

	public static void modificarMailJson() throws Exception {
		// Creamos un servicio para alumno y un auxiliar de alumno en este caso
		ServicioAlumno sAlumno = new ServicioAlumno();
		Alumno auxiliarAlumno = new Alumno();

		auxiliarAlumno = sAlumno.getUsuario(1L);

		// creo un mail para modificar
		Mail mailAModificar = new Mail();

		// creamos set auxiliar para localizar un mail especifico y traemos la
		// lista que tiene cargada el alumno
		Set<Mail> listadoMailAux = new HashSet<Mail>();
		listadoMailAux = sAlumno.getMails(auxiliarAlumno.getIdUsuario());

		// recorremos y localizamos el mail
		for (Mail a : listadoMailAux) {
			if (a.getTipoMail().equals("Academico de la Conicet")) {
				mailAModificar = a;
				break;
			}
		}

		// Convertimos nuestra busqueda a Json y lo mandamos (a la pagina
		// ponele)
		
		Gson gson = new Gson();
		String elementoJson = gson.toJson(mailAModificar);

		//Se envia y  se  cierra la sesion y/o la transaccion 
		
		sAlumno.closeSession();
		
		// la pagina haría esto
		elementoJson = elementoJson.replace("Academico de la Conicet", "El que usa para contactarse con menga");
		
		// cargo de nuevo mailAModificar
		Mail mailModificado = new Mail();
		mailModificado = gson.fromJson(elementoJson, Mail.class);
		//Mail mailModificado2 = new Mail();
		
		//asignarMail(mailModificado2, mailModificado);
		
		// le decimos al servicio que lo modifique
		sAlumno.modifyMail(mailModificado);
	}

	private static void asignarMail(Mail mailAModificar, Mail mailModificado) {
		mailAModificar.setIdMail(mailModificado.getIdMail());
		mailAModificar.setDireccionMail(mailModificado.getDireccionMail());
		mailAModificar.setTipoMail(mailModificado.getTipoMail());
	}

	public static void modificarUnMail() throws Exception {
		// Creamos un servicio para alumno y un auxiliar de alumno en este caso
		ServicioAlumno sAlumno = new ServicioAlumno();
		Alumno auxiliarAlumno = new Alumno();

		auxiliarAlumno = sAlumno.getUsuario(1L);

		// creo un mail para modificar
		Mail mailModificado = new Mail();

		// creamos set auxiliar para localizar un mail especifico y traemos la
		// lista que tiene cargada el alumno
		Set<Mail> listadoMailAux = new HashSet<Mail>();
		listadoMailAux = sAlumno.getMails(auxiliarAlumno.getIdUsuario());

		// recorremos y localizamos el mail
		for (Mail a : listadoMailAux) {
			if (a.getTipoMail().equals("Antiguo correo que casi no usa.")) {
				mailModificado = a;
				break;
			}
		}

		// cambiamos un dato del mail que sacamos
		mailModificado.setTipoMail("Academico de la Conicet");

		// le decimos al servicio que lo modifique
		sAlumno.modifyMail(mailModificado);

	}

	public static void jSonTestSinPersistir() throws Exception {
		// Creamos un servicio para alumno y un auxiliar de alumno en este caso

		ServicioAlumno sAlumno = new ServicioAlumno();
		Alumno auxiliarAlumno = new Alumno();

		auxiliarAlumno = sAlumno.getUsuario(1L);

		// Creamos un elemento json
		Gson gson = new Gson();
		// cargamos el json con información de alumno
		String elementoJson = gson.toJson(auxiliarAlumno);
		// mostramos el json
		System.out.println(elementoJson);

		// Intentar pasar de json a un objeto
		Alumno alumnoJson = new Alumno();
		alumnoJson = gson.fromJson(elementoJson, Alumno.class);

		// mostramos un dato del objeto para verificar
		System.out.println("alumnoJson: " + alumnoJson.getDomicilio());

	}

	public static void verToString() throws Exception {
		// Creamos un servicio para alumno y un auxiliar de alumno en este caso

		ServicioAlumno sAlumno = new ServicioAlumno();
		Alumno auxiliarAlumno = new Alumno();

		// Extraemos un alumno
		auxiliarAlumno = sAlumno.getUsuario(1L);

		// mostramos su sobrecarga
		System.out.println(auxiliarAlumno.toString());

		// ver domicilio del alumno
		System.out.println(auxiliarAlumno.getDomicilio().toString()); // toString
																		// sobrecargado

		// ver las direcciones de mail del alumno
		System.out.println(auxiliarAlumno.getListaMails().toString()); // toString
																		// sobrecargado

		// ver los teléfonos del alumno
		System.out.println(auxiliarAlumno.getListaTelefonos()); // toString
																// sobrecargado

	}

	public static void crearUnAlumno() throws Exception {
		// Creamos un servicio para alumno y un auxiliar de alumno en este caso

		ServicioAlumno sAlumno = new ServicioAlumno();
		Alumno auxiliarAlumno = new Alumno();

		auxiliarAlumno.setIdUsuario(null);
		auxiliarAlumno.setApellido("Ramirez");
		auxiliarAlumno.setNombre("Mauricio Ariel");
		auxiliarAlumno.setFechaNacimiento(new Date(90, 12, 21));
		auxiliarAlumno.setMatricula(699841L);
		auxiliarAlumno.setNombreUsuario("La mitocondria gamer");
		auxiliarAlumno.setNroDocumento(35442873L);
		auxiliarAlumno.setSexo('m');
		auxiliarAlumno.setTipoDocumento("DU/DNI");

		// Agregamos un domicilio
		Domicilio domicilio1 = new Domicilio("El Rodeo", 419, 0, "Paraná", "-", "Paraná", "Entre Ríos", 3100,
				"San Agustín");
		auxiliarAlumno.setDomicilio(domicilio1);

		// Agregamos 2 teléfonos
		Set<Telefono> listaTelefonos = new HashSet<Telefono>();
		Telefono telefono1 = new Telefono();
		Telefono telefono2 = new Telefono();
		telefono1.setCaracteristica(343L);
		telefono1.setNroTelefono(155046725L);
		telefono1.setTipoTelefono("Celular");
		telefono2.setCaracteristica(343L);
		telefono2.setNroTelefono(4272056L);
		telefono2.setTipoTelefono("Casa/Fijo");
		listaTelefonos.add(telefono1);
		listaTelefonos.add(telefono2);
		auxiliarAlumno.setListaTelefonos(listaTelefonos);

		// Agregamos 2 direcciones de e-mail
		Set<Mail> listaMails = new HashSet<Mail>();
		Mail mail1 = new Mail();
		Mail mail2 = new Mail();
		mail1.setDireccionMail("mauricioarielramirezi@gmail.com");
		mail1.setTipoMail("Trabajo/Académico");
		mail2.setDireccionMail("pcma_21@hotmail.com");
		mail2.setTipoMail("Otro");
		listaMails.add(mail1);
		listaMails.add(mail2);
		auxiliarAlumno.setListaMails(listaMails);

		sAlumno.addUsuario(auxiliarAlumno);

	}

	public static void jSonTestPersistir() throws Exception {
		// Creamos un servicio para alumno y un auxiliar de alumno en este caso

		ServicioAlumno sAlumno = new ServicioAlumno();
		Alumno auxiliarAlumno = new Alumno();

		auxiliarAlumno.setApellido("Pennachini");
		auxiliarAlumno.setNombre("Eric Daniel");
		auxiliarAlumno.setFechaNacimiento(new Date(90, 11, 21));
		auxiliarAlumno.setMatricula(465488L);
		auxiliarAlumno.setNombreUsuario("EiiEricpara las turras");
		auxiliarAlumno.setNroDocumento(36998547L);
		auxiliarAlumno.setSexo('m');
		auxiliarAlumno.setTipoDocumento("DU/DNI");

		// Agregamos un domicilio
		Domicilio domicilio1 = new Domicilio("O'Higgins", 1390, 0, "Paraná", "-", "Paraná", "Entre Ríos", 3100,
				"Gazzano");
		auxiliarAlumno.setDomicilio(domicilio1);

		// Agregamos 2 teléfonos
		Set<Telefono> listaTelefonos = new HashSet<Telefono>();
		Telefono telefono1 = new Telefono();
		Telefono telefono2 = new Telefono();
		telefono1.setCaracteristica(343L);
		telefono1.setNroTelefono(156235148L);
		telefono1.setTipoTelefono("Celular");
		telefono2.setCaracteristica(343L);
		telefono2.setNroTelefono(4077373L);
		telefono2.setTipoTelefono("Casa/Fijo");
		listaTelefonos.add(telefono1);
		listaTelefonos.add(telefono2);
		auxiliarAlumno.setListaTelefonos(listaTelefonos);

		// Agregamos 2 direcciones de e-mail
		Set<Mail> listaMails = new HashSet<Mail>();
		Mail mail1 = new Mail();
		Mail mail2 = new Mail();
		mail1.setDireccionMail("eric.pennachini@gmail.com");
		mail1.setTipoMail("Trabajo/Académico");
		mail2.setDireccionMail("ericdp_0591@hotmail.com");
		mail2.setTipoMail("Otro");
		listaMails.add(mail1);
		listaMails.add(mail2);
		auxiliarAlumno.setListaMails(listaMails);

		// Pasamos el alumno a un json
		Gson gson = new Gson();
		String elementoJson = gson.toJson(auxiliarAlumno);

		// Asignamos a un nuevo alumno desde el json
		Alumno alumnoPersistir = new Alumno();
		alumnoPersistir = gson.fromJson(elementoJson, Alumno.class);

		// Intentar persistirlo
		sAlumno.addUsuario(alumnoPersistir);
	}
}
