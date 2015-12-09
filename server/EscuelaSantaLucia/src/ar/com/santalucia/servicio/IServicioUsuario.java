package ar.com.santalucia.servicio;

import java.util.List;
import java.util.Set;

import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;

/**
 * Interfaz de servicio para operaciones b�sicas con usuarios
 * 
 * @author Ariel Ramirez
 * 
 * @version 1.1
 * */

//	�ltimo modificador: Ariel Ramirez @ 07-12-2015 19:31

public interface IServicioUsuario<T> {
	/**
	 * Devuelve un Usuario en particular a partir de su Id
	 * @param id Id de Usuario que se desea obtener. 
	 * @return Una instancia de Usuario (o hijos).
	 * @throws Exception Lanzada en caso de existir un problema.
	 */
	T getUsuario(Long id) throws Exception;	
	/**
	 * Devuelve una lista completa de usuarios en estado persistentes que correspondan con el patr�n solicitado.
	 * @param example Objeto ejemplo (patr�n) para realizar la b�squeda.
	 * @return Lista de todos los usuarios del tipo solicitado.
	 * @throws Exception Lanzada en caso de existir un problema.
	 */ 
	List <T> getUsuarios(T example) throws Exception;
	/**
	 * Agrega a un objeto de tipo Usuario en la base de datos. Si el Usuario tiene asignado un valor de ID, se llamar� al m�todo modifyUsuario(T usuarioModificado) autom�ticamente.
	 * @param usuario Objeto de tipo Usuario que se desea dejar en estado persistente.
	 * @return True si el usuario pudo darse de alta.
	 * @throws Exception Lanzada en caso de existir un problema o por error de validaci�n.
	 */
	boolean addUsuario(T usuario) throws Exception;	
	/**
	 * Devuelve un Set de objetos de tipo Tel�fono asociado a un Usuario en particular.  
	 * @param idUsuario Id de Usuario del cu�l se desea obtener los tel�fonos asociados
	 * @return Listado (Set) de objetos de tipo Telefono.
	 * @throws Exception Lanzasa en caso de existir un problema o por problemas de validaci�n.
	 */
	Set<Telefono> getTelefonos(Long idUsuario) throws Exception;	
	/**
	 * Devuelve un Set de objetos de tipo Mail asociado a un Usuario en particular.
	 * @param idUsuario Id de Usuario del cu�l se desea obtener los mails asociados.
	 * @return listado (Set) de objetos de tipo Mail.
	 * @throws Exception
	 */
	Set<Mail> getMails(Long idUsuario) throws Exception;
	/**
	 * Devuelve un Set de objetos de tipo Titulo asociado a un Usuario en particular.
	 * @param idUsuario Id de Usuario del cu�l se desea obtener los t�tulos asociados.
	 * @return Listado (Set) de objetos de tipo Mail.
	 * @throws Exception Lanzada en caso de existir un problema.
	 */
	Set<Titulo> getTitulos(Long idUsuario) throws Exception; 	
	/**
	 * Modifica un objeto de tipo Telefono.
	 * @param telefonoModificado Objeto de tipo Telefono que se desea modificar.
	 * @return True si la operaci�n se pudo completar exitosamente.
	 * @throws Exception Lanzada en caso de existir un problema o si el objeto a modificar no est� en estado persistente.
	 */
	boolean modifyTelefono(Telefono telefonoModificado) throws Exception;					
	/**
	 * Modifica un objeto de tipo Mail.
	 * @param mailModificado Objeto de tipo Mail que se desea modificar.
	 * @return True si la operaci�n se pudo completar exitosamente.
	 * @throws Exception Lanzada en caso de existir un problema o si el objeto a modificar no est� en estado persitente.
	 */
	boolean modifyMail(Mail mailModificado) throws Exception; 	
	/**
	 * Modifica un objeto de tipo Domicilio.
	 * @param domicilioModificado Objeto de tipo Domicilio que se desea modificar.
	 * @return True si la operaci�n se pudo completar exitosamente.
	 * @throws Exception Lanzada en caso de existir un problema o si el objeto a modificar no est� en estado persistente.
	 */
	boolean modifyDomicilio(Domicilio domicilioModificado) throws Exception; 
	/**
	 * Modifica un objeto de tipo Titulo.
	 * @param tituloModificado Objeto de tipo Titulo que se desea modificar.
	 * @return True si la operaci�n se pudo completar exitosamente.
	 * @throws Exception Lanzada en caso de existir un problema o si el objeto a modificar no est� en estado persistente.
	 */
	boolean modifyTitulo(Titulo tituloModificado) throws Exception; 								
	/**
	 * Modifica un objeto de tipo Usuario. Para realizar operaciones de modificaci�n y bajas de objetos asociados a estos, prefiera los m�todos modifyObjeto() y/o deleteObjeto().
	 * @param usuarioModificado Objeto de tipo Usuario que se desea modificar.
	 * @return True si la operaci�n se pudo completar exitosamente,
	 * @throws Exception Lanzada en caso de existir un problema, si el objeto a modificar no est� en estado persistente o por error de validaci�n.
	 */
	boolean modifyUsuario(T usuarioModificado) throws Exception;
	/**
	 * Elimina un objeto de tipo Tel�fono que est� en estado persistente.
	 * @param idTelefono Id del objeto de tipo Telefono que se desea eliminar.
	 * @return True si la operaci�n se pudo completar exitosamente.
	 * @throws Exception Lanzada en caso de existir un problema o si el objeto no est� en estado persistente.
	 */
	boolean removeTelefono(Long idTelefono) throws Exception;		
	/**
	 * Elimina un objeto de tipo Mail que est� en estado persistente.
	 * @param idMail Id del objeto Mail que se desea eliminar.
	 * @return True si la operaci�n se pudo completar exitosamente.
	 * @throws Exception Lanzado en caso de existir un problema o si el objeto no est� en estado persistente.
	 */
	boolean removeMail(Long idMail) throws Exception;										
	/**
	 * Elimina un objeto de tipo Domicilio que est� en estado persistente.
	 * @param idDomicilio Id del objeto Domicilio que se desea eliminar.
	 * @return True si la operaci�n se pudo completar exitosamente.
	 * @throws Exception Lanzado en caso de existir un problema o si el objeto no est� en estado persistente.
	 */
	boolean removeDomicilio(Long idDomicilio) throws Exception;				
	/**
	 * Elimina un objeto de tipo titulo que est� en estado persistente.
	 * @param idTitulo Id del objeto Titulo que se desea eliminar.
	 * @return True si la operaci�n se pudo completar exitosamente.
	 * @throws Exception Lanzado en caso de existir un problema o si el objeto no est� en estado persistente.
	 */
	boolean removeTitulo(Long idTitulo) throws Exception;	
	/**
	 * Elimina un objeto l�gicamente (establece el estado de usuario en "inactivo") de tipo Usuario.
	 * @param usuario Objeto de tipo Usuario que se desea eliminar l�gicamente.
	 * @return True si la operaci�n se pudo completar exitosamente.
	 * @throws Exception Lanzado en caso de existir un problema o si el objeto no est� en estado persistente.
	 */
	boolean removeUsuario(T usuario) throws Exception;		
	/**
	 * Cierra la sesi�n de Hibernate forzando a ejecutar commit() previamente si existe alguna transacci�n activa, o cerr�ndola simplemente si no exite transacci�n activa.
	 * @throws Exception Lanzado en caso de existir un problema.
	 */
	void closeSession() throws Exception;											
}
