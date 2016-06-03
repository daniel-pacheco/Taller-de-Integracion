package ar.com.santalucia.aplicacion.gestor.aulavirtual;

import java.util.ArrayList;

import ar.com.santalucia.accesodatos.dao.aulavirtual.AulaVirtualHome;
import ar.com.santalucia.aplicacion.gestor.Gestor;
import ar.com.santalucia.dominio.modelo.aulavirtual.AulaVirtual;
/**
 * 
 * @author Ariel Ramirez
 *
 * @version 1.0
 * 
 */
public class GestorAulaVirtual extends Gestor<AulaVirtual> {
	private AulaVirtualHome aulaVirtualDAO;
	
	public GestorAulaVirtual() throws Exception {
		super();
		aulaVirtualDAO = new AulaVirtualHome();
	}

	@Override
	public void add(AulaVirtual object) throws Exception {
		try{
			setSession();
			setTransaction();
			aulaVirtualDAO.persist(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al agregar el AULA VIRTUAL: " + ex.getMessage());
		}
	}

	@Override
	public void modify(AulaVirtual object) throws Exception {
		try{
			setSession();
			setTransaction();
			aulaVirtualDAO.attachDirty(object);
			sesionDeHilo.getTransaction().commit();
		}catch(Exception ex){
			setSession();
			setTransaction();
			sesionDeHilo.getTransaction().rollback();
			throw new Exception("Ha ocurrido un problema al actualizar el AULA VIRTUAL: " + ex.getMessage());
		}
		
	}

	@Override
	public void delete(AulaVirtual object) throws Exception {
		try {
			setSession();
			setTransaction();
			aulaVirtualDAO.delete(object);
			sesionDeHilo.getTransaction().commit();
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un problema al eliminar el AULA VIRTUAL: " + ex.getMessage());
		}
		
	}

	@Override
	public AulaVirtual getById(Long id) throws Exception {
		try {
			setSession();
			setTransaction();
			AulaVirtual aulaVirtualDevolver = new AulaVirtual();
			aulaVirtualDevolver = aulaVirtualDAO.findById(id);
			return aulaVirtualDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception("Ha ocurrido un error al buscar el AULA VIRTUAL por su ID: " + ex.getMessage());
		}
	}
	
	public ArrayList<AulaVirtual> getByExample(AulaVirtual example) throws Exception {
		try {
			setSession();
			setTransaction();
			ArrayList<AulaVirtual> listaAulaVirtualDevolver = (ArrayList<AulaVirtual>) aulaVirtualDAO.findByExample((AulaVirtual) example);
			sesionDeHilo.getTransaction().commit();
			return listaAulaVirtualDevolver;
		} catch (Exception ex) {
			closeSession();
			throw new Exception(
					"Ha ocurrido un error al buscar AULAS VIRTUALES que coincidan con el ejemplo dado: " + ex.getMessage());
		}
	}
	
}
