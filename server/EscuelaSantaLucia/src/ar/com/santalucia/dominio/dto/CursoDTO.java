package ar.com.santalucia.dominio.dto;

/**
 * CursoDTO: maneja información reducida de los cursos para un año, con
 * división, turno y cantidad de alumnos.
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class CursoDTO {

	private Long idCurso;
	private String division;
	private String turno;
	private Integer cantAlu;

	public CursoDTO() {
		super();
	}

	public CursoDTO(Long idCurso, String division, String turno, Integer cantAlu) {
		super();
		this.idCurso = idCurso;
		this.division = division;
		this.turno = turno;
		this.cantAlu = cantAlu;
	}

	/**
	 * @return the idCurso
	 */
	public Long getIdCurso() {
		return idCurso;
	}

	/**
	 * @param idCurso
	 *            the idCurso to set
	 */
	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	/**
	 * @return the division
	 */
	public String getDivision() {
		return division;
	}

	/**
	 * @param division
	 *            the division to set
	 */
	public void setDivision(String division) {
		this.division = division;
	}

	/**
	 * @return the turno
	 */
	public String getTurno() {
		return turno;
	}

	/**
	 * @param turno
	 *            the turno to set
	 */
	public void setTurno(String turno) {
		this.turno = turno;
	}

	/**
	 * @return the cantAlu
	 */
	public Integer getCantAlu() {
		return cantAlu;
	}

	/**
	 * @param cantAlu
	 *            the cantAlu to set
	 */
	public void setCantAlu(Integer cantAlu) {
		this.cantAlu = cantAlu;
	}

}
