package ar.com.santalucia.dominio.modelo.academico;

import ar.com.santalucia.dominio.modelo.desempenio.Nota;

/**
 * 
 * @author Eric
 * @version 1.1
 *
 */

// Ultimo modificador: Eric Pennachini @ 19-09-15 17:20

public class MesaExamen {
	private Long idMesaExamen;
	private String nroActa;
	private Inscripcion inscripcion;
	private Nota nota; 

	public MesaExamen() {
		super();
	}

	public MesaExamen(Long idMesaExamen, String nroActa, Inscripcion inscripcion, Nota nota) {
		super();
		this.idMesaExamen = idMesaExamen;
		this.nroActa = nroActa;
		this.inscripcion = inscripcion;
		this.nota = nota;
	}

	public Long getIdMesaExamen() {
		return idMesaExamen;
	}

	public void setIdMesaExamen(Long idMesaExamen) {
		this.idMesaExamen = idMesaExamen;
	}

	public String getNroActa() {
		return nroActa;
	}

	public void setNroActa(String nroActa) {
		this.nroActa = nroActa;
	}

	public Inscripcion getInscripcion() {
		return inscripcion;
	}

	public void setInscripcion(Inscripcion inscripcion) {
		this.inscripcion = inscripcion;
	}

	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

}
