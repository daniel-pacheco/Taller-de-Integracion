package ar.com.santalucia.aplicacion.gestor.desempenio;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.desempenio.MateriaNotasBoletinHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
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
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar la entidad MATERIA_NOTAS_BOELTIN histórico: " + ex.getMessage());
		}
	}

	@Override
	public void modify(MateriaNotasBoletin object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(MateriaNotasBoletin object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MateriaNotasBoletin getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<MateriaNotasBoletin> getByExample(MateriaNotasBoletin example) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<MateriaNotasBoletin> List() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
