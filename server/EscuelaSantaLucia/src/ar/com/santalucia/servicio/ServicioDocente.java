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
import ar.com.santalucia.excepciones.SugerenciaPersonalException;
import ar.com.santalucia.excepciones.ValidacionException;

/**
 * 
 * @author Ariel Ramirez
 *
 * @version 1.0
 */

// Último modificador: Ariel Ramirez @ 25-11-2015 18:18

public class ServicioDocente extends ServicioUsuario<Personal> {

	private GestorPersonal gPersonal;

	public ServicioDocente() throws Exception {
		super();
		gPersonal = new GestorPersonal();
	}

	@Override
	public Personal getUsuario(Long id) throws Exception {
		if (id != null) {
			try {
				Personal usuario = new Personal();
				usuario = (Personal) gPersonal.getById(id);
				if (usuario != null) {
					if ((usuario.getRol().equals(Personal.DOCENTE))
							|| (usuario.getRol().equals(Personal.DOCENTE_DIRECTIVO))) {
						return usuario;
					} else {
						return null;
					}
					//antes de devolver, comprobar que sea rol DOCENTE o DOCENTE/DIRECTIVO (Realizado), previamente comprobar no null
				}
				return null;
			} catch (Exception ex) {
				throw new Exception("Hubo un problema al obtener el DOCENTE: " + ex.getMessage());
			}
		}
		return null;
	}

	@Override
	public List<Personal> getUsuarios(Personal example) throws Exception {
		try {
			// hacer dos llamadas al get by example
			// la primera hacer el example con el atributo rol=DOCENTE
			// la segunda hacer el example con el atributo rol=DOCENTE/DIRECTIVO
			// unir ambas listas
			// devolver esa lista
			List<Personal> listaDevolver = new ArrayList<Personal>();
			example.setRol(Personal.DOCENTE);
			listaDevolver.addAll(gPersonal.getByExample(example));
			example.setRol(Personal.DOCENTE_DIRECTIVO);
			listaDevolver.addAll(gPersonal.getByExample(example));
			return listaDevolver;
		} catch (Exception ex) {
			throw new Exception("Hubo un problema al obtener el listado de DOCENTES: " + ex.getMessage());
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
			throw new Exception("Hubo un problema al agregar o modificar el DOCENTE: " + ex.getMessage());
		}
	}

	@Override
	public Set<Telefono> getTelefonos(Long idUsuario) throws Exception {
		Set<Telefono> telefonos = new HashSet<Telefono>();
		telefonos = null;
		try {
			Personal docente = new Personal();
			if ((docente = getUsuario(idUsuario)) != null) {
				telefonos = docente.getListaTelefonos();
			}
		} catch (Exception ex) {
			throw new Exception(
					"Hubo un problema al obtener el listado de TELÉFONOS del DOCENTE: " + ex.getMessage());
		}
		return telefonos;
	}

	@Override
	public Set<Mail> getMails(Long idUsuario) throws Exception {
		Set<Mail> mails = new HashSet<Mail>();
		mails = null;
		try {
			Personal docente = new Personal();
			if ((docente = getUsuario(idUsuario)) != null) {
				mails = docente.getListaMails();
			}
		} catch (Exception ex) {
			throw new Exception(
					"Hubo un problema al obtener el listado de E-MAILs del DOCENTE: " + ex.getMessage());
		}
		return mails;
	}

	@Override
	public Set<Titulo> getTitulos(Long idUsuario) throws Exception {
		Set<Titulo> titulos = new HashSet<Titulo>();
		titulos = null;
		try {
			Personal docente = new Personal();
			if ((docente = getUsuario(idUsuario)) != null) {
				titulos = docente.getListaTitulos();
			}
		} catch (Exception ex) {
			throw new Exception(
					"Hubo un problema al obtener el listado de TÍTULOS del DOCENTE: " + ex.getMessage());
		}
		return titulos;
	}


	/*
	 * 
	 */

	@Override
	public boolean removeUsuario(Personal usuario) throws Exception {
		try {
			if(usuario.getRol().equals(Personal.DOCENTE_DIRECTIVO)){
				usuario.setRol(Personal.DIRECTIVO);
			}else{
				usuario.setActivo(false);
			}
			gPersonal.modify(usuario);
			return true;
		} catch (Exception ex) {
			throw new Exception("Hubo un problema al eliminar el DOCENTE: " + ex.getMessage());
		}
	}

	@Override
	public void closeSession() throws Exception {
		gPersonal.closeSession();

	}

}
