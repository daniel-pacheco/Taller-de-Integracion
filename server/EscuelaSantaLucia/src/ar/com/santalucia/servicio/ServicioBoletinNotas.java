package ar.com.santalucia.servicio;

import java.util.List;
import java.util.Set;

import ar.com.santalucia.aplicacion.gestor.desempenio.GestorBoletinNotas;
import ar.com.santalucia.aplicacion.gestor.desempenio.GestorBoletinNotasHist;
import ar.com.santalucia.dominio.modelo.academico.Inscripcion;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotas;
import ar.com.santalucia.dominio.modelo.desempenio.BoletinNotasHist;
import ar.com.santalucia.dominio.modelo.desempenio.MateriaNotasBoletin;
import ar.com.santalucia.dominio.modelo.desempenio.Nota;
import ar.com.santalucia.dominio.modelo.desempenio.Trimestre;

/**
 * Clase ServicioBoletinNotas: manejo del boletin de notas del alumno, además maneja el boletín histórico
 * 
 * @author Eric
 * @version 1.0
 *
 */

public class ServicioBoletinNotas {
	
	private GestorBoletinNotas gBoletin;
	private GestorBoletinNotasHist gBoletinHist;
	
	public ServicioBoletinNotas() throws Exception {
		try {
			gBoletin = new GestorBoletinNotas();
			gBoletinHist = new GestorBoletinNotasHist();
		} catch (Exception ex) {
			throw new Exception("Ha ocurrido un problema al inicializar el servicio de operaciones básicas: "
					+ ex.getMessage());
		}
	}
	
	public Boolean addBoletinNotas(BoletinNotas boletinNotas) throws Exception{
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
	
	public Boolean deleteBoletinNotas(BoletinNotas boletinNotas) throws Exception {
		try {
			gBoletin.delete(boletinNotas);
		} catch (Exception ex) {
			throw new Exception("No se pudo eliminar el BOLETÍN DE NOTAS: " + ex.getMessage());
		}
		return true;
	}
	
	public BoletinNotas getBoletin(Long idBoletin) throws Exception {
		try {
			return gBoletin.getById(idBoletin);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener el BOLETÍN DE NOTAS: " + ex.getMessage());
		}
	}
	
	public List<BoletinNotas> getBoletines(BoletinNotas example) throws Exception {
		try {
			return gBoletin.getByExample(example);
		} catch (Exception ex) {
			throw new Exception("No se pudo obtener la lista de BOLETINES DE NOTAS: " + ex.getMessage());
		}
	}
	
	public Boolean pasarAHistorico(BoletinNotas boletinNotas) throws Exception {
		try {
			BoletinNotasHist boletinHistorico = new BoletinNotasHist();
			// pasar a histórico
			boletinHistorico.setIdBoletin(boletinNotas.getIdBoletinNotas());
			boletinHistorico.setDniAlumno(boletinNotas.getPropietario().getNroDocumento());
			boletinHistorico.setNombreAlumno(boletinNotas.getPropietario().getNombre());
			boletinHistorico.setApellidoAlumno(boletinNotas.getPropietario().getApellido());
			boletinHistorico.setAnio(null);
			boletinHistorico.setCurso(null);
			boletinHistorico.setCicloLectivo(boletinNotas.getCicloLectivo());
			// materias y notas
			Set<Trimestre> trimestres = boletinNotas.getListaTrimestres();
			Set<Nota> notasExtras = boletinNotas.getListaNotasExamen();
			MateriaNotasBoletin materiasNotasBoletin = new MateriaNotasBoletin();
			for (Trimestre t : trimestres) {
				materiasNotasBoletin.setMateria(t.getMateria().getNombre());
				materiasNotasBoletin.setNotaTrimestre1(t.getNotaFinal().getCalificacion().intValue());
			}
		} catch (Exception ex) {
			throw new Exception ("No se pudo pasar a histórico el BOLETÍN DE NOTAS: " + ex.getMessage());
		}
		return true;
	}
	
}
