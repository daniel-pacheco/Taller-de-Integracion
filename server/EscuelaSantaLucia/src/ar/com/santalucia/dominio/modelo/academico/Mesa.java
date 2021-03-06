package ar.com.santalucia.dominio.modelo.academico;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ar.com.santalucia.dominio.modelo.usuarios.Personal;

/**
 * 
 * @author Ariel Ramirez
 * @version 2.0
 * 
 */

// �ltimo modificador: Eric Pennachini @ 03-02-16 11:42

public class Mesa {
	private Long idMesa;
	private Date fechaHoraInicio;
	private Date fechaHoraFin;
	private Materia materia;
	private Set<Personal> integrantesTribunal;

	public Mesa() {
		super();
		integrantesTribunal = new HashSet<Personal>();
	}

	public Mesa(Long idMesa, Date fechaHoraInicio, Date fechaHoraFin, Materia materia, Set<Personal> integrantesTribunal,
			Set<Inscripcion> listaInscripciones) {
		super();
		this.idMesa = idMesa;
		this.fechaHoraInicio = fechaHoraInicio;
		this.fechaHoraFin = fechaHoraFin;
		this.materia = materia;
		this.setIntegrantesTribunal(integrantesTribunal);
	}
	

	public Long getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(Long idMesa) {
		this.idMesa = idMesa;
	}

	public Date getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public void setFechaHoraInicio(Date fecha) {
		this.fechaHoraInicio = fecha;
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

	public Date getFechaHoraFin() {
		return fechaHoraFin;
	}

	public void setFechaHoraFin(Date fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}

	@Override
	public int hashCode() {
		Long valor = idMesa + Long.valueOf(fechaHoraInicio.getTime()) + Long.valueOf(fechaHoraFin.getTime());
		return valor.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Mesa){
			return this.idMesa == ((Mesa)obj).idMesa;
		}
		return false;
	}
	
	
	
}
