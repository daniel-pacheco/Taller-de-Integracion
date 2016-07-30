package ar.com.santalucia.servicio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.sistema.login.GestorLogin;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorPersonal;
import ar.com.santalucia.dominio.dto.DirectivoDTO;
import ar.com.santalucia.dominio.modelo.sistema.login.Login;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
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
				// Crear Set para guardar elementos que vienen con usuario
				Set<Telefono> telefonos = usuario.getListaTelefonos();
				Set<Mail> mails = usuario.getListaMails();
				Set<Titulo> titulos = usuario.getListaTitulos();
				Domicilio domicilio = usuario.getDomicilio();
				// Anulo los contenidos agregado
				usuario.setListaTelefonos(null);
				usuario.setListaMails(null);
				usuario.setListaTitulos(null);
				usuario.setDomicilio(null);
				//Agrego el usuario sin listas. La sesión de hibernarte asigna el id al usuario.
				gPersonal.add(usuario);
				// Persiste los elementos de lista haciendo add y los vincula al usuario
				modificarElementosDeListas(usuario, telefonos, mails, titulos);
				domicilio.setIdDomicilio(null);
				super.gDomicilio.add(domicilio);
				//Se hizo lo mismo con domicilio. Finalmente se hace modify de usuario
				usuario.setDomicilio(domicilio);
				gPersonal.modify(usuario);
				sLogin.addLogin(usuario.getNroDocumento(), Login.DIRECTIVO);
				if(usuario.getRolDocente() == true){
					sLogin.addLogin(usuario.getNroDocumento(), Login.DOCENTE); // Parche por si viene con los dos roles (a modo de prueba)
				}
			} else {
				Long dniViejo = gPersonal.getById(usuario.getIdUsuario()).getNroDocumento();
				ActualizarLoginDocDir(dniViejo, usuario.getNroDocumento());
				
				Set<Telefono> listaTelefonosNueva = usuario.getListaTelefonos();
				if (listaTelefonosNueva.size() > 0) {
					for (Telefono t : listaTelefonosNueva) {
						t.setIdTelefono(null);
					} 
				}
				Set<Mail> listaMailsNueva = usuario.getListaMails();
				if (listaMailsNueva.size() > 0) {
					for (Mail m : usuario.getListaMails()) {
						m.setIdMail(null);
					} 
				}
				Set<Titulo> listaTitulosNueva = usuario.getListaTitulos();
				if (listaTitulosNueva.size() > 0) {
					for (Titulo t : usuario.getListaTitulos()) {
						t.setIdTitulo(null);
					} 
				}
				Domicilio domicilioNuevo = usuario.getDomicilio();
				domicilioNuevo.setIdDomicilio(null);
				Set<Telefono> listaTelefonosPersis = new HashSet<Telefono>();
				Set<Mail> listaMailsPersis = new HashSet<Mail>();
				Set<Titulo> listaTitulosPersis = new HashSet<Titulo>();
				Domicilio domicilioPersis = new Domicilio();
				listaTelefonosPersis = this.getTelefonos(usuario.getIdUsuario());
				listaMailsPersis = this.getMails(usuario.getIdUsuario());
				listaTitulosPersis = this.getTitulos(usuario.getIdUsuario());
				domicilioPersis = this.getDomicilio(usuario.getIdUsuario());
				usuario.setListaTelefonos(null);
				usuario.setListaMails(null);
				usuario.setListaTitulos(null);
				usuario.setDomicilio(null);
				gPersonal.modify(usuario);
				if (listaTelefonosPersis.size() > 0) {
					for (Telefono t : listaTelefonosPersis) {
						super.gTelefono.delete(t);
					} 
				}
				if (listaMailsPersis.size() > 0) {
					for (Mail m : listaMailsPersis) {
						super.gMail.delete(m);
					} 
				}
				if (listaTitulosPersis.size() > 0) {
					for (Titulo t : listaTitulosPersis) {
						super.gTitulo.delete(t);
					} 
				}
				if (domicilioPersis != null) {
					super.gDomicilio.delete(domicilioPersis);
				}
				modificarElementosDeListas(usuario, listaTelefonosNueva, listaMailsNueva, listaTitulosNueva);
				usuario.setDomicilio(domicilioNuevo);
				super.gDomicilio.add(usuario.getDomicilio());
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

	/**
	 * 
	 * @param usuario
	 * @param telefonos
	 * @param mails
	 * @param domicilio
	 * @throws Exception
	 */
	private void modificarElementosDeListas(Personal usuario, Set<Telefono> telefonos, Set<Mail> mails, Set<Titulo> titulos) throws Exception {
		if (telefonos.size() > 0) {
			for (Telefono t : telefonos) {
				//t.setIdTelefono(null);
				super.gTelefono.add(t);
			} 
		}
		if (mails.size() > 0) {
			for (Mail m : mails) {
				//m.setIdMail(null);
				super.gMail.add(m);
			} 
		}
		if (titulos.size() > 0) {
			for (Titulo t : titulos) {
				//t.setIdTitulo(null);
				super.gTitulo.add(t);
			} 
		}
		// agrego al alumno las listas de tel., mail y dom. que en teoría ya tienen id los elementos.
		usuario.setListaTelefonos(telefonos);
		usuario.setListaMails(mails);
		usuario.setListaTitulos(titulos);
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
			directivoEx.setActivo(true);
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
		directivoLista = gPersonal.getByExample(new Personal(dni,null,null,null,null,null,null,null,null,null,null,true,null,null,true,null)); //Tipo docente anulado para que busque los dos si existiera
		for (Personal p: directivoLista){
			return p;
		}
		throw new Exception ("Ocurrió un error al recuperar los datos del directivo");
	}

	@Override
	public Domicilio getDomicilio(Long idUsuario) throws Exception {
		Domicilio domicilio = new Domicilio();
		try {
			Personal personal = new Personal();
			if ((personal = getUsuario(idUsuario)) != null) {
				domicilio = personal.getDomicilio();
			}
			return domicilio;
		} catch (Exception ex) {
			throw new Exception("Hubo un problema al obtener el DOMICILIO del Directivo: " + ex.getMessage());
		}
	}
	
	/**
	 * Actualiza la tabla de Login para usuarios tipo Personal.
	 * @param dniViejo
	 * @param dniNuevo
	 * @throws Exception
	 */
	private void ActualizarLoginDocDir(Long dniViejo, Long dniNuevo) throws Exception{
		try{
		GestorLogin gLogin = new GestorLogin();
		
		List<Login> auxDir = gLogin.getByExample(new Login(null,dniViejo,null,null,null,Login.DIRECTIVO,null));
		if(auxDir.size() == 1){
			sLogin.actualizarUsuario(dniViejo, dniNuevo, Login.DIRECTIVO);	
		}
		List<Login> auxDoc = gLogin.getByExample(new Login(null,dniViejo,null,null,null,Login.DOCENTE,null));
		if(auxDoc.size() == 1){
			sLogin.actualizarUsuario(dniViejo, dniNuevo, Login.DOCENTE);	
		}	
		}catch (Exception ex){
			throw ex;
		}
	}
	
}
