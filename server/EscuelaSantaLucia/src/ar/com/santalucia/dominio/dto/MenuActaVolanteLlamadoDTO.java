package ar.com.santalucia.dominio.dto;

import java.util.List;
/**
 * Este DTO representa un elemento (mesa) que compone a MenuActaVolanteLlamado
 * @author Ariel
 *
 */
public class MenuActaVolanteLlamadoDTO {
	private String nombreLlamado;
	private List<MenuActaVolanteMesaDTO> mesas;
	
	public MenuActaVolanteLlamadoDTO() {
		super();
	}

	public MenuActaVolanteLlamadoDTO(String nombreLlamado, List<MenuActaVolanteMesaDTO> mesas) {
		super();
		this.nombreLlamado = nombreLlamado;
		this.mesas = mesas;
	}

	public String getNombreLlamado() {
		return nombreLlamado;
	}

	public void setNombreLlamado(String nombreLlamado) {
		this.nombreLlamado = nombreLlamado;
	}

	public List<MenuActaVolanteMesaDTO> getMesas() {
		return mesas;
	}

	public void setMesas(List<MenuActaVolanteMesaDTO> mesas) {
		this.mesas = mesas;
	}
	
}
