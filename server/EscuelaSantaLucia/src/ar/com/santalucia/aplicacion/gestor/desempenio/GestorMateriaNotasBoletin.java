package ar.com.santalucia.aplicacion.gestor.desempenio;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.desempenio.MateriaNotasBoletinHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotasHist;
import ar.com.santalucia.dominio.modelo.desempenio.MateriaNotasBoletin;

public class GestorMateriaNotasBoletin extends Gestor<MateriaNotasBoletin> implements IListable<MateriaNotasBoletin> {

	private MateriaNotasBoletinHome materiaNotasBoletinDAO;
	
	public GestorMateriaNotasBoletin() throws Exception {
		super();
		try {
			materiaNotasBoletinDAO = new MateriaNotasBoletinHome();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al inicializar el gestor: " + ex.getMessage());
		}
	}

	@Override
	public void add(MateriaNotasBoletin object) throws Exception {
		try {
			setSession();
			setTransaction();
			materiaNotasBoletinDAO.persist(object);
			//sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar la entidad MATERIA_NOTAS_BOELTIN histórico: " + ex.getMessage());
		}
	}

	@Override
	public void modify(MateriaNotasBoletin object) throws Exception {
		try {
			setSession();
			setTransaction();
			materiaNotasBoletinDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar la entidad MATERIA_NOTAS_BOELTIN histórico: " + ex.getMessage());
		}
	}

	/**
	 * NO SE IMPLEMENTA
	 */
	@Override
	public void delete(MateriaNotasBoletin object) throws Exception {}

	@Override
	public MateriaNotasBoletin getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction(); 
			MateriaNotasBoletin materiasNotasDevolver = new MateriaNotasBoletin();
			materiasNotasDevolver = materiaNotasBoletinDAO.findById(id);
			return materiasNotasDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar la entidad MATERIA_NOTAS_BOELTIN histórico por su ID: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<MateriaNotasBoletin> getByExample(MateriaNotasBoletin example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<MateriaNotasBoletin> listaMateriasNotasDevolver = (ArrayList<MateriaNotasBoletin>) materiaNotasBoletinDAO.findByExample((MateriaNotasBoletin) example);
			sesionDeHilo.getTransaction().commit();
			return listaMateriasNotasDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar la entidad MATERIA_NOTAS_BOELTIN histórico que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}

	@Override
	public ArrayList<MateriaNotasBoletin> List() throws Exception {
		try {
			setSession();
			setTransaction();
			MateriaNotasBoletin criterioVacio = new MateriaNotasBoletin();
			ArrayList<MateriaNotasBoletin> listaMateriasBoletinDevolver = new ArrayList<MateriaNotasBoletin>();
			listaMateriasBoletinDevolver = (ArrayList<MateriaNotasBoletin>) materiaNotasBoletinDAO.findByExample(criterioVacio);
			sesionDeHilo.getTransaction().commit();
			return listaMateriasBoletinDevolver;
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al listar la entidad MATERIA_NOTAS_BOELTIN histórico: " + ex.getMessage());
		}
	}

}
