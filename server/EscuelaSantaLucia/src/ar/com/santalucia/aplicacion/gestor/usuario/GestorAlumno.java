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
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.aplicacion.gestor.academico.GestorCurso;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorDomicilio;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorMail;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorTelefono;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Usuario;
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

// UltimoModificador: Ariel Ramirez @ 09-11-2015 16:56

public class GestorAlumno extends GestorUsuario implements IValidacionUsuarioAlumno {
	private AlumnoHome alumnoDAO;

	public GestorAlumno() throws Exception {
		super();
		try {
			alumnoDAO = new AlumnoHome();
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
	public void add(Usuario object) throws Exception {
		Alumno alumno = (Alumno) object;
		try {
			this.validar(alumno);
			setSession();
			setTransaction();
			if (alumno.getListaTelefonos() != null) {
				for (Telefono t : alumno.getListaTelefonos()) {
					GTelefono.add(t);
				}
			}
			if (alumno.getListaMails() != null) {
				for (Mail m : alumno.getListaMails()) {
					GMail.add(m);
				}
			}
			if (alumno.getDomicilio() != null) {
				GDomicilio.add(alumno.getDomicilio());
			}
			alumnoDAO.persist(alumno);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
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
	public void modify(Usuario object) throws Exception {
		Alumno alumno = (Alumno) object;
		try {
			this.validar(alumno);
			setSession();
			setTransaction();
			alumnoDAO.attachDirty(alumno);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el objeto: " + ex.getMessage());
		}
	}

	/*
	 * Implementacion de IValidacionUsuarioAlumno
	 */

	@Override
	public Boolean existeMatricula(Alumno alumno) throws Exception {
		Boolean resultado = false;
		Alumno alumnoEjemplo = new Alumno();
		alumnoEjemplo.setMatricula(alumno.getMatricula());
		try {
			ArrayList<Alumno> listaAlumnos = this.getByExample(alumnoEjemplo);
			if (alumno.getIdUsuario() == null) {
				resultado = (listaAlumnos.isEmpty() ? false : true);
			} else {
				if (!listaAlumnos.isEmpty()) {
					Alumno alumnoTemp = new Alumno();
					for (Alumno a : listaAlumnos) {
						alumnoTemp = a;
					}
					if (alumnoTemp.getIdUsuario().equals(alumno.getIdUsuario())) {
						resultado = false;
					} else {
						resultado = true;
					}
				}
			}
		} catch (Exception ex) {
			throw ex;
		}
		return resultado;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see ar.com.santalucia.aplicacion.gestor.Gestor#validar(java.lang.Object)
	 */
	@Override
	public void validar(Object object) throws Exception {
		Boolean vDocumento, vMatricula, vNombreUsuario;
		ValidacionException exception = new ValidacionException();
		// Validación del documento
		vDocumento = this.existeDocumento((Alumno) object);
		// Validación de direcciones de mail
		if (((Alumno) object).getListaTelefonos() != null) {
			for (Mail m : ((Alumno) object).getListaMails()) {
				exception.addMensajeError(
						(this.existeMail(m) ? "La dirección de e-mail: " + m.getDireccionMail() + " ya existe" : null));
			}
		}
		// Validación de matrícula
		vMatricula = this.existeMatricula((Alumno) object);
		// Validación de nombre de usuario
		vNombreUsuario = this.existeNombreUsuario((Alumno) object);

		exception.addMensajeError((vDocumento ? "El documento ya existe" : null));
		exception.addMensajeError((vMatricula ? "La matrícula ya existe" : null));
		exception.addMensajeError((vNombreUsuario ? "El nombre de usuario ya existe" : null));

		if (!exception.getMensajesError().isEmpty()) {
			throw exception;
		}
	}

	
	public ArrayList getByExample(Alumno example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Alumno> listaDirectivosDevolver = (ArrayList<Alumno>) alumnoDAO.findByExample(example);
			sesionDeHilo.getTransaction().commit();
			return listaDirectivosDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());
		}

	}

	@Override
	public ArrayList List() throws Exception {
		try {
			setSession();
			setTransaction();
			Alumno criterioVacio = new Alumno();
			ArrayList<Alumno> listadoAlumnosDevolver = new ArrayList<Alumno>();
			listadoAlumnosDevolver = (ArrayList<Alumno>) alumnoDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listadoAlumnosDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los directivos: " + ex.getMessage());
		}
	}
	

	@Override
	public ArrayList getByExample(Object example) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
