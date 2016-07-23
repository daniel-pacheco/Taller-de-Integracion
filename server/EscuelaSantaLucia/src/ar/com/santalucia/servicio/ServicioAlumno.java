package ar.com.santalucia.servicio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ar.com.santalucia.aplicacion.gestor.academico.GestorCurso;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.dominio.dto.AlumnoDTO;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotas;
import ar.com.santalucia.dominio.modelo.sistema.login.Login;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;
import ar.com.santalucia.excepciones.ValidacionException;

// Esta clase es un Facade que permite acceder a todas las operaciones de ABM de alumno,
// incorporando las ABM  para sus compuestos (telefono, mail y dirección)

/**
 * 
 * @author Ariel Ramirez
 * 
 * @version 2.0
 *
 */

// Último modificador: Ariel Ramirez @ 20-07-2016 20:01

public class ServicioAlumno extends ServicioUsuario<Alumno>  {

	private GestorAlumno gAlumno;
	private GestorCurso gCurso;
	private ServicioLogin sLogin;

	public ServicioAlumno() throws Exception {
		super();
		gAlumno = new GestorAlumno();
		gCurso = new GestorCurso();
	}

	@Override
	public void closeSession() throws Exception {
		gAlumno.closeSession();
	}

	@Override
	public Alumno getUsuario(Long id) throws Exception {
		if (id > 0) {
			try {
				return (Alumno) gAlumno.getById(id);
			} catch (Exception ex) {
				throw new Exception("Hubo un problema al obtener el ALUMNO: " + ex.getMessage());
			}
		}
		return null;
	}

	@Override
	public List<Alumno> getUsuarios(Alumno example) throws Exception {
		try {
			return gAlumno.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("Hubo un problema al obtener el listado de ALUMNOS: " + ex.getMessage());
		}
	}
	
