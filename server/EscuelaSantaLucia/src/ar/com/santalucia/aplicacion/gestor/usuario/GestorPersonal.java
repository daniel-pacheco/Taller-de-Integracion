package ar.com.santalucia.aplicacion.gestor.usuario;

import java.util.ArrayList;
import ar.com.santalucia.accesodatos.dao.usuario.PersonalHome;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorTitulo;
import ar.com.santalucia.dominio.modelo.usuarios.Directivo;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;
import ar.com.santalucia.dominio.modelo.usuarios.Usuario;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;
import ar.com.santalucia.excepciones.SugerenciaDirectivoException;
import ar.com.santalucia.excepciones.SugerenciaPersonalException;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.validaciones.IValidacionUsuarioDocDir;

/**
 * Clase gestor de Docentes
 * 
 * @author Eric
 * @version 2.0
 *
 */

// UltimoModificador: Ariel Ramirez @ 27-11-15 16:00

public class GestorPersonal extends GestorUsuario implements IValidacionUsuarioDocDir {
	private PersonalHome docenteDAO;
	private GestorTitulo GTitulo;

	public GestorPersonal() throws Exception {
		super();
		try {
			docenteDAO = new PersonalHome();
			GTitulo = new GestorTitulo();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar la persistencia.");
		}
	}

