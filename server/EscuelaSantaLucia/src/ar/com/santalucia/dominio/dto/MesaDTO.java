package ar.com.santalucia.dominio.dto;

import java.util.Date;

public class MesaDTO {
	private Long idMesa;
	private Long idMateria;
	private String Materia;
	private Date fechaHoraInicio;
	private Date fechaHoraFin;
	private Long idTribunal1;
	private String nombreTribunal1;
	private Long idTribunal2;
	private String nombreTribunal2;
	private Long idTribunal3;
	private String nombreTribunal3;
	
	public MesaDTO() {
		super();
	}

	public MesaDTO(Long idMesa, Long idMateria, String materia, Date fechaHoraInicio, Date fechaHoraFin,
			Long idTribunal1, String nombreTribunal1, Long idTribunal2, String nombreTribunal2, Long idTribunal3,
			String nombreTribunal3) {
		super();
		this.idMesa = idMesa;
		this.idMateria = idMateria;
		Materia = materia;
		this.fechaHoraInicio = fechaHoraInicio;
		this.fechaHoraFin = fechaHoraFin;
		this.idTribunal1 = idTribunal1;
		this.nombreTribunal1 = nombreTribunal1;
		this.idTribunal2 = idTribunal2;
		this.nombreTribunal2 = nombreTribunal2;
		this.idTribunal3 = idTribunal3;
		this.nombreTribunal3 = nombreTribunal3;
	}

	public Long getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(Long idMesa) {
		this.idMesa = idMesa;
	}

	public Long getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(Long idMateria) {
		this.idMateria = idMateria;
	}

	public String getMateria() {
		return Materia;
	}

	public void setMateria(String materia) {
		Materia = materia;
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

	public Long getIdTribunal1() {
		return idTribunal1;
	}

	public void setIdTribunal1(Long idTribunal1) {
		this.idTribunal1 = idTribunal1;
	}

	public String getNombreTribunal1() {
		return nombreTribunal1;
	}

	public void setNombreTribunal1(String nombreTribunal1) {
		this.nombreTribunal1 = nombreTribunal1;
	}

	public Long getIdTribunal2() {
		return idTribunal2;
	}

	public void setIdTribunal2(Long idTribunal2) {
		this.idTribunal2 = idTribunal2;
	}

	public String getNombreTribunal2() {
		return nombreTribunal2;
	}

	public void setNombreTribunal2(String nombreTribunal2) {
		this.nombreTribunal2 = nombreTribunal2;
	}

	public Long getIdTribunal3() {
		return idTribunal3;
	}

	public void setIdTribunal3(Long idTribunal3) {
		this.idTribunal3 = idTribunal3;
	}

	public String getNombreTribunal3() {
		return nombreTribunal3;
	}

	public void setNombreTribunal3(String nombreTribunal3) {
		this.nombreTribunal3 = nombreTribunal3;
	}
	
}
