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

//	Último modificador: Ariel Ramirez @ 26-09-2015 12:55

public interface IServicioUsuario<T> {
	T getUsuario(Long id) throws Exception;										//  En endPoint
	List <T> getUsuarios(T example);													//  En endPoint 
	boolean addUsuario(T usuario) throws Exception;						//  En endPoint
	Set<Telefono> getTelefonos(Long idUsuario) throws Exception;	//  En endPoint
	Set<Mail> getMails(Long idUsuario) throws Exception; 				//  En endPoint 
	Set<Titulo> getTitulos(Long idUsuario) throws Exception; 			//  En endPoint
	boolean modifyTelefono(Telefono telefonoModificado);					//
	boolean modifyMail(Mail mailModificado); 									//
	boolean modifyDireccion(Domicilio domicilioModificado); 			//
	boolean modifyTitulo(Titulo tituloModificado); 								//
	boolean modifyUsuario(T usuarioModificado) throws Exception;	//  En endPoint
	boolean removeTelefono(Telefono telefonoEliminar);					//
	boolean removeMail(Mail mailEliminar);										//
	boolean removeDomicilio(Domicilio domicilioEliminar);				//
	boolean removeTitulo(Titulo titulo);												//
	boolean removeUsuario(T usuario);												//  En endPoint
	void closeSession() throws Exception;											//
}
