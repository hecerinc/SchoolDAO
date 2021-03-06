package dao.mysql;

import dao.AlumnoDAO;
import dao.DAOException;
import modelo.Alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class MySQLAlumnoDAO implements AlumnoDAO{

	final String INSERT = "INSERT INTO alumnos(nombre, apellidos, fecha_nac) VALUES(?, ?, ?)";
	final String UPDATE = "UPDATE alumnos SET nombre = ?, apellidos = ?, fecha_nac = ? WHERE alumnos.id = ?";
	final String DELETE = "DELETE FROM alumnos WHERE alumnos.id = ?";
	final String GETALL = "SELECT id, nombre, apellidos, fecha_nac FROM alumnos";
	final String GETONE = GETALL + " WHERE alumnos.id = ?";

	private Connection conn;

	public MySQLAlumnoDAO(Connection conn){
		this.conn = conn;
	}

	@Override
	public void insert(Alumno a) throws DAOException{
		PreparedStatement stat = null;
		ResultSet rs = null;
		try{
			stat = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			stat.setString(1, a.getNombre());
			stat.setString(2, a.getApellidos());
			stat.setDate(3, new Date(a.getBirthDate().getTime()));
			if(stat.executeUpdate() == 0){
				throw new DAOException("Puede que no se haya guardado");
			}
			rs = stat.getGeneratedKeys();
			if(rs.next())
				a.setId(rs.getLong(1));
			else {
				throw new DAOException("No puedo asignar ID a este alumno");
			}

		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);

		}
		finally {
			MySQLUtils.close(stat, rs);
		}
	}

	@Override
	public void modify(Alumno a) throws DAOException {
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(UPDATE);
			stat.setString(1, a.getNombre());
			stat.setString(2, a.getApellidos());
			stat.setDate(3, new Date(a.getBirthDate().getTime()));
			stat.setLong(4, a.getId());
			if(stat.executeUpdate() == 0){
				throw new DAOException("Puede que no se haya modificado el modelo");
			}
		}catch (SQLException ex) {
			throw new DAOException("Error en SQL", ex);
		}finally {
			MySQLUtils.close(stat);
		}
	}

	@Override
	public void delete(Alumno a) throws DAOException {
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(DELETE);
			stat.setLong(1, a.getId());
			if(stat.executeUpdate() == 0)
				throw new DAOException("Puede que el alumno no se haya borrado");
		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally {
			MySQLUtils.close(stat);
		}
	}

	private Alumno convertir(ResultSet rs) throws SQLException {
		String nombre = rs.getString("nombre");
		String apellidos = rs.getString("apellidos");
		Date fecha_nac = rs.getDate("fecha_nac");
		Alumno a = new Alumno(nombre, apellidos, fecha_nac);
		a.setId(rs.getLong("id"));
		return a;
	}

	@Override
	public List<Alumno> getAll() throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Alumno> alumnos = new ArrayList<>();
		try {
			stat = conn.prepareStatement(GETALL);
			rs = stat.executeQuery();
			while(rs.next()){
				alumnos.add(convertir(rs));
			}
		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally {
			MySQLUtils.close(stat, rs);
		}
		return alumnos;
	}

	@Override
	public Alumno get(Long id) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Alumno a = null;
		try {
			stat = conn.prepareStatement(GETONE);
			stat.setLong(1, id);
			rs = stat.executeQuery();
			if(rs.next()){
				a = convertir(rs);
			}
			else{
				throw new DAOException("No se ha encontrado ningun alumno con ese ID.");
			}
		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally {
			MySQLUtils.close(stat, rs);
		}
		return a;
	}


}
