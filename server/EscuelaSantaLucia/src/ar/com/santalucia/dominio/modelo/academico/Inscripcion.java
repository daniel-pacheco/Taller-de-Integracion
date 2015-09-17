package ar.com.santalucia.dominio.modelo.academico;

import java.util.Date;

import ar.com.santalucia.dominio.modelo.Entidad;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

public class Inscripcion extends Entidad {

	private Date fecha;
	private Mesa mesa;
	private Alumno alumno;

	public Inscripcion() {
		super();
	}

	public Inscripcion(Date fecha, Mesa mesa, Alumno alumno) {
		super();
		this.fecha = fecha;
		this.mesa = mesa;
		this.alumno = alumno;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

}
