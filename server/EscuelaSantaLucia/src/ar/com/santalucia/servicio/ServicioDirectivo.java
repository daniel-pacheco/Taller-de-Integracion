package ar.com.santalucia.servicio;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.usuario.GestorDirectivo;
import ar.com.santalucia.dominio.modelo.usuarios.Directivo;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;
import ar.com.santalucia.excepciones.ValidacionException;

/**
 * 
 * @author Ariel Ramirez
 *
 * @version 1.0
 */

// �ltimo modificador: Ariel Ramirez @ 12-10-2015 21:10

public class ServicioDirectivo extends ServicioUsuario<Directivo> {

	private GestorDirectivo gDirectivo;

	public ServicioDirectivo() throws Exception {
		super();
		gDirectivo = new GestorDirectivo();
	}

	@Override
	public Directivo getUsuario(Long id) throws Exception {
		if (id > 0) {
			try {
				return (Directivo) gDirectivo.getById(id);
			} catch (Exception ex) {
				throw new Exception("Servicio: problemas. " + ex.getMessage());
			}
		}
		return null;
	}

	@Override
	public List<Directivo> getUsuarios(Directivo example) throws Exception {
		try {
			return gDirectivo.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("Servicio: problemas. " + ex.getMessage());
		}
	}

	@Override
	public boolean addUsuario(Directivo usuario) throws Exception {
		try {
			if (usuario.getIdUsuario() == null) {
				gDirectivo.add(usuario);
			} else {
				gDirectivo.modify(usuario);
			}
			;
			return true;
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
			Directivo directivo = new Directivo();
			if ((directivo = getUsuario(idUsuario)) != null) { 
				telefonos = directivo.getListaTelefonos(); 
			}
		} catch (Exception ex) {
			throw new Exception(
					"Servicio: Ha ocurrido un problema al intentar recuperar los tel�fonos . " + ex.getMessage());
		}
		return telefonos;
	}

	@Override
	public Set<Mail> getMails(Long idUsuario) throws Exception {
		Set<Mail> mails = new HashSet<Mail>();
		mails = null;
		try {
			Directivo directivo = new Directivo();
			if ((directivo = getUsuario(idUsuario)) != null) {
				mails = directivo.getListaMails();
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
			Directivo directivo = new Directivo();
			if ((directivo = getUsuario(idUsuario)) != null) {
				titulos = directivo.getListaTitulos();
			}
		} catch (Exception ex) {
			throw new Exception(
					"Servicio: Ha ocurrido un problema al intentar recuperar los titulos. " + ex.getMessage());
		}
		return titulos;
	}

	@Override
	public boolean modifyUsuario(Directivo usuarioModificado) throws Exception {
		try {
			gDirectivo.modify(usuarioModificado);
			return true;
		} catch (Exception ex) {
			throw new Exception("Servicio modify(): no se pudo completar la operacion. " + ex.getMessage());
		}
	}

	@Override
	public boolean removeUsuario(Directivo usuario) throws Exception {
		try {
			gDirectivo.delete(usuario);
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public void closeSession() throws Exception {
		gDirectivo.closeSession();
	}

}
