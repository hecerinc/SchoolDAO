package dao.mysql;

import dao.AsignaturaDAO;
import modelo.Asignatura;

import java.sql.Connection;
import java.util.List;

public class MySQLAsignaturaDAO implements AsignaturaDAO{


	public MySQLAsignaturaDAO(Connection conn) {

	}

	@Override
	public void insert(Asignatura a) {

	}

	@Override
	public void modify(Asignatura a) {

	}

	@Override
	public void delete(Asignatura a) {

	}

	@Override
	public List<Asignatura> getAll() {
		return null;
	}

	@Override
	public Asignatura get(Long id) {
		return null;
	}
}
