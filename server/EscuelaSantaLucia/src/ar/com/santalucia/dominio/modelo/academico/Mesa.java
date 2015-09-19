package ar.com.santalucia.dominio.modelo.academico;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import ar.com.santalucia.dominio.modelo.usuarios.Docente;

/**
 * 
 * @author Ariel Ramirez
 * @version 1.0
 * 
 */

// Último modificador: Eric Pennachini @ 19-09-15 16:00

public class Mesa {
	private Long idMesa;
	private Date fecha;
	private Long plazoInscripcion;
	private Materia materia;
	private Set<Docente> integrantesTribunal;
	private Set<Inscripcion> listaInscripciones;

	public Mesa() {
		super();
		integrantesTribunal = new HashSet<Docente>();
		listaInscripciones = new HashSet<Inscripcion>();
	}

	public Mesa(Long idMesa, Date fecha, Long plazoInscripcion, Materia materia, Set<Docente> integrantesTribunal,
			Set<Inscripcion> listaInscripciones) {
		super();
		this.idMesa = idMesa;
		this.fecha = fecha;
		this.plazoInscripcion = plazoInscripcion;
		this.materia = materia;
		this.setIntegrantesTribunal(integrantesTribunal);
		this.setListaInscripciones(listaInscripciones);
	}

	public Long getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(Long idMesa) {
		this.idMesa = idMesa;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getPlazoInscripcion() {
		return plazoInscripcion;
	}

	public void setPlazoInscripcion(Long plazoInscripcion) {
		this.plazoInscripcion = plazoInscripcion;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Set<Docente> getIntegrantesTribunal() {
		return integrantesTribunal;
	}

	public void setIntegrantesTribunal(Set<Docente> integrantesTribunal) {
		this.integrantesTribunal = integrantesTribunal;
	}

	public Set<Inscripcion> getListaInscripciones() {
		return listaInscripciones;
	}

	public void setListaInscripciones(Set<Inscripcion> listaInscripciones) {
		this.listaInscripciones = listaInscripciones;
	}

}
