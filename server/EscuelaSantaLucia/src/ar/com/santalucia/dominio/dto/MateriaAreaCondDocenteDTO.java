package ar.com.santalucia.dominio.dto;

/**
 * MateriaAreaCondDocenteDTO: contiene información sobre el nombre de una materia y el área al que pertenece.
 * Estas materias son para un docente determinado, su condición se especifica en el campo 'condicion'.
 * 
 * @author Eric
 * @version 1.0
 *
 */

//Último modificador: Eric Pennachini @ 05/06/2016 13:30

public class MateriaAreaCondDocenteDTO {

	private String nombre;
	private String condicion;
	private String area;
	
	public static String TITULAR = "Titular";
	public static String SUPLENTE = "Suplente";

	public MateriaAreaCondDocenteDTO() {
		super();
	}

	public MateriaAreaCondDocenteDTO(String nombre, String condicion, String area) {
		super();
		this.nombre = nombre;
		this.condicion = condicion;
		this.area = area;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the condicion
	 */
	public String getCondicion() {
		return condicion;
	}

	/**
	 * @param condicion the condicion to set
	 */
	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	

}
