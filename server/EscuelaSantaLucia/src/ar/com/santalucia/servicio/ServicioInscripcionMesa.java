package ar.com.santalucia.servicio;

import java.util.List;

import ar.com.santalucia.aplicacion.gestor.academico.GestorInscripcion;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMesa;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMesaExamenHist;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorNota;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.dominio.modelo.academico.Inscripcion;
import ar.com.santalucia.dominio.modelo.academico.Mesa;
import ar.com.santalucia.dominio.modelo.academico.MesaExamenHist;
import ar.com.santalucia.dominio.modelo.desempenio.Nota;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;
import ar.com.santalucia.dominio.modelo.usuarios.Personal;
import ar.com.santalucia.excepciones.ValidacionException;

/**
 * Clase servicio para inscripciones a mesa
 * 
 * @author Eric
 * @version 1.0
 */

public class ServicioInscripcionMesa {
	
	private GestorInscripcion gInscripcion;
	private GestorMesaExamenHist gMesaExamenHist;
	private GestorNota gNota;
	private GestorMesa gMesa;
	private GestorAlumno gAlumno;
	
	public ServicioInscripcionMesa() throws Exception{
		try {
			gInscripcion = new GestorInscripcion();
			gMesaExamenHist = new GestorMesaExamenHist();
			gMesa = new GestorMesa();
			gAlumno = new GestorAlumno();
			gNota = new GestorNota();
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
			//inscripcion.setMesa(mesa);
			gInscripcion.modify(inscripcion);
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar la MESA a la INSCRIPCIÓN: " + ex.getMessage());
		}
		
		return true;
	}
	
	
	public Boolean addMesaExamenHist(MesaExamenHist mesaExamenHist) throws Exception {
		try {
			if (mesaExamenHist.getIdMesaExamenHist() == null) {
				gMesaExamenHist.add(mesaExamenHist);
			} else {
				gMesaExamenHist.modify(mesaExamenHist);
			}
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta la MESAEXAMENHIST: " + ex.getMessage());
		}
		
		return true;
	}
	
	public Boolean deleteMesaExamen(MesaExamenHist mesaExamen) throws Exception {
		try {
			gMesaExamenHist.delete(mesaExamen);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar la MESA-EXAMEN: " + ex.getMessage());
		}
		
		return true;
	}
	
	public MesaExamenHist getMesaExamen(Long idMesaExamen) throws Exception {
		try {
			return gMesaExamenHist.getById(idMesaExamen);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la MESA-EXAMEN: " + ex.getMessage());
		}
	}
	
	public List<MesaExamenHist> listMesaExamen(MesaExamenHist example) throws Exception {
		try {
			return gMesaExamenHist.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista de MESA-EXAMEN: " + ex.getMessage());
		}
	}
	
	/*
	public Boolean asignarInscripcionAMesaExamen(Inscripcion inscripcion, Long idMesaExamen) throws Exception {
		try {
			MesaExamen mesaExamen = gMesaExamenHist.getById(idMesaExamen);
			mesaExamen.setInscripcion(inscripcion);
			gMesaExamenHist.modify(mesaExamen);
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar la INSCRIPCION a la MESA-EXAMEN: " + ex.getMessage());
		}
		
		return true;
	}
	*/
	
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
	
	public Boolean pasarAHistorico(Inscripcion inscripcion) throws Exception {
		/*
		 * 1) Inscripcion -> mesa, alumno (nombre y apellido), nota (calificacion), asistencia
		 * 2) Mesa -> docentes (nombres y apellidos [3]), materia, fechas inicio y fin
		 * 3) Materia -> nombre
		 */
		try {
			MesaExamenHist mesaExamen = new MesaExamenHist();
			//mesaExamen.setNota(inscripcion.getNota().getCalificacion()); //asignando la calificación
			//mesaExamen.setAsistencia(inscripcion.getAsistencia());
			mesaExamen.setFechaInscripcion(inscripcion.getFecha());
			//mesaExamen.setNombreMateria(inscripcion.getMesa().getMateria().getNombre());
			//Personal docente1 = (Personal) inscripcion.getMesa().getIntegrantesTribunal().toArray()[0];
			//Personal docente2 = (Personal) inscripcion.getMesa().getIntegrantesTribunal().toArray()[1];
			//Personal docente3 = (Personal) inscripcion.getMesa().getIntegrantesTribunal().toArray()[2];
			//mesaExamen.setDniDocente1(docente1.getNroDocumento());
			//mesaExamen.setNombreDocente1(docente1.getNombre());
			//mesaExamen.setApellidoDocente1(docente1.getApellido());
			//mesaExamen.setDniDocente2(docente2.getNroDocumento());
			//mesaExamen.setNombreDocente2(docente2.getNombre());
			//mesaExamen.setApellidoDocente2(docente2.getApellido());
			//mesaExamen.setDniDocente3(docente3.getNroDocumento());
			//mesaExamen.setNombreDocente3(docente3.getNombre());
			//mesaExamen.setApellidoDocente3(docente3.getApellido());
			mesaExamen.setDniAlumno(inscripcion.getAlumno().getNroDocumento());
			mesaExamen.setNombreAlumno(inscripcion.getAlumno().getNombre());
			mesaExamen.setApellidoAlumno(inscripcion.getAlumno().getApellido());
			//mesaExamen.setFechaHoraInicioMesa(inscripcion.getMesa().getFechaHoraInicio());
			//mesaExamen.setFechaHoraFinMesa(inscripcion.getMesa().getFechaHoraFin());
			
			gMesaExamenHist.add(mesaExamen);
		} catch (Exception ex) {
			throw new Exception("No se pudo agregar la MESA-EXAMEN: " + ex.getMessage());
		}
		
		return true;
	}
	
	/*
	public Boolean asignarNotaAMesaExamen(Nota nota, Long idMesaExamen) throws Exception {
		try {
			/**
			 * 1) obtener mesa-examen
			 * 2) asignar nota a mesa-examen
			 * 3) asignar materia a nota (mesa-examen->inscripcion->mesa->materia)
			 * 4) modify mesa-examen
			 * 5) modify nota
			 *//*
			MesaExamen mesaExamen = gMesaExamenHist.getById(idMesaExamen);
			mesaExamen.setNota(nota);
			nota.setMateria(mesaExamen.getInscripcion().getMesa().getMateria());
			gMesaExamenHist.modify(mesaExamen);
			gNota.modify(nota);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	*/

}
