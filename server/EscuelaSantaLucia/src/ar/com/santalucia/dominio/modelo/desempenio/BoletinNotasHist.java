package ar.com.santalucia.dominio.modelo.desempenio;

import java.util.Set;

/**
 * Clase BoletinNotasHist: guarda información histórica de los boletines de
 * notas, con el año, curso, materias, notas, ...
 * 
 * @author Eric
 * @version 1.1
 *
 */

public class BoletinNotasHist {
	private Long idBoletinNotasHist;
	private Long idBoletin;
	private Long dniAlumno;
	private String nombreAlumno;
	private String apellidoAlumno;
	private Set<MateriaNotasBoletin> listaMateriasNotasBoletin;
	private String anio;
	private String curso;
	private Long cicloLectivo;

	public BoletinNotasHist() {
		super();
	}

	public BoletinNotasHist(Long idBoletinNotasHist, Long idBoletin, Long dniAlumno, String nombreAlumno,
			String apellidoAlumno, Set<MateriaNotasBoletin> listaMateriasNotasBoletin, String anio, String curso,
			Long cicloLectivo) {
		super();
		this.idBoletinNotasHist = idBoletinNotasHist;
		this.idBoletin = idBoletin;
		this.dniAlumno = dniAlumno;
		this.nombreAlumno = nombreAlumno;
		this.apellidoAlumno = apellidoAlumno;
		this.listaMateriasNotasBoletin = listaMateriasNotasBoletin;
		this.anio = anio;
		this.curso = curso;
		this.cicloLectivo = cicloLectivo;
	}

	public Long getIdBoletinNotasHist() {
		return idBoletinNotasHist;
	}

	public void setIdBoletinNotasHist(Long idBoletinNotasHist) {
		this.idBoletinNotasHist = idBoletinNotasHist;
	}

	public Long getIdBoletin() {
		return idBoletin;
	}

	public void setIdBoletin(Long idBoletin) {
		this.idBoletin = idBoletin;
	}

	public Long getDniAlumno() {
		return dniAlumno;
	}

	public void setDniAlumno(Long dniAlumno) {
		this.dniAlumno = dniAlumno;
	}

	public String getNombreAlumno() {
		return nombreAlumno;
	}

	public void setNombreAlumno(String nombreAlumno) {
		this.nombreAlumno = nombreAlumno;
	}

	public String getApellidoAlumno() {
		return apellidoAlumno;
	}

	public void setApellidoAlumno(String apellidoAlumno) {
		this.apellidoAlumno = apellidoAlumno;
	}

	public Set<MateriaNotasBoletin> getListaMateriasNotasBoletin() {
		return listaMateriasNotasBoletin;
	}

	public void setListaMateriasNotasBoletin(Set<MateriaNotasBoletin> listaMateriasNotasBoletin) {
		this.listaMateriasNotasBoletin = listaMateriasNotasBoletin;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public Long getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(Long cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

}
