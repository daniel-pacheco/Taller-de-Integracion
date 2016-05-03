package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.MateriaHistHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.academico.MateriaHist;

/**
 * 
 * @author Eric Pennachini
 *
 * @version 1.1
 * 
 */

// Último modificador: Ariel Ramírez @ 30-04-2016 13:17 

public class GestorMateriaHist extends Gestor<MateriaHist> implements IListable<MateriaHist> {

	private MateriaHistHome materiaHistDAO;
	
	public GestorMateriaHist() throws Exception {
		super();
		try{
			materiaHistDAO = new MateriaHistHome();
		}catch (Exception ex){
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(MateriaHist object) throws Exception {
		try{
			setSession();
			setTransaction();
			materiaHistDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar MATERIAHISTORICA: " + ex.getMessage());
		}
	}

	@Override
	public void modify(MateriaHist object) throws Exception {
		try{
			setSession();
			setTransaction();
			materiaHistDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar MATERIAHISTORICA: " + ex.getMessage());
		}
		
	}

	@Override
	public void delete(MateriaHist object) throws Exception {
		try {
			setSession();
			setTransaction();
			materiaHistDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar MATERIAHISTORICA: " + ex.getMessage());
		}
		
	}

	@Override
	public MateriaHist getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			MateriaHist materiaHistDevolver = new MateriaHist();
			materiaHistDevolver = materiaHistDAO.findById(id);
			return materiaHistDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar MATERIAHISTORICA por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<MateriaHist> getByExample(MateriaHist example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<MateriaHist> listaMateriasHistDevolver = (ArrayList<MateriaHist>) materiaHistDAO.findByExample((MateriaHist) example);
			sesionDeHilo.getTransaction().commit();
			return listaMateriasHistDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar MATERIAHISTORICA que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<MateriaHist> List() throws Exception {
		try {
			MateriaHist criterioVacio = new MateriaHist();
			return this.getByExample(criterioVacio);
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar las MATERIAS: " + ex.getMessage());
		}
	}

}
