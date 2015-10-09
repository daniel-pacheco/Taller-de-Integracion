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
 * @version 1.2
 *
 */

// UltimoModificador: Eric Pennachini @ 07-08-15 19:40

public class GestorDocente extends Gestor<Docente> implements IValidacionUsuarioDocDir{
	private DocenteHome docenteDAO;
	private GestorTitulo GTitulo;
	private GestorTelefono GTelefono;
	private GestorMail GMail;
	private GestorDomicilio GDomicilio;
	private Session sesionDeHilo;

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
			setSession();
			setTransaction();
			this.validar(object);
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

	@Override
	public ArrayList<Docente> getByExample(Docente example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Docente> listaDocentesDevolver = (ArrayList<Docente>) docenteDAO.findByExample(example);
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
			return listaDocentesDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los docentes: " + ex.getMessage());
		}
	}

	@Override
	public Boolean existeDocumento(String tipo, Long numero) throws Exception {
		Docente docenteEjemplo = new Docente();
		docenteEjemplo.setTipoDocumento(tipo);
		docenteEjemplo.setNroDocumento(numero);
		ArrayList<Docente> ejemplos = this.getByExample(docenteEjemplo);		
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
		Docente docenteEjemplo = new Docente();
		docenteEjemplo.setNombreUsuario(nombreUsuario);
		ArrayList<Docente> ejemplos = this.getByExample(docenteEjemplo);		
		return (ejemplos.isEmpty() ? false : true);
	}

	@Override
	public Boolean existeCuil(Long cuil) throws Exception {
		Docente docenteEjemplo = new Docente();
		docenteEjemplo.setCuil(cuil);
		ArrayList<Docente> ejemplos = this.getByExample(docenteEjemplo);		
		return (ejemplos.isEmpty() ? false : true);
	}

	@Override
	public void validar(Docente object) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
