package ar.com.santalucia.dominio.dto;

/**
 * MateriaNotaDTO: contiene información sobre una materia con la nota en la libreta. 
 * Se usa para comprobar las previas del alumno, entre otros.
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class MateriaNotaDTO {

	private String materia;
	private Float nota;

	public MateriaNotaDTO() {
		super();
	}

	public MateriaNotaDTO(String materia, Float nota) {
		super();
		this.materia = materia;
		this.nota = nota;
	}

	/**
	 * @return the materia
	 */
	public String getMateria() {
		return materia;
	}

	/**
	 * @param materia the materia to set
	 */
	public void setMateria(String materia) {
		this.materia = materia;
	}

	/**
	 * @return the nota
	 */
	public Float getNota() {
		return nota;
	}

	/**
	 * @param nota the nota to set
	 */
	public void setNota(Float nota) {
		this.nota = nota;
	}

}
