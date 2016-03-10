package ar.com.santalucia.dominio.modelo.desempenio;

/**
 * Clase MateriaNotasBoletin: contiene las materias del boletin con sus notas trimestrales,
 * 	diciembre y marzo.
 * 
 * @author Eric
 * @version 1.0
 *
 */

public class MateriaNotasBoletin {
	private Long idMateriaNotasBoletin;
	private String materia;
	private Integer notaTrimestre1;
	private Integer notaTrimestre2;
	private Integer notaTrimestre3;
	private Integer notaDiciembre;
	private Integer notaMarzo;

	public MateriaNotasBoletin() {
		super();
	}

	public MateriaNotasBoletin(Long idMateriaNotasBoletin, String materia, Integer notaTrimestre1,
			Integer notaTrimestre2, Integer notaTrimestre3, Integer notaDiciembre, Integer notaMarzo) {
		super();
		this.idMateriaNotasBoletin = idMateriaNotasBoletin;
		this.materia = materia;
		this.notaTrimestre1 = notaTrimestre1;
		this.notaTrimestre2 = notaTrimestre2;
		this.notaTrimestre3 = notaTrimestre3;
		this.notaDiciembre = notaDiciembre;
		this.notaMarzo = notaMarzo;
	}

	public Long getIdMateriaNotasBoletin() {
		return idMateriaNotasBoletin;
	}

	public void setIdMateriaNotasBoletin(Long idMateriaNotasBoletin) {
		this.idMateriaNotasBoletin = idMateriaNotasBoletin;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public Integer getNotaTrimestre1() {
		return notaTrimestre1;
	}

	public void setNotaTrimestre1(Integer notaTrimestre1) {
		this.notaTrimestre1 = notaTrimestre1;
	}

	public Integer getNotaTrimestre2() {
		return notaTrimestre2;
	}

	public void setNotaTrimestre2(Integer notaTrimestre2) {
		this.notaTrimestre2 = notaTrimestre2;
	}

	public Integer getNotaTrimestre3() {
		return notaTrimestre3;
	}

	public void setNotaTrimestre3(Integer notaTrimestre3) {
		this.notaTrimestre3 = notaTrimestre3;
	}

	public Integer getNotaDiciembre() {
		return notaDiciembre;
	}

	public void setNotaDiciembre(Integer notaDiciembre) {
		this.notaDiciembre = notaDiciembre;
	}

	public Integer getNotaMarzo() {
		return notaMarzo;
	}

	public void setNotaMarzo(Integer notaMarzo) {
		this.notaMarzo = notaMarzo;
	}

}
