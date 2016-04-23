package ar.com.santalucia.dominio.dto;

/**
 * MateriaAreaDTO: contiene información sobre el nombre de una materia y el área al que pertenece.
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class MateriaAreaDTO {

	private String materia;
	private String area;

	public MateriaAreaDTO() {
		super();
	}

	public MateriaAreaDTO(String materia, String area) {
		super();
		this.materia = materia;
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

}
