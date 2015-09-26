package ar.com.santalucia.aplicacion.gestor.usuario;

import java.util.ArrayList;

import org.hibernate.Session;

import ar.com.santalucia.accesodatos.dao.usuario.DirectivoHome;
import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.IGestor;
import ar.com.santalucia.dominio.modelo.usuarios.Directivo;

public class GestorDirectivo implements IGestor<Directivo> {
	private Directivo directivo;
	private DirectivoHome directivoDAO;
	private Session sesionDeHilo;
	
	
	
	public GestorDirectivo() throws Exception {
		try {
			sesionDeHilo = HibernateUtil.getSessionFactory().getCurrentSession();
			directivoDAO = new DirectivoHome();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar la persistencia.");
		}
	}

	@Override
	public void add(Directivo object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify(Directivo object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Directivo object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Directivo getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Directivo> getByExample(Directivo example) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Directivo> List() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSession() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeSession() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTransaction() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
