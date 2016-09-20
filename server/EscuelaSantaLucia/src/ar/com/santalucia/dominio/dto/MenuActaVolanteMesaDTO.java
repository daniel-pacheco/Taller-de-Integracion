package ar.com.santalucia.dominio.dto;

/**
 * Este DTO representa una elemento (llamado) que compone a MenuActaVolanteDTO
 * @author Ariel
 *
 */
public class MenuActaVolanteMesaDTO {
	
	private String nombreMesa;
	private Long idActaVolante;
	
	public MenuActaVolanteMesaDTO() {
		super();
	}

	public MenuActaVolanteMesaDTO(String nombreMesa, Long idActaVolante) {
		super();
		this.nombreMesa = nombreMesa;
		this.idActaVolante = idActaVolante;
	}

	public String getNombreMesa() {
		return nombreMesa;
	}

	public void setNombreMesa(String nombreMesa) {
		this.nombreMesa = nombreMesa;
	}

	public Long getIdActaVolante() {
		return idActaVolante;
	}

	public void setIdActaVolante(Long idActaVolante) {
		this.idActaVolante = idActaVolante;
	}
		
}
