package ar.com.santalucia.servicio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.sistema.login.GestorLogin;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorPersonal;
import ar.com.santalucia.dominio.dto.DirectivoDTO;
import ar.com.santalucia.dominio.modelo.sistema.login.Login;
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

// Último modificador: Ariel Ramirez @ 25-11-2015 18:19

public class ServicioDirectivo extends ServicioUsuario<Personal> {

	private GestorPersonal gPersonal;
	private ServicioLogin sLogin;

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
					if (personal.getRolDirectivo()) {
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
			//example.setRol(Personal.DIRECTIVO);
			//listaDevolver.addAll(gPersonal.getByExample(example));
			//example.setRol(Personal.DOCENTE_DIRECTIVO);
			example.setRolDirectivo(true);
			listaDevolver.addAll(gPersonal.getByExample(example));
			return listaDevolver;
		} catch (Exception ex) {
			throw new Exception("Hubo un problema al obtener el listado de DIRECTIVOS: " + ex.getMessage());
		}
	}

	@Override
	public boolean addUsuario(Personal usuario) throws Exception {
		try {
			sLogin = new ServicioLogin();	
			if (usuario.getIdUsuario() == null) {
				gPersonal.add(usuario);
				sLogin.addLogin(usuario.getNroDocumento(), Login.DIRECTIVO);
				if(usuario.getRolDocente() == true){
					sLogin.addLogin(usuario.getNroDocumento(), Login.DOCENTE); // Parche por si viene con los dos roles (a modo de prueba)
				}
			} else {
				gPersonal.modify(usuario);
				if(usuario.getRolDocente()){
					GestorLogin gLogin = new GestorLogin();
					if ((gLogin.getByExample(new Login(null,usuario.getNroDocumento(),null,null,null,Personal.DIRECTIVO,true))).size() == 0){
						sLogin.addLogin(usuario.getNroDocumento(), Login.DIRECTIVO);
					}
				}
			}
			return true;
		} catch (SugerenciaPersonalException ex) {
			throw ex;
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("Hubo un problema al agregar o modificar el DIRECTIVO: " + ex.getMessage());
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
					"Hubo un problema al obtener el listado de TELÉFONOS del DIRECTIVO: " + ex.getMessage());
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
					"Hubo un problema al obtener el listado de E-MAILs del DIRECTIVO: " + ex.getMessage());
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
					"Hubo un problema al obtener el listado de TÍTULOS del DIRECTIVO: " + ex.getMessage());
		}
		return titulos;
	}


	/*
	 * 
	 */

	@Override
	public boolean removeUsuario(Personal usuario) throws Exception {
		try {
			/*if(usuario.getRol().equals(Personal.DOCENTE_DIRECTIVO)){
				usuario.setRol(Personal.DOCENTE);
			}else{
				usuario.setActivo(false);
			}*/
			if(usuario.getRolDirectivo() && usuario.getRolDocente()){
				usuario.setRolDirectivo(false);
			}else{
				usuario.setActivo(false);
			}
			gPersonal.modify(usuario);
			return true;
		} catch (Exception ex) {
			throw new Exception("Hubo un problema al eliminar el DIRECTIVO: " + ex.getMessage());
		}
	}

	@Override
	public void closeSession() throws Exception {
		gPersonal.closeSession();
	}

	public ArrayList<DirectivoDTO> listDirectivosDTO() throws Exception {
		try {
			Personal directivoEx = new Personal();
			directivoEx.setRolDirectivo(true);
			ArrayList<Personal> listaDirectivos = new ArrayList<Personal>();
			ArrayList<DirectivoDTO> listaDirectivosDTO = new ArrayList<DirectivoDTO>();
			listaDirectivos.addAll(gPersonal.getByExample(directivoEx));
			for (Personal d : listaDirectivos) {
				DirectivoDTO directivoDTO = new DirectivoDTO();
				directivoDTO.setIdUsuario(d.getIdUsuario());
				directivoDTO.setNombre(d.getNombre());
				directivoDTO.setApellido(d.getApellido());
				directivoDTO.setNroDocumento(d.getNroDocumento());
				listaDirectivosDTO.add(directivoDTO);
			}
			return listaDirectivosDTO;
		} catch (Exception ex) {
			throw new Exception("No se pudo listar los directivos: " + ex.getMessage());
		}
	}
	
	
	
	
	@Override
	public Personal getUsuarioByDni(Long dni) throws Exception {
		List<Personal> directivoLista = new ArrayList<Personal>();
		directivoLista = gPersonal.getByExample(new Personal(dni,null,null,null,null,null,null,null,null,null,true,null,null,true,null)); //Tipo docente anulado para que busque los dos si existiera
		for (Personal p: directivoLista){
			return p;
		}
		throw new Exception ("Ocurrió un error al recuperar los datos del directivo");
	}

}
