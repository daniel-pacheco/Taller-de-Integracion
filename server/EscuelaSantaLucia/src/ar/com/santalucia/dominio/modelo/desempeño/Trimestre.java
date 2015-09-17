package ar.com.santalucia.dominio.modelo.desempeño;

import java.util.ArrayList;
import java.util.List;

import ar.com.santalucia.dominio.modelo.Entidad;
import ar.com.santalucia.dominio.modelo.academico.Materia;

public class Trimestre extends Entidad {

	private int cicloLectivo;
	private List<Nota> listaNotas;
	private Materia materia;

	public Trimestre() {
		super();
		listaNotas = new ArrayList<Nota>();
	}

	public Trimestre(int cicloLectivo, List<Nota> listaNotas, Materia materia) {
		super();
		this.cicloLectivo = cicloLectivo;
		this.listaNotas = listaNotas;
		this.materia = materia;
	}

	public int getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(int cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

	public List<Nota> getListaNotas() {
		return listaNotas;
	}

	public void setListaNotas(List<Nota> listaNotas) {
		this.listaNotas = listaNotas;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public void agregarNota(Nota nota) {
		this.listaNotas.add(nota);
	}

	public void quitarNota(Nota nota) {
		this.listaNotas.remove(nota);
	}

}
