package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.DetalleVolanteHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.academico.DetalleVolante;

public class GestorDetalleVolante extends Gestor<DetalleVolante> implements IListable<DetalleVolante> {

	private DetalleVolanteHome detalleVolanteDAO;
	
	public GestorDetalleVolante() throws Exception {
		super();
		detalleVolanteDAO = new DetalleVolanteHome();
	}

	@Override
	public void add(DetalleVolante object) throws Exception {
		try{
			setSession();
			setTransaction();
			detalleVolanteDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el Detalle de Volante: " + ex.getMessage());
		}
	}

	@Override
	public void modify(DetalleVolante object) throws Exception {
		try{
			setSession();
			setTransaction();
			detalleVolanteDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el Detalle de Volante: " + ex.getMessage());
		}
	}

	@Override
	public void delete(DetalleVolante object) throws Exception {
		try{
			setSession();
			setTransaction();
			detalleVolanteDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el Detalle de Volante: " + ex.getMessage());
		}
	}
	
	/**
	 * Llama a delete indirectamente buscando primero la entidad por Id
	 * @param idDetalleVolante
	 * @throws Exception
	 */
	public void deleteById(Long idDetalleVolante) throws Exception{
		try{
			DetalleVolante detalle = getById(idDetalleVolante);
			delete(detalle);
		}catch(Exception ex){
			throw ex;
		}
	}

	@Override
	public DetalleVolante getById(Long id) throws Exception {
		try{
			setSession();
			setTransaction();
			DetalleVolante detalleVolanteDevolver = new DetalleVolante();
			detalleVolanteDevolver = detalleVolanteDAO.findById(id);
			return detalleVolanteDevolver;
		}catch(Exception ex){
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el Detalle de Volante por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<DetalleVolante> getByExample(DetalleVolante example) throws Exception {
		try{
			setSession();
			setTransaction();
			ArrayList<DetalleVolante> detalleVolanteDevolver = (ArrayList<DetalleVolante>) detalleVolanteDAO.findByExample(example);
			sesionDeHilo.getTransaction().commit();
			return detalleVolanteDevolver;
		}catch (Exception ex){
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar Detalle de Volante que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<DetalleVolante> List() throws Exception {
		try{
			setSession();
			setTransaction();
			DetalleVolante criterioVacio = new DetalleVolante();
			ArrayList<DetalleVolante> detalleVolanteDevolver = new ArrayList<DetalleVolante>();
			detalleVolanteDevolver = (ArrayList<DetalleVolante>) detalleVolanteDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return detalleVolanteDevolver;
		}catch(Exception ex){
			closeSession();
			throw new Exception("Ha ocurrido un error al listar los Detalle de Volante: " + ex.getMessage());
		}
	}
}
