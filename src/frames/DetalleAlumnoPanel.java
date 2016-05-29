package frames;

import modelo.Alumno;

import javax.swing.*;
import java.util.Date;

public class DetalleAlumnoPanel {
	private Alumno alumno;
	private boolean editable;

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
		nombre.setEditable(editable);
		apellidos.setEditable(editable);
		fecha_nac.setEditable(editable);
	}

	public void loadData(){
		if(alumno!= null){
			nombre.setText(alumno.getNombre());
			apellidos.setText(alumno.getApellidos());
			fecha_nac.setValue(alumno.getBirthDate());
		}
		else{
			nombre.setText("");
			apellidos.setText("");
			fecha_nac.setText("");
		}
		nombre.requestFocus();
	}

	public void saveData(){
		if(alumno == null){
			alumno = new Alumno();
		}
		alumno.setNombre(nombre.getText());
		alumno.setApellidos(apellidos.getText());
		alumno.setBirthDate((Date) fecha_nac.getValue());
	}

	private JPanel panel1;
	private JTextField nombre;
	private JTextField apellidos;
	private JFormattedTextField fecha_nac;
}
