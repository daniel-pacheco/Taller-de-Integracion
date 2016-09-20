package ar.com.santalucia.dominio.dto;

import java.util.List;

/**
 * Este DTO represneta el contenido del menúd desplegable para las entidades ActaVolanteExamenes
 * 
 * @author Ariel
 *
 */
public class MenuActaVolanteDTO {

	private Integer cicloLectivo;
	private List<MenuActaVolanteLlamadoDTO> llamadosActaDTO;
	
	public MenuActaVolanteDTO() {
		super();
	}

	public MenuActaVolanteDTO(Integer cicloLectivo, List<MenuActaVolanteLlamadoDTO> llamadosActaDTO) {
		super();
		this.cicloLectivo = cicloLectivo;
		this.llamadosActaDTO = llamadosActaDTO;
	}

	public Integer getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(Integer cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

	public List<MenuActaVolanteLlamadoDTO> getLlamadosActaDTO() {
		return llamadosActaDTO;
	}

	public void setLlamadosActaDTO(List<MenuActaVolanteLlamadoDTO> llamadosActaDTO) {
		this.llamadosActaDTO = llamadosActaDTO;
	}
	
}
