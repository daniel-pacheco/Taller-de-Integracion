package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.InscripcionHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.academico.Inscripcion;

public class GestorInscripcion extends Gestor<Inscripcion> implements IListable<Inscripcion> {

	private InscripcionHome inscripcionDAO;
	
	public GestorInscripcion() throws Exception {
		super();
		try {
			inscripcionDAO = new InscripcionHome();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un error al iniciar la persistencia: " + ex.getMessage());
		}
	}

	@Override
	public void add(Inscripcion object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify(Inscripcion object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Inscripcion object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Inscripcion getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Inscripcion> getByExample(Inscripcion example) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Inscripcion> List() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
