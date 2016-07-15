package ar.com.santalucia.dominio.dto;

/**
 * MateriaAreaCondDocenteDTO: contiene información sobre el nombre de una materia y el área al que pertenece.
 * Estas materias son para un docente determinado, su condición se especifica en el campo 'condicion'.
 * 
 * @author Eric
 * @version 1.0
 *
 */

//Último modificador: Ariel Ramirez @ 15-07-2016 1:05

public class MateriaAreaCondDocenteDTO {

	private Long idMateria;
	private String nombre;
	private String condicion;
	private Long idArea;
	private String area;
	
	public static String TITULAR = "Titular";
	public static String SUPLENTE = "Suplente";

	public MateriaAreaCondDocenteDTO() {
		super();
	}

	public MateriaAreaCondDocenteDTO(Long idMateria, String nombre, String condicion, Long idArea, String area) {
		super();
		this.setIdMateria(idMateria);
		this.nombre = nombre;
		this.condicion = condicion;
		this.setIdArea(idArea);
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

	public Long getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(Long idMateria) {
		this.idMateria = idMateria;
	}

	public Long getIdArea() {
		return idArea;
	}

	public void setIdArea(Long idArea) {
		this.idArea = idArea;
	}

	

}
