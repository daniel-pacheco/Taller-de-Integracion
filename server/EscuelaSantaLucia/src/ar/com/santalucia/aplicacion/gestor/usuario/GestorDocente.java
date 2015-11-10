package ar.com.santalucia.aplicacion.gestor.usuario;

import java.util.ArrayList;
import java.util.Set;

import org.hibernate.Session;

import ar.com.santalucia.accesodatos.dao.usuario.DocenteHome;
import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IGestor;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorDomicilio;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorMail;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorTelefono;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorTitulo;
import ar.com.santalucia.dominio.modelo.usuarios.Directivo;
import ar.com.santalucia.dominio.modelo.usuarios.Docente;
import ar.com.santalucia.dominio.modelo.usuarios.Usuario;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.validaciones.IValidacionUsuarioAlumno;
import ar.com.santalucia.validaciones.IValidacionUsuarioDocDir;

/**
 * Clase gestor de Docentes
 * 
 * @author Eric
 * 
 * @version 1.3
 *
 */

// UltimoModificador: Eric Pennachini @ 16-10-15 16:41

public class GestorDocente extends Gestor<Docente> implements IValidacionUsuarioDocDir{
	private DocenteHome docenteDAO;
	private GestorTitulo GTitulo;
	private GestorTelefono GTelefono;
	private GestorMail GMail;
	private GestorDomicilio GDomicilio;
	//private Session sesionDeHilo;

	public GestorDocente() throws Exception {
		try {
			sesionDeHilo = HibernateUtil.getSessionFactory().getCurrentSession();
			docenteDAO = new DocenteHome();
			GTitulo = new GestorTitulo();
			GTelefono = new GestorTelefono();
			GMail = new GestorMail();
			GDomicilio = new GestorDomicilio();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar la persistencia.");
		}
	}

