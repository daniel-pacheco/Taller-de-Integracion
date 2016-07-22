package ar.com.santalucia.dominio.modelo.academico;

public class Especialidad {

	private Long idEspecialidad;
	private String nombre;
	private String nombreCorto;

	public Especialidad() {
		super();
	}

	public Especialidad(Long idEspecialidad, String nombre, String nombreCorto) {
		super();
		this.idEspecialidad = idEspecialidad;
		this.nombre = nombre;
		this.nombreCorto = nombreCorto;
	}

	public Long getIdEspecialidad() {
		return idEspecialidad;
	}

	public void setIdEspecialidad(Long idEspecialidad) {
		this.idEspecialidad = idEspecialidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Especialidad){
			if (this.getNombre().equals(((Especialidad) obj).getNombre()) || 
					this.getNombreCorto().equals(((Especialidad) obj).getNombreCorto())){
				return true;
			}
		}
		return false;
	}

	
}
