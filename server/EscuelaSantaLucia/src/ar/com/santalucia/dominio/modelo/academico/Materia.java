package ar.com.santalucia.dominio.modelo.academico;

import java.util.ArrayList;
import java.util.List;

import ar.com.santalucia.dominio.modelo.Entidad;
import ar.com.santalucia.dominio.modelo.desempeño.Trimestre;
import ar.com.santalucia.dominio.modelo.usuarios.Docente;

public class Materia extends Entidad {

	private String nombre;
	private Docente docenteTitular;
	private List<Trimestre> listaTrimestres;

	public Materia() {
		super();
		listaTrimestres = new ArrayList<Trimestre>();
	}

	public Materia(String nombre, Docente docenteTitular,
			List<Trimestre> listaTrimestres) {
		super();
		this.nombre = nombre;
		this.docenteTitular = docenteTitular;
		this.listaTrimestres = listaTrimestres;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Docente getDocenteTitular() {
		return docenteTitular;
	}

	public void setDocenteTitular(Docente docenteTitular) {
		this.docenteTitular = docenteTitular;
	}

	public List<Trimestre> getListaTrimestres() {
		return listaTrimestres;
	}

	public void setListaTrimestres(List<Trimestre> listaTrimestres) {
		this.listaTrimestres = listaTrimestres;
	}

	public void agregarTrimestre(Trimestre trimestre) {
		this.listaTrimestres.add(trimestre);
	}
	public void quitarTrimestre(Trimestre trimestre) {
		this.listaTrimestres.remove(trimestre);
	}
}
