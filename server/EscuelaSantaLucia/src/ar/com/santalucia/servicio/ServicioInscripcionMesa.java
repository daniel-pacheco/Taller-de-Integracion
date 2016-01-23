package ar.com.santalucia.servicio;

import java.util.List;

import ar.com.santalucia.aplicacion.gestor.academico.GestorInscripcion;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMesa;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMesaExamen;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.dominio.modelo.academico.Inscripcion;
import ar.com.santalucia.dominio.modelo.academico.Mesa;
import ar.com.santalucia.dominio.modelo.academico.MesaExamen;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.excepciones.ValidacionException;

/**
 * Clase servicio para inscripciones a mesa
 * 
 * <code>
 * **métodos de relaciones**
 * public Boolean inscribirAlumno(Alumno alumno, Long idInscripcion); OK
 * public Boolean asignarMesaAInscripcion(Mesa mesa, Long idInscripcion); OK
 * public Boolean asignarInscripcionAMesaExamen(Inscripcion inscripcion, Long idMesaExamen);
 *  
 * </code>
 * 
 * @author Eric
 * @version 1.0
 */

public class ServicioInscripcionMesa {
	
	private GestorInscripcion gInscripcion;
	private GestorMesaExamen gMesaExamen;
	private GestorMesa gMesa;
	private GestorAlumno gAlumno;
	
	public ServicioInscripcionMesa() throws Exception{
		try {
			gInscripcion = new GestorInscripcion();
			gMesa = new GestorMesa();
			gAlumno = new GestorAlumno();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar el servicio de operaciones básicas: "
					+ ex.getMessage());
		}
	}
	
	public Boolean addInscripcion(Inscripcion inscripcion) throws Exception {
		try {
			if (inscripcion.getIdInscripcion() == null) {
				gInscripcion.add(inscripcion);
			} else {
				gInscripcion.modify(inscripcion);
			}
		} catch(ValidacionException ex){
			throw ex;
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta la INSCRIPCION: " + ex.getMessage());
		}
		return true;
	}
	
	public Boolean deleteInscripcion(Inscripcion inscripcion) throws Exception{
		try {
			gInscripcion.delete(inscripcion);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar la INSCRIPCION: " + ex.getMessage());
		}
		return true;
	}
	
	public Inscripcion getInscripcion(Long idInscripcion) throws Exception {
		try {
			return gInscripcion.getById(idInscripcion);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la INSCRIPCION: " + ex.getMessage());
		}
	}
	
	public List<Inscripcion> getInscripciones(Inscripcion example) throws Exception {
		try {
			return gInscripcion.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista de INSCRIPCIONES: " + ex.getMessage());
		}
	}
	
	public Boolean inscribirAlumno(Alumno alumno, Long idInscripcion) throws Exception {
		try {
			Inscripcion inscripcion = gInscripcion.getById(idInscripcion);
			inscripcion.setAlumno(alumno);
			gInscripcion.modify(inscripcion);
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar el ALUMNO a la INSCRIPCIÓN: " + ex.getMessage());
		}
		
		return true;
	}
	
	public Boolean asignarMesaAInscripcion(Mesa mesa, Long idInscripcion) throws Exception {
		try {
			Inscripcion inscripcion = gInscripcion.getById(idInscripcion);
			inscripcion.setMesa(mesa);
			gInscripcion.modify(inscripcion);
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar la MESA a la INSCRIPCIÓN: " + ex.getMessage());
		}
		
		return true;
	}
	
	public Boolean addMesaExamen(MesaExamen mesaExamen) throws Exception {
		try {
			if (mesaExamen.getIdMesaExamen() == null) {
				gMesaExamen.add(mesaExamen);
			} else {
				gMesaExamen.modify(mesaExamen);
			}
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta la MESA-EXAMEN: " + ex.getMessage());
		}
		
		return true;
	}
	
	public Boolean deleteMesaExamen(MesaExamen mesaExamen) throws Exception {
		try {
			gMesaExamen.delete(mesaExamen);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar la MESA-EXAMEN: " + ex.getMessage());
		}
		
		return true;
	}
	
	public MesaExamen getMesaExamen(Long idMesaExamen) throws Exception {
		try {
			return gMesaExamen.getById(idMesaExamen);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la MESA-EXAMEN: " + ex.getMessage());
		}
	}
	
	public List<MesaExamen> listMesaExamen(MesaExamen example) throws Exception {
		try {
			return gMesaExamen.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista de MESA-EXAMEN: " + ex.getMessage());
		}
	}
	
	public Boolean asignarInscripcionAMesaExamen(Inscripcion inscripcion, Long idMesaExamen) throws Exception {
		try {
			MesaExamen mesaExamen = gMesaExamen.getById(idMesaExamen);
			mesaExamen.setInscripcion(inscripcion);
			gMesaExamen.modify(mesaExamen);
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar la INSCRIPCION a la MESA-EXAMEN: " + ex.getMessage());
		}
		
		return true;
	}

	
}
