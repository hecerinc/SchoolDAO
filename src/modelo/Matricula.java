package modelo;

public class Matricula {

	private long alumno, asignatura;
	private int year;
	private Integer nota = null;

	public Matricula(long alumno, long asignatura, int year) {
		this.alumno = alumno;
		this.asignatura = asignatura;
		this.year = year;
	}

	public long getAlumno() {
		return alumno;
	}

	public void setAlumno(long alumno) {
		this.alumno = alumno;
	}

	public long getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(long asignatura) {
		this.asignatura = asignatura;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		return "Matricula{" +
				"alumno=" + alumno +
				", asignatura=" + asignatura +
				", year=" + year +
				", nota=" + nota +
				'}';
	}
}
