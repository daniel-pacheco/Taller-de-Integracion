package ar.com.santalucia.dominio.dto;

public class GetListaPasajeAlumnosDTO {

	private String anio;
	private String curso;

	public GetListaPasajeAlumnosDTO() {
		super();
	}

	public GetListaPasajeAlumnosDTO(String anio, String curso) {
		super();
		this.anio = anio;
		this.curso = curso;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

}
