package ar.com.santalucia.servicio;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.usuario.GestorDirectivo;
import ar.com.santalucia.dominio.modelo.usuarios.Directivo;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;
import ar.com.santalucia.excepciones.ValidacionException;

public class ServicioDirectivo extends ServicioUsuario<Directivo> {

	private GestorDirectivo gDirectivo;
	public ServicioDirectivo() throws Exception {
		super();
		gDirectivo=new GestorDirectivo();
	}

	@Override
	public Directivo getUsuario(Long id) throws Exception {
		if (id>0){
			try{
				return gDirectivo.getById(id);
			}catch(Exception ex){
				throw new Exception("Servicio: problemas. " + ex.getMessage());
			}
		}
		return null;
	}

	@Override
	public List<Directivo> getUsuarios(Directivo example) throws Exception {
		try{
			return gDirectivo.getByExample(example);
		}catch (Exception ex){
			throw new Exception("Servicio: problemas. " + ex.getMessage());
		}
	}

	@Override
	public boolean addUsuario(Directivo usuario) throws Exception {
		try {
			if(usuario.getIdUsuario() == null){
				gDirectivo.add(usuario);
					}else{
				gDirectivo.modify(usuario);
			};
			return true;
		}
		catch (ValidacionException ex){
			throw ex;
		} 
		catch (Exception ex) {
			throw new Exception("Servicio add(): no se pudo completar la operacion. "+ex.getMessage());
		}
	}

	@Override
	public Set<Telefono> getTelefonos(Long idUsuario) throws Exception {
		Set<Telefono> telefonos = new HashSet<Telefono>();
		telefonos = null;
		try {
			Directivo directivo = new Directivo();
			if ((directivo = getUsuario(idUsuario)) != null) { // pruebo si el directivo existe, sino no - > Null
				telefonos = directivo.getListaTelefonos(); // getListaTelefonos del directivo que se busc, si existe
			}
		} catch (Exception ex) {
			throw new Exception(
					"Servicio: Ha ocurrido un problema al intentar recuperar los teléfonos . " + ex.getMessage());
		}
		return telefonos;
	}

	@Override
	public Set<Mail> getMails(Long idUsuario) throws Exception {
		Set<Mail> mails = new HashSet<Mail>();
		mails = null;
		try {
			Directivo directivo = new Directivo();
			if ((directivo = getUsuario(idUsuario)) != null) {
				mails = directivo.getListaMails();
			}
		} catch (Exception ex) {
			throw new Exception(
					"Servicio: Ha ocurrido un problema al intentar recuperar los mails. " + ex.getMessage());
		}
		return mails;
	}

	@Override
	public Set<Titulo> getTitulos(Long idUsuario) throws Exception {
		Set<Titulo> titulos = new HashSet<Titulo>();
		titulos = null;
		try {
			Directivo directivo = new Directivo();
			if ((directivo = getUsuario(idUsuario)) != null) {
				titulos = directivo.getListaTitulos();
			}
		} catch (Exception ex) {
			throw new Exception(
					"Servicio: Ha ocurrido un problema al intentar recuperar los titulos. " + ex.getMessage());
		}
		return titulos;
	}

	@Override
	public boolean modifyUsuario(Directivo usuarioModificado) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeUsuario(Directivo usuario) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void closeSession() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
