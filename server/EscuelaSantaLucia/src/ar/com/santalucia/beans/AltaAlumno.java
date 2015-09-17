package ar.com.santalucia.beans;

import java.util.Date;

import ar.com.santalucia.dominio.modelo.usuarios.Alumno;

public class AltaAlumno {

	private Alumno alumnoBean = new Alumno();

	private String nombres;
	private String apellidos;
	private String tipoDni;
	private String nombreCalle;
	private String fechaNacimiento;
	private char sexo;
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getTipoDni() {
		return tipoDni;
	}
	public void setTipoDni(String tipoDni) {
		this.tipoDni = tipoDni;
	}
	public String getNombreCalle() {
		return nombreCalle;
	}
	public void setNombreCalle(String nombreCalle) {
		this.nombreCalle = nombreCalle;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	
	public void setAlumnoBean(){
		alumnoBean.setApellido(apellidos);
		alumnoBean.setNombre(nombres);
		alumnoBean.setFechaNacimiento(new Date(fechaNacimiento));
		alumnoBean.setSexo(sexo);
	}
}
