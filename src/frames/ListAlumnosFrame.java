package frames;

import dao.DAOException;
import dao.DAOManager;
import dao.mysql.MySQLDAOManager;
import modelo.Alumno;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;

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
		this.detalle.setEditable(false);
		editButton.addActionListener(this::editActionPerformed);
		cancelButton.addActionListener(this::cancelActionPerformed);
		newButton.addActionListener(this::addActionPerformed);
		saveButton.addActionListener(this::saveActionPerformed);
		deleteButton.addActionListener(this::deleteActionPerformed);
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
		clearFields();
	}

	private void addActionPerformed(ActionEvent evt){
		detalle.setAlumno(null);
		detalle.loadData();
		detalle.setEditable(true);
		saveButton.setEnabled(true);
		cancelButton.setEnabled(true);
	}

	private void saveActionPerformed(ActionEvent evt) {
		// Get la info del alumno de los fields
		try {
			detalle.saveData();
			Alumno a = detalle.getAlumno();
			if(a.getId() == null){
				// Nuevo alumno
				manager.getAlumnoDAO().insert(a);
			}
			else{
				// Alumno ya existente
				manager.getAlumnoDAO().modify(a);
			}
			// Clear fields here
			clearFields();
			this.model.updateModel();
			this.model.fireTableDataChanged();
		} catch (ParseException | DAOException e) {
			e.printStackTrace();
		}

	}
	private void clearFields(){
		detalle.setAlumno(null);
		detalle.setEditable(false);
		detalle.loadData();
		table1.clearSelection();
		saveButton.setEnabled(false);
		cancelButton.setEnabled(false);
	}
	private void deleteActionPerformed(ActionEvent evt){
		if(JOptionPane.showConfirmDialog(rootPane,  "¿Seguro que quieres borrar a este alumno?",
				"Borrar alumno", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
			try {
				Alumno alumno = getSelectedAlumno();
				manager.getAlumnoDAO().delete(alumno);
				model.updateModel();
				model.fireTableDataChanged();
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
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
