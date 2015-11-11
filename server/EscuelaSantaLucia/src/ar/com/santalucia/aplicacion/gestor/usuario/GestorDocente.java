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

public class GestorDocente extends GestorUsuario implements IValidacionUsuarioDocDir {
	private DocenteHome docenteDAO;
	private GestorTitulo GTitulo;
	private GestorTelefono GTelefono;
	private GestorMail GMail;
	private GestorDomicilio GDomicilio;

	public GestorDocente() throws Exception {
		super();
		try {
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
	public void add(Usuario object) throws Exception {
		Docente docente = (Docente) object;
		try {
			this.validar(docente);
			setSession();
			setTransaction();
			if (docente.getListaTitulos() != null) {
				for (Titulo t : docente.getListaTitulos()) {
					GTitulo.add(t);
				} 
			}
			if (docente.getListaTelefonos() != null) {
				for (Telefono t : docente.getListaTelefonos()) {
					GTelefono.add(t);
				} 
			}
			if (docente.getListaMails() != null) {
				for (Mail m : docente.getListaMails()) {
					GMail.add(m);
				} 
			}
			if (docente.getDomicilio() != null) {
				GDomicilio.add(docente.getDomicilio());
			}
			docenteDAO.persist(docente);
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
	public void modify(Usuario object) throws Exception {
		Docente docente = (Docente) object;
		try {
			this.validar(docente);
			setSession();
			setTransaction();
			docenteDAO.attachDirty(docente);
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
	
	//@Override
	public ArrayList<Docente> getByExample(Docente object) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Docente> listaDocentesDevolver = (ArrayList<Docente>) docenteDAO.findByExample(object);
			sesionDeHilo.getTransaction().commit();
			return listaDocentesDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
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
	public void validar(Object object) throws Exception {
		Boolean vDocumento, vNombreUsuario, vCuil;
		ValidacionException exception = new ValidacionException();
		
		vDocumento = this.existeDocumento((Docente) object);
		if (((Docente) object).getListaMails() != null) {
			for (Mail m : ((Docente) object).getListaMails()) {
				exception.addMensajeError(
						(this.existeMail(m) ? "La dirección de e-mail: " + m.getDireccionMail() + " ya existe" : null));
			} 
		}
		vNombreUsuario = this.existeNombreUsuario((Docente) object);
		vCuil = this.existeCuil((Docente) object);
		
		exception.addMensajeError(vDocumento ? "El documento ya existe" : null);
		exception.addMensajeError(vNombreUsuario ? "El nombre de usuario ya existe" : null);
		exception.addMensajeError(vCuil ? "El número de CUIL ya existe" : null);
		
		if (!exception.getMensajesError().isEmpty()) {
			throw exception;
		}
		
	}

	
	@Override
	public ArrayList getByExample(Object example) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
