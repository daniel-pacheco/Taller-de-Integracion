package ar.com.santalucia.aplicacion.gestor.aulavirtual;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.aulavirtual.ConfiguracionEntregaHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.dominio.modelo.aulavirtual.ConfiguracionEntrega;
/**
 * 
 * @author Ariel Ramirez
 *
 * @version 1.0
 *
 */
public class GestorConfiguracionEntrega extends Gestor<ConfiguracionEntrega> {

	private ConfiguracionEntregaHome configuracionEntregaDAO;
	
	public GestorConfiguracionEntrega() throws Exception {
		super();
		configuracionEntregaDAO = new ConfiguracionEntregaHome();
	}

	@Override
	public void add(ConfiguracionEntrega object) throws Exception {
		try{
			setSession();
			setTransaction();
			configuracionEntregaDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar la CONFIGURACION ENTREGA: " + ex.getMessage());
		}
	}

	@Override
	public void modify(ConfiguracionEntrega object) throws Exception {
		try{
			setSession();
			setTransaction();
			configuracionEntregaDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar la CONFIGURACION ENTREGA: " + ex.getMessage());
		}
		
	}

	@Override
	public void delete(ConfiguracionEntrega object) throws Exception {
		try {
			setSession();
			setTransaction();
			configuracionEntregaDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar la CONFIGURACION ENTREGA: " + ex.getMessage());
		}
		
	}

	@Override
	public ConfiguracionEntrega getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			ConfiguracionEntrega configuracionEntregaDevolver = new ConfiguracionEntrega();
			configuracionEntregaDevolver = configuracionEntregaDAO.findById(id);
			return configuracionEntregaDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar la CONFIGURACION ENTREGA por su ID: " + ex.getMessage());
		}
	}
	
	public ArrayList<ConfiguracionEntrega> getByExample(ConfiguracionEntrega example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<ConfiguracionEntrega> listaConfiguracionEntregaDevolver = (ArrayList<ConfiguracionEntrega>) configuracionEntregaDAO.findByExample((ConfiguracionEntrega) example);
			sesionDeHilo.getTransaction().commit();
			return listaConfiguracionEntregaDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar CONFIGURACIONES ENTREGAS que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}
}
