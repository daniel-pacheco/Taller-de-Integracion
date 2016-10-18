package ar.com.santalucia.dominio.modelo.academico;

import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

/**
 * Esta entidad representa el detalle de ActaVolanteExamen 
 * @author Ariel
 * @version 1.0
 */
public class DetalleVolante {
	private Long idDetalleVolante;
	private Alumno alumno;
	private Boolean asistencia;
	private Float nota;
	
	public DetalleVolante() {
		super();
	}

	public DetalleVolante(Long idDetalleVolante, Alumno alumno, Boolean asistencia, Float nota) {
		super();
		this.idDetalleVolante = idDetalleVolante;
		this.alumno = alumno;
		this.asistencia = asistencia;
		this.nota = nota;
	}

	public Long getIdDetalleVolante() {
		return idDetalleVolante;
	}

	public void setIdDetalleVolante(Long idDetalleVolante) {
		this.idDetalleVolante = idDetalleVolante;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Boolean getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(Boolean asistencia) {
		this.asistencia = asistencia;
	}

	public Float getNota() {
		return nota;
	}

	public void setNota(Float nota) {
		this.nota = nota;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDetalleVolante == null) ? 0 : idDetalleVolante.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetalleVolante other = (DetalleVolante) obj;
		if (idDetalleVolante == null) {
			if (other.idDetalleVolante != null)
				return false;
		} else if (!idDetalleVolante.equals(other.idDetalleVolante))
			return false;
		return true;
	}

}
