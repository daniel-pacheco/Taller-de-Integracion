package ar.com.santalucia.aplicacion.gestor.sistema.login;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import ar.com.santalucia.accesodatos.dao.sistema.login.LoginHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.dominio.modelo.sistema.login.Login;
/**
 * 
 * @author Ariel Ramirez
 *
 */

// Último modificador: Ariel Ramirez @ 05/01/2015 17:34
public class GestorLogin extends Gestor<Login> {

	private LoginHome loginDAO;
	
	
	public GestorLogin() throws Exception {
		super();
		try{
			loginDAO = new LoginHome();
		}catch(Exception ex){
			closeSession();
			throw new Exception("[GestorLogin] " + "Ha ocurrido un problema al inicializar el gestor. "+ex.getMessage());
		}
	}

	@Override
	public void add(Login object) throws Exception {
		// CUANDO SE CREA UN USUARIO NUEVO TIENE QUE LLEGAR LOS DATOS PARA DAR DE ALTA LA CREDENCIAL
		try{
			setSession();
			setTransaction();
			object.setClave(encriptar(object.getClave()));
			loginDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("[GestorLogin] " + "Ha ocurrido un problema al agregar el objeto. " + ex.getMessage());
		}
		
	}

	@Override
	public void modify(Login object) throws Exception {
		// PARA CAMBIOS DE CLAVE O DATO DE LOGIN (DOCUMENTO, DATOS DE ULTIMO ACCESO)
		try{
			setSession();
			setTransaction();
			loginDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("[GestorLogin] " + "Ha ocurrido un problema al modificar el objeto. " + ex.getMessage());
		}
	}

	@Override
	public void delete(Login object) throws Exception {
		// ELIMINA LA CREDENCIAL
		try{
			setSession();
			setTransaction();
			loginDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("[GestorLogin] " + "Ha ocurrido un problema al eliminar el objeto. " + ex.getMessage());
		}
	}

	@Override
	public Login getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<Login> getByExample(Login example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<Login> listaLoginDevolver = (ArrayList<Login>) loginDAO.findByExample(example);
			sesionDeHilo.getTransaction().commit();
			return listaLoginDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("[GestorLogin] " + "Ha ocurrido un error al buscar objetos que coincidan con el ejemplo dado. " + ex.getMessage());
		}
	}
	
	/**
	 * Actualiza el dato de identificación de usuario (número de documento) en la tabla de login.
	 * @param usuarioExistente
	 * @param usuarioNuevo
	 * @throws Exception
	 */
	public void updateUser(Long usuarioExistente, Long usuarioNuevo, String rol) throws Exception{
		try{
			Login login = new Login();  
			List<Login> listaLogin = new ArrayList<Login>();
			listaLogin = this.getByExample(new Login(null, usuarioExistente, null, null, null, rol, null));
			for(Login l:listaLogin){
				login = l;
			}
			login.setUsuario(usuarioNuevo);
			this.modify(login);
		}catch(Exception ex){
			closeSession();
			throw new Exception("[GestorLogin] " + "Ha ocurrido un error al actualizar el usuario. " + ex.getMessage());
		}
	}
	
	public Boolean autenticar(Login login) throws Exception{
		// LOGICA PARA VERIFICAR CREDENCIALES
		// Recibir las credenciales entrantes
		// Buscar en tabla de login
		// Traer credencial coincidencia, si existira (Si no, false, login error: usuario o clave incorrecta)
		// Comrpobar habilitacion de login, si no está habilitado lanzar excepcion UsuarioNoHabilitado
		
		try {
			String clave = encriptar(login.getClave());			// ENCRIPTAR LA CLAVE PARA COMPARAR
			login.setClave(clave); 		
			//login.setUltimoAcceso(null); 				 		// FIJA EN NULL EL ATRIBUTO ultimoAcceso
			//login.setUltimoEgreso(null);						// FIJA EN NULL EL ATRIBUTO ultimoEgreso
			login.setHabilitado(null); 							// FIJA EN NULL EL ATRIBUTO habilitado
			login.setIdLogin(null);
			List<Login> listLogin = this.getByExample(login); 	// BUSCAR COINCIDENCIA
			if (listLogin.isEmpty()){							// SI NO SE DEVOLVIERON ELEMENTOS, NO HUBO COINCIDENCIA
				return false; 
			}
			for(Login l:listLogin){
				//login.setHabilitado(l.getHabilitado());
				login = l;
			}
			if(login.getHabilitado() == false){					// VERIFICA EL ESTADO DEL USUARIO PARA LOGIN
				//throw usuarioNoHabilitado();
			}
			Calendar calendar = Calendar.getInstance();			// FIJAR FECHA Y HORA ACTUAL (!) ¡TOMARÁ FECHA DEL SISTEMA!
			login.setUltimoAcceso(calendar.getTime());
			login.setUltimoEgreso(null);
			this.modify(login);
			return true; // CREDENCIALES CORRECTAS 
		} catch (Exception ex) {
			throw new Exception ("[GestorLogin] " + "Ha ocurrido un error intentar validar la credencial. " + ex.getMessage());
		}
	}
	
	public Boolean finalizar(Login login) throws Exception{
		// INVALIDAR TOKEN?
		// OBTENER CREDENCIAL DESDE BD
		// FIJAR ATRIBUTO ultimaSalida
		// MODIFICAR
		Login lTemp = new Login();
		try{
			lTemp.setUsuario(login.getUsuario());
			List<Login> listLogin =  this.getByExample(lTemp);
			if(!listLogin.isEmpty()){
				for(Login l:listLogin){
					login = l;
			}
			Calendar calendar = Calendar.getInstance();			// FIJAR FECHA Y HORA ACTUAL (!) ¡TOMARÁ FECHA DEL SISTEMA!
			login.setUltimoEgreso(calendar.getTime());	 		
			this.modify(login);									// HACE EFECTIVO EL CAMBIO
			}else{
				return false;									// HA OCURRIDO UN PROBLEMA :/
			}
			return true;	
		}catch(Exception ex){
			throw new Exception ("[GestorLogin] " + "Ha ocurrido un error intentar cerrar la sesion de usuario " + ex.getMessage());
		}
	}
	
	public Boolean reestablecer(Login login){
		// REESTABLECE LA CLAVE (BLANQUEO)
	
		return true;
	}
	
	public String encriptar(String clave) throws Exception{
		StringBuffer cadenaEncriptada = new StringBuffer();
		MessageDigest md = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
		md.update((clave).getBytes());
		byte[] digest = md.digest();
		for (byte b : digest) {
			cadenaEncriptada.append(Integer.toHexString(0xFF & b));
	      }	
		return cadenaEncriptada.toString();
	}
	
}
