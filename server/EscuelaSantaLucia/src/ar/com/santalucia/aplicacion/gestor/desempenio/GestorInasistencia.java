package ar.com.santalucia.aplicacion.gestor.desempenio;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.desempenio.InasistenciaHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.desempenio.Inasistencia;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.validaciones.IValidacionInasistencia;

public class GestorInasistencia extends Gestor<Inasistencia> implements IListable<Inasistencia>, IValidacionInasistencia {

	private InasistenciaHome inasistenciaDAO;
	
	public GestorInasistencia() throws Exception {
		super();
		try {
			inasistenciaDAO = new InasistenciaHome();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(Inasistencia object) throws Exception {
		try {
			this.validar(object);
			setSession();
			setTransaction();
			inasistenciaDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException vEx) {
			throw vEx;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar la INASISTENCIA: " + ex.getMessage());
		}
	}

	@Override
	public void modify(Inasistencia object) throws Exception {
		try {
			this.validar(object);
			setSession();
			setTransaction();
			inasistenciaDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException vEx) {
			throw vEx;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar la INASISTENCIA: " + ex.getMessage());
		}
	}

	@Override
	public void delete(Inasistencia object) throws Exception {
		try {
			setSession();
			setTransaction();
			inasistenciaDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar la INASISTENCIA: " + ex.getMessage());
		}
	}

	@Override
	public Inasistencia getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Inasistencia inasistenciaDevolver = new Inasistencia();
			inasistenciaDevolver = inasistenciaDAO.findById(id);
			return inasistenciaDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar la INASISTENCIA por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Inasistencia> getByExample(Inasistencia example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Inasistencia> listaInasistenciasDevolver = 
					(ArrayList<Inasistencia>) inasistenciaDAO.findByExample((Inasistencia) example);
			sesionDeHilo.getTransaction().commit();
			return listaInasistenciasDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar INASISTENCIAS que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Inasistencia> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Inasistencia criterioVacio = new Inasistencia();
			ArrayList<Inasistencia> listaInasistenciasDevolver = new ArrayList<Inasistencia>();
			listaInasistenciasDevolver = (ArrayList<Inasistencia>) inasistenciaDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaInasistenciasDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar las INASISTENCIAS: " + ex.getMessage());
		}
	}

	@Override
	public Boolean existeConceptoEnFecha(Inasistencia inasistencia) throws Exception {
		Boolean resultado = false;
		Inasistencia inasistenciaEjemplo = new Inasistencia();
		inasistenciaEjemplo.setConcepto(inasistencia.getConcepto());
		ArrayList<Inasistencia> listaInasistencia;
		try {
			listaInasistencia = this.getByExample(inasistenciaEjemplo);
		} catch (Exception ex) {
			throw ex;
		}
		if (!listaInasistencia.isEmpty()) { // si no está vacía
			resultado = true;
		}
		return resultado;
	}

	@Override
	public void validar(Object object) throws Exception {
		Inasistencia inasistencia = (Inasistencia) object;
		Boolean vInasistencia;
		ValidacionException validacionException = new ValidacionException();
		
		vInasistencia = this.existeConceptoEnFecha(inasistencia);
		
		validacionException.addMensajeError(vInasistencia 
												? "Ya existe una inasistencia con el mismo concepto para la misma fecha" 
												: null);
		
		if (!validacionException.getMensajesError().isEmpty()) {
			throw validacionException;
		}
	}

}
