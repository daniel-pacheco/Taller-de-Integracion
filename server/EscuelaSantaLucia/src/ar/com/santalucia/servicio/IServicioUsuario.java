package ar.com.santalucia.servicio;

import java.util.List;
import java.util.Set;

import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;

/**
 * Interfaz de servicio para operaciones básicas con usuarios
 * 
 * @author Ariel Ramirez
 * 
 * @version 1.1
 * */

//	Último modificador: Ariel Ramirez @ 14-09-2015 15:30

public interface IServicioUsuario<T> {
	T getUsuario(Long id) throws Exception;
	List <T> getUsuarios(T example);
	boolean addUsuario(T usuario) throws Exception;
	Set<Telefono> getTelefonos(Long idUsuario) throws Exception;	// sólo pasar id del usuario nuevo
	Set<Mail> getMails(Long idUsuario) throws Exception; 				// sólo pasar id del usuario
	Set<Titulo> getTitulos(Long idUsuario) throws Exception; 			// sólo pasar id del usuario
	boolean modifyTelefono(Telefono telefonoModificado);
	boolean modifyMail(Mail mailModificado);
	boolean modifyDireccion(Domicilio domicilioModificado);
	boolean modifyTitulo(Titulo tituloModificado);
	boolean modifyUsuario(T usuarioModificado) throws Exception;
	boolean removeTelefono(Telefono telefonoEliminar);
	boolean removeMail(Mail mailEliminar);
	boolean removeDomicilio(Domicilio domicilioEliminar);
	boolean removeTitulo(Titulo titulo);
	boolean removeUsuario(T usuario);
	void closeTransaction() throws Exception;
}
