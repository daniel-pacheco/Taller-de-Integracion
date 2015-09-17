package ar.com.santalucia.beans;

import java.util.Date;

import ar.com.santalucia.aplicacion.gestor.usuario.GestorAlumno;
import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

public class AlumnoBean {

	public AlumnoBean() {

	}

	private Alumno alumnoBean = new Alumno();

	private GestorAlumno GAlumno;

	private String nombres;
	private String apellidos;
	private String nombreUsuario;
	private String nombreCalle;
	private Long numeroCalle;
	private String correoElectronico;
	private Date fechaNacimiento;
	private Long matricula;
	private String telefono;
	private String tipoDni;
	private Long numeroDni;

	public Alumno getAlumnoBean() {
		return alumnoBean;
	}

	public void setAlumnoBean() {
		alumnoBean.setNombre(nombres);
		alumnoBean.setApellido(apellidos);
		alumnoBean.setFechaNacimiento(fechaNacimiento);
		alumnoBean.setMatricula(matricula);
		alumnoBean.setNroDocumento(numeroDni);
		alumnoBean.setTipoDocumento(tipoDni);
		alumnoBean.setNombreUsuario(nombreUsuario);
		try {
			GAlumno=new GestorAlumno();
			GAlumno.add(alumnoBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public GestorAlumno getGAlumno() {
		return GAlumno;
	}

	public void setGAlumno(GestorAlumno gAlumno) {
		GAlumno = gAlumno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNombreCalle() {
		return nombreCalle;
	}

	public void setNombreCalle(String nombreCalle) {
		this.nombreCalle = nombreCalle;
	}

	public Long getNumeroCalle() {
		return numeroCalle;
	}

	public void setNumeroCalle(Long numeroCalle) {
		this.numeroCalle = numeroCalle;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipoDni() {
		return tipoDni;
	}

	public void setTipoDni(String tipoDni) {
		this.tipoDni = tipoDni;
	}

	public Long getNumeroDni() {
		return numeroDni;
	}

	public void setNumeroDni(Long numeroDni) {
		this.numeroDni = numeroDni;
	}

}
