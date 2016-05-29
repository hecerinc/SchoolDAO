package frames;

import dao.DAOException;
import dao.DAOManager;
import dao.mysql.MySQLDAOManager;
import modelo.Alumno;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class ListAlumnosFrame extends JFrame{

	private DAOManager manager;
	private AlumnosTableModel model;

	public ListAlumnosFrame(DAOManager manager) throws DAOException{
		this.manager = manager;
		this.model = new AlumnosTableModel(manager.getAlumnoDAO());
		this.model.updateModel();
		this.table1.setModel(model);
		this.table1.getSelectionModel().addListSelectionListener(e -> {
			boolean seleccionValida =  table1.getSelectedRow() != -1;
			editButton.setEnabled(seleccionValida);
			deleteButton.setEnabled(seleccionValida);
		});
		editButton.addActionListener(this::editActionPerformed);
		cancelButton.addActionListener(this::cancelActionPerformed);
		newButton.addActionListener(this::addActionPerformed);
	}

	private void editActionPerformed(ActionEvent evt){
		try {
			Alumno alumno = getSelectedAlumno();
			detalle.setAlumno(alumno);
			detalle.setEditable(true);
			detalle.loadData();
			saveButton.setEnabled(true);
			cancelButton.setEnabled(true);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	private void cancelActionPerformed(ActionEvent evt){
		detalle.setAlumno(null);
		detalle.setEditable(false);
		detalle.loadData();
		table1.clearSelection();
		saveButton.setEnabled(false);
		cancelButton.setEnabled(false);
	}

	private void addActionPerformed(ActionEvent evt){
		detalle.setAlumno(null);
		detalle.loadData();
		detalle.setEditable(true);
		saveButton.setEnabled(true);
		cancelButton.setEnabled(true);
	}

	private Alumno getSelectedAlumno() throws DAOException {
		Long id = (Long) table1.getValueAt(table1.getSelectedRow(), 0);
		return manager.getAlumnoDAO().get(id);
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
	private JPanel panel2;
	private DetalleAlumnoPanel detalle;


}
