package ar.com.santalucia.dominio.dto;

/**
 * InasistenciasBoletinDTO: contiene información sobre el detalle de las
 * inasistencias por trimestre del boletín del alumno.
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class InasistenciasBoletinDTO {

	private Float justificadasTrim1;
	private Float justificadasTrim2;
	private Float justificadasTrim3;
	private Float injustificadasTrim1;
	private Float injustificadasTrim2;
	private Float injustificadasTrim3;

	public InasistenciasBoletinDTO() {
		super();
		this.justificadasTrim1 = 0F;
		this.justificadasTrim2 = 0F;
		this.justificadasTrim3 = 0F;
		this.injustificadasTrim1 = 0F;
		this.injustificadasTrim2 = 0F;
		this.injustificadasTrim3 = 0F;
	}

	public InasistenciasBoletinDTO(Float justificadasTrim1, Float justificadasTrim2, Float justificadasTrim3,
			Float injustificadasTrim1, Float injustificadasTrim2, Float injustificadasTrim3) {
		super();
		this.justificadasTrim1 = justificadasTrim1;
		this.justificadasTrim2 = justificadasTrim2;
		this.justificadasTrim3 = justificadasTrim3;
		this.injustificadasTrim1 = injustificadasTrim1;
		this.injustificadasTrim2 = injustificadasTrim2;
		this.injustificadasTrim3 = injustificadasTrim3;
	}

	public Float getJustificadasTrim1() {
		return justificadasTrim1;
	}

	public void setJustificadasTrim1(Float justificadasTrim1) {
		this.justificadasTrim1 = justificadasTrim1;
	}

	public Float getJustificadasTrim2() {
		return justificadasTrim2;
	}

	public void setJustificadasTrim2(Float justificadasTrim2) {
		this.justificadasTrim2 = justificadasTrim2;
	}

	public Float getJustificadasTrim3() {
		return justificadasTrim3;
	}

	public void setJustificadasTrim3(Float justificadasTrim3) {
		this.justificadasTrim3 = justificadasTrim3;
	}

	public Float getInjustificadasTrim1() {
		return injustificadasTrim1;
	}

	public void setInjustificadasTrim1(Float injustificadasTrim1) {
		this.injustificadasTrim1 = injustificadasTrim1;
	}

	public Float getInjustificadasTrim2() {
		return injustificadasTrim2;
	}

	public void setInjustificadasTrim2(Float injustificadasTrim2) {
		this.injustificadasTrim2 = injustificadasTrim2;
	}

	public Float getInjustificadasTrim3() {
		return injustificadasTrim3;
	}

	public void setInjustificadasTrim3(Float injustificadasTrim3) {
		this.injustificadasTrim3 = injustificadasTrim3;
	}

}
