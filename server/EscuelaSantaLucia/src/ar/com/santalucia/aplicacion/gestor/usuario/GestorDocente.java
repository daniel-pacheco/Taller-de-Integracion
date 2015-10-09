package ar.com.santalucia.aplicacion.gestor.usuario;

import java.util.ArrayList;
import java.util.Set;

import org.hibernate.Session;

import ar.com.santalucia.accesodatos.dao.usuario.DocenteHome;
import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IGestor;
import ar.com.santalucia.dominio.modelo.usuarios.Docente;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.validaciones.IValidacionUsuarioAlumno;
import ar.com.santalucia.validaciones.IValidacionUsuarioDocDir;

/**
 * Clase gestor de Docentes
 * 
 * @author Eric
 * 
 * @version 1.1
 *
 */

// UltimoModificador: Eric Pennachini @ 07-08-15 19:40

public class GestorDocente extends Gestor<Docente> implements IValidacionUsuarioDocDir{
	private Docente docente;
	private DocenteHome docenteDAO;
	private Session sesionDeHilo;

	public GestorDocente() throws Exception {
		try {
			sesionDeHilo = HibernateUtil.getSessionFactory().getCurrentSession();
			docenteDAO = new DocenteHome();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar la persistencia.");
		}
	}

	@Override
	public void add(Docente object) throws Exception {
		try {
			sesionDeHilo.beginTransaction();
			docenteDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al persistir el objeto");
		}
	}

	@Override
	public void modify(Docente object) throws Exception {
		try {
			sesionDeHilo.beginTransaction();
			docenteDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al actualizar el objeto");
		}
	}

	@Override
	public void delete(Docente object) throws Exception {
		try {
			sesionDeHilo.beginTransaction();
			docenteDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al eliminar el objeto");
		}
	}

	@Override
	public Docente getById(Long id) throws Exception {
		try {
			Docente docenteDevolver = new Docente();
			docenteDevolver = docenteDAO.findById(id);
			return docenteDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al buscar el objeto por su ID");
		}
	}

	@Override
	public ArrayList<Docente> getByExample(Docente example) throws Exception {
		try {
			ArrayList<Docente> listaDocentesDevolver = (ArrayList<Docente>) docenteDAO.findByExample(example);
			return listaDocentesDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado");
		}
	}

	@Override
	public ArrayList<Docente> List() throws Exception{
		try {
			Docente criterioVacio = new Docente();
			ArrayList<Docente> listaDocentesDevolver = new ArrayList<Docente>();
			listaDocentesDevolver = (ArrayList<Docente>) docenteDAO.findByExample(criterioVacio);
			return listaDocentesDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los docentes");
		}
	}

	@Override
	public void existeDocumento(String tipo, Long numero) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void existeNombreUsuario(String nombreUsuario) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void existeCuil(Long cuil) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void existeMail(Set<Mail> mail) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validar(Docente object) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
