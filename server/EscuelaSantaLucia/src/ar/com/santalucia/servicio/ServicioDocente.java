package ar.com.santalucia.servicio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.sistema.login.GestorLogin;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorPersonal;
import ar.com.santalucia.dominio.dto.DocenteMateriasDTO;
import ar.com.santalucia.dominio.dto.MateriaAreaCondDocenteDTO;
import ar.com.santalucia.dominio.dto.MateriaDTO;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Materia;
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

// Último modificador: Ariel Ramirez @ 12-06-2016 20:47

public class ServicioDocente extends ServicioUsuario<Personal> {

	private GestorPersonal gPersonal;
	private ServicioLogin sLogin;

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
					if (usuario.getRolDocente()) {
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
			/*example.setRol(Personal.DOCENTE);
			listaDevolver.addAll(gPersonal.getByExample(example));
			example.setRol(Personal.DOCENTE_DIRECTIVO);*/
			example.setRolDocente(true);
			listaDevolver.addAll(gPersonal.getByExample(example));
			return listaDevolver;
		} catch (Exception ex) {
			throw new Exception("Hubo un problema al obtener el listado de DOCENTES: " + ex.getMessage());
		}
	}

	@Override
	public boolean addUsuario(Personal usuario) throws Exception {
		try {
			sLogin = new ServicioLogin();
			if (usuario.getIdUsuario() == null) {
				Set<Telefono> telefonos = usuario.getListaTelefonos();
				Set<Mail> mails = usuario.getListaMails();
				Set<Titulo> titulos = usuario.getListaTitulos();
				Domicilio domicilio = usuario.getDomicilio();
				usuario.setListaTelefonos(null);
				usuario.setListaMails(null);
				usuario.setListaTitulos(null);
				usuario.setDomicilio(null);
				gPersonal.add(usuario);
				modificarElementosDeListas(usuario, telefonos, mails, titulos);
				super.gDomicilio.add(domicilio);
				usuario.setDomicilio(domicilio);
				gPersonal.modify(usuario);
				sLogin.addLogin(usuario.getNroDocumento(), Login.DOCENTE);
				if(usuario.getRolDirectivo() == true){
					sLogin.addLogin(usuario.getNroDocumento(), Login.DIRECTIVO); // Parche pr si viene con los dos roles, aunque no seria tan común, como sí lo tiene directivo (a modo de prueba)
				}
			} else {
				Long dniViejo = gPersonal.getById(usuario.getIdUsuario()).getNroDocumento();
				ActualizarLoginDocDir(dniViejo, usuario.getNroDocumento());
				
				if(usuario.getRolDirectivo()){ //Actualiza la tabla de login 
					sLogin.actualizarUsuario(dniViejo, usuario.getNroDocumento(), Login.DIRECTIVO);	
				}
				if(usuario.getRolDocente()){ //Actualiza la tabla de login 
					sLogin.actualizarUsuario(dniViejo, usuario.getNroDocumento(), Login.DOCENTE);
				}
				
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
				//Si viene por modify, puede venir con dos roles!
				//Buscamos la entrada de login con rol Directivo. Si no existe, la creamos.
				if(usuario.getRolDirectivo()){
					GestorLogin gLogin = new GestorLogin();
					if ((gLogin.getByExample(new Login(null,usuario.getNroDocumento(),null,null,null,Personal.DOCENTE,true))).size() == 0){
						sLogin.addLogin(usuario.getNroDocumento(), Login.DOCENTE);
					}
				}
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
				super.gTelefono.add(t);
			} 
		}
		if (mails.size() > 0) {
			for (Mail m : mails) {
				super.gMail.add(m);
			} 
		}
		if (titulos.size() > 0) {
			for (Titulo t : titulos) {
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
			if(usuario.getRolDocente() && usuario.getRolDirectivo()){
				usuario.setRolDocente(false);
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
	
	/*
	 * 
	 */
	
	public ArrayList<DocenteMateriasDTO> listDocentesMateriasDTO() throws Exception {
		ArrayList<DocenteMateriasDTO> listaDocentesMateriasDTO = new ArrayList<DocenteMateriasDTO>();
		ArrayList<Personal> listaDocentes = new ArrayList<Personal>();
		ServicioAcademico servicioAcademico = new ServicioAcademico();
		try {
			Personal personal = new Personal();
			personal.setActivo(true);
			personal.setRolDocente(true);
			listaDocentes = gPersonal.getByExample(personal);
			for (Personal p: listaDocentes) {
				DocenteMateriasDTO docenteDTO = new DocenteMateriasDTO(
						p.getIdUsuario(), p.getNroDocumento(), p.getNombre(), p.getApellido(), null, null);
				Anio anioEx = new Anio();
				List<Anio> listaAnios = new ArrayList<Anio>();
				listaAnios = servicioAcademico.getAnios(anioEx);
				for (Anio a: listaAnios) {
					for (Materia m: a.getListaMaterias()) {
						ArrayList<String> anios = (docenteDTO.getListaMaterias() == null) ? new ArrayList<String>() : docenteDTO.getListaAnios();
						if (!anios.contains(a.getNombre())) {
							anios.add(a.getNombre());
						}
						docenteDTO.setListaAnios(anios);
						ArrayList<MateriaAreaCondDocenteDTO> materias = (docenteDTO.getListaMaterias() == null) 
																? new ArrayList<MateriaAreaCondDocenteDTO>() 
																: docenteDTO.getListaMaterias();
						if ((m.getDocenteTitular() != null) && (m.getDocenteTitular().equals(p))) {
							materias.add(new MateriaAreaCondDocenteDTO(m.getIdMateria(),m.getNombre(), MateriaAreaCondDocenteDTO.TITULAR,m.getArea().getIdArea(), m.getArea().getNombre()));
							docenteDTO.setListaMaterias(materias);
						}
						if ((m.getDocenteSuplente() != null) && (m.getDocenteSuplente().equals(p))) {
							materias.add(new MateriaAreaCondDocenteDTO(m.getIdMateria(), m.getNombre(), MateriaAreaCondDocenteDTO.SUPLENTE,m.getArea().getIdArea(), m.getArea().getNombre()));
							docenteDTO.setListaMaterias(materias);
						}
					}
				}
				listaDocentesMateriasDTO.add(docenteDTO);
			}
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los docentes y materias: " + ex.getMessage());
		}
		return listaDocentesMateriasDTO;
	}

	@Override
	public Personal getUsuarioByDni(Long dni) throws Exception {
		List<Personal> docenteLista = new ArrayList<Personal>();
		docenteLista = gPersonal.getByExample(new Personal(dni,null,null,null,null,null,null,null,null,null,null,true,null,null,null,true)); //Tipo directivo anulado para que busque los dos si existiera
		for (Personal d: docenteLista){
			return d;
		}
		throw new Exception ("Ocurrió un error al recuperar los datos del docente");
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

	public List<MateriaDTO> ObtenerMateriasDictadas(Long idPersonal) throws ValidacionException, Exception{
		// Solicitar el listado completo de DTO de materias
		// Buscar el personal para comparar los string
		// poner las coincidencias en un set y luego devolver una lista
		try{
			ValidacionException vEx = new ValidacionException();
			ServicioAcademico sAcademico = new ServicioAcademico();
			Personal docente = getUsuario(idPersonal); //OBTENER EL PERSONAL
			if(docente == null){
				vEx.addMensajeError("No se encontró el docente.");
				throw vEx;
			}
			List<MateriaDTO> listadoCompleto = sAcademico.getMateriasDTO();
			Set<MateriaDTO> listadoIntermedio = new HashSet<MateriaDTO>();
			List<MateriaDTO> listadoDevolver = new ArrayList<MateriaDTO>();
			for(MateriaDTO dmDTO : listadoCompleto){ // lleno el set de materias que dicata el docente (set para filtrar) 
				if( dmDTO.getDocenteTitular().equals(docente.toString()) || dmDTO.getDocenteSuplente().equals(docente.toString()) ){
					listadoIntermedio.add(dmDTO);
				}
			}
			listadoDevolver.addAll(listadoIntermedio); // Paso a lista lo que resulta del set
			return listadoDevolver;
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}
	
}
