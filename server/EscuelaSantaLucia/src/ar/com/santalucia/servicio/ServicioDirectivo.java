package ar.com.santalucia.servicio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.usuario.GestorPersonal;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;
import ar.com.santalucia.excepciones.SugerenciaDocenteException;
import ar.com.santalucia.excepciones.SugerenciaPersonalException;
import ar.com.santalucia.excepciones.ValidacionException;

/**
 * 
 * @author Ariel Ramirez
 *
 * @version 1.0
 */

// Último modificador: Ariel Ramirez @ 25-11-2015 18:19

public class ServicioDirectivo extends ServicioUsuario<Personal> {

	private GestorPersonal gPersonal;

	public ServicioDirectivo() throws Exception {
		super();
		gPersonal = new GestorPersonal();
	}

	@Override
	public Personal getUsuario(Long id) throws Exception {
		if (id > 0) {
			try {
				Personal personal = new Personal();
				personal = (Personal) gPersonal.getById(id);
				if (personal != null) {
					if ((personal.getRol().equals(Personal.DIRECTIVO))
							|| (personal.getRol().equals(Personal.DOCENTE_DIRECTIVO))) {
						return personal;
					} else {
						return null;
					}
				}else{
					return null;
				}
				// antes de devolver comprobar que sea rol DIRECTIVO o DOCENTE/DIRECTIVO (Realizado), previamente controlando no null
			} catch (Exception ex) {
				throw new Exception("Servicio: problemas. " + ex.getMessage());
			}
		}
		return null;
	}

	@Override
	public List<Personal> getUsuarios(Personal example) throws Exception {
		try {
			
			// hacer dos llamadas al get by example
			// la primera hacer el example con el atributo rol=DIRECTIVO
			// la segunda hacer el example con el atributo rol=DOCENTE/DIRECTIVO
			// unir ambas listas
			// devolver esa lista
			List<Personal> listaDevolver = new ArrayList<Personal>();
			example.setRol(Personal.DIRECTIVO);
			listaDevolver.addAll(gPersonal.getByExample(example));
			example.setRol(Personal.DOCENTE_DIRECTIVO);
			listaDevolver.addAll(gPersonal.getByExample(example));
			return listaDevolver;
		} catch (Exception ex) {
			throw new Exception("Servicio: problemas. " + ex.getMessage());
		}
	}

	@Override
	public boolean addUsuario(Personal usuario) throws Exception {
		try {
			if (usuario.getIdUsuario() == null) {
				gPersonal.add(usuario);
			} else {
				gPersonal.modify(usuario);
			}
			return true;
		} catch (SugerenciaPersonalException ex) {
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
			Personal personal = new Personal();
			if ((personal = getUsuario(idUsuario)) != null) { 
				telefonos = personal.getListaTelefonos(); 
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
			Personal personal = new Personal();
			if ((personal = getUsuario(idUsuario)) != null) {
				mails = personal.getListaMails();
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
			Personal personal = new Personal();
			if ((personal = getUsuario(idUsuario)) != null) {
				titulos = personal.getListaTitulos();
			}
		} catch (Exception ex) {
			throw new Exception(
					"Servicio: Ha ocurrido un problema al intentar recuperar los titulos. " + ex.getMessage());
		}
		return titulos;
	}

	@Override
	public boolean modifyUsuario(Personal usuarioModificado) throws Exception {
		try {
			gPersonal.modify(usuarioModificado);
			return true;
		} catch (Exception ex) {
			throw new Exception("Servicio modify(): no se pudo completar la operacion. " + ex.getMessage());
		}
	}

	@Override
	public boolean removeUsuario(Personal usuario) throws Exception {
		try {
			//gPersonal.delete(usuario);
			if(usuario.getRol().equals(Personal.DOCENTE_DIRECTIVO)){
				usuario.setRol(Personal.DOCENTE);
			}else{
				usuario.setActivo(false);
			}
			modifyUsuario(usuario);
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public void closeSession() throws Exception {
		gPersonal.closeSession();
	}

}