	@Override
	public boolean addUsuario(Alumno usuario) throws Exception {
		try {
			sLogin = new ServicioLogin();			//Instanciado para usar sólo aquí
			if (usuario.getIdUsuario() == null) {
				Set<Telefono> telefonos = usuario.getListaTelefonos();
				Set<Mail> mails = usuario.getListaMails();
				Domicilio domicilio = usuario.getDomicilio();
				usuario.setListaTelefonos(null);
				usuario.setListaMails(null);
				usuario.setDomicilio(null);
				gAlumno.add(usuario);
				modificarElementosDeListas(usuario, telefonos, mails);
				super.gDomicilio.add(domicilio);
				usuario.setDomicilio(domicilio);
				gAlumno.modify(usuario);
				sLogin.addLogin(usuario.getNroDocumento(), Login.ALUMNO);
				Curso cursoGen = new Curso();
				cursoGen = gCurso.getByDivision('0');
				cursoGen.getListaAlumnos().add(usuario);
				gCurso.modify(cursoGen);
			} else {
				Long dniViejo = gAlumno.getById(usuario.getIdUsuario()).getNroDocumento(); 
				sLogin.actualizarUsuario(dniViejo, usuario.getNroDocumento(), Login.ALUMNO);
				
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
				Domicilio domicilioNuevo = usuario.getDomicilio();
				domicilioNuevo.setIdDomicilio(null);
				Set<Telefono> listaTelefonosPersis = new HashSet<Telefono>();
				Set<Mail> listaMailsPersis = new HashSet<Mail>();
				Domicilio domicilioPersis = new Domicilio();
				listaTelefonosPersis = this.getTelefonos(usuario.getIdUsuario());
				listaMailsPersis = this.getMails(usuario.getIdUsuario());
				domicilioPersis = this.getDomicilio(usuario.getIdUsuario());
				usuario.setListaTelefonos(null);
				usuario.setListaMails(null);
				usuario.setDomicilio(null);
				gAlumno.modify(usuario);
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
				if (domicilioPersis != null) {
					super.gDomicilio.delete(domicilioPersis);
				}
				modificarElementosDeListas(usuario, listaTelefonosNueva, listaMailsNueva);
				usuario.setDomicilio(domicilioNuevo);
				super.gDomicilio.add(usuario.getDomicilio());
				gAlumno.modify(usuario);
			}
			return true;
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("Hubo un problema al agregar o modificar el ALUMNO: " + ex.getMessage());
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
	private void modificarElementosDeListas(Alumno usuario, Set<Telefono> telefonos, Set<Mail> mails) throws Exception {
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
		// agrego al alumno las listas de tel., mail y dom. que en teoría ya tienen id los elementos.
		usuario.setListaTelefonos(telefonos);
		usuario.setListaMails(mails);
	}
	
	

	@Override
	public Set<Telefono> getTelefonos(Long idUsuario) throws Exception {
		Set<Telefono> telefonos = new HashSet<Telefono>();
		telefonos = null;
		try {
			Alumno alumno = new Alumno();
			if ((alumno = getUsuario(idUsuario)) != null) { // pruebo si el
															// alumno existe,
															// sino no - > Null
				telefonos = alumno.getListaTelefonos(); // getListaTelefonos del
														// alumno que se buscó,
														// si existe
			}
		} catch (Exception ex) {
			throw new Exception(
					"Hubo un problema al obtener el listado de TELÉFONOS del ALUMNO: " + ex.getMessage());
		}
		return telefonos;
	}

	@Override
	public Set<Mail> getMails(Long idUsuario) throws Exception {
		Set<Mail> mails = new HashSet<Mail>();
		mails = null;
		try {
			Alumno alumno = new Alumno();
			if ((alumno = getUsuario(idUsuario)) != null) {
				mails = alumno.getListaMails();
			}
		} catch (Exception ex) {
			throw new Exception(
					"Hubo un problema al obtener el listado de E-MAILs del ALUMNO: " + ex.getMessage());
		}
		return mails;
	}
	
	@Override
	public Domicilio getDomicilio(Long idUsuario) throws Exception {
		Domicilio domicilio = new Domicilio();
		try {
			Alumno alumno = new Alumno();
			if ((alumno = getUsuario(idUsuario)) != null) {
				domicilio = alumno.getDomicilio();
			}
			return domicilio;
		} catch (Exception ex) {
			throw new Exception("Hubo un problema al obtener el DOMICILIO del ALUMNO: " + ex.getMessage());
		}
	}
	

	@Override
	public Set<Titulo> getTitulos(Long idUsuario) {
		// Este método no se implementa para esta clase
		return null;
	}

	@Override
	public boolean removeUsuario(Alumno usuario) throws Exception {
		try {
			// Obtenemos el dto del alumno, y con ello el anio
			// Obtenemos la entidad anio con un getbyexample
			// Recorremos los curso en busca del id de curso (lo hago coincidir con el nombre)
			// obtenemos el id y llamamo a desvinbcularalumnodecurso
			ServicioAcademico sAcademico = new ServicioAcademico();
			Curso curso = new Curso();
			AlumnoDTO aux = getAlumnoByDniMin(usuario.getNroDocumento());
			if(!aux.getCurso().equals("0")){
				Anio anioAux = new Anio(null,aux.getAnio(),null,null,null,null, null, null, true);
				List<Anio> listAnio = sAcademico.getAnios(anioAux);
				Set<Curso> listCurso = new HashSet<Curso>();
				anioAux=listAnio.get(0);
				listCurso = anioAux.getListaCursos();
				for (Curso c: listCurso){
					if (c.getDivision().equals(Character.valueOf(aux.getCurso().charAt(0)))){
						curso = c;
						break;
					}
				}
				if (!curso.getDivision().equals(null) ){
					sAcademico.desvincularAlumnoDeCurso(usuario, curso.getIdCurso());
				}else{
					throw new Exception("No se encontró el curso para desvincular al alumno " +usuario.getNroDocumento());
				}
			}
			usuario.setActivo(false);
			gAlumno.modify(usuario);
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}

	// No se incluyen los métodos para agregar mail, título o teléfono dado a
	// que solo es necesario llamar a this.addUsuario(Alumno usuario)
	
	public ArrayList<AlumnoDTO> listAlumnosDTO() throws Exception {
		ArrayList<AlumnoDTO> listaAlumnosDTO = new ArrayList<AlumnoDTO>();
		ArrayList<Alumno> listaAlumnos = new ArrayList<Alumno>();
		ServicioAcademico servicioAcademico = new ServicioAcademico();
		try {
			Alumno alumnoEx = new Alumno(); // alumno nulo de ejemplo
			listaAlumnos = gAlumno.getByExample(alumnoEx);
			for (Alumno a : listaAlumnos) {
				AlumnoDTO aDTO = new AlumnoDTO(a.getIdUsuario(), a.getNroDocumento(), a.getNombre(), a.getApellido(), "", "");
				Set<Alumno> listaAluCurso = gCurso.getByDivision('0').getListaAlumnos();
				if (listaAluCurso.contains(a)) {
					aDTO.setCurso("0");
					aDTO.setAnio("-");
				} else {
					Anio anioEx = new Anio();
					List<Anio> listaAnios = new ArrayList<Anio>();
					listaAnios = servicioAcademico.getAnios(anioEx);
					for (Anio anio : listaAnios) {
						for (Curso c : anio.getListaCursos()) {
							if (c.getListaAlumnos().contains(a)) {
								aDTO.setCurso(c.getDivision().toString()/* + " " + c.getTurno()*/);
								aDTO.setAnio(anio.getNombre());
							}
						}
					}
				}
				listaAlumnosDTO.add(aDTO);
			} 
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los alumos: " + ex.getMessage());
		}
		return listaAlumnosDTO;
	}
	
	/**
	 * Devuelve un listado minificado cde alumnos activos y no graduados
	 * @return
	 * @throws Exception
	 */
	public ArrayList<AlumnoDTO> listAlumnosActivosDTO() throws Exception {
		ArrayList<AlumnoDTO> listaAlumnosDTO = new ArrayList<AlumnoDTO>();
		ArrayList<Alumno> listaAlumnos = new ArrayList<Alumno>();
		ServicioAcademico servicioAcademico = new ServicioAcademico();
		try {
			Alumno alumnoEx = new Alumno(); // alumno nulo de ejemplo
			alumnoEx.setActivo(true);
			//alumnoEx.setGraduado(false); USO FUTURO. DESCARTA LOS ALUMNOS YA GRADUADOS O FINALIZADO EL CICLO ESCOLAR
			listaAlumnos = gAlumno.getByExample(alumnoEx);
			for (Alumno a : listaAlumnos) {
				AlumnoDTO aDTO = new AlumnoDTO(a.getIdUsuario(), a.getNroDocumento(), a.getNombre(), a.getApellido(), "", "");
				Set<Alumno> listaAluCurso = gCurso.getByDivision('0').getListaAlumnos();
				if (listaAluCurso.contains(a)) {
					aDTO.setCurso("0");
					aDTO.setAnio("-");
				} else {
					Anio anioEx = new Anio();
					List<Anio> listaAnios = new ArrayList<Anio>();
					listaAnios = servicioAcademico.getAnios(anioEx);
					for (Anio anio : listaAnios) {
						for (Curso c : anio.getListaCursos()) {
							if (c.getListaAlumnos().contains(a)) {
								aDTO.setCurso(c.getDivision().toString()/* + " " + c.getTurno()*/);
								aDTO.setAnio(anio.getNombre());
							}
						}
					}
				}
				listaAlumnosDTO.add(aDTO);
			} 
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los alumos: " + ex.getMessage());
		}
		return listaAlumnosDTO;
	}
	
	@Override
	public Alumno getUsuarioByDni(Long dni) throws Exception {
		List<Alumno> alumnoLista = new ArrayList<Alumno>();
		alumnoLista = gAlumno.getByExample(new Alumno(dni,null,null,null,null,null,null,null,null,null,true,null));
		for (Alumno a: alumnoLista){
			return a;
		}
		throw new Exception ("Ocurrió un error al recuperar los datos del alumno por dni");
	}
	
	/**
	 * Obtiene un objeto AlumnoDTO con un DNI especificado. Se hace uso de listAlumnosDTO() para obtener los alumnos con curso y año.
	 * @param dni
	 * @return
	 * @throws Exception
	 */
	public AlumnoDTO getAlumnoByDniMin(Long dni) throws Exception{
		AlumnoDTO alumnoDto = new AlumnoDTO();
		List<AlumnoDTO> listaAlumno = listAlumnosActivosDTO();
		for(AlumnoDTO a : listaAlumno){
			if(a.getDniAlumno().equals(dni)){
				return a;
			}
		}
		return alumnoDto;
	}

	/**
	 * Devuelve un listado de AlumnoDTO de un año específico
	 * @param idAnio
	 * @return
	 * @throws Exception
	 */
	public List<AlumnoDTO> listAlumnosActivosAnioDTO(Long idAnio) throws ValidacionException, Exception{
		try{
			//Instanciamos un servicio para encontrar el nombre del anio
			//Capturamos el nombre y filtramos lo que devuelve listAlumnosActivosDTO()
			ValidacionException vEx = new ValidacionException();
			ServicioAcademico sAcademico = new ServicioAcademico();
			Anio anioAux = sAcademico.getAnio(idAnio);
			if(anioAux == null){
				vEx.addMensajeError("No existe el año solicitado.");
				throw vEx;
			}
			List<AlumnoDTO> todosLosAlumnos = listAlumnosActivosDTO();    // Me traigo los DTO de todos los alumnos
			List<AlumnoDTO> listadoDevolver = new ArrayList<AlumnoDTO>();
			for(AlumnoDTO aDTO : todosLosAlumnos){
				if (aDTO.getAnio().equals(anioAux.getNombre())){
					listadoDevolver.add(aDTO);
				}
			}
			if(listadoDevolver.size() == 0){
				vEx.addMensajeError("El año no tiene alumnos");
				throw vEx;
			}
			return listadoDevolver;
		}catch(ValidacionException vEx){
			throw vEx;
		}catch(Exception ex){
			throw ex;
		}
	}

}
