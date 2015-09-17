package ar.com.santalucia.servicio.adapter;

import com.google.gson.Gson;

import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.servicio.ServicioAlumno;

/**
 * Clase adaptadora para servicio java-json
 * 
 * @author Ariel Ramirez
 *
 * @version 1.0
 */

public class AdapterAlumno implements ITargetUsuario {

	private ServicioAlumno sAlumno;
	private Gson json;

	public AdapterAlumno() throws Exception {
		sAlumno = new ServicioAlumno();
		json = new Gson();
	}

	@Override
	public String getUsuario(String id) {
		String alumnoJson = new String();
		try {
			Long idUsuario = Long.valueOf(id);
			alumnoJson = json.toJson(sAlumno.getUsuario(idUsuario));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return alumnoJson;
	}

	/**
	 * @param example
	 *            Toma un alumno a modo de ejemplo en formato JSON
	 * @return Devuelve un listado de alumno en formato JSON
	 */
	@Override
	public String getUsuarios(String example) {
		String alumnosJson = new String();
		Alumno alumno = new Alumno();
		alumno = json.fromJson(example, Alumno.class); // convertimos el string(JSON) example en Alumno
		alumnosJson = json.toJson(sAlumno.getUsuarios(alumno)); // convertimos el listado devuelto por getUsuarios en string(JSON)
		return alumnosJson;
	}

	@Override
	public boolean addUsuario(String usuario) {

		return false;
	}

	@Override
	public String getTelefonos(String usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMails(String usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitulos(String usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean modifyTelefono(String telefonoModificado) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyMail(String mailModificado) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyDireccion(String domicilioModificado) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyTitulo(String tituloModificado) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyUsuario(String usuarioModificado) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeTelefono(String telefonoEliminar) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeMail(String mailEliminar) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeDomicilio(String domicilioEliminar) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeTitulo(String titulo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeUsuario(String usuario) {
		// TODO Auto-generated method stub
		return false;
	}

}
