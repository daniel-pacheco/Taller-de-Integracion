package ar.com.santalucia.dominio.modelo.academico;

import java.util.Date;
import java.util.List;
import java.util.Set;

import ar.com.santalucia.dominio.modelo.usuarios.Personal;

/**
 * Esta entidad representa un elemento intermedio (Acta volante de Examen) a la tabla histórica de rendidas (MesaExamenHist)
 * @author Ariel
 * @version 1.0
 */
public class ActaVolanteExamenes {
	private Long idActaVolanteExamen;
	private Long idLlamado;
	private String nombreLlamado;
	private Long idMesa;
	private String nombreMesa;
	private Date fechaMesa;
	private Date horaInicio;
	private Date horaFin;
	private Personal tribunal1;
	private Personal tribunal2;
	private Personal tribunal3;
	private Integer cicloLectivo;
	private Set<DetalleVolante> detalles;
	private Boolean estado;
	private Boolean modificable;
	
	public ActaVolanteExamenes() {
		super();
	}

	public ActaVolanteExamenes(Long idActaVolanteExamen, Long idLlamado,String nombreLlamado, Long idMesa, String nombreMesa, Date fechaMesa, Date horaInicio,
			Date horaFin, Personal tribunal1, Personal tribunal2, Personal tribunal3, Integer cicloLectivo,
			Set<DetalleVolante> detalles, Boolean estado, Boolean modificable) {
		super();
		this.idActaVolanteExamen = idActaVolanteExamen;
		this.idLlamado = idLlamado;
		this.setNombreLlamado(nombreLlamado);
		this.idMesa = idMesa;
		this.setNombreMesa(nombreMesa);
		this.fechaMesa = fechaMesa;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.tribunal1 = tribunal1;
		this.tribunal2 = tribunal2;
		this.tribunal3 = tribunal3;
		this.cicloLectivo = cicloLectivo;
		this.detalles = detalles;
		this.estado = estado;
		this.setModificable(modificable);
	}

	public Long getIdActaVolanteExamen() {
		return idActaVolanteExamen;
	}

	public void setIdActaVolanteExamen(Long idActaVolanteExamen) {
		this.idActaVolanteExamen = idActaVolanteExamen;
	}

	public Long getIdLlamado() {
		return idLlamado;
	}

	public void setIdLlamado(Long idLlamado) {
		this.idLlamado = idLlamado;
	}

	public Long getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(Long idMesa) {
		this.idMesa = idMesa;
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

	public Personal getTribunal1() {
		return tribunal1;
	}

	public void setTribunal1(Personal tribunal1) {
		this.tribunal1 = tribunal1;
	}

	public Personal getTribunal2() {
		return tribunal2;
	}

	public void setTribunal2(Personal tribunal2) {
		this.tribunal2 = tribunal2;
	}

	public Personal getTribunal3() {
		return tribunal3;
	}

	public void setTribunal3(Personal tribunal3) {
		this.tribunal3 = tribunal3;
	}

	public Integer getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(Integer cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

	public Set<DetalleVolante> getDetalles() {
		return detalles;
	}

	public void setDetalles(Set<DetalleVolante> detalles) {
		this.detalles = detalles;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Boolean getModificable() {
		return modificable;
	}

	public void setModificable(Boolean modificable) {
		this.modificable = modificable;
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
	
}
