package ar.com.santalucia.dominio.modelo.aulavirtual;

/**
 * Clae Recurso (link, pdf, algo para descargar, ...)
 * 
 * @author Eric
 * @version 1.0
 *
 */

// Último modificador: Ariel Ramirez @ 03-02-2016 09:06

public class Recurso {
	private Long idRecurso;
	private String nombre;
	private String descripcion;
	private String vinculo;
	private Character tipo;

	public static Character VINCULO_WEB = 'v';
	public static Character ARCHIVO = 'a';
	
	public Recurso() {
		super();
	}

	public Recurso(Long idRecurso, String nombre, String descripcion, String vinculo, Character tipo) {
		super();
		this.idRecurso = idRecurso;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.vinculo = vinculo;
		this.tipo = tipo;
	}

	public Long getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(Long idRecurso) {
		this.idRecurso = idRecurso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getVinculo() {
		return vinculo;
	}

	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

}
