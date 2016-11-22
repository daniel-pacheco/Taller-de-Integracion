package ar.com.santalucia.dominio.dto;

import java.util.Date;
import java.util.List;

/**
 * Este DTO representa la información mostrada en la pantala de calificacion de notas en mesa
 * @author Ariel
 *
 */
public class ActaVolanteExamenesDTO {
	private Long idActaVolanteExamen;
	private String nombreLlamado;
	private String nombreMesa;
	private Date fechaMesa;
	private Date horaInicio;
	private Date horaFin;
	private String datosTribunal1;
	private String datosTribunal2;
	private String datosTribunal3;
	private List<DetalleActaVolanteDTO> detalleActaVolante;
	
	public ActaVolanteExamenesDTO() {
		super();
	}

	public ActaVolanteExamenesDTO(Long idActaVolanteExamen, String nombreLlamado, String nombreMesa, Date fechaMesa,
			Date horaInicio, Date horaFin, String datosTribunal1, String datosTribunal2, String datosTribunal3, 
			List<DetalleActaVolanteDTO> detalleActaVolante) {
		super();
		this.idActaVolanteExamen = idActaVolanteExamen;
		this.nombreLlamado = nombreLlamado;
		this.nombreMesa = nombreMesa;
		this.fechaMesa = fechaMesa;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.datosTribunal1 = datosTribunal1;
		this.datosTribunal2 = datosTribunal2;
		this.datosTribunal3 = datosTribunal3;
		this.setDetalleActaVolante(detalleActaVolante);
	}

	public Long getIdActaVolanteExamen() {
		return idActaVolanteExamen;
	}

	public void setIdActaVolanteExamen(Long idActaVolanteExamen) {
		this.idActaVolanteExamen = idActaVolanteExamen;
	}

	public String getNombreLlamado() {
		return nombreLlamado;
	}

	public void setNombreLlamado(String nombreLlamado) {
		this.nombreLlamado = nombreLlamado;
	}

	public String getNombreMesa() {
		return nombreMesa;
	}

	public void setNombreMesa(String nombreMesa) {
		this.nombreMesa = nombreMesa;
	}

	public Date getFechaMesa() {
		return fechaMesa;
	}

	public void setFechaMesa(Date fechaMesa) {
		this.fechaMesa = fechaMesa;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	public String getDatosTribunal1() {
		return datosTribunal1;
	}

	public void setDatosTribunal1(String datosTribunal1) {
		this.datosTribunal1 = datosTribunal1;
	}

	public String getDatosTribunal2() {
		return datosTribunal2;
	}

	public void setDatosTribunal2(String datosTribunal2) {
		this.datosTribunal2 = datosTribunal2;
	}

	public String getDatosTribunal3() {
		return datosTribunal3;
	}

	public void setDatosTribunal3(String datosTribunal3) {
		this.datosTribunal3 = datosTribunal3;
	}

	public List<DetalleActaVolanteDTO> getDetalleActaVolante() {
		return detalleActaVolante;
	}

	public void setDetalleActaVolante(List<DetalleActaVolanteDTO> detalleActaVolante) {
		this.detalleActaVolante = detalleActaVolante;
	}
	
}
