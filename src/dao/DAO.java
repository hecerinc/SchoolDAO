package dao;


import java.util.List;


public interface DAO<T, K> {

	void insert(T a) throws DAOException;

	void modify(T a) throws DAOException;

	void delete(T a) throws DAOException;

	List<T> getAll() throws DAOException;

	T get(Long id) throws DAOException;

}
