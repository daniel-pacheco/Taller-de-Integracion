package ar.com.santalucia.dominio.modelo.sistema.configuracion;


/**
 * Clase ParametroConfiguracion: contiene parámetros de configuracion del sistema varios
 * 
 * @author Eric
 * @ersion 1.0
 *
 */

public class ParametroConfiguracion {
	private Long idParametroConfiguracion;
	private String nombre;
	private String valor;

	public ParametroConfiguracion() {
		super();
	}

	public ParametroConfiguracion(Long idParametroConfiguracion, String nombre, String valor) {
		super();
		this.idParametroConfiguracion = idParametroConfiguracion;
		this.nombre = nombre;
		this.valor = valor;
	}

	public Long getIdParametroConfiguracion() {
		return idParametroConfiguracion;
	}

	public void setIdParametroConfiguracion(Long idParametroConfiguracion) {
		this.idParametroConfiguracion = idParametroConfiguracion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
