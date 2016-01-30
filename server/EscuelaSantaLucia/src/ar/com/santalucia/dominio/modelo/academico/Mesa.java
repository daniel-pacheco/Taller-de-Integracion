package ar.com.santalucia.dominio.modelo.academico;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ar.com.santalucia.dominio.modelo.usuarios.Personal;

/**
 * 
 * @author Ariel Ramirez
 * @version 1.1
 * 
 */

// Último modificador: Eric Pennachini @ 30-01-16 13:00

public class Mesa {
	private Long idMesa;
	private Date fecha;
	private Long plazoInscripcion;
	private Materia materia;
	private Set<Personal> integrantesTribunal;

	public Mesa() {
		super();
		integrantesTribunal = new HashSet<Personal>();
	}

	public Mesa(Long idMesa, Date fecha, Long plazoInscripcion, Materia materia, Set<Personal> integrantesTribunal,
			Set<Inscripcion> listaInscripciones) {
		super();
		this.idMesa = idMesa;
		this.fecha = fecha;
		this.plazoInscripcion = plazoInscripcion;
		this.materia = materia;
		this.setIntegrantesTribunal(integrantesTribunal);
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

	public Set<Personal> getIntegrantesTribunal() {
		return integrantesTribunal;
	}

	public void setIntegrantesTribunal(Set<Personal> integrantesTribunal) {
		this.integrantesTribunal = integrantesTribunal;
	}
	
}
