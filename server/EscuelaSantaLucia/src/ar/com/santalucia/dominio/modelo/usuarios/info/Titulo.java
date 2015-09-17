package ar.com.santalucia.dominio.modelo.usuarios.info;

/**
 * Clase Titulo, almacena los titulos académicos de los Docente y Directivo
 * 
 * @author Eric
 * @version 1.2
 * 
 */

// UltimoModificador: Eric Pennachini @ 16-08-15 16:19

public class Titulo {

	private Long idTitulo;
	private String nombreTitulo;
	private String descripcionTitulo;

	public Titulo() {
		super();
	}

	public Titulo(String nombreTitulo, String descripcionTitulo) {
		super();
		this.nombreTitulo = nombreTitulo;
		this.descripcionTitulo = descripcionTitulo;
	}

	public Long getIdTitulo() {
		return idTitulo;
	}

	public void setIdTitulo(Long idTitulo) {
		this.idTitulo = idTitulo;
	}

	public String getNombreTitulo() {
		return nombreTitulo;
	}

	public void setNombreTitulo(String nombreTitulo) {
		this.nombreTitulo = nombreTitulo;
	}

	public String getDescripcionTitulo() {
		return descripcionTitulo;
	}

	public void setDescripcionTitulo(String descripcionTitulo) {
		this.descripcionTitulo = descripcionTitulo;
	}

	@Override
	public String toString() {
		String tituloCompleto = nombreTitulo + ", Descripcion: "
				+ descripcionTitulo;
		return tituloCompleto;
	}

}
