package ar.com.santalucia.aplicacion.gestor.usuario;

import java.util.ArrayList;
import ar.com.santalucia.accesodatos.dao.usuario.DocenteHome;
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
import ar.com.santalucia.excepciones.SugerenciaDirectivoException;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.validaciones.IValidacionUsuarioDocDir;

/**
 * Clase gestor de Docentes
 * 
 * @author Eric
 * @version 2.0
 *
 */

// UltimoModificador: Eric Pennachini @ 13-11-15 18:12

public class GestorDocente extends GestorUsuario implements IValidacionUsuarioDocDir {
	private DocenteHome docenteDAO;
	private GestorTitulo GTitulo;

	public GestorDocente() throws Exception {
		super();
		try {
			docenteDAO = new DocenteHome();
			GTitulo = new GestorTitulo();
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
			setSession();
			setTransaction();
			docenteDAO.persist(docente);
			sesionDeHilo.getTransaction().commit();
		} catch (SugerenciaDirectivoException ex) {
			throw ex;
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
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
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el objeto: " + ex.getMessage());
		}
	}

	// @Override
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
	public ArrayList<Docente> List() throws Exception {
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

	public Boolean existeDniEnDirectivo(Docente docente) throws Exception {
		GestorDirectivo gDir = new GestorDirectivo();
		Directivo directivoEjemplo = new Directivo();
		directivoEjemplo.setNroDocumento(docente.getNroDocumento());
		ArrayList<Directivo> listaDirectivo = gDir.getByExample(directivoEjemplo);
		return (listaDirectivo.isEmpty() ? false : true);
	}

	public Boolean datosIguales(Docente docente) throws Exception {
		GestorDirectivo gDir = new GestorDirectivo();
		Directivo directivoEjemplo = new Directivo();
		directivoEjemplo.setNroDocumento(docente.getNroDocumento());
		ArrayList<Directivo> listaDirectivo = gDir.getByExample(directivoEjemplo);
		for (Directivo d : listaDirectivo) 
			if ((d.getApellido().equals(docente.getApellido())) 
					&& (d.getCuil().equals(docente.getCuil()))
					&& (d.getDomicilio().equals(docente.getDomicilio()))
					&& (d.getFechaNacimiento().getTime() == docente.getFechaNacimiento().getTime() )
					&& (d.getListaMails().contains((docente.getListaMails())))
					&& (d.getListaTelefonos().equals(docente.getListaTelefonos()))
					&& (d.getListaTitulos().equals(docente.getListaTitulos()))
					&& (d.getNombre().equals(docente.getNombre()))
					&& (d.getNombreUsuario().equals(docente.getNombreUsuario()))
					&& (d.getNroDocumento().equals(docente.getNroDocumento()))
					&& (d.getSexo().equals(docente.getSexo()))
					&& (d.getTipoDocumento().equals(docente.getTipoDocumento()))) {
				return true;
			} else {
				return false;
			}
		return false;
		}

	@Override
	public void validar(Object object) throws Exception {
		Docente docente = (Docente) object;
		ValidacionException exception = new ValidacionException();
		SugerenciaDirectivoException sugDirException = new SugerenciaDirectivoException();

		if (existeDniEnDirectivo(docente)) {
			if (!datosIguales(docente)) {
				GestorDirectivo gDir = new GestorDirectivo();
				Directivo directivo = new Directivo();
				directivo.setNroDocumento(docente.getNroDocumento());
				ArrayList<Directivo> listaDirectivo = gDir.getByExample(directivo);
				for (Directivo d : listaDirectivo) {
					sugDirException.setDirectivoSugerido(d);
					sugDirException.setMensaje("El documento ya pertenece a un directivo. ");
					throw sugDirException;
				}
			}
		} else {
			if (existeDocumento(docente)) {
				exception.addMensajeError("El documento ya existe");
				throw exception;
			}
		}
	}

	@Override
	public ArrayList getByExample(Object example) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
