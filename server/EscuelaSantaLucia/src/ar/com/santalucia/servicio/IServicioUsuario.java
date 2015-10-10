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
	List <T> getUsuarios(T example) throws Exception;													//  En endPoint 
	boolean addUsuario(T usuario) throws Exception;						//  En endPoint
	Set<Telefono> getTelefonos(Long idUsuario) throws Exception;	//  En endPoint
	Set<Mail> getMails(Long idUsuario) throws Exception; 				//  En endPoint 
	Set<Titulo> getTitulos(Long idUsuario) throws Exception; 			//  En endPoint
	boolean modifyTelefono(Telefono telefonoModificado) throws Exception;					//En endPoint
	boolean modifyMail(Mail mailModificado) throws Exception; 									//
	boolean modifyDomicilio(Domicilio domicilioModificado) throws Exception; 			// En endPoint
	boolean modifyTitulo(Titulo tituloModificado) throws Exception; 								//
	boolean modifyUsuario(T usuarioModificado) throws Exception;	//  En endPoint
	boolean removeTelefono(Long idTelefono) throws Exception;					//
	boolean removeMail(Long idMail) throws Exception;										//
	boolean removeDomicilio(Long idDomicilio) throws Exception;				//
	boolean removeTitulo(Long idTitulo) throws Exception;												//
	boolean removeUsuario(T usuario) throws Exception;				//  En endPoint
	void closeSession() throws Exception;											//
}
