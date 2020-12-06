package server_view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import server_manager.SStudentManager;
import valueObject.VStudent;

public class StuList extends JTable{
	// data model
	private DefaultTableModel tableModel;
	// entity data
	private Vector<VStudent> students;
	// selected lectures
	private Vector<VStudent> selectedStudents;
	//controller
	private SStudentManager sStudentManager;
	
	//constructor
	public StuList() {
		// data model
		Vector<String> header = new Vector<String>();
		header.addElement("학번");
		header.addElement("이름");
		header.addElement("ID");
		header.addElement("학과 번호");
		this.tableModel = new DefaultTableModel(header, 0);
		this.setModel(this.tableModel);
		
		this.sStudentManager = new SStudentManager("student");
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	
	public void showInfos() throws FileNotFoundException {
		try {
			this.tableModel.setRowCount(0);

			this.students = this.sStudentManager.getInfos();
			Vector<String> rowData = null;
			for (VStudent student: students) {
				rowData = new Vector<String>();
				rowData.addElement(Integer.toString(student.getId()));
				rowData.addElement(student.getName());
				rowData.addElement(student.getUserName());
				rowData.addElement(Integer.toString(student.getDepartmentId()));
	
				this.tableModel.addRow(rowData);
			}
			this.updateUI();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getUserName(int index) throws FileNotFoundException {
		this.students = this.sStudentManager.getInfos();
		return this.students.get(index).getUserName();
		
	}
}
