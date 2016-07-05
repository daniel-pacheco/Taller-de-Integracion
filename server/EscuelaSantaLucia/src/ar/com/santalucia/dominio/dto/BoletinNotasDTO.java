package ar.com.santalucia.dominio.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * BoletinNotasDTO: Mantiene información resumida del boletin de notas, asi
 * también como el detalle de las inasistencias por trimestre
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class BoletinNotasDTO {

	private Long idBoletinNotas;
	private String nombre;
	private String apellido;
	private Long nroDocumento;
	private String anio;
	private String curso;
	private Integer cicloLectivo;
	private List<PlanillaNotasBoletinDTO> listaNotas;
	private InasistenciasBoletinDTO detalleInasistencias;

	public BoletinNotasDTO() {
		super();
		listaNotas = new ArrayList<PlanillaNotasBoletinDTO>();
	}

	public BoletinNotasDTO(Long idBoletinNotas, String nombre, String apellido, Long nroDocumento, String anio,
			String curso, Integer cicloLectivo, List<PlanillaNotasBoletinDTO> listaNotas,
			InasistenciasBoletinDTO detalleInasistencias) {
		super();
		this.idBoletinNotas = idBoletinNotas;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nroDocumento = nroDocumento;
		this.anio = anio;
		this.curso = curso;
		this.cicloLectivo = cicloLectivo;
		this.listaNotas = listaNotas;
		this.detalleInasistencias = detalleInasistencias;
	}

	public Long getIdBoletinNotas() {
		return idBoletinNotas;
	}

	public void setIdBoletinNotas(Long idBoletinNotas) {
		this.idBoletinNotas = idBoletinNotas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Long getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(Long nroDocumento) {
		this.nroDocumento = nroDocumento;
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

	public Integer getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(Integer cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

	public List<PlanillaNotasBoletinDTO> getListaNotas() {
		return listaNotas;
	}

	public void setListaNotas(List<PlanillaNotasBoletinDTO> listaNotas) {
		this.listaNotas = listaNotas;
	}

	public InasistenciasBoletinDTO getDetalleInasistencias() {
		return detalleInasistencias;
	}

	public void setDetalleInasistencias(InasistenciasBoletinDTO detalleInasistencias) {
		this.detalleInasistencias = detalleInasistencias;
	}

}
