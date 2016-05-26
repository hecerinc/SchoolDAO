package dao.mysql;

import dao.AsignaturaDAO;
import dao.DAOException;
import modelo.Asignatura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLAsignaturaDAO implements AsignaturaDAO{

	private Connection conn;

	final static String INSERT = "INSERT INTO asignaturas (nombre, profesor_id) VALUES(?, ?)";
	final static String UPDATE = "UPDATE asignaturas SET nombre = ?, profesor_id = ? WHERE asignaturas.id = ?";
	final static String DELETE = "DELETE FROM asignaturas WHERE asignaturas.id = ?";
	final static String GETALL = "SELECT * FROM asignaturas";
	final static String GETONE = GETALL + " WHERE asignaturas.id = ?";


	MySQLAsignaturaDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Asignatura a) throws DAOException{
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			stat.setString(1, a.getNombre());
			stat.setLong(2, a.getIdProfesor());
			if(stat.executeUpdate() == 0){
				throw new DAOException("Puede que no se haya insertado ningun registro");
			}
			rs = stat.getGeneratedKeys();
			if(rs.next()){
				a.setId(rs.getLong(1));
			}
			else{
				throw new DAOException("No se puede obtener el id de la asignatura");
			}
		}
		catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally {
			MySQLUtils.close(stat, rs);
		}
	}

	@Override
	public void modify(Asignatura a) throws DAOException{
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(UPDATE);
			stat.setString(1, a.getNombre());
			stat.setLong(2, a.getIdProfesor());
			stat.setLong(3, a.getId());
			if(stat.executeUpdate() == 0){
				throw new DAOException("Puede que no se haya modificado ningun registro");
			}
		}
		catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally {
			MySQLUtils.close(stat);
		}
	}

	@Override
	public void delete(Asignatura a) throws DAOException{
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(DELETE);
			stat.setLong(1, a.getId());
			if(stat.executeUpdate() == 0){
				throw new DAOException("Puede que no se haya borrado ningun registro");
			}
		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally {
			MySQLUtils.close(stat);
		}
	}

	private Asignatura convert(ResultSet rs) throws SQLException{
		String nombre = rs.getString("nombre");
		long idprofesor = rs.getLong("profesor_id");
		long id = rs.getLong("id");
		Asignatura a = new Asignatura(nombre, idprofesor);
		a.setId(id);
		return a;
	}

	@Override
	public List<Asignatura> getAll() throws DAOException{
		List<Asignatura> asignaturas = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(GETALL);
			rs = stat.executeQuery();
			while(rs.next()){
				asignaturas.add(convert(rs));
			}
		}
		catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally {
			MySQLUtils.close(stat, rs);
		}
		return asignaturas;
	}

	@Override
	public Asignatura get(Long id) throws DAOException{
		Asignatura a = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = conn.prepareStatement(GETONE);
			stat.setLong(1, id);
			rs = stat.executeQuery();
			if(rs.next()){
				a = convert(rs);
			}
			else{
				throw new DAOException("No se ha podido encontrar ningun registro con ese id");
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
