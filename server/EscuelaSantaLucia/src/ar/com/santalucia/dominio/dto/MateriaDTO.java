package ar.com.santalucia.dominio.dto;

/**
 * MateriaDTO: contiene información sobre la materia y el año al que pertenece.
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class MateriaDTO {
	private Long idMateria;
	private String nombre;
	private String descripcion;
	private String docenteTitular;
	private String docenteSuplente;
	private String area;
	private String anio;

	public MateriaDTO() {
		super();
	}

	public MateriaDTO(Long idMateria, String nombre, String descripcion, String docenteTitular, String docenteSuplente, String area,
			String anio) {
		super();
		this.idMateria = idMateria;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.docenteTitular = docenteTitular;
		this.docenteSuplente = docenteSuplente;
		this.area = area;
		this.anio = anio;
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
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the docenteTitular
	 */
	public String getDocenteTitular() {
		return docenteTitular;
	}

	/**
	 * @param docenteTitular
	 *            the docenteTitular to set
	 */
	public void setDocenteTitular(String docenteTitular) {
		this.docenteTitular = docenteTitular;
	}

	/**
	 * @return the docenteSuplente
	 */
	public String getDocenteSuplente() {
		return docenteSuplente;
	}

	/**
	 * @param docenteSuplente
	 *            the docenteSuplente to set
	 */
	public void setDocenteSuplente(String docenteSuplente) {
		this.docenteSuplente = docenteSuplente;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the anio
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * @param anio
	 *            the anio to set
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}

	public Long getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(Long idMateria) {
		this.idMateria = idMateria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anio == null) ? 0 : anio.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((docenteSuplente == null) ? 0 : docenteSuplente.hashCode());
		result = prime * result + ((docenteTitular == null) ? 0 : docenteTitular.hashCode());
		result = prime * result + ((idMateria == null) ? 0 : idMateria.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MateriaDTO other = (MateriaDTO) obj;
		if (anio == null) {
			if (other.anio != null)
				return false;
		} else if (!anio.equals(other.anio))
			return false;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (docenteSuplente == null) {
			if (other.docenteSuplente != null)
				return false;
		} else if (!docenteSuplente.equals(other.docenteSuplente))
			return false;
		if (docenteTitular == null) {
			if (other.docenteTitular != null)
				return false;
		} else if (!docenteTitular.equals(other.docenteTitular))
			return false;
		if (idMateria == null) {
			if (other.idMateria != null)
				return false;
		} else if (!idMateria.equals(other.idMateria))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	
}
