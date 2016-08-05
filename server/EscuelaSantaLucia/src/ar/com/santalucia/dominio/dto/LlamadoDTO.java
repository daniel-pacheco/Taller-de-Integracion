package ar.com.santalucia.dominio.dto;

import java.util.Date;
import java.util.List;

public class LlamadoDTO {
	private Long idLlamado;
	private String descripcion;
	private Boolean vigente;
	private Date fechaInicio;
	private Date fechaFin;
	private List<MesaDTO>listaMesas;
	
	public LlamadoDTO() {
		super();
	}

	public LlamadoDTO(Long idLlamado, String descripcion, Boolean vigente, Date fechaInicio, Date fechaFin,
			List<MesaDTO> listaMesas) {
		super();
		this.idLlamado = idLlamado;
		this.descripcion = descripcion;
		this.vigente = vigente;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.listaMesas = listaMesas;
	}

	public Long getIdLlamado() {
		return idLlamado;
	}

	public void setIdLlamado(Long idLlamado) {
		this.idLlamado = idLlamado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getVigente() {
		return vigente;
	}

	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<MesaDTO> getListaMesas() {
		return listaMesas;
	}

	public void setListaMesas(List<MesaDTO> listaMesas) {
		this.listaMesas = listaMesas;
	}
}
