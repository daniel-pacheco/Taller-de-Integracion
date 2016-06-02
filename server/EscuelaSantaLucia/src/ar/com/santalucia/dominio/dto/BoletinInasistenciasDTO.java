package ar.com.santalucia.dominio.dto;

import java.util.ArrayList;

import ar.com.santalucia.dominio.modelo.desempenio.Inasistencia;

/**
 * BoletinInasistenciasDTO: contiene información sobre el boletin de
 * inasistencias con información resumida del alumno (nombre, apellido y DNI)
 * más la lista de inasistencias (InasistenciaDTO)
 * 
 * @author Eric
 * @version 1.0
 * 
 */
public class BoletinInasistenciasDTO {

	private Long idBoletinInasistencias;
	private String nombre;
	private String apellido;
	private Long nroDocumento;
	private String anio;
	private String curso;
	private Integer cicloLectivo;
	private ArrayList<Inasistencia> listaInasistencias;

	public BoletinInasistenciasDTO() {
		super();
	}

	public BoletinInasistenciasDTO(String nombre, String apellido, Long nroDocumento, String anio, String curso,
			Integer cicloLectivo, ArrayList<Inasistencia> listaInasistencias) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.nroDocumento = nroDocumento;
		this.anio = anio;
		this.curso = curso;
		this.cicloLectivo = cicloLectivo;
		this.listaInasistencias = listaInasistencias;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * @param apellido
	 *            the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @return the nroDocumento
	 */
	public Long getNroDocumento() {
		return nroDocumento;
	}

	/**
	 * @param nroDocumento
	 *            the nroDocumento to set
	 */
	public void setNroDocumento(Long nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	/**
	 * @return the anio
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * @param anio
	 *            the anio to set
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}

	/**
	 * @return the curso
	 */
	public String getCurso() {
		return curso;
	}

	/**
	 * @param curso
	 *            the curso to set
	 */
	public void setCurso(String curso) {
		this.curso = curso;
	}

	/**
	 * @return the cicloLectivo
	 */
	public Integer getCicloLectivo() {
		return cicloLectivo;
	}

	/**
	 * @param cicloLectivo
	 *            the cicloLectivo to set
	 */
	public void setCicloLectivo(Integer cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

	/**
	 * @return the listaInasistenciasDTO
	 */
	public ArrayList<Inasistencia> getListaInasistencias() {
		return listaInasistencias;
	}

	/**
	 * @param listaInasistencias
	 *            the listaInasistenciasDTO to set
	 */
	public void setListaInasistencias(ArrayList<Inasistencia> listaInasistencias) {
		this.listaInasistencias = listaInasistencias;
	}

	public Long getIdBoletinInasistencias() {
		return idBoletinInasistencias;
	}

	public void setIdBoletinInasistencias(Long idBoletinInasistencias) {
		this.idBoletinInasistencias = idBoletinInasistencias;
	}

}
