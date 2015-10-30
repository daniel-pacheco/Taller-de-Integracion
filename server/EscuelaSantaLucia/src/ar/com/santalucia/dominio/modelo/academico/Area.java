package ar.com.santalucia.dominio.modelo.academico;

/**
 * Clase Area (areas de las materias)
 * @author ericpennachini
 * @version 1.0
 *
 */

public class Area {

	private Long idArea;
	private String nombre;

	public Area() {
		super();
	}

	public Area(Long idArea, String nombre) {
		super();
		this.idArea = idArea;
		this.nombre = nombre;
	}

	public Long getIdArea() {
		return idArea;
	}

	public void setIdArea(Long idArea) {
		this.idArea = idArea;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
