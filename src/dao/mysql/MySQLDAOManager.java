package dao.mysql;

import dao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDAOManager implements DAOManager{

	private Connection conn;
	private AlumnoDAO alumnos = null;
	private MatriculaDAO matriculas = null;
	private AsignaturaDAO asignaturas = null;
	private ProfesorDAO profesores = null;

	public MySQLDAOManager(String username, String password, String host, String database) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://"+ host + "/" + database, username, password);
	}

	@Override
	public AlumnoDAO getAlumnoDAO() {
		if(alumnos == null){
			alumnos = new MySQLAlumnoDAO(conn);
		}
		return alumnos;
	}

	@Override
	public AsignaturaDAO getAsignaturaDAO() {
		if(asignaturas == null){
			asignaturas = new MySQLAsignaturaDAO(conn);
		}
		return asignaturas;
	}

	@Override
	public MatriculaDAO getMatriculaDAO() {
		if(matriculas == null){
			matriculas = new MySQLMatriculaDAO(conn);
		}
		return matriculas;
	}

	@Override
	public ProfesorDAO getProfesorDAO() {
		if(profesores == null){
			profesores = new MySQLProfesorDAO(conn);
		}
		return profesores;
	}
}
