package ar.com.santalucia.dominio.dto;

import java.util.Date;

public class MesaAltaDTO {
	private Long idLlamado;
	private Long idMesa;
	private Date fechaHoraInicio;
	private Date fechaHoraFin;
	private Long idMateria;
	private Long tribunalDoc1;
	private Long tribunalDoc2;
	private Long tribunalDoc3;
	
	public Long getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(Long idMesa) {
		this.idMesa = idMesa;
	}


	
	public MesaAltaDTO() {
		super();
	}

	public MesaAltaDTO(Long idLlamado, Long idMesa, Date fechaHoraInicio, Date fechaHoraFin, Long idMateria, Long tribunalDoc1,
			Long tribunalDoc2, Long tribunalDoc3) {
		super();
		this.idLlamado = idLlamado;
		this.idMesa = idMesa;
		this.fechaHoraInicio = fechaHoraInicio;
		this.fechaHoraFin = fechaHoraFin;
		this.idMateria = idMateria;
		this.tribunalDoc1 = tribunalDoc1;
		this.tribunalDoc2 = tribunalDoc2;
		this.tribunalDoc3 = tribunalDoc3;
	}

	public Long getIdLlamado() {
		return idLlamado;
	}

	public void setIdLlamado(Long idLlamado) {
		this.idLlamado = idLlamado;
	}

	public Date getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public void setFechaHoraInicio(Date fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}

	public Date getFechaHoraFin() {
		return fechaHoraFin;
	}

	public void setFechaHoraFin(Date fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}

	public Long getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(Long idMateria) {
		this.idMateria = idMateria;
	}

	public Long getTribunalDoc1() {
		return tribunalDoc1;
	}

	public void setTribunalDoc1(Long tribunalDoc1) {
		this.tribunalDoc1 = tribunalDoc1;
	}

	public Long getTribunalDoc2() {
		return tribunalDoc2;
	}

	public void setTribunalDoc2(Long tribunalDoc2) {
		this.tribunalDoc2 = tribunalDoc2;
	}

	public Long getTribunalDoc3() {
		return tribunalDoc3;
	}

	public void setTribunalDoc3(Long tribunalDoc3) {
		this.tribunalDoc3 = tribunalDoc3;
	}
	
}
