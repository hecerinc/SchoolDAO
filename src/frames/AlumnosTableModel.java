package frames;

import dao.AlumnoDAO;
import dao.DAOException;
import modelo.Alumno;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class AlumnosTableModel extends AbstractTableModel{

	private AlumnoDAO alumnos;

	private List<Alumno> datos = new ArrayList<>();

	public AlumnosTableModel(AlumnoDAO alumnos) {
		this.alumnos = alumnos;

	}

	public void updateModel() throws DAOException{
		datos = alumnos.getAll();
	}
	@Override
	public String getColumnName(int column){
		switch (column){
			case 0: return "ID";
			case 1: return "Apellidos";
			case 2: return "Nombre";
			case 3: return "Fecha de nacimiento";
			default: return "[no]";
		}
	}

	@Override
	public int getRowCount() {
		return datos.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Alumno requested = datos.get(rowIndex);
		switch(columnIndex){
			case 0: return requested.getId();
			case 1: return requested.getApellidos();
			case 2: return requested.getNombre();
			case 3:
				DateFormat df = DateFormat.getDateInstance();
				return df.format(requested.getBirthDate());
			default:
				return "";
		}
	}
}
