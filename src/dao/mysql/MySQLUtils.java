package dao.mysql;

import dao.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLUtils {
	static void close(PreparedStatement stm) throws DAOException {
		if(stm != null){
			try{
				stm.close();
			}
			catch(SQLException ex){
				throw new DAOException("Error al cerrar el recurso SQL", ex);
			}
		}
	}
	static void close(PreparedStatement stm, ResultSet rs) throws DAOException{
		if(rs != null){
			try {
				rs.close();
			}catch(SQLException ex){
				throw new DAOException("Error al cerrar el recurso SQL", ex);
			}
		}
		close(stm);
	}
}
