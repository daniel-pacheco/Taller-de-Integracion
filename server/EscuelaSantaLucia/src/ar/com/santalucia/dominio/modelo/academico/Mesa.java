package ar.com.santalucia.dominio.modelo.academico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.santalucia.dominio.modelo.Entidad;
import ar.com.santalucia.dominio.modelo.usuarios.Docente;

/**
 * Clase Mesa (mesas de examen)
 * @author EricDaniel
 *
 */
public class Mesa extends Entidad {

	private Date fecha;
	private int diasPermitirInscripcion;
	private Materia materia;
	private List<Docente> listaIntegrantesTribunal;
	private List<Inscripcion> listaInscripciones;

	public Mesa() {
		super();
		listaIntegrantesTribunal = new ArrayList<Docente>();
		listaInscripciones = new ArrayList<Inscripcion>();
	}

	public Mesa(Date fecha, int diasPermitirInscripcion, Materia materia,
			List<Docente> listaIntegrantesTribunal,
			List<Inscripcion> listaInscripciones) {
		super();
		this.fecha = fecha;
		this.diasPermitirInscripcion = diasPermitirInscripcion;
		this.materia = materia;
		this.listaIntegrantesTribunal = listaIntegrantesTribunal;
		this.listaInscripciones = listaInscripciones;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getDiasPermitirInscripcion() {
		return diasPermitirInscripcion;
	}

	public void setDiasPermitirInscripcion(int diasPermitirInscripcion) {
		this.diasPermitirInscripcion = diasPermitirInscripcion;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public List<Docente> getListaIntegrantesTribunal() {
		return listaIntegrantesTribunal;
	}

	public void setListaIntegrantesTribunal(
			List<Docente> listaIntegrantesTribunal) {
		this.listaIntegrantesTribunal = listaIntegrantesTribunal;
	}

	public List<Inscripcion> getListaInscripciones() {
		return listaInscripciones;
	}

	public void setListaInscripciones(List<Inscripcion> listaInscripciones) {
		this.listaInscripciones = listaInscripciones;
	}

	public void agregarDocenteATribunal(Docente integranteTribunal) {
		this.listaIntegrantesTribunal.add(integranteTribunal);
	}
	public void quitarDocenteDeTribunal(Docente integranteTribunal) {
		this.listaIntegrantesTribunal.remove(integranteTribunal);
	}
	public void agregarInscripcion(Inscripcion inscripcion) {
		this.listaInscripciones.add(inscripcion);
	}
	public void quitarInscripcion(Inscripcion inscripcion) {
		this.listaInscripciones.remove(inscripcion);
	}
}
