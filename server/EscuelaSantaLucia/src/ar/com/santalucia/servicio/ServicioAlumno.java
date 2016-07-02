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
 * @version 1.1
 *
 */

// Último modificador: Eric Pennachini @ 23-10-2015 18:23

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
				gAlumno.add(usuario);
				sLogin.addLogin(usuario.getNroDocumento(), Login.ALUMNO);
				Curso cursoGen = new Curso();
				cursoGen = gCurso.getByDivision('0');
				cursoGen.getListaAlumnos().add(usuario);
				gCurso.modify(cursoGen);
			} else {
				Long dniViejo = gAlumno.getById(usuario.getIdUsuario()).getNroDocumento(); 
				gAlumno.modify(usuario);
				sLogin.actualizarUsuario(dniViejo, usuario.getNroDocumento(), Login.ALUMNO);
			}
			return true;
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("Hubo un problema al agregar o modificar el ALUMNO: " + ex.getMessage());
		}
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
			if(!aux.getCurso().equals('0')){
				Anio anioAux = new Anio(null,aux.getAnio(),null,null,null,true);
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
		List<AlumnoDTO> listaAlumno = listAlumnosDTO();
		for(AlumnoDTO a : listaAlumno){
			if(a.getDniAlumno().equals(dni)){
				return a;
			}
		}
		return alumnoDto;
	}

}
