package ar.com.santalucia.dominio.modelo.desempeño;

import java.util.ArrayList;
import java.util.List;

import ar.com.santalucia.dominio.modelo.Entidad;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

public class BoletinInasistencia extends Entidad {

	private Alumno propietario;
	private float total;
	private List<Inasistencia> listaInasistencia;
	private int cicloLectivo;

	public BoletinInasistencia() {
		super();
		listaInasistencia = new ArrayList<Inasistencia>();
	}

	public BoletinInasistencia(Alumno propietario, float total,
			List<Inasistencia> listaInasistencia, int cicloLectivo) {
		super();
		this.propietario = propietario;
		this.total = total;
		this.listaInasistencia = listaInasistencia;
		this.cicloLectivo = cicloLectivo;
	}

	public Alumno getPropietario() {
		return propietario;
	}

	public void setPropietario(Alumno propietario) {
		this.propietario = propietario;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public List<Inasistencia> getListaInasistencia() {
		return listaInasistencia;
	}

	public void setListaInasistencia(List<Inasistencia> listaInasistencia) {
		this.listaInasistencia = listaInasistencia;
	}

	public int getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(int cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

	public void agregarInasistencia(Inasistencia inasistencia) {
		this.listaInasistencia.add(inasistencia);
	}

	public void quitarInasistencia(Inasistencia inasistencia) {
		this.listaInasistencia.remove(inasistencia);
	}

}
