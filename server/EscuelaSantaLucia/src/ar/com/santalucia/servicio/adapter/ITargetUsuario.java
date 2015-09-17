package ar.com.santalucia.servicio.adapter;

/**
 * Interfaz Target para implementar el aptrón Adapter Servicio - Json 
 * 
 * @author Ariel Ramirez
 *
 *@version 1.0
 */

public interface ITargetUsuario {
	String getUsuario(String id);
	String getUsuarios(String example);
	boolean addUsuario(String usuario);
	String getTelefonos(String usuario);
	String getMails(String usuario);
	String  getTitulos(String usuario);
	boolean modifyTelefono(String telefonoModificado);
	boolean modifyMail(String mailModificado);
	boolean modifyDireccion(String domicilioModificado);
	boolean modifyTitulo(String tituloModificado);
	boolean modifyUsuario(String usuarioModificado);
	boolean removeTelefono(String telefonoEliminar);
	boolean removeMail(String mailEliminar);
	boolean removeDomicilio(String domicilioEliminar);
	boolean removeTitulo(String titulo);
	boolean removeUsuario(String usuario);
}
