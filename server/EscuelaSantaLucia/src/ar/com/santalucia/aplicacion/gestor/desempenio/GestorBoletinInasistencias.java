package ar.com.santalucia.aplicacion.gestor.desempenio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ar.com.santalucia.accesodatos.dao.desempenio.BoletinInasistenciasHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinInasistencias;
import ar.com.santalucia.dominio.modelo.desempenio.Inasistencia;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.validaciones.IValidacionBoletinInasistencias;

public class GestorBoletinInasistencias extends Gestor<BoletinInasistencias>
		implements IListable<BoletinInasistencias>, IValidacionBoletinInasistencias {
	
	private BoletinInasistenciasHome boletinInasistenciasDAO;
	private GestorInasistencia GInasistencia;
	
	public GestorBoletinInasistencias() throws Exception {
		super();
		try {
			boletinInasistenciasDAO = new BoletinInasistenciasHome();
			GInasistencia = new GestorInasistencia();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(BoletinInasistencias object) throws Exception {
		Set<Inasistencia> listaInasistencias = object.getListaInasistencias();
		try {
			this.validar(object);
			setSession();
			setTransaction();
			if (listaInasistencias != null) {
				for (Inasistencia i : listaInasistencias) {
					if (i.getIdInasistencia() == null) {
						GInasistencia.add(i);
					} else {
						GInasistencia.modify(i);
					}
				}
			}
			boletinInasistenciasDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el BOLETIN DE INASISTENCIAS: " + ex.getMessage());
		}
	}

	@Override
	public void modify(BoletinInasistencias object) throws Exception {
		Set<Inasistencia> listaInasistencias = object.getListaInasistencias();
		try {
			this.validar(object);
			setSession();
			setTransaction();
			if (listaInasistencias != null) {
				for (Inasistencia i : listaInasistencias) {
					if (i.getIdInasistencia() == null) {
						GInasistencia.add(i);
					} else {
						GInasistencia.modify(i);
					}
				}
			}
			boletinInasistenciasDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el BOLETIN DE INASISTENCIAS: " + ex.getMessage());
		}
	}

	@Override
	public void delete(BoletinInasistencias object) throws Exception {
		
	}

	@Override
	public BoletinInasistencias getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			BoletinInasistencias boletinInasistenciaDevolver = new BoletinInasistencias();
			boletinInasistenciaDevolver = boletinInasistenciasDAO.findById(id);
			return boletinInasistenciaDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el BOLETIN DE INASISTENCIAS por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<BoletinInasistencias> getByExample(BoletinInasistencias example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<BoletinInasistencias> listaBoletinInasistenciasDevolver = 
					(ArrayList<BoletinInasistencias>) boletinInasistenciasDAO.findByExample((BoletinInasistencias) example);
			sesionDeHilo.getTransaction().commit();
			return listaBoletinInasistenciasDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar BOLETINES DE INASISTENCIAS que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<BoletinInasistencias> List() throws Exception {
		try {
			setSession();
			setTransaction();
			BoletinInasistencias criterioVacio = new BoletinInasistencias();
			ArrayList<BoletinInasistencias> listaBoletinInasistenciasDevolver = new ArrayList<BoletinInasistencias>();
			listaBoletinInasistenciasDevolver = (ArrayList<BoletinInasistencias>) boletinInasistenciasDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaBoletinInasistenciasDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los BOLETINES DE INASISTENCIAS: " + ex.getMessage());
		}
	}
	
	@Override
	public Boolean existeConceptoEnFecha(Inasistencia inasistencia) throws Exception {
		Boolean resultado = false;
		Inasistencia inasistenciaEjemplo = new Inasistencia();
		inasistenciaEjemplo.setConcepto(inasistencia.getConcepto());
		ArrayList<Inasistencia> listaInasistencia;
		try {
			listaInasistencia = GInasistencia.getByExample(inasistenciaEjemplo);
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
		BoletinInasistencias boletinInasistencia = (BoletinInasistencias) object;
		ValidacionException validacionException = new ValidacionException();
		
		Set<Inasistencia> listaInasistencias = new HashSet<Inasistencia>();
		listaInasistencias = boletinInasistencia.getListaInasistencias();
		int contadorFaltas = 0;
		if (!listaInasistencias.isEmpty()) {
			for (Inasistencia i : listaInasistencias) {
				contadorFaltas = (this.existeConceptoEnFecha(i) ? contadorFaltas + 1 : contadorFaltas + 0);
			}
		}
		
		validacionException.addMensajeError(contadorFaltas > 0 
											? "Existe" + (contadorFaltas == 1 ? "" : "n") + " " + contadorFaltas 
													+ " inasistencia" + (contadorFaltas == 1 ? "" : "s") 
													+ " con el mismo concepto para la misma fecha." 
											: null);
		
		if (!validacionException.getMensajesError().isEmpty()) {
			throw validacionException;
		}
	}

}
