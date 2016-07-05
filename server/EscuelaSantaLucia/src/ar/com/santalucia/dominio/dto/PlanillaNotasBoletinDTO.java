package ar.com.santalucia.dominio.dto;

/**
 * PlanillaNotasBoletinDTO: es la planilla de notas del boletín, por materia y
 * trimestre.
 * 
 * @author Eric
 *
 */
public class PlanillaNotasBoletinDTO {

	private String materia;
	private Integer notaTrim1;
	private Integer notaTrim2;
	private Integer notaTrim3;
	private Float notaFinal;
	private Integer notaDiciembre;
	private Integer notaMarzo;
	private Integer notaDefinitiva;

	public PlanillaNotasBoletinDTO() {
		super();
	}

	public PlanillaNotasBoletinDTO(String materia, Integer notaTrim1, Integer notaTrim2, Integer notaTrim3,
			Float notaFinal, Integer notaDiciembre, Integer notaMarzo, Integer notaDefinitiva) {
		super();
		this.materia = materia;
		this.notaTrim1 = notaTrim1;
		this.notaTrim2 = notaTrim2;
		this.notaTrim3 = notaTrim3;
		this.notaFinal = notaFinal;
		this.notaDiciembre = notaDiciembre;
		this.notaMarzo = notaMarzo;
		this.notaDefinitiva = notaDefinitiva;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public Integer getNotaTrim1() {
		return notaTrim1;
	}

	public void setNotaTrim1(Integer notaTrim1) {
		this.notaTrim1 = notaTrim1;
	}

	public Integer getNotaTrim2() {
		return notaTrim2;
	}

	public void setNotaTrim2(Integer notaTrim2) {
		this.notaTrim2 = notaTrim2;
	}

	public Integer getNotaTrim3() {
		return notaTrim3;
	}

	public void setNotaTrim3(Integer notaTrim3) {
		this.notaTrim3 = notaTrim3;
	}

	public Float getNotaFinal() {
		return notaFinal;
	}

	public void setNotaFinal(Float notaFinal) {
		this.notaFinal = notaFinal;
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

	public Integer getNotaDefinitiva() {
		return notaDefinitiva;
	}

	public void setNotaDefinitiva(Integer notaDefinitiva) {
		this.notaDefinitiva = notaDefinitiva;
	}

}
