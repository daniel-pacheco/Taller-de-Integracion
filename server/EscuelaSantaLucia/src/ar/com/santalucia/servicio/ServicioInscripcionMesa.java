package ar.com.santalucia.servicio;

import java.util.List;

import ar.com.santalucia.aplicacion.gestor.academico.GestorInscripcion;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMesa;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMesaExamen;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorNota;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.dominio.modelo.academico.Inscripcion;
import ar.com.santalucia.dominio.modelo.academico.Mesa;
import ar.com.santalucia.dominio.modelo.academico.MesaExamen;
import ar.com.santalucia.dominio.modelo.desempenio.Nota;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.excepciones.ValidacionException;

/**
 * Clase servicio para inscripciones a mesa
 * 
 * @author Eric
 * @version 1.0
 */

public class ServicioInscripcionMesa {
	
	private GestorInscripcion gInscripcion;
	private GestorMesaExamen gMesaExamen;
	private GestorNota gNota;
	private GestorMesa gMesa;
	private GestorAlumno gAlumno;
	
	public ServicioInscripcionMesa() throws Exception{
		try {
			gInscripcion = new GestorInscripcion();
			gMesa = new GestorMesa();
			gAlumno = new GestorAlumno();
			gNota = new GestorNota();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar el servicio de operaciones b�sicas: "
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
			throw new Exception("No se pudo asignar el ALUMNO a la INSCRIPCI�N: " + ex.getMessage());
		}
		
		return true;
	}
	
	public Boolean asignarMesaAInscripcion(Mesa mesa, Long idInscripcion) throws Exception {
		try {
			Inscripcion inscripcion = gInscripcion.getById(idInscripcion);
			inscripcion.setMesa(mesa);
			gInscripcion.modify(inscripcion);
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar la MESA a la INSCRIPCI�N: " + ex.getMessage());
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

	
	public Boolean addNota(Nota nota) throws Exception {
		try {
			if (nota.getIdNota() == null) {
				gNota.add(nota);
			} else {
				gNota.modify(nota);
			}
		} catch (ValidacionException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta la NOTA: " + ex.getMessage());
		}
		return false;
	}
	
	public Boolean deleteNota(Nota nota) throws Exception{
		try {
			gNota.delete(nota);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar la NOTA: " + ex.getMessage());
		}
		return true;
	}

	public Nota getNota(Long idNota) throws Exception {
		try {
			return gNota.getById(idNota);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la NOTA: " + ex.getMessage());
		}
	}
	
	public List<Nota> getNotas(Nota example) throws Exception {
		try {
			return gNota.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista de NOTA: " + ex.getMessage());
		}
	}

	public Boolean asignarNotaAMesaExamen(Nota nota, Long idMesaExamen) throws Exception {
		try {
			/**
			 * 1) obtener mesa-examen
			 * 2) asignar nota a mesa-examen
			 * 3) asignar materia a nota (mesa-examen->inscripcion->mesa->materia)
			 * 4) modify mesa-examen
			 * 5) modifi nota
			 */
			MesaExamen mesaExamen = gMesaExamen.getById(idMesaExamen);
			mesaExamen.setNota(nota);
			nota.setMateria(mesaExamen.getInscripcion().getMesa().getMateria());
			gMesaExamen.modify(mesaExamen);
			gNota.modify(nota);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
}
