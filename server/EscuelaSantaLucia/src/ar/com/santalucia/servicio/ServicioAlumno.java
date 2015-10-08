package ar.com.santalucia.servicio;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.info.Domicilio;
import ar.com.santalucia.dominio.modelo.usuarios.info.Mail;
import ar.com.santalucia.dominio.modelo.usuarios.info.Telefono;
import ar.com.santalucia.dominio.modelo.usuarios.info.Titulo;
import ar.com.santalucia.excepciones.ValidacionException;

// Esta clase es un Facade que permite acceder a todas las operaciones de ABM de alumno,
// incorporando las ABM  para sus compuestos (telefono, mail y dirección)

/**
 * 
 * @author Ariel Ramirez
 * 
 * @version 1.0
 *
 */

// Último modificador: Ariel Ramirez @ 26-09-2015 12:55

public class ServicioAlumno extends ServicioUsuario<Alumno> {

	private GestorAlumno gAlumno;

	public ServicioAlumno() throws Exception {
		super();
		gAlumno = new GestorAlumno();
	}

	@Override
	public Alumno getUsuario(Long id) throws Exception {
		if (id > 0) {
			try {
				return gAlumno.getById(id);
			} catch (Exception ex) {
				throw new Exception("Servicio: problemas. " + ex.getMessage());
			}
		}
		return null;
	}

	@Override
	public List<Alumno> getUsuarios(Alumno example) {																		
		try {
			return gAlumno.getByExample(example);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addUsuario(Alumno usuario) throws Exception {															
		try {
			if(usuario.getIdUsuario() == null){
				gAlumno.add(usuario);
					}else{
				gAlumno.modify(usuario);
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
	public Set<Telefono> getTelefonos(/* Alumno usuario */ Long idUsuario) throws Exception {				
		Set<Telefono> telefonos = new HashSet<Telefono>();
		telefonos = null;
		try {
			Alumno alumno = new Alumno();
			if ((alumno = getUsuario(idUsuario)) != null) { // pruebo si el alumno existe, sino no - > Null
				telefonos = alumno.getListaTelefonos(); // getListaTelefonos del alumno que se buscó, si existe
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
			Alumno alumno = new Alumno();
			if ((alumno = getUsuario(idUsuario)) != null) {
				mails = alumno.getListaMails();
			}
		} catch (Exception ex) {
			throw new Exception(
					"Servicio: Ha ocurrido un problema al intentar recuperar los mails. " + ex.getMessage());
		}
		return mails;
	}

	@Override
	public Set<Titulo> getTitulos(Long idUsuario) {
		// Este método no se implementa para esta clase
		return null;
	}

	@Override
	public boolean modifyTelefono(Telefono telefonoModificado) throws Exception {
		try {
			gTelefono.modify(telefonoModificado);
			return true;
		} catch (Exception ex) {
			throw ex;
		}
		
	}

	@Override
	public boolean modifyMail(Mail mailModificado) {
		try {
			gMail.modify(mailModificado);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean modifyDomicilio(Domicilio domicilioModificado) throws Exception{
		try {
			gDomicilio.modify(domicilioModificado);
			return true;
		} catch (Exception ex) {
			throw ex;
		}

	}

	/**
	 * @return Siempre se devolverá true dado a que este método no se implementa
	 *         para esta clase.
	 */
	@Override
	public boolean modifyTitulo(Titulo tituloModifado) {
		// Este método no se implementa para esta clase.
		return true;
	}

	@Override
	public boolean removeTelefono(Long idTelefono)  throws Exception{
		try {
			gTelefono.delete(gTelefono.getById(idTelefono));
			return true;
		} catch (Exception ex) {
			//e.printStackTrace();
			throw ex;
		}
	}

	@Override
	public boolean removeMail(Long idMail) throws Exception{
		try {
			gMail.delete(gMail.getById(idMail));
			return true;
		} catch (Exception ex) {
			//e.printStackTrace();
			throw ex;
		}
	}

	/**
	 * @return Siempre se devolverá true dado a que este método no se implementa
	 *         para esta clase.
	 */
	@Override
	public boolean removeTitulo(Long idTitulo) throws Exception{
		// Este método no se implementa para esta clase.
		return true;
	}

	@Override
	public boolean removeUsuario(Alumno usuario) throws Exception {																					
		try {
			gAlumno.delete(usuario);
			return true;
		} catch (Exception ex) {
			//e.printStackTrace();
			throw ex;
		}
	}

	@Override
	public boolean modifyUsuario(Alumno usuarioModificado) throws Exception{										
		try {
			gAlumno.modify(usuarioModificado);
			return true;
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			throw new Exception("Servicio modify(): no se pudo completar la operacion. "+ex.getMessage());
		}
	}

	@Override
	public boolean removeDomicilio(Long idDomicilio) {
		try {
			gDomicilio.delete(gDomicilio.getById(idDomicilio));
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return false;
	}

	@Override
	public void closeSession() throws Exception {
		gAlumno.closeSession();
	}

	
	// No se incluyen los métodos para agregar mail, o teléfono dado a que solo es necesario llamar a this.addUsuario(Alumno usuario)

}
 