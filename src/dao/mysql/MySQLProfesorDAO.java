package dao.mysql;

import dao.ProfesorDAO;
import modelo.Profesor;

import java.sql.Connection;
import java.util.List;

public class MySQLProfesorDAO implements ProfesorDAO{
	public MySQLProfesorDAO(Connection conn) {

	}

	@Override
	public void insert(Profesor a) {

	}

	@Override
	public void modify(Profesor a) {

	}

	@Override
	public void delete(Profesor a) {

	}

	@Override
	public List<Profesor> getAll() {
		return null;
	}

	@Override
	public Profesor get(Long id) {
		return null;
	}
}