	@Override
	public void add(Usuario object) throws Exception {
		Personal personal = (Personal) object;
		try {
			this.validar(personal);
			if (personal.getListaTitulos() != null) {
				for (Titulo t : personal.getListaTitulos()) {
					GTitulo.add(t);
				}
			}
			if (personal.getListaTelefonos() != null) {
				for (Telefono t : personal.getListaTelefonos()) {
					GTelefono.add(t);
				}
			}
			if (personal.getListaMails() != null) {
				for (Mail m : personal.getListaMails()) {
					GMail.add(m);
				}
			}
			if (personal.getDomicilio() != null) {
				GDomicilio.add(personal.getDomicilio());
			}
			setSession();
			setTransaction();
			docenteDAO.persist(personal);
			sesionDeHilo.getTransaction().commit();
		} catch (SugerenciaPersonalException ex) {
			throw ex;
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public void modify(Usuario object) throws Exception {
		Personal personal = (Personal) object;
		try {
			this.validar(personal);
			setSession();
			setTransaction();
			docenteDAO.attachDirty(personal);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el objeto: " + ex.getMessage());
		}
	}

	// @Override
	public ArrayList<Personal> getByExample(Personal object) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Personal> listaPersonalDevolver = (ArrayList<Personal>) docenteDAO.findByExample(object);
			sesionDeHilo.getTransaction().commit();
			return listaPersonalDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	//SUPRIMIDO
//	public ArrayList<Personal> List() throws Exception {
//		try {
//			setSession();
//			setTransaction();
//			Personal criterioVacio = new Personal();
//			ArrayList<Personal> listaPersonalDevolver = new ArrayList<Personal>();
//			listaPersonalDevolver = (ArrayList<Personal>) docenteDAO.findByExample(criterioVacio);
//			sesionDeHilo.getTransaction().commit();
//			return listaPersonalDevolver;
//		} catch (Exception ex) {
//			throw new Exception("Ha ocurrido un error al listar los docentes: " + ex.getMessage());
//		}
//	}

	/*
	 * Implementaci�n de IValidacionDocDir
	 */

	@Override
	public Boolean existeCuil(Usuario usuario) throws Exception {
		Boolean resultado = false;
		Personal personal = (Personal) usuario;
		Personal personalEjemplo = new Personal();
		personalEjemplo.setCuil(personal.getCuil());
		try {
			ArrayList<Personal> listaDocente = this.getByExample(personalEjemplo);
			if (personal.getIdUsuario() == null) {
				resultado = (listaDocente.isEmpty() ? false : true);
			} else {
				if (!listaDocente.isEmpty()) {
					Personal docenteTemp = new Personal();
					for (Personal d : listaDocente) {
						docenteTemp = d;
					}
					if (docenteTemp.getIdUsuario().equals(personal.getIdUsuario())) {
						resultado = false;
					} else {
						resultado = true;
					}
				}
			}
		} catch (Exception ex) {
			throw ex;
		}
		return resultado;
	}

	/**
	 * 
	 * @param docente
	 * @return Boolean
	 * @throws Exception
	 * 
	 *             Comprueba si existe del DNI cargado en el Docente en alg�n
	 *             Directivo existente.
	 */

	// existe en directivo o en docente se hace aqui porque personal trae su
	// idetificacion cargada
	public Boolean existeDniEnPersonal(Personal personal) throws Exception {
		Personal personalTemp = new Personal();
		personalTemp.setNroDocumento(personal.getNroDocumento());
//		personalTemp.setListaMails(null);
//		personalTemp.setListaTelefonos(null);
//		personalTemp.setDomicilio(null);
//		personalTemp.setListaTitulos(null);
		// Buscar en el rol contrario
		if (personal.getRol().equals(Personal.DIRECTIVO)) {
			personalTemp.setRol(Personal.DOCENTE);
		} else {
			personalTemp.setRol(Personal.DIRECTIVO);
		}
		ArrayList<Personal> listaPersonal = this.getByExample(personalTemp);
		return (listaPersonal.isEmpty() ? false : true);
		// GestorDirectivo gDir = new GestorDirectivo();
		// Directivo directivoEjemplo = new Directivo();
		// directivoEjemplo.setNroDocumento(docente.getNroDocumento());
		// ArrayList<Directivo> listaDirectivo =
		// gDir.getByExample(directivoEjemplo);
		// return (listaDirectivo.isEmpty() ? false : true);
	}

	public Boolean mismoUsuario(Personal personal) throws Exception {
		Personal personalTemp = new Personal();
		personalTemp.setNroDocumento(personal.getNroDocumento());
		ArrayList<Personal> listadoPersonal = this.getByExample(personalTemp);

		for (Personal p : listadoPersonal) {
			personalTemp = p;
		}

		if (personalTemp.getIdUsuario().equals(personal.getIdUsuario())) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param docente
	 * @return Boolean
	 * @throws Exception
	 * 
	 *             Compara los datos de un Docente con los del Directivo
	 *             existente en la base de datos. Se comparan los atributos
	 *             nombre, apellido, tipo de documento, numero de documento,
	 *             cuil, fecha de nacimiento y nombre de usuario.
	 */

	public Boolean datosIguales(Personal docente) throws Exception {
		GestorDirectivo gDir = new GestorDirectivo();
		Directivo directivoEjemplo = new Directivo();
		directivoEjemplo.setNroDocumento(docente.getNroDocumento());
		ArrayList<Directivo> listaDirectivo = gDir.getByExample(directivoEjemplo);
		for (Directivo d : listaDirectivo)
			if ((d.getApellido().equals(docente.getApellido())) && (d.getCuil().equals(docente.getCuil()))
					&& (d.getDomicilio().equals(docente.getDomicilio()))
					&& (d.getFechaNacimiento().getTime() == docente.getFechaNacimiento().getTime())
					&& (d.getNombre().equals(docente.getNombre()))
					&& (d.getNombreUsuario().equals(docente.getNombreUsuario()))
					&& (d.getNroDocumento().equals(docente.getNroDocumento()))
					&& (d.getSexo().equals(docente.getSexo()))
					&& (d.getTipoDocumento().equals(docente.getTipoDocumento()))) {
				return true;
			} else {
				return false;
			}
		return false;
	}

	/**
	 * @param Object
	 *            de tipo Docente
	 * @exception ValidacionException
	 * @exception sugDirException
	 * @throws Exception
	 * 
	 *             El m�todo realiza la validaci�n y lanza una excepci�n si no
	 *             se supera la misma:<br>
	 *             1) Se comprueba si el DNI ingresado para el docente que se
	 *             quiere dar de alta existe en alg�n directivo.<br>
	 *             2) Si se da el caso 1) se comprueba la coincidencia de los
	 *             datos del directivo existente con los del docente a dar de
	 *             alta (se compara nombre, apellido, tipo de documento, numero
	 *             de documento, cuil, fecha de nacimiento y nombre de usuario)
	 *             <br>
	 *             3) Si los datos comparados en 2) no son id�nticos se lanza un
	 *             sugDirException con los datos del directivo encontrado como
	 *             sugerencia.<br>
	 *             4) Si no se da el caso 1) se busca coincidencias de n�mero de
	 *             DNI en el resto de los usuarios, lanzando un
	 *             ValidacionException si es encontrado.<br>
	 *             <img src="DiagramaFlujovalidarDocente.png" width="250" height
	 *             ="250"></img>
	 */
	@Override
	public void validar(Object object) throws SugerenciaPersonalException, Exception {
		Personal personal = (Personal) object;
		ValidacionException exception = new ValidacionException();
		SugerenciaDirectivoException sugDirException = new SugerenciaDirectivoException();
		SugerenciaPersonalException sugPerException = new SugerenciaPersonalException();

		switch (personal.getRol()) {
		case "DIRECTIVO":
			if (existeDniEnPersonal(personal)) {
				if (!mismoUsuario(personal)) {
					// L�gica para localizar al docente para devolver
					Personal personalTemp = new Personal();
					personalTemp.setNroDocumento(personal.getNroDocumento());
					ArrayList<Personal> listadoPersonal = this.getByExample(personalTemp);
					for (Personal p : listadoPersonal) {
						personalTemp = p;
					}
					sugPerException.setPersonalSugerido(personalTemp);
					sugPerException.setMensaje(
							"El DNI se encuentra en uso por otro usuario. Se sugiere:  " + personalTemp.toString());
					throw sugPerException;
				}
			}
			break;
		case "DOCENTE":
			if (existeDniEnPersonal(personal)) {
				if (!mismoUsuario(personal)) {
					// L�gica para localizar al directivo para devolver
					Personal personalTemp = new Personal();
					personalTemp.setNroDocumento(personal.getNroDocumento());
					ArrayList<Personal> listadoPersonal = this.getByExample(personalTemp);
					for (Personal p : listadoPersonal) {
						personalTemp = p;
					}
					sugPerException.setPersonalSugerido(personalTemp);
					sugPerException.setMensaje(
							"El DNI se encuentra en uso por otro usuario. Se sugiere: " + personalTemp.toString());
					throw sugPerException;
				}
			}
			break;
		case "DOCENTE/DIRECTIVO":
			if (existeDocumento(personal)) {
				exception.addMensajeError("El n�mero de DNI ya est� en uso.");
				throw exception;
			}
			break;
		default:
			break;
		}
	}

	

}
