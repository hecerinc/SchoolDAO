package dao.mysql;

import dao.DAOException;
import dao.MatriculaDAO;
import modelo.Matricula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLMatriculaDAO implements MatriculaDAO {

	private final static String INSERT = "INSERT INTO matriculas(alumno_id, asignatura_id, fecha, nota) VALUES(?, ?, ?, ?)";
	private final static String UPDATE = "UPDATE matriculas SET nota = ? WHERE alumno_id = ? AND asignatura_id = ? AND fecha = ?";
	private final static String DELETE = "DELETE FROM matriculas WHERE alumno_id = ? AND asignatura_id = ? AND fecha = ?";
	private final static String GETALL = "SELECT * FROM matriculas";
	private final static String GETONE = GETALL + " WHERE alumno_id = ? AND asignatura_id = ? AND fecha = ?";
	private final static String GETALU = GETALL + " WHERE alumno_id = ?";
	private final static String GETASI = GETALL + " WHERE asignatura_id = ?";
	private final static String GETCUR = GETALL + " WHERE fecha = ?";

	private Connection conn;

	public MySQLMatriculaDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Matricula a) throws DAOException{
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(INSERT);
			stat.setLong(1, a.getId().getAlumno());
			stat.setLong(2, a.getId().getAsignatura());
			stat.setInt(3, a.getId().getYear());
			if(a.getNota() != null)
				stat.setInt(4, a.getNota());
			else
				stat.setNull(4, Types.INTEGER);
			if(stat.executeUpdate() == 0)
				throw new DAOException("Puede que no se haya insertado ningun registro");
		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}finally {
			MySQLUtils.close(stat);
		}

	}

	@Override
	public void modify(Matricula a) throws DAOException {
		PreparedStatement stat = null;
		try{
			stat = conn.prepareStatement(UPDATE);
			if(a.getNota() != null)
				stat.setInt(1, a.getNota());
			else
				stat.setNull(1, Types.INTEGER);
			stat.setLong(2, a.getId().getAlumno());
			stat.setLong(3, a.getId().getAsignatura());
			stat.setInt(4, a.getId().getYear());
			if(stat.executeUpdate() == 0){
				throw new DAOException("Puede que no se haya modificado ningun registro");
			}
		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally{
			MySQLUtils.close(stat);
		}
	}

	@Override
	public void delete(Matricula a) throws DAOException {
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(DELETE);
			stat.setLong(1, a.getId().getAlumno());
			stat.setLong(2, a.getId().getAsignatura());
			stat.setInt(3, a.getId().getYear());
			if(stat.executeUpdate() == 0)
				throw new DAOException("Puede que no se haya borrado ningun registro");
		} catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally {
			MySQLUtils.close(stat);
		}
	}

	private Matricula convert(ResultSet rs) throws SQLException{
		long alumno = rs.getLong("alumno_id");
		long asignatura = rs.getLong("asignatura_id");
		int year = rs.getInt("fecha");

		// Nota es distinto porque puede ser null
		Integer nota = rs.getInt("nota");
		if(rs.wasNull()) nota = null;

		Matricula mat = new Matricula(alumno, asignatura, year);
		mat.setNota(nota);
		return mat;
	}

	@Override
	public List<Matricula> getAll() throws DAOException{
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Matricula> matriculas = new ArrayList<>();
		try {
			stat = conn.prepareStatement(GETALL);
			rs = stat.executeQuery();
			while(rs.next()){
				matriculas.add(convert(rs));
			}
		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}finally {
			MySQLUtils.close(stat, rs);
		}
		return matriculas;
	}
	@Override
	public Matricula get(Matricula.IdMatricula id) throws DAOException{
		PreparedStatement stat = null;
		ResultSet rs = null;
		Matricula m = null;
		try {
			stat = conn.prepareStatement(GETONE);
			stat.setLong(1, id.getAlumno());
			stat.setLong(2, id.getAsignatura());
			stat.setInt(3, id.getYear());
			rs = stat.executeQuery();
			if(rs.next())
				m = convert(rs);
			else
				throw new DAOException("No se ha encontrado ningun registro con ese ID");
		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally {
			MySQLUtils.close(stat, rs);
		}
		return m;
	}
	private List<Matricula> getByStmt(long value, String stmt) throws DAOException{
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Matricula> mats = new ArrayList<>();
		try {
			stat = conn.prepareStatement(stmt);
			stat.setLong(1, value);
			rs = stat.executeQuery();
			while(rs.next()){
				mats.add(convert(rs));
			}
		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally{
			MySQLUtils.close(stat, rs);
		}
		return mats;
	}

	@Override
	public List<Matricula> getByAlumno(long alumno) throws DAOException {
		return getByStmt(alumno, GETALU);
	}

	@Override
	public List<Matricula> getByAsignatura(long asignatura) throws DAOException {
		return getByStmt(asignatura, GETASI);
	}

	@Override
	public List<Matricula> getByCourse(int course) throws DAOException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Matricula> mats = new ArrayList<>();
		try {
			stat = conn.prepareStatement(GETCUR);
			stat.setInt(1, course);
			rs = stat.executeQuery();
			while(rs.next()){
				mats.add(convert(rs));
			}
		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally{
			MySQLUtils.close(stat, rs);
		}
		return mats;
	}
}
