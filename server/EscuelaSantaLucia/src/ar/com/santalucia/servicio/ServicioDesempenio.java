package ar.com.santalucia.servicio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.academico.GestorAnio;
import ar.com.santalucia.aplicacion.gestor.academico.GestorMateria;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorBoletinNotas;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorBoletinNotasHist;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorNota;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorTrimestre;
import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.dominio.modelo.academico.Anio;
import ar.com.santalucia.dominio.modelo.academico.Inscripcion;
import ar.com.santalucia.dominio.modelo.academico.Materia;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotas;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotasHist;
import ar.com.santalucia.dominio.modelo.desempenio.MateriaNotasBoletin;
import ar.com.santalucia.dominio.modelo.desempenio.Nota;
import ar.com.santalucia.dominio.modelo.desempenio.Trimestre;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

/**
 * Clase ServicioDesempenio: manejo del boletin de notas del alumno, además maneja el boletín histórico
 * 
 * @author Eric
 * @version 1.0
 *
 */

public class ServicioDesempenio {
	
	private GestorNota gNota;
	private GestorMateria gMateria; // ¿?
	private GestorTrimestre gTrimestre;
	private GestorBoletinNotas gBoletin;
	private GestorBoletinNotasHist gBoletinHist;
	private GestorAlumno gAlumno; // ¿?
	private GestorAnio gAnio;
	
