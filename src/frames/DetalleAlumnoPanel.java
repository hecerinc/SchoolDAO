package frames;

import modelo.Alumno;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetalleAlumnoPanel {
	private Alumno alumno;
	private boolean editable;

	public DetalleAlumnoPanel(){
		this.fecha_nac.setFormatterFactory(new JFormattedTextField.AbstractFormatterFactory() {
			@Override
			public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
				return new DateFormatter(new SimpleDateFormat("dd/MM/yyyy"));
			}
		});
	}
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
			fecha_nac.setValue(null);
		}
		nombre.requestFocus();
	}

	public void saveData() throws ParseException {
		if(alumno == null){
			alumno = new Alumno();
		}
		alumno.setNombre(nombre.getText());
		alumno.setApellidos(apellidos.getText());
		fecha_nac.commitEdit();
		alumno.setBirthDate((Date) fecha_nac.getValue());
	}

	private JPanel panel1;
	private JTextField nombre;
	private JTextField apellidos;
	private JFormattedTextField fecha_nac;
}
