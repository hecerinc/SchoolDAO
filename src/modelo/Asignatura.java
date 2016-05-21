package modelo;

public class Asignatura {

	private Long id = null;
	private String nombre;
	private Long idProfesor;

	public Asignatura(String nombre, Long idProfesor) {
		this.nombre = nombre;
		this.idProfesor = idProfesor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getIdProfesor() {
		return idProfesor;
	}

	public void setIdProfesor(Long idProfesor) {
		this.idProfesor = idProfesor;
	}

	@Override
	public String toString() {
		return "Asignatura{" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				", idProfesor=" + idProfesor +
				'}';
	}
}
