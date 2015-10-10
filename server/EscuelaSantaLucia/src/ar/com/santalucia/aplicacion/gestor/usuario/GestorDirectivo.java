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
import ar.com.santalucia.dominio.modelo.usuarios.Directivo;
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

public class GestorDirectivo extends Gestor<Directivo> implements IValidacionUsuarioDocDir {
	private DirectivoHome directivoDAO;
	private GestorTitulo GTitulo;
	private GestorTelefono GTelefono;
	private GestorMail GMail;
	private GestorDomicilio GDomicilio;
	private Session sesionDeHilo;
	
	public GestorDirectivo() throws Exception {
		try {
			sesionDeHilo = HibernateUtil.getSessionFactory().getCurrentSession();
			directivoDAO = new DirectivoHome();
			GTitulo = new GestorTitulo();
			GTelefono = new GestorTelefono();
			GMail = new GestorMail();
			GDomicilio = new GestorDomicilio();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(Directivo object) throws Exception {
		try {
			setSession();
			setTransaction();
			this.validar(object);
			for (Titulo t: object.getListaTitulos()) {
				GTitulo.add(t);
			}
			for (Telefono t: object.getListaTelefonos()) {
				GTelefono.add(t);
			}
			for (Mail m: object.getListaMails()) {
				GMail.add(m);
			}
			GDomicilio.add(object.getDomicilio());
			directivoDAO.persist(object);
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
	public void modify(Directivo object) throws Exception {
		try {
			setSession();
			setTransaction();
			this.validar(object);
			directivoDAO.attachDirty(object);
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
	public void delete(Directivo object) throws Exception {
		try {
			setSession();
			setTransaction();
			directivoDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public Directivo getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Directivo directivoDevolver = new Directivo();
			directivoDevolver = directivoDAO.findById(id);
			return directivoDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el objeto por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Directivo> getByExample(Directivo example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Directivo> listaDirectivosDevolver = (ArrayList<Directivo>) directivoDAO.findByExample(example);
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
			return listaDirectivosDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los directivos: " + ex.getMessage());
		}
	}

	/*
	 * Implementacion de gestor
	 */
	
	@Override
	public Boolean existeDocumento(String tipo, Long numero) throws Exception {
		Directivo directivoEjemplo = new Directivo();
		directivoEjemplo.setTipoDocumento(tipo);
		directivoEjemplo.setNroDocumento(numero);
		ArrayList<Directivo> ejemplos = this.getByExample(directivoEjemplo);		
		return (ejemplos.isEmpty() ? false : true);
	}

	@Override
	public Boolean existeMail(Mail mail) throws Exception {
		Mail mailEjemplo = new Mail();
		mailEjemplo.setDireccionMail(mail.getDireccionMail());
		ArrayList<Mail> ejemplos = GMail.getByExample(mailEjemplo);
		return (ejemplos.isEmpty() ? false : true);
	}

	@Override
	public Boolean existeNombreUsuario(String nombreUsuario) throws Exception {
		Directivo directivoEjemplo = new Directivo();
		directivoEjemplo.setNombreUsuario(nombreUsuario);
		ArrayList<Directivo> ejemplos = this.getByExample(directivoEjemplo);
		return (ejemplos.isEmpty() ? false : true);
	}

	@Override
	public Boolean existeCuil(Long cuil) throws Exception {
		Directivo directivoEjemplo = new Directivo();
		directivoEjemplo.setCuil(cuil);
		ArrayList<Directivo> ejemplos = this.getByExample(directivoEjemplo);
		return (ejemplos.isEmpty() ? false : true);
	}

	@Override
	public void validar(Directivo object) throws Exception {
		Boolean vDocumento, vNombreUsuario, vCuil;
		ValidacionException exception = new ValidacionException();
		
		vDocumento = this.existeDocumento(object.getTipoDocumento(), object.getNroDocumento());
		for(Mail m : object.getListaMails()){
			exception.addMensajeError((this.existeMail(m) 
										? "La direcci�n de e-mail: " + m.getDireccionMail() +" ya existe" 
										: null));
		}
		vNombreUsuario = this.existeNombreUsuario(object.getNombreUsuario());
		vCuil = this.existeCuil(object.getCuil());
		
		exception.addMensajeError(vDocumento ? "El documento ya existe" : null);
		exception.addMensajeError(vNombreUsuario ? "El nombre de usuario ya existe" : null);
		exception.addMensajeError(vCuil ? "El n�mero de CUIL ya existe" : null);
		
		if (!exception.getMensajesError().isEmpty()) {
			throw exception;
		}
	}
}
