package ar.com.santalucia.excepciones;

import java.util.ArrayList;

import ar.com.santalucia.dominio.modelo.desempenio.Inasistencia;

public class InasistenciaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Inasistencia> inasistenciasInvalidas;

	public InasistenciaException() {
		super();
		inasistenciasInvalidas = new ArrayList<Inasistencia>();
	}

	public ArrayList<Inasistencia> getInasistenciasInvalidas() {
		return inasistenciasInvalidas;
	}

	public void setInasistenciasInvalidas(ArrayList<Inasistencia> inasistenciasInvalidas) {
		this.inasistenciasInvalidas = inasistenciasInvalidas;
	}

	public void addInasistencia(Inasistencia inasistencia) {
		inasistenciasInvalidas.add(inasistencia);
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return "Inasistencias repetidas ";
	}

}
