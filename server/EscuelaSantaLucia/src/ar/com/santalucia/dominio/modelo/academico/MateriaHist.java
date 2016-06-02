package ar.com.santalucia.dominio.modelo.academico;

/**
 * Clase MateriaHist: guarda información histórica de materias
 * 
 * @author Eric
 * @version 1.1
 *
 */

// Último modificador: Ariel Ramírez @ 30-04-2016 11:02

public class MateriaHist {
	private Long idMateriaHist;
	private String nombreMateria;
	private String descripcionMateria;
	private String nombreAnio;
	private String cicloLectivo;

	public MateriaHist() {
		super();
	}

	public MateriaHist(Long idMateriaHist, String nombreMateria, String descripcionMateria) {
		super();
		this.idMateriaHist = idMateriaHist;
		this.nombreMateria = nombreMateria;
		this.descripcionMateria = descripcionMateria;
	}

	public Long getIdMateriaHist() {
		return idMateriaHist;
	}

	public void setIdMateriaHist(Long idMateriaHist) {
		this.idMateriaHist = idMateriaHist;
	}

	public String getNombreMateria() {
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	public String getDescripcionMateria() {
		return descripcionMateria;
	}

	public void setDescripcionMateria(String descripcionMateria) {
		this.descripcionMateria = descripcionMateria;
	}

	public String getNombreAnio() {
		return nombreAnio;
	}

	public void setNombreAnio(String nombreAnio) {
		this.nombreAnio = nombreAnio;
	}

	public String getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(String cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

}
