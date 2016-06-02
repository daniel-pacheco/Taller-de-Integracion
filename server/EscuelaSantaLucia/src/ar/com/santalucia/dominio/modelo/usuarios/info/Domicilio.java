package ar.com.santalucia.dominio.modelo.usuarios.info;

/**
 * Clase Domicilio. Maneja la información completa del domicilio de un usuario
 * 
 * @author Eric
 * @version 1.4
 */

// UltimoModificador: Eric Pennachini @ 16-08-15 16:19

public class Domicilio {

	private Long idDomicilio;
	private String calle;
	private Integer numero;
	private Integer piso;
	private String localidad;
	private String dpto;
	private String departamento;
	private String provincia;
	private Integer codigoPostal;
	private String barrio;

	public Domicilio() {
		super();
	}

	public Domicilio(String calle, Integer numero, Integer piso, String localidad, String dpto, String departamento,
			String provincia, Integer codigoPostal, String barrio) {
		super();
		this.calle = calle;
		this.numero = numero;
		this.piso = piso;
		this.localidad = localidad;
		this.dpto = dpto;
		this.departamento = departamento;
		this.provincia = provincia;
		this.codigoPostal = codigoPostal;
		this.barrio = barrio;
	}

	public Long getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(Long idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getPiso() {
		return piso;
	}

	public void setPiso(Integer piso) {
		this.piso = piso;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getDpto() {
		return dpto;
	}

	public void setDpto(String dpto) {
		this.dpto = dpto;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	@Override
	public String toString() {

		String domicilioCompleto = calle + " " + numero + " - " + piso + " " + dpto + " " + barrio + " " + localidad
				+ " (" + codigoPostal + ") - " + provincia;

		return domicilioCompleto;
	}

	@Override
	public boolean equals(Object obj) {
		if ((this.barrio.equals(((Domicilio) obj).barrio)) 
				&& (this.calle.equals(((Domicilio) obj).calle))
				&& (this.codigoPostal.equals(((Domicilio) obj).codigoPostal))
				&& (this.departamento.equals(((Domicilio) obj).departamento))
				&& (this.dpto.equals(((Domicilio) obj).dpto))
				&& (this.localidad.equals(((Domicilio) obj).localidad))
				&& (this.numero.equals(((Domicilio) obj).numero)) 
				&& (this.piso.equals(((Domicilio) obj).piso))
				&& (this.provincia.equals(((Domicilio) obj).provincia))) {
			return true;
		} else {
			return false;
		}
	}

}
