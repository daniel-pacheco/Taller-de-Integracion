package ar.com.santalucia.servicio;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ar.com.santalucia.aplicacion.gestor.academico.GestorCurso;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.dominio.modelo.academico.Curso;
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
				sLogin.actualizarUsuario(dniViejo, usuario.getNroDocumento());
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
			gAlumno.delete(usuario);
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}

	/*
	 * CANDIDATO A SUPRIMIR
	 */
	@Override
	public boolean modifyUsuario(Alumno usuarioModificado) throws Exception {
		try {
			gAlumno.modify(usuarioModificado);
			return true;
		} catch (Exception ex) {
			throw new Exception("Servicio modify(): no se pudo completar la operacion. " + ex.getMessage());
		}
	}
	/*
	 * 
	 */

	@Override
	public void closeSession() throws Exception {
		gAlumno.closeSession();
	}

	// No se incluyen los métodos para agregar mail, título o teléfono dado a
	// que solo es necesario llamar a this.addUsuario(Alumno usuario)

}
