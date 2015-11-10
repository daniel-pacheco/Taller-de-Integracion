package ar.com.santalucia.aplicacion.gestor.usuario;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.usuario.UsuarioHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.aplicacion.gestor.IListable;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorDomicilio;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorMail;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorTelefono;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Usuario;

public abstract class GestorUsuario extends Gestor<Usuario> implements IListable{
	
	protected UsuarioHome usuarioDAO;
	protected GestorTelefono GTelefono;
	protected GestorMail GMail;
	protected GestorDomicilio GDomicilio;
	
	public GestorUsuario() throws Exception {
		super();
		usuarioDAO = new UsuarioHome();
		GTelefono = new GestorTelefono();
		GMail = new GestorMail();
		GDomicilio = new GestorDomicilio();
	}

	@Override
	public abstract void add(Usuario object) throws Exception;

	@Override
	public abstract void modify(Usuario object) throws Exception;

	@Override
	public void delete(Usuario object) throws Exception{
		try {
			setSession();
			setTransaction();
			usuarioDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el objeto: " + ex.getMessage());
		}
	}

	@Override
	public Usuario getById(Long id) throws Exception{
		try {
			setSession();
			setTransaction();
			Usuario usuarioDevolver = new Usuario();
			usuarioDevolver = usuarioDAO.findById(id);
			// NO HACER COMMIT para poder más tarde hacer el get de la lista de
			// teléfonos
			// y/o mails, Hibernate no las trae de una. La transacción se cierra
			// en el modificar.
			// sesionDeHilo.getTransaction().commit();
			return usuarioDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el objeto por su ID: " + ex.getMessage());
		}
	};
	
	public ArrayList getByExample(Usuario example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Usuario> listaDirectivosDevolver = (ArrayList<Usuario>) usuarioDAO.findByExample(example);
			sesionDeHilo.getTransaction().commit();
			return listaDirectivosDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado: " + ex.getMessage());
		}

	}
	
	public Boolean existeDocumento(Usuario usuario) throws Exception {
		Boolean resultado = false;
		Usuario usuarioEjemplo = new Usuario();
		usuarioEjemplo.setNroDocumento(usuario.getNroDocumento());
		try {
			ArrayList<Usuario> listaUsuarios = this.getByExample(usuarioEjemplo);
			if (usuario.getIdUsuario() == null) {
				resultado = (listaUsuarios.isEmpty() ? false : true);
			} else {
				if (!listaUsuarios.isEmpty()) {
					Usuario usuarioTemp = new Usuario();
					for (Usuario u : listaUsuarios) {
						usuarioTemp = u;
					}
					if (usuarioTemp.getIdUsuario().equals(usuario.getIdUsuario())) {
						resultado = false;
					} else {
						resultado = true;
					}
				}
			}
		} catch (Exception ex) {
			throw ex;
		}
		return resultado;
	}

	public Boolean existeNombreUsuario(Usuario usuario) throws Exception {
		Boolean resultado = false;
		Usuario usuarioEjemplo = new Usuario();
		usuarioEjemplo.setNombreUsuario(usuario.getNombreUsuario());
		try {
			ArrayList<Usuario> listaUsuarios = this.getByExample(usuarioEjemplo);
			if (usuario.getIdUsuario() == null) {
				resultado = (listaUsuarios.isEmpty() ? false : true);
			} else {
				if (!listaUsuarios.isEmpty()) {
					Usuario usuarioTemp = new Alumno();
					for (Usuario u : listaUsuarios) {
						usuarioTemp = u;
					}
					if (usuarioTemp.getIdUsuario().equals(usuario.getIdUsuario())) {
						resultado = false;
					} else {
						resultado = true;
					}
				}
			}
		} catch (Exception ex) {
			throw ex;
		}
		return resultado;
	}
}