	public ServicioDesempenio() throws Exception {
		try {
			gNota = new GestorNota();
			//gMateria = new GestorMateria();
			gTrimestre = new GestorTrimestre();
			gBoletin = new GestorBoletinNotas();
			gBoletinHist = new GestorBoletinNotasHist();
			//gAlumno = new GestorAlumno();
			gAnio = new GestorAnio();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar el servicio de operaciones básicas: "
					+ ex.getMessage());
		}
	}
	
	public Boolean addBoletinNotas(BoletinNotas boletinNotas) throws Exception { // EN ENDPOINT
		try {
			if (boletinNotas.getIdBoletinNotas() != null) {
				gBoletin.add(boletinNotas);
			} else {
				gBoletin.modify(boletinNotas);
			}
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta el BOLETÍN DE NOTAS: " + ex.getMessage());
		}
		return true;
	}
	
	public Boolean deleteBoletinNotas(BoletinNotas boletinNotas) throws Exception { // EN ENDPOINT
		try {
			gBoletin.delete(boletinNotas);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar el BOLETÍN DE NOTAS: " + ex.getMessage());
		}
		return true;
	}
	
	public BoletinNotas getBoletin(Long idBoletin) throws Exception { // EN ENDPOINT
		try {
			return gBoletin.getById(idBoletin);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener el BOLETÍN DE NOTAS: " + ex.getMessage());
		}
	}
	
	public List<BoletinNotas> getBoletines(BoletinNotas example) throws Exception { // EN ENDPOINT
		try {
			return gBoletin.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista de BOLETINES DE NOTAS: " + ex.getMessage());
		}
	}
	
	public Boolean pasarAHistorico(BoletinNotas boletinNotas) throws Exception { // EN ENDPOINT
		try {
			BoletinNotasHist boletinHistorico = new BoletinNotasHist();
			// pasar a histórico
			boletinHistorico.setIdBoletin(boletinNotas.getIdBoletinNotas());
			boletinHistorico.setDniAlumno(boletinNotas.getPropietario().getNroDocumento());
			boletinHistorico.setNombreAlumno(boletinNotas.getPropietario().getNombre());
			boletinHistorico.setApellidoAlumno(boletinNotas.getPropietario().getApellido());
			boletinHistorico.setAnio(boletinNotas.getAnio());
			boletinHistorico.setCurso(null);
			boletinHistorico.setCicloLectivo(boletinNotas.getCicloLectivo());
			
			Set<Trimestre> trimestres = boletinNotas.getListaTrimestres();
			Set<Nota> notasExtras = boletinNotas.getListaNotasExamen();
			Set<MateriaNotasBoletin> listaMateriasNotasBoletin = new HashSet<MateriaNotasBoletin>();
			
			Anio anioBuscar = new Anio();
			anioBuscar.setNombre(boletinNotas.getAnio());
			ArrayList<Anio> listaAnios = gAnio.getByExample(anioBuscar);
			Anio anioEnc = new Anio();
			anioEnc = listaAnios.get(0);
			for (Materia m : anioEnc.getListaMaterias()) {
				MateriaNotasBoletin materiaNotasBoletin = new MateriaNotasBoletin();
				materiaNotasBoletin.setMateria(m.getNombre());
				for (Trimestre t : trimestres) {
					materiaNotasBoletin.setNotaTrimestre1((
						t.getOrden()==1 && t.getMateria().getNombre()==m.getNombre()
							? t.getNotaFinal().getCalificacion().intValue()
							:null));
					materiaNotasBoletin.setNotaTrimestre2((
						t.getOrden()==2 && t.getMateria().getNombre()==m.getNombre()
							? t.getNotaFinal().getCalificacion().intValue()
							:null));
					materiaNotasBoletin.setNotaTrimestre3((
						t.getOrden()==3 && t.getMateria().getNombre()==m.getNombre()
							? t.getNotaFinal().getCalificacion().intValue()
							:null));
				}
				for (Nota ne : notasExtras) {
					materiaNotasBoletin.setNotaDiciembre(
						ne.getTipo()==Nota.DICIEMBRE && ne.getMateria().getNombre()==m.getNombre()
						? ne.getCalificacion().intValue()
						: null);
					materiaNotasBoletin.setNotaDiciembre(
						ne.getTipo()==Nota.MARZO && ne.getMateria().getNombre()==m.getNombre()
						? ne.getCalificacion().intValue()
						: null);
				}
				listaMateriasNotasBoletin.add(materiaNotasBoletin);
			}
			
			boletinHistorico.setListaMateriasNotasBoletin(listaMateriasNotasBoletin);
			gBoletinHist.add(boletinHistorico);
			
		} catch (Exception ex) {
			throw new Exception ("No se pudo pasar a histórico el BOLETÍN DE NOTAS: " + ex.getMessage());
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
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta la NOTA: " + ex.getMessage());
		}
		return false;
	}
		
	public Boolean deleteNota(Nota nota) throws Exception {
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
			throw new Exception("No se pudo obtener la lista de NOTAS: " + ex.getMessage());
		}
	}
	
	
	public Boolean addTrimestre(Trimestre trimestre) throws Exception {
		try {
			if (trimestre.getIdTrimestre() == null) {
				gTrimestre.add(trimestre);
			} else {
				gTrimestre.modify(trimestre);
			}
		} catch (Exception ex) {
			throw new Exception("No se pudo dar de alta el TRIMESTRE: " + ex.getMessage());
		}
		return false;
	}
		
	public Boolean deleteTrimestre(Trimestre trimestre) throws Exception {
		try {
			gTrimestre.delete(trimestre);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar el TRIMESTRE: " + ex.getMessage());
		}
		return true;
	}
	
	public Trimestre getTrimestre(Long idTrimestre) throws Exception {
		try {
			return gTrimestre.getById(idTrimestre);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener el TRIMESTRE: " + ex.getMessage());
		}
	}
	
	public List<Trimestre> getTrimestres(Trimestre example) throws Exception {
		try {
			return gTrimestre.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista de TRIMESTRES: " + ex.getMessage());
		}
	}

	
	public Boolean asignarMateriaATrimestre(Materia materia, Long idTrimestre) throws Exception {
		try {
			Trimestre trimestre = gTrimestre.getById(idTrimestre);
			trimestre.setMateria(materia);
			gTrimestre.modify(trimestre);
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar la MATERIA al TRIMESTRE: " + ex.getMessage());
		}
		return true;
	}
	
	public Boolean asignarNotaATrimestre(Nota nota, Long idTrimestre) throws Exception { // EN ENDPOINT
		try {
			Trimestre trimestre = gTrimestre.getById(idTrimestre);
			nota.setMateria(trimestre.getMateria()); // asigno la materia a la nota tomando del trimestre.
			gNota.modify(nota);
			if (nota.getTipo().equals(Nota.NOTA_FINAL_TRIMESTRAL)) {
				trimestre.setNotaFinal(nota);
			} else {
				Set<Nota> listaNotas = trimestre.getListaNotas();
				listaNotas.add(nota);
				trimestre.setListaNotas(listaNotas);
			}
			gTrimestre.modify(trimestre);
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar la NOTA al TRIMESTRE: " + ex.getMessage());
		}
		return true;
	}
	
	public Boolean asignarTrimestreABoletin(Trimestre trimestre, Long idBoletin) throws Exception {
		try {
			BoletinNotas boletinNotas = gBoletin.getById(idBoletin);
			Set<Trimestre> listaTrimestres = boletinNotas.getListaTrimestres();
			listaTrimestres.add(trimestre);
			boletinNotas.setListaTrimestres(listaTrimestres);
			gBoletin.modify(boletinNotas);
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar el TRIMESTRE al BOLETÍN DE NOTAS: " + ex.getMessage());
		}
		return true;
	}
	
	public Boolean asignarAlumnoABoletin(Alumno alumno, Long idBoletin) throws Exception {
		try {
			BoletinNotas boletinNotas = gBoletin.getById(idBoletin);
			boletinNotas.setPropietario(alumno);
			gBoletin.modify(boletinNotas);
		} catch (Exception ex) {
			throw new Exception("No se pudo asignar el ALUMNO al BOLETÍN DE NOTAS: " + ex.getMessage());
		}
		return true;
	}

	

}
