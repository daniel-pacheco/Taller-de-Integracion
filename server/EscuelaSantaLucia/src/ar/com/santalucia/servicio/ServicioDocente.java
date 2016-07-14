package ar.com.santalucia.servicio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.sistema.login.GestorLogin;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorPersonal;
import ar.com.santalucia.dominio.dto.DocenteMateriasDTO;
import ar.com.santalucia.dominio.dto.MateriaAreaCondDocenteDTO;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.sistema.login.Login;
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
				gPersonal.add(usuario);
				sLogin.addLogin(usuario.getNroDocumento(), Login.DOCENTE);
				if(usuario.getRolDirectivo() == true){
					sLogin.addLogin(usuario.getNroDocumento(), Login.DIRECTIVO); // Parche pr si viene con los dos roles, aunque no seria tan común, como sí lo tiene directivo (a modo de prueba)
				}
			} else {
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
							materias.add(new MateriaAreaCondDocenteDTO(m.getNombre(), MateriaAreaCondDocenteDTO.TITULAR, m.getArea().getNombre()));
							docenteDTO.setListaMaterias(materias);
						}
						if ((m.getDocenteSuplente() != null) && (m.getDocenteSuplente().equals(p))) {
							materias.add(new MateriaAreaCondDocenteDTO(m.getNombre(), MateriaAreaCondDocenteDTO.SUPLENTE, m.getArea().getNombre()));
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
		docenteLista = gPersonal.getByExample(new Personal(dni,null,null,null,null,null,null,null,null,null,true,null,null,null,true)); //Tipo directivo anulado para que busque los dos si existiera
		for (Personal d: docenteLista){
			return d;
		}
		throw new Exception ("Ocurrió un error al recuperar los datos del docente");
	}

	@Override
	public Domicilio getDomicilio(Long idUsuario) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}	
}
