package ar.com.santalucia.dominio.dto;

// Último modificador: Ariel Ramirez @ 17-07-2016 22:03 
public class GetListaPasajeAlumnosDTO {

	private String anio;
	private String especialidad;
	private String curso;

	public GetListaPasajeAlumnosDTO() {
		super();
	}

	public GetListaPasajeAlumnosDTO(String anio, String especialidad, String curso) {
		super();
		this.anio = anio;
		this.setEspecialidad(especialidad);
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

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

}
