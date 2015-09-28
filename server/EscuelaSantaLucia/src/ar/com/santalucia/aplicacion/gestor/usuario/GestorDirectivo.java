package ar.com.santalucia.aplicacion.gestor.usuario;

import java.util.ArrayList;
import java.util.Set;

import org.hibernate.Session;

import ar.com.santalucia.accesodatos.dao.usuario.DirectivoHome;
import ar.com.santalucia.accesodatos.persistencia.HibernateUtil;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IGestor;
import ar.com.santalucia.dominio.modelo.usuarios.Directivo;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.validaciones.IValidacionUsuarioDocDir;

public class GestorDirectivo extends Gestor<Directivo> implements IValidacionUsuarioDocDir{
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
	public void existeDocumento(String tipo, Long numero) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void existeNombreUsuario(String nombreUsuario) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void existeCuil(Long cuil) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void existeMail(Set<Mail> mail) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validar(Directivo object) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