	@Override
	public void add(Docente object) throws Exception {
		try {
			this.validar(object);
			setSession();
			setTransaction();
			if (object.getListaTitulos() != null) {
				for (Titulo t : object.getListaTitulos()) {
					GTitulo.add(t);
				} 
			}
			if (object.getListaTelefonos() != null) {
				for (Telefono t : object.getListaTelefonos()) {
					GTelefono.add(t);
				} 
			}
			if (object.getListaMails() != null) {
				for (Mail m : object.getListaMails()) {
					GMail.add(m);
				} 
			}
			if (object.getDomicilio() != null) {
				GDomicilio.add(object.getDomicilio());
			}
			docenteDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} 
		catch (ValidacionException ex) {
			throw ex;
		}
		catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el objeto: " + ex.getMessage());
		}	
	}

	@Override
	public void modify(Docente object) throws Exception {
		try {
			this.validar(object);
			setSession();
			setTransaction();
			docenteDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} 
		catch (ValidacionException ex) {
			throw ex;
		}
		catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public void delete(Docente object) throws Exception {
		try {
			setSession();
			setTransaction();
			docenteDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public Docente getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Docente docenteDevolver = new Docente();
			docenteDevolver = docenteDAO.findById(id);
			return docenteDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el objeto por su ID: " + ex.getMessage());
		}
	}
	
	public ArrayList<Docente> getByExample(Docente example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Docente> listaDocentesDevolver = (ArrayList<Docente>) docenteDAO.findByExample(example);
			sesionDeHilo.getTransaction().commit();
			return listaDocentesDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	public ArrayList<Docente> List() throws Exception{
		try {
			setSession();
			setTransaction();
			Docente criterioVacio = new Docente();
			ArrayList<Docente> listaDocentesDevolver = new ArrayList<Docente>();
			listaDocentesDevolver = (ArrayList<Docente>) docenteDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaDocentesDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los docentes: " + ex.getMessage());
		}
	}

	/*
	 * Implementación de IValidacionDocDir
	 */
	
	public void validar(Docente object) throws Exception {
		Boolean vDocumento, vNombreUsuario, vCuil;
		ValidacionException exception = new ValidacionException();
		
		vDocumento = this.existeDocumento(object);
		if (object.getListaMails() != null) {
			for (Mail m : object.getListaMails()) {
				exception.addMensajeError(
						(this.existeMail(m) ? "La dirección de e-mail: " + m.getDireccionMail() + " ya existe" : null));
			} 
		}
		vNombreUsuario = this.existeNombreUsuario(object);
		vCuil = this.existeCuil(object);
		
		exception.addMensajeError(vDocumento ? "El documento ya existe" : null);
		exception.addMensajeError(vNombreUsuario ? "El nombre de usuario ya existe" : null);
		exception.addMensajeError(vCuil ? "El número de CUIL ya existe" : null);
		
		if (!exception.getMensajesError().isEmpty()) {
			throw exception;
		}
		
	}

	@Override
	public Boolean existeDocumento(Usuario usuario) throws Exception {
		Boolean resultado = false;
		Docente docente = (Docente) usuario;
		Docente docenteEjemplo = new Docente();
		docenteEjemplo.setNroDocumento(docente.getNroDocumento());
		try {
			ArrayList<Docente> listaDocentes = this.getByExample(docenteEjemplo);
			if (docente.getIdUsuario() == null) {
				resultado = (listaDocentes.isEmpty() ? false : true);
			} else {
				if (!listaDocentes.isEmpty()) {
					Docente docenteTemp = new Docente();
					for (Docente d : listaDocentes) {
						docenteTemp = d;
					}
					if (docenteTemp.getIdUsuario().equals(docente.getIdUsuario())) {
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

	@Override
	public Boolean existeNombreUsuario(Usuario usuario) throws Exception {
		Boolean resultado = false;
		Docente docente = (Docente) usuario;
		Docente docenteEjemplo = new Docente();
		docenteEjemplo.setNombreUsuario(docente.getNombreUsuario());
		try {
			ArrayList<Docente> listaDocente = this.getByExample(docenteEjemplo);
			if (docente.getIdUsuario() == null) {
				resultado = (listaDocente.isEmpty() ? false : true);
			} else {
				if (!listaDocente.isEmpty()) {
					Docente docenteTemp = new Docente();
					for (Docente d : listaDocente) {
						docenteTemp = d;
					}
					if (docenteTemp.getIdUsuario().equals(docente.getIdUsuario())) {
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

	@Override
	public Boolean existeCuil(Usuario usuario) throws Exception {
		Boolean resultado = false;
		Docente docente = (Docente) usuario;
		Docente docenteEjemplo = new Docente();
		docenteEjemplo.setCuil(docente.getCuil());
		try {
			ArrayList<Docente> listaDocente = this.getByExample(docenteEjemplo);
			if (docente.getIdUsuario() == null) {
				resultado = (listaDocente.isEmpty() ? false : true);
			} else {
				if (!listaDocente.isEmpty()) {
					Docente docenteTemp = new Docente();
					for (Docente d : listaDocente) {
						docenteTemp = d;
					}
					if (docenteTemp.getIdUsuario().equals(docente.getIdUsuario())) {
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

	@Override
	public Boolean existeMail(Mail mail) throws Exception {
		Boolean resultado = false;
		Mail mailEjemplo = new Mail();
		mailEjemplo.setDireccionMail(mail.getDireccionMail());
		try {
			ArrayList<Mail> listaMails = GMail.getByExample(mailEjemplo);
			if (mail.getIdMail() == null) {
				resultado = (listaMails.isEmpty() ? false : true);
			} else {
				if (!listaMails.isEmpty()) {
					Mail mailTemp = new Mail();
					for (Mail m : listaMails) {
						mailTemp = m;
					}
					if (mailTemp.getIdMail().equals(mail.getIdMail())) {
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

	
	@Override
	public void validar(Object object) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
