package ar.com.santalucia.servicio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.academico.GestorCurso;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.dominio.modelo.academico.Curso;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;
import ar.com.santalucia.excepciones.ValidacionException;

// Esta clase es un Facade que permite acceder a todas las operaciones de ABM de alumno,
// incorporando las ABM  para sus compuestos (telefono, mail y direcci�n)

/**
 * 
 * @author Ariel Ramirez
 * 
 * @version 1.1
 *
 */

// �ltimo modificador: Eric Pennachini @ 23-10-2015 18:23

public class ServicioAlumno extends ServicioUsuario<Alumno> {

	private GestorAlumno gAlumno;
	private GestorCurso gCurso;

	public ServicioAlumno() throws Exception {
		super();
		gAlumno = new GestorAlumno();
		gCurso = new GestorCurso();
	}

	@Override
	public Alumno getUsuario(Long id) throws Exception {
		if (id > 0) {
			try {
				return gAlumno.getById(id);
			} catch (Exception ex) {
				throw new Exception("Servicio: problemas. " + ex.getMessage());
			}
		}
		return null;
	}

	@Override
	public List<Alumno> getUsuarios(Alumno example) throws Exception {
		try {
			return gAlumno.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("Servicio: problemas. " + ex.getMessage());
		}
	}

	@Override
	public boolean addUsuario(Alumno usuario) throws Exception {
		try {
			if (usuario.getIdUsuario() == null) {
				gAlumno.add(usuario);
				Curso cursoGen = new Curso();
				cursoGen = gCurso.getByDivision('0');
				cursoGen.getListaAlumnos().add(usuario);
				gCurso.modify(cursoGen);
			} else {
				gAlumno.modify(usuario);
			}
			return true;
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
			Alumno alumno = new Alumno();
			if ((alumno = getUsuario(idUsuario)) != null) { // pruebo si el
															// alumno existe,
															// sino no - > Null
				telefonos = alumno.getListaTelefonos(); // getListaTelefonos del
														// alumno que se busc�,
														// si existe
			}
		} catch (Exception ex) {
			throw new Exception(
					"Servicio: Ha ocurrido un problema al intentar recuperar los tel�fonos . " + ex.getMessage());
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
					"Servicio: Ha ocurrido un problema al intentar recuperar los mails. " + ex.getMessage());
		}
		return mails;
	}

	@Override
	public Set<Titulo> getTitulos(Long idUsuario) {
		// Este m�todo no se implementa para esta clase
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

	@Override
	public boolean modifyUsuario(Alumno usuarioModificado) throws Exception {
		try {
			gAlumno.modify(usuarioModificado);
			return true;
		} catch (Exception ex) {
			throw new Exception("Servicio modify(): no se pudo completar la operacion. " + ex.getMessage());
		}
	}

	@Override
	public void closeSession() throws Exception {
		gAlumno.closeSession();
	}

	// No se incluyen los m�todos para agregar mail, t�tulo o tel�fono dado a
	// que solo es necesario llamar a this.addUsuario(Alumno usuario)

}
