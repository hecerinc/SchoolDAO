package dao;

import modelo.Matricula;

import java.util.List;

public interface MatriculaDAO extends DAO<Matricula, Matricula.IdMatricula>{

	List<Matricula> getByAlumno(long alumno) throws DAOException;

	List<Matricula> getByAsignatura(long profesor) throws DAOException;

	List<Matricula> getByCourse(int course) throws DAOException;

}
