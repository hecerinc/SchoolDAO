package dao.mysql;

import dao.DAOException;
import dao.ProfesorDAO;
import modelo.Profesor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLProfesorDAO implements ProfesorDAO{

	final static String INSERT = "INSERT INTO profesores(nombre, apellidos) VALUES(?,?)";
	final static String UPDATE = "UPDATE profesores SET nombre = ?, apellidos = ?, WHERE profesores.id = ?";
	final static String DELETE = "DELETE FROM profesores WHERE profesores.id = ?";
	final static String GETALL = "SELECT * FROM profesores";
	final static String GETONE = GETALL + " WHERE profesores.id = ?";


	private Connection conn;

	public MySQLProfesorDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Profesor p) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		try{
			stat = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			stat.setString(1, p.getNombre());
			stat.setString(2, p.getApellidos());
			if(stat.executeUpdate() == 0){
				throw new DAOException("Puede que no se haya insertado el registro");
			}
			rs = stat.getGeneratedKeys();
			if(rs.next()){
				p.setId(rs.getLong(1));
			}
			else{
				throw new DAOException("No se puede obtener la clave del profesor.");
			}

		}catch(SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally{
			MySQLUtils.close(stat, rs);
		}
	}

	@Override
	public void modify(Profesor p) throws DAOException {
		PreparedStatement stat = null;
		try{
			stat = conn.prepareStatement(UPDATE);
			stat.setString(1, p.getNombre());
			stat.setString(2, p.getApellidos());
			stat.setLong(3, p.getId());
			if(stat.executeUpdate() == 0){
				throw new DAOException("Puede que no se haya modificado el registro");
			}

		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally {
			MySQLUtils.close(stat);
		}
	}

	@Override
	public void delete(Profesor p) throws DAOException {
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(DELETE);
			stat.setLong(1, p.getId());
			if(stat.executeUpdate() == 0){
				throw new DAOException("Puede que no se haya borrado ningun registro");
			}
		}
		catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally {
			MySQLUtils.close(stat);
		}
	}

	Profesor convert(ResultSet rs) throws SQLException{
		String nombre = rs.getString("nombre");
		String apellidos = rs.getString("apellidos");
		long id = rs.getLong("id");

		Profesor p = new Profesor(nombre, apellidos);
		p.setId(id);
		return p;
	}

	@Override
	public List<Profesor> getAll() throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Profesor> profesores = new ArrayList<>();
		try {
			stat = conn.prepareStatement(GETALL);
			rs = stat.executeQuery();
			while(rs.next()){
				profesores.add(convert(rs));
			}
		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}finally {
			MySQLUtils.close(stat, rs);
		}
		return profesores;
	}

	@Override
	public Profesor get(Long id) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		Profesor p = null;
		try {
			stat = conn.prepareStatement(GETONE);
			stat.setLong(1, id);
			rs = stat.executeQuery();
			if(rs.next()){
				p = convert(rs);
			}
		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}finally {
			MySQLUtils.close(stat, rs);
		}
		return p;
	}
}
