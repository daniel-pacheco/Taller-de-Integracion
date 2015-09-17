package ar.com.santalucia.dominio.modelo.academico;

import java.util.Date;

import ar.com.santalucia.dominio.modelo.Entidad;
import ar.com.santalucia.dominio.modelo.desempeño.Nota;
import ar.com.santalucia.dominio.modelo.desempeño.Trimestre;

public class MesaExamen extends Entidad {

	private String nroActa;

	public MesaExamen() {
		super();
	}

	public MesaExamen(String nroActa) {
		super();
		this.nroActa = nroActa;
	}

	public String getNroActa() {
		return nroActa;
	}

	public void setNroActa(String nroActa) {
		this.nroActa = nroActa;
	}
	
	/**
	 * Metodo que setea la nota de un examen en una Mesa. Instancia una clase Nota y le setea la calificacion
	 * con setCalificacion(...), junto con los otros atributos de Nota
	 * @param nota
	 * @return Un objeto Nota que contiene la calificacion del examen
	 */
	public Nota calificar(Date fecha, float nota, Materia materia, String tipo, Trimestre trimestre) {
		Nota notaExamen = new Nota();
		notaExamen.setFecha(fecha);
		notaExamen.setCalificacion(nota);
		notaExamen.setMateria(materia);
		notaExamen.setTipo(tipo);
		notaExamen.setTrimestre(trimestre);
		return notaExamen;
	}
	
}
