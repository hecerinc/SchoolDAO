CREATE TABLE alumnos(
	id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(32) NOT NULL,
	apellidos VARCHAR(64) NOT NULL,
	fecha_nac DATE NOT NULL
);

CREATE TABLE profesores(
	id int(11) NOT NULL PRIMARY KEY  AUTO_INCREMENT,
	nombre VARCHAR(32) NOT NULL,
	apellidos VARCHAR(64) NOT NULL
);

CREATE TABLE asignaturas(
	id int(11) NOT NULL PRIMARY KEY  AUTO_INCREMENT,
	nombre VARCHAR(64) NOT NULL,
	profesor_id int(11) NOT NULL,
	FOREIGN KEY profesor_asignatura(profesor_id) REFERENCES profesores(id)
);

CREATE TABLE matriculas(
	alumno_id int(11) NOT NULL,
	asignatura_id int(11) NOT NULL,
	fecha YEAR NOT NULL,
	nota int(11),
	PRIMARY KEY(alumno_id, asignatura_id, fecha),
	FOREIGN KEY alumno_matriculado(alumno_id) REFERENCES alumnos(id),
	FOREIGN KEY asignatura_matriculada(asignatura_id) REFERENCES asignaturas(id)
);