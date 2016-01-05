package ar.com.santalucia.aplicacion.gestor.academico;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.academico.MesaHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.dominio.modelo.academico.Mesa;

public class GestorMesa extends Gestor<Mesa> implements IListable<Mesa> {

	private MesaHome mesaDAO;
	
	public GestorMesa() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void add(Mesa object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify(Mesa object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Mesa object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Mesa getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Mesa> getByExample(Mesa example) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Mesa> List() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
