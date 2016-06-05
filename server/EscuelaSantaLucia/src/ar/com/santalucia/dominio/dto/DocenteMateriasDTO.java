package ar.com.santalucia.dominio.dto;

import java.util.ArrayList;

/**
 * DocenteMateriasDTO: contiene información de un docente con los años en que
 * dicta materias las materias que dicta.
 * 
 * @author Eric
 * @version 1.1
 *
 */

// Último modificador: Eric Pennachini @ 05/06/2016 13:32

public class DocenteMateriasDTO {
	private Long nroDocumento;
	private String nombre;
	private String apellido;
	private ArrayList<String> listaAnios;
	// private ArrayList<String> areas;
	private ArrayList<MateriaAreaCondDocenteDTO> listaMaterias;

	public DocenteMateriasDTO() {
		super();
	}

	public DocenteMateriasDTO(Long nroDocumento, String nombre, String apellido, ArrayList<String> listaAnios,
			ArrayList<MateriaAreaCondDocenteDTO> listaMaterias) {
		super();
		this.nroDocumento = nroDocumento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.listaAnios = listaAnios;
		this.listaMaterias = listaMaterias;
	}

	/**
	 * @return the nroDocumento
	 */
	public Long getNroDocumento() {
		return nroDocumento;
	}

	/**
	 * @param nroDocumento
	 *            the nroDocumento to set
	 */
	public void setNroDocumento(Long nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * @param apellido
	 *            the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @return the listaAnios
	 */
	public ArrayList<String> getListaAnios() {
		return listaAnios;
	}

	/**
	 * @param listaAnios
	 *            the listaAnios to set
	 */
	public void setListaAnios(ArrayList<String> listaAnios) {
		this.listaAnios = listaAnios;
	}

	/**
	 * @return the listaMaterias
	 */
	public ArrayList<MateriaAreaCondDocenteDTO> getListaMaterias() {
		return listaMaterias;
	}

	/**
	 * @param listaMaterias
	 *            the listaMaterias to set
	 */
	public void setListaMaterias(ArrayList<MateriaAreaCondDocenteDTO> listaMaterias) {
		this.listaMaterias = listaMaterias;
	}

}
