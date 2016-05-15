package ar.com.santalucia.dominio.dto;

/**
 * MateriaDTO: contiene información sobre la materia y el año al que pertenece.
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class MateriaDTO {

	private String nombre;
	private String descripcion;
	private String docenteTitular;
	private String docenteSuplente;
	private String area;
	private String anio;

	public MateriaDTO() {
		super();
	}

	public MateriaDTO(String nombre, String descripcion, String docenteTitular, String docenteSuplente, String area,
			String anio) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.docenteTitular = docenteTitular;
		this.docenteSuplente = docenteSuplente;
		this.area = area;
		this.anio = anio;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the docenteTitular
	 */
	public String getDocenteTitular() {
		return docenteTitular;
	}

	/**
	 * @param docenteTitular
	 *            the docenteTitular to set
	 */
	public void setDocenteTitular(String docenteTitular) {
		this.docenteTitular = docenteTitular;
	}

	/**
	 * @return the docenteSuplente
	 */
	public String getDocenteSuplente() {
		return docenteSuplente;
	}

	/**
	 * @param docenteSuplente
	 *            the docenteSuplente to set
	 */
	public void setDocenteSuplente(String docenteSuplente) {
		this.docenteSuplente = docenteSuplente;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the anio
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * @param anio
	 *            the anio to set
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}

}
