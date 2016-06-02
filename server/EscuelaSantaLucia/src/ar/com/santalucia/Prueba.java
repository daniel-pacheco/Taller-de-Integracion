package ar.com.santalucia;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorTelefono;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;

public class Prueba {
	//comentario de prueba
	public static void main(String[] args) throws Exception {

		GestorAlumno GAlumno = new GestorAlumno();
		
		Long dni = 30000000L;
		for (int i = 0; i < 300; i++) {
			Alumno alumnoPrueba = new Alumno();
			

			//alumnoPrueba.setApellido("Pennachini " + i);
			alumnoPrueba.setApellido(generarStringRandom(16));
			//alumnoPrueba.setNombre("Eric Daniel");
			alumnoPrueba.setNombre(generarStringRandom(8));
			alumnoPrueba.setFechaNacimiento(new Date(90, 11, 21));
			alumnoPrueba.setMatricula(dni + 10000000L);
			alumnoPrueba.setNombreUsuario("eric.pennachini." + i);
			alumnoPrueba.setNroDocumento(dni);
			alumnoPrueba.setSexo('m');
			alumnoPrueba.setTipoDocumento("DU/DNI");

			// Agregamos un domicilio
			Domicilio domicilio1 = new Domicilio("O'Higgins", 1390, 0, "Paraná", "-", "Paraná", "Entre Ríos", 3100,
					"Gazzano");
			alumnoPrueba.setDomicilio(domicilio1);

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
			alumnoPrueba.setListaTelefonos(listaTelefonos);

			// Agregamos 2 direcciones de e-mail
			Set<Mail> listaMails = new HashSet<Mail>();
			Mail mail1 = new Mail();
			Mail mail2 = new Mail();
			mail1.setDireccionMail("eric.pennachini." + i + "@gmail.com");
			mail1.setTipoMail("Trabajo/Académico");
			mail2.setDireccionMail("ericdp_0591_" + i + "@hotmail.com");
			mail2.setTipoMail("Otro");
			listaMails.add(mail1);
			listaMails.add(mail2);
			alumnoPrueba.setListaMails(listaMails);

			GAlumno.add(alumnoPrueba);
			dni++;
		}
		
//		Alumno alumnoModificar = GAlumno.getById(3L);
//		alumnoModificar.setApellido("Todo bien sip");
//		alumnoModificar.setNroDocumento(12334567L);
//		Set<Telefono> listaModificada = new HashSet<Telefono>();
//		listaModificada = alumnoModificar.getListaTelefonos();
//		listaModificada.add(new Telefono(11L,4232545L,"Celular"));
//		alumnoModificar.setListaTelefonos(listaModificada);
//		GAlumno.modify(alumnoModificar);
		
//		GestorTelefono GTelefono = new GestorTelefono();
//		Telefono telefonoEliminar = GTelefono.getById(9L);
//		listaModificada.remove(telefonoEliminar);
//		alumnoModificar.setListaTelefonos(listaModificada);
//		GAlumno.modify(alumnoModificar);
		
		
		//GAlumno.delete(alumnoEliminar);
		
		
		
		 // Recupero un alumno por su ID 
//		Alumno alumnoModificado = GAlumno.getById(1L); // Las listas no las carga a la primera 
		 					   							// sino cuando hago el get de la lista 
														// (ver aclaración en getById(...) del gestor de alumnos)
//		alumnoModificado.setNombre("Jessicax");
		
//		GAlumno.closeTransaction();
		
//		GAlumno.modify(alumnoModificado);
		
		 // Busco un telefono en la lista del alumno 
		 // listaTelefonos.clear(); 
		
//		Set<Telefono> listaTelefonos = alumnoModificado.getListaTelefonos();
//		Telefono telModif = new Telefono();
//		for (Telefono t : listaTelefonos) {
//			if (t.getIdTelefono() == 1L) {
//				telModif = t;
//			}
//		}

//		GAlumno.closeTransaction();
		
//		listaTelefonos.remove(telModif);
		// Modifico y vuelvo a guardar 
//		telModif.setCaracteristica(888L);
//		listaTelefonos.add(telModif);
//		alumnoModificado.setListaTelefonos(listaTelefonos);
//		GAlumno.modify(alumnoModificado);
		/*
		Telefono telefonoTrucho=new Telefono();
		
		TelefonoHome telefonoDAO = new TelefonoHome();
		telefonoTrucho = telefonoDAO.findById(1L);
		telefonoTrucho.setCaracteristica(777L);
		telefonoDAO.attachDirty(telefonoTrucho);
		GAlumno.closeTransaction();
		*/
		
	}
	
	/**
	 * Genera una cadena alfanumérica de 10 caracteres
	 * @return
	 */
	public static String generarStringRandom(int cantCaract) {
		String caracteres = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890";
		Random rnd = new Random();
		StringBuilder cadenaRandom = new StringBuilder();
		for (int i = 0; i < cantCaract; i++) {
			cadenaRandom.append(caracteres.charAt(rnd.nextInt(caracteres.length())));
		}
		return cadenaRandom.toString();
	}

}
