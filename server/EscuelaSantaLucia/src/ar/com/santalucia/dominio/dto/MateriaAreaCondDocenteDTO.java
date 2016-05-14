package ar.com.santalucia.dominio.dto;

/**
 * MateriaAreaCondDocenteDTO: contiene información sobre el nombre de una materia y el área al que pertenece.
 * Estas materias son para un docente determinado, su condición se especifica en el campo 'condicion'.
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class MateriaAreaCondDocenteDTO {

	private String materia;
	private String condicion;
	private String area;
	
	public static String TITULAR = "Titular";
	public static String SUPLENTE = "Suplente";

	public MateriaAreaCondDocenteDTO() {
		super();
	}

	public MateriaAreaCondDocenteDTO(String materia, String condicion, String area) {
		super();
		this.materia = materia;
		this.condicion = condicion;
		this.area = area;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

}
