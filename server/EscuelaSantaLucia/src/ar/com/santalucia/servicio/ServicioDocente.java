package ar.com.santalucia.servicio;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.usuario.GestorDocente;
import ar.com.santalucia.dominio.modelo.usuarios.Docente;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;
import ar.com.santalucia.excepciones.SugerenciaDirectivoException;
import ar.com.santalucia.excepciones.ValidacionException;

/**
 * 
 * @author Ariel Ramirez
 *
 * @version 1.0
 */

// Último modificador: Ariel Ramirez @ 14-10-2015 16:58

public class ServicioDocente extends ServicioUsuario<Docente> {

	private GestorDocente gDocente;

	public ServicioDocente() throws Exception {
		super();
		gDocente = new GestorDocente();
	}

	@Override
	public Docente getUsuario(Long id) throws Exception {
		if (id > 0) {
			try {
				return (Docente) gDocente.getById(id);
			} catch (Exception ex) {
				throw new Exception("Servicio: problemas. " + ex.getMessage());
			}
		}
		return null;
	}

	@Override
	public List<Docente> getUsuarios(Docente example) throws Exception {
		try {
			return gDocente.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("Servicio: problemas. " + ex.getMessage());
		}
	}

	@Override
	public boolean addUsuario(Docente usuario) throws Exception {
		try {
			if (usuario.getIdUsuario() == null) {
				gDocente.add(usuario);
			} else {
				gDocente.modify(usuario);
			}
			;
			return true;
		} catch (SugerenciaDirectivoException ex) {
			throw ex;
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("Servicio add(): no se pudo completar la operacion. " + ex.getMessage());
		}
	}

	@Override
	public Set<Telefono> getTelefonos(Long idUsuario) throws Exception {
		Set<Telefono> telefonos = new HashSet<Telefono>();
		telefonos = null;
		try {
			Docente docente = new Docente();
			if ((docente = getUsuario(idUsuario)) != null) {
				telefonos = docente.getListaTelefonos();
			}
		} catch (Exception ex) {
			throw new Exception(
					"Servicio: Ha ocurrido un problema al intentar recuperar los teléfonos . " + ex.getMessage());
		}
		return telefonos;
	}

	@Override
	public Set<Mail> getMails(Long idUsuario) throws Exception {
		Set<Mail> mails = new HashSet<Mail>();
		mails = null;
		try {
			Docente docente = new Docente();
			if ((docente = getUsuario(idUsuario)) != null) {
				mails = docente.getListaMails();
			}
		} catch (Exception ex) {
			throw new Exception(
					"Servicio: Ha ocurrido un problema al intentar recuperar los mails. " + ex.getMessage());
		}
		return mails;
	}

	@Override
	public Set<Titulo> getTitulos(Long idUsuario) throws Exception {
		Set<Titulo> titulos = new HashSet<Titulo>();
		titulos = null;
		try {
			Docente docente = new Docente();
			if ((docente = getUsuario(idUsuario)) != null) {
				titulos = docente.getListaTitulos();
			}
		} catch (Exception ex) {
			throw new Exception(
					"Servicio: Ha ocurrido un problema al intentar recuperar los titulos. " + ex.getMessage());
		}
		return titulos;
	}

	@Override
	public boolean modifyUsuario(Docente usuarioModificado) throws Exception {
		try {
			gDocente.modify(usuarioModificado);
			return true;
		} catch (Exception ex) {
			throw new Exception("Servicio modify(): no se pudo completar la operacion. " + ex.getMessage());
		}
	}

	@Override
	public boolean removeUsuario(Docente usuario) throws Exception {
		try {
			gDocente.delete(usuario);
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public void closeSession() throws Exception {
		gDocente.closeSession();

	}

}
