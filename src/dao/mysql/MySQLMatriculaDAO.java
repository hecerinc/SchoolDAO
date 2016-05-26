package dao.mysql;

import dao.DAOException;
import dao.MatriculaDAO;
import modelo.Matricula;

import java.sql.*;
import java.util.List;

public class MySQLMatriculaDAO implements MatriculaDAO {

	private final static String INSERT = "INSERT INTO matriculas(alumno_id, asignatura_id, fecha, nota) VALUES(?, ?, ?, ?)";
	private final static String UPDATE = "UPDATE matriculas SET nota = ? WHERE alumno_id = ? AND asignatura_id = ? AND fecha = ?";
	private final static String DELETE = "DELTE FROM matriculas WHERE alumno_id = ? AND asignatura_id = ? AND fecha = ?";
	private final static String GETALL = "SELECT * FROM matriculas";
	private final static String GETONE = GETALL + " WHERE alumno_id = ? AND asignatura_id = ? AND fecha = ?";
	private final static String GETALU = GETALL + " WHERE alumno_id = ?";
	private final static String GETCUR = GETALL + " WHERE fecha = ?";
	private final static String GETASI = GETALL + " WHERE asignatura_id = ?";

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

			/* TODO: Implement this part */

		}catch (SQLException ex){
			throw new DAOException("Error en SQL", ex);
		}
		finally{
			MySQLUtils.close(stat);
		}
	}

	@Override
	public void delete(Matricula a) {

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

	}

	@Override
	public Matricula get(Long id) {
		return null;
	}

	@Override
	public List<Matricula> getByAlumno(long alumno) throws DAOException {
		return null;
	}

	@Override
	public List<Matricula> getByAsignatura(long profesor) throws DAOException {
		return null;
	}

	@Override
	public List<Matricula> getByCourse(int course) throws DAOException {
		return null;
	}
}
