package ar.com.santalucia.aplicacion.gestor.usuario;

import java.util.ArrayList;
import java.util.List;

import ar.com.santalucia.accesodatos.dao.usuario.AdministradorHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.dominio.modelo.usuarios.Administrador;
/**
 * 
 * @author Ariel Ramirez
 *
 * @version 1.0
 *
 */

/*
 * 
 * EL USUARIO ADMINISTRADOR ES UN USUARIO ÚNICO Y ESPECIAL.
 * NO ES POSIBLE MODIFICAR LOS DATOS DEL ADMINISTRADOR, SOLO SE PUEDE CAMBIAR LA CONTRASEÑA
 * 
 */

public class GestorAdministrador extends Gestor<Administrador> {

	private AdministradorHome administradorDAO;
	
	//private static String MASTER_KEY = "NIsdgOVWR7834B9U7sdgfg5V23ddfgds4Jnmrt79arhmBCUB2938sadgYRASPDF48dsgdsfg782aqw3c";
	
	public GestorAdministrador() throws Exception {
		super();
		administradorDAO = new AdministradorHome();
	}

	@Override
	public void add(Administrador object) throws Exception {
		// TODO Auto-generated method stub
		// BUSCAR SI YA EXISTE UN USUARIO ADMINISTRADOR. SI EXISTE NO PERMITIR AGREGAR.
		List<Administrador> lista = this.getByExample(object); 
		if (lista.isEmpty()){
			setSession();
			setTransaction();
			administradorDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		}
		
	}

	@Override
	public void modify(Administrador object) throws Exception {
		// TODO Auto-generated method stub
		// SOLO SE PUEDE MODIFICAR LA CONTRASEÑA, NO LOS DATOS DEL ADMINISTRADOR PORQUE ES UN USUARIO ESPECIAL
	}

	@Override
	public void delete(Administrador object) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Administrador getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Administrador> getByExample(Administrador example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Administrador> listaAdministradorDevolver = (ArrayList<Administrador>) administradorDAO.findByExample((Administrador) example);
			sesionDeHilo.getTransaction().commit();
			return listaAdministradorDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar ADMINISTRADOR que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}
}
