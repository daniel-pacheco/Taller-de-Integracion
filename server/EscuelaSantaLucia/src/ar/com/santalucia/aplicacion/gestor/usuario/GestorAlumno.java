package ar.com.santalucia.aplicacion.gestor.usuario;

import java.util.ArrayList;
import java.util.Set;

import org.hibernate.Session;

import ar.com.santalucia.accesodatos.dao.usuario.AlumnoHome;
import ar.com.santalucia.accesodatos.dao.usuario.info.DomicilioHome;
import ar.com.santalucia.accesodatos.dao.usuario.info.MailHome;
import ar.com.santalucia.accesodatos.dao.usuario.info.TelefonoHome;
import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorDomicilio;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorMail;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorTelefono;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.validaciones.IValidacionUsuarioAlumno;

/**
 * Clase gestor para Alumno
 * 
 * @author Ariel Ramirez
 *
 * @version 2.0
 */

// UltimoModificador: Ariel Ramirez @ 26-09-2015 12:55

public class GestorAlumno extends Gestor<Alumno> implements IValidacionUsuarioAlumno{
	private AlumnoHome alumnoDAO;
	private GestorDomicilio GDomicilio;
	private GestorTelefono GTelefono;
	private GestorMail GMail;

	public GestorAlumno() throws Exception {
		super();
		try {
			alumnoDAO = new AlumnoHome();
			GDomicilio = new GestorDomicilio();
			GTelefono = new GestorTelefono();
			GMail = new GestorMail();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	/**
	 * @param object
	 *            Alumno. Este método hace efectiva la persistencia (commit),
	 *            con la posibilidad de hacer rollback en caso de que falle
	 *            parte de la transacción.
	 */
	@Override
	public void add(Alumno object) throws Exception {
		try {
			setSession();
			setTransaction();
			this.validar(object);			
			for (Telefono t : object.getListaTelefonos()) {
				GTelefono.add(t);
			}
			for (Mail m : object.getListaMails()) {
				GMail.add(m);
			}
			GDomicilio.add(object.getDomicilio());
			alumnoDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el objeto: " + ex.getMessage());
		}
	}

	/**
	 * Modifica los atributos propios del alumno, no los objetos que lo
	 * componen. Para modificar dichos elementos use los gestores
	 * correspondientes.
	 */
	@Override
	public void modify(Alumno object) throws Exception {
		try {
			setSession();
			setTransaction();
			alumnoDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el objeto: " + ex.getMessage());
		}
	}

	/**
	 * @param object
	 *            Borra al alumno recibido y elimina a todos los objetos que lo
	 *            componen.
	 */
	@Override
	public void delete(Alumno object) throws Exception {
		try {
			setSession();
			setTransaction();
			alumnoDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public Alumno getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Alumno alumnoDevolver = new Alumno();
			alumnoDevolver = alumnoDAO.findById(id);
			// NO HACER COMMIT para poder más tarde hacer el get de la lista de
			// teléfonos
			// y/o mails, Hibernate no las trae de una. La transacción se cierra
			// en el modificar.
			// sesionDeHilo.getTransaction().commit();
			return alumnoDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el objeto por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Alumno> getByExample(Alumno example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Alumno> listaAlumnosDevolver = (ArrayList<Alumno>) alumnoDAO.findByExample((Alumno) example);
			return listaAlumnosDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Alumno> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Alumno criterioVacio = new Alumno();
			ArrayList<Alumno> listaAlumnosDevolver = new ArrayList<Alumno>();
			listaAlumnosDevolver = (ArrayList<Alumno>) alumnoDAO.findByExample(criterioVacio);
			return listaAlumnosDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los alumnos: " + ex.getMessage());
		}
	}

	/*
	 * Implementacion de IValidacionUsuarioAlumno
	 */

	@Override
	public Boolean existeDocumento(String tipo, Long numero) throws Exception {
		Alumno alumnoEjemplo = new Alumno();
		alumnoEjemplo.setTipoDocumento(tipo);
		alumnoEjemplo.setNroDocumento(numero);
		ArrayList<Alumno> ejemplos =  this.getByExample(alumnoEjemplo);
		return (ejemplos.isEmpty() ? false : true);
	}

	@Override
	public Boolean existeMail(Set<Mail> mail) throws Exception {
		Alumno alumnoEjemplo = new Alumno();
		alumnoEjemplo.setListaMails(mail);
		ArrayList<Alumno> ejemplos =  this.getByExample(alumnoEjemplo);
		return (ejemplos.isEmpty() ? false : true);
	}

	@Override
	public Boolean existeNombreUsuario(String nombreUsuario) throws Exception {
		Alumno alumnoEjemplo = new Alumno();
		alumnoEjemplo.setNombreUsuario(nombreUsuario);
		ArrayList<Alumno> ejemplos =  this.getByExample(alumnoEjemplo);
		return (ejemplos.isEmpty() ? false : true);
	}

	@Override
	public Boolean existeMatricula(Long matricula) throws Exception {
		Alumno alumnoEjemplo = new Alumno();
		alumnoEjemplo.setMatricula(matricula);
		ArrayList<Alumno> ejemplos =  this.getByExample(alumnoEjemplo);
		return (ejemplos.isEmpty() ? false : true);
	}

	/**
	 * (non-Javadoc)
	 * @see ar.com.santalucia.aplicacion.gestor.Gestor#validar(java.lang.Object)
	 */
	@Override
	public void validar(Alumno object) throws Exception {
		Boolean vDocumento, vMail, vMatricula, vNombreUsuario;
		ValidacionException exception = new ValidacionException();
		
		vDocumento = this.existeDocumento(object.getTipoDocumento(), object.getNroDocumento());
		vMail = this.existeMail(object.getListaMails());
		vMatricula = this.existeMatricula(object.getMatricula());
		vNombreUsuario = this.existeNombreUsuario(object.getNombreUsuario());
		
		exception.addMensajeError((vDocumento ? "El documento ya existe" : null));
		exception.addMensajeError((vMail ? "La dirección del e-mail ya existe" : null));
		exception.addMensajeError((vMatricula ? "La matrícula ya existe" : null));
		exception.addMensajeError((vNombreUsuario ? "El nombre de usuario ya existe" : null));
		
		if (!exception.getMensajesError().isEmpty()) {
			throw exception;
		}
	}
}
