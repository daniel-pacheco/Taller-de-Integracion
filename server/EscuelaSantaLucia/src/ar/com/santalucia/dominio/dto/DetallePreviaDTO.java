package ar.com.santalucia.dominio.dto;
/**
 * DetallePreviaDTO proporciona un objeto simplificado para devolver al frontend
 * información resumida de materias previas.
 * 
 * @author Ariel
 * @version 1.0
 */
public class DetallePreviaDTO {
	private Long idDetallePrevia;
	private String nombreMateria;
	private Long dniAlumno;
	private Integer cicloLectivoMateria;
	private String anio;
	private Float nota;
	
	public DetallePreviaDTO() {
		super();
	}

	public DetallePreviaDTO(Long idDetallePrevia, String nombreMateria, Integer cicloLectivoMateria, String anio) {
		super();
		this.idDetallePrevia = idDetallePrevia;
		this.nombreMateria = nombreMateria;
		this.cicloLectivoMateria = cicloLectivoMateria;
		this.anio = anio;
	}

	public Long getIdDetallePrevia() {
		return idDetallePrevia;
	}

	public void setIdDetallePrevia(Long idDetallePrevia) {
		this.idDetallePrevia = idDetallePrevia;
	}

	public String getNombreMateria() {
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	public Long getDniAlumno() {
		return dniAlumno;
	}

	public void setDniAlumno(Long dniAlumno) {
		this.dniAlumno = dniAlumno;
	}

	public Integer getCicloLectivoMateria() {
		return cicloLectivoMateria;
	}

	public void setCicloLectivoMateria(Integer cicloLectivoMateria) {
		this.cicloLectivoMateria = cicloLectivoMateria;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public Float getNota() {
		return nota;
	}

	public void setNota(Float nota) {
		this.nota = nota;
	}
	
}
