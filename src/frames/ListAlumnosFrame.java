package frames;

import dao.DAOException;
import dao.DAOManager;
import dao.mysql.MySQLDAOManager;

import javax.swing.*;
import java.lang.instrument.IllegalClassFormatException;
import java.sql.SQLException;

public class ListAlumnosFrame extends JFrame{

	private DAOManager manager;
	private AlumnosTableModel model;

	public ListAlumnosFrame(DAOManager manager) throws DAOException{
		this.manager = manager;
		this.model = new AlumnosTableModel(manager.getAlumnoDAO());
		this.model.updateModel();
		this.table1.setModel(model);
	}

	public static void main(String[] args) throws SQLException {
		DAOManager manager = new MySQLDAOManager("root", "", "localhost", "escuela");
		java.awt.EventQueue.invokeLater( () ->{
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				ListAlumnosFrame lf = new ListAlumnosFrame(manager);
				lf.setContentPane(lf.panel1);
				lf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				lf.pack();
				lf.setVisible(true);
			} catch (DAOException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
	}
	private JPanel panel1;
	private JButton newButton;
	private JTable table1;
	private JScrollPane tabla;
	private JButton editButton;
	private JButton deleteButton;
	private JButton saveButton;
	private JButton cancelButton;
	private JLabel foundRecords;


}
