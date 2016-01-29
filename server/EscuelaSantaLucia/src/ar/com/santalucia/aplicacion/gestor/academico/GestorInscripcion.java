package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.InscripcionHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.academico.Inscripcion;
import ar.com.santalucia.dominio.modelo.academico.Llamado;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.validaciones.IValidacionInscripcion;

public class GestorInscripcion extends Gestor<Inscripcion> implements IListable<Inscripcion>, IValidacionInscripcion {

	private InscripcionHome inscripcionDAO;
	
	public GestorInscripcion() throws Exception {
		super();
		try {
			inscripcionDAO = new InscripcionHome();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(Inscripcion object) throws Exception {
		try {
			this.validar(object);
			setSession();
			setTransaction();
			inscripcionDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar la INSCRIPCIÓN: " + ex.getMessage());
		}
	}

	@Override
	public void modify(Inscripcion object) throws Exception {
		try {
			this.validar(object);
			setSession();
			setTransaction();
			inscripcionDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar la INSCRIPCIÓN: " + ex.getMessage());
		}
	}

	@Override
	public void delete(Inscripcion object) throws Exception {
		try {
			setSession();
			setTransaction();
			inscripcionDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar la INSCRIPCIÓN: " + ex.getMessage());
		}
	}

	@Override
	public Inscripcion getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Inscripcion inscripcionDevolver = new Inscripcion();
			inscripcionDevolver = inscripcionDAO.findById(id);
			return inscripcionDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar la INSCRIPCIÓN por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Inscripcion> getByExample(Inscripcion example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Inscripcion> listaInscripcionDevolver = (ArrayList<Inscripcion>) inscripcionDAO.findByExample(example);
			sesionDeHilo.getTransaction().commit();
			return listaInscripcionDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar INSCRIPCIONES que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Inscripcion> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Inscripcion criterioVacio = new Inscripcion();
			ArrayList<Inscripcion> listaInscripcionesDevolver = new ArrayList<Inscripcion>();
			listaInscripcionesDevolver = (ArrayList<Inscripcion>) inscripcionDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaInscripcionesDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar las INSCRIPCIONES: " + ex.getMessage());
		}
	}

	@Override
	public Boolean validarFecha(Inscripcion inscripcion) {
		if (inscripcion.getFecha().getTime() >= (inscripcion.getMesa().getFecha().getTime() - 2)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Boolean existeNroActa(String nroActa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validar(Object object) throws Exception {
		Inscripcion inscripcion = (Inscripcion) object;
		
		ValidacionException exception = new ValidacionException();
		Boolean vFecha = this.validarFecha(inscripcion);
		
		// validar si existe nro acta
		
		exception.addMensajeError(vFecha ? null : "La fecha de la inscripción excede el plazo mínimo. "
													+ "Recuerde que se puede inscribir hasta " 
													+ inscripcion.getMesa().getPlazoInscripcion()
													+ (inscripcion.getMesa().getPlazoInscripcion() 
															> 1 ? " días antes." : " día antes."));
		
		if (!exception.getMensajesError().isEmpty()) {
			throw exception;
		}
	}

}
