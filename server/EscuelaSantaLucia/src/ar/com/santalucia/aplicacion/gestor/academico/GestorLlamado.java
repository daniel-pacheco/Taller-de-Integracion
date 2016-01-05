package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.LlamadoHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Llamado;
import ar.com.santalucia.dominio.modelo.academico.Mesa;
import ar.com.santalucia.excepciones.ValidacionException;
import ar.com.santalucia.validaciones.IValidacionLlamado;

/**
 * Gestor de Llamados (períodos de mesas)
 * 
 * @author Eric
 * @version 1.0
 *
 */

//Último modificador: Eric Pennachini @ dd-MM-aaaa hh:mm

public class GestorLlamado extends Gestor<Llamado> implements IListable<Llamado>, IValidacionLlamado{

	private LlamadoHome llamadoDAO;
	private GestorMateria GMateria;
	
	public GestorLlamado() throws Exception {
		super();
		try {
			llamadoDAO = new LlamadoHome();
			GMateria = new GestorMateria();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(Llamado object) throws Exception {
		try {
			this.validar(object);
			setSession();
			setTransaction();
			llamadoDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
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
	public void modify(Llamado object) throws Exception {
		try {
			this.validar(object);
			setSession();
			setTransaction();
			llamadoDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public void delete(Llamado object) throws Exception {
		try {
			setSession();
			setTransaction();
			llamadoDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public Llamado getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			Llamado llamadoDevolver = new Llamado();
			llamadoDevolver = llamadoDAO.findById(id);
			return llamadoDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el objeto por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Llamado> getByExample(Llamado example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Llamado> listaLlamadosDevolver = (ArrayList<Llamado>) llamadoDAO.findByExample((Llamado) example);
			sesionDeHilo.getTransaction().commit();
			return listaLlamadosDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<Llamado> List() throws Exception {
		try {
			setSession();
			setTransaction();
			Llamado criterioVacio = new Llamado();
			ArrayList<Llamado> listaLlamadosDevolver = new ArrayList<Llamado>();
			listaLlamadosDevolver = (ArrayList<Llamado>) llamadoDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaLlamadosDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar los áreas: " + ex.getMessage());
		}
	}

	@Override
	public Boolean existeLlamado(Llamado llamado) throws Exception {
		//TODO revisar método
		Boolean resultado = false;
		Llamado llamadoEjemplo = new Llamado();
		llamadoEjemplo.setDescripcion(llamado.getDescripcion());
		llamadoEjemplo.setFechaInicio(llamado.getFechaInicio());
		llamadoEjemplo.setFechaFin(llamado.getFechaFin());
		try {
			ArrayList<Llamado> listaLlamados = this.getByExample(llamadoEjemplo);
			if (llamado.getIdLlamado() == null) {
				resultado = (listaLlamados.isEmpty() ? false : true);
			} else {
				if (!listaLlamados.isEmpty()) {
					Llamado llamadoTemp = new Llamado();
					for (Llamado l : listaLlamados) {
						llamadoTemp = l;
					}
					if (llamadoTemp.getIdLlamado().equals(llamado.getIdLlamado())) {
						resultado = false;
					} else {
						resultado = true;
					}
				}
			}
		} catch (Exception ex) {
			// Excepción no tratada
			throw ex;
		}
		return resultado;
	}

	@Override
	public Boolean existeMesaEnLlamado(Mesa mesa, Llamado llamado) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validar(Object object) throws Exception {
		Llamado llamado = (Llamado) object;
		
		Boolean vLlamado;
		ValidacionException exception = new ValidacionException();
		
		// - comprobar existencia de llamado
		vLlamado = this.existeLlamado(llamado);
		exception.addMensajeError(vLlamado ? "El llamado " + llamado.getDescripcion() + " ya existe" : null);
		
		//TODO - comprobar existencia de mesa en llamado
		
		if (!exception.getMensajesError().isEmpty()) {
			throw exception;
		}
	}

}
