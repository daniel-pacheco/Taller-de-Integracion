package ar.com.santalucia.aplicacion.gestor.usuario;

import java.util.ArrayList;
import org.hibernate.Session;

import ar.com.santalucia.accesodatos.dao.usuario.DirectivoHome;
import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorDomicilio;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorMail;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorTelefono;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorTitulo;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Directivo;
import ar.com.santalucia.dominio.modelo.usuarios.Docente;
import ar.com.santalucia.dominio.modelo.usuarios.Usuario;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.validaciones.IValidacionUsuarioDocDir;

/**
 * Clase gestor de directivos
 *
 * @author ericpennachini
 * @version 1.1
 *
 */

//UltimoModificador: Eric Pennachini @ 10-11-15 17:50

public class GestorDirectivo extends GestorUsuario implements IValidacionUsuarioDocDir {
	private DirectivoHome directivoDAO;
	private GestorTitulo GTitulo;
	
	public GestorDirectivo() throws Exception {
		super();
		try {
			directivoDAO = new DirectivoHome();
			GTitulo = new GestorTitulo();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(Usuario object) throws Exception {
		Directivo directivo = (Directivo) object;
		try {
			this.validar(directivo);
			if (directivo.getListaTitulos() != null) {
				for (Titulo t : directivo.getListaTitulos()) {
					GTitulo.add(t);
				} 
			}
			if (directivo.getListaTelefonos() != null) {
				for (Telefono t : directivo.getListaTelefonos()) {
					GTelefono.add(t);
				} 
			}
			if (directivo.getListaMails() != null) {
				for (Mail m : directivo.getListaMails()) {
					GMail.add(m);
				} 
			}
			if (directivo.getDomicilio() != null) {
				GDomicilio.add(directivo.getDomicilio());
			}
			setSession();
			setTransaction();
			directivoDAO.persist(directivo);
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
		Directivo directivo = (Directivo) object;
		try {
			this.validar(directivo);
			setSession();
			setTransaction();
			directivoDAO.attachDirty(directivo);
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
	public ArrayList<Directivo> getByExample(Directivo object) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Directivo> listaDirectivosDevolver = (ArrayList<Directivo>) directivoDAO.findByExample((Directivo) object);
			sesionDeHilo.getTransaction().commit();
			return listaDirectivosDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Directivo> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Directivo criterioVacio = new Directivo();
			ArrayList<Directivo> listaDirectivosDevolver = new ArrayList<Directivo>();
			listaDirectivosDevolver = (ArrayList<Directivo>) directivoDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaDirectivosDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los directivos: " + ex.getMessage());
		}
	}

	/*
	 * Implementación de IValidacionDocDir
	 */

	@Override
	public void validar(Object object) throws Exception {
		Boolean vDocumento, vNombreUsuario, vCuil;
		ValidacionException exception = new ValidacionException();
		
		vDocumento = this.existeDocumento((Directivo) object);
		if (((Directivo) object).getListaMails() != null) {
			for (Mail m : ((Directivo) object).getListaMails()) {
				exception.addMensajeError(
						(this.existeMail(m) ? "La dirección de e-mail: " + m.getDireccionMail() + " ya existe" : null));
			} 
		}
		vNombreUsuario = this.existeNombreUsuario((Directivo) object);
		vCuil = this.existeCuil((Directivo) object);
		
		exception.addMensajeError(vDocumento ? "El documento ya existe" : null);
		exception.addMensajeError(vNombreUsuario ? "El nombre de usuario ya existe" : null);
		exception.addMensajeError(vCuil ? "El número de CUIL ya existe" : null);
		
		if (!exception.getMensajesError().isEmpty()) {
			throw exception;
		}
	}
	
	@Override
	public Boolean existeCuil(Usuario usuario) throws Exception {
		Boolean resultado = false;
		Directivo directivo = (Directivo) usuario;
		Directivo directivoEjemplo = new Directivo();
		directivoEjemplo.setCuil(directivo.getCuil());
		try {
			ArrayList<Directivo> listaDirectivos = this.getByExample(directivoEjemplo);
			if (directivo.getIdUsuario() == null) {
				resultado = (listaDirectivos.isEmpty() ? false : true);
			} else {
				if (!listaDirectivos.isEmpty()) {
					Directivo directivoTemp = new Directivo();
					for (Directivo d : listaDirectivos) {
						directivoTemp = d;
					}
					if (directivoTemp.getIdUsuario().equals(directivo.getIdUsuario())) {
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
	public ArrayList getByExample(Object example) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



}
