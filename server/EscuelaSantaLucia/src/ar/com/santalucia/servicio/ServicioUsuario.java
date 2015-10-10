package ar.com.santalucia.servicio;

import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorDomicilio;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorMail;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorTelefono;
import ar.com.santalucia.aplicacion.gestor.usuario.info.GestorTitulo;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;

public abstract class ServicioUsuario<T> implements IServicioUsuario<T> {
	protected GestorDomicilio gDomicilio;
	protected GestorMail gMail;
	protected GestorTelefono gTelefono;
	protected GestorTitulo gTitulo;

	public ServicioUsuario() throws Exception {
		try {
			gDomicilio = new GestorDomicilio();
			gMail = new GestorMail();
			gTelefono = new GestorTelefono();
			gTitulo = new GestorTitulo();
		} catch (Exception ex) {
			throw new Exception(
					"Ha ocurrido un problema al intentar inicializar el servicio de operaciones básicas. "
							+ ex.getMessage());
		}
	}
	
	public boolean modifyTelefono(Telefono telefonoModificado) throws Exception {
		try {
			gTelefono.modify(telefonoModificado);
			return true;
		} catch (Exception ex) {
			throw ex;
		}	
	}
	
	public boolean modifyMail(Mail mailModificado) throws Exception{
		try {
			gMail.modify(mailModificado);
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public boolean modifyDomicilio(Domicilio domicilioModificado) throws Exception{
		try {
			gDomicilio.modify(domicilioModificado);
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public boolean modifyTitulo(Titulo tituloModifado) throws Exception{
		try{
			gTitulo.modify(tituloModifado);
			return true;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	public boolean removeTelefono(Long idTelefono)  throws Exception{
		try {
			gTelefono.delete(gTelefono.getById(idTelefono));
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public boolean removeMail(Long idMail) throws Exception{
		try {
			gMail.delete(gMail.getById(idMail));
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public boolean removeTitulo(Long idTitulo) throws Exception{
		try{
			gTitulo.delete(gTitulo.getById(idTitulo));
			return true;
		}catch(Exception ex){
			throw ex;
		}
	}
	
	public boolean removeDomicilio(Long idDomicilio) throws Exception{
		try {
			gDomicilio.delete(gDomicilio.getById(idDomicilio));
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}
}
