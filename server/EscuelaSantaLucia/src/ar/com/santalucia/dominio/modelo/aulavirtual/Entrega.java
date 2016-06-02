package ar.com.santalucia.dominio.modelo.aulavirtual;

import java.util.Date;

import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

/**
 * Clase Entrega (entrega que hace el alumno al profesor mediante el aula virtual)
 * 
 * @author Eric
 * @version 1.0
 *
 */

// último modificador: Ariel Ramírez @ 02-02-2016 22:02

public class Entrega {
	private Long idEntrega;
	private String descripcion;
	private Date fecha;
	private String vinculo;
	private Float calificacion;
	private String retroalimentacion;
	private ConfiguracionEntrega perteneceEntrega;
	private Alumno alumno;
	private Integer cantidadModificaciones;
	
	private static Integer MAX_MODIF_ENTREGAS = 5;
	
	public Entrega() {
		super();
	}

	public Entrega(Long idEntrega, String descripcion, Date fecha, String vinculo, Float calificacion,
			String retroalimentacion, ConfiguracionEntrega perteneceEntrega, Alumno alumno) {
		super();
		this.idEntrega = idEntrega;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.vinculo = vinculo;
		this.calificacion = calificacion;
		this.retroalimentacion = retroalimentacion;
		this.perteneceEntrega = perteneceEntrega;
		this.alumno = alumno;
	}

	public Long getIdEntrega() {
		return idEntrega;
	}

	public void setIdEntrega(Long idEntrega) {
		this.idEntrega = idEntrega;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getVinculo() {
		return vinculo;
	}

	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}

	public Float getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Float calificacion) {
		this.calificacion = calificacion;
	}

	public String getRetroalimentacion() {
		return retroalimentacion;
	}

	public void setRetroalimentacion(String retroalimentacion) {
		this.retroalimentacion = retroalimentacion;
	}

	public ConfiguracionEntrega getPerteneceEntrega() {
		return perteneceEntrega;
	}

	public void setPerteneceEntrega(ConfiguracionEntrega perteneceEntrega) {
		this.perteneceEntrega = perteneceEntrega;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Integer getCantidadModificaciones() {
		return cantidadModificaciones;
	}

	public void setCantidadModificaciones(Integer cantidadModificaciones) {
		this.cantidadModificaciones = cantidadModificaciones;
	}

}
