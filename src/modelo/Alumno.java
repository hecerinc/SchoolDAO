package modelo;

import java.util.Date;

public class Alumno {

	private Long id = null;
	private String nombre, apellidos;
	private Date birthDate;

	public Alumno(String nombre, String apellidos, Date birthDate) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.birthDate = birthDate;
	}

	public Alumno(){
		this.nombre = "";
		this.apellidos = "";
		this.birthDate = new Date();
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "Alumno{" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				", apellidos='" + apellidos + '\'' +
				'}';
	}
}
